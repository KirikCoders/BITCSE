package io.kirikcoders.bitcse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import io.kirikcoders.bitcse.tools.Attendence;
import io.kirikcoders.bitcse.tools.FacultyDetails;
import io.kirikcoders.bitcse.tools.FindRoomActivity;
import io.kirikcoders.bitcse.tools.Marks;
import io.kirikcoders.bitcse.tools.SGPAActivity;
import io.kirikcoders.bitcse.events.EventAdapter;
import io.kirikcoders.bitcse.events.MyEventsAdapter;
import io.kirikcoders.bitcse.tools.FindProfessor.SearchFaculty;
import io.kirikcoders.bitcse.tools.StudentDetails.StudentInfo;
import io.kirikcoders.bitcse.utils.Constants;
import io.kirikcoders.bitcse.utils.UserDetails;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_WRITE_EXT_STORAGE = 1;
    private FirebaseAuth mAuth;
    private UserDetails userDetails;
    private ViewPager pager;
    private HomePagerAdapter adapter;
    private BottomNavigationView navigation;
    private MenuItem prevMenuItem;
    private DatabaseReference referenceEvents = FirebaseDatabase.getInstance().getReference("events");
    private ArrayList<URL> imageUrl = new ArrayList<>(20);
    private ArrayList<String> eventName = new ArrayList<>(20);
    private EventAdapter mAdapter;
    private MyEventsAdapter ad;
    private ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                navigation.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: " + position);
            navigation.getMenu().getItem(position).setChecked(true);
            prevMenuItem = navigation.getMenu().getItem(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    getSupportActionBar().setElevation(0);
                    pager.setCurrentItem(0);
                    getCurrentEventsFromFirebase(getCurrentFocus());
                    return true;
                case R.id.navigation_tools:
                    getSupportActionBar().setElevation(1);
                    pager.setCurrentItem(1);
                    return true;
                case R.id.navigation_time_table:
                    getSupportActionBar().setElevation(1);
                    pager.setCurrentItem(2);
                    return true;
                case R.id.navigation_notifications:
                    getSupportActionBar().setElevation(1);
                    pager.setCurrentItem(3);
                    return true;
                case R.id.navigation_settings:
                    getSupportActionBar().setElevation(1);
                    pager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get user permissions for storage, etc if not granted
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_WRITE_EXT_STORAGE);
        }
        getSupportActionBar().setElevation(0);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(this,LoginActivity.class));
        }
        pager = findViewById(R.id.pager);
        userDetails =  new UserDetails(getApplicationContext(),Constants.USER_PREFERENCE_FILE);
        adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventFragment());
        adapter.addFragment(new ToolsFragment());
        adapter.addFragment(new TimeTableFragment());
        adapter.addFragment(new NotificationsFragment());
        adapter.addFragment(new SettingsFragment());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(changeListener);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void getCurrentEventsFromFirebase(View view) {
        referenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageUrl.clear();
                eventName.clear();
                for (DataSnapshot s:dataSnapshot.getChildren()){
                    eventName.add(s.getKey());
                    try {
                        System.out.println("value exists="+s.child("imageUrl").exists());
                        imageUrl.add(new URL(s.child("imageUrl").getValue().toString()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e){
                        e.printStackTrace();

                    }
                }
                mAdapter = new EventAdapter(getApplicationContext(),imageUrl,eventName);
                EventFragment eventFragment = (EventFragment) adapter.getFragment(0);
                eventFragment.setupRecyclerView(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUserRegisteredEventsFromDb(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentEventsFromFirebase(getCurrentFocus());
        getMyEventsFromFirebase(getCurrentFocus());

    }

    public void getMyEventsFromFirebase(View view) {
        ArrayList<URL> images= new ArrayList<>();
        ArrayList<String> eventNames= new ArrayList<>();
        referenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot s:dataSnapshot.getChildren()){
                    if (s.child("owner").getValue().toString().equals(userDetails.getmUsn())) {
                        eventNames.add(s.getKey());
                        try {
                            System.out.println("value exists=" + s.child("imageUrl").exists());
                            images.add(new URL(s.child("imageUrl").getValue().toString()));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();

                        }
                    }
                }
                if (imageUrl.size() == 0)
                    displayNoDataImage();
                else {
                    ad = new MyEventsAdapter(getApplicationContext(), images, eventNames);
                    EventFragment eventFragment = (EventFragment) adapter.getFragment(0);
                    eventFragment.setMyEventsAdapter(ad);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayNoDataImage() {
        EventFragment eventFragment = (EventFragment) adapter.getFragment(0);
        eventFragment.setErrorMessage("No data available");
    }
    private void displayNoNetworkImage(){

    }
    public void facultyDetailsClick(View v)
    {
        Intent i=new Intent(this,FacultyDetails.class);
        startActivity(i);
    }

    public void clickSgpa(View v)
    {
        Intent i=new Intent(this,SGPAActivity.class);
        startActivity(i);
    }

    public void clickAttendance(View v)
    {
        Intent i=new Intent(this, Attendence.class);
        startActivity(i);
    }

    public void temp(View v)
    {
        Toast.makeText(this, "Docs will be added in the next release", Toast.LENGTH_SHORT).show();
    }

    public void clickMarks(View v)
    {
        Intent i=new Intent(this, Marks.class);
        startActivity(i);
    }

    public void clickFacSearch(View v)
    {
        Intent i=new Intent(this, SearchFaculty.class);
        startActivity(i);
    }
    public void findRoom(View v){
        Intent i = new Intent(this, FindRoomActivity.class);
        startActivity(i);
    }

    public void clickStudentinfo(View v)
    {
        Intent i=new Intent(this, StudentInfo.class);
        startActivity(i);
    }
}
