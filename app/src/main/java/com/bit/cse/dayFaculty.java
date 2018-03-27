package com.bit.cse;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dayFaculty.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dayFaculty#newInstance} factory method to
 * create an instance of this fragment.
 */

@Keep
@KeepClassMembers
@proguard.annotation.Keep
@KeepImplementations
public class dayFaculty extends Fragment implements DisplayEntireWeek.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView toolbarText;
    Button search,entireWeekDisplay;
    Spinner daySpin;
    ArrayAdapter<String> faculty_name;
    String[] fnames;
    public static   ArrayList<display> displayArrayList;
    ListView l;
    AutoCompleteTextView nameInput;
    Cursor fname1,res;
    DatabaseHelper myDbHelper;
    RecyclerView recyclerView;
    public dayFaculty() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dayFaculty.
     */
    // TODO: Rename and change types and number of parameters
    public static dayFaculty newInstance(String param1, String param2) {
        dayFaculty fragment = new dayFaculty();
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

        View view=inflater.inflate(R.layout.fragment_day_faculty,container,false);
        databaseCaller();
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        search=(Button)view.findViewById(R.id.dayFacultySearchButton);

        toolbarText=(TextView)getActivity().findViewById(R.id.toolbarText);
        daySpin=(Spinner)view.findViewById(R.id.dayFacultydaySpinner);
        nameInput=(AutoCompleteTextView)view.findViewById(R.id.dayFacultyautoComplete);
        toolbarText.setText("Day schedule : Faculty");
        entireWeekDisplay=(Button)view.findViewById(R.id.dayFacultyEntireWeekDisplay);
        nameInput.setAdapter(faculty_name);
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
        nameInput.setDropDownBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.autoComplete)));
        // attaching data adapter to spinner
        daySpin.setAdapter(dataAdapter);

        final String[] customDay = new String[1];
        daySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customDay[0] = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nameInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = customDay[0].substring(0, 3);
                day = day.toUpperCase();
                String lName = nameInput.getText().toString();
                if (lName.length()==0)
                    Toast.makeText(getContext(), "Please Enter a Faculty name", Toast.LENGTH_SHORT).show();
                else {
                    nameInput.setText("");
                    if (lName.length() > 4)
                        res = myDbHelper.getDayFaculty(lName, day);
                    else
                        res = myDbHelper.getDayFacultyInitials(lName, day);
                    displayArrayList = new ArrayList<display>();
                    while (res.moveToNext()) {
                        //   String temp = "" + res.getString(0) + res.getInt(1) + res.getString(2) + res.getString(3);
                        // Log.v("Hello", temp);
                        displayArrayList.add(new display(res.getString(0), res.getString(1), res.getString(2), res.getString(3)));
                    }

                    ArrayList<String> head_parameters = new ArrayList<String>();
                    head_parameters.add("Slot");
                    head_parameters.add("Room");
                    head_parameters.add("Sem");
                    head_parameters.add("Subject");
                    View view = getActivity().getLayoutInflater().inflate(R.layout.list_view_gen, null);
                    DisplayMetrics metrics = new DisplayMetrics();
                    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    display_dialog_general popup = new display_dialog_general(displayArrayList, view, head_parameters, getContext(), lName, metrics);
                    popup.showDialog();
                    //displayArrayList.clear();
                    //Toast.makeText(getContext(),res.getCount(),Toast.LENGTH_LONG).show();
                }
            }
        });

        entireWeekDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lName =nameInput.getText().toString();
                if(lName.length()>1) {
                    DisplayEntireWeek fragment = new DisplayEntireWeek();
                    Bundle b = new Bundle();
                    b.putString("incoming", "dayFaculty");
                    b.putString("data", lName);
                    fragment.setArguments(b);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.container, fragment).addToBackStack("dayFaculty")
                            .commit();
                    toolbarText.setText("Entire Week Display");
                    MainActivity.toolbarTitle="Day schedule : Faculty";
                }
                else
                    Toast.makeText(getContext(), "Please Enter a Faculty name", Toast.LENGTH_SHORT).show();
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

        int i=0;
        fname1=myDbHelper.getnames();
        fnames=new String[fname1.getCount()*2];
        while(fname1.moveToNext())
        {
            fnames[i]=fname1.getString(0);
            i++;
            fnames[i]=fname1.getString(1);
            i++;
        }
        faculty_name=new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,fnames);




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

