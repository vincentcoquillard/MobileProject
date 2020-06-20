package fr.cqfvideo.publicApisapp.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import fr.cqfvideo.publicApisapp.Constants;
import fr.cqfvideo.publicApisapp.R;
import fr.cqfvideo.publicApisapp.Singletons;
import fr.cqfvideo.publicApisapp.presentation.model.publicApi;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;
    private TextView txtDescription;
    private TextView txtCategory;
    private TextView txtLink;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        txtDetail = findViewById(R.id.detail_txt);
        txtDescription = findViewById(R.id.description_txt);
        txtCategory = findViewById(R.id.category_txt);

        txtLink = (TextView)findViewById(R.id.link_txt);


        Intent intent = getIntent();
        String publicApiJson = intent.getStringExtra(Constants.publicApiKey);
        publicApi publicApi = Singletons.getGson().fromJson(publicApiJson, publicApi.class);
        showDetail(publicApi);
    }

    private void showDetail(publicApi publicApi) {
        txtDetail.setText(publicApi.getAPI());
        txtDescription.setText(publicApi.getDescription());
        txtCategory.setText(publicApi.getCategory());
        Spanned policy = Html.fromHtml(publicApi.getLink());
        txtLink.setText(policy);
        txtLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
