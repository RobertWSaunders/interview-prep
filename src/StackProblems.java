import java.util.*;

/**
 * Implementation of a stack using an array.
 * Author: Robert Saunders
 */
public class StackProblems {
    // implement a three stacks with one array
    // sort a stack in ascending order

    public static char[][] tokens = new char[][]{{ '(', ')'}, { '{', '}'}, { '[', ']'}};

    private static boolean isOpener(char bracket) {
        for (char[] pair : tokens) {
            if (bracket == pair[0]) {
                return true;
            }
        }
        return false;
    }

    private static boolean matches(char opener, char closer) {
        for (char[] pair : tokens) {
            if (pair[0] == opener) {
                return pair[1] == closer;
            }
        }
        return false;
    }

    public static boolean isBalanced(String expression) {
        Stack<Character> active = new Stack<Character>();
        char[] chars = expression.toCharArray();

        for (char bracket : chars) {
            if (isOpener(bracket)) {
                active.push(bracket);
            } else {
                if (active.empty() || !matches(active.pop(), bracket)) {
                    return false;
                }
            }
        }

        return active.empty();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int a0 = 0; a0 < t; a0++) {
            String expression = in.next();
            System.out.println( (isBalanced(expression)) ? "YES" : "NO" );
        }
    }
}

// bracket validator


