package io.kirikcoders.bitcse.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import io.kirikcoders.bitcse.R;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by Akash on 15-Jan-19.
 */

public class FacultyDetailsActivity extends AppCompatActivity {

    ListView faclst;
    FirebaseListAdapter adapter;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);
        faclst=(ListView) findViewById(R.id.fac_lst);
        constraintLayout=findViewById(R.id.fac_cl);
        if(isNetworkConnected()==false)
        {
            Snackbar.make(constraintLayout,"Check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", v -> {
                Intent intent=getIntent();
                finish();
                startActivity(intent);
            }).show();
        }
        Query query= FirebaseDatabase.getInstance().getReference().child("faculty_details");
        FirebaseListOptions<FacultyModel> options=new FirebaseListOptions.Builder<FacultyModel>()
                .setLayout(R.layout.faculty_item)
                .setQuery(query,FacultyModel.class)
                .build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView facn=v.findViewById(R.id.f_name);
                TextView facd=v.findViewById(R.id.f_desig);
                TextView facq=v.findViewById(R.id.f_qual);
                TextView facm=v.findViewById(R.id.f_mail);
                TextView facp=v.findViewById(R.id.f_ph);
                CircularImageView faci=v.findViewById(R.id.f_iv);
                FacultyModel facultyModel=(FacultyModel) model;
                facn.setText(facultyModel.getName().toString());
                facd.setText("Designation:- "+facultyModel.getDesignation().toString());
                facq.setText("Qualifications:- "+facultyModel.getQualification().toString());
                facm.setText("Email ID:- "+facultyModel.getEmailid().toString());
                facp.setText("Phone No. :- "+facultyModel.getPhoneno().toString());
                Glide
                        .with(getApplicationContext())
                        .load(facultyModel.getImage().toString())
                        .into(faci);
            }
        };
        faclst.setAdapter(adapter);
    }

   @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
