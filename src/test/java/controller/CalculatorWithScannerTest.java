package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class CalculatorSimpleTest {

    //Setup for using and reading from a standardOutput
//    private final PrintStream originalStandardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //buffer containing the user input
    private final PrintStream outputStreamPrinter = new PrintStream(outputStream);//equals System.out, it is able to print the buffer
    private final CalculatorSimple calculatorSimple = new CalculatorSimple();

/*    @BeforeEach
    void setupStreams() {
//        System.setOut(new PrintStream(outputStream));
        calculator = new Calculator();
    }*/

    @AfterEach
    void clearBuffer() {
        outputStream.reset();//clear contents in the buffer
    }

/*    @ParameterizedTest
    @MethodSource("provideNumbers")
    void calculateSumTest(int numberOne, int numberTwo, int expected) {
        int actual = calculator.calculateSum(numberOne, numberTwo);
        assertEquals(expected, actual);
    }*/

/*    @Test
    void calculateMultiSumTest() {
        Calculator calculator = new Calculator();
        List<Integer> numbers = new ArrayList<>(List.of(4, 5, 6));
        int expected = 4 + 5 + 6;
        int actual = calculator.calculate(numbers);
        assertEquals(expected, actual);
    }*/

/*    @Test
    void dummyTest() {
        System.out.println("test");
        assertEquals("test", outputStream.toString().trim());
    }*/

    @ParameterizedTest
    @MethodSource("provideNumbers")
    void calculateUsingScanner(int numberOne, int numberTwo) {
        String input = String.format("%d\n%d", numberOne, numberTwo);
        calculatorSimple.startWithScanner(new ByteArrayInputStream(input.getBytes()), outputStreamPrinter);
        String expectedString = "The sum of these numbers is: " + (numberOne + numberTwo);
        boolean containsString = outputStream.toString().trim().contains(expectedString);
        assertTrue(containsString, "Actual string: " + outputStream.toString().trim() + "\nExpected String: " + expectedString);
    }

    private static Stream<Arguments> provideNumbers() {
        return Stream.of(
                Arguments.of(20, 30, 50),
                Arguments.of(-50, 50, 0),
                Arguments.of(34, -12, 22),
                Arguments.of(-25, -75, -100)
        );
    }

    @ParameterizedTest
    @MethodSource("provideNumbers")
    void calculateUsingConsole(int numberOne, int numberTwo) {
        Console console = mock(Console.class);
        doReturn(String.valueOf(numberOne)).doReturn(String.valueOf(numberTwo)).when(console).readLine();
        calculatorSimple.startWithConsole(console, new PrintStream(outputStream));
        String expectedString = "The sum of these numbers is: " + (numberOne + numberTwo);
        boolean containsString = outputStream.toString().trim().contains(expectedString);
        assertTrue(containsString, "Actual string: " + outputStream.toString().trim() + "\nExpected String: " + expectedString);
    }

    @Test
    void invalidInputTest() {
        String input = "invalid input";
        calculatorSimple.startWithScanner(new ByteArrayInputStream(input.getBytes()), outputStreamPrinter);
        String expectedString = "Invalid number, only whole numbers are accepted in this version (e.g. 10). " +
                "Please enter a valid and correctly formatted number.";
        boolean containsString = outputStream.toString().trim().contains(expectedString);
        assertTrue(containsString, "Actual string: " + outputStream.toString().trim() + "\nExpected String: " + expectedString);
    }
}