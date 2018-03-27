package com.bit.cse;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import easyFonts.EasyFonts;

public class FacultyData extends AppCompatActivity {
    //ArrayList<String> facultyNameList = new ArrayList<>();
    ArrayList<FacultyCardGeneralInfo> facultyNameListFiltered = new ArrayList<>();
    ArrayList<FacultyCardGeneralInfo> facultyGeneralInfoList=new ArrayList<>();
    DatabaseHelper myDbHelper;
    Context context;
    FloatingSearchView searchbox;
    RecyclerView recyclerFacultyInfo;
    facultyInfoAdapter adapter;
    StorageReference imagereference;
    TextView topHeading;
    Intent i;
    ProgressDialog progressDoalog;
    DatabaseReference databaseReference;
    CleverTapManager cleverTapManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_data);
        i=new Intent(getApplicationContext(),googleSitesWebView.class);
        searchbox=(FloatingSearchView) findViewById(R.id.searchBox);
        recyclerFacultyInfo=(RecyclerView)findViewById(R.id.recyclerFacultyInfo);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerFacultyInfo.setLayoutManager(layoutManager);
        adapter=new facultyInfoAdapter();
        topHeading=(TextView)findViewById(R.id.topHeading);
        topHeading.setTypeface(EasyFonts.androidNationBold(this));

        cleverTapManager=new CleverTapManager(this);


        progressDoalog = new ProgressDialog(FacultyData.this,R.style.MyTheme);
        progressDoalog.setIndeterminate(true);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //progressDoalog.setMax(100);
        //progressDoalog.setMessage("Loading");

        //progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);




    //  imagereference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://BIT.timetable-dbdd5.appspot.com/nanda.jpg");
        myDbHelper  = new DatabaseHelper(getApplicationContext());
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
       final Cursor fname=myDbHelper.getFacultyInfo();
        while(fname.moveToNext())
        {
          final   FacultyCardGeneralInfo temp=new FacultyCardGeneralInfo();
            temp.name=fname.getString(0);
            temp.tag=fname.getString(1);
            temp.fid=fname.getString(2);
            temp.designation=fname.getString(3);
            temp.qualification=fname.getString(4);
            temp.mail_id=fname.getString(5);
            temp.phonNo=fname.getString(6);
            if(temp.fid.equals("0"))
            {continue;}
            else
           facultyGeneralInfoList.add(temp);

        }
        facultyNameListFiltered.addAll(facultyGeneralInfoList);
        recyclerFacultyInfo.setAdapter(adapter);

      // searchbox.addTextChangedListener(new TextWatcher() {
      //     @Override
      //     public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      //     }

      //     @Override
      //     public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      //         AlterAdapter();
      //     }

      //     @Override
      //     public void afterTextChanged(Editable editable) {
      //         facultyNameListFiltered.clear();
      //         adapter.notifyDataSetChanged();
      //         AlterAdapter();

      //     }
      // });
        searchbox.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {

                facultyNameListFiltered.clear();
                adapter.notifyDataSetChanged();
                AlterAdapter(newQuery);


            }
        });





        


    }

    public class facultyInfoAdapter extends RecyclerView.Adapter<facultyInfoAdapter.myViewHolder>
    {
        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.faculty_card, parent, false);


            return new facultyInfoAdapter.myViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final myViewHolder holder, final int position) {
            holder.cardLecturerName.setText("Name: "+facultyNameListFiltered.get(position).name);
            holder.cardLecturerDesignation.setText("Designation :"+facultyNameListFiltered.get(position).designation);
            holder.cardLecturerQualification.setText("Qualification :" +facultyNameListFiltered.get(position).qualification);
            holder.cardLecturerMailId.setText("Mail-Id: "+facultyNameListFiltered.get(position).mail_id);
            holder.cardLecturerPhoneNo.setText("Phone-No: "+facultyNameListFiltered.get(position).phonNo);
            holder.facultyCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cleverTapManager.facultyCalled(facultyNameListFiltered.get(position).name,facultyNameListFiltered.get(position).phonNo);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", facultyNameListFiltered.get(position).phonNo, null));
                    startActivity(intent);
                }
            });
            imagereference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://bitcse-90b07.appspot.com/Faculty-Images/"+facultyNameListFiltered.get(position).fid+".jpg");

            Glide.with(getApplicationContext())
                    .using(new FirebaseImageLoader())
                    .load(imagereference)
                    .centerCrop()
                    .error(R.drawable.profile)
                    .into(holder.profileImage);

            holder.linearLayoutClicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDoalog.show();
                    int p=position;
                  //databaseReference.child("facultyLinks").child(facultyNameListFiltered.get(position).fid).addListenerForSingleValueEvent(
                  //        new ValueEventListener() {
                  //            @Override
                  //            public void onDataChange(DataSnapshot dataSnapshot) {
                  //                i.putExtra("site",dataSnapshot.getValue(String.class));
                  //                startActivity(i);
                  //            }
                  //            @Override
                  //            public void onCancelled(DatabaseError databaseError) {

                  //            }

                  //        });
                     String fid=facultyNameListFiltered.get(position).fid;
                    databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://bitcse-90b07.firebaseio.com/");

                    if(isInternetAvailable(getApplicationContext()))
                    {
                        cleverTapManager.facultyDocViewed(facultyNameListFiltered.get(position).name);
                        databaseReference.child("facultyLinks").child(fid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String site=dataSnapshot.getValue(String.class);
                                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setPackage("com.android.chrome");
                                try {
                                    progressDoalog.dismiss();
                                    getApplicationContext().startActivity(intent);
                                } catch (ActivityNotFoundException ex) {
                                    // Chrome browser presumably not installed so allow user to choose instead
                                    intent.setPackage(null);
                                    getApplicationContext().startActivity(intent);
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else
                        Toast.makeText(getApplicationContext(),"No internet available. Please try again later",Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return facultyNameListFiltered.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            TextView cardLecturerName,cardLecturerDesignation,cardLecturerQualification,cardLecturerMailId,cardLecturerPhoneNo,googleSiteText;
            CardView cardLecturerInfo;
            CircleImageView profileImage;

            LinearLayout linearLayoutClicked,facultyCall;
            public myViewHolder(View itemView) {
                super(itemView);
                cardLecturerName=(TextView)itemView.findViewById(R.id.cardLecturerName);
                cardLecturerDesignation=(TextView)itemView.findViewById(R.id.cardLecturerDesignation);
                cardLecturerQualification=(TextView)itemView.findViewById(R.id.cardLecturerQualification);
                cardLecturerMailId=(TextView)itemView.findViewById(R.id.cardLecturerMailId);
                cardLecturerPhoneNo=(TextView)itemView.findViewById(R.id.cardLecturerPhoneNo);
                cardLecturerInfo=(CardView)itemView.findViewById(R.id.cardLecturer);
                profileImage=(CircleImageView)itemView.findViewById(R.id.profileImage);
               googleSiteText=(TextView)itemView.findViewById(R.id.googleSiteText);
                googleSiteText.setTypeface(EasyFonts.freedom(getApplicationContext()));
                linearLayoutClicked=(LinearLayout)itemView.findViewById(R.id.LinearLayoutClicked);
                facultyCall=(LinearLayout)itemView.findViewById(R.id.facultyCall);
            }
        }
    }

    private void AlterAdapter(String newQuery) {
        facultyNameListFiltered.clear();
        if (newQuery.isEmpty()) {
            facultyNameListFiltered.addAll(facultyGeneralInfoList);

        }
        else {


            for (int k = 0; k < facultyGeneralInfoList.size(); k++) {

                if ((facultyGeneralInfoList.get(k).name.toString().toUpperCase().contains(newQuery.toUpperCase()))||facultyGeneralInfoList.get(k).tag.toString().toUpperCase().contains(newQuery.toUpperCase()))
                {

                    facultyNameListFiltered.add(facultyGeneralInfoList.get(k));
                }


            }

        }
        adapter.notifyDataSetChanged();
    }

    public boolean isInternetAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }
    }




