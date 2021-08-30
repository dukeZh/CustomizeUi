package com.example.eventtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class MyImageView extends AppCompatImageView {
    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eventTest","image | dispatchTouchEvent -->"+TouchEventUtil.getTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("eventTest","image | onTouchEvent -->"+TouchEventUtil.getTouchEvent(event));
        return super.onTouchEvent(event);
//        return true;
    }
}
