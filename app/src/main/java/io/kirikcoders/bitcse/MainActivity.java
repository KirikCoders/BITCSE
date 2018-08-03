package io.kirikcoders.bitcse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isLoggedIn = false;
    private ViewPager pager;
    private HomePagerAdapter adapter;
    private BottomNavigationView navigation;
    private MenuItem prevMenuItem;
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
        getSupportActionBar().setElevation(0);
        if (!isLoggedIn)
            loginOrSignUp();
        pager = findViewById(R.id.pager);
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

    private void loginOrSignUp() {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

}
