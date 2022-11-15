package parser;

import lexer.NumberToken;
import lexer.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void createLeaf() {
        Token numberToken = new NumberToken("6", 1);
        Node leafNode = Node.createLeaf(numberToken);

    }

    @Test
    void createNode() {
    }
}