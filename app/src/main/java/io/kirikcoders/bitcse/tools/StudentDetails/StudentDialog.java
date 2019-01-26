package io.kirikcoders.bitcse.tools.StudentDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
/**
 * Created by Akash on 26-Jan-19.
 */


public class StudentDialog extends AppCompatDialogFragment
{
    String USN;
    String Name;
    String PhNo;
    String email;
    int flag;

    public StudentDialog(String USN, String name, String phNo, String email,int flag) {
        this.USN = USN;
        Name = name;
        PhNo = phNo;
        this.email = email;
        this.flag=flag;
    }


    public StudentDialog() {
        this.USN =null;
        Name = null;
        PhNo = null;
        this.email = null;
        this.flag=0;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (flag == 1) {
            builder.setTitle("Student Info of " + USN)
                    .setMessage(" Name:- " + Name + "\n Phone No. :- " + PhNo + "\n Email ID:- " + email)
                    .setPositiveButton("Ok", (dialog, which) -> {
                        //Dismiss on click;
                    })
                    .setNeutralButton("Call", (dialog, which) -> {
                        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+PhNo));
                        startActivity(intent);
                    })
                    .setNegativeButton("Email", (dialog, which) -> {
                        Intent intent=new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+email));
                        startActivity(intent);
                    });
            return builder.create();
        }
        else
        {
            builder.setTitle("Information")
                    .setMessage("The USN was not found in the database")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        //Dismiss on Click
                    });
            return builder.create();
        }
    }
}
