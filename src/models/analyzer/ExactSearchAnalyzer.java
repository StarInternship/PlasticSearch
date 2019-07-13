package models.analyzer;

public class ExactSearchAnalyzer implements Analyzer {
    @Override
    public String cleanText(String text) {
        return text.toLowerCase();
    }
}
