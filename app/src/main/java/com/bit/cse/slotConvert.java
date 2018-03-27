package com.bit.cse;

import android.database.Cursor;
import android.database.SQLException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by SharathBhargav on 31-10-2016.
 */
public class slotConvert {

    DatabaseHelper myDbHelper;
    String slotConversion(String s)
    {

        myDbHelper  = new DatabaseHelper();
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
        Cursor slot123=myDbHelper.getSlot();
        HashMap<String,String> slotConversion=new HashMap<>();
        while (slot123.moveToNext())
        {
            String slot=slot123.getString(0);
            String time=slot123.getString(1);
            slotConversion.put(slot,time);
        }


        return slotConversion.get(s);


    }
}
