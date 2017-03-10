package chapter1_fundamentals.C1_3_BagsQueuesStacks.exercise;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;

import java.util.Scanner;

/**
 * 编写一个Stack的用例Parentheses,从标准输入中读取一个文本流并使用栈判定其中的括号是否配对完整.
 * 例如:对于{@code [()]{}{[()()]()} }程序应该打印true,对于{@code [(]] }则打印false.
 * <p>
 * Created by SylvanasSun on 2017/3/10.
 */
public class Ex_1_3_04 {

    /**
     * 判断括号是否完整
     */
    private static boolean isParenthesesFull(String s) {
        Stack<Character> stack = new Stack<Character>();
        int n = s.length();

        //遍历字符串中的每一个字符用来判断
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if ((c == ')' && (stack.isEmpty() || stack.pop() != '(')) ||
                    (c == ']' && (stack.isEmpty() || stack.pop() != '[')) ||
                    (c == '}' && (stack.isEmpty() || stack.pop() != '{'))) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().trim();
        System.out.println(isParenthesesFull(s));
    }

}
