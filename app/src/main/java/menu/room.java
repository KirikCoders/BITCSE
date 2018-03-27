package menu;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bit.cse.DatabaseHelper;
import com.bit.cse.R;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;


public class room extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Cursor res,froom;
    String lName;
    Calendar calendar;
    DatabaseHelper myDbHelper1;
    int timeArray[],slotTime[];
TextView toolbarText;
    //String[] RoomNo;
    ArrayList<String> RoomNo;
    ArrayAdapter<String> Room_no;
    AutoCompleteTextView roomSearch1;
    ArrayList<String> roomdisplay;
    int totalSlots=0;






    private String mParam1;
    private String mParam2;
    TextView roomText,roomDisplayFaculty,roomDisplaySem,roomDisplaySub;
    private OnFragmentInteractionListener mListener;
    EditText roomSearch;
    Button roomSearchButton;
    SimpleDateFormat Hour;

    RadioGroup roomRadioGroup;
    RadioButton radioCurrent,radioCustom;
    TimePicker timePicker;
    TextClock textClock;
    public room() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment room.
     */
    // TODO: Rename and change types and number of parameters
    public static room newInstance(String param1, String param2) {
        room fragment = new room();
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


            int i=0;
            //  froom=myDbHelper.getnames();
            //    fnames=new String[fname1.getCount()];
            //     while(fname1.moveToNext())
            {
                //          fnames[i]=fname1.getString(0);
                //          i++;
            }
            //     faculty_name=new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,fnames);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_room,container,false);
        roomSearch=(EditText)view.findViewById(R.id.roomSearch);
        roomSearchButton=(Button)view.findViewById(R.id.buttonRoomSearch);
        // roomDisplayFaculty=(TextView)view.findViewById(R.id.roomDisplayFaculty);
        // roomDisplaySem=(TextView)view.findViewById(R.id.roomDisplaySem);
        // roomDisplaySub=(TextView)view.findViewById(R.id.roomDisplaySub);
        roomSearch1=(AutoCompleteTextView)view.findViewById(R.id.roomSearch);
        //roomText=(TextView) view.findViewById(R.id.roomText);

toolbarText=(TextView)getActivity().findViewById(R.id.toolbarText);

        toolbarText.setText("Search by Room Number");


        roomRadioGroup=(RadioGroup)view.findViewById(R.id.roomRadioGroup);
        radioCurrent=(RadioButton)view.findViewById(R.id.roomRadioCurrent);
        radioCustom=(RadioButton)view.findViewById(R.id.roomRadioCustom);
        timePicker=(TimePicker)view.findViewById(R.id.timePicker2);
        textClock=(TextClock)view.findViewById(R.id.textClockRoom);
        final Spinner spinner = (Spinner) view.findViewById(R.id.roomDaySpinner);

        // Spinner click listener
        roomSearch1.setDropDownBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.autoComplete)));

        // Spinner Drop down elements
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
        spinner.setAdapter(dataAdapter);
        calendar=Calendar.getInstance();
        Hour = new SimpleDateFormat("EEEHmm");
        radioCurrent.setChecked(true);
        timePicker.setVisibility(View.INVISIBLE);
        dataBaseCaller();
        roomSearch1.setAdapter(Room_no);

        roomRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.roomRadioCurrent)
                {
                    timePicker.setVisibility(View.INVISIBLE);
                    textClock.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.roomRadioCustom)
                {
                    timePicker.setVisibility(View.VISIBLE);
                    textClock.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.VISIBLE);
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
        roomSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        roomSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // roomDisplayFaculty.setText("");
                // roomDisplaySem.setText("");
                // roomDisplaySub.setText("");
                int ind=roomRadioGroup.getCheckedRadioButtonId();
                String total=Hour.format(calendar.getTime());
                String day=total.substring(0,3);
                if(ind==R.id.roomRadioCurrent)
                {
                    total=Hour.format(calendar.getTime());

                }
                else if(ind==R.id.roomRadioCustom)
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

                while(!(time<timeArray[i]) && i<totalSlots)
                {

                    i++;
                }

                if(i!=0)
                 //   i--;



                day=day.toUpperCase();
                lName=roomSearch.getText().toString();
                if(lName.length()==0)
                    Toast.makeText(getContext(),"Please enter a room number",Toast.LENGTH_SHORT).show();
                else
                {
                    roomSearch.setText("");

                    res = myDbHelper1.getdataRoom(lName, day, slotTime[i]);
                    if (res.getCount() == 0) {
                        if (slotTime[i] < 4)
                            res = myDbHelper1.getdataRoom(lName, day, 12);
                        else if (slotTime[i] < 8)
                            res = myDbHelper1.getdataRoom(lName, day, 13);
                        else
                            res = myDbHelper1.getdataRoom(lName, day, 14);

                    }

                    //while(res.moveToNext()) {
                    //    roomDisplayFaculty.setText("Name: "+res.getString(0));
                    //    roomDisplaySem.setText("Class: "+res.getString(1));
                    //    roomDisplaySub.setText("Subject: "+res.getString(2));
                    //}

                    roomdisplay = new ArrayList<String>();
                    while (res.moveToNext()) {
                        //result="Class: "+res.getString(1)+"\n"+"Subject: "+res.getString(2)+"Name: "+res.getString(0)+"\n";

                        roomdisplay.add(res.getString(0));
                        roomdisplay.add(res.getString(1));
                        roomdisplay.add(res.getString(2));


                    }
                    String result = "Faculty :";
                    for (int j = 0; j < roomdisplay.size(); j = j + 3) {
                        result += roomdisplay.get(j) + "\n\t\t\t\t\t\t";

                    }
                    result = result.substring(0, result.length() - 7);
                    String result1 = null;
                    if (res.getCount() >= 1)
                        result1 = result + "\n" + "Sem: " + roomdisplay.get(1) + "\n" + "Subject: " + roomdisplay.get(2);
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
                    alert.setTitle("Search Result");
                    alert.setPositiveButton("OK", null);
                    if (result1 == null || time > 1645 || time < 800)
                        result1 = "No classes";

                    alert.setMessage(result1);
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

    void dataBaseCaller()
    {
        myDbHelper1  = new DatabaseHelper(getContext());

        try {
            myDbHelper1.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper1.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        Cursor slots=myDbHelper1.getSlot();
        int i=0;
        timeArray=new int[slots.getCount()];
        slotTime=new int[slots.getCount()];
        while (slots.moveToNext())
        {
            timeArray[i]=Integer.parseInt(slots.getString(2));
            slotTime[i]=Integer.parseInt(slots.getString(0));
            i++;
        }
       // timeArray;
        totalSlots=i-2;
        froom=myDbHelper1.getroomno();
         i=0;
        //RoomNo=new String[froom.getCount()];
        RoomNo=new ArrayList<String>();
        // RoomNo=new HashSet<String>(Arrays.asList(array)).toArray(new String[0]);

        //Toast.makeText(getContext(),Integer.toString(froom.getCount()),Toast.LENGTH_LONG).show();
        while(froom.moveToNext())
        {
            if(froom.getString(0).length()<=3) {
                // RoomNo[i] = froom.getString(0);
                RoomNo.add(froom.getString(0));
            }
            else {
                //RoomNo[i] = froom.getString(0).substring(0, froom.getString(0).length() - 1);
                RoomNo.add(froom.getString(0).substring(0, froom.getString(0).length() - 1));
            }
            i++;
        }
        ArrayList<String> RoomNo1=new ArrayList<String>(new HashSet<String>(RoomNo));
        Room_no=new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,RoomNo1);
    }





}
