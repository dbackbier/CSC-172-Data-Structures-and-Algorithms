// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 1 - Infix Calculator

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class URCalculator {
    /*  Addition (+)
    •Subtraction (-)
    •Multiplication (*)
    •Division (/)
    •Exponentiation (^)
    •Equal to (=)
    •Less than (<)
    •Greater than (>)
    •Logical AND (&)
    •Logical OR (|)
    •Logical NOT (!)
    •Parentheses (())
    For extra credit,you may add additional operations like
    •Modulo (%)
    •Sin (sin)
    •Cin (cos)
    •Tin (tan)
    */
    private static final Map<String, Integer> precedence = Map.ofEntries( // precedence map for translation
        Map.entry("|", 1),
        
        Map.entry("&", 2),

        Map.entry("=", 3),
        Map.entry("<", 3),
        Map.entry(">", 3),

        Map.entry("+", 4),
        Map.entry("-", 4),

        Map.entry("*", 5),
        Map.entry("/", 5),
        Map.entry("%", 5),

        Map.entry("^", 6),

        Map.entry("!", 7),
        Map.entry("sin", 7),
        Map.entry("cos", 7),
        Map.entry("tan", 7)
    );

    private static final Map<String, java.util.function.DoubleUnaryOperator> functions = Map.of(
    "sin", Math::sin,
    "cos", Math::cos,
    "tan", Math::tan
    );

    /*  Operands: send to queue
    Close parenthesis: pop stack and send to queue until you find an open parenthesis
    Operators:
    1. Pop all stack symbols and send to queue until a symbol of lower precedence
    (or a right associative symbol of equal precedence) appears.
    2. Push operator
    EOF: pop all remaining stack symbols and send to queue
    */
    public static URQueue<String> translate(String[] infix) {
        URQueue<String> output = new URQueue<String>();
        URStack<String> operators = new URStack<String>();

        for (String item : infix) {
            if (isNumber(item)) { // if operand, enqueue
                output.enqueue(item);
            } else if (precedence.containsKey(item)) { // if operator, pop from operators stack until it's empty, or hits an "(", or find a lower precedence operator
                while (!operators.isEmpty() && !operators.peek().equals("(") && (precedence.get(operators.peek()) > precedence.get(item))) {
                    output.enqueue(operators.pop());
                }
                operators.push(item);
            } else if (functions.containsKey(item)) {
                operators.push(item);
            } else if ("(".equals(item)) {
                operators.push(item);
            } else if (")".equals(item)) {
                while (!operators.peek().equals("(")) { // pop from operators stack until you find a "("
                    output.enqueue(operators.pop());
                }
                operators.pop(); // pop the "("
            }
        }
        while (!operators.isEmpty()) { // flush out the rest
            output.enqueue(operators.pop());
        }

        return output;
    }
    

    /*  Postfix evaluation using a Stack
    1. Made an empty Stack
    2. Read token until EOF
        a. If operand push onto Stack
        b. If operator
            i. Pop two stack values
            ii. Perform binary operation
            iii. Push result
    3. At EOF, pop final result
    */
    public static double postFixEval(URQueue<String> postfix) {
        URStack<Double> stack = new URStack<>();

        while (!postfix.isEmpty()) {
            String item = postfix.dequeue();
            if (isNumber(item)) { // if operand, push onto stack
                stack.push(Double.valueOf(item));
            } else if (functions.containsKey(item)) { // if trig function pop one stack value and perform operation, push result
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Too few operands for operator '" + item + "' at this point.");
                }
                double n = stack.pop();
                stack.push(functions.get(item).applyAsDouble(n));
            } else if (item.equals("!")) { // if unary, pop stack value and perform operation, push result
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Too few operands for operator '" + item + "' at this point.");
                }
                double n = stack.pop();
                stack.push((n == 0) ? 1.0 : 0.0); // either return 1 or 0 for true or false
            } else { // if a regular operator, pop two stack values and perform operation, push result
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Too few operands for operator '" + item + "' at this point.");
                }
                double b = stack.pop();
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Too few operands for operator '" + item + "' at this point.");
                }
                double a = stack.pop();
                switch (item) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                    case "%":
                        stack.push(a % b);
                        break;
                    case "^":
                        stack.push(Math.pow(a, b));
                        break;
                    case "=":
                        stack.push((a == b) ? 1.0 : 0.0);
                        break;
                    case "<":
                        stack.push((a < b) ? 1.0 : 0.0);
                        break;
                    case ">":
                        stack.push((a > b) ? 1.0 : 0.0);
                        break;
                    case "&":
                        stack.push(((a != 0) && (b != 0)) ? 1.0 : 0.0);
                        break;
                    case "|":
                        stack.push(((a != 0) || (b != 0)) ? 1.0 : 0.0);
                        break;
                    default: throw new IllegalArgumentException("Invalid operator: " + item);
                }
            }
        }
        if (stack.size() != 1) { // size should be 1
            throw new IllegalArgumentException("Invalid expression: too little or too many values left in the stack.");
        }
        return stack.pop(); // return the final result
    }

    public static String[] tokenize(String exp) {
        URLinkedList<String> tokens = new URLinkedList<String>();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (Character.isDigit(c) || c == '.') { // if c is a digit or decimal point
                current.append(c);
                continue;
            }
            if (Character.isLetter(c)) { // for trig functions
                current.append(c);
                continue;
            }

            flushToken(current, tokens);

            if ("+-*/%^<>=&|".indexOf(c) != -1) { // if operator
                tokens.add(String.valueOf(c));
                continue;
            }

            if (c == '(' || c == ')') { // if parenthesis
                tokens.add(String.valueOf(c));
                continue;
            }

            if (c == '!') { // if unary
                tokens.add("!");
                continue;
            }
        }

        if (current.length() > 0) { // if unary
            tokens.add(current.toString());
        }

        Object[] array = tokens.toArray();
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) { // create new String[] to be returned
            result[i] = (String) array[i];
        }
        return result;
    }

    private static void flushToken(StringBuilder current, URLinkedList<String> tokens){
        if (current.length() > 0) {
            tokens.add(current.toString());
            current.setLength(0);
        }
    }


    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s); // if parseDouble causes error NumberFormatException s is not a double
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void arrayToFile(Object[] arr, String fileName) {
        try {
            String file = fileName + ".txt";
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < arr.length; i++) {
                writer.write(arr[i] + "\n"); // write each array element to a file
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printAll(Object[] arr) { // print all array elements, mainly used for debugging
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                System.out.print(arr[i] + ", ");
            } else {
                System.out.print(arr[i]);
            }
        }
        System.out.print("]" + "\n");
    }

    public static void main(String[] args) {
        if (args.length != 2) { // expects infix expressions and postfix evaluations (results)
            throw new IllegalArgumentException("Usage: java URCalculator \"<infix expression>\" <expected result>");
        }

        String infixFile = args[0];
        String expectedResult = args[1];

        URLinkedList<String> myOutput = new URLinkedList<>(); // to be checked against correctOutput
        URLinkedList<String> correctOutput = new URLinkedList<>();
        try {
            File file = new File(infixFile);
            File ansFile = new File(expectedResult);

            Scanner scanner = new Scanner(file); // used to scan infixFile
            Scanner scanner2 = new Scanner(ansFile); // used to scan expectedResult
            while (scanner.hasNextLine()) {
                String expression = scanner.nextLine().trim();
                if (expression.isEmpty()) {
                    continue;
                }
                String[] tokens = URCalculator.tokenize(expression); // tokenize the expression
                URQueue<String> postfix = URCalculator.translate(tokens); // translate to postfix
                System.out.print("Tokenized Expression: ");
                printAll(tokens); // help debug
                System.out.print("Post fix expression: ");
                printAll(postfix.toArray()); // help debug
                try {
                    double result = postFixEval(postfix);
                    result = Math.round(result * 100.0) / 100.0;
                    String ans = String.format("%.2f", result); // evaluate postfix expression and format it to 2 decimal points
                    System.out.println("My answer to " + expression + " is " + ans + ".\n");
                    myOutput.add(ans);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error evaluating expression: " + expression);
                    System.err.println(e.getMessage());
                }
            }
            scanner.close();

            while (scanner2.hasNextLine()) {
                correctOutput.add(scanner2.nextLine().trim()); // add each correct answer to correctOutput LL
            }
            scanner2.close();
            System.out.print("myOutput: ");
            printAll(myOutput.toArray()); // used for debugging
            System.out.print("correctOutput: ");
            printAll(correctOutput.toArray()); // used for debugging
            if (Arrays.equals(myOutput.toArray(), correctOutput.toArray())) { // compare URCalculator answers and correct answers, adding more expressions to infix file will cause this to be false
                System.out.println("URCalculator calculated all expressions from infix_expr_short.txt correctly.");
            } else {
                System.out.println("URCalculator was not able to calculate all expressions from infix_expr_short.txt correctly.");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        arrayToFile(myOutput.toArray(), "myOutput"); // create a file of URCalculator answers
    }
}
