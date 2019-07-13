package models.tokenizer;

import java.util.*;

public class NgramSearchTokenizer implements Tokenizer {

    @Override
    public LinkedList<String> develop(String token) {
        return new LinkedList<>(Collections.singletonList(token));
    }
}
