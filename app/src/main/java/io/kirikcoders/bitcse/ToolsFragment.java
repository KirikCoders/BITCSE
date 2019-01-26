package io.kirikcoders.bitcse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class ToolsFragment extends Fragment {
    RelativeLayout student;
    RelativeLayout internal;
    RelativeLayout attendance;
    RelativeLayout sgpa;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tools,container,false);
        student=rootView.findViewById(R.id.studentinfo_rl);
        internal=rootView.findViewById(R.id.relative_internal);
        attendance=rootView.findViewById(R.id.relative_attendance);
        sgpa=rootView.findViewById(R.id.relative_sgpa);
        UserDetails obj=new UserDetails(getContext(), Constants.USER_PREFERENCE_FILE);
        if(obj.isProfessor()==false)
        {
            student.setVisibility(student.INVISIBLE);
        }
        else
        {
            internal.setVisibility(internal.GONE);
            attendance.setVisibility(attendance.GONE);
            sgpa.setVisibility(sgpa.GONE);
        }
        return rootView;
    }
}
