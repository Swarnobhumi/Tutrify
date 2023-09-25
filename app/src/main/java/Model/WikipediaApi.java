package Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaApi {
    @GET("w/api.php?action=query&format=json&list=search")
    Call<ApiResponse> search(@Query("srsearch") String query);
}
