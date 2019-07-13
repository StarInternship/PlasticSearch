package models.search;

import models.tokenizer.NgramSearchTokenizer;
import models.tokenizer.Tokenizer;

import java.io.File;
import java.util.*;

public class Search {
    private final Map<File, Set<String>> ngramTokensList = new HashMap<>();
    private final Map<File, Set<String>> exactTokensList = new HashMap<>();
    private Map<File, Set<String>> currentList;
    private Tokenizer queryTokenizer;

    public void insertNgram(File file, Set<String> tokens) {
        ngramTokensList.put(file, tokens);
    }

    public void insertExact(File file, Set<String> tokens) {
        ngramTokensList.get(file).addAll(tokens);
        exactTokensList.put(file, tokens);
    }

    public void setQueryTokenizer(Tokenizer queryTokenizer) {
        this.queryTokenizer = queryTokenizer;

        if (queryTokenizer instanceof NgramSearchTokenizer) {
            currentList = ngramTokensList;
        } else {
            currentList = exactTokensList;
        }
    }

    public void search(ArrayList<String> queryTokens, int index, LinkedList<File> files, Set<File> result) {
        if (files == null) {
            files = new LinkedList<>(currentList.keySet());
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
                if (currentList.get(file).contains(token)) {
                    newFiles.add(file);
                }
            }

            search(queryTokens, index + 1, newFiles, result);
        }
        files.clear();
    }
}