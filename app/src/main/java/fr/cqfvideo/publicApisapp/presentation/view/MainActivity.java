package fr.cqfvideo.publicApisapp.presentation.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import fr.cqfvideo.publicApisapp.R;
import fr.cqfvideo.publicApisapp.Singletons;
import fr.cqfvideo.publicApisapp.presentation.controller.MainController;
import fr.cqfvideo.publicApisapp.presentation.model.publicApi;

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
                Singletons.getGson(),
                Singletons.getsharedPreferences(getApplicationContext())
        );
        controller.onStart();

    }

    public void showList(List <publicApi> publicApiList) {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        mAdapter = new ListAdapter(publicApiList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(publicApi item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    public void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(publicApi publicApi) {
        Toast.makeText(getApplicationContext(), "Do click", Toast.LENGTH_SHORT).show();
    }
}
