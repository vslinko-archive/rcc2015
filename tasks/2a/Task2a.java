import java.util.Scanner;

public class Task2a {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int t = in.nextInt();

        String output = "";

        for (int i = 0; i < t; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int k = in.nextInt();

            int days = 0;
            int result = -1;

            while (a > 0 && b > 0) {
                if (a * k == b || b * k == a) {
                    result = days;
                    break;
                }

                days += 1;
                a -= 1;
                b -= 1;
            }

            output += String.valueOf(result);
            output += "\n";
        }

        System.out.print(output);
    }
}
