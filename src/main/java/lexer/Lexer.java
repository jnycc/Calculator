package lexer;

import exception.CalculatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lexer {

    /**
     * Transforms the received input into a list of tokens (numbers, operators, brackets) which can be parsed further.
     * @param input the mathematical calculation as entered by the user.
     * @return list of tokens (numbers, operators, brackets).
     */
    public List<Token> lex(String input) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Token.Type lastParsedType = null;

        List<Character> characters = cleanOperators(input);
        for (int i = 0; i < characters.size(); i++) {
            char character = characters.get(i);

            Token.Type characterType = typeOf(character);

            // If - or + is the first character (e.g. -1+2) OR
            // If - or + follows an open bracket e.g. (-1) OR
            // If *- or /- (e.g. 5*-5), then the - is part of a number and not an operator.
            if (characterType == Token.Type.OPERATOR &&
                    (lastParsedType == null || characters.get(i-1) == '(' || characterType == lastParsedType)) {
                characterType = Token.Type.NUMBER;
            }
            // Expected order of tokens is always number, operator, number (123+4*5).
            // Within a numberToken (e.g. 123), its chars are of the same Type. If it's a different type, it is the next token, then we add the previously built token to the list.
            if ((characterType == Token.Type.BRACKET || characterType != lastParsedType) && lastParsedType != null) { //Also null-check because reading the first element/digit is a Number, which != null, which should not be added as a token already.
                tokens.add(Token.of(lastParsedType, sb.toString(), i));
                sb.delete(0, sb.length()); // reset the stringBuilder
                if (character == '(' && lastParsedType == Token.Type.NUMBER) { // If 5(1), then it implies multiplication, so add * in between.
                    tokens.add(Token.of(Token.Type.OPERATOR, "*", i));
                }
            }
            sb.append(character);
//            System.out.println("StringBuilder content: " + sb);
            lastParsedType = characterType;
        }

        if (lastParsedType == null) {
            throw new CalculatorException("Try again.");
        }
        if (lastParsedType == Token.Type.OPERATOR) {
            throw new CalculatorException("Syntax error. Statement is unfinished as it may not end with an operator.");
        }

        // Add last token remaining in the stringBuilder
        if (!sb.isEmpty()) {
            tokens.add(Token.of(lastParsedType, sb.toString(), characters.size()));
        }
        System.out.println("Lexed characters: " + tokens);
        return tokens;
    }

    private Token.Type typeOf(final char character) {
        if (Character.isDigit(character) || character == '.') {
            return Token.Type.NUMBER;
        }
        if (Operator.isOperator(character)) {
            return Token.Type.OPERATOR;
        }
        if (character == '(' || character == ')') {
            return Token.Type.BRACKET;
        }
        throw new CalculatorException("Cannot parse '%s' into a valid token.".formatted(character));
    }

    /**
     * Creates a new List of Characters which excludes whitespaces and simplifies operators into one character (or two if the second is - for a negative number).
     * @param input: the mathematical calculation as entered by the user.
     * @return List of Characters of the mathematical calculation with simplified operators, excluding whitespaces.
     */
    private List<Character> cleanOperators(String input) {
        List<Character> characters = input.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList());
//        System.out.println("Converted characters:" + character);

        List<Character> cleanedCharacters = new ArrayList<>();
        Character character;
        Character lastAddedCharacter;
        for (int i = 0; i < characters.size(); i++) {
            character = characters.get(i);
            if (i == 0) {
                cleanedCharacters.add(character);
                continue;
            }
            if (!Character.isWhitespace(character)) {
                lastAddedCharacter = cleanedCharacters.get(cleanedCharacters.size() - 1);
                if (character.equals('+')) {
                    if (!lastAddedCharacter.equals('+') && !lastAddedCharacter.equals('-')) { // Don't add + if ++ or -+.
                        cleanedCharacters.add(character);
                    }
                } else if (character.equals('-')) { // If -- replace previousChar with +. If +- replace previousChar with -.
                    switch (lastAddedCharacter) {
                        case ('-') -> cleanedCharacters.set(cleanedCharacters.size() - 1, '+');
                        case ('+') -> cleanedCharacters.set(cleanedCharacters.size() - 1, '-');
                        default -> cleanedCharacters.add(character);
                    }
                } else if ((character.equals('*') || character.equals('/')) && !Character.isDigit(lastAddedCharacter) && lastAddedCharacter != ')') {
                    throw new CalculatorException("Syntax Error. Operators '*' and '/' can not be preceded by anything else other than a number.");
                } else {
                    cleanedCharacters.add(character);
                }
            }
        }
//        System.out.println("Cleaned characters: " + cleanedCharacters);
        return cleanedCharacters;
    }
}
