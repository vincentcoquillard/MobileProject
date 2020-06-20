package fr.cqfvideo.publicApisapp.presentation.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import fr.cqfvideo.publicApisapp.Constants;
import fr.cqfvideo.publicApisapp.ListAdapter;
import fr.cqfvideo.publicApisapp.R;
import fr.cqfvideo.publicApisapp.data.publicApiAPI;
import fr.cqfvideo.publicApisapp.presentation.model.RestpublicApiResponse;
import fr.cqfvideo.publicApisapp.presentation.model.publicApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String BASE_URL = "https://api.publicapis.org/";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List <publicApi> publicApiList = getDataFromCache();
        if (publicApiList != null){
            showList(publicApiList);
        }else{
            makeApiCall();
        }
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

    private void showList(List <publicApi> publicApiList) {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        mAdapter = new ListAdapter(publicApiList);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final publicApiAPI publicApiAPI = retrofit.create(publicApiAPI.class);

        Call<RestpublicApiResponse> call = publicApiAPI.getpublicApiResponse();
        call.enqueue(new Callback<RestpublicApiResponse>() {
            @Override
            public void onResponse(Call<RestpublicApiResponse> call, Response<RestpublicApiResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<publicApi> publicApiList = response.body().getEntries();
                    saveList(publicApiList);
                    showList(publicApiList);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestpublicApiResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<publicApi> publicApiList) {
        String jsonString = gson.toJson(publicApiList);

        sharedPreferences
                .edit()
                .putString(Constants.PUBLICAPI_LIST, jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
