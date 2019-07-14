package controller;

import models.search.Search;
import models.tokenizer.ExactSearchTokenizer;
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
    private final Search search = new Search();
    private Set<File> result;
    private Set<String> queryTokens;
    private double currentTime;

    public Controller(String PATH) {
        this.PATH = PATH;
    }

    public void main() throws IOException {
        preprocess();

        while (true) {
            String query = view.readNextLine();
            result = new HashSet<>();

            queryProcess(query);
            doSearch();
            view.showResult(result);
        }
    }

    private void preprocess() throws IOException {
        FileImporter fileImporter = new FileImporter(PATH);

        Map<File, String> filesData = fileImporter.readFiles();

        currentTime = System.nanoTime() / 1000000.0;

        filesData.forEach(((file, text) -> {
            String cleanText = ngramSearchTokenizer.cleanText(text);
            ngramSearchTokenizer.tokenizeData(file, cleanText, search.getNgramData());
            exactSearchTokenizer.tokenizeData(file, cleanText, search.getExactData());
        }));

        System.out.println("preprocess duration: " + getTimeDifference() + "ms");
    }

    private void queryProcess(String query) {
        currentTime = System.nanoTime() / 1000000.0;

        queryTokens = exactSearchTokenizer.tokenizeQuery(ngramSearchTokenizer.cleanText(query));

        System.out.println("query process duration: " + getTimeDifference() + "ms");
    }

    private void doSearch() {
        currentTime = System.nanoTime() / 1000000.0;

        search.search(new ArrayList<>(queryTokens),  result);

        System.out.println("search duration: " + getTimeDifference() + "ms");
    }

    private double getTimeDifference() {
        return System.nanoTime() / 1000000.0 - currentTime;
    }
}
