import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    private int upper_bound(long[] arr, int n, long number, int l, int r) {
        int k;
        while (l < r) {
            k = (l + r) / 2;
            if (number >= arr[k]) {
                l = k + 1;
            } else {
                r = k;
            }
        }
        return r;
    }

    private int longestIncreasingSubsequenceLength(long[] numbers, int n) {

        if (n == 1)
            return 1;

        int length = 0;

        long[] subsequence = new long[n];

        for (int i = 0; i < n; ++i)
            subsequence[i] = Long.MAX_VALUE;

        subsequence[0] = numbers[0];

        int index;

        for (int i = 1; i < n; ++i) {
            index = upper_bound(subsequence, n, numbers[i], 0, length + 1);

            if (index == 0)
                subsequence[0] = numbers[i];
            else if (subsequence[index - 1] != numbers[i]) {
                subsequence[index] = numbers[i];
                if (index > length)
                    ++length;
            }
        }
        return length + 1;
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

            int n = input.nextInt();
            long[] numbers = new long[n];

            for (int i = 0; i < n; ++i)
                numbers[i] = input.nextLong();

            FileWriter output = new FileWriter("output.txt", false);
            output.write(Integer.toString(longestIncreasingSubsequenceLength(numbers, n)));
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
