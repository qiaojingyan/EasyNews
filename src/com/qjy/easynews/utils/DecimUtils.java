package com.qjy.easynews.utils;

import android.content.Context;
import android.util.TypedValue;

import java.lang.reflect.TypeVariable;

/**
 * Created by qjy on 15-4-21.
 */
public class DecimUtils {
    public static int px2dp(float x,Context context){
        int n = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,x,context.getResources().getDisplayMetrics());
        return n;
    }
    public static int px2sp(float x,Context context){
        int n = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,x,context.getResources().getDisplayMetrics());
        return n;
    }
}
