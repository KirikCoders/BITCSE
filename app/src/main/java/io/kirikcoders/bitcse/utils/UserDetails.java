package io.kirikcoders.bitcse.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kartik on 07-Sep-18.
 */
public class UserDetails {
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    public UserDetails(Context context,String file){
        mPrefs = context.getSharedPreferences(file,Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
    }

    public String getmUsn() {
        return mPrefs.getString("USN",null);
    }

    public void setmUsn(String mUsn) {
        mPrefsEditor.putString("USN",mUsn);
        mPrefsEditor.apply();
    }

    public String getmPhoneNumber() {
        return mPrefs.getString("PhoneNumber",null);
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        mPrefsEditor.putString("PhoneNumber",mPhoneNumber);
        mPrefsEditor.apply();
    }

    public String getmEmail() {
        return mPrefs.getString("email",null);
    }

    public void setmEmail(String mEmail) {
        mPrefsEditor.putString("email",mEmail);
        mPrefsEditor.apply();
    }

    public String getmName() {
        return mPrefs.getString("name",null);
    }

    public void setmName(String mName) {
        mPrefsEditor.putString("name",mName);
        mPrefsEditor.apply();
    }

    public String getmSemester() {
        return mPrefs.getString("semester",null);
    }

    public void setmSemester(String mSemester) {
        mPrefsEditor.putString("semester",mSemester);
        mPrefsEditor.apply();
    }
}
