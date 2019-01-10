package io.kirikcoders.bitcse;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import io.kirikcoders.bitcse.database.TimeTableDataBaseHelper;
import io.kirikcoders.bitcse.timetable.SubjectFragment;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class TimeTableFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner semSectionSpinner;
    private TimeTableDataBaseHelper timeTableDataBaseHelper;
    private ViewPager viewPager;
    private TextView dayTextView;
    private UserDetails userDetails;
    private HomePagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time_table,container,false);
        semSectionSpinner = rootView.findViewById(R.id.time_table_sem);
        viewPager = rootView.findViewById(R.id.time_table);
        dayTextView = rootView.findViewById(R.id.day_shower);
        userDetails = new UserDetails(getContext(),Constants.USER_PREFERENCE_FILE);
        timeTableDataBaseHelper = new TimeTableDataBaseHelper(getContext());
        pagerAdapter = new HomePagerAdapter(getFragmentManager());
        SubjectFragment subjectFragment = new SubjectFragment();
        pagerAdapter.addFragment(subjectFragment);
        viewPager.setAdapter(pagerAdapter);
        // create the database by copying it into /data/data/ folder
        // if it is already created, the helper will automatically ignore the operation and continue
        // to openDatabase()
        try {
            timeTableDataBaseHelper.createDatabase();
            timeTableDataBaseHelper.openDatabase();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ArrayAdapter<CharSequence> semesterAdapter = new ArrayAdapter<CharSequence>(getContext(),
                R.layout.support_simple_spinner_dropdown_item);
        semesterAdapter.add(userDetails.getmSemester());
        semesterAdapter.addAll(convertCursorToList(timeTableDataBaseHelper.getSem()));
        semSectionSpinner.setAdapter(semesterAdapter);
        semSectionSpinner.setOnItemSelectedListener(this);
        return rootView;
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent != null && view != null) {
            Log.d("TimeTableFragment", "Spinner");
            Log.i("parent", parent.toString());
            Log.i("view", view.toString());
            Log.i("parent", String.valueOf(position));
            Log.i("parent", String.valueOf(id));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
