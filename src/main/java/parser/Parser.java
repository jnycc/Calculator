package parser;

import exception.CalculatorException;
import lexer.BracketToken;
import lexer.Token;
import lexer.OperatorToken;

import java.util.List;

public class Parser {
    /* Input with addition and subtraction only: 1 + 2 - 3 + 4 - 5 + 6 - 7
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

    Input with priority for multiplication: 1 + 2 - 3 + 4 - 5 * 6 - 7
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
=> De rightChild van deze previousNode vervangen met nieuwe node: leftChild=previousNode.rightChild, operator (*), rightChild= nextNumber (6).


    Input with priority for multiplication(or division): 1 + 2 * 3 - 4.
    1. Create node(1+2)
    2. * priority> +    => replace rightChild(2) of lastNode(1+2) with node(2*3)
    3. We are still at the position of lastNode(1+x). Create node(lastNode-4) on top of it.`
              -
            /   \
           +     4
         /   \
        1     *
             /  \
            2    3

    Input with priority for brackets: 1 + 2 * (3 - 4 ÷ 5) - 6.
    In order:
    1a. * priority> +        => rightChild(2) of node(1+2) replaced with a new node. Normally node(2+3), but it comes across a bracket thus,
    1a. ( highest priority   => create a new subtree within this rightChild(2) of node(1+2). Create a new parser with the substring(3 - 4 ÷ 5), create the tree and return the firstNode of this subtree.
        1b. ÷ priority> -         => rightChild(4) of node(3-4) replaced with node(4÷5)
    2. - same priority +     => we put a subtree in the rightChild of the lastNode of the main tree. Thus lastNode is still (1+x). - same priority as +, create a new node above it.
              -
            /   \
           +     6
         /   \
        1     *
             /  \
            2    -          <- subtree: the node from 3-x and under
                /  \
               3    ÷
                   /  \
                  4    5


    Other possible tree, however not what is created in this app. Retained for learning purposes. (input: 1 + 2 - 3 + 4 - 5 + 6 - 7).
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
    private Node firstExpressionNode; // this is needed to link the main tree with a new subtree via the subtree's firstNode, which will be rightChild in the main tree.

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Node parse() {
        // Tree structure: each node consists of 3 elements: previous node-object(first one is the leftNumber, rest are operatorNodes), operator, rightNumber.
        // This creates an unbalanced tree: only the operatorNode has childNodes(which are numberNodes).
        // Thus, position will start at every operator: every 2nd element thus start at token[1] and increment by 2.
        if (tokens.size() <3) {
            return Node.createLeaf(tokens.get(0));
        }
        Node lastNode = null;
        for (int i = 1; i < tokens.size(); i += 2) {
            if (lastNode == null && tokens.get(i-1).getType() == Token.Type.NUMBER) { //first left leaf node which will be the leftChild
                lastNode = parseNumber(tokens.get(0));
            }
            Node rightNode;
            // If first node is a bracket e.g. (3-1), for the subset of tokens within the brackets, create a tree, set its firstNode as lastNode of the main tree.
            if (tokens.get(i-1).getType() == Token.Type.BRACKET && ((BracketToken) tokens.get(i-1)).isOpen()) {
                List<Token> sublistOfTokens = sublistOfTokens(tokens.subList(i - 1, tokens.size()));
                lastNode = createSubTree(sublistOfTokens);
                i += sublistOfTokens.size() -1;
            }
            //  15 - 2((3-1) * 3)
            // If 2nd node is a bracket and the first node is -, e.g. -(5+2), make the tree: -1 * subtree(5-2).
            // If -2(5+3), create a sublist of tokens which is -2*(5+3) and parse it by using the other bracket situation, into a subtree.
            else if (tokens.get(i).getType() == Token.Type.BRACKET) {
                List<Token> sublistOfTokens = sublistOfTokens(tokens.subList(i, tokens.size()));
                sublistOfTokens = addMultiplierToBracketedTokens(tokens.get(i-1), sublistOfTokens);
                lastNode = new Parser(sublistOfTokens).parse();
                i += sublistOfTokens.size();
            }
            // If brackets are met, a prioritized subcalculation has to be done first - create a subtree for this and link the main tree to the subtree's first node.
            else if (tokens.get(i + 1).getType() == Token.Type.BRACKET) { //TODO: bracket can be first (3-1), second -(5) and third token 2*(3-1).
                List<Token> sublistOfTokens = sublistOfTokens(tokens.subList(i + 1, tokens.size()));
                rightNode = createSubTree(sublistOfTokens);
                lastNode = parseExpression(lastNode, (OperatorToken) tokens.get(i), rightNode);
                i += 1 + sublistOfTokens.size();
            } else {
                rightNode = parseNumber(tokens.get(i + 1));
                lastNode = parseExpression(lastNode, (OperatorToken) tokens.get(i), rightNode);
                if (i == 1) {
                    firstExpressionNode = lastNode;
                }
            }
        }
        return lastNode;
    }

    private static Node createSubTree(List<Token> sublistOfTokens) {
//        Parser parser = new Parser(sublistOfTokens);
//        parser.parse();
//        return parser.getFirstExpressionNode().makeBracketed();
        return new Parser(sublistOfTokens).parse().makeBracketed();
    }

    private List<Token> sublistOfTokens(List<Token> tokenList) {
        // Example tokenList: {(, 3, *, 4, ), -, 5}
        int closingBracketPosition = 0;
        int bracketCount = 0;
        // Count openingBrackets and subtract with closingBrackets.
        // When the matching closing bracket for the first opening bracket has been found (i.e count = 0), remember its position.
        for (int i = 0; i < tokenList.size(); i++) {
            Token token = tokenList.get(i);
            if (token.getType() == Token.Type.BRACKET && ((BracketToken) token).isOpen()) {
                bracketCount++;
            } else if (token.getType() == Token.Type.BRACKET && !((BracketToken) token).isOpen()) {
                bracketCount--;
                if (bracketCount == 0) {
                    closingBracketPosition = i;
                }
            }
        }
        if (closingBracketPosition == 0) {
            throw new CalculatorException("Syntax error. No matching closing bracket found.");
        }
        return tokenList.subList(1, closingBracketPosition);
    }

    private List<Token> addMultiplierToBracketedTokens(Token numberToken, List<Token> sublistOfTokens) {
        int initialSize = sublistOfTokens.size();
        sublistOfTokens.add(0, numberToken);
        sublistOfTokens.add(1, new OperatorToken("*", 1));
        sublistOfTokens.add(2, Token.of(Token.Type.BRACKET, "(", 0));
        sublistOfTokens.add(Token.of(Token.Type.BRACKET, ")", initialSize+3));
        return sublistOfTokens;
    }

    // Nodes are connected via the operatorNodes: only operatorNodes have childNodes(which are numberNodes). See visual example at the top.
    // Thus Numbers are always leaf nodes (children are null)
    private Node parseNumber(final Token token) {
        return Node.createLeaf(token);
    }

    private Node parseExpression(final Node lastNode, final OperatorToken operator, final Node rightChild) {
        // Example: 4 - 5 * 6. When getting to the operator with a higher priority (i.e. * higher than -), you already have a lastNode (leftChild=4, operator(+), rightChild=leafNode(5)).
        // Instead of putting the prioritized calculation (previousNode*6) above the previous node, it has to be put in a node below, so it gets calculated earlier in the recursion.
        // To do so, the rightChild in this previousNode has to be replaced with a new (priority) node: leftChild=previousNode.rightChild (5), operator (*), rightChild= nextNumber (6).
        // Exception if the leftChild is bracketed and gets highest priority e.g. (4-5) * 6.
        if (lastNode.leftChild != null && lastNode.hasLowerPriorityThan(operator) && !lastNode.isBracketed) {
            return lastNode.replaceRightChild(lastNode, operator, rightChild);
        }
        // For prioritized bracket calculations (replace rightChild with the subtree).
        // Brackets indicate a
//        if (rightChild.getToken().getType() == Token.Type.OPERATOR) {
////            lastNode.rightChild = rightChild;
//            return Node.createNode(lastNode, operator, rightChild);
////            return lastNode.replaceRightChild(lastNode, operator, rightChild);
//        }
        return Node.createNode(lastNode, operator, rightChild, false);
    }

    public Node getFirstExpressionNode() {
        return firstExpressionNode;
    }
}
