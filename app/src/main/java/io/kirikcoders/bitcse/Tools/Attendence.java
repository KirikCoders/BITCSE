package io.kirikcoders.bitcse.Tools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akash on 18-Jan-19.
 */


public class Attendence extends AppCompatActivity {

    UserDetails obj;
    DatabaseReference ref;
    TextView sub1,sub2,sub3,sub4,sub5,sub6,sub7,sub8,sub9;
    TextView[] tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        obj = new UserDetails(Attendence.this, Constants.USER_PREFERENCE_FILE);
        ref= FirebaseDatabase.getInstance().getReference().child("attendance");
        TableLayout table=findViewById(R.id.tableLayout);
        TableRow[] row={findViewById(R.id.r2),findViewById(R.id.r3),findViewById(R.id.r4),findViewById(R.id.r5),findViewById(R.id.r6),findViewById(R.id.r7),findViewById(R.id.r8),findViewById(R.id.r8),findViewById(R.id.r10)};
        TextView[] sub={findViewById(R.id.a_sub1),findViewById(R.id.a_sub2),findViewById(R.id.a_sub3),findViewById(R.id.a_sub4),findViewById(R.id.a_sub5),findViewById(R.id.a_sub6),findViewById(R.id.a_sub7),findViewById(R.id.a_sub8),findViewById(R.id.a_sub9)};
        TextView[] t1={findViewById(R.id.sub1_t1),findViewById(R.id.sub2_t1),findViewById(R.id.sub3_t1),findViewById(R.id.sub4_t1),findViewById(R.id.sub5_t1),findViewById(R.id.sub6_t1),findViewById(R.id.sub7_t1),findViewById(R.id.sub8_t1),findViewById(R.id.sub9_t1)};
        TextView[] t2={findViewById(R.id.sub1_t2),findViewById(R.id.sub2_t2),findViewById(R.id.sub3_t2),findViewById(R.id.sub4_t2),findViewById(R.id.sub5_t2),findViewById(R.id.sub6_t2),findViewById(R.id.sub7_t2),findViewById(R.id.sub8_t2),findViewById(R.id.sub9_t2)};
        TextView[] t3={findViewById(R.id.sub1_t3),findViewById(R.id.sub2_t3),findViewById(R.id.sub3_t3),findViewById(R.id.sub4_t3),findViewById(R.id.sub5_t3),findViewById(R.id.sub6_t3),findViewById(R.id.sub7_t3),findViewById(R.id.sub8_t3),findViewById(R.id.sub9_t3)};
        ref.child(obj.getmUsn()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                long j=dataSnapshot.getChildrenCount();
                System.out.println(j);
                for(DataSnapshot ds : dataSnapshot.getChildren() )
                {
                    sub[i].setText(ds.child("subname").getValue().toString());
                    t1[i].setText(ds.child("test1").getValue().toString());
                    t2[i].setText(ds.child("test2").getValue().toString());
                    t3[i].setText(ds.child("test3").getValue().toString());
                    i++;
                }
                while((int)j<=8)
                {
                    table.removeView(row[(int)j]);
                    j++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
