import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    private long f(int i, int j, long[][] matrix, long[] numbers, char[] operations) {
        if (i == j)
            matrix[i][j] = (numbers[i]);
        else if (matrix[i][j] == Long.MIN_VALUE) {
            long max = Long.MIN_VALUE;
            for (int k = i; k < j; ++k) {
                long temp = doOperation(f(i, k, matrix, numbers, operations), operations[k + 1], f(k + 1, j, matrix, numbers, operations));
                if (max < temp)
                    max = temp;
            }
            matrix[i][j] = max;
        }
        return matrix[i][j];
    }

    private void (int j, int n, long[][] matrix){
        for(int i  = j-n; i<2*n)
    }

    private long findMax(int l, int r, long[] d) {
        if (r - l > 1) {
            long left = findMax(l, (l + r) / 2, d);
            long right = findMax((l + r) / 2 + 1, r, d);
            return Math.max(left, right);
        } else
            return Math.max(d[l], d[r]);
    }

    private long doOperation(long number1, char operation, long number2) {

        if (operation == 't')
            return number1 + number2;
        else
            return number1 * number2;
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

            int n = input.nextInt();
            long[] numbers = new long[2 * n];
            char[] operations = new char[2 * n];

            for (int i = 0; i < n; ++i) {
                operations[i] = input.next().charAt(0);
                operations[i + n] = operations[i];
                numbers[i] = input.nextLong();
                numbers[i + n] = numbers[i];
            }

            long[][] matrix = new long[2 * n - 1][2 * n - 1];

            for (int i = 0; i < 2 * n - 2; ++i) {
                for (int j = i + 1; j < n + i && j < 2 * n - 1; ++j)
                    matrix[i][j] = Long.MIN_VALUE;
            }
            int n_new = (int) Math.pow(2, (int) (Math.log(n) / Math.log(2) + 1));
            long[] maxNumbers = new long[n_new];
            for (int i = 0; i < n; ++i)
                maxNumbers[i] = f(i, n + i - 1, matrix, numbers, operations);
            for (int i = n; i < n_new; ++i)
                maxNumbers[i] = Long.MIN_VALUE;

            long max = findMax(0, n_new - 1, maxNumbers);

            FileWriter output = new FileWriter("output.txt", false);
            output.write(Long.toString(max));
            output.write(Character.toString('\n'));
            boolean isWritten = false;
            for (int i = 0; i < n; ++i) {
                if (maxNumbers[i] == max) {
                    if (isWritten)
                        output.write((Character.toString(' ')));
                    output.write(Integer.toString(i + 1));
                    isWritten = true;
                }
            }
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
