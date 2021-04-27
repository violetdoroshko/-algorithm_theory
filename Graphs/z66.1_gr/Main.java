import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }


    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

            int n = input.nextInt();

            int[] vec = new int[n];

            for (int k = 0; k < n - 1; ++k) {
                int i = input.nextInt();
                int j = input.nextInt();
                vec[j - 1] = i;
            }

            FileWriter output = new FileWriter("output.txt", false);
            for (int i = 0; i < n; ++i) {
                output.write(vec[i] + " ");
            }

            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
