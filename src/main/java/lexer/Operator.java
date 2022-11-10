package lexer;

import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class Operator implements DoubleBinaryOperator{

    public final String value;
    private final int priority;
    private final DoubleBinaryOperator operation;

    public static final List<Operator> OPERATORS = List.of(
            new Operator("+", 1, Double::sum), //last param is the implementation of method applyAsDouble in functional interface DoubleBinaryOperator
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

    public static Operator of(String input, int index) {
        Operator result = null;
        for (Operator operator: OPERATORS) {
            if (operator.value.equals(input)) {
                result = operator;
            }
        }
        return result;
    }

    @Override
//note that we implement this method using the already anonymously implemented method within variable operation (see lambdas in List<Operator>).
    public double applyAsDouble(double left, double right) {
        return operation.applyAsDouble(left, right);
    }

    public int getPriority() {
        return priority;
    }

    public DoubleBinaryOperator getOperation() {
        return operation;
    }
}
