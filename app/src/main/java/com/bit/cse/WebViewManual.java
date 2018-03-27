package com.bit.cse;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WebViewManual extends AppCompatActivity {
    DatabaseReference databaseReference;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_manual);

        webView=(WebView) findViewById(R.id.webViewManual);
        webView.setWebViewClient(new CustomWebViewClient());

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://bitcse-90b07.firebaseio.com/");
        DatabaseReference about=databaseReference.child("sites").child("docs");

        about.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                webView.loadUrl(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
            finish();
    }
}
