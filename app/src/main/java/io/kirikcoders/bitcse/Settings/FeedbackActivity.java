package io.kirikcoders.bitcse.Settings;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;

import android.os.Bundle;
import android.webkit.WebView;
/**
 * Created by Akash on 12-Feb-19.
 */
public class FeedbackActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        webView=findViewById(R.id.wv_feedback);
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfxgJVJX8XIo7TlDsEU28dmlCFj0YCpotbdoxOEqCJg4WXBYw/viewform");
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
