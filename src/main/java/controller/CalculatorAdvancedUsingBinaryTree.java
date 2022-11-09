package controller;

import lexer.Lexer;
import lexer.Token;
import parser.Node;
import parser.Parser;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorAdvancedUsingBinaryTree {
    Logger logger = Logger.getLogger(CalculatorIntermediate.class.getName());
    Lexer lexer = new Lexer();

    public void start() {
        System.out.print("Welcome to TI-002 Advanced Calculator! Let us do the math for you.\n\n");
        final Scanner scanner = new Scanner(System.in);
        List<Token> tokens;

        boolean inputValid = false;
        while (!inputValid) {
            try {
                System.out.print("Enter your calculation: ");
                String input = scanner.nextLine();
                inputValid = validateInput(input);
                if (!inputValid) {
                    throw new InputMismatchException("Syntax error. Input does not meet regex pattern.");
                }
                tokens = lexer.lex(input);
                Node lastNode = new Parser(tokens).parse();
                final Double result = lastNode.compute();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Syntax error. Please enter a valid and correctly formatted mathematical calculation.");
            }
        }
//        scanner.close();
//        printMath(parsedNumbers, operators);
    }

    private boolean validateInput(String input) {
        boolean inputValidity = false;
        //Mag starten met meerdere - of + gevolgd door getal.
        //Mag daarna meerdere - of + bevatten maar niet gecombineerd +-.
        //Meerdere * of / niet toegestaan.
        if (input.matches("^(-*|\\+*)\\d+((\\++|-+|[*/]{1})\\d+)*")) {
            inputValidity = true;
            logger.log(Level.INFO, "Input is valid.");
        }
        return inputValidity;
    }

    private int calculate(List<String> tokens) {
        int result;
        if (tokens.size() < 2) { //need at least 2 digits to do a calculation
            return Integer.parseInt(tokens.get(0));
        }
        //Example Tokens: [, 5, -5, 3, 1, , (, 2, -2, ), -, -8, *, 7, /, 3]
        if (tokens.get(0).isBlank()) { //should only occur if first digit has a + (e.g. +5) which gives an empty element after splitting
            tokens.set(0, "0");
        }
        tokens = cleanOperators(tokens);
        System.out.println("Tokens after cleanOperators: " + tokens);
        tokens = multiplyDivide(tokens);
        System.out.println("Tokens after multipleDivide: " + tokens);
        result = addSubtract(tokens);
        return result;
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

    private int addSubtract(List<String> tokens) {
        //Example Tokens: [5, -5, 3, 1]
        if (tokens.size() < 2) {
            return Integer.parseInt(tokens.get(0));
        }
        int result = Integer.parseInt(tokens.get(0)) + Integer.parseInt(tokens.get(1));
        for (int i = 1; i < tokens.size() - 1; i++) {
            result += Integer.parseInt(tokens.get(i + 1));
        }
        return result;
    }

    private List<String> multiplyDivide(List<String> tokens) {
        final List<String> newTokens = new ArrayList<>();
        int j = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("*")) {
                int result = Integer.parseInt(newTokens.get(j)) * Integer.parseInt(tokens.get(i + 1));
                newTokens.set(j, String.valueOf(result));
                i++;
            } else if (tokens.get(i).equals("/")) {
                int result = Integer.parseInt(newTokens.get(j)) / Integer.parseInt(tokens.get(i + 1));
                newTokens.set(j, String.valueOf(result));
                i++;
            } else { // if it is a number, + or -
                newTokens.add(tokens.get(i));
                j++;
            }
            System.out.println("NewTokens: " + newTokens);
        }
        return newTokens;
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
