/*
 * Copyright (C) 2015 EasyFonts
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package easyFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Keep;

import com.bit.cse.R;


/**
 * Wraps Typeface object creation.
 * Developer should not have to worry about adding fonts in asset folder.
 *
 * @author vijay.s.vankhede@gmail.com (Vijay Vankhede)
 */
@Keep
public final class EasyFonts {

    private EasyFonts(){}


    /**
     * Android Nation-Bold font face
     * @param context Context
     * @return Typeface object for Android Nation-Bold
     */
    public static Typeface androidNationBold(Context context){
        return FontSourceProcessor.process(R.raw.androidnation_b, context);
    }


    /**
     * Freedom font face
     * @param context Context
     * @return TypefaceTypeface object for Freedom
     */
    public static Typeface freedom(Context context){
        return FontSourceProcessor.process(R.raw.freedom, context);
    }


    /**
     * Tangerine-Bold font face
     * @param context Context
     * @return Typeface object for Tangerine-Bold
     */
    public static Typeface tangerineBold(Context context){
        return FontSourceProcessor.process(R.raw.tangerine_bold, context);
    }


    /**
     * Capture-It font face
     * @param context Context
     * @return Typeface object for Capture-It
     */
    public static Typeface captureIt(Context context){
        return FontSourceProcessor.process(R.raw.capture_it, context);
    }

    /**
     * Capture-It font face
     * @param context Context
     * @return Typeface object for Capture-It2
     */
    public static Typeface captureIt2(Context context){
        return FontSourceProcessor.process(R.raw.capture_it_2, context);
    }


}