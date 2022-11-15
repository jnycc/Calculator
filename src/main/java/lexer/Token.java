package lexer;

public class Token {
    public enum Type {
        NUMBER,
        OPERATOR,
        BRACKET
    }

    private final Type type;
    private final int index;

    protected Token(Type type, int index) {
        this.type = type;
        this.index = index;
    }

    public static Token of(final Type type, final String input, final int index) {
        return switch (type) {
            case NUMBER -> new NumberToken(input, index);
            case OPERATOR -> new OperatorToken(input, index);
            case BRACKET -> new BracketToken(input, index);
        };
    }

    public Type getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

}
