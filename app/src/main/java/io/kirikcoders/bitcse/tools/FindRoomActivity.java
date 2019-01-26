package io.kirikcoders.bitcse.tools;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.events.TimePickerFragment;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.InputCheckUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FindRoomActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText startTime,endTime;
    private Spinner daySpinner;
    private ListView roomView;
    private int slot = -1;
    private static int lastClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);
        daySpinner = findViewById(R.id.day_spinner_room);
        startTime = findViewById(R.id.room_start_time);
        endTime = findViewById(R.id.room_end_time);
        roomView = findViewById(R.id.room_list_view);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,
                Constants.days);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                slot = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTime.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        lastClicked = v.getId();
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setFindRoom(true);
        timePickerFragment.show(getSupportFragmentManager(),"Select a time");
    }

    public void findEmptyRoomsGivenParams(int hour,int minute) {
        switch (lastClicked){
            case R.id.room_end_time:
                endTime.setText(Integer.toString(hour)+":"+Integer.toString(minute));
                break;
            case R.id.room_start_time:
                startTime.setText(Integer.toString(hour)+":"+Integer.toString(minute));
                break;
        }
        if (slot != -1 && InputCheckUtils.checkInputs(startTime,endTime))
            Log.i("ROOM","It works "+slot);
    }

}
