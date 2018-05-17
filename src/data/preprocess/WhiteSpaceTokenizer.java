package data.preprocess;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WhiteSpaceTokenizer implements Tokenizer {
    @Override
    public List<List<String>> tokenize(List<String> texts) {
        Set<String> stopWords;
        List<List<String>> tokenizedTexts = new ArrayList<>();
        try {
            stopWords = Utils.getStopWordSet(null);
            System.out.println("StopWord Size: " + stopWords.size());

            for (String s : texts) {
                s = s.toLowerCase().replaceAll("[^a-z0-9 ]", "");
                List<String> posTokens = new ArrayList<>();
                for (String token: s.split("\\s+")) {
                    if (!stopWords.contains(token) && !token.startsWith("<") && !token.endsWith(">")){
                        posTokens.add(token);
                    }
                }
                tokenizedTexts.add(posTokens);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokenizedTexts;
    }
}
