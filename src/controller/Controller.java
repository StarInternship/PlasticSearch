package controller;

import models.analyzer.NgramSearchAnalyzer;
import models.search.Search;
import models.tokenizer.NgramSearchTokenizer;
import view.FileImporter;
import view.View;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Controller {
    private static final int NGRAM_MIN = 3;
    private static final int NGRAM_MAX = 100;
    private View view = new View();
    private final String PATH;
    private final NgramSearchTokenizer ngramSearchTokenizer = new NgramSearchTokenizer();
    private final NgramSearchAnalyzer ngramSearchAnalyzer = new NgramSearchAnalyzer();

    public Controller(String PATH) {
        this.PATH = PATH;
    }

    public void main() throws IOException {
        FileImporter fileImporter = new FileImporter(PATH);

        Search search = new Search();

        Map<File, String> filesData = fileImporter.readFiles();

        filesData.forEach(((file, text) ->
                search.insert(
                        file, ngramSearchTokenizer.tokenize(
                                ngramSearchAnalyzer.cleanText(text), NGRAM_MIN, NGRAM_MAX
                        )
                )
        ));

        while (true) {
            String query = view.readNextLine();
            Set<File> result = new HashSet<>();

            search.search(query, result);

            view.showResult(result);
        }
    }
}
