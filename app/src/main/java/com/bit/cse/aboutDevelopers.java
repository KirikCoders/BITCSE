package com.bit.cse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;




import easyFonts.EasyFonts;


public class aboutDevelopers extends AppCompatActivity {


    TextView name1,name2,name3,name4,des1,des2,des3,des4,mentors,name5,name6,des5,des6,topText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developers);

        name1=(TextView)findViewById(R.id.aboutDevName1);
        des1=(TextView)findViewById(R.id.aboutDevdes1);
        name2=(TextView)findViewById(R.id.aboutDevName2);
        des2=(TextView)findViewById(R.id.aboutDevdes2);
        name3=(TextView)findViewById(R.id.aboutDevName3);
        des3=(TextView)findViewById(R.id.aboutDevdes3);
        name4=(TextView)findViewById(R.id.aboutDevName4);
        des4=(TextView)findViewById(R.id.aboutDevdes4);
        mentors=(TextView)findViewById(R.id.aboutDevText);
        name5=(TextView)findViewById(R.id.aboutDevName5);
        des5=(TextView)findViewById(R.id.aboutDevdes5);
        name6=(TextView)findViewById(R.id.aboutDevName6);
        des6=(TextView)findViewById(R.id.aboutDevdes6);
        topText=(TextView)findViewById(R.id.aboutDevTopText);


        name1.setTypeface(EasyFonts.captureIt(this));
        name2.setTypeface(EasyFonts.captureIt(this));
        name3.setTypeface(EasyFonts.captureIt(this));
        name4.setTypeface(EasyFonts.captureIt(this));
        name5.setTypeface(EasyFonts.captureIt(this));
        name6.setTypeface(EasyFonts.captureIt(this));
        des1.setTypeface(EasyFonts.tangerineBold(this));
        des2.setTypeface(EasyFonts.tangerineBold(this));
        des3.setTypeface(EasyFonts.tangerineBold(this));
        des4.setTypeface(EasyFonts.tangerineBold(this));
        des5.setTypeface(EasyFonts.tangerineBold(this));
        des6.setTypeface(EasyFonts.tangerineBold(this));
        mentors.setTypeface(EasyFonts.freedom(this));
        topText.setTypeface(EasyFonts.freedom(this));

    }
}
