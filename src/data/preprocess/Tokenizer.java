package data.preprocess;

import java.util.List;

public interface Tokenizer {

    public List<List<String>> tokenize(List<String> texts);

}
