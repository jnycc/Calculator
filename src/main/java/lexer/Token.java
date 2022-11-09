package lexer;

public class Token {
    public enum Type {
        NUMBER,
        OPERATOR
    }

    private final Type type;
    private String value;
    private final int index;

    protected Token(Type type, String input, int index) {
        this.type = type;
        this.index = index;
        this.value = switch (type) {
            case NUMBER -> String.valueOf(Double.parseDouble(input));
            case OPERATOR -> input;
        };
    }

    public Type getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public static Token of(final Type type, final String input, final int index) {
//        switch (type) {
//            case NUMBER -> return 5;
//        }
        return null;
    }


/*    public class NumberToken extends Token {
        private double value;

        public NumberToken(String input, int index) {
            super(Type.NUMBER, index);
            try {
                this.value = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        public double getValue() {
            return value;
        }
    }

    public class operatorToken extends Token {
        private final Operator operator;


        public operatorToken(String input, int index) {
            super(Type.OPERATOR, index);
            this.operator = Operator.of(input, index);
        }
    }*/

}
