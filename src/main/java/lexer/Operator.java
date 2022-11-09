package lexer;

import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class Operator {

    public final String value;
    private final int priority;
    private final DoubleBinaryOperator operation;

    public static final List<Operator> OPERATORS = List.of(
            new Operator("+", 1, Double::sum),
            new Operator("-", 1, (a, b) -> a -b),
            new Operator("*", 2, (a ,b) -> a * b),
            new Operator("/", 2, (a ,b) -> a / b)
    );

    public Operator(String value, int priority, DoubleBinaryOperator operation) {
        this.value = value;
        this.priority = priority;
        this.operation = operation;
    }

    public static boolean isOperator(final char character) {
//        return OPERATORS.stream().anyMatch(operator -> operator.value.equals(String.valueOf(character)));
        for (Operator operator : OPERATORS) {
            if (operator.value.equals(String.valueOf(character))) {
                return true;
            }
        }
        return false;
    }
}
