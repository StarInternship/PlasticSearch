package models.tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FuzzySearchTokenizer implements Tokenizer {
    private static List<Character> characters = new ArrayList<>();

    static {
        for (char c = 'a'; c <= 'z'; c++) {
            characters.add(c);
        }
        for (char c = '0'; c < '9'; c++) {
            characters.add(c);
        }
    }

    @Override
    public LinkedList<String> develop(String token) {
        LinkedList<String> developedTokens = new LinkedList<>(Collections.singletonList(token));

        developedTokens.addAll(insert(token));
        developedTokens.addAll(delete(token));
        developedTokens.addAll(substitute(token));

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
        return null;
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
