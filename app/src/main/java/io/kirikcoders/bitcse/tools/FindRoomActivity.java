package io.kirikcoders.bitcse.tools;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.database.DataBaseHelper;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class FindRoomActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText startTime,endTime;
    private Spinner daySpinner;
    private ListView roomView;
    private int slot = -1,hour,minute;
    private static int lastClicked;
    private DataBaseHelper dataBaseHelper;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);
        dataBaseHelper = new DataBaseHelper(this);
        try {
            dataBaseHelper.createDatabase();
            dataBaseHelper.openDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                findEmptyRoomsGivenParams(hour,minute);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTime.setText(new SimpleDateFormat("h:mm a").format(new Date()));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBaseHelper.close();
    }

    public void findEmptyRoomsGivenParams(int hour, int minute) {
        this.hour = hour; this.minute = minute;
        StringBuffer s = new StringBuffer();
        String min;
        if (hour >= 12){
            s.append("PM");
            if (hour > 12)
                hour -= 12;
        }
        else
            s.append("AM");
        if (minute < 10)
            min = "0"+Integer.toString(minute);
        else
            min = Integer.toString(minute);
        switch (lastClicked){
            case R.id.room_end_time:
                endTime.setText(hour+":"+min+" "+s);
                break;
            case R.id.room_start_time:
                startTime.setText(hour+":"+min+" "+s);
                break;
        }
        if (slot != -1 && InputCheckUtils.checkInputs(startTime,endTime))
        {
            ArrayList<String> roomNumbers = dataBaseHelper.getRoomNumbers();
            int beginTime = Integer.parseInt(startTime.getText().toString().split(":")[0]);
            int finalTime = Integer.parseInt(endTime.getText().toString().split(":")[0]);
            beginTime = convertToTwelveHours(beginTime);
            finalTime = convertToTwelveHours(finalTime);
            ArrayList<String> filledRooms = dataBaseHelper.getFullRooms(beginTime,finalTime,
                    Constants.days[slot].substring(0,3).toUpperCase());
            roomNumbers.removeAll(filledRooms);
            roomNumbers.removeAll(Collections.singleton(null));
            arrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    roomNumbers);
            roomView.setAdapter(arrayAdapter);
        }
    }

    private int convertToTwelveHours(int time) {
        if (time > 12)
            time =- 12;
        return time;
    }

}
