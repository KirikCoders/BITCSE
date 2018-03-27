package com.bit.cse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.Keep;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;




import easyFonts.EasyFonts;
import liquidButton.LiquidButton;

@Keep
public class FirstScreen extends AppCompatActivity {
    LiquidButton splashToAboutCSE,splashToSyllabus,splashToFaculty,splashToAboutDev;
    LiquidButton splashhToTT;
    TextView textAboutCse,textAboutDev,textSyllabus,textFacultyData,textTimeTable;
    CleverTapManager cleverTapManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        textAboutCse=(TextView)findViewById(R.id.textAboutCse);
        textAboutDev=(TextView)findViewById(R.id.textAboutDev);
        textFacultyData=(TextView)findViewById(R.id.textFacultyData);
        textSyllabus=(TextView)findViewById(R.id.textSyllabus);
        textTimeTable=(TextView)findViewById(R.id.textTimeTable);


        textAboutCse.setTypeface(EasyFonts.androidNationBold(this));
        textAboutDev.setTypeface(EasyFonts.androidNationBold(this));
        textFacultyData.setTypeface(EasyFonts.androidNationBold(this));
        textSyllabus.setTypeface(EasyFonts.androidNationBold(this));
        textTimeTable.setTypeface(EasyFonts.androidNationBold(this));
        cleverTapManager=new CleverTapManager(this);


        SharedPreferences sp = getSharedPreferences("shared", Activity.MODE_PRIVATE);
       boolean firstRun = sp.getBoolean("firstRun", true);
        Log.d("firstrun",firstRun+"");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#212121"));
        }

        splashhToTT=(LiquidButton) findViewById(R.id.splashToTimeTable);
        splashhToTT.setColor("#69D8EC");
        splashhToTT.setFillAfter(true);
        splashhToTT.setAutoPlay(true);
        splashhToTT.startPour();
        splashhToTT.setEnabled(false);
        splashhToTT.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                textTimeTable.setVisibility(View.VISIBLE);

                splashhToTT.setEnabled(true);
            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });

        splashToSyllabus=(LiquidButton) findViewById(R.id.splashToSyllabus);
        splashToSyllabus.setColor("#F92772");
        splashToSyllabus.setFillAfter(true);
        splashToSyllabus.setAutoPlay(true);
        splashToSyllabus.startPour();
        splashToSyllabus.setEnabled(false);
        splashToSyllabus.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                textSyllabus.setVisibility(View.VISIBLE);
                splashToSyllabus.setEnabled(true);
            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });

        splashToAboutCSE=(LiquidButton)findViewById(R.id.splashToAboutCSE);
        splashToAboutCSE.setColor("#A7E22E");
        splashToAboutCSE.setFillAfter(true);
        splashToAboutCSE.setAutoPlay(true);
        splashToAboutCSE.startPour();
        splashToAboutCSE.setEnabled(false);
        splashToAboutCSE.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                textAboutCse.setVisibility(View.VISIBLE);
                splashToAboutCSE.setEnabled(true);
            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });


        splashToFaculty=(LiquidButton)findViewById(R.id.splashToFacultyData);
        splashToFaculty.setColor("#FE9721");
        splashToFaculty.setFillAfter(true);
        splashToFaculty.setAutoPlay(true);
        splashToFaculty.startPour();
        splashToFaculty.setEnabled(false);
        splashToFaculty.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                textFacultyData.setVisibility(View.VISIBLE);
                splashToFaculty.setEnabled(true);
            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });


        splashToAboutDev=(LiquidButton)findViewById(R.id.splashToAboutDev);
        splashToAboutDev.setColor("#EEFFFFFF");
        splashToAboutDev.setFillAfter(true);
        splashToAboutDev.setAutoPlay(true);
        splashToAboutDev.startPour();
        splashToAboutDev.setEnabled(false);
        splashToAboutDev.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
                textAboutDev.setVisibility(View.VISIBLE);
                splashToAboutDev.setEnabled(true);
            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });
        splashhToTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapManager.firstScreenClicks(CleverTapManager.timeTable);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        splashToAboutCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable(getApplicationContext()))

                startActivity(new Intent(getApplicationContext(),AboutCSE.class));
                else
                    Toast.makeText(getApplicationContext(),"Please connect to internet",Toast.LENGTH_LONG).show();
            }
        });

        splashToSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable(getApplicationContext())) {
                    cleverTapManager.firstScreenClicks(CleverTapManager.docs);
                    startActivity(new Intent(getApplicationContext(), WebViewManual.class));
                }
                else
                    Toast.makeText(getApplicationContext(),"Please connect to internet connection",Toast.LENGTH_LONG).show();
            }
        });

        splashToAboutDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapManager.firstScreenClicks(CleverTapManager.developers);
                startActivity(new Intent(getApplicationContext(),aboutDevelopers.class));
            }
        });
        splashToFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapManager.firstScreenClicks(CleverTapManager.facultyData);
                startActivity(new Intent(getApplicationContext(),FacultyData.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    public boolean isInternetAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }
}
