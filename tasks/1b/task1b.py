import sys

def read_string():
    return sys.stdin.readline().strip()

def read_int():
    return int(read_string())

def read_ints(count):
    string = read_string()
    ints = list(map(int, string.split(' ')))
    return ints

def test():
    x = read_string()
    y = read_string()
    z = read_string()

    for k in range(2, 36):
        try:
            xk = int(x, k)
            yk = int(y, k)
            zk = int(z, k)
            if not xk * yk == zk:
                return "Finite"
        except:
            pass

    return "Infinity"

def main():
    t = read_int()

    for i in range(t):
        print(test())

if __name__ == '__main__':
    main()
