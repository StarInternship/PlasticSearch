package models.tokenizer;

import java.util.LinkedList;
import java.util.Set;

public interface Tokenizer {
    String SPLITTER = "\\s";

    default String cleanText(String text) {
        return text.toLowerCase();
    }

    Set<String> tokenize(String text);

    LinkedList<String> develop(String token);

}
