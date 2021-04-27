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
            int[] binaryHeap = new int[n + 1];
            for (int i = 1; i < n + 1; ++i)
                binaryHeap[i] = input.nextInt();

            boolean isBinaryHeap = true;

            int leaves = (int) (Math.log(n)/Math.log(2));
            for (int i = n; i > 1; --i) {
                if (binaryHeap[(i) / 2] > binaryHeap[i]) {
                    isBinaryHeap = false;
                    break;
                }
            }

            FileWriter output = new FileWriter("output.txt", false);
            if (isBinaryHeap)
                output.write("Yes");
            else
                output.write("No");
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
