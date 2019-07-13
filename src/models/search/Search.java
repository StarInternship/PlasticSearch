package models.search;

import models.tokenizer.Tokenizer;

import java.io.File;
import java.util.*;

public class Search {
    private final Map<File, Set<String>> ngramTokensList = new HashMap<>();
    private final Map<File, Set<String>> exactTokensList = new HashMap<>();
    private final Map<ListType, Map<File, Set<String>>> lists = new HashMap<>();
    private Map<File, Set<String>> currentList;
    private Tokenizer queryTokenizer;

    {
        lists.put(ListType.EXACT, exactTokensList);
        lists.put(ListType.NGRAM, ngramTokensList);
        lists.put(ListType.FUZZY, exactTokensList);
    }

    public void insertNgram(File file, Set<String> tokens) {
        ngramTokensList.put(file, tokens);
    }

    public void insertExact(File file, Set<String> tokens) {
        exactTokensList.put(file, tokens);
    }

    public void setQueryTokenizer(Tokenizer queryTokenizer, ListType type) {
        this.queryTokenizer = queryTokenizer;
        currentList = lists.get(type);
    }

    public void search(ArrayList<String> queryTokens, int index, LinkedList<File> files, Set<File> result) {
        if (files == null) {
            files = new LinkedList<>(currentList.keySet());
        }
        if (files.isEmpty()) {
            return;
        }
        if (queryTokens.size() == index) {
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