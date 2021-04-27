import java.util.Scanner;
import java.util.TreeSet;

public class Main {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] numbers = new int[n];
        int[] lengths = new int[n];
        for (int i = 0; i < n; ++i) {
            numbers[i] = input.nextInt();
            f(i, n, numbers, lengths);
        }
        int start = 0;
        int max = findMax(n, lengths);
        System.out.println(max + 1);
        for (int i = 0; i < n; ++i) {
            if (lengths[i] == max) {
                start = i;
                break;
            }
        }
        TreeSet<Integer> indexes = new TreeSet<>();
        indexes.add(start + 1);

        int temp = start;

        for (int i = start - 1; i >= 0; --i) {
            if (lengths[i] == max - 1) {
                if (numbers[temp] % numbers[i] == 0) {
                    indexes.add(i + 1);
                    temp = i;
                    --max;
                }
            }
        }


        for (int i : indexes)
            System.out.print(Integer.toString(i) + ' ');
    }

    private static void f(int i, int n, int[] numbers, int[] lengths) {
        for (int j = 0; j < i; j++) {
            if (numbers[j] != 0)
                if (numbers[i] % numbers[j] == 0 && lengths[i] < lengths[j] + 1)
                    lengths[i] = lengths[j] + 1;
        }
    }

    private static int findMax(int n, int[] d) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            if (max < d[i]) {
                max = d[i];
            }
        }
        return max;
    }

}


