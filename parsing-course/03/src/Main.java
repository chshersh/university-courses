import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.kovanikov.formatter.JavaAutoFormatter;
import com.kovanikov.parser.*;
import org.antlr.v4.runtime.*;

public class Main {

    private static void test() throws IOException {
        InputStream file = new FileInputStream("TestClass.java");

        CharStream input = new ANTLRInputStream(file);
        JavaLexer lexer = new JavaLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);

        JavaParser parser = new JavaParser(tokens);
        parser.setBuildParseTree(true);
        RuleContext tree = parser.javaLang();
        tree.inspect(parser);
        System.out.println("Antlr: " + tree.toStringTree(parser));
    }

    public static void main(String[] args) throws IOException {
        //test();
        JavaAutoFormatter.format(new String[]{"TestClass.java"});
    }
}
