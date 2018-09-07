package io.kirikcoders.bitcse;

import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.kirikcoders.bitcse.auth.RegisterActivity;
import io.kirikcoders.bitcse.utils.UserDetails;

public class LoginActivity extends AppCompatActivity {
//  Fire base objects
    private FirebaseAuth mAuth;
    private RadioGroup radioGroup;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    // UI objects
    private EditText mUsn,mPassword;
    private UserDetails user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mUsn = findViewById(R.id.usn);
        mPassword = findViewById(R.id.password);
        radioGroup = findViewById(R.id.logRadioGroup);
//        Radio group event handler
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.logStudent:
                        myRef = database.getReference("students");
                        break;
                    case R.id.logProfessor:
                        myRef = database.getReference("professors");
                        break;
                }
            }
        });
        user = new UserDetails(getApplicationContext(),getString(R.string.user_pref_key));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //  Check if the user is already logged in
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            if(!user.isEmailVerified()){
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(LoginActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                goToMainActivity();
                finish();
            }
        }
    }

    private void goToMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void resetPassword(View view) {
        if(TextUtils.isEmpty(mUsn.getText()) || mUsn.getText().toString().trim().equals("")){
            mUsn.setError("Please enter your USN or professor code and select " +
                    "whether you are a student or professor. We will sent the reset" +
                    " link to your email");
            return;
        }
        if(radioGroup.getCheckedRadioButtonId() == -1){
            Snackbar.make(getCurrentFocus(),"Please choose an Option: Student or Professor",Snackbar.LENGTH_LONG)
                    .show();
            return;
        }
        myRef.child(mUsn.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseAuth.getInstance()
                        .sendPasswordResetEmail(dataSnapshot.child("emailId").getValue().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ResetPasswordError",databaseError.getMessage());
            }
        });
    }

    public void goToHelpScreen(View view) {
        Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
    }

    public void loginUser(View view) {
        if(radioGroup.getCheckedRadioButtonId() == -1){
            Snackbar.make(getCurrentFocus(),"Please select one of the Options: Student or Professor",Snackbar.LENGTH_LONG).show();
            return;
        }
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
            myRef.child(usn).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
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
                                        user.setmEmail(dataSnapshot.child("emailId").getValue().toString());
                                        user.setmUsn(mUsn.getText().toString().trim());
                                        user.setmName(dataSnapshot.child("name").getValue().toString());
                                        user.setmPhoneNumber(dataSnapshot.child("phone").getValue().toString());
                                        user.setmSemester(dataSnapshot.child("semester").getValue().toString());
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
