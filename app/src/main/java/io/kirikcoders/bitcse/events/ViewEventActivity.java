package io.kirikcoders.bitcse.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.kirikcoders.bitcse.R;

public class ViewEventActivity extends AppCompatActivity {
    private TextView showEventHeadline,showEventDescription,showEventTime,showEventDate,
    showEventCost,showEventVenue,showEventCreator,showEventParticipation,showEventContactOne,showEventContactTwo;
    private ImageView eventBanner;
    private TextView showEventName;
    private String eventName;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("events");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(eventName);
        setContentView(R.layout.activity_view_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showEventHeadline = findViewById(R.id.showEventHeadline);
        eventBanner = findViewById(R.id.showEventBanner);
        showEventDescription = findViewById(R.id.showEventDescription);
        showEventTime = findViewById(R.id.showEventTime);
        showEventDate = findViewById(R.id.showEventDate);
        showEventCost = findViewById(R.id.showEventCost);
        showEventVenue = findViewById(R.id.showEventVenue);
        showEventCreator = findViewById(R.id.showEventCreator);
        showEventParticipation = findViewById(R.id.showEventParticipants);
        showEventContactOne = findViewById(R.id.showEventContactOne);
        showEventContactTwo = findViewById(R.id.showEventContactTwo);
        showEventName = findViewById(R.id.showEventName);
        eventName = getIntent().getStringExtra("event");
        showEventName.setText(eventName);
        setTextViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_assignment_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setTextViews() {
        reference.child(eventName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showEventCost.setText(dataSnapshot.child("cost").getValue().toString());
                showEventParticipation.setText(dataSnapshot.child("participants").getValue().toString());
                showEventHeadline.setText(dataSnapshot.child("headline").getValue().toString());
                showEventDescription.setText(dataSnapshot.child("description").getValue().toString());
                showEventDate.setText(dataSnapshot.child("date").getValue().toString());
                showEventTime.setText(dataSnapshot.child("time").getValue().toString());
                showEventVenue.setText(dataSnapshot.child("venue").getValue().toString());
                showEventCreator.setText(dataSnapshot.child("owner").getValue().toString());
                showEventContactOne.setText(dataSnapshot.child("contactOne").getValue().toString());
                showEventContactTwo.setText(dataSnapshot.child("contactTwo").getValue().toString());
                String imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .into(eventBanner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
