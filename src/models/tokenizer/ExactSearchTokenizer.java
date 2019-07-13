package models.tokenizer;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class ExactSearchTokenizer implements Tokenizer {

    @Override
    public Set<String> tokenize(String text) {
        return Arrays.stream(text.split(SPLITTER)).collect(Collectors.toSet());
    }

    @Override
    public LinkedList<String> develop(String token) {
        return new LinkedList<>(Collections.singletonList(token));
    }
}
