package models.tokenizer;

import java.util.Set;

public interface Tokenizer {

    Set<String> tokenize(String text);

}
