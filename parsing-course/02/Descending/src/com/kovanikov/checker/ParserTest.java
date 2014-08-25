package com.kovanikov.checker;

import com.kovanikov.parser.LexicalAnalyzer;
import com.kovanikov.parser.Parser;
import com.kovanikov.parser.Token;
import com.kovanikov.parser.Tree;
import org.antlr.v4.runtime.*;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test cases for testing {@link com.kovanikov.parser.Parser}.
 */
public class ParserTest {
    private static String[] BAD_CHARACTERS = {"x", "#", "!"};
    private static Random RND = new Random();
    private static String[] SIGNS = {"+", "-", "*"};
    private static int MIN_TEST_CASES = 10,
                       MAX_PARTS_IN_TEST = 20,
                       MAX_PART_LENGTH = 10;

    // get InputStream tokens
    private String[] getTokens(LexicalAnalyzer lex) throws ParseException {
        List<String> tokens = new ArrayList<>();
        lex.nextToken();
        while (lex.curToken() != Token.END) {
            tokens.add(lex.getDetails());
            lex.nextToken();
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    /**
     * Tests on LexicalAnalyzer.
     */
    @Test
    public void emptyString() throws ParseException {
        LexicalAnalyzer lex = new LexicalAnalyzer("");
        String[] tokens = getTokens(lex);
        assertArrayEquals(tokens, new String[0]);
    }

    @Test(expected = ParseException.class)
    public void illegalCharacter() throws ParseException {
        String test = BAD_CHARACTERS[0];
        LexicalAnalyzer lex = new LexicalAnalyzer(test);
        getTokens(lex);
    }

    @Test
    public void correctString1() throws ParseException {
        LexicalAnalyzer lex = new LexicalAnalyzer("10 20 +");
        String[] tokens = getTokens(lex);
        assertArrayEquals(tokens, new String[]{"10", "20", "+"});
    }

    @Test
    public void correctString2() throws ParseException {
        String test = "    \n     -   123 + -555 *    ";
        LexicalAnalyzer lex = new LexicalAnalyzer(test);
        assertArrayEquals(getTokens(lex), new String[]{"-", "123", "+", "-555", "*"});
    }

    @Test
    public void negativeNumber() throws ParseException {
        String test = "    \n     -123 \t   ";
        LexicalAnalyzer lex = new LexicalAnalyzer(test);
        assertArrayEquals(getTokens(lex), new String[]{"-123"});
    }

    @Test
    public void spaceless() throws ParseException {
        String test = "12+214*-14";
        LexicalAnalyzer lex = new LexicalAnalyzer(test);
        assertArrayEquals(getTokens(lex), new String[]{"12", "+", "214", "*", "-14"});
    }

    /**
     * Tests on Parser -- correct expessions
     */

    private String getAntlrTree(String expr) {
        CharStream input = new ANTLRInputStream(expr);
        PostfixLexer lexer = new PostfixLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        PostfixParser parser = new PostfixParser(tokens);
        parser.setBuildParseTree(true);
        RuleContext tree = parser.postfix();
        tree.inspect(parser);
        //System.out.println("Antlr: " + tree.toStringTree(parser));
        return tree.toStringTree(parser);
    }

    String getParseTree(String s)  {
        Tree t = new Tree("");
        try {
            t = Parser.parse(s);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + " " + e.getErrorOffset());
        }
        //System.out.println("My: " + t.toString());
        return t.toString();
    }

    // S -> eps
    @Test
    public void empty() throws ParseException {
        String s = "";
        assertEquals(getParseTree(s), getAntlrTree(s));
    }

    // S -> n A, where A -> eps
    @Test
    public void onePositiveNumber() throws ParseException {
        String s = "42";
        assertEquals(getParseTree(s), getAntlrTree(s));
    }

    // S -> n A, where A -> eps
    @Test
    public void oneNegativeNumber() throws ParseException {
        String s = "-42";
        assertEquals(getParseTree(s), getAntlrTree(s));
    }

    // A -> n A sign A, where both A -> eps, also some whitespaces in string
    @Test
    public void simpleBinary() throws ParseException {
        String s = "2  2 +";
        assertEquals(getParseTree(s), getAntlrTree(s));
    }

    // A -> n A sign A, where first of A -> eps
    @Test
    public void complicatedBinary1() throws ParseException {
        String s = "12 \n 2 + -3  25 **";
        assertEquals(getParseTree(s), getAntlrTree(s));
    }

    // A -> n A sign A, where none of A -> eps
    @Test
    public void complicatedBinary2() throws ParseException {
        String s = "1 2 3 + + 4 +";
        assertEquals(getParseTree(s), getAntlrTree(s));
    }

    @Test
    public void allSign() throws ParseException {
        String s = "1 2 3 4 + - *";
        assertEquals(getParseTree(s), getAntlrTree(s));
    }

    /**
     * Tests on Parser -- incorrect expressions
     */
    @Test(expected = ParseException.class)
    public void illegalString() throws ParseException {
        Parser.parse(BAD_CHARACTERS[1]);
    }

    @Test(expected = ParseException.class)
    public void onlySign() throws ParseException {
        Parser.parse("-");
    }

    @Test(expected = ParseException.class)
    public void tooLittleNumbers() throws ParseException {
        Parser.parse("-10 -");
    }

    @Test(expected = ParseException.class)
    public void usualNotation() throws ParseException {
        Parser.parse("5 + 10");
    }

    @Test(expected = ParseException.class)
    public void prefixNotation() throws ParseException {
        Parser.parse("+ 5 10");
    }

    @Test(expected = ParseException.class)
    public void incorrectSymbol1() throws ParseException {
        Parser.parse("x 2 +");
    }

    @Test(expected = ParseException.class)
    public void incorrectSymbol2() throws ParseException {
        Parser.parse("2 2 ^");
    }

    /**
     * Test on Parser -- random expressions
     */
    public String getRandomCorrectExpression(int k) {
        StringBuilder buf = new StringBuilder(String.valueOf(RND.nextInt()) + " ");
        for (int i = 0; i < k; ++i) {
            int partLength = RND.nextInt(MAX_PART_LENGTH);

            for (int j = 0; j < partLength; ++j) {
                buf.append(RND.nextInt() + " ");
            }
            for (int j = 0; j < partLength; ++j) {
                buf.append(SIGNS[RND.nextInt(3)] + " ");
            }
        }
        return buf.toString();
    }

    private String getRandomIncorrectExpression() {
        return getRandomCorrectExpression(RND.nextInt(MAX_PARTS_IN_TEST)) + BAD_CHARACTERS[RND.nextInt(3)];
    }

    @Test
    public void randomCorrectMultiple() throws ParseException {
        int testNumbers = MIN_TEST_CASES + RND.nextInt(100);
        for (int i = 0; i < testNumbers; ++i) {
            int numberOfParts = RND.nextInt(MAX_PARTS_IN_TEST);
            String rndString = getRandomCorrectExpression(numberOfParts);
            assertEquals(getParseTree(rndString), getAntlrTree(rndString));
        }
    }

    @Test(expected = ParseException.class)
    public void randomIncorrectSingle() throws ParseException {
        Parser.parse(getRandomIncorrectExpression());
    }
}
