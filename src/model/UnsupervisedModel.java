package model;

import evaluate.Evaluator;
import utils.ParameterSpaceException;

import java.util.Map;

public abstract class UnsupervisedModel extends Model {


    UnsupervisedModel(Evaluator evaluator, Map<String, Object> params) {
        super(evaluator, params);
    }

    public abstract void fit(double [][] X) throws ParameterSpaceException;

}
