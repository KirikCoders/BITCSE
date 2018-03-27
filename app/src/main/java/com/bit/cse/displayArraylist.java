package com.bit.cse;

import android.support.annotation.Keep;

import java.util.ArrayList;

import proguard.annotation.KeepClassMembers;

/**
 * Created by SharathBhargav on 26-02-2017.
 */

@Keep
@proguard.annotation.Keep
@KeepClassMembers

public class displayArraylist {
    public ArrayList<display> displayArrayList;
  public displayArraylist()
    {
        this.displayArrayList=new ArrayList<display>();

    }
}
