package io.kirikcoders.bitcse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.kirikcoders.bitcse.events.CreateEventActivity;
import io.kirikcoders.bitcse.events.EventAdapter;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class EventFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event,container,false);
        floatingActionButton = rootView.findViewById(R.id.add_event_fab);
        recyclerView = rootView.findViewById(R.id.event_recycler_view);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateEventActivity.class));
            }
        });
        return rootView;
    }
    public void setupRecyclerView(EventAdapter adapter){
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
