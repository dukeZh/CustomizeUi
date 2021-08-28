package com.example.viewgroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private MyViewPager myViewPager;
    private int[] ids ={
            R.drawable.ic_launcher_background7,
            R.drawable.ic_launcher_background2,
            R.drawable.ic_launcher_background3,
            R.drawable.ic_launcher_background4,
            R.drawable.ic_launcher_background5,
            R.drawable.ic_launcher_background6,
            R.drawable.ic_launcher_background7};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = findViewById(R.id.viewpager);
        for (int id : ids) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
            myViewPager.addView(imageView);
        }
    }
}