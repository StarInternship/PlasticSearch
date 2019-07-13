package controller;

import models.search.Search;
import models.tokenizer.FuzzySearchTokenizer;
import models.tokenizer.NgramSearchTokenizer;
import view.FileImporter;
import view.View;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Controller {
    private static final int NGRAM_MIN = 3;
    private static final int NGRAM_MAX = 30;
    private View view = new View();
    private final String PATH;
    private final NgramSearchTokenizer ngramSearchTokenizer = new NgramSearchTokenizer();
    private final FuzzySearchTokenizer fuzzySearchTokenizer = new FuzzySearchTokenizer();

    public Controller(String PATH) {
        this.PATH = PATH;
    }

    public void main() throws IOException {
        FileImporter fileImporter = new FileImporter(PATH);

        Search search = new Search();

        Map<File, String> filesData = fileImporter.readFiles();

        long currentTime = System.currentTimeMillis();

        filesData.forEach(((file, text) -> {
            String cleanText = ngramSearchTokenizer.cleanText(text);
            search.insertNgram(
                    file, ngramSearchTokenizer.tokenize(
                            cleanText, NGRAM_MIN, NGRAM_MAX
                    )
            );
            search.insertExact(file, ngramSearchTokenizer.tokenize(cleanText));
        }
        ));

        System.out.println("preprocess duration: " + (System.currentTimeMillis() - currentTime) + "ms");

        while (true) {
            String query = view.readNextLine();
            Set<File> result = new HashSet<>();

            currentTime = System.currentTimeMillis();
            Set<String> queryTokens = ngramSearchTokenizer.tokenize(ngramSearchTokenizer.cleanText(query));
            System.out.println("query process duration: " + (System.currentTimeMillis() - currentTime) + "ms");

            currentTime = System.currentTimeMillis();
            search.setQueryTokenizer(ngramSearchTokenizer);
            search.search(new ArrayList<>(queryTokens), 0, null, result);
            System.out.println("ngram search duration: " + (System.currentTimeMillis() - currentTime) + "ms");

            currentTime = System.currentTimeMillis();
            search.setQueryTokenizer(fuzzySearchTokenizer);
            search.search(new ArrayList<>(queryTokens), 0, null, result);
            System.out.println("fuzzy search duration: " + (System.currentTimeMillis() - currentTime) + "ms");

            view.showResult(result);
        }
    }
}
