package com.ifmo.ml.reg;

import com.ifmo.ml.reg.dividers.CrossValidationDivider;
import com.ifmo.ml.reg.utils.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 11/13/14
 */
public class LinearRegression {
    private List<FlatInfo> flats = new ArrayList<>();
    private CrossValidationDivider<FlatInfo> divider;
    private List<FlatInfo> trains;
    private List<FlatInfo> test;
    private static int KD_FOLD_CONST = 5;
    private double[][] Y;
    private double[][] X;
    private double[][] XT;
    private double[][] B;

    public LinearRegression(Path path) {

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] pts = line.split(",");
                assert pts.length == 3;
                flats.add(new FlatInfo(Integer.parseInt(pts[0]),
                                       Integer.parseInt(pts[1]),
                                       Integer.parseInt(pts[2])));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        divider = new CrossValidationDivider<>(flats, KD_FOLD_CONST);
        divider.divide(0);
        trains = divider.getTrainingSamples();
        test = divider.getTestingSamples();
        initXY();

    }

    private void initXY() {
        X = new double[trains.size()][3];
        XT = new double[3][trains.size()];
        Y = new double[trains.size()][1];

        for (int i = 0; i < X.length; i++) {
            X[i][0] = 1;
            XT[0][i] = 1;
        }
        int i1 = 1;
        int i2 = 2;
        for (int i = 0; i < X.length; i++) {
            X[i][i1] = XT[i1][i] = trains.get(i).getSquare();
            X[i][i2] = XT[i2][i] = trains.get(i).getRooms();
            Y[i][0] = trains.get(i).getCost();
        }
    }

    private double findMeasure(List<FlatInfo> list) {
        double res = 0.;
        for (FlatInfo aTest : list) {
            double y1 = B[0][0] + B[1][0] * aTest.getSquare() + B[2][0] * aTest.getRooms();
            res += Math.pow(y1 - aTest.getCost(), 2.);
            System.out.println(Math.abs(y1 - aTest.getCost()) / aTest.getCost());
        }
        return Math.sqrt(res / list.size());
    }

    public double learn() {
        double[][] multiply = Matrix.multiply(XT, X);
        double[][] multiply1 = Matrix.multiply(Matrix.invert(multiply), XT);
        B = Matrix.multiply(multiply1, Y);
        return findMeasure(test);
    }
}
