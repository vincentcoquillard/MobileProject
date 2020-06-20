package fr.cqfvideo.publicApisapp.data;

import fr.cqfvideo.publicApisapp.presentation.model.RestpublicApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface publicApiAPI {
    @GET("/entries")
    Call<RestpublicApiResponse> getpublicApiResponse();
}
