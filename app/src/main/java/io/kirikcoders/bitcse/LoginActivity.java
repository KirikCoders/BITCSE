package io.kirikcoders.bitcse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import io.kirikcoders.bitcse.auth.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
    }

    public void resetPassword(View view) {
        Toast.makeText(this, "Reset Password", Toast.LENGTH_SHORT).show();
    }

    public void goToHelpScreen(View view) {
        Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
    }

    public void loginUser(View view) {
        Intent i = new Intent(this,MainActivity.class);
        Toast.makeText(this, "Sign in", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    public void registerUser(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
