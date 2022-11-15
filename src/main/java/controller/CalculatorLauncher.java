package controller;

import exception.CalculatorException;

public class CalculatorLauncher {

    public static void main(String[] args) {
//        new Calculator().startWithScanner();
//        new Calculator().startWithConsole();
//        new CalculatorIntermediate().start();
//        new CalculatorAdvanced().start();

        CalculatorAdvancedUsingBinaryTree calculator = new CalculatorAdvancedUsingBinaryTree(System.in, System.out);
        System.out.print("Welcome to Texas Instruments-003 Advanced Calculator! Let us do the math for you.\n\n");
        while (true) {
            calculator.start();
        }
//        new Experiments().startExperiment();


    }
}
