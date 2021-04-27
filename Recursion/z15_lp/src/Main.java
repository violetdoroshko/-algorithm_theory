import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class MinAndMax {
    long min = Long.MAX_VALUE;
    long max = Long.MIN_VALUE;
}

public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    private MinAndMax f(int i, int j, MinAndMax[][] matrix, long[] numbers, char[] operations) {
        if (matrix[i][j].max == Long.MIN_VALUE && matrix[i][j].min == Long.MAX_VALUE) {
            if (i == j) {
                matrix[i][j].min = numbers[i];
                matrix[i][j].max = numbers[i];
            } else {

                long max = Long.MIN_VALUE;
                long min = Long.MAX_VALUE;
                long temp[] = new long[4];
                for (int k = i; k < j; ++k) {
                    MinAndMax left = f(i, k, matrix, numbers, operations);
                    MinAndMax right = f(k + 1, j, matrix, numbers, operations);

                    temp[0] = doOperation(left.max, operations[k + 1], right.max);
                    temp[1] = doOperation(left.max, operations[k + 1], right.min);
                    temp[2] = doOperation(left.min, operations[k + 1], right.max);
                    temp[3] = doOperation(left.min, operations[k + 1], right.min);

                    for (int l = 0; l < 4; ++l) {
                        if (max < temp[l])
                            max = temp[l];
                        else if (min > temp[l])
                            min = temp[l];
                    }
                }
                matrix[i][j].max = max;
                matrix[i][j].min = min;
            }
        }
        return matrix[i][j];
    }

    private long findMax(int n, long[] d) {
        long max = Long.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            if (max < d[i])
                max = d[i];
        }
        return max;
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

            MinAndMax[][] matrix = new MinAndMax[2 * n - 1][2 * n - 1];
            long[] maxNumbers = new long[n];
            for (int i = 0; i < 2 * n - 1; ++i) {
                for (int j = i; j < 2 * n - 1; ++j) {
                    matrix[i][j] = new MinAndMax();
                }
            }
            for (int i = 0; i < n; ++i)
                maxNumbers[i] = f(i, n + i - 1, matrix, numbers, operations).max;

            long max = findMax(n, maxNumbers);

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
