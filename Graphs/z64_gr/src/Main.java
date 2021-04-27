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
            int m = input.nextInt();

            int[][] table = new int[n][n];

            for (int k = 0; k < m; ++k){
                int i = input.nextInt();
                int j = input.nextInt();
                table[i-1][j-1] = 1;
                table[j-1][i-1] = 1;
            }

            FileWriter output = new FileWriter("output.txt", false);
            for(int i = 0; i < n; ++i){
                for(int j = 0; j<n;++j){
                    output.write(table[i][j] + " ");
                }
                output.write('\n');
            }

            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
