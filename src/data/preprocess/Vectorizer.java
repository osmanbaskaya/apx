package data.preprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Vectorizer {

    final int freqThreshold;
    final List<String> texts;
    double[][] vectorizedMatrix;

    public Tokenizer tokenizer;

    public double[][] getVectorizedMatrix() {
        return vectorizedMatrix;
    }

    public Map<String, Integer> getVocabularyMap() {
        return vocabularyMap;
    }

    Map<String, Integer> vocabularyMap;

    protected Map<String, Integer> token2idx;
    protected Map<Integer, String> idx2token;

    public Vectorizer(int freqThreshold, List<String> texts) {
        this.freqThreshold = freqThreshold;
        this.texts = texts;
    }

    public int getFreqThreshold() {
        return freqThreshold;
    }

    public List<String> getTexts() {
        return texts;
    }


    public Map<String, Integer> getToken2idx() {
        return token2idx;
    }

    public Map<Integer, String> getIdx2token() {
        return idx2token;
    }

    public abstract void fit();
}
