package models.search;

import models.tokenizer.Tokenizer;

import java.io.File;
import java.util.*;

public class Search {
    private final Map<File, Set<String>> tokensList = new HashMap<>();
    private Tokenizer queryTokenizer;

    public void insert(File file, Set<String> tokens) {
        tokensList.put(file, tokens);
    }

    public void setQueryTokenizer(Tokenizer queryTokenizer) {
        this.queryTokenizer = queryTokenizer;
    }

    public void search(ArrayList<String> queryTokens, int index, LinkedList<File> files, Set<File> result) {
        if (files == null) {
            files = new LinkedList<>(tokensList.keySet());
        }
        if (files.isEmpty()) {
            return;
        }
        if (index == queryTokens.size()) {
            result.addAll(files);
            return;
        }

        String currentToken = queryTokens.get(index);
        LinkedList<String> developedTokens = queryTokenizer.develop(currentToken);

        for (String token : developedTokens) {
            LinkedList<File> newFiles = new LinkedList<>();

            for (File file : files) {
                if (tokensList.get(file).contains(token)) {
                    newFiles.add(file);
                }
            }

            search(queryTokens, index + 1, newFiles, result);
        }
        files.clear();
    }
}