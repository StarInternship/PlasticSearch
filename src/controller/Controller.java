package controller;

import models.analyzer.ExactSearchAnalyzer;
import models.search.Search;
import models.tokenizer.ExactSearchTokenizer;
import view.FileView;
import view.View;

import java.io.IOException;

public class Controller {
    private View view = new View();
    private FileView fileView;
    private final String PATH;

    public Controller(String PATH) {
        this.PATH = PATH;
    }

    public void main() throws IOException {
        fileView = new FileView(PATH);
        Search search = new Search(new ExactSearchAnalyzer(), new ExactSearchTokenizer());
        search.preprocess(fileView.readFiles());
        while (true) {
            view.showResult(search.search(view.readNextLine()));
        }
    }
}
