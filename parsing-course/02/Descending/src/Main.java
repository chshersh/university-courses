import com.kovanikov.parser.Parser;
import org.StructureGraphic.v1.DSutils;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            String example = "1 2 3 ^ 5 4 + - *", example2 = "1 2 3 + + 4 +";
            //String falseTest = "10 +";
            DSutils.show(Parser.parse(new ByteArrayInputStream(example.getBytes())), 60, 30);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + " at " + e.getErrorOffset());
        }
    }
}
