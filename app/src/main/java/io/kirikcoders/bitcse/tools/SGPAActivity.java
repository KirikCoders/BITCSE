package io.kirikcoders.bitcse.tools;
/**
 * Created by Akash on 18-Jan-19.
 */

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import io.kirikcoders.bitcse.R;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.material.snackbar.Snackbar;

public class SGPAActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.loadUrl("http://resnal.ml");
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
