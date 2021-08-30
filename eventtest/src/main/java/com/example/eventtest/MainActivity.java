package com.example.eventtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eventTest","Activity | dispatchTouchEvent -->"+TouchEventUtil.getTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("eventTest","Activity | onTouchEvent -->"+TouchEventUtil.getTouchEvent(event));
        return super.onTouchEvent(event);
    }
}