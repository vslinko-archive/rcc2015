import sys
import threading
import queue
import random
import math

def read_string():
    return sys.stdin.readline().strip()

def read_int():
    return int(read_string())

def read_ints(count):
    string = read_string()
    ints = tuple(map(int, string.split(' ')))
    return ints

def single_task(start, end, teleports):
    max_depth = 10

    def go(point, depth, path):
        if depth <= 0:
            return False

        for teleport in teleports:
            next_point = (2*teleport[0]-point[0], 2*teleport[1]-point[1])

            if next_point == end:
                return True
            elif next_point in path:
                continue
            elif go(next_point, depth-1, path + [next_point]):
                return True

        return False

    return go(start, max_depth, [start])

def parallel_task(start, end, teleports):
    max_depth = 15
    threads_limit = 1
    start_queue = queue.Queue()
    end_queue = queue.Queue()
    result = threading.Event()
    ports = {"start": teleports, "end": reversed(teleports)}
    visited = {"start": [start], "end": [end]}

    def process(q, left, right):
        def go(point, depth):
            for teleport in teleports:
                next_point = (2*teleport[0]-point[0], 2*teleport[1]-point[1])

                if next_point in visited[right]:
                    result.set()
                    return
                elif next_point in visited[left]:
                    continue
                else:
                    visited[left].append(next_point)
                    q.put((next_point, depth + 1))

        while not q.empty():
            point, depth = q.get()
            if depth <= max_depth and not result.is_set():
                go(point, depth)
            q.task_done()

    start_queue.put((start, 0))
    end_queue.put((end, 0))

    for i in range(threads_limit):
        thread = threading.Thread(target=process, args=(start_queue, 'start', 'end'))
        thread.start()

    for i in range(threads_limit):
        thread = threading.Thread(target=process, args=(end_queue, 'end', 'start'))
        thread.start()

    start_queue.join()
    end_queue.join()

    return result.is_set()

def main():
    t = read_int()
    parallel = True
    buffered = True
    sort = True
    output = ""

    if t > 100:
        parallel = False
        sort = False

    for i in range(t):
        n = read_int()
        teleports = []
        for j in range(n):
            teleports.append(read_ints(2))
        start = read_ints(2)
        end = read_ints(2)

        if sort:
            teleports = sorted(teleports, key=lambda t: math.sqrt((t[0] - start[0])**2 + (t[1] - start[1])**2))

        if parallel and len(teleports) < 30:
            result = parallel_task(start, end, teleports)
        else:
            result = single_task(start, end, teleports)

        output += "YES" if result else "NO"
        output += "\n"

        if (i > 0 and i % 10000 == 0) or not buffered:
            sys.stdout.write(output)
            output = ""

    sys.stdout.write(output)

if __name__ == '__main__':
    main()
