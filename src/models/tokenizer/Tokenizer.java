package models.tokenizer;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public interface Tokenizer {
    String SPLITTER = "\\s";

    default String cleanText(String text) {
        return text.toLowerCase();
    }

    void tokenizeData(File file, String text, Map<String, Map<File, Integer>> data);

    Set<String> tokenizeQuery(String text);

    LinkedList<String> develop(String token);

}
