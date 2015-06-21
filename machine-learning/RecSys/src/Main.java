import com.ifmo.ml.yandex.recommend.SVDRatingPredictor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SVDRatingPredictor predictor = new SVDRatingPredictor("data/train.csv", "data/validation.csv");
            predictor.learn();
            predictor.passTests("data/test-ids.csv");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
