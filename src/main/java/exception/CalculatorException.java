package exception;

public class CalculatorException extends RuntimeException {

    public CalculatorException(final String message) {
        super(message);
    }

    public CalculatorException(final String message, Throwable cause) {
        super(message, cause);
    }
}
