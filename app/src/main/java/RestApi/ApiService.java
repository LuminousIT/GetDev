package RestApi;

import com.example.samth.getdevdata.GetDevDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/search/users?q=language:java+location:lagos")
    Call<GetDevDataResponse> getDataItems();
}
