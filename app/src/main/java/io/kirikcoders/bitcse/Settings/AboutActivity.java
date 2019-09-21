package io.kirikcoders.bitcse.Settings;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.Constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Akash on 26-Jan-19.
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        SharedPreferences mPrefs = getBaseContext().getSharedPreferences(Constants.USER_PREFERENCE_FILE, Context.MODE_PRIVATE);
        int mode = mPrefs.getInt("MODE",-1);
        getDelegate().setLocalNightMode(mode);
    }
}
