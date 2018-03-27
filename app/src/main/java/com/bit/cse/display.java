package com.bit.cse;

import proguard.annotation.Keep;

/**
 * Created by SharathBhargav on 30-10-2016.
 */

@Keep
@android.support.annotation.Keep
public class display {
    String string1,string2,string3,string4;
    public display(String s1,String s2,String s3,String s4)
    {
        this.string1=s1;
        this.string2=s2;
        this.string3=s3;
        this.string4=s4;
    }
}
