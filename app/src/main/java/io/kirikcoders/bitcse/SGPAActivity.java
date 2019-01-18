package io.kirikcoders.bitcse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class SGPAActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgpa);
        WebView webView=findViewById(R.id.sgpa_wv);
        webView.loadUrl("http://www.vtusgpacalculator.online/");
    }
}
