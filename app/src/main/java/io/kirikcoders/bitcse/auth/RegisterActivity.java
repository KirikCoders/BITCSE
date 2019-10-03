package io.kirikcoders.bitcse.auth;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.LoginActivity;
import io.kirikcoders.bitcse.MainActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.Constants;

public class RegisterActivity extends AppCompatActivity {
    // get references to UI elements
    private EditText usn,password,confirmPassword,email,phone;
    private Button register;
    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    // Fire base objects to access db to verify users during registration
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser mUser;
    // Firebase auth listener triggers when the register event completes i.e signUpUser....()
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usn = findViewById(R.id.register_usn);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);
        email = findViewById(R.id.register_email);
        phone = findViewById(R.id.register_phone);
        register = findViewById(R.id.register_btn);
        radioGroup = findViewById(R.id.regRadioGroup);
        progressBar = findViewById(R.id.progressBar);
        register.setEnabled(false);
        // radioGroup event listener to decide which db to use
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.regStudent:
                        myRef = database.getReference(Constants.STUDENT_DATABASE);
                        email.setVisibility(View.VISIBLE);
                        phone.setVisibility(View.VISIBLE);
                        break;
                    case R.id.regProfessor:
                        myRef = database.getReference(Constants.PROF_DATABASE);
                        email.setVisibility(View.GONE);
                        phone.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }
    /*
    * This method is called when the register button is clicked
    * It queries fire base to find the user with USN or profID, gets their email and registers
    * them with fire base
    */
    public void register(View view) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Snackbar.make(getCurrentFocus(), "Please select one of the Options: Student or Professor", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (usn.getText().toString().trim().equals("")) {
            usn.setError("This field cannot be empty");
            return;
        } else if (email.getText().toString().trim().equals("")) {
            email.setError("This field cannot be empty");
            return;
        }else if (phone.getText().toString().trim().length() != 10) {
            phone.setError("Enter a 10 digit phone number");
            return;
        }else if (password.getText().toString().trim().equals("")) {
            password.setError("This field cannot be empty");
            return;
        } else if (password.getText().toString().trim().length() < 8) {
            password.setError("Password too short.");
            return;
        } else if (confirmPassword.getText().toString().trim().equals("")) {
            confirmPassword.setError("This field cannot be empty");
            return;
        } else if (!(password.getText().toString().trim()
                .equals(confirmPassword.getText().toString().trim()))) {
            password.setError("Passwords do not match");
            confirmPassword.setError("Passwords do not match");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        if (radioGroup.getCheckedRadioButtonId() == R.id.regProfessor) {
            mAuth.signInAnonymously().addOnSuccessListener(authResult -> myRef.child(usn.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String username = dataSnapshot.child("emailId").getValue().toString();
                        String password = confirmPassword.getText().toString().trim();
                        System.out.println(username);
                        mAuth.createUserWithEmailAndPassword(username, password)
                                .addOnCompleteListener(RegisterActivity.this, task -> {
                                    Log.d("TAG", "Created User:" + task.isSuccessful());
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Error occurred." +
                                                " Could not create user. Please " +
                                                "check your internet connection", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                });

                    } catch (NullPointerException e) {
                        usn.setError("Invalid USN. Please check your input or contact" +
                                " your department for help");
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }))
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(view, "Something went wrong.Please check if you have an internet connection or that the details" +
                                "entered are valid", Snackbar.LENGTH_LONG).show();
                    });
        }
        else{
            myRef.child(usn.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("flag").getValue().toString().trim().equals("reg"))
                    {
                        System.out.println("Enter");
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        Toast.makeText(getBaseContext(),"User Already registered,Please Login",Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mAuth.signInAnonymously().addOnSuccessListener(authResult -> myRef.child(usn.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String username = email.getText().toString().trim();
                        String password = confirmPassword.getText().toString().trim();
                        if(!dataSnapshot.child("flag").equals("reg"))
                        mAuth.createUserWithEmailAndPassword(username, password)
                                .addOnCompleteListener(RegisterActivity.this, task -> {
                                    Log.d("TAG", "Created User:" + task.isSuccessful());
                                    if (!isNetworkConnected()) {
                                        Toast.makeText(RegisterActivity.this, "Error occurred." +
                                                " Could not create user. Please " +
                                                "check your internet connection", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        myRef.child(usn.getText().toString().trim()).child("flag").setValue("reg");
                                        myRef.child(usn.getText().toString().trim()).child("emailId").setValue(username);
                                        myRef.child(usn.getText().toString().trim()).child("phone").setValue(phone.getText().toString().trim());
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                });

                    } catch (NullPointerException e) {
                        usn.setError("Invalid USN. Please check your input or contact" +
                                " your department for help");
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }))
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(view, "Something went wrong.Please check if you have an internet connection or that the details" +
                                "entered are valid", Snackbar.LENGTH_LONG).show();
                    });

        }

    }
//    This method ensures that users accept the terms and conditions
    public void acceptTerms(View view) {
        if(((CheckBox)view).isChecked()){
            register.setEnabled(true);
        }
        else
            register.setEnabled(false);
    }

    public void launchTermsOfService(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://kirikcoders.github.io/BITCSE/"));
        startActivity(intent);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
