package models.tokenizer;

import java.io.File;
import java.util.*;

public class NgramSearchTokenizer implements Tokenizer {
    private static int MIN = 3;
    private static int MAX = 30;

    @Override
    public void tokenizeData(File file, String text, Map<String, Map<File, Integer>> data) {

        Arrays.stream(text.split(SPLITTER)).forEach(token -> {
            if (token.length() > MIN) {
                final int max = Math.min(MAX, token.length() - 1);

                for (int length = MIN; length <= max; length++) {
                    for (int start = 0; start + length <= token.length(); start++) {
                        String newToken = token.substring(start, start + length);

                        Map<File, Integer> occurrences = data.get(newToken);
                        if (occurrences != null) {
                            Integer occurrence = occurrences.get(file);
                            if (occurrence != null) {
                                occurrences.replace(file, occurrence + 1);
                            } else {
                                occurrences.put(file, 1);
                            }
                        } else {
                            Map<File, Integer> occurrence = new HashMap<>();
                            occurrence.put(file, 1);
                            data.put(newToken, occurrence);
                        }
                    }
                }
            }
        });
    }

    @Override
    public Set<String> tokenizeQuery(String text) {
        Set<String> tokenSet = new HashSet<>();

        Arrays.stream(text.split(SPLITTER)).forEach(token -> {
            if (token.length() > MIN) {
                final int max = Math.min(MAX, token.length() - 1);

                for (int length = MIN; length <= max; length++) {
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
