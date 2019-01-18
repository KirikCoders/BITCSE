package io.kirikcoders.bitcse.timetable;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.kirikcoders.bitcse.R;

public class MySubjectRecyclerViewAdapter extends RecyclerView.Adapter<MySubjectRecyclerViewAdapter.SubjectViewHolder> {
    private Cursor cursor;
    public MySubjectRecyclerViewAdapter(Cursor subject){
        cursor = subject;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_subject,
                parent,false);
        SubjectViewHolder viewHolder = new SubjectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        cursor.moveToFirst();
        // move cursor pointer to the correct row based on position
        for(int i=0;i<position;i++)
            cursor.moveToNext();
        holder.mSubjectName.setText(cursor.getString(cursor.getColumnIndex("sub")));
        holder.mFaculty.setText(cursor.getString(cursor.getColumnIndex("name")));
        holder.mTiming.setText(cursor.getString(cursor.getColumnIndex("timings")));
        holder.mRoom.setText(cursor.getString(cursor.getColumnIndex("room")));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        public TextView mTiming;
        public TextView mSubjectName;
        public TextView mFaculty;
        public TextView mRoom;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            mTiming = itemView.findViewById(R.id.subject_time);
            mSubjectName = itemView.findViewById(R.id.subject_name);
            mFaculty = itemView.findViewById(R.id.subject_prof);
            mRoom = itemView.findViewById(R.id.subject_room);
        }

    }
}
