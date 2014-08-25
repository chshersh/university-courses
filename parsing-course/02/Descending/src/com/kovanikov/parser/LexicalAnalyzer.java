package com.kovanikov.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Fetches characters from InputStream and
 * turns them to {@link com.kovanikov.parser.Token}
 */
public class LexicalAnalyzer  {
    private static Set<Character> signs = new HashSet<Character>() {{
        addAll(Arrays.asList('+', '-', '*', '^'));
    }};

    private InputStream inputStream;
    private int curChar;
    private int curPos;
    private Token curToken;
    private String detail;

    public LexicalAnalyzer(InputStream inputStream) throws ParseException {
        this.inputStream = inputStream;
        this.curPos = 0;
        nextChar();
    }

    public LexicalAnalyzer(String inputString) throws ParseException {
        this(new ByteArrayInputStream(inputString.getBytes()));
    }

    private void nextChar() throws ParseException {
        curPos++;
        curChar = -1;
        try {
            curChar = inputStream.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    private boolean isBlank(int c) {
        return Character.isWhitespace(c);
    }

    private String getNumber() throws ParseException {
        String number = "";
        while (Character.isDigit(curChar)) {
            number += (char) curChar;
            nextChar();
        }
        return number;
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        if (signs.contains((char) curChar)) {
            detail = "" + (char) curChar;
            curToken = Token.SIGN;
            nextChar();

            if (detail.equals("-") && Character.isDigit(curChar)) { // negative number
                detail += getNumber();
                curToken = Token.NUMBER;
            }
        } else if (Character.isDigit(curChar)) {
            curToken = Token.NUMBER;
            detail = getNumber();
        } else if (curChar == -1) {
            curToken = Token.END;
        } else {
            throw new ParseException("Illegal character: " + (char) curChar, curPos);
        }
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }

    public String getDetails() {
        return detail;
    }
}