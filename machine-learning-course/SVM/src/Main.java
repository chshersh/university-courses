import com.ifmo.ml.svm.SVMClassifier;
import com.ifmo.ml.svm.utils.Pair;

public class Main {
    public static void main(String[] args) {
        SVMClassifier svm = new SVMClassifier("chips.txt");
        Pair<Double, Double> coef = svm.learn();
        double k = coef.getFirst(), b = coef.getSecond();
        System.out.println("y = " + k + " * x + " + b);
    }
}
