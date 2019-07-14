package models.search;

import models.tokenizer.ExactSearchTokenizer;
import models.tokenizer.FuzzySearchTokenizer;
import models.tokenizer.NgramSearchTokenizer;
import models.tokenizer.Tokenizer;

import java.io.File;
import java.util.*;

public class Search {
    private final Map<String, Map<File, Integer>> ngramData = new HashMap<>();
    private final Map<String, Map<File, Integer>> exactData = new HashMap<>();
    private final Tokenizer exactSearchTokenizer = new ExactSearchTokenizer();
    private final Tokenizer ngramSearchTokenizer = new NgramSearchTokenizer();
    private final Tokenizer fuzzySearchTokenizer = new FuzzySearchTokenizer();

    public void search(ArrayList<String> queryTokens, Set<File> result) {
        boolean first = true;
        for (String queryToken : queryTokens) {
            Set<File> foundFiles = new HashSet<>();

            findFiles(queryToken, foundFiles, exactSearchTokenizer, exactData);
            findFiles(queryToken, foundFiles, ngramSearchTokenizer, ngramData);
            findFiles(queryToken, foundFiles, fuzzySearchTokenizer, exactData);

            if (first) {
                result.addAll(foundFiles);
                first = false;
            } else {
                share(result, foundFiles);
            }
        }
    }

    private void findFiles(String queryToken, Set<File> foundFiles, Tokenizer queryTokenizer, Map<String, Map<File, Integer>> currentData) {
        LinkedList<String> developedTokens = queryTokenizer.develop(queryToken);

        for (String developedToken : developedTokens) {
            Map<File, Integer> occurrences = currentData.get(developedToken);
            if (occurrences != null) {
                foundFiles.addAll(occurrences.keySet());
            }
        }
    }

    private void share(Set<File> result, Set<File> foundFiles) {
        result.retainAll(foundFiles);
    }

    public Map<String, Map<File, Integer>> getNgramData() {
        return ngramData;
    }

    public Map<String, Map<File, Integer>> getExactData() {
        return exactData;
    }
}