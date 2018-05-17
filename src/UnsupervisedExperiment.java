import data.preprocess.Vectorizer;
import model.UnsupervisedModel;
import utils.ParameterSpaceException;

public class UnsupervisedExperiment {

    private Vectorizer vectorizer;
    private UnsupervisedModel model;
    private int[] groundTruth;

    public UnsupervisedExperiment(Vectorizer vectorizer, UnsupervisedModel model, int[] groundTruth) {
        this.vectorizer = vectorizer;
        this.model = model;
        this.groundTruth = groundTruth;
    }

    public double score() throws ParameterSpaceException {
        vectorizer.fit();
        double[][] X = vectorizer.getVectorizedMatrix();
        model.fit(X);
        int [] predictions = model.predict(X);
        return model.evaluate(groundTruth, predictions); // prediction for training data. Unsupervised.
    }
}
