import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Node {
    int key;
    Node left;
    Node right;

    Node(int key) {
        this.key = key;
    }

    void setKey(int key) {
        this.key = key;
    }
}

class Tree {
    public Node root;

    Tree() {
    }

    void add(int value) {
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

    StringBuilder preOrderTraversal() {
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

    Node delete_recursively(Node root, int value) {
        if (root == null)
            return null;
        if (value < root.key) {
            root.left = delete_recursively(root.left, value);
            return root;
        } else if (value > root.key) {
            root.right = delete_recursively(root.right, value);
            return root;
        }
        //root.key == value
        if (root.left == null)
            return root.right;
        else if (root.right == null)
            return root.left;
        else {
            int newRootKey = findMin(root.right).key;
            root.setKey(newRootKey);
            root.right = delete_recursively(root.right, newRootKey);
            return root;
        }
    }

    private Node findMin(Node root) {
        if (root.left != null)
            return findMin(root.left);
        else
            return root;
    }
}

public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));
            Tree tree = new Tree();
            int value = input.nextInt();

            while (input.hasNextInt()) {
                tree.add(input.nextInt());
            }

            tree.root = tree.delete_recursively(tree.root, value);

            FileWriter output = new FileWriter("output.txt", false);
            output.write(tree.preOrderTraversal().toString());
            output.flush();
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
