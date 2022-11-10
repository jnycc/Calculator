package lexer;

import exception.CalculatorException;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    /**
     * Transform the received input into a list of tokens which can be parsed further.
     */
    public List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Token.Type lastParsedType = null;


        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            char character = characters[i];
            if (Character.isWhitespace(characters[i])) {
//                tokens.add(Token.of(null, "", i));//waarom blanko element toevoegen?
                continue;
            }

            Token.Type characterType = typeOf(character);
            // Expected order of tokens is always number, operator, number (123+4*5).
            // Within a numberToken (e.g. 123), its chars are of the same Type. If it's a different type, it is the next token, then we add the previously built token to the list.
            if (characterType != lastParsedType && lastParsedType != null) { //Also null-check because reading the first element/digit is a Number, which != null, which should not be added as a token already.
//                tokens.add(new Token(lastParsedType, sb.toString(), i));
                tokens.add(Token.of(lastParsedType, sb.toString(), i));
                sb.delete(0, sb.length()); // reset the stringBuilder

            }
            sb.append(character);
            lastParsedType = characterType;
        }
        // Add last token remaining in the stringBuilder
        if (lastParsedType != null && !sb.isEmpty()) {
            tokens.add(Token.of(lastParsedType, sb.toString(), characters.length));
//            tokens.add(new Token(lastParsedType, sb.toString(), characters.length));
        }
        return tokens;
    }

    private Token.Type typeOf(final char character) {
        if (Character.isDigit(character) || character == '.') {
            return Token.Type.NUMBER;
        }
        if (Operator.isOperator(character)) {
            return Token.Type.OPERATOR;
        }
        throw new CalculatorException("Cannot parse '%s' into a valid token.".formatted(character));
    }
}
