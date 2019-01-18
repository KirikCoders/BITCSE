package io.kirikcoders.bitcse.Tools;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.MainActivity;
import io.kirikcoders.bitcse.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Tools_new extends AppCompatActivity {
    ListView toolslist;
    Tools_new obj;
    String[] items={"Faculty Details","SGPA Calculator","Internal Marks","Attendance"};
    Integer[] imgid={R.drawable.round_people_24,R.drawable.sgpa,R.drawable.marks,R.drawable.attend};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_new);
        toolslist=findViewById(R.id.Tools_List);
        ToolsAdapter toolsAdapter=new ToolsAdapter(this,items,imgid);
        toolslist.setAdapter(toolsAdapter);

        toolslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:Intent i=new Intent(view.getContext(),FacultyDetails.class);
                           startActivity(i);
                           break;
                    case 1: Intent j=new Intent(view.getContext(),SGPAActivity.class);
                            startActivity(j);
                            break;
                    case 2:System.out.println("INTERNAL");
                           break;
                    case 3:Intent k=new Intent(view.getContext(),Attendence.class);
                           startActivity(k);
                           break;
                }
            }
        });
    }


}
