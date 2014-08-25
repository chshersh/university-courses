package com.kovanikov.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Grammar:
 * S -> n A | EPS
 * A -> n A SIGN A | EPS
 * SIGN -> + | - | * | ^
 */
public class Parser {
    private static LexicalAnalyzer lex;

    public static Tree parse(InputStream inputStream) throws ParseException {
        lex = new LexicalAnalyzer(inputStream);
        lex.nextToken();
        return POSTFIX();
    }

    public static Tree parse(String inputString) throws ParseException {
        return parse(new ByteArrayInputStream(inputString.getBytes()));
    }

    private static Tree POSTFIX() throws ParseException {
        switch (lex.curToken()) {
            case NUMBER:
                String number = lex.getDetails();
                lex.nextToken();
                Tree aux = AUXILARY();

                if (lex.curToken() != Token.END) {
                    throw new ParseException("Expected END but found " +
                            lex.getDetails(), lex.curPos() - 1);
                }

                return new Tree("POSTFIX", new Tree(number), aux);
            case END:
                // S -> eps
                return new Tree("POSTFIX");
            default:
                throw new ParseException("Expected [0-9]+ or END but found: " +
                        lex.getDetails(), lex.curPos() - 1);
        }
    }

    private static Tree AUXILARY() throws ParseException {
        switch (lex.curToken()) {
            case NUMBER:
                // A -> n A sign A
                String number = lex.getDetails();
                lex.nextToken();
                Tree aux1 = AUXILARY();
                Tree sign = SIGN();
                Tree aux2 = AUXILARY();
                return new Tree("AUX", new Tree(number), aux1, sign, aux2);
            case SIGN:
            case END:
                // A -> eps
                return new Tree("AUX");
            default:
                throw new ParseException("Expected [0-9], sign or END but found: " +
                        lex.getDetails(), lex.curPos() - 1);
        }
    }

    private static Tree SIGN() throws ParseException {
        switch (lex.curToken()) {
            case SIGN:
                Tree sign = new Tree("SIGN", new Tree(lex.getDetails()));
                lex.nextToken();
                return sign;
            default:
                throw new ParseException("Expected +, -, *, ^ but found: " +
                        lex.getDetails(), lex.curPos() - 1);
        }
    }
}