package com.futureadymedia.alumni.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by developer on 10/28/2016.
 */
public class TextFont {

    public static final String BARIOL_REGULAR = "fonts/BARIOL_REGULAR_0.OTF";
    public static final String BARIOL_REGULAR_ITALIC = "fonts/BARIOL_REGULAR_ITALIC.OTF";


    public static Typeface setFontFamily(Context context, String path){
        return Typeface.createFromAsset(context.getAssets(), path);
    }
}