import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


class Node implements Comparable<Node> {
    int n;
    double value;
    Node[] sons;

    Node(double value, int n) {
        this.value = value;
        this.n = n;
    }

    void split(int k, double[] a) {
        sons = new Node[k];
        for (int i = 0; i < k; ++i) {
            sons[i] = new Node(this.value * a[i], this.n + 1);
        }
    }

    @Override
    public int compareTo(Node node) {
        return -Double.compare(this.value, node.value);
    }
}

class CompleteTree {

    Queue<Node> activeLeafs = new PriorityQueue<>();

    int k;
    double[] a;

    CompleteTree(int k, double[] a) {
        activeLeafs.add(new Node(1, 0));
        this.k = k;
        this.a = a;
    }


    void split() {
        Node temp = activeLeafs.poll();
        temp.split(k, a);
        activeLeafs.addAll(Arrays.asList(temp.sons));
    }

    void doSplit(double d_n) {
        while (activeLeafs.size() + k - 1 <= d_n)
            this.split();
    }

    double getAverageLength(double d_n) {
        doSplit(d_n);
        double length = 0;
        for (Node i : activeLeafs)
            length += i.n * i.value;
        return length;
    }
}

public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }


    public void run() {
        try {
            Scanner input = new Scanner(new File("Tunstall.in"));

            int d = input.nextInt();
            int k = input.nextInt();
            int n = input.nextInt();

            double[] a = new double[k];

            for (int i = 0; i < k; ++i)
                a[i] = input.nextDouble();

            CompleteTree completeTree = new CompleteTree(k, a);


            FileWriter output = new FileWriter("Tunstall.out", false);

            double result = completeTree.getAverageLength(Math.pow(d, n));

            output.write(Double.toString(result));
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
