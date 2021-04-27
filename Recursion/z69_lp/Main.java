import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    private int func(int i, int[] f, int[] lilies) {
        if (i == 0)
            f[i] = lilies[0];
        else if (i == 1)
            f[i] = -1;
        else if (i == 2)
            f[i] = lilies[0] + lilies[2];
        else if (f[i] == -1)
            f[i] = Math.max(func(i - 2, f, lilies), func(i - 3, f, lilies)) + lilies[i];
        return f[i];
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

           /* int answer = 0;

            int n = input.nextInt();
            if (n == 2)
                answer = -1;
            else {
                int[] lilies = new int[n];           //сколько комаров на i-ой кувшнке
                int[] f = new int[n];                //

                for (int i = 0; i < n; ++i) {
                    lilies[i] = input.nextInt();
                    f[i] = -1;
                }

                if (n == 1) {
                    answer = lilies[0];

                } else {

                    f[0] = lilies[0];
                    f[2] = lilies[2] + f[0];

                    for (int i = 0; i < n - 3; ++i) {
                        f[i + 3] = Math.max(f[i], f[i + 1]) + lilies[i + 3];
                    }

                    answer = f[n - 1];
                }
            }*/

            int n = input.nextInt();
            int[] lilies = new int[n];
            int[] f = new int[n];

            for (int i = 0; i < n; ++i) {
                lilies[i] = input.nextInt();
                f[i] = -1;
            }

            FileWriter output = new FileWriter("output.txt", false);
            int answer = func(n - 1, f, lilies);
            output.write(Integer.toString(answer));
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
