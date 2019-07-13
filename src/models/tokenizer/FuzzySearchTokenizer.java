package models.tokenizer;

import java.util.*;

public class FuzzySearchTokenizer implements Tokenizer {
    private static final int FUZZINESS = 1;
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
    public Set<String> tokenize(String text) {
        return new HashSet<>();
    }

    @Override
    public LinkedList<String> develop(String token) {
        LinkedList<String> developedTokens = new LinkedList<>(Collections.singletonList(token));
        for (int i = 0; i < FUZZINESS; i++) {
            LinkedList<String> tempDevelopedTokens = new LinkedList<>();
            for (String tok : developedTokens) {
                tempDevelopedTokens.addAll(insert(tok));
                tempDevelopedTokens.addAll(delete(tok));
                tempDevelopedTokens.addAll(substitute(tok));
            }
            developedTokens.addAll(tempDevelopedTokens);
            developedTokens.remove(token);
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
