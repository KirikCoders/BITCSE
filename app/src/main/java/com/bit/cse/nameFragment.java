package com.bit.cse;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import showcaseView.ChainTourGuide;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



@Keep
@android.support.annotation.Keep
@KeepClassMembers
@KeepImplementations
public class nameFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    AutoCompleteTextView nameInput;
    TextView toolbarText;
    String lName;
    private OnFragmentInteractionListener mListener;
    Button testButton;
    Cursor res;
    DatabaseHelper myDbHelper;
    int timeArray[],slotTime[];
    TextView nameHead,nameRoomDisplay,nameSemDisplay,nameSubDisplay;
    Calendar calendar;
    SimpleDateFormat Hour;
    Cursor fname1;
    String[] fnames;
    ArrayAdapter<String> faculty_name;
    RadioGroup nameRadioGroup;
    RadioButton radioCurrent,radioCustom;
    TimePicker timePicker;
    TextClock textClock;
    View hamburger;
Toolbar toolbar;

    int totalSlots=0;
    public ChainTourGuide mTourGuideHandler;


    public nameFragment() {
        // Required empty public constructor
    }


    public static nameFragment newInstance(String param1, String param2) {
        nameFragment fragment = new nameFragment();
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
        Cursor slots=myDbHelper.getSlot();
        int i=0;
        timeArray=new int[slots.getCount()];
        slotTime=new int[slots.getCount()];
        while (slots.moveToNext())
        {
            timeArray[i]=Integer.parseInt(slots.getString(2));
            slotTime[i]=Integer.parseInt(slots.getString(0));


            i++;
        }
        totalSlots=i-2;
         i=0;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_name,container,false);

        toolbar=(Toolbar)getActivity().findViewById(R.id.mainToolbar);
        hamburger= toolbar.getChildAt(1);

        toolbarText=(TextView)getActivity().findViewById(R.id.toolbarText);
        toolbarText.setText("Search by Faculty name");
        testButton=(Button)view.findViewById(R.id.buttonNameSearch);
     //   nameHead=(TextView)view.findViewById(R.id.nameText);
        nameInput=(AutoCompleteTextView)view.findViewById(R.id.nameSearch);
        nameInput.setAdapter(faculty_name);
        //nameRoomDisplay=(TextView)view.findViewById(R.id.nameRoomDisplay);
        //nameSemDisplay=(TextView)view.findViewById(R.id.nameSemDisplay);
        //nameSubDisplay=(TextView)view.findViewById(R.id.nameSubDisplay);
        nameRadioGroup=(RadioGroup)view.findViewById(R.id.nameRadioGroup);
        radioCurrent=(RadioButton)view.findViewById(R.id.nameRadioCurrent);
        radioCustom=(RadioButton)view.findViewById(R.id.nameRadioCustom);
        timePicker=(TimePicker)view.findViewById(R.id.timePicker3);
        textClock=(TextClock)view.findViewById(R.id.textClockName);
        // Spinner element
        final Spinner spinner = (Spinner) view.findViewById(R.id.nameDaySpinner);

        // Spinner click listener



        nameInput.setDropDownBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.autoComplete)));
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Monday");
        categories.add("Tuesday");
        categories.add("Wednesday");
        categories.add("Thursday");
        categories.add("Friday");
        categories.add("Saturday");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextSize(14);
                // custom method to get a font from "assets" folder


                ((TextView) v) .setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);

                return v;
            }
        };;

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        calendar =Calendar.getInstance();
        Hour  = new SimpleDateFormat("EEEHmm");
        radioCurrent.setChecked(true);
        timePicker.setVisibility(View.INVISIBLE);
        nameRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.nameRadioCurrent)
                {
                    timePicker.setVisibility(View.INVISIBLE);
                    textClock.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    //spinner.setEnabled(false);
                }
                else if(checkedId==R.id.nameRadioCustom)
                {
                    textClock.setVisibility(View.INVISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                  //  spinner.setEnabled(true);
                }

            }
        });
        final String[] customDay = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



           //
                // nameRoomDisplay.setText("");
                //nameSemDisplay.setText("");
                //nameSubDisplay.setText("");
                int ind=nameRadioGroup.getCheckedRadioButtonId();
                String total=Hour.format(calendar.getTime());
                String day=total.substring(0,3);
                //  total="";
                if(ind==R.id.nameRadioCurrent)
                {
                    total=Hour.format(calendar.getTime());

                }
                else if(ind==R.id.nameRadioCustom)
                {
                    day=customDay[0].substring(0,3);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(timePicker.getMinute()<10)
                            total=day+timePicker.getHour()+"0"+timePicker.getMinute();
                        else
                            total=day+timePicker.getHour()+timePicker.getMinute();


                    }
                    else {
                        if(timePicker.getCurrentMinute()<10) {
                            total = day + timePicker.getCurrentHour() + "0" + timePicker.getCurrentMinute();

                        }
                        else {
                            total = day + timePicker.getCurrentHour() + timePicker.getCurrentMinute();

                        }

                    }
                }
                int time=Integer.parseInt(total.substring(3));
                int i=0;

                while(!(time<timeArray[i]) && i<totalSlots )
                {
                    i++;

                }


                if(i!=0)
                //    i--;

                day=day.toUpperCase();
                lName=nameInput.getText().toString();
                if(lName.length()==0)
                    Toast.makeText(getContext(),"Please Enter the faculty name",Toast.LENGTH_SHORT).show();
                else
                {
                    nameInput.setText("");
                    if (lName.length() > 4)
                        res = myDbHelper.getdataName(lName, day, slotTime[i]);
                    else
                        res = myDbHelper.getDataNameInitials(lName, day, slotTime[i]);
                    if (res.getCount() == 0) {

                        if (slotTime[i] < 4) {
                            res = myDbHelper.getdataName(lName, day, 21);

                        } else if (slotTime[i] < 8) {
                            res = myDbHelper.getdataName(lName, day, 22);

                        } else
                            res = myDbHelper.getdataName(lName, day, 23);

                    }

                    String result = null;
                    //while(res.moveToNext()) {
                    //    nameRoomDisplay.setText("Room No: "+res.getString(0));
                    //    nameSemDisplay.setText("Class: "+res.getString(1));
                    //    nameSubDisplay.setText("Subject: "+res.getString(2));
                    //}
                    //if(slotTime[i]==4 || slotTime[i]==8)
                    //{
                    //    nameSemDisplay.setText("Break");
                    //}
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
                    alert.setTitle("Search Result");
                    alert.setPositiveButton("OK", null);
                    while (res.moveToNext()) {
                        if (res.getString(0).length() <= 3)
                            result = "Room No: " + res.getString(0) + "\n" + "Subject: " + res.getString(1) + "\n" + "Sem: " + res.getString(2);
                        else
                            result = "Room No: " + res.getString(0).substring(0, res.getString(0).length() - 1) + "\n" + "Class: " + res.getString(1) + "\n" + "Subject: " + res.getString(2);
                    }



                    if (result == null || time > 1645 || time < 800)
                        result = "No classes";

                    alert.setMessage(result);

                    alert.show();
                }
            }
        });

        return view;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





}
