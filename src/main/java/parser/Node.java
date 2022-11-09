package parser;

import lexer.Token;

import java.util.InputMismatchException;

public class Node {

    /* Input: 1 + 2 - 3 + 4 - 5 + 6 - 7

             +
           /   \
          /     +
         /    /    \
        1   -       +
           / \     /  \
          2   3   -    6
                 /  \
                4    5

             +
           /   \
          /     +
         /    /    \
        1   -        -
           / \      /  \
          2   3    +     7
                 /   \
                -      6
               /  \
              4    5

     */

    public Token token;
    public Node leftChild;
    public Node rightChild;

    public Node(Node leftChild, Token token, Node rightChild) {
        this.leftChild = leftChild;
        this.token = token;
        this.rightChild = rightChild;
    }

    public static Node createLeaf(final Token token) {
        if (token.getType() != Token.Type.NUMBER) {
            throw new RuntimeException("Can only create leaf-nodes with numbers as value.");
        }
        return new Node(null, token, null);
    }

    public static Node createNode(final Node leftChild, final Token token, final Node rightChild) {
//        if (token.getType() != Token.Type.NUMBER) {
//            throw new Exception("Can only create regular nodes with operators as value.");
//        }
        return new Node(leftChild, token, rightChild);
    }

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
    }
}
