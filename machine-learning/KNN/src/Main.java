import com.ifmo.ml.knn.KNNLearner;
import com.ifmo.ml.knn.utils.Pair;

public class Main {
    public static void main(String[] args) {
        KNNLearner machine = new KNNLearner("chips.txt");
        int cnt = 100;
        double testsMin = 1, trainsMin = 1,
               testsMax = 0, trainsMax = 0,
               testsAve = 0, trainsAve = 0;

        for (int i = 0; i < cnt; i++) {
            Pair<Double, Double> l = machine.learn();

            if (l.getFirst() == 0 || l.getSecond() == 0) {
                continue;
            }

            testsAve += l.getFirst();
            testsMin = Math.min(testsMin, l.getFirst());
            testsMax = Math.max(testsMax, l.getFirst());

            trainsAve += l.getSecond();
            trainsMin = Math.min(trainsMin, l.getSecond());
            trainsMax = Math.max(trainsMax, l.getSecond());
        }

        System.out.println("tests min: " + testsMin + "; trains min: " + trainsMin);
        System.out.println("tests max: " + testsMax + "; trains max: " + trainsMax);
        System.out.println("tests ave: " + (testsAve / cnt) + "; trains ave: " + (trainsAve / cnt));
    }
}
