import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    Node root;

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

    Node delete(Node root, int value) {
        if (root == null)
            return null;
        if (value < root.key) {
            root.left = delete(root.left, value);
            return root;
        } else if (value > root.key) {
            root.right = delete(root.right, value);
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
            root.right = delete(root.right, newRootKey);
            return root;
        }
    }

    private Node findMin(Node root) {
        if (root.left != null)
            return findMin(root.left);
        else
            return root;
    }

    int getHeight(Node node) {
        if (node == null)
            return -1;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    void getNodesFromLevel(Node root, int level, ArrayList<Node> treeList) {
        if (root == null) {
            return;
        } else if (level == 0)
            treeList.add(root);
        else {
            getNodesFromLevel(root.left, level - 1, treeList);
            getNodesFromLevel(root.right, level - 1, treeList);
        }
    }

    ArrayList<Node> getNodesWithSameHeightOfSons(ArrayList<Node> treeList) {
        ArrayList<Node> newTreeList = new ArrayList<>();
        for (Node i : treeList) {
            if (getHeight(i.left) == getHeight(i.right))
                newTreeList.add(i);
        }
        return newTreeList;
    }
}


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("in.txt"));
            Tree tree = new Tree();

            while (input.hasNextInt()) {
                tree.add(input.nextInt());
            }

            ArrayList<Node> treeList = new ArrayList<>();

            int height = tree.getHeight(tree.root);
            int level = height - height / 2;

            tree.getNodesFromLevel(tree.root, level, treeList);
            treeList = tree.getNodesWithSameHeightOfSons(treeList);

            if (treeList.size() % 2 != 0) {
                int i = treeList.size() / 2;
                tree.root = tree.delete(tree.root, treeList.get(i).key);
            }
            FileWriter output = new FileWriter("out.txt", false);
            output.write(tree.preOrderTraversal().toString());
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
