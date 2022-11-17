package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorAdvancedUsingBinaryTreeTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream outputStreamPrinter = new PrintStream(outputStream);
//    private final CalculatorAdvancedUsingBinaryTree calculator = new CalculatorAdvancedUsingBinaryTree();

    @AfterEach
    void clearBuffer() {
        outputStream.reset();//clear contents in the buffer
    }

    @ParameterizedTest
    @MethodSource("provideHappyNumbers")
    void calculateWithValidInput(String input, String expectedResult) {
        CalculatorAdvancedUsingBinaryTree calculator = new CalculatorAdvancedUsingBinaryTree(new ByteArrayInputStream(input.getBytes()), outputStreamPrinter);
        calculator.start();
        String expectedString = "Result: " + expectedResult;
        boolean containsString = outputStream.toString().trim().contains(expectedString);
        assertTrue(containsString, "\nExpected String: " + expectedString + "\nActual String: " + outputStream.toString().trim());
    }

    private static Stream<Arguments> provideHappyNumbers() {
        return Stream.of(
                Arguments.of("1", "1"),
                Arguments.of("345.18", "345.18"),
                Arguments.of("345 + 23 +4+ 3", "375.0"),
                Arguments.of("345 - 23 -4- 3", "315.0"),
                Arguments.of("5.78 + 2.1345 - 1.1234", "6.7911"),
                Arguments.of("5 + 3 * 3 - 6 / 2", "11.0"),
                Arguments.of("2.0+3.32 * 4.5 / 2.1222 * 5 *3 /10", "12.559796437659035"),
                Arguments.of("5 -- 3-2.0---4", "2"),
                Arguments.of("5 +- 3-2.0+-+-4", "4"),
                Arguments.of("5 * -3", "-15"),
                Arguments.of("5 / - 2", "-2.5"),
                Arguments.of("12.5 / - 2", "-6.25"),
                Arguments.of("-2+6", "4"),
                Arguments.of("(5)", "5"),
                Arguments.of("(-5)", "-5"),
                Arguments.of("2 * (3-1)", "4.0"),
                Arguments.of("2(3-1)", "4"),
                Arguments.of("(3-1)*2", "4"),
                Arguments.of("15 - ((3-1) * 3)", "9.0"),
                Arguments.of("15 - 2((3-1) * 3)", "3.0")
        );

    }

    @ParameterizedTest
    @MethodSource("provideUnhappyInput")
    void calculateWithInvalidInput(String input, String expectedResult) {
        CalculatorAdvancedUsingBinaryTree calculator = new CalculatorAdvancedUsingBinaryTree(new ByteArrayInputStream(input.getBytes()), outputStreamPrinter);
        calculator.start();
        boolean containsString = outputStream.toString().contains(expectedResult);
        assertTrue(containsString, "\nExpected String: " + expectedResult + "\nActual String: " + outputStream.toString().trim());
    }

    private static Stream<Arguments> provideUnhappyInput() {
        String defaultRetryMessage = "Please enter a valid and correctly formatted mathematical expression.";
        return Stream.of(
                Arguments.of("", "No line found"),
                Arguments.of(" ", "Try again. " + defaultRetryMessage),
                Arguments.of("\n", "Try again. " + defaultRetryMessage),
                Arguments.of("not valid input", "Cannot parse 'n' into a valid token. " + defaultRetryMessage),
                Arguments.of("45r3", "Cannot parse 'r' into a valid token. " + defaultRetryMessage),
                Arguments.of("34.5*/2", "Syntax Error. Invalid usage of operators '*' and '/'. " + defaultRetryMessage),
                Arguments.of("34.5-/2", "Syntax Error. Invalid usage of operators '*' and '/'. " + defaultRetryMessage),
                Arguments.of("34.5+*2", "Syntax Error. Invalid usage of operators '*' and '/'. " + defaultRetryMessage)
        );
    }


}