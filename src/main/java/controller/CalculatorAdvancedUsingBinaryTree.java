package controller;

import exception.CalculatorException;
import lexer.Lexer;
import lexer.Token;
import parser.Node;
import parser.Parser;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorAdvancedUsingBinaryTree {
    Logger logger = Logger.getLogger(CalculatorIntermediate.class.getName());
    private final InputStream inputStream;
    private final PrintStream outputStream;
    Lexer lexer;

    public CalculatorAdvancedUsingBinaryTree(InputStream inputStream, PrintStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.lexer = new Lexer();
    }

//    public void start() {
//        start(System.in, System.out);
//    }

    public void start() {
        final Scanner scanner = new Scanner(inputStream);
        List<Token> tokens;

        try {
            outputStream.print("Enter your calculation: ");
            String input = scanner.nextLine();
//            validateInput(input);
//            if (input.length() < 1) {
//                throw new CalculatorException("empty");
//            }
            tokens = lexer.lex(input);
            if (tokens.size() < 0) {
                return;
            }
            Node lastNode = new Parser(tokens).parse();
            final double result = lastNode.compute();
            outputStream.println("Result: " + result);
            scanner.reset();
        } catch (CalculatorException e) {
            outputStream.println(e.getMessage() + " Please enter a valid and correctly formatted mathematical expression.");
//                e.printStackTrace();
        }
//        }
//        scanner.close();
//        printMath(parsedNumbers, operators);
    }

    private void validateInput(String input) {
        //Mag starten met meerdere - of + gevolgd door getal.
        //Mag daarna meerdere - of + bevatten maar niet gecombineerd +-.
        //Meerdere * of / niet toegestaan.
        if (!input.matches("^(-*|\\+*)\\d+\\.?\\d*\\s*(\\s*(\\++|-+|[*/]{1})\\s*\\d+\\.?\\d*)*")) {
            throw new CalculatorException("Syntax error (input does not meet regex pattern).");
        }
//            logger.log(Level.INFO, "Input is valid.");
    }

    private static void printMath(int[] numbers, String[] operators) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < operators.length; i++) {
            stringBuilder.append(numbers[i])
                    .append(" ")
                    .append(operators[i])
                    .append(" ");
        }
        stringBuilder.append(numbers[numbers.length - 1]);
        System.out.println("Calculation of: " + stringBuilder);
    }
}
