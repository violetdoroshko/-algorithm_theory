import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Node {
    int key;
    Node left;
    Node right;

    public Node(int key) {
        this.key = key;
    }
}

class Tree {
    private Node root;

    public Tree() {
    }

    public void add(int value) {
        root = add(root, value);
    }

    private Node add(Node node, int value) {
        if (node == null) {
            node = new Node(value);
        } else if (node.key < value) {
            node.right = add(node.right, value);
        } else if (node.key > value) {
            node.left = add(node.left, value);
        }
        return node;
    }

    public StringBuilder preOrderTraversal() {
        StringBuilder str = new StringBuilder();
        preOrderTraversal(root, str);
        return str;
    }

    private void preOrderTraversal(Node node, StringBuilder str) {
        if (node != null) {
            str.append(node.key);
            str.append("\n");
            preOrderTraversal(node.left, str);
            preOrderTraversal(node.right, str);
        }
    }

}


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    public void run() {
        Scanner input = null;
        try {
            input = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
        }
        Tree tree = new Tree();
        while (input.hasNextInt()) {
            tree.add(input.nextInt());
        }
        FileWriter output = null;
        try {
            output = new FileWriter("output.txt", false);
            output.write(tree.preOrderTraversal().toString());
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }

    }
}
