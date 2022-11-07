package controller;

import java.io.Console;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculatorSimple {

    public CalculatorSimple() {
    }

    public void startWithScanner () {
        startWithScanner(System.in, System.out);//pass the whole input and outputStream in which the user input will be given.
    }

    void startWithScanner(InputStream inputStream, PrintStream outputStream) {
        outputStream.print("Welcome to TI-001 Calculator! Let us do the math for you and sum two whole numbers.\n\n");
        final Scanner scanner = new Scanner(inputStream);
        final List<Integer> numbers = new ArrayList<>();

        int i = 1;
        while(i <= 2) {
            try {
                outputStream.printf("Enter number #%d: ", i);
                numbers.add(scanner.nextInt());
                i++;
            } catch (Exception e) {
//                e.printStackTrace();
                outputStream.println("Invalid number, only whole numbers are accepted in this version (e.g. 10). " +
                        "Please enter a valid and correctly formatted number.");
                scanner.nextLine();
            }
        }
//        scanner.close();
        outputStream.println("\nYou entered: " + numbers);
        final int result = numbers.get(0) + numbers.get(1);
        outputStream.println("The sum of these numbers is: " + result);
    }

/*    public void startWithScannerMulti() {
        System.out.print("Welcome to TI-001 Calculator!\n\nEnter your first number: ");
        final Scanner scanner = new Scanner(System.in);
        final List<Integer> numbers = new ArrayList<>();
        numbers.add(scanner.nextInt());

        boolean moreNumbers = true;
        while (moreNumbers) {
            System.out.print("Would you like to add another number? (Y/N): ");
            final String continueResponse = scanner.next();
            if (continueResponse.equalsIgnoreCase("Y")) {
                System.out.print("Enter your next number: ");
                numbers.add(scanner.nextInt());
            } else if (continueResponse.equalsIgnoreCase("N")) {
                moreNumbers = false;
            } else {
                System.out.println("Please only enter 'Y' or 'N'!");
            }

        }
        System.out.println("\nYou entered: " + numbers);
        final int result = calculateSum(numbers);
        System.out.println("The sum of these numbers is: " + result);
    }*/

    public void startWithConsole() {
        Console console = System.console();
        startWithConsole(console, System.out);
    }

    void startWithConsole(Console console, PrintStream printStream) {
        printStream.println("This one uses Console.");
        printStream.print("Welcome to TI-001 Calculator! Let us do the math for you and sum two whole numbers.\n\n");
//        String text = console.readLine();
//        System.out.println(text);

        final List<Integer> numbers = new ArrayList<>();
        int i = 1;
        while(i <= 2) {
            try {
                printStream.printf("Enter number #%d: ", i);
                int nr = Integer.parseInt(console.readLine());
                numbers.add(nr);
                i++;
            } catch (NumberFormatException e) {
//                e.printStackTrace();
                printStream.println("Invalid number, only whole numbers are accepted in this version (e.g. 10). " +
                        "Please enter a valid and correctly formatted number.");
            }
        }
        printStream.println("\nYou entered: " + numbers);
        final int result = numbers.get(0) + numbers.get(1);
        printStream.println("The sum of these numbers is: " + result);
    }

    public int calculateSum(int numberOne, int numberTwo) {
        return numberOne + numberTwo;
    }

/*    public int calculateMultiSum(List<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
//        sum = numbers.stream().reduce(0, Integer::sum);
        return sum;
    }*/
}
