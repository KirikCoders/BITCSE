package io.kirikcoders.bitcse.tools.StudentDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.InputCheckUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akash on 25-Jan-19.
 */

public class StudentInfo extends AppCompatActivity {
    Button button;
    EditText usn;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        usn=findViewById(R.id.stdinf_et);
        button=findViewById(R.id.stdinf_b);
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
}
