package io.kirikcoders.bitcse.tools.FindProfessor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.database.TimeTableDataBaseHelper;
/**
 * Created by Akash on 25-Jan-19.
 */

public class SlotSelect extends AppCompatActivity {
    int slot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_faculty);
        TextView tw = findViewById(R.id.facsearchtw);
        Intent intent=getIntent();
        String Daykey=intent.getStringExtra("DAYKEY");
        tw.setText("Select Slot:-");
        TimeTableDataBaseHelper dataBaseHelper = new TimeTableDataBaseHelper(getApplicationContext());
        ListView lst = findViewById(R.id.fac_search_lst);
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
        FacSearchModel adapter=new FacSearchModel(this,slots);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener((parent, view, position, id) -> {
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
          Intent i1 =new Intent(view.getContext(),SelectFac.class);
            i1.putExtra("SLOT",slot);
            i1.putExtra("DAYKEY",Daykey);
            startActivity(i1);

        });

    }
    }
