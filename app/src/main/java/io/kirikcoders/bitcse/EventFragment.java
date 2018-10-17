package io.kirikcoders.bitcse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.kirikcoders.bitcse.events.CreateEventActivity;
import io.kirikcoders.bitcse.events.EventAdapter;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class EventFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private LottieAnimationView lottieAnimationView;
    private TextView errorMessageTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event,container,false);
        floatingActionButton = rootView.findViewById(R.id.add_event_fab);
        recyclerView = rootView.findViewById(R.id.event_recycler_view);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        errorMessageTextView = rootView.findViewById(R.id.errorMessage);
        lottieAnimationView = rootView.findViewById(R.id.loadingAnimation);
        floatingActionButton.setOnClickListener((view)-> startActivity(new Intent(getActivity(), CreateEventActivity.class)));
        return rootView;
    }
    public void setupRecyclerView(EventAdapter adapter){
        if (recyclerView != null) {
            errorMessageTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            lottieAnimationView.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }
    public void setErrorMessage(String message){
        errorMessageTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorMessageTextView.setText(message);
    }
}
