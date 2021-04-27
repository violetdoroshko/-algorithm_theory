import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;


class Node {
    int key;
    Node left;
    Node right;

    int height;                 //высота
    int leaves;                 //кол-во листов на высоте h
    int msl;                    //максимальный полупуть с корнев в данной вершине
    int b = 0;                  //кличество разных наибольших полупутей для которых вершина корень
    int a = 0;                  //число различных наибольших полупутей, которые приходят из отца

    Node(int key) {
        this.key = key;
    }

    void setKey(int key) {
        this.key = key;
    }
}

class Tree {
    Node root;
    private int msl = 0;             //длина наибольшего полупути

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

    void setParametersOfNodes(Node root) {
        if (root == null)
            return;
        else if (root.left == null && root.right == null) {
            root.height = 0;
            root.leaves = 1;
            root.msl = 0;
        } else {
            setParametersOfNodes(root.left);
            setParametersOfNodes(root.right);

            if (root.left == null) {
                root.leaves = root.right.leaves;
                root.height = root.right.height + 1;
                root.msl = root.height;
            } else if (root.right == null) {
                root.leaves = root.left.leaves;
                root.height = root.left.height + 1;
                root.msl = root.height;
            } else {
                root.height = Math.max(root.left.height, root.right.height) + 1;
                root.msl = root.left.height + root.right.height + 2;
                if (root.left.height == root.right.height)
                    root.leaves = root.left.leaves + root.right.leaves;
                else if (root.left.height > root.right.height)
                    root.leaves = root.left.leaves;
                else root.leaves = root.right.leaves;
            }
        }
    }

    void getMSL(Node node) {
        if (msl < node.msl)
            msl = node.msl;
        if (node.left != null)
            getMSL(node.left);
        if (node.right != null)
            getMSL(node.right);
    }

    void setNumberOfDifferentMSLsToNodes(Node node) {
        firstStep(node);
        secondStep(node);
    }

    private void firstStep(Node node) {
        if (node.msl == this.msl) {
            if (node.left == null)
                node.b = node.right.leaves;
            else if (node.right == null)
                node.b = node.left.leaves;
            else
                node.b = node.right.leaves * node.left.leaves;
        }

        if (node.left != null)
            firstStep(node.left);
        if (node.right != null)
            firstStep(node.right);
    }

    private void secondStep(Node node) {
        if (node == root)
            node.a = 0;
        if (node.left == null && node.right == null)
            return;
        else if (node.left == null) {
            node.right.a = node.a + node.b;
            secondStep(node.right);
        } else if (node.right == null) {
            node.left.a = node.a + node.b;
            secondStep(node.left);
        } else {
            if (node.left.height > node.right.height) {
                node.left.a = node.a + node.b;
                node.right.a = node.b;
            } else if (node.right.height > node.left.height) {
                node.right.a = node.a + node.b;
                node.left.a = node.b;
            } else {
                node.left.a = node.b + node.left.leaves * node.a / node.leaves;
                node.right.a = node.b + node.right.leaves * node.a / node.leaves;
            }
            secondStep(node.left);
            secondStep(node.right);
        }
    }

    void nodesWithOddNumberOfMSL(Node node, TreeSet<Integer> treeSet) {
        if (node == null)
            return;
        if ((node.a + node.b) % 2 == 1)
            treeSet.add(node.key);
        if (node.left != null)
            nodesWithOddNumberOfMSL(node.left, treeSet);
        if (node.right != null)
            nodesWithOddNumberOfMSL(node.right, treeSet);
    }
}


public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(new File("tst.in"));
            Tree tree = new Tree();

            while (input.hasNextInt()) {
                tree.add(input.nextInt());
            }

            tree.setParametersOfNodes(tree.root);
            tree.getMSL(tree.root);
            tree.setNumberOfDifferentMSLsToNodes(tree.root);

            TreeSet<Integer> treeSet = new TreeSet<>();
            tree.nodesWithOddNumberOfMSL(tree.root, treeSet);


            if (treeSet.size() != 0)
                tree.root = tree.delete(tree.root, treeSet.last());

            FileWriter output = new FileWriter("tst.out", false);
            output.write(tree.preOrderTraversal().toString());
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
