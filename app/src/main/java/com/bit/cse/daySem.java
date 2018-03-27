package com.bit.cse;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import showcaseView.ChainTourGuide;
import showcaseView.Overlay;
import showcaseView.Sequence;
import showcaseView.ToolTip;
import showcaseView.TourGuide;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link daySem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link daySem#newInstance} factory method to
 * create an instance of this fragment.
 */

@Keep
@android.support.annotation.Keep
@KeepClassMembers
@KeepImplementations
public class daySem extends Fragment implements DisplayEntireWeek.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static   ArrayList<display> displayArrayList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView toolbarText;
    Spinner semSpinner,daySpinner;//Spinners for selecting day and sem
    Button search,viewEntireWeek;
    DatabaseHelper myDbHelper;
    ListView l;
    RecyclerView recyclerView;
   // public ArrayList<String> res_sem;
    Cursor semList,res;
    Toolbar toolbar;
    private OnFragmentInteractionListener mListener;
    View hamburger;

    public daySem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment daySem.
     */
    // TODO: Rename and change types and number of parameters
    public static daySem newInstance(String param1, String param2) {
        daySem fragment = new daySem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_day_sem, container, false);
        databaseCaller();

toolbarText=(TextView)getActivity().findViewById(R.id.toolbarText);
        toolbar=(Toolbar)getActivity().findViewById(R.id.mainToolbar);

        hamburger= toolbar.getChildAt(1);
        toolbarText.setText("Day schedule : Sem");
        semSpinner=(Spinner)view.findViewById(R.id.daySem_Semspinner);
        daySpinner=(Spinner)view.findViewById(R.id.daySem_daySpinner);
        search=(Button)view.findViewById(R.id.daySemSearchButton);
        viewEntireWeek=(Button)view.findViewById(R.id.daySemViewEntireWeekButton);
        if(MainActivity.firstRun)
            tour1();
        List<String> categories = new ArrayList<String>();
        categories.add("Monday");
        categories.add("Tuesday");
        categories.add("Wednesday");
        categories.add("Thursday");
        categories.add("Friday");
        categories.add("Saturday");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        daySpinner.setAdapter(dataAdapter);


        List<String> semCtegories = new ArrayList<String>();
        semList=myDbHelper.getSem();
        while(semList.moveToNext())
        {
            semCtegories.add(semList.getString(0));
        }
        ArrayAdapter<String> semAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, semCtegories);

        // Drop down layout style - list view with radio button
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        semSpinner.setAdapter(semAdapter);
        final String[] customDay = new String[1];

        //get selected day and put it into customDay
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customDay[0] = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //select sem selected and put it into customSem
        final String[] customSem = new String[1];
        semSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customSem[0] = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                res=myDbHelper.getDaySem(customSem[0],customDay[0].substring(0,3).toUpperCase());

                displayArrayList  =new ArrayList<display>();
                while (res.moveToNext()) {
                    displayArrayList.add(new display(res.getString(0), res.getString(1), res.getString(2), res.getString(3)));
                }

                int o=0;
               while (o<displayArrayList.size())
               {
                   if(Integer.parseInt(displayArrayList.get(o).string1)>11)
                   {
                       String room1=displayArrayList.get(o).string2;
//
                       String room2="";
                       if(o+1<displayArrayList.size())
                       {
                           room2=displayArrayList.get(o+1).string2;
                       }

                       if(room1.equals(room2))
                       {
                           String faculty1 = displayArrayList.get(o).string3;

                           String faculty2 = displayArrayList.get(o + 1).string3;

                           faculty1=faculty1.concat("\n");
                           faculty1= faculty1.concat(faculty2);

                           display d=displayArrayList.get(o);
                           d.string3=faculty1;
                        String te=   d.string2.substring(0,d.string2.length());
                       //   d.string2=te;
                           displayArrayList.set(o,d);
                           displayArrayList.remove(o + 1);
                           o--;
                       }
                    }
                   o++;
                   }

                ArrayList<String> head_parameters=new ArrayList<String>();
                head_parameters.add("Slot");
                head_parameters.add("Room no");
                head_parameters.add("Faculty");
                head_parameters.add("Subject");
                View view = getActivity().getLayoutInflater().inflate(R.layout.list_view_gen, null);
                DisplayMetrics metrics = new DisplayMetrics();
                ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                display_dialog_general popup=new display_dialog_general(displayArrayList,view,head_parameters,getContext(),metrics);
                popup.showDialog();
               }


        });




        viewEntireWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DisplayEntireWeek fragment=new DisplayEntireWeek();
                Bundle b=new Bundle();
                b.putString("incoming","daySem");
                b.putString("data",customSem[0]);
                fragment.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.container, fragment).addToBackStack("daySem")
                        .commit();

                Log.v("1release ver","in day sem frag count in stack== "+getFragmentManager().getBackStackEntryCount());

            }
        });

        return view;
    }



    void databaseCaller() {
        myDbHelper = new DatabaseHelper(getContext());
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    void tour1()
    {

        MainActivity.firstRun=false;
        Log.d("firstrun","In daysem tour1() value="+MainActivity.firstRun);

        View v= new MainActivity().getNavButtonView(toolbar);

        ChainTourGuide tourGuide1 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Tour")
                        .setDescription("Click this button to view the entire week schedule of the selected class")
                        .setGravity(Gravity.TOP)
                )
                // note that there is no Overlay here, so the default one will be used
                .playLater(viewEntireWeek);

        ChainTourGuide tourGuide2 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Menu Tutorial")
                        .setDescription("Press the button to get other options")
                        .setGravity(Gravity.BOTTOM | Gravity.RIGHT)
                        .setBackgroundColor(Color.parseColor("#c0392b"))
                )
                .setOverlay(new Overlay()
                                .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        //    .setEnterAnimation(mEnterAnimation)
                        //   .setExitAnimation(mExitAnimation)
                ).with(TourGuide.Technique.HORIZONTAL_LEFT)
                .playLater(v);


        Sequence sequence = new Sequence.SequenceBuilder()
                .add(tourGuide1, tourGuide2)
                .setDefaultOverlay(new Overlay()
                        //   .setEnterAnimation(mEnterAnimation)
                        //   .setExitAnimation(mExitAnimation)
                )
                .setDefaultPointer(null)
                .setContinueMethod(Sequence.ContinueMethod.OVERLAY)
                .build();


        ChainTourGuide.init(getActivity()).playInSequence(sequence);
    }

}

