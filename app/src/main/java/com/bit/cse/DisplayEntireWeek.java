package com.bit.cse;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.mutualmobile.cardstack.CardStackLayout;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayEntireWeek.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayEntireWeek#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayEntireWeek extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     String keyword,incoming;
    TextView toolbarText;
    Cursor res;
    displayArraylist[] displayArrayList;
    DatabaseHelper myDbHelper;
    private OnFragmentInteractionListener mListener;
    String day[]={"MON","TUE","WED","THU","FRI","SAT"};
Button back;
    CardStackLayout cardStackLayout;
FragmentTransaction fragmentTransaction;
View view;

    public DisplayEntireWeek() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayEntireWeek.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayEntireWeek newInstance(String param1, String param2) {
        DisplayEntireWeek fragment = new DisplayEntireWeek();
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

       Bundle bundle = this.getArguments();
       if (bundle != null) {
         incoming =getArguments().getString("incoming", "name");
           keyword=getArguments().getString("data","3c");

       }
        displayArrayList=new displayArraylist[6];
        for(int i=0;i<6;i++)
            displayArrayList[i]=new displayArraylist();


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   view=inflater.inflate(R.layout.fragment_display_entire_week, container, false);

        databaseCaller();

        toolbarText=(TextView)getActivity().findViewById(R.id.toolbarText);
        cardStackLayout =(CardStackLayout)view.findViewById(R.id.cardStackEntireWeek);

        cardStackLayout.setShowInitAnimation(true);

        //   cardStackLayout.setParallaxEnabled(pgDashboardCardPreferences.isParallaxEnabled());
        cardStackLayout.setParallaxEnabled(false);
        //  cardStackLayout.setParallaxScale(pgDashboardCardPreferences.getParallaxScale(this));
        cardStackLayout.setParallaxScale(30);

        cardStackLayout.setShowInitAnimation(true);


        //  cardStackLayout.setCardGap(Units.dpToPx(this, pgDashboardCardPreferences.getCardGap(this)));
        cardStackLayout.setCardGap((float)150);

        cardStackLayout.setCardGapBottom(50);

        switch (incoming) {
            case "daySem":
        for(int i=0;i<6;i++) {

            res = myDbHelper.getDaySem(keyword, day[i]);

            while (res.moveToNext()) {

                displayArrayList[i].displayArrayList.add(new display(res.getString(0), res.getString(1), res.getString(2), res.getString(3)));
            }

            int o = 0;
            while (o < displayArrayList[i].displayArrayList.size()) {
                if (Integer.parseInt(displayArrayList[i].displayArrayList.get(o).string1) > 11) {
                    String room1 = displayArrayList[i].displayArrayList.get(o).string2;
//
                    String room2 = "";
                    if (o + 1 < displayArrayList[i].displayArrayList.size()) {
                        room2 = displayArrayList[i].displayArrayList.get(o + 1).string2;
                    }

                    if (room1.equals(room2)) {
                        String faculty1 = displayArrayList[i].displayArrayList.get(o).string3;

                        String faculty2 = displayArrayList[i].displayArrayList.get(o + 1).string3;

                        faculty1 = faculty1.concat("\n");
                        faculty1 = faculty1.concat(faculty2);

                        display d = displayArrayList[i].displayArrayList.get(o);
                        d.string3 = faculty1;
                        String te = d.string2.substring(0, d.string2.length());
                        //   d.string2=te;
                        displayArrayList[i].displayArrayList.set(o, d);
                        displayArrayList[i].displayArrayList.remove(o + 1);
                        o--;
                    }
                }
                o++;
            }



        }
                ArrayList<String> head_parameters = new ArrayList<String>();
                head_parameters.add("Slot");
                head_parameters.add("Room no");
                head_parameters.add("Faculty");
                head_parameters.add("Subject");
                cardStackLayout.setAdapter(new EachDayDetailCardAdaptor(this, displayArrayList, head_parameters));

                break;



            case "dayFaculty":
                for(int i=0;i<6;i++) {
                    if (keyword.length() > 4)
                        res = myDbHelper.getDayFaculty(keyword, day[i]);
                    else
                        res = myDbHelper.getDayFacultyInitials(keyword, day[i]);

                    while (res.moveToNext()) {
                        //   String temp = "" + res.getString(0) + res.getInt(1) + res.getString(2) + res.getString(3);

                        displayArrayList[i].displayArrayList.add(new display(res.getString(0), res.getString(1), res.getString(2), res.getString(3)));
                    }
                    ArrayList<String> head_parameters1 = new ArrayList<String>();
                    head_parameters1.add("Slot");
                    head_parameters1.add("Room");
                    head_parameters1.add("Sem");
                    head_parameters1.add("Subject");
                    cardStackLayout.setAdapter(new EachDayDetailCardAdaptor(this, displayArrayList, head_parameters1));
                }
                break;

            case "dayRoom":
                for(int i=0;i<6;i++) {
                    res = myDbHelper.getDayRoom(keyword, day[i]);

                    while (res.moveToNext()) {

                        displayArrayList[i].displayArrayList.add(new display(res.getString(0), res.getString(1), res.getString(2), res.getString(3)));
                    }

                    int o = 0;
                    while (o < displayArrayList[i].displayArrayList.size()) {

                        if (Integer.parseInt(displayArrayList[i].displayArrayList.get(o).string1) > 11) {
                            if (o + 1 < displayArrayList[i].displayArrayList.size()) {

                                if (displayArrayList[i].displayArrayList.get(o).string1.equals(displayArrayList[i].displayArrayList.get(o + 1).string1)) {

                                    String q = displayArrayList[i].displayArrayList.get(o).string3;

                                    String j = displayArrayList[i].displayArrayList.get(o + 1).string3;

                                    q = q.concat("\n");
                                    q = q.concat(j);

                                    display d = displayArrayList[i].displayArrayList.get(o);
                                    d.string3 = q;
                                    String te = d.string2.substring(0, d.string2.length());
                                    //   d.string2=te;
                                    displayArrayList[i].displayArrayList.set(o, d);
                                    displayArrayList[i].displayArrayList.remove(o + 1);
                                    o--;
                                }


                            }
                        }
                        o++;
                    }

                }
                    ArrayList<String> head_parameters2 = new ArrayList<String>();
                    head_parameters2.add("Slot");
                    head_parameters2.add("Sem");
                    head_parameters2.add("Faculty");
                    head_parameters2.add("Subject");
                cardStackLayout.setAdapter(new EachDayDetailCardAdaptor(this, displayArrayList, head_parameters2));

        }








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





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
