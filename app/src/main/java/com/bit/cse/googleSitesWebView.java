package com.bit.cse;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class googleSitesWebView extends AppCompatActivity implements View.OnClickListener {

    ProgressBar bar;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sites_web_view);
        bar=(ProgressBar)findViewById(R.id.marker_progress);
       bar.setVisibility(View.VISIBLE);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://bitcse-90b07.firebaseio.com/");
        Intent i=getIntent();
        //String site=i.getStringExtra("site");
     /*   webviewBackButton=(Button)findViewById(R.id.webviewBackButton);
        webviewForwardButton=(Button)findViewById(R.id.webviewForwardButton);
        webviewReloadButton=(Button)findViewById(R.id.webviewReloadButton);
     */
        String fid=i.getStringExtra("fid");
        databaseReference.child("facultyLinks").child(fid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               bar.setVisibility(View.INVISIBLE);
                String site=dataSnapshot.getValue(String.class);
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    getApplicationContext().startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    getApplicationContext().startActivity(intent);
                    finish();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


   /*     webviewReloadButton.setOnClickListener(this);
        webviewForwardButton.setOnClickListener(this);
        webviewBackButton.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View view) {
      /*  switch (view.getId())
        {
            case R.id.webviewBackButton:
                if(webView.canGoBack())
                    webView.goBack();
                break;
            case R.id.webviewForwardButton:
                if(webView.canGoForward())
                    webView.goForward();
                break;
            case R.id.webviewReloadButton:
                webView.reload();
        }*/
    }

    @Override
    public void onBackPressed() {

    }
}
