package controller;

import models.search.ListType;
import models.search.Search;
import models.tokenizer.ExactSearchTokenizer;
import models.tokenizer.FuzzySearchTokenizer;
import models.tokenizer.NgramSearchTokenizer;
import models.tokenizer.Tokenizer;
import view.FileImporter;
import view.View;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Controller {
    private final View view = new View();
    private final String PATH;
    private final Tokenizer exactSearchTokenizer = new ExactSearchTokenizer();
    private final Tokenizer ngramSearchTokenizer = new NgramSearchTokenizer();
    private final Tokenizer fuzzySearchTokenizer = new FuzzySearchTokenizer();
    private final Search search = new Search();
    private Set<File> result;
    private Set<String> queryTokens;
    private long currentTime;

    public Controller(String PATH) {
        this.PATH = PATH;
    }

    public void main() throws IOException {
        preprocess();

        while (true) {
            String query = view.readNextLine();
            result = new HashSet<>();

            queryProcess(query);

            doSearch(ListType.EXACT, exactSearchTokenizer);
            doSearch(ListType.NGRAM, ngramSearchTokenizer);
            doSearch(ListType.FUZZY, fuzzySearchTokenizer);

            view.showResult(result);
        }
    }

    private void preprocess() throws IOException {
        FileImporter fileImporter = new FileImporter(PATH);

        Map<File, String> filesData = fileImporter.readFiles();

        currentTime = System.currentTimeMillis();

        filesData.forEach(((file, text) -> {
            String cleanText = ngramSearchTokenizer.cleanText(text);
            search.insertNgram(file, ngramSearchTokenizer.tokenize(cleanText));
            search.insertExact(file, exactSearchTokenizer.tokenize(cleanText));
        }));

        System.out.println("preprocess duration: " + (System.currentTimeMillis() - currentTime) + "ms");
    }

    private void queryProcess(String query) {
        currentTime = System.currentTimeMillis();

        queryTokens = exactSearchTokenizer.tokenize(ngramSearchTokenizer.cleanText(query));

        System.out.println("query process duration: " + (System.currentTimeMillis() - currentTime) + "ms");
    }

    private void doSearch(ListType listType, Tokenizer tokenizer) {
        currentTime = System.currentTimeMillis();

        search.setQueryTokenizer(tokenizer, listType);
        search.search(new ArrayList<>(queryTokens), 0, null, result);

        System.out.println(listType + " search duration: " + (System.currentTimeMillis() - currentTime) + "ms");
    }
}
