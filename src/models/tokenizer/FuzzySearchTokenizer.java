package models.tokenizer;

import java.util.*;
import java.util.function.Consumer;

public class FuzzySearchTokenizer implements Tokenizer {
    private static List<Character> characters = new ArrayList<>();
    private Consumer<String> insert = this::insert;
    private Consumer<String> delete = this::delete;
    private Consumer<String> substitute = this::substitute;
    private ArrayList<Consumer> consumers = new ArrayList<>();
    static {
        for (char c = 'a'; c <= 'z'; c++) {
            characters.add(c);
        }
        for (char c = '0'; c < '9'; c++) {
            characters.add(c);
        }
    }

    public FuzzySearchTokenizer(){
        consumers.add(insert);
        consumers.add(substitute);
        consumers.add(delete);
    }

    @Override
    public LinkedList<String> develop(String token) {
        LinkedList<String> developedTokens = new LinkedList<>(Collections.singletonList(token));
        for (int i = 0; i < 2; i++) {
            LinkedList<String> tempDevelopedTokens = new LinkedList<>();
            for (String tok : developedTokens) {
                tempDevelopedTokens.addAll(insert(tok));
                tempDevelopedTokens.addAll(delete(tok));
                tempDevelopedTokens.addAll(substitute(tok));
            }
            developedTokens.remove(token);
            developedTokens.addAll(tempDevelopedTokens);
        }
        return developedTokens;
    }

    private LinkedList<String> delete(String token) {
        LinkedList<String> deletes = new LinkedList<>();

        for (int i = 0; i < token.length(); i++) {
            deletes.add(token.substring(0, i) + token.substring(i + 1));
        }
        return deletes;
    }

    private LinkedList<String> insert(String token) {
        LinkedList<String> inserts = new LinkedList<>();

        for (int i = 0; i <= token.length(); i++) {
            for (char c : characters) {
                if (i < token.length() && c == token.charAt(i)) continue;
                inserts.add(token.substring(0, i) + c + token.substring(i));
            }
        }
        return inserts;
    }

    private LinkedList<String> substitute(String token) {
        LinkedList<String> substitutes = new LinkedList<>();

        for (int i = 0; i < token.length(); i++) {
            for (char c : characters) {
                if (c == token.charAt(i)) continue;
                substitutes.add(token.substring(0, i) + c + token.substring(i + 1));
            }
        }
        return substitutes;
    }
}
