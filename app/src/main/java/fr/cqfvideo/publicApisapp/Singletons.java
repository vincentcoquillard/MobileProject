package fr.cqfvideo.publicApisapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.cqfvideo.publicApisapp.data.publicApiAPI;
import fr.cqfvideo.publicApisapp.presentation.model.publicApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static publicApiAPI publicApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder().setLenient().create();
        }
        return gsonInstance;
    }

    public static publicApiAPI getPublicApi(){
        if(publicApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            publicApiInstance = retrofit.create(publicApiAPI.class);
        }
        return publicApiInstance;
    }

    public static SharedPreferences getsharedPreferences(Context context) {
        if (sharedPreferencesInstance == null) {
            sharedPreferencesInstance = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
