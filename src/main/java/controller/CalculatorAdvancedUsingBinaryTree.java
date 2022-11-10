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
    Lexer lexer = new Lexer();

    public void start() {
        start(System.in, System.out);
    }

    public void start(InputStream inputStream, PrintStream outputStream) {
        final Scanner scanner = new Scanner(inputStream);
        List<Token> tokens;

        boolean inputValid = false;
        while (!inputValid) {
            try {
                outputStream.print("Enter your calculation: ");
                String input = scanner.nextLine();
                inputValid = validateInput(input);
                tokens = lexer.lex(input);
//                System.out.println(tokens);
                Node lastNode = new Parser(tokens).parse();
                final Double result = lastNode.compute();
                outputStream.println("Result: " + result);
            } catch (CalculatorException e) {
                outputStream.println(e.getMessage() + " Please enter a valid and correctly formatted mathematical calculation.");
//                e.printStackTrace();
            }
        }
//        scanner.close();
//        printMath(parsedNumbers, operators);
    }

    private boolean validateInput(String input) {
        //Mag starten met meerdere - of + gevolgd door getal.
        //Mag daarna meerdere - of + bevatten maar niet gecombineerd +-.
        //Meerdere * of / niet toegestaan.
        if (input.matches("^(-*|\\+*)\\d+\\.?\\d*\\s*(\\s*(\\++|-+|[*/]{1})\\s*\\d+\\.?\\d*)*")) {
//            logger.log(Level.INFO, "Input is valid.");
            return true;
        } else {
            throw new CalculatorException("Syntax error. Input does not meet regex pattern.");
        }
    }

    private List<String> cleanOperators(List<String> tokens) {
        List<String> cleanedTokens = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("-")) {
                count++;
                String nextToken = tokens.get(i + 1);
                if (!nextToken.equals("-")) {
                    int multiplier = (count % 2 == 0) ? +1 : -1;
                    nextToken = String.valueOf(multiplier * Integer.parseInt(nextToken));
                    cleanedTokens.add(nextToken);
                    i++;
                }
            } else if (!tokens.get(i).isBlank()) { // multiple +++ splits into an empty element which we want to omit
                cleanedTokens.add(tokens.get(i));
            }
        }
        return cleanedTokens;
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
