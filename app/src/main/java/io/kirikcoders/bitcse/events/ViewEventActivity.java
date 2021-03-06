package io.kirikcoders.bitcse.events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.kirikcoders.bitcse.R;
import io.kirikcoders.bitcse.database.DataBaseHelper;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

public class ViewEventActivity extends AppCompatActivity {
    private TextView showEventHeadline,showEventDescription,showEventTime,showEventDate,
    showEventCost,showEventVenue,showEventCreator,showEventParticipation,showEventContactOne,showEventContactTwo;
    private ImageView eventBanner;
    private TextView showEventName;
    private String eventName;
    private UserDetails userDetails;
    private DataBaseHelper dataBaseHelper;
    private String imageUrl;
    private String time,date,phone,venue,descreption;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.EVENT_DATABASE),
    registrationReference = FirebaseDatabase.getInstance().getReference(Constants.REGISTERED_DATABASE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mPrefs = getBaseContext().getSharedPreferences(Constants.USER_PREFERENCE_FILE, Context.MODE_PRIVATE);
        int mode = mPrefs.getInt("MODE",-1);
        getDelegate().setLocalNightMode(mode);
        setTitle(eventName);
        setContentView(R.layout.activity_view_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userDetails = new UserDetails(ViewEventActivity.this,Constants.USER_PREFERENCE_FILE);
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
                Calendar calendar= Calendar.getInstance();
                String paths[]= date.split("/");
                calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(paths[0]));
                calendar.set(Calendar.MONTH,Integer.parseInt(paths[1])-1);
                calendar.set(Calendar.YEAR,Integer.parseInt(paths[2]));
                String timePaths[]=time.split(":");
                int hh=Integer.parseInt(timePaths[0]);
                int mm=Integer.parseInt(timePaths[1].split(" ")[0]);
                if(timePaths[1].split(" ")[1].equals("pm")&& hh>12){
                 hh+=12;
                }
                calendar.set(Calendar.HOUR_OF_DAY,hh);
                calendar.set(Calendar.MINUTE,mm);

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventActivity.this);
                EditText text = new EditText(builder.getContext());
                text.setHint("No. of participants?");
                builder.setView(text)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        registrationReference.child(eventName).child(userDetails.getmUsn()).child("name")
                                .setValue(userDetails.getmName());
                        registrationReference.child(eventName).child(userDetails.getmUsn()).child("phone")
                                .setValue(userDetails.getmPhoneNumber());
                        registrationReference.child(eventName).child(userDetails.getmUsn()).child("participants")
                                .setValue(text.getText().toString());
                        Toast.makeText(ViewEventActivity.this, "You have beem registered!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.Events.TITLE, eventName)
                                .putExtra(CalendarContract.Events.EVENT_LOCATION,venue )
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calendar.getTime().getTime() )
                                .putExtra(CalendarContract.Events.DESCRIPTION,(descreption+"\nVenue:"+venue+"\nPhone:"+phone));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }

    private void setTextViews() {
        reference.child(eventName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("time").getValue() != null) {
                    time = dataSnapshot.child("time").getValue().toString();
                    venue = dataSnapshot.child("venue").getValue().toString();
                    descreption = dataSnapshot.child("description").getValue().toString();
                    System.out.println(time);
                    date = dataSnapshot.child("date").getValue().toString();
                    phone = dataSnapshot.child("contactOne").getValue().toString();
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
                    imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                    Glide.with(getApplicationContext())
                            .load(imageUrl)
                            .into(eventBanner);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
