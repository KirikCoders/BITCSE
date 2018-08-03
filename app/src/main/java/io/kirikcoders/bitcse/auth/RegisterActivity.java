package io.kirikcoders.bitcse.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.kirikcoders.bitcse.MainActivity;
import io.kirikcoders.bitcse.R;

public class RegisterActivity extends AppCompatActivity {
    EditText registerOtp,usn,password,confirmPassword;
    Button sendOtp,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerOtp = findViewById(R.id.register_otp);
        usn = findViewById(R.id.register_usn);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);
        sendOtp = findViewById(R.id.otp_btn);
        register = findViewById(R.id.register_btn);
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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
