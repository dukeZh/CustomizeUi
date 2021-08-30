package com.example.eventtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TouchEventChild extends LinearLayout {
    public TouchEventChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eventTest","child | dispatchTouchEvent -->"+TouchEventUtil.getTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("eventTest","child | onInterceptTouchEvent -->"+TouchEventUtil.getTouchEvent(ev));
       return super.onInterceptTouchEvent(ev);
       // return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("eventTest","child | onTouchEvent -->"+TouchEventUtil.getTouchEvent(event));
//        return super.onTouchEvent(event);
        return true;
    }
}
