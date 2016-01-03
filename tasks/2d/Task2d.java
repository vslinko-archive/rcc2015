import java.lang.OutOfMemoryError;
import java.lang.System;
import java.math.BigInteger;
import java.util.Scanner;

public class Task2d {
    private static BigInteger[] answers;
    private static BigInteger mod = new BigInteger("1000000023");
    private static boolean cachedAlgo;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int t = in.nextInt();

        int maxN = 0;
        int ns[] = new int[t];
        long ks[] = new long[t];

        for (int i = 0; i < t; i++) {
            ns[i] = in.nextInt();
            ks[i] = in.nextLong();

            if (ns[i] > maxN) {
                maxN = ns[i];
            }
        }

        try {
            answers = new BigInteger[maxN];
            answers[0] = BigInteger.ONE;
            answers[1] = BigInteger.ONE;
            for (int i = 2; i < maxN; i++) {
                answers[i] = BigInteger.ZERO;
            }

            cachedAlgo = true;
        } catch (OutOfMemoryError e) {
            cachedAlgo = false;
        }

        for (int i = 0; i < t; i++) {
            test(ns[i], ks[i]);
        }
    }

    private static void test(int n, long k) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 1; i <= n; i++) {
            result = result.add(fibonacci(i).modPow(BigInteger.valueOf(k), mod));
        }

        System.out.println(result.mod(mod));
    }

    private static BigInteger fibonacci(int n) {
        return cachedAlgo ? cachedFibonacci(n) : fastFibonacciDoubling(n);
    }

    private static BigInteger cachedFibonacci(int n) {
        if ((n == 1) || (n == 2)) {
            return answers[0];
        }

        if (answers[n - 1].compareTo(BigInteger.ZERO) != 0) {
            return answers[n - 1];
        }

        if (answers[n - 2].compareTo(BigInteger.ZERO) == 0) {
            answers[n - 2] = cachedFibonacci(n - 1);
        }

        if (answers[n - 3].compareTo(BigInteger.ZERO) == 0) {
            answers[n - 3] = cachedFibonacci(n - 2);
        }

        return answers[n - 2].add(answers[n - 3]);
    }

    private static BigInteger fastFibonacciDoubling(int n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        int m = 0;
        for (int i = 31 - Integer.numberOfLeadingZeros(n); i >= 0; i--) {
            BigInteger d = a.multiply(b.shiftLeft(1).subtract(a));
            BigInteger e = a.multiply(a).add(b.multiply(b));
            a = d;
            b = e;
            m *= 2;

            if (((n >>> i) & 1) != 0) {
                BigInteger c = a.add(b);
                a = b;
                b = c;
                m++;
            }
        }
        return a;
    }
}
