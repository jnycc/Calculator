package parser;

import lexer.Token;

import java.util.List;

public class Parser {
    /* Input: 1 + 2 - 3 + 4 - 5 + 6 - 7

             +
           /   \
          /     +
         /    /    \
        1   -       +
           / \     /  \
          2   3   -    6
                 /
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
    private final List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Node parse() {
        Node lastNode = null;
        // Tree structure: each node consists of 3 elements: previous node-object(first one is the leftNumber, rest are operatorNodes), operator, rightNumber.
        // This creates an unbalanced tree: only the operatorNode has childNodes(which are numberNodes).
        // Thus, position will start at every operator: every 2nd element thus start at token[1] and increment by 2.
        for (int i = 1; i < tokens.size(); i+=2) {
            if (lastNode == null) { //first left leaf node
                lastNode = parseNumber(tokens.get(0));
            }
            Node rightNode = parseNumber(tokens.get(i+1));
            lastNode = parseExpression(lastNode, tokens.get(i), rightNode);

//            if (lastNode == null) { //get first leaf (most left in tree)
//                lastNode = parseNumber(token);
//            } else {
//                lastNode = parseExpression(lastNode, token);
//            }
        }
        return lastNode; //Hoe computen met lastNode?asdsa
    }

    // Nodes are connected via the operatorNodes: only operatorNodes have childNodes(which are numberNodes). See visual example at the top.
    // Thus Numbers are always leaf nodes (children are null)
    private Node parseNumber(final Token token) {
        return Node.createLeaf(token);
    }

    private Node parseExpression(final Node leftChild, final Token operator, final Node rightChild) {
        return Node.createNode(leftChild, operator, rightChild);
    }
}
