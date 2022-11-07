package controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorIntermediateTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream outputStreamPrinter = new PrintStream(outputStream);
    private final CalculatorIntermediate calculator = new CalculatorIntermediate();

    @ParameterizedTest
    @MethodSource("provideNumbers")
    void start(String input, int expectedResult) {
        calculator.start(new ByteArrayInputStream(input.getBytes()), outputStreamPrinter);
        String expectedString = "Result: " + expectedResult;
        boolean containsString = outputStream.toString().trim().contains(expectedString);
        assertTrue(containsString, "\nExpected String: " + expectedString + "\nActual String: " + outputStream.toString().trim());
    }

    private static Stream<Arguments> provideNumbers() {
        return Stream.of(Arguments.of("5 + 2 - 1", 6),
                Arguments.of("-2+3 * 4 / 2 * 5 *3 /10", 3),
                Arguments.of("5 -- 3", 8));
    }
}