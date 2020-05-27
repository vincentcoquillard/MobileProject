package fr.cqfvideo.publicApisapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface publicApiAPI {
    @GET("/entries")
    Call<RestpublicApiResponse> getpublicApiResponse();
}
