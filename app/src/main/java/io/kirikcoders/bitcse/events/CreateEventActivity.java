package io.kirikcoders.bitcse.events;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.kirikcoders.bitcse.R;

public class CreateEventActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 4;
    private EditText eventName,eventDate,eventTime,eventDescription,eventHeadline,eventVenue,
    eventContactOne,eventContactTwo;
    private ImageView eventBanner;
    private Uri imagePath;
    private Bitmap imageBitmap;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage imageStore;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.eventSave:
                Toast.makeText(this, "Everything is saved!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_event,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        eventBanner = findViewById(R.id.eventBanner);
        eventName = findViewById(R.id.eventName);
        eventDescription = findViewById(R.id.eventDescription);
        eventHeadline = findViewById(R.id.eventHeadline);
        eventVenue = findViewById(R.id.eventVenue);
        eventContactOne = findViewById(R.id.eventContactOne);
        eventContactTwo = findViewById(R.id.eventContactTwo);
        eventDate = findViewById(R.id.eventDate);
        eventTime = findViewById(R.id.eventTime);
    }

    public void pickDateTime(View view) {
        switch (view.getId()){
            case R.id.eventDate:
                DatePickerFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(),"Pick a Date");
                break;
            case R.id.eventTime:
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(),"Pick a Time");
                break;
        }
    }

    public void setDate(int day, int month, int year) {
        eventDate.setText(day+"/"+(month+1)+"/"+year);
    }

    public void setTime(int hour, int minute) {
        StringBuffer s = new StringBuffer();
        String min;
        if (hour >= 12){
            s.append("pm");
            if (hour > 12)
                hour -= 12;
        }
        else
            s.append("am");
        if (minute < 10)
            min = "0"+Integer.toString(minute);
        else
            min = Integer.toString(minute);
        eventTime.setText(hour+":"+min+" "+s);
    }

    public void loadImage(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select your event banner"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            imagePath = data.getData();
            try{
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                eventBanner.setImageBitmap(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
