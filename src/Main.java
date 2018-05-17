import data.Data;
import data.HtmlData;
import data.preprocess.FrequencyVectorizer;
import data.preprocess.Vectorizer;
import evaluate.Evaluator;
import evaluate.VScore;
import model.KMeans;
import model.UnsupervisedModel;
import utils.ParameterSpaceException;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, ParameterSpaceException {

        Data data = new HtmlData();
        final List<String> dataAddress = Utils.getUrlAddresses("misc/urls.txt");

        List<String> readResults = data.read(dataAddress);
        Vectorizer vectorizer = new FrequencyVectorizer(20, readResults);

        int [] groundTruth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                              1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        // TODO: Write jsonParamLoder later.
        Evaluator evaluator = new VScore();
        Map<String, Object> hyperParams = new HashMap<>();
        hyperParams.put("numCluster", 2);
        hyperParams.put("maxIter", 10);

        UnsupervisedModel model = new KMeans(evaluator, hyperParams);
        UnsupervisedExperiment unsupExperiment = new UnsupervisedExperiment(vectorizer, model, groundTruth);
        double score = unsupExperiment.score();
        System.out.println(evaluator.getClass().getName() + ": " + score);

    }
}
