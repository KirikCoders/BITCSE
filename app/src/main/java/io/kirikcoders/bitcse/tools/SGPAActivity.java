package io.kirikcoders.bitcse.tools;
/**
 * Created by Akash on 18-Jan-19.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import io.kirikcoders.bitcse.R;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.google.android.material.snackbar.Snackbar;

public class SGPAActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgpa);
        WebView webView = findViewById(R.id.sgpa_wv);
        constraintLayout=findViewById(R.id.sgpa_cl);
        if(isNetworkConnected()==false)
        {
            webView.setVisibility(webView.GONE);
            Snackbar.make(constraintLayout,"Check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", v -> {
                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }).show();
        }
        webView.loadUrl("https://datron.github.io/sgpa/");
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
