package io.kirikcoders.bitcse.utils;

import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kartik on 07-Sep-18.
 */
public class InputCheckUtils {

    public static boolean checkInputs(EditText... editTexts){
        for (EditText e:editTexts){
            System.out.println(e.getText().toString());
            if (e.getText().toString() == null || e.getText().toString().equals("") ||
                    e.getText().toString().matches("\\s+")){
                e.setError("This field cannot be empty");
                return false;
            }
        }
        return true;
    }
}
