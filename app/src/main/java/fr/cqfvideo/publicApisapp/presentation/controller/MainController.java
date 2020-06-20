package fr.cqfvideo.publicApisapp.presentation.controller;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import fr.cqfvideo.publicApisapp.Constants;
import fr.cqfvideo.publicApisapp.Singletons;
import fr.cqfvideo.publicApisapp.presentation.model.RestpublicApiResponse;
import fr.cqfvideo.publicApisapp.presentation.model.publicApi;
import fr.cqfvideo.publicApisapp.presentation.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity view, Gson gson, SharedPreferences sharedPreferences){
        this.view = view;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    private List<publicApi> getDataFromCache() {
        String jsonpublicApis = sharedPreferences.getString(Constants.PUBLICAPI_LIST, null);

        if(jsonpublicApis == null){
            return null;
        }else{
            Type listType = new TypeToken<List<publicApi>>(){}.getType();
            return gson.fromJson(jsonpublicApis, listType);
        }
    }

    private void makeApiCall(){
        Call<RestpublicApiResponse> call = Singletons.getPublicApi().getpublicApiResponse();
        call.enqueue(new Callback<RestpublicApiResponse>() {
            @Override
            public void onResponse(Call<RestpublicApiResponse> call, Response<RestpublicApiResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<publicApi> publicApiList = response.body().getEntries();
                    saveList(publicApiList);
                    view.showList(publicApiList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestpublicApiResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<publicApi> publicApiList) {
        String jsonString = gson.toJson(publicApiList);

        sharedPreferences
                .edit()
                .putString(Constants.PUBLICAPI_LIST, jsonString)
                .apply();
    }

    public void onStart(){
        List<publicApi> publicApiList = getDataFromCache();
        if (publicApiList != null){
            view.showList(publicApiList);
        }else{
            makeApiCall();
        }
    }

    public void onItemClick(publicApi publicApi){
        view.navigateToDetails(publicApi);
    }
}
