package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    //Setup for using and reading from a standardOutput
    private final PrintStream originalStandardOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private Calculator calculator;

    @BeforeEach
    void setupStreams() {
        System.setOut(new PrintStream(outContent));
        calculator = new Calculator();
    }

    @ParameterizedTest
    @MethodSource("provideNumbers")
    void calculateSumTest(int numberOne, int numberTwo, int expected) {
        int actual = calculator.calculateSum(numberOne, numberTwo);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideNumbers() {
        return Stream.of(
                Arguments.of(20, 30, 50),
                Arguments.of(-50, 50, 0),
                Arguments.of(-25, -75, -100)
        );
    }

/*    @Test
    void calculateMultiSumTest() {
        Calculator calculator = new Calculator();
        List<Integer> numbers = new ArrayList<>(List.of(4, 5, 6));
        int expected = 4 + 5 + 6;
        int actual = calculator.calculate(numbers);
        assertEquals(expected, actual);
    }*/

    @Test
    void dummyTest() {
        System.out.println("test");
        assertEquals("test", outContent.toString().trim());
    }

    @Test
    void calculateOutputTest() {
//        System.setIn(new ByteArrayInputStream("lala".getBytes()));
        calculator.startWithScanner();
        String expectedString = "The sum of these numbers is: ";
        boolean containsString = outContent.toString().trim().contains(expectedString);
        assertTrue(containsString);

    }

    @Test
    void calculateInvalidInputTest() {

    }
}