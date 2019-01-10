package io.kirikcoders.bitcse.timetable;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.TimeTableFragment;
import io.kirikcoders.bitcse.database.TimeTableDataBaseHelper;

public class SubjectFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public SubjectFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subject_list,container,false);
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
        return rootView;
    }
}
