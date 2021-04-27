import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Main(), "", 64 * 1024 * 1024).start();
    }


    public void run() {
        try {
            Scanner input = new Scanner(new File("input.txt"));

            Stack<Character> bracket = new Stack<>();
            boolean isEnd = false;
            int prefix = 0;

            if (input.hasNext()) {
                String str = input.next();
                char[] brackets = str.toCharArray();

                for (int i = 0; i < brackets.length; ++i) {
                    if (brackets[i] == '(')
                        bracket.push('(');
                    else if (brackets[i] == '[')
                        bracket.push('[');
                    else if (brackets[i] == '{')
                        bracket.push('{');
                    else if (brackets[i] == ')') {
                        if (bracket.isEmpty() || bracket.pop() != '(') {
                            isEnd = true;
                            break;
                        }
                    } else if (brackets[i] == ']') {
                        if (bracket.isEmpty() || bracket.pop() != '[') {
                            isEnd = true;
                            break;
                        }
                    } else if (brackets[i] == '}') {
                        if (bracket.isEmpty() || bracket.pop() != '{') {
                            isEnd = true;
                            break;
                        }
                    }
                    ++prefix;
                }
            }
            FileWriter output = new FileWriter("output.txt", false);
            if (!isEnd && bracket.isEmpty())
                output.write("YES");
            else {
                output.write("NO\n");
                output.write(Integer.toString(prefix));
            }
            output.flush();
            output.close();
        } catch (IOException ignored) {
        }
    }
}
