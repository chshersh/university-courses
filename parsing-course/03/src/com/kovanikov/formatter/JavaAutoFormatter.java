package com.kovanikov.formatter;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.kovanikov.parser.*;

public class JavaAutoFormatter {
    public static void format(String[] args) throws IOException {
        String inputFile = args.length > 0 ? args[0] : null;
        InputStream inputStream = inputFile == null ? System.in : new FileInputStream(inputFile);

        ANTLRInputStream input = new ANTLRInputStream(inputStream);

        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.javaLang(); // parse

        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        //JavaFormatListener extractor = new JavaFormatListener(parser);
        JavaFormatListener extractor = new JavaFormatListener();
        walker.walk(extractor, tree); // initiate walk of tree with listener
    }
}
