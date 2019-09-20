package io.kirikcoders.bitcse.tools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akash on 18-Jan-19.
 */


public class AttendenceActivity extends AppCompatActivity {

    UserDetails obj;
    DatabaseReference ref,test1,test2,test3;
    TextView sub1,sub2,sub3,sub4,sub5,sub6,sub7,sub8,sub9;
    TextView[] tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        obj = new UserDetails(AttendenceActivity.this, Constants.USER_PREFERENCE_FILE);
        ref= FirebaseDatabase.getInstance().getReference().child("attendance");
        test1=ref.child("test1");
        test2=ref.child("test2");
        test3=ref.child("test3");
        RelativeLayout relativeLayout=findViewById(R.id.linearLayout2a);
        TableLayout table=findViewById(R.id.tableLayouta);
        if(isNetworkConnected()==false)
        {
            table.setVisibility(table.GONE);
            Snackbar.make(relativeLayout,"Check Internet Connection",Snackbar.LENGTH_INDEFINITE).setAction("Retry", v -> {
                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }).show();
        }
        TableRow[] row={findViewById(R.id.r2a),findViewById(R.id.r3a),findViewById(R.id.r4a),findViewById(R.id.r5a),findViewById(R.id.r6a),findViewById(R.id.r7a),findViewById(R.id.r8a),findViewById(R.id.r9a),findViewById(R.id.r10a)};
        TextView[] sub={findViewById(R.id.a_sub1a),findViewById(R.id.a_sub2a),findViewById(R.id.a_sub3a),findViewById(R.id.a_sub4a),findViewById(R.id.a_sub5a),findViewById(R.id.a_sub6a),findViewById(R.id.a_sub7a),findViewById(R.id.a_sub8a),findViewById(R.id.a_sub9a)};
        TextView[] t1={findViewById(R.id.sub1_t1a),findViewById(R.id.sub2_t1a),findViewById(R.id.sub3_t1a),findViewById(R.id.sub4_t1a),findViewById(R.id.sub5_t1a),findViewById(R.id.sub6_t1a),findViewById(R.id.sub7_t1a),findViewById(R.id.sub8_t1a),findViewById(R.id.sub9_t1a)};
        try{
        test1.child(obj.getmUsn()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count=dataSnapshot.getChildrenCount();
                if(count==0)
                {
                    Toast.makeText(AttendenceActivity.this,"No data found in Database",Toast.LENGTH_LONG).show();
                }
                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren() )
                {
                    sub[i].setText(ds.getKey().toString());
                    t1[i].setText(ds.getValue().toString()+"%");
                    i++;
                }
                while(i<9)
                {
                    table.removeView(row[i]);
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }catch (Exception e){Toast.makeText(AttendenceActivity.this,"Wrong Data in database",Toast.LENGTH_LONG).show();}
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
