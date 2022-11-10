package lexer;

import exception.CalculatorException;

public class NumberToken extends Token {
    private final double number;

    public NumberToken(String input, int index) {
        super(Type.NUMBER, index);
        try {
            this.number = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new CalculatorException(e.getMessage());
        }
    }

    public double getNumber() {
        return number;
    }
}