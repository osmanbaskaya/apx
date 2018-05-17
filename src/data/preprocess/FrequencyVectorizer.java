package data.preprocess;

import java.util.*;

public class FrequencyVectorizer extends Vectorizer {

    public FrequencyVectorizer(int freqThreshold, List<String> texts) {
        super(freqThreshold, texts);
        tokenizer = new WhiteSpaceTokenizer();

    }

    public void fit() {
        this.vectorizedMatrix = vectorize();
    }

    private double[][] vectorize() {
        List<List<String>> tokenizedTexts = tokenize(texts);
        createVocabMap(tokenizedTexts);
        return createFrequencyMatrix(tokenizedTexts);
    }

    private double[][] createFrequencyMatrix(List<List<String>> tokenizedTexts) {
        int tokenId;
        int documentId = 0;
        double[][] m = new double[tokenizedTexts.size()][idx2token.size()];

        for (List<String> tokens : tokenizedTexts) {
            for (String token : tokens) {
                if (token2idx.containsKey(token)) {
                    tokenId = token2idx.get(token);
                    m[documentId][tokenId] += 1;
                }
            }
            documentId++;
        }
        utils.Utils.normalizeInPlace(m);
        return m;
    }


    private List<List<String>> tokenize(List<String> texts) {
        return this.tokenizer.tokenize(texts);
    }

    private void createVocabMap(List<List<String>> tokenizedText) {

        vocabularyMap = new HashMap<>();

        token2idx = new HashMap<>();
        idx2token = new HashMap<>();

        int idx = 0;

        // Read all the documents and calculate the token counts.
        for (List<String> tokens : tokenizedText) {
            for (String token : tokens) {
                if (vocabularyMap.containsKey(token))
                    vocabularyMap.put(token, vocabularyMap.get(token) + 1);
                else {
                    vocabularyMap.put(token, 1);
                }
            }
        }

        // Sort the vocab.
        vocabularyMap = utils.Utils.sortByComparator(vocabularyMap, false);  // descending order.
        Map<String, Integer> filteredVocabularyMap = new HashMap<>();
        String token;
        for (Map.Entry<String, Integer> entry : vocabularyMap.entrySet()) {
            if (entry.getValue() >= freqThreshold) {
                token = entry.getKey();
                filteredVocabularyMap.put(token, entry.getValue());
                token2idx.put(token, idx);
                idx2token.put(idx, token);
                idx++;
            } else
                break;
        }

        vocabularyMap = filteredVocabularyMap; // replace it with new Vocabulary.
        System.out.println("Vocabulary size " + vocabularyMap.size());
    }
}
