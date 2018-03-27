package com.bit.cse;

/**
 * Created by sharathbhragav on 25/3/18.
 */

import android.content.Context;
import android.text.TextUtils;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;

import java.sql.Date;
import java.util.HashMap;


public class CleverTapManager {
    public static final String timeTable= "Time Table clicked";
    public static final String facultyData= "Faculty Data clicked";
    public static final String docs= "Documents opened";
    public static final String developers= "Developers viewed";
    public static final String buttonPressedConstant = "First screen button pressed";

    private CleverTapAPI cInstance;

    public CleverTapManager(Context context) {
        try {
            cInstance = CleverTapAPI.getInstance(context);
        } catch (CleverTapMetaDataNotFoundException e) {
            e.printStackTrace();
        } catch (CleverTapPermissionsNotSatisfied cleverTapPermissionsNotSatisfied) {
            cleverTapPermissionsNotSatisfied.printStackTrace();
        }
    }






    public void firstScreenClicks(String button)
    {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        switch (button)
        {
            case timeTable:
                stringObjectHashMap.put(buttonPressedConstant,timeTable);
                break;
            case docs:
                stringObjectHashMap.put(buttonPressedConstant,docs);
                break;
            case facultyData:
                stringObjectHashMap.put(buttonPressedConstant,facultyData);
                break;
            case developers:
                stringObjectHashMap.put(buttonPressedConstant,developers);
                break;
        }
        cInstance.event.push(buttonPressedConstant,stringObjectHashMap);
    }

    public void facultyDocViewed(String facultyName)
    {
        long unixTime = System.currentTimeMillis();
        Date date=new Date(unixTime);
        String date1=date.toString();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("Faculty name",facultyName);
        stringObjectHashMap.put("Time stamp",date1);
        cInstance.event.push("FacultyDetailsViewed",stringObjectHashMap);
    }

    public void facultyCalled(String name,String phone)
    {
        long unixTime = System.currentTimeMillis();
        Date date=new Date(unixTime);
        String date1=date.toString();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("Faculty name",name);
        stringObjectHashMap.put("Phone",phone);
        stringObjectHashMap.put("Time stamp",date1);
        cInstance.event.push("FacultyCalled",stringObjectHashMap);
    }

    public void FragmentChosen(String fragmentName)
    {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("Fragment name",fragmentName);
        cInstance.event.push("FragmentChosen",stringObjectHashMap);
    }
}
