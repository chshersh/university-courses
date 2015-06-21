import com.ifmo.ml.bsc.BayesClassifier;
import com.ifmo.ml.bsc.utils.Pair;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BayesClassifier bc = new BayesClassifier();
            Pair<Double, Double> res = bc.learn();
            System.out.println(res.getFirst() + " " + res.getSecond());
        } catch (IOException e) {

        }
    }
}
