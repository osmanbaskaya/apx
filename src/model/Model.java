package model;

import evaluate.Evaluator;

import java.util.Map;

public abstract class Model {

    private boolean isTrained = false;
    private Evaluator evaluator;
    private Map<String, Object> hyperParams;

    Model(Evaluator evaluator, Map<String, Object> params) {
        this.evaluator = evaluator;
        this.hyperParams = params;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Map<String, Object> getHyperParams() {
        return hyperParams;
    }

    public void setHyperParams(Map<String, Object> hyperParams) {
        this.hyperParams = hyperParams;
    }

    Evaluator getEvaluator() {
        return evaluator;
    }

    public boolean isTrained() {
        return isTrained;
    }

    public abstract int predict(double [] X);
    public abstract int [] predict(double [][] X);

    public double evaluate(int[] groundTruth, int[] predictions){
        return getEvaluator().evaluate(groundTruth, predictions);
    }
}
