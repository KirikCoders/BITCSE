package io.kirikcoders.bitcse;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.kirikcoders.bitcse.database.TimeTableDataBaseHelper;
import io.kirikcoders.bitcse.timetable.MySubjectRecyclerViewAdapter;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class TimeTableFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // variables are declared
    private Spinner semSectionSpinner;
    private TimeTableDataBaseHelper timeTableDataBaseHelper;
    private TextView dayTextView;
    private UserDetails userDetails;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MySubjectRecyclerViewAdapter mAdapter;
    private ArrayAdapter<CharSequence> semesterAdapter;
    private HashMap<String,String> dayMap;
    private String selectedSemester;
    private int selectedDay;
    private ImageButton next,previous;
    private String[] days = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time_table,container,false);
        dayMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        selectedDay = cal.get(Calendar.DAY_OF_WEEK);
        initDayMap();
        next = rootView.findViewById(R.id.day_next);
        previous = rootView.findViewById(R.id.day_prev);
        semSectionSpinner = rootView.findViewById(R.id.time_table_sem);
        dayTextView = rootView.findViewById(R.id.day_shower);
        userDetails = new UserDetails(getContext(),Constants.USER_PREFERENCE_FILE);
        timeTableDataBaseHelper = new TimeTableDataBaseHelper(getContext());
        recyclerView = rootView.findViewById(R.id.time_table_recycler_view);
        TimeTableDataBaseHelper timeTableDataBaseHelper = new TimeTableDataBaseHelper(getContext());
        try {
            timeTableDataBaseHelper.createDatabase();
            timeTableDataBaseHelper.openDatabase();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MySubjectRecyclerViewAdapter(timeTableDataBaseHelper.getDaySem("6a","MON"));
        recyclerView.setAdapter(mAdapter);
        // create the database by copying it into /data/data/ folder
        // if it is already created, the helper will automatically ignore the operation and continue
        // to openDatabase()
        semesterAdapter = new ArrayAdapter<CharSequence>(getContext(),
                R.layout.support_simple_spinner_dropdown_item);
        if (!userDetails.isProfessor())
            semesterAdapter.add(userDetails.getmSemester());
        semesterAdapter.addAll(convertCursorToList(timeTableDataBaseHelper.getSem()));
        selectedSemester = userDetails.getmSemester();
        semSectionSpinner.setAdapter(semesterAdapter);
        semSectionSpinner.setOnItemSelectedListener(this);
        next.setOnClickListener((view)->{
            changeDay(true); // true indicates that the day should move forward
            String dbQuery = days[selectedDay-1];
            dayTextView.setText(dayMap.get(dbQuery));
            mAdapter.setCursor(timeTableDataBaseHelper.getDaySem(selectedSemester, dbQuery));
            mAdapter.notifyDataSetChanged();
        });
        previous.setOnClickListener((view)->{
            changeDay(false); // false indicates that the day should move backward
            String dbQuery = days[selectedDay-1];
            dayTextView.setText(dayMap.get(dbQuery));
            mAdapter.setCursor(timeTableDataBaseHelper.getDaySem(selectedSemester, dbQuery));
            mAdapter.notifyDataSetChanged();
        });
        return rootView;
    }

    private void changeDay(boolean direction) {
        if (direction){
            if (selectedDay < 7)
                selectedDay++;
            else if (selectedDay+1 > 7)
                selectedDay = 1;
        }
        else {
            if (selectedDay > 1)
                selectedDay--;
            else if (selectedDay-1 < 1)
                selectedDay = 7;
        }
    }

    private void initDayMap() {
        dayMap.put("SUN","Sunday");
        dayMap.put("MON","Monday");
        dayMap.put("TUE","Tuesday");
        dayMap.put("WED","Wednesday");
        dayMap.put("THU","Thursday");
        dayMap.put("FRI","Friday");
        dayMap.put("SAT","Saturday");
    }

    private ArrayList<CharSequence> convertCursorToList(Cursor sem) {
        ArrayList<CharSequence> arrayList = new ArrayList<>(sem.getCount());
        while (sem.moveToNext()){
            if (sem.getString(sem.getColumnIndex("sem")).equals(userDetails.getmSemester()))
                break;
            arrayList.add(sem.getString(sem.getColumnIndex("sem")));
        }
        sem.close();
        return arrayList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeTableDataBaseHelper.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            timeTableDataBaseHelper.openDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dbQuery = days[selectedDay-1];
        dayTextView.setText(dayMap.get(dbQuery));
        selectedSemester = semesterAdapter.getItem(position).toString();
        mAdapter.setCursor(timeTableDataBaseHelper.getDaySem(selectedSemester, dbQuery));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
