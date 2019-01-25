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

public class SearchFaculty extends AppCompatActivity {
    String Daykey=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_faculty);
        TextView tw=findViewById(R.id.facsearchtw);
        tw.setText("Select Day:-");
        TimeTableDataBaseHelper dataBaseHelper = new TimeTableDataBaseHelper(getApplicationContext());
        ListView lst=findViewById(R.id.fac_search_lst);
        String Days[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        FacSearchModel adapter=new FacSearchModel(this,Days);
        lst.setAdapter(adapter);
        try {
            dataBaseHelper.createDatabase();
            dataBaseHelper.openDatabase();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        lst.setOnItemClickListener((parent, view, position, id) -> {
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
            Intent i=new Intent(view.getContext(),SlotSelect.class);
            i.putExtra("DAYKEY",Daykey);
            startActivity(i);

        });
    }
}
