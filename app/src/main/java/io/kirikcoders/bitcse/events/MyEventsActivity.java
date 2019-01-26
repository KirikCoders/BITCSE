package io.kirikcoders.bitcse.events;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;

/**
 * Created by Abhishek on 18-Jan-19.
 */


public class MyEventsActivity extends AppCompatActivity {
    ListView events_list;
    FirebaseListAdapter adapter;
    private TextView showEventName;
    private String eventName;
    private ImageView eventBanner;
    private DataSnapshot dataSnapshot;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        showEventName = findViewById(R.id.showEventName);
        eventName = getIntent().getStringExtra("event");
        setTitle(eventName);
        showEventName.setText("Users registered for "+eventName);

        events_list=(ListView) findViewById(R.id.my_events_list);
        Query query= FirebaseDatabase.getInstance().getReference().child("events_registered").child(eventName);
        FirebaseListOptions<MyEventsModel> options=new FirebaseListOptions.Builder<MyEventsModel>()
                .setLayout(R.layout.my_events_item)
                .setQuery(query,MyEventsModel.class)
                .build();
        System.out.println(((DatabaseReference) query).getKey());
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name=v.findViewById(R.id.showName);
                TextView numberOfParticipants= v.findViewById(R.id.showParticipants);
                TextView phoneNum=v.findViewById(R.id.showPhone);
                MyEventsModel myEvents=(MyEventsModel) model;
               name.setText("Name:-"+myEvents.getName());
                numberOfParticipants.setText(("Participants:-"+myEvents.getParticipants()));
                phoneNum.setText("Phone:-"+myEvents.getPhone());


            }
        };
        events_list.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

