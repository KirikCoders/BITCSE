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

    }

    public String getmPhoneNumber() {
        return mPrefs.getString("PhoneNumber",null);
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        mPrefsEditor.putString("PhoneNumber",mPhoneNumber);

    }

    public String getmEmail() {
        return mPrefs.getString("email",null);
    }

    public void setmEmail(String mEmail) {
        mPrefsEditor.putString("email",mEmail);

    }

    public String getmName() {
        return mPrefs.getString("name",null);
    }

    public void setmName(String mName) {
        mPrefsEditor.putString("name",mName);

    }

    public String getmSemester() {
        return mPrefs.getString("semester",null);
    }

    public void setmSemester(String mSemester) {
        mPrefsEditor.putString("semester",mSemester);

    }
    public void setIsProfessor(boolean value){
        mPrefsEditor.putBoolean("professor",value);
    }
    public boolean isProfessor(){
        return mPrefs.getBoolean("professor",false);
    }
    public void save(){
        mPrefsEditor.apply();
    }
    public void deleteAll(){
        mPrefsEditor.clear();
        mPrefsEditor.apply();
    }
}
