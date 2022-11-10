package lexer;

public class OperatorToken extends Token {
    private final Operator operator;


    public OperatorToken(String input, int index) {
        super(Type.OPERATOR, index);
        this.operator = Operator.of(input, index);
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return operator.getOperation().toString();
    }
}