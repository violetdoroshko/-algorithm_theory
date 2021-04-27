import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new File("input.txt"));
        Set<Integer> tree = new TreeSet<>();
        while (input.hasNextInt()) {
            tree.add(input.nextInt());
        }
        FileWriter output = new FileWriter("output.txt", false);
        output.write(String.valueOf(tree.stream().mapToInt(s -> s).sum()));
        output.flush();
        output.close();
    }
}
