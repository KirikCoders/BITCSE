package com.bit.cse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutCSE extends AppCompatActivity {

    DatabaseReference databaseReference;
    WebView aboutCSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cse);


        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://bitcse-90b07.firebaseio.com/");
        DatabaseReference about=databaseReference.child("sites").child("about");
        aboutCSE=(WebView) findViewById(R.id.webViewAboutCSE);
        about.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                aboutCSE.loadUrl(dataSnapshot.getValue(String.class));
                aboutCSE.setWebViewClient(new CustomWebViewClient());
                aboutCSE.getSettings().setJavaScriptEnabled(true);
                aboutCSE.getSettings().setLoadWithOverviewMode(true);
                aboutCSE.getSettings().setUseWideViewPort(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    @Override
    public void onBackPressed() {
        if(aboutCSE.canGoBack())
            aboutCSE.goBack();
        else
            finish();
    }
}
