package io.kirikcoders.bitcse.Settings;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.Constants;

import android.content.Context;
import android.content.SharedPreferences;
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
        SharedPreferences mPrefs = getBaseContext().getSharedPreferences(Constants.USER_PREFERENCE_FILE, Context.MODE_PRIVATE);
        int mode = mPrefs.getInt("MODE",-1);
        getDelegate().setLocalNightMode(mode);
        setContentView(R.layout.activity_feedback);
        webView=findViewById(R.id.wv_feedback);
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfxgJVJX8XIo7TlDsEU28dmlCFj0YCpotbdoxOEqCJg4WXBYw/viewform");
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
