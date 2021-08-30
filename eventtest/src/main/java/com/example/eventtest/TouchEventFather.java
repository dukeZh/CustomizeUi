package com.example.eventtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TouchEventFather extends LinearLayout {
    public TouchEventFather(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eventTest","father | dispatchTouchEvent -->"+TouchEventUtil.getTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("eventTest","father | onInterceptTouchEvent -->"+TouchEventUtil.getTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("eventTest","father | onTouchEvent -->"+TouchEventUtil.getTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
