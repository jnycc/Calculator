package parser;

import exception.CalculatorException;
import lexer.Operator;
import lexer.Token;
import lexer.OperatorToken;
import lexer.NumberToken;


public class Node {

    public Token token;
    public Node leftChild;
    public Node rightChild;
    public boolean isBracketed;

    public Node(Node leftChild, Token token, Node rightChild, boolean isBracketed) {
        this.leftChild = leftChild;
        this.token = token;
        this.rightChild = rightChild;
        this.isBracketed = isBracketed;
    }

    public static Node createLeaf(final Token token) {
        if (token.getType() != Token.Type.NUMBER) {
            throw new CalculatorException("Can only create leaf-nodes with numbers as value. Received '" + token + "'.");
        }
        return new Node(null, token, null, false);
    }

    public static Node createNode(final Node leftChild, final Token token, final Node rightChild, final boolean isBracketed) {
        if (token.getType() != Token.Type.OPERATOR) {
            throw new CalculatorException("Can only create regular nodes with operators as value.");
        }
        return new Node(leftChild, token, rightChild, isBracketed);
    }

    public Node replaceRightChild(Node lastNode, OperatorToken operator, Node rightGrandChild) {
        lastNode.rightChild = createNode(lastNode.rightChild, operator, rightGrandChild, false);
        return lastNode;
    }

    public double compute() {
//        System.out.printf("Current token at index [%d]: %s%n", token.getIndex(), token.getValue());
        if (leftChild == null) { // A node consisting of leftChild, token, rightChild should always have a leftChild, unless it is a leaf (a number without children).
            return ((NumberToken) token).getNumber();
        }
        double leftValue = leftChild.compute();
        double rightValue = rightChild.compute();
        Operator operation = (((OperatorToken) token).getOperator());
        return operation.applyAsDouble(leftValue, rightValue);
    }

/* Older version of compute method using only one generic Token class.
    public double compute() {
        System.out.printf("Current token at index [%d]: %s%n", token.getIndex(), token.getValue());
        if (leftChild == null) { // A node consisting of leftChild, token, rightChild should always have a leftChild, unless it is a leaf (a number without children).
            return Double.parseDouble(token.getValue());
        }
        Double leftValue = leftChild.compute();
        Double rightValue = rightChild.compute();
        return switch (token.getValue()) {
            case ("+") -> leftValue + rightValue;
            case ("-") -> leftValue - rightValue;
            case ("*") -> leftValue * rightValue;
            case ("/") -> leftValue / rightValue;
            default -> throw new InputMismatchException("Unknown operator: " + token.getValue());
        };
    }*/

    public boolean hasLowerPriorityThan(OperatorToken thatToken) {
        int thisOperatorPriority = ((OperatorToken) token).getOperator().getPriority();//this is the lastNode
        int thatOperatorPriority = thatToken.getOperator().getPriority(); //receives the nextNode from the Parser
        return thisOperatorPriority - thatOperatorPriority < 0;
        }

    public Token getToken() {
        return token;
    }

    /**
     * @return a copy of the same node but with the isBracketed value to true.
     */
    public Node makeBracketed() {
        return new Node(leftChild, token, rightChild, true);
    }
}
