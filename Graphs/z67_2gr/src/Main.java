import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main implements Runnable {
    private int counter = 1;
    private boolean[] wasThere;

    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

            int n = input.nextInt();
            ArrayList<Integer>[] g = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }

            int[] dfs = new int[n];
            wasThere = new boolean[n];

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (input.nextInt() == 1)
                        g[i].add(j);
                }
            }
            for (int i = 0; i < n; ++i) {
                if (!wasThere[i])
                    dfs(i, dfs, g);
            }
            FileWriter output = new FileWriter("output.txt", false);
            for (int i = 0; i < n; ++i) {
                output.write(dfs[i] + " ");
            }
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }

    private void dfs(int v, int[] dfs, ArrayList<Integer>[] g) {
        wasThere[v] = true;
        dfs[v] = counter++;
        for (int i = 0; i < g[v].size(); ++i) {
            int j = g[v].get(i);
            if (!wasThere[j])
                dfs(j, dfs, g);
        }
    }
}