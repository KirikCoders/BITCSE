package io.kirikcoders.bitcse.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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
import io.kirikcoders.bitcse.utils.Constants;

public class RegisterActivity extends AppCompatActivity {
    // get references to UI elements
    private EditText usn,password,confirmPassword;
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
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usn = findViewById(R.id.register_usn);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);
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
                        break;
                    case R.id.regProfessor:
                        myRef = database.getReference(Constants.PROF_DATABASE);
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
        if(radioGroup.getCheckedRadioButtonId() == -1){
            Snackbar.make(getCurrentFocus(),"Please select one of the Options: Student or Professor",Snackbar.LENGTH_LONG).show();
            return;
        }
        if (usn.getText().toString().trim().equals("")){
            usn.setError("This field cannot be empty");
            return;
        } else if (password.getText().toString().trim().equals("")){
            password.setError("This field cannot be empty");
            return;
        } else if(password.getText().toString().trim().length() < 8){
            password.setError("Password too short.");
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
        myRef.child(usn.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String username = dataSnapshot.child("emailId").getValue().toString();
                    String password = confirmPassword.getText().toString().trim();
                    System.out.println(username);
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
                            else {
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
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
//    This method ensures that users accept the terms and conditions
    public void acceptTerms(View view) {
        if(((CheckBox)view).isChecked()){
            register.setEnabled(true);
        }
        else
            register.setEnabled(false);
    }
}
