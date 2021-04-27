import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    public long func(int i, int j, int[] n, int[] m, long[][] f) {
        long min = 2147483647;
        if (f[i][j] == 0 && i != j) {
            for (int k = i; k < j; ++k) {
                long temp = func(i, k, n, m, f) + func(k + 1, j, n, m, f) + n[i] * m[k] * m[j];
                if (temp < min)
                    min = temp;
            }
            f[i][j] = min;
        }
        return f[i][j];
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

            int s = input.nextInt();
            int[] n = new int[s];
            int[] m = new int[s];

            for (int i = 0; i < s; ++i) {
                n[i] = input.nextInt();
                m[i] = input.nextInt();
            }

            long[][] f = new long[s][s];
            for (int i = 0; i < s; ++i)
                for (int j = 0; j < s; ++j)
                    f[i][j] = 0;

            FileWriter output = new FileWriter("output.txt", false);

            output.write(Long.toString(func(0, s - 1, n, m, f)));
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}