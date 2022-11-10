package parser;

import lexer.Token;
import lexer.OperatorToken;

import java.util.List;

public class Parser {
    /* Input: 1 + 2 - 3 + 4 - 5 + 6 - 7
    Creates this tree:
                              -
                            /   \
                  ->     {+}      7
                        /   \
                      -     {6}         <- If it was 5*6 instead of 5+6, these would change, see below tree.
                    /   \               <-
                  +     {5}             <- als je * tegenkomt, moet deze rechterNode vervangen worden met een fullNode waarin 5*6 zit.
                /   \
              -      4
            /   \
           +     3
         /   \
        1     2

    Input with priority for multiplication: 1 + 2 - 3 + 4 - (5 * 6) - 7
                         -
                       /   \
                      -     7
                    /    \
                  +       {*}  (previously: 5)
                /   \     /  \
              -      4  {5}   {6}
            /   \
           +     3
         /   \
        1     2


Zodra je bij het parsen van de string * tegenkomt, heb je reeds de reguliere node aangemaakt (leftChild=previousNode, operator (-), rightChild=leafNode(5)).
1) De rightChild van deze previousNode vervangen met nieuwe node: leftChild=previousNode.rightChild, operator (*), rightChild= nextNumber (6).

    Other tree which we don't use (input: 1 + 2 - 3 + 4 - 5 + 6 - 7).
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
        // Tree structure: each node consists of 3 elements: previous node-object(first one is the leftNumber, rest are operatorNodes), operator, rightNumber.
        // This creates an unbalanced tree: only the operatorNode has childNodes(which are numberNodes).
        // Thus, position will start at every operator: every 2nd element thus start at token[1] and increment by 2.
        Node lastNode = null;
        for (int i = 1; i < tokens.size(); i+=2) {
            if (lastNode == null) { //first left leaf node which will be the leftChild
                lastNode = parseNumber(tokens.get(0));
            }
            Node rightNode = parseNumber(tokens.get(i+1));
            lastNode = parseExpression(lastNode, (OperatorToken) tokens.get(i), rightNode);
        }
        return lastNode;
    }

    // Nodes are connected via the operatorNodes: only operatorNodes have childNodes(which are numberNodes). See visual example at the top.
    // Thus Numbers are always leaf nodes (children are null)
    private Node parseNumber(final Token token) {
        return Node.createLeaf(token);
    }

    private Node parseExpression(final Node lastNode, final OperatorToken operator, final Node rightChild) {
        if (lastNode.leftChild != null && lastNode.hasLowerPriorityThan(operator)) {
            return lastNode.replaceRightChild(lastNode, operator, rightChild);
        }
        return Node.createNode(lastNode, operator, rightChild);
    }
}
