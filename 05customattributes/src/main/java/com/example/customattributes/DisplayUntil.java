package com.example.customattributes;

import android.content.Context;

public class DisplayUntil {
    public static float dip2px(Context context, int size) {
        return size/context.getResources().getDisplayMetrics().density+0.5f;
    }

    public static float px2dip(Context context, int size) {
     return  size*context.getResources().getDisplayMetrics().density+0.5f;
    }
}
