package models.tokenizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public interface Tokenizer {
    String SPLITTER = "\\s";

    default String cleanText(String text) {
        return text.toLowerCase();
    }

    default Set<String> tokenize(String text) {
        return Arrays.stream(text.split(SPLITTER)).collect(Collectors.toSet());
    }

    default Set<String> tokenize(String text, int min, int max) {
        Set<String> tokenSet = new HashSet<>();

        Arrays.stream(text.split(SPLITTER)).forEach(token -> {
            if (token.length() <= min) {
                tokenSet.add(token);
            } else {
                final int MAX = Math.min(max, token.length());

                for (int length = min; length <= MAX; length++) {
                    for (int start = 0; start + length <= token.length(); start++) {
                        tokenSet.add(token.substring(start, start + length));
                    }
                }
            }
        });

        return tokenSet;
    }

    LinkedList<String> develop(String token);

}
