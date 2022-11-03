package controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    public Calculator() {
    }

    public void startWithScanner() {
        System.out.print("Welcome to TI-001 Calculator! Let us do the math for you and sum two whole numbers.\n\n");
        final Scanner scanner = new Scanner(System.in);
        final List<Integer> numbers = new ArrayList<>();

        int i = 1;
        while(i <= 2) {
            try {
                System.out.printf("Enter number #%d: ", i);
                numbers.add(scanner.nextInt());
                i++;
            } catch (InputMismatchException e) {
//                e.printStackTrace();
                System.out.println("Invalid number, only whole numbers are accepted in this version (e.g. 10). " +
                        "Please enter a valid and correctly formatted number.");
                scanner.nextLine();
            }
        }
//        scanner.close();
        System.out.println("\nYou entered: " + numbers);
        final int result = calculateSum(numbers.get(0), numbers.get(1));
        System.out.println("The sum of these numbers is: " + result);
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
        System.out.println("This one uses Console.");
        System.out.print("Welcome to TI-001 Calculator! Let us do the math for you and sum two whole numbers.\n\n");
        Console console = System.console();
//        String text = console.readLine();
//        System.out.println(text);

        final List<Integer> numbers = new ArrayList<>();
        int i = 1;
        while(i <= 2) {
            try {
                System.out.printf("Enter number #%d: ", i);
                int nr = Integer.parseInt(console.readLine());
                numbers.add(nr);
                i++;
            } catch (NumberFormatException e) {
//                e.printStackTrace();
                System.out.println("Invalid number, only whole numbers are accepted in this version (e.g. 10). " +
                        "Please enter a valid and correctly formatted number.");
            }
        }
        System.out.println("\nYou entered: " + numbers);
        final int result = numbers.get(0) + numbers.get(1);
        System.out.println("The sum of these numbers is: " + result);
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
