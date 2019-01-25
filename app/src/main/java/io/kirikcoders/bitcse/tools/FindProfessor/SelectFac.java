package io.kirikcoders.bitcse.tools.FindProfessor;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.database.TimeTableDataBaseHelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
/**
 * Created by Akash on 25-Jan-19.
 */

public class SelectFac extends AppCompatActivity {
    String resRoom;
    String resSub;
    String resSem;
    String FacName=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_faculty);
        Intent intent=getIntent();
        int slot=intent.getIntExtra("SLOT",-1);
        String Daykey=intent.getStringExtra("DAYKEY");
        TextView tw=findViewById(R.id.facsearchtw);
        tw.setText("Select Faculty:-");
        TimeTableDataBaseHelper dataBaseHelper = new TimeTableDataBaseHelper(getApplicationContext());
        try {
            dataBaseHelper.createDatabase();
            dataBaseHelper.openDatabase();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Cursor c=dataBaseHelper.getnames();
        c.moveToFirst();
        c.moveToNext();
        int num=c.getCount();
        int i=2;
        int j=0;
        String[] names=new String[42];
        while(i<num)
        {
            names[j]=c.getString(c.getColumnIndex("name"));
            c.moveToNext();
            i++;
            j++;
        }
        FacSearchModel adapter=new FacSearchModel(this,names);
        ListView lst = findViewById(R.id.fac_search_lst);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener((parent, view, position, id) -> {
         c.moveToFirst();
         c.moveToPosition(position+1);
         FacName=c.getString(c.getColumnIndex("name"));
         Cursor c1=dataBaseHelper.getdataName(FacName,Daykey,slot);
         c1.moveToFirst();
         try
         {
             resRoom = c1.getString(c1.getColumnIndex("room"));
             resSub = c1.getString(c1.getColumnIndex("sub"));
             resSem = c1.getString(c1.getColumnIndex("sem"));
             OpenDialog_success();
         }catch (Exception e)
         {
             OpenDialog_Failure();
         }
        });
    }

    public void OpenDialog_success()
    {
        ResDiag resDiag=new ResDiag(resRoom,resSub,resSem,1);
        resDiag.show(getSupportFragmentManager(),"Success");
    }

    public void OpenDialog_Failure()
    {
        ResDiag resDiag=new ResDiag("null","null","null",0);
        resDiag.show(getSupportFragmentManager(),"Failure");
    }
}
