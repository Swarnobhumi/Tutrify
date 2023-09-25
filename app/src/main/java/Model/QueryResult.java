package Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryResult {
    @SerializedName("search")
    private List<SearchResult> searchResults;

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }
}