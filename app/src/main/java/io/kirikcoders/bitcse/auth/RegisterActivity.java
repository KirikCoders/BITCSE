package io.kirikcoders.bitcse.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.kirikcoders.bitcse.LoginActivity;
import io.kirikcoders.bitcse.MainActivity;
import io.kirikcoders.bitcse.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText registerOtp,usn,password,confirmPassword;
    private Button sendOtp,register;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mStudents,mProfessors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    sendVerificationEmail();
                }
            }
        };
        database = FirebaseDatabase.getInstance();
        mAuth.addAuthStateListener(mAuthStateListener);
        mStudents = database.getReference("students");
        mProfessors = database.getReference("professors");
        usn = findViewById(R.id.register_usn);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);
        register = findViewById(R.id.register_btn);
    }

    private void sendVerificationEmail() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Could not send " +
                                    "verification email. Please check your internet" +
                                    " connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void sendOtp(View view) {
        if(!(password.getText().toString().equals(confirmPassword.getText().toString()))){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, usn.getText()+":"+password.getText()+":"
                +confirmPassword.getText(), Toast.LENGTH_SHORT).show();
        sendOtp.setVisibility(View.GONE);
        register.setVisibility(View.VISIBLE);
        registerOtp.setVisibility(View.VISIBLE);
    }

    public void register(View view) {
        if (usn.getText().toString().trim().equals("")){
            usn.setError("This field cannot be empty");
            return;
        } else if (password.getText().toString().trim().equals("")){
            password.setError("This field cannot be empty");
            return;
        } else if(confirmPassword.getText().toString().trim().equals("")){
            confirmPassword.setError("This field cannot be empty");
            return;
        } else if(!(password.getText().toString().trim()
                .equals(confirmPassword.getText().toString().trim()))){
            password.setError("Passwords do not match");
            confirmPassword.setError("Passwords do not match");
            return;
        }
        mStudents.child(usn.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String username = dataSnapshot.child("emailId").getValue().toString();
                    String password = confirmPassword.getText().toString().trim();
                    mAuth.createUserWithEmailAndPassword(username,password)
                            .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG","Created User:"+task.isSuccessful());
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Error occurred." +
                                        " Could not create user. Please " +
                                        "check your internet connection", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

                }catch (NullPointerException e){
                    usn.setError("Invalid USN. Please check your input or contact" +
                            " your department for help");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
