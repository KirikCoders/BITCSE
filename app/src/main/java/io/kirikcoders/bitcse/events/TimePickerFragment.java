package io.kirikcoders.bitcse.events;

import android.app.Dialog;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import io.kirikcoders.bitcse.tools.FindRoomActivity;

/**
 * Created by Kartik on 05-Aug-18.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private boolean isFindRoom = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,hour,minute,false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if (isFindRoom){
            FindRoomActivity roomActivity = (FindRoomActivity) getActivity();
            roomActivity.findEmptyRoomsGivenParams(hour,minute);
        }
        else {
            CreateEventActivity eventActivity = (CreateEventActivity) getActivity();
            eventActivity.setTime(hour, minute);
        }
    }

    public void setFindRoom(boolean findRoom) {
        isFindRoom = findRoom;
    }
}
