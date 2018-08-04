package io.kirikcoders.bitcse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.kirikcoders.bitcse.auth.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private boolean emailVerified,accountExists;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mStudents,mProfessors;
    private EditText mUsn,mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mStudents = database.getReference("students");
        mProfessors = database.getReference("professors");
        mUsn = findViewById(R.id.usn);
        mPassword = findViewById(R.id.password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            goToMainActivity();
            finish();
        }
    }

    private void goToMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void resetPassword(View view) {
        Toast.makeText(this, "Reset Password", Toast.LENGTH_SHORT).show();
    }

    public void goToHelpScreen(View view) {
        Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
    }

    public void loginUser(View view) {
        if(mUsn.getText().toString().trim().equals("")){
            mUsn.setError("Field cannot be empty");
            return;
        }
        else if (TextUtils.isEmpty(mPassword.getText())){
            mPassword.setError("Field cannot be empty");
            return;
        }
        try {
            String usn = mUsn.getText().toString().trim();
            mStudents.child(usn).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        String username = dataSnapshot.child("emailId").getValue().toString();
                        String password = mPassword.getText().toString().trim();
                        mAuth.signInWithEmailAndPassword(username,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                            mUsn.setError("Email ID not verified.Check your email");
                                            FirebaseAuth.getInstance().signOut();
                                            return;
                                        }
                                       goToMainActivity();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mPassword.setError("Password incorrect");
                                    }
                                });
                    } catch (NullPointerException e){
                        mUsn.setError("Invalid USN. Please check your input or contact" +
                                " your department for help");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("FirebaseStudentLoadFail", databaseError.getMessage());
                }
            });
        } catch (NullPointerException e){
            mUsn.setError("Invalid USN. Please check your input or contact" +
                    " your department for help");
        }
    }
    public void registerUser(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
