import java.io.File;
import java.io.FileNotFoundException;
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
            long n = input.nextLong();
            int k_max = (int) (Math.log(n) / Math.log(2)) + 1;
            long[] binary = new long[k_max];
            for (int i = 0; i < k_max && n != 0; ++i) {
                binary[i] = n % 2;
                n /= 2;
            }

            FileWriter output = new FileWriter("output.txt", false);

            //long temp = 0;
            for (int i = 0; i < k_max; ++i) {
                if (binary[i] == 1) {
                   // temp += Math.pow(2,i);
                    output.write(Integer.toString(i));
                    output.write('\n');
                }
            }
            //output.write(Long.toString(temp));
            output.flush();
            output.close();
        } catch (IOException ignored) {

        }

    }
}
