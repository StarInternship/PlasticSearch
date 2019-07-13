package models.tokenizer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ExactSearchTokenizer implements Tokenizer {
    private static final String SPLITTER = "\\s";

    @Override
    public Set<String> tokenize(String text) {
        return Arrays.stream(text.split(SPLITTER)).collect(Collectors.toSet());
    }
}
