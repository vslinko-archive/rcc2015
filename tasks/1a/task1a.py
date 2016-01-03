import sys
from itertools import islice

def read_string():
    return sys.stdin.readline().strip()

def read_int():
    return int(read_string())

def read_ints(count):
    string = read_string()
    ints = list(map(int, string.split(' ')))
    return ints

def test():
    n, l = read_ints(2)

    a = read_ints(n)
    b = read_ints(n)
    a_smallest = islice(sorted(a), l)
    b_biggest = islice(reversed(sorted(b)), l)
    a_sum = sum(a_smallest)
    b_sum = sum(b_biggest)

    if a_sum > b_sum:
        return "YES"
    else:
        return "NO"

def main():
    t = read_int()

    for i in range(t):
        print(test())

if __name__ == '__main__':
    main()
