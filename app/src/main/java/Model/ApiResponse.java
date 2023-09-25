package Model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("query")
    private QueryResult queryResult;

    public QueryResult getQueryResult() {
        return queryResult;
    }
}



