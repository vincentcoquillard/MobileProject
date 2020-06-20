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
import fr.cqfvideo.publicApisapp.presentation.controller.MainController;
import fr.cqfvideo.publicApisapp.presentation.model.RestpublicApiResponse;
import fr.cqfvideo.publicApisapp.presentation.model.publicApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(
                this,
                new GsonBuilder().setLenient().create(),
                getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        );
        controller.onStart();

    }

    public void showList(List <publicApi> publicApiList) {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        mAdapter = new ListAdapter(publicApiList);
        recyclerView.setAdapter(mAdapter);
    }


    public void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
