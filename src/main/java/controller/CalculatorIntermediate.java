package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorIntermediate {
    Logger logger = Logger.getLogger(CalculatorIntermediate.class.getName());

    public CalculatorIntermediate() {
    }

    //Idee 1: elke character inlezen totdat het niet een getal is, dit getal opslaan in list, de operator ook opslaan in list, doorlezen
    //Idee 2: String splitten obv de operators. Elk element (if getal -> parsen en opslaan in list, else if operator -> operator opslaan in list).
    //TODO: meerdere + veranderen in 1 +. Meerdere - veranderen in 1 - of +.
    //TODO: Is +- toegestaan?
    //TODO: voorrang * en /
    //Tokenizer: lexer - split alles wat geen getal of operator is: lijst van tokens. Omzetten in een abstract syntax tree: structuur om te bepalen welke operator bij welk getal is, subsom uitvoeren.
    public void start() {
        start(System.in, System.out);
    }

    void start(InputStream inputStream, PrintStream outputStream) {
        outputStream.print("Welcome to TI-001 Calculator! Let us do the math for you.\n\n");
        final Scanner scanner = new Scanner(inputStream);
        String[] numbers = new String[0];
        String[] operators = new String[0];

        boolean inputValid = false;
        while (!inputValid) {
            try {
                outputStream.print("Enter your calculation: ");
//            String input = scanner.nextLine().replaceAll("\\s+|[\\s-+*/]+$", ""); // remove whitespaces and trailing whitespaces+operators
                String input = scanner.nextLine().replaceAll("\\s", ""); // remove whitespaces and trailing whitespaces+operators
                inputValid = validateInput(input);
                if (!inputValid) {
                    throw new InputMismatchException("Syntax error. Input does not meet regex pattern.");
                }
                outputStream.println("--After regex: " + input);
                numbers = input.split("[-+*/]+");
                outputStream.println("--Split String[] numbers: " + Arrays.toString(numbers));

                operators = input.split("[^-+*/]+");
                operators = cleanOperators(operators);

                outputStream.println("--Split String[] operators: " + Arrays.toString(operators));
            } catch (Exception e) {
//                e.printStackTrace();
                outputStream.println("Syntax error. Only whole numbers are accepted in this version (e.g. 10). " +
                        "Please enter a valid and correctly formatted mathematical calculation.");
            }
        }
//        scanner.close();

        final int[] parsedNumbers = parseNumbers(numbers);
        final int result = feedCalculator(parsedNumbers, operators);
        printMath(parsedNumbers, operators);
        outputStream.println("Result: " + result);
    }

    private boolean validateInput(String input) {
        boolean inputValid = false;
//        boolean validNumbers = false;
//        boolean validOperators = false;
        //Mag starten met meerdere - of + gevolgd door getal.
        //Mag daarna meerdere - of + bevatten maar niet gecombineerd +-.
        //Meerdere * of / niet toegestaan.
        if (input.matches("^(-*|\\+*)\\d+((\\++|-+|[*/]{1})\\d+)*")) {
            inputValid = true;
            logger.log(Level.INFO, "Input is valid.");
//        } else {
//            throw new InputMismatchException("Syntax error. Input does not meet regex pattern.");
        }
        return inputValid;

//        String[] operators = input.split("[^-+*/]+");
//        for (String operator : operators) {
//            if (operator.matches("-+|\\++|\\*{1}|/{1}")) {
//                validOperators = true;
//            }
//
//        }
/*        System.out.println("Split string[] operators again: " + Arrays.toString(operators));
//        for (String operator : operators) {
        int i = 0;
        while (i < operators.length && validOperators == false) {
            if (operators.length() > 1) {
                //String met meervoudige operators veranderen in charArray en hierdoorheen loopen
                char[] operatorArray = operator.toCharArray();
                System.out.println("operatorArray" + Arrays.toString(operatorArray));
                for (char item : operatorArray) {
*//*                if (item == operatorArray[0]) {
                    validOperators = false;
                    break;*//*

                    if (item != operatorArray[0]) { // meervoudige operators (e.g. ---) moeten hetzelfde zijn, (+-) niet toegestaan.
                        validOperators = true;
                    } else {
                        validOperators = false;
                    }
                }
            }
        }*/
    }


    public int[] parseNumbers(String[] numbers) {
        final int[] parsedNumbers = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            if (!numbers[i].equals("")) {
                parsedNumbers[i] = Integer.parseInt(numbers[i]);
            }
        }
        System.out.println("--Parsed int[] numbers: " + Arrays.toString(parsedNumbers));
        return parsedNumbers;
    }

    private String[] cleanOperators(String[] operators) {
        int countNonEmptyOperators = 0;
        for (String operator : operators) {
            if (!operator.equals("")) {
                countNonEmptyOperators++;
            }
        }
        String[] cleanedOperators = new String[countNonEmptyOperators];
        int i = 0;
        for (String operator : operators) {
            if (!operator.equals("")) {
                if (operator.matches("\\++")) {
                    operator = "+";
                } else if (operator.matches("-+")) {
                    if (operator.length() % 2 == 0) {
                        operator = "+";
                    } else {
                        operator = "-";
                    }
                }

                cleanedOperators[i] = operator;
                i++;
            }
        }
        return cleanedOperators;
    }

    private int feedCalculator(int[] numbers, String[] operators) {
        int result = numbers.length == 1 ? numbers[0] : calculate(numbers[0], numbers[1], operators[0]);
        for (int i = 1; i < numbers.length - 1; i++) {
            result = calculate(result, numbers[i + 1], operators[i]);
        }
        return result;
    }

    public int calculate(int numberOne, int numberTwo, String operator) {
        int result = 0;
        switch (operator) {
            case ("+") -> result = numberOne + numberTwo;
            case ("-") -> result = numberOne - numberTwo;
            case ("*") -> result = numberOne * numberTwo;
            case ("/") -> result = numberOne / numberTwo;
        }
        return result;
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
