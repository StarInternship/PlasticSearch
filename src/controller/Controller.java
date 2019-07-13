package controller;

import models.analyzer.ExactSearchAnalyzer;
import models.search.Search;
import models.tokenizer.ExactSearchTokenizer;
import view.FileView;
import view.View;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Controller {
    private View view = new View();
    private FileView fileView;
    private final String PATH;

    public Controller(String PATH) {
        this.PATH = PATH;
    }

    public void main() throws IOException {
        fileView = new FileView(PATH);
        Search exactSearch = new Search(new ExactSearchAnalyzer(), new ExactSearchTokenizer());
        exactSearch.preprocess(fileView.readFiles());

        while (true) {
            String query = view.readNextLine();
            Set<File> result = new HashSet<>();

            exactSearch.search(query, result);

            view.showResult(result);
        }
    }
}
