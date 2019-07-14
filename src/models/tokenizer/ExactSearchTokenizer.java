package models.tokenizer;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ExactSearchTokenizer implements Tokenizer {

    @Override
    public void tokenizeData(File file, String text, Map<String, Map<File, Integer>> data) {
        Arrays.stream(text.split(SPLITTER)).forEach(token -> {
            Map<File, Integer> occurrences = data.get(token);
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
                data.put(token, occurrence);
            }
        });
    }

    @Override
    public Set<String> tokenizeQuery(String text) {
        return Arrays.stream(text.split(SPLITTER)).collect(Collectors.toSet());
    }

    @Override
    public LinkedList<String> develop(String token) {
        return new LinkedList<>(Collections.singletonList(token));
    }
}
