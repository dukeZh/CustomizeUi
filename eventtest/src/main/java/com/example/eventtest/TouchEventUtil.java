package com.example.eventtest;

import android.view.MotionEvent;

public class TouchEventUtil {
    public static String getTouchEvent(MotionEvent ev) {
        String tag ="";
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                tag = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                tag = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                tag = "ACTION_UP";
                break;

        }
        return tag;

    }
}
