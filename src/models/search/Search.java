package models.search;

import models.analyzer.Analyzer;
import models.tokenizer.Tokenizer;

import java.io.File;
import java.util.*;

public class Search {
    private final Analyzer analyzer;
    private final Tokenizer tokenizer;
    private final Map<File, Set<String>> tokensList = new HashMap<>();

    public Search(Analyzer analyzer, Tokenizer tokenizer) {
        this.analyzer = analyzer;
        this.tokenizer = tokenizer;
    }

    public void preprocess(Map<File, String> list) {
        list.forEach(
                (file, text) -> tokensList.put(file, tokenizer.tokenize(analyzer.cleanText(text)))
        );
    }

    public List<File> search(String query) {
        List<File> matchedFiles = new ArrayList<>();
        Set<String> queryTokens = tokenizer.tokenize(analyzer.cleanText(query));

        tokensList.forEach(((file, dataTokens) -> {
            for (String queryToken : queryTokens) {
                if (!dataTokens.contains(queryToken)) return;
            }
            matchedFiles.add(file);
        }));

        return matchedFiles;
    }
}