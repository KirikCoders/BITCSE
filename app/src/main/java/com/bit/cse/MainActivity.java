package com.bit.cse;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



import java.lang.reflect.Field;

import menu.room;
import proguard.annotation.KeepClassMembers;


/*This activity is the activity that holds all fragments
* An app drawer is set up for navigation
* Each fragment is deployed according to the selection in nav drawer*/

@Keep
@proguard.annotation.Keep
@KeepClassMembers
public class MainActivity extends AppCompatActivity implements  nameFragment.OnFragmentInteractionListener,changeDb.OnFragmentInteractionListener ,dayRoom.OnFragmentInteractionListener, room.OnFragmentInteractionListener, dayFaculty.OnFragmentInteractionListener, daySem.OnFragmentInteractionListener,DisplayEntireWeek.OnFragmentInteractionListener {
    DatabaseHelper myDbHelper;
   public static boolean firstRun=true;
    public static boolean firstRun2=true;
    static String toolbarTitle="";
    DrawerLayout  drawerLayout;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView toolbarText;

    CleverTapManager cleverTapManager;
    ImageView locButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        navigationView=(NavigationView) findViewById(R.id.navigationView);

        SharedPreferences sp = getSharedPreferences("shared", Activity.MODE_PRIVATE);
        firstRun = sp.getBoolean("firstRun", true);

        DatabaseHelper.DB_NAME=sp.getString("db","2017e");

      toolbarText=(TextView)findViewById(R.id.toolbarText);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);

        cleverTapManager=new CleverTapManager(MainActivity.this);


        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                InputMethodManager mgr = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);

                MenuItem menuItem= navigationView.getMenu().findItem(R.id.change);

                Log.v("hamburger","menum"+navigationView.getMenu());



            }
        };




//Initially name fragment is deployed
       drawerLayout.setDrawerListener(actionBarDrawerToggle);
        Drawable d = toolbar.getNavigationIcon();

        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,new daySem());
     //   toolbarText.setText("Search by Faculty name");
        fragmentTransaction.commit();


//Seletion in nav drawer determines the switch and accordingly a function is called
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.name:
                       nameFragment(item);
                       cleverTapManager.FragmentChosen("NameFragment");
                        break;
                    case R.id.room:
                        roomFragment(item);
                        cleverTapManager.FragmentChosen("RoomFragment");
                        break;
                    case R.id.sem:
                        semFragment(item);
                        cleverTapManager.FragmentChosen("SemFragment");
                        break;
                    case R.id.faculty:

                        facultyFragment(item);
                        cleverTapManager.FragmentChosen("FacultyFragment");
                        break;
                    case R.id.dayRoom:
                        dayRoomFragment(item);
                        cleverTapManager.FragmentChosen("DayRoomFragment");
                        break;

                    case R.id.change:
                        changeDB(item);
                        cleverTapManager.FragmentChosen("ChangeDBFragment");
                        break;


                }




                return true;
            }
        });






    }
    public ImageButton getNavButtonView(Toolbar toolbar) {
        try {
            Class<?> toolbarClass = Toolbar.class;
            Field navButtonField = toolbarClass.getDeclaredField("mNavButtonView");
            navButtonField.setAccessible(true);
            ImageButton navButtonView = (ImageButton) navButtonField.get(toolbar);

            return navButtonView;
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

// Functions for deploying req fragment
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    void roomFragment(MenuItem item)
    {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new room());
        fragmentTransaction.commit();
        item.setChecked(true);

        drawerLayout.closeDrawers();

    }

    void nameFragment(MenuItem item)
    {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new nameFragment());
        fragmentTransaction.commit();

        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

     void nameFragment()
    {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new nameFragment());
        fragmentTransaction.commit();

    }
    void facultyFragment(MenuItem item)
    {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new dayFaculty());
        fragmentTransaction.commit();

      //  toolbarText.setTextColor(555555);
        item.setChecked(true);
        drawerLayout.closeDrawers();
    }
    void semFragment(MenuItem item)
    {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new daySem());
        fragmentTransaction.commit();

        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    void dayRoomFragment(MenuItem item)
    {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new dayRoom());
        fragmentTransaction.commit();

        item.setChecked(true);
        drawerLayout.closeDrawers();
    }


    void changeDB(MenuItem item)
    {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new changeDb());
        fragmentTransaction.commit();


        item.setChecked(true);
        drawerLayout.closeDrawers();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void freeMemory()
    {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();


    }
    void setToolbarText(String s)
    {

        toolbarText.setText(s);
    }
    @Override
    protected void onPause() {
        super.onPause();
        freeMemory();
        SharedPreferences sp = getSharedPreferences("shared", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("firstRun", firstRun);
        Log.d("firstrun","Main activity on pause "+firstRun);
        editor.putString("db",DatabaseHelper.DB_NAME);
        editor.apply();


    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        Log.v("1release ver","frag count in stack== "+getFragmentManager().getBackStackEntryCount());


      //  if (getFragmentManager().getBackStackEntryCount() > 0  ) {
      //      int index = getFragmentManager().getBackStackEntryCount() - 1;
      //      FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
      //      String tag = backEntry.getName();
      //      Fragment fragment = getFragmentManager().findFragmentByTag(tag);
      //      Log.v("1release ver","frag id= "+fragment.getTag());
      //      if( fragment.getId()==R.layout.fragment_display_entire_week) {
      //          Log.v("1release","in level2 id="+fragment.getTag());
      //          getFragmentManager().popBackStack();
      //      }
//
//
      //  //    Toast.makeText(getApplicationContext(),""+tag,Toast.LENGTH_SHORT).show();
      //    //  new DisplayEntireWeek().changeFragment("asd");
      //  } else {
            super.onBackPressed();
       // }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        getMenuInflater().inflate(R.menu.drawer_menu, menu);
      locButton = (ImageButton) menu.findItem(R.id.change).getActionView();


        return true;
    }
*/
}
