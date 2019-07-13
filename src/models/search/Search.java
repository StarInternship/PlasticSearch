package models.search;

import java.util.List;

public interface Search {

    void preprocess(List<String> list);

    void search(String query);
}
