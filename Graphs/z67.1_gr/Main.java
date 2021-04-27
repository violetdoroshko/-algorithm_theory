import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Main implements Runnable {
    private int counter = 0;

    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    private void bfs(int v, int[] bfs, boolean[] wasThere, ArrayList<Integer>[] g, Queue<Integer> q) {

        if (!wasThere[v]) {
            q.add(v);
            wasThere[v] = true;
            while (!q.isEmpty()) {
                v = q.poll();
                counter++;
                bfs[v] = counter;

                for (int i = 0; i < g[v].size(); ++i) {
                    int w = g[v].get(i);
                    if (!wasThere[w]) {
                        q.add(w);
                        wasThere[w] = true;
                    }
                }
            }
        }
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

            int n = input.nextInt();
            ArrayList<Integer>[] g = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }
            boolean[] wasThere = new boolean[n];
            int[] bfs = new int[n];
            Queue<Integer> q = new LinkedList<>();

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (input.nextInt() == 1)
                        g[i].add(j);
                }
            }
            for (int i = 0; i < n; ++i)
                bfs(i, bfs, wasThere, g, q);

            FileWriter output = new FileWriter("output.txt", false);
            for (int i = 0; i < n; ++i) {
                output.write(bfs[i] + " ");
            }
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}