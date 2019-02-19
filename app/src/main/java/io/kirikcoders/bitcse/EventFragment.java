package io.kirikcoders.bitcse;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import io.kirikcoders.bitcse.events.CreateEventActivity;
import io.kirikcoders.bitcse.events.EventAdapter;
import io.kirikcoders.bitcse.events.MyEventsAdapter;
import io.kirikcoders.bitcse.tools.MarksActivity;

/**
 * Created by Kartik on 24-Jul-18.
 */
public class EventFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private LottieAnimationView lottieAnimationView;
    private TextView errorMessageTextView;
    private EventAdapter adapter;
    private MyEventsAdapter ad;
    private ImageView Netimage;
    private TextView NetText;
    private boolean network;
    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        floatingActionButton = rootView.findViewById(R.id.add_event_fab);
        recyclerView = rootView.findViewById(R.id.event_recycler_view);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        errorMessageTextView = rootView.findViewById(R.id.errorMessage);
        lottieAnimationView = rootView.findViewById(R.id.loadingAnimation);
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                if(network==false){
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Dismiss", v -> {

                        }).show();}
            }
        }, 5000);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().toString().equals("All Events")){
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else if(tab.getText().toString().equals("My Events")){
                    if(network==false){Toast.makeText(getContext(),"Requires connection to Internet",Toast.LENGTH_LONG).show();}
                    else {
                        recyclerView.setAdapter(ad);
                        if (ad != null)
                            ad.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        floatingActionButton.setOnClickListener((view) -> startActivity(new Intent(getActivity(), CreateEventActivity.class)));
        return rootView;
    }

    public void setupRecyclerView(EventAdapter adapter,boolean net) {
        this.adapter=adapter;
        network=net;
                if (recyclerView != null) {
                    errorMessageTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    lottieAnimationView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
             }
        }

    public void setErrorMessage(String message) {
        try {
            errorMessageTextView.setVisibility(View.VISIBLE);
            lottieAnimationView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.GONE);
            errorMessageTextView.setText(message);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void setMyEventsAdapter(MyEventsAdapter ad) {
        this.ad=ad;


    }

}

