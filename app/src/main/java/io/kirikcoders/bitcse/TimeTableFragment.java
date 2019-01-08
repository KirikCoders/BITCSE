package io.kirikcoders.bitcse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class TimeTableFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner semSpinner;
    private Spinner sectionSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time_table,container,false);
        semSpinner = rootView.findViewById(R.id.time_table_sem);
        sectionSpinner = rootView.findViewById(R.id.time_table_section);
        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(getContext(),R.array.semesters,
                android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.sections,
                android.R.layout.simple_spinner_dropdown_item);
        semSpinner.setAdapter(semesterAdapter);
        sectionSpinner.setAdapter(sectionAdapter);
        semSpinner.setOnItemSelectedListener(this);
        sectionSpinner.setOnItemSelectedListener(this);
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TimeTableFragment","Spinner");
        Log.i("parent",parent.toString());
        Log.i("view",view.toString());
        Log.i("parent", String.valueOf(position));
        Log.i("parent", String.valueOf(id));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
