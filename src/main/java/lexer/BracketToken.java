package lexer;

public class BracketToken extends Token {

    private boolean isOpen;

    public BracketToken(String input, int index) {
        super(Type.BRACKET, index);
        this.isOpen = input.equals("(");
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String toString() {
        return isOpen ? "true": "false";
    }
}
