package io.kirikcoders.bitcse.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.database.DataBaseHelper;
import io.kirikcoders.bitcse.utils.Constants;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by Abhishek on 18-Jan-19.
 */

public class MyEventsActivity extends AppCompatActivity {

    ListView eventList;
    FirebaseListAdapter adapter;
    private String eventName;
    private TextView showEventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        eventList=(ListView) findViewById(R.id.my_events_list);
        eventName = getIntent().getStringExtra("event");
        showEventName=(TextView)findViewById(R.id.showEventName);
        Query query= FirebaseDatabase.getInstance().getReference().child("events_registered").child(eventName);
        showEventName.setText("Users Registered for:\n"+eventName);
        setTitle(eventName);
        FirebaseListOptions<MyEventsModel> options=new FirebaseListOptions.Builder<MyEventsModel>()
                .setLayout(R.layout.my_events_item)
                .setQuery(query,MyEventsModel.class)
                .build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name=v.findViewById(R.id.showName);
                TextView participants=v.findViewById(R.id.showParticipants);
                TextView phone=v.findViewById(R.id.showPhone);

                MyEventsModel myEventsModel=(MyEventsModel) model;

                name.setText(myEventsModel.getName().toString());
                participants.setText(myEventsModel.getParticipants().toString()+"\t\t");

                phone.setTextIsSelectable(true);
                phone.setText(myEventsModel.getPhone().toString());


            }
        };
        eventList.setAdapter(adapter);
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
