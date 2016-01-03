import java.lang.System;
import java.util.ArrayList;
import java.util.Scanner;

public class Task2e {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int n = in.nextInt();

            int teleports[][] = new int[n][2];
            int start[] = new int[2];
            int end[] = new int[2];

            for (int j = 0; j < n; j++) {
                teleports[j][0] = in.nextInt();
                teleports[j][1] = in.nextInt();
            }

            start[0] = in.nextInt();
            start[1] = in.nextInt();
            end[0] = in.nextInt();
            end[1] = in.nextInt();

//            if (i != 4) continue;

            ArrayList<int[]> path = new ArrayList<int[]>();
            path.add(start);

            if (go(start, end, teleports, n * n, path)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    public static boolean go(int[] point, int[] end, int[][] teleports, int depth, ArrayList<int[]> path) {
        if (depth == 0) {
//            print(end, path, depth, "FALSE");
            return false;
        }

        for (int[] teleport : teleports) {
            int[] nextPoint = new int[2];

            nextPoint[0] = 2 * teleport[0] - point[0];
            nextPoint[1] = 2 * teleport[1] - point[1];

            if (nextPoint[0] == end[0] && nextPoint[1] == end[1]) {
//                print(end, path, depth, "TRUE");
                return true;
            } else {
                boolean has = false;

                for (int[] passed : path) {
                    if (passed[0] == nextPoint[0] && passed[1] == nextPoint[1]) {
                        has = true;
                        break;
                    }
                }

                if (has) {
                    continue;
                }

                ArrayList<int[]> newPath = new ArrayList<int[]>(path);
                newPath.add(nextPoint);

                if (go(nextPoint, end, teleports, depth - 1, newPath)) {
                    return true;
                }
            }
        }

        return false;
    }
}
