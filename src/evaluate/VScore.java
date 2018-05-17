package evaluate;

import utils.Utils;

import java.util.Map;

public class VScore implements Evaluator {

    @Override
    public double evaluate(int[] groundTruth, int [] predictions) {

        assert groundTruth.length == predictions.length;

        double homogeneity = calculateHomogeneity(groundTruth, predictions);
        double completeness = calculateCompleteness(groundTruth, predictions);

        double num = 2 * homogeneity * completeness;
        double denom = homogeneity + completeness;
        if (denom == 0)
            return 0.0;
        return  num / denom;
    }

    private double calculateHomogeneity(int[] groundTruth, int [] predictions) {
        int [][] m =createConfusionMatrix(groundTruth, predictions);
        double N = Utils.sum(m);


        // Conditional Prob part.
        int rowSum;
        double val;
        double condEntropy = .0;

        for (int[] aM : m) {
            rowSum = Utils.sum(aM);
            for (int j = 0; j < m[0].length; j++) {
                val = aM[j];
                if (val != 0) {
                    condEntropy -= (val / N) * Math.log(val / rowSum);
                }
            }
        }

        // Total part
        double totalEntropy = .0;
        int [][] mTrans =createConfusionMatrix(predictions, groundTruth);
        for (int[] mTran : mTrans) {
            val = Utils.sum(mTran);
            if (val != 0) {
                totalEntropy -= (val / N) * Math.log(val / N);
            }
        }

        if (totalEntropy == 0)
            return 0.0;

        return 1 - (condEntropy / totalEntropy);
    }

    private int [][] createConfusionMatrix(int [] a, int [] b) {

        Map<Integer, Integer> truthLabelMap = Utils.getLabelMap(a);
        Map<Integer, Integer> predLabelMap = Utils.getLabelMap(b);

        int numUniqueTruth = truthLabelMap.size();
        int numUniquePred = predLabelMap.size();

        int [][] m = new int [numUniqueTruth][numUniquePred];
        for (int i = 0; i < a.length; i++) {
            m[truthLabelMap.get(a[i])][predLabelMap.get(b[i])] += 1;
        }
        return m;
    }

    private double calculateCompleteness(int[] groundTruth, int [] predictions) {
        return calculateHomogeneity(predictions, groundTruth);
    }
}
