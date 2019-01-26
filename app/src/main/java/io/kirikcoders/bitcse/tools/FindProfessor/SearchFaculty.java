package io.kirikcoders.bitcse.tools.FindProfessor;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.database.DataBaseHelper;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
/**
 * Created by Akash on 25-Jan-19.
 */

public class SearchFaculty extends AppCompatActivity {
    String Daykey=new String();
    int slot;
    String resRoom;
    String resSub;
    String resSem;
    String FacName=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_faculty);
        Spinner day=findViewById(R.id.day_spinner);
        Spinner Slot=findViewById(R.id.slot_spinner);
        ListView lst=findViewById(R.id.fac_search_lst);
        String Days[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        ArrayAdapter<String> Dayadapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Days);
        Dayadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        day.setAdapter(Dayadapter);
        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:Daykey="MON";
                        break;
                    case 1:Daykey="TUE";
                        break;
                    case 2:Daykey="WED";
                        break;
                    case 3:Daykey="THU";
                        break;
                    case 4:Daykey="FRI";
                        break;
                    case 5:Daykey="SAT";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        try {
            dataBaseHelper.createDatabase();
            dataBaseHelper.openDatabase();
        } catch (IOException e) {
            e.printStackTrace();}
        Cursor c=dataBaseHelper.getSlot();
        c.moveToFirst();
        int num=c.getCount();
        int i=1;
        int j=0;
        String[] slots=new String[num];
        while(i<=num)
        {
            slots[j]=c.getString(c.getColumnIndex("timings"));
            c.moveToNext();
            i++;
            j++;
        }
        ArrayAdapter<String> Slotadapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,slots);
        Slotadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Slot.setAdapter(Slotadapter);
        Slot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:slot=1;
                        break;
                    case 1:slot=2;
                        break;
                    case 2:slot=3;
                        break;
                    case 3:slot=4;
                        break;
                    case 4:slot=5;
                        break;
                    case 5:slot=6;
                        break;
                    case 6:slot=7;
                        break;
                    case 7:slot=8;
                        break;
                    case 8:slot=9;
                        break;
                    case 9:slot=10;
                        break;
                    case 10:slot=11;
                        break;
                    case 11:slot=12;
                        break;
                    case 12:slot=13;
                        break;
                    case 13:slot=14;
                        break;
                    case 14:slot=15;
                        break;
                    case 15:slot=21;
                        break;
                    case 16:slot=22;
                        break;
                    case 17:slot=23;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Cursor c1=dataBaseHelper.getnames();
        c1.moveToFirst();
        c1.moveToNext();
        int num1=c1.getCount();
        int i1=2;
        int j1=0;
        String[] names=new String[42];
        while(i1<num1)
        {
            names[j1]=c1.getString(c1.getColumnIndex("name"));
            c1.moveToNext();
            i1++;
            j1++;
        }
        FacSearchModel adapter=new FacSearchModel(this,names);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener((parent, view, position, id) ->
        {
            c1.moveToFirst();
            c1.moveToPosition(position+1);
            FacName=c1.getString(c1.getColumnIndex("name"));
            Cursor c2=dataBaseHelper.getdataName(FacName,Daykey,slot);
            c2.moveToFirst();
            try
            {
                resRoom = c2.getString(c2.getColumnIndex("room"));
                resSub = c2.getString(c2.getColumnIndex("sub"));
                resSem = c2.getString(c2.getColumnIndex("sem"));
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
