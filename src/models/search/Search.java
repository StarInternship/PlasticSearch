package models.search;

import models.analyzer.NgramSearchAnalyzer;
import models.tokenizer.NgramSearchTokenizer;

import java.io.File;
import java.util.*;

public class Search {
    private final NgramSearchAnalyzer queryAnalyzer = new NgramSearchAnalyzer();
    private final NgramSearchTokenizer queryTokenizer = new NgramSearchTokenizer();
    private final Map<File, Set<String>> tokensList = new HashMap<>();

    public void insert(File file, Set<String> tokens) {
        tokensList.put(file, tokens);
    }

    public void search(String query, Set<File> result) {
        Set<String> queryTokens = queryTokenizer.tokenize(queryAnalyzer.cleanText(query));

        tokensList.forEach(((file, dataTokens) -> {
            for (String queryToken : queryTokens) {
                if (!dataTokens.contains(queryToken)) return;
            }
            result.add(file);
        }));
    }
}