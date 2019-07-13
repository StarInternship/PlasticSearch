package models.tokenizer;

import java.util.*;

public class NgramSearchTokenizer implements Tokenizer {
    private static int Min = 3;
    private static int MAX = 30;

    @Override
    public Set<String> tokenize(String text) {
        Set<String> tokenSet = new HashSet<>();

        Arrays.stream(text.split(SPLITTER)).forEach(token -> {
            if (token.length() > Min) {
                final int max = Math.min(MAX, token.length() - 1);

                for (int length = Min; length <= max; length++) {
                    for (int start = 0; start + length <= token.length(); start++) {
                        tokenSet.add(token.substring(start, start + length));
                    }
                }
            }
        });

        return tokenSet;
    }

    @Override
    public LinkedList<String> develop(String token) {
        return new LinkedList<>(Collections.singletonList(token));
    }
}
