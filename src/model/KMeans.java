package model;

import evaluate.Evaluator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.ParameterSpaceException;

import java.util.*;

import utils.Utils;

public class KMeans extends UnsupervisedModel {

    private double [][] centroids;
    private List<List<Integer>> centroidAssignments;

    public KMeans(Evaluator evaluator, Map<String, Object> params) {
        super(evaluator, params);
    }

    @Override
    public void fit(final double[][] X) throws ParameterSpaceException {
        int numOfPoint = X.length;

        // Pull Hyperparameters for this algorithm.
        int seed = (int) this.getHyperParams().getOrDefault("seed", 42);
        int numCluster = (int) this.getHyperParams().getOrDefault("numCluster", 5);
        int maxIter = (int) this.getHyperParams().getOrDefault("maxIter", 1000);

        // TODO: Distance metric can be a Class. We can use Strategy pattern.
        // DistanceMetric distanceMetric = EuclideanDistance() ...


        if (numCluster > numOfPoint) throw new ParameterSpaceException("Number of " +
                "points too " +"small to use " + numCluster);

        int numDim = X[0].length;

        centroids = new double[numCluster][numDim];

        List<List<Integer>> centroidAssignments;

        // Random initialization.
        Random r = new Random(seed);
        Set<Integer> usedIndices = new HashSet<>();

        for (int i = 0; i < numCluster; i++) {
            int idx;
            // Sampling without replacement.
            do {
                idx = r.nextInt(numOfPoint);
            } while (usedIndices.contains(idx));

            centroids[i] = X[idx].clone();
            usedIndices.add(idx);
        }

        // Expectation-Maximization part. Training.
        for (int i = 0; i < maxIter; i++) {
            centroidAssignments = expectationStep(X);
            maximizationStep(centroidAssignments, numCluster, numDim, X);
        }

        this.centroidAssignments = expectationStep(X); // Last assignment step.
    }

    private List<List<Integer>> createCentroidAssignmentList(int numCluster){
        List<List<Integer>> centroidAssignments = new ArrayList<>(numCluster);
        for (int i = 0; i < numCluster; i++) {
            centroidAssignments.add(new ArrayList<>());
        }
        return centroidAssignments;
    }

    private List<List<Integer>> expectationStep(final double[][] X) {

        int numCluster = centroids.length;
        final List<List<Integer>> centroidAssignments = createCentroidAssignmentList(numCluster);

        int closestCentroid;
        double minDistance;
        double distance;
        // Iterate over all data points.
        for (int i = 0; i < X.length; i++) {
            closestCentroid = -1;
            minDistance = Integer.MAX_VALUE;
            for (int j = 0; j < numCluster; j++) {
                distance = Utils.calculateEuclideanDistance(centroids[j], X[i]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCentroid = j;
                }
            }
            centroidAssignments.get(closestCentroid).add(i); // label the point regarding closest centroid.
        }
        return centroidAssignments;
    }

    private void maximizationStep(List<List<Integer>> centroidAssignments,
                                         int numCluster, int numDim, double [][] X) {
        for (int i = 0; i < numCluster; i++) {
            double[] total = new double[numDim];
            List<Integer> points = centroidAssignments.get(i);
            for (Integer point : points) {
                total = Utils.add(total, X[point]);
            }
            if (points.size() > 0)
                centroids[i] = Utils.divideScalar(total, points.size());
        }
    }


    @Override
    public int predict(double[] X) {
        throw new NotImplementedException();
    }

    @Override
    public int[] predict(double[][] X) {
        List<List<Integer>> lists = expectationStep(X);
        int [] predictions = new int[X.length];
        for (int clusterId = 0; clusterId < lists.size(); clusterId++) {
            List<Integer> list = lists.get(clusterId);
            for (Integer pointId: list) {
                predictions[pointId] = clusterId;
            }
        }
        return predictions;
    }



}
