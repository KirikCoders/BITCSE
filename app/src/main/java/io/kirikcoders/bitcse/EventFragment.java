package io.kirikcoders.bitcse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.kirikcoders.bitcse.events.CreateEventActivity;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class EventFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event,container,false);
        floatingActionButton = rootView.findViewById(R.id.add_event_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateEventActivity.class));
            }
        });
        return rootView;
    }
}
