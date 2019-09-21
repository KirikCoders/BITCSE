package io.kirikcoders.bitcse.tools.StudentDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.InputCheckUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akash on 25-Jan-19.
 */

public class StudentInfoActivity extends AppCompatActivity {
    Button button;
    EditText usn;
    DatabaseReference ref;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        SharedPreferences mPrefs = getBaseContext().getSharedPreferences(Constants.USER_PREFERENCE_FILE, Context.MODE_PRIVATE);
        int mode = mPrefs.getInt("MODE",-1);
        getDelegate().setLocalNightMode(mode);
        usn=findViewById(R.id.stdinf_et);
        button=findViewById(R.id.stdinf_b);
        relativeLayout=findViewById(R.id.info_rl);
        if(isNetworkConnected()==false)
        {
            usn.setFocusable(false);
            Snackbar.make(relativeLayout,"Check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", v -> {
                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }).show();
        }
        ref= FirebaseDatabase.getInstance().getReference().child("students");
    }

    public void onclickButton(View v)
    {
        InputCheckUtils.checkInputs(usn);
        String USN=usn.getText().toString();
        ref.child(USN).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try
                {
                    StudentDialog studentDialog=new StudentDialog(USN,dataSnapshot.child("name").getValue().toString(),dataSnapshot.child("phone").getValue().toString(), dataSnapshot.child("emailId").getValue().toString(),1);
                   studentDialog.show(getSupportFragmentManager(),"Success");
                }catch(Exception e)
                {
                    StudentDialog studentDialog=new StudentDialog();
                    studentDialog.show(getSupportFragmentManager(),"Failure");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
