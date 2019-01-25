package io.kirikcoders.bitcse.tools.FindProfessor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
/**
 * Created by Akash on 25-Jan-19.
 */

public class ResDiag extends AppCompatDialogFragment {
    String resRoom;
    String resSub;
    String resSem;
    int flag;
    public ResDiag(String resRoom,String resSub,String resSem,int flag)
    {
        this.resRoom=resRoom;
        this.resSub=resSub;
        this.resSem=resSem;
        this.flag=flag;
    }

    public ResDiag() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        if(flag==1)
        {
            builder.setTitle("The Faculty is engaging a lecture")
                    .setMessage(" Room:- " + resRoom + "\n Subject:- " + resSub + "\n Semester:- " + resSem)
                    .setPositiveButton("ok", (dialog, which) -> {
                    //Dismiss on click
                    });
            return builder.create();
        }
        else
        {
            builder.setTitle("Information")
                    .setMessage("The Faculty is not engaging any lecture")
                    .setPositiveButton("ok", (dialog, which) -> {
                  //Dismiss on click
                    });
            return  builder.create();
        }
    }
}
