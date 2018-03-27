package com.bit.cse;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
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
import java.util.HashSet;
import java.util.List;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dayRoom.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dayRoom#newInstance} factory method to
 * create an instance of this fragment.
 */

@KeepClassMembers
@Keep
@android.support.annotation.Keep
@KeepImplementations
public class dayRoom extends Fragment implements DisplayEntireWeek.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner roomSpinner,daySpinner;
    ListView l;
    Button search,displayEntireWeek;
    DatabaseHelper myDbHelper;
    Cursor roomList,res;
    TextView toolbarText;
    public static   ArrayList<display> displayArrayList;
    private OnFragmentInteractionListener mListener;

    public dayRoom() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dayRoom.
     */
    // TODO: Rename and change types and number of parameters
    public static dayRoom newInstance(String param1, String param2) {
        dayRoom fragment = new dayRoom();
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
        View view=inflater.inflate(R.layout.fragment_day_room, container, false);
        databaseCaller();
    toolbarText=(TextView)getActivity().findViewById(R.id.toolbarText);
        toolbarText.setText("Day schedule :Room");
        roomSpinner=(Spinner)view.findViewById(R.id.dayRoom_Roomspinner);
        daySpinner=(Spinner)view.findViewById(R.id.dayRoom_daySpinner);
        search=(Button)view.findViewById(R.id.dayRoomSearchButton);
        displayEntireWeek=(Button)view.findViewById(R.id.dayRoomDisplayEntireWeekButton);
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


        List<String> roomCategoriesTemp = new ArrayList<String>();
        roomList=myDbHelper.getRoom();
        while(roomList.moveToNext())
        {
            if(roomList.getString(0).length()<=3)
            roomCategoriesTemp.add(roomList.getString(0));
            else
                roomCategoriesTemp.add(roomList.getString(0).substring(0,roomList.getString(0).length()-1));
        }
        ArrayList<String> roomCategories =new ArrayList<String>(new HashSet<String>(roomCategoriesTemp));
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roomCategories);

        // Drop down layout style - list view with radio button
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        roomSpinner.setAdapter(roomAdapter);


        final String[] customDay = new String[1];
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customDay[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String[] customRoom = new String[1];
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customRoom[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                res=myDbHelper.getDayRoom(customRoom[0],customDay[0].substring(0,3).toUpperCase());
                displayArrayList  =new ArrayList<display>();
                while (res.moveToNext()) {

                    displayArrayList.add(new display(res.getString(0), res.getString(1), res.getString(2), res.getString(3)));
                }

                int o=0;
                while (o<displayArrayList.size())
                {

                    if(Integer.parseInt(displayArrayList.get(o).string1)>11) {
                        if (o + 1 < displayArrayList.size()) {

                            if (displayArrayList.get(o).string1.equals(displayArrayList.get(o + 1).string1)) {

                                String i = displayArrayList.get(o).string3;

                                String j = displayArrayList.get(o + 1).string3;

                                i = i.concat("\n");
                                i = i.concat(j);

                                display d = displayArrayList.get(o);
                                d.string3 = i;
                                String te = d.string2.substring(0, d.string2.length());
                                //   d.string2=te;
                                displayArrayList.set(o, d);
                                displayArrayList.remove(o + 1);
                                o--;
                            }



                        } else {



                        }
                    }
                    o++;
                }


                ArrayList<String> head_parameters=new ArrayList<String>();
                head_parameters.add("Slot");
                head_parameters.add("Sem");
                head_parameters.add("Faculty");
                head_parameters.add("Subject");
                View view = getActivity().getLayoutInflater().inflate(R.layout.list_view_gen, null);
                DisplayMetrics metrics = new DisplayMetrics();
                ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                display_dialog_general popup=new display_dialog_general(displayArrayList,view,head_parameters,getContext(),metrics);
                popup.showDialog();





            }
        });


        displayEntireWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayEntireWeek fragment=new DisplayEntireWeek();
                Bundle b=new Bundle();
                b.putString("incoming","dayRoom");
                b.putString("data",customRoom[0]);
                fragment.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.container, fragment).addToBackStack("dayRoom")
                        .commit();
            }
        });

        return view;
    }


    void databaseCaller()
    {
        myDbHelper  = new DatabaseHelper(getContext());
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
}
