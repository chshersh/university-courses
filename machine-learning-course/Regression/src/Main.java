import com.ifmo.ml.reg.LinearRegression;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Created by ruslan on 11/13/14
 */
public class Main {
    public static void main(String[] args) throws URISyntaxException {
        LinearRegression regression =
                new LinearRegression(Paths.get(Main.class.getResource("prices.txt").toURI()));
        System.out.println(regression.learn());
    }
}
