package fr.cqfvideo.publicApisapp.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fr.cqfvideo.publicApisapp.Constants;
import fr.cqfvideo.publicApisapp.R;
import fr.cqfvideo.publicApisapp.Singletons;
import fr.cqfvideo.publicApisapp.presentation.model.publicApi;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        txtDetail = findViewById(R.id.detail_txt);
        Intent intent = getIntent();
        String publicApiJson = intent.getStringExtra(Constants.publicApiKey);
        publicApi publicApi = Singletons.getGson().fromJson(publicApiJson, publicApi.class);
        showDetail(publicApi);
    }

    private void showDetail(publicApi publicApi) {
        txtDetail.setText(publicApi.getAPI());
    }
}
