package com.example.viewgroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

public class MainActivity extends Activity {
    private MyViewPager myViewPager;
    private RadioGroup radioGroup;
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
        radioGroup = findViewById(R.id.radio_group);
        for (int id : ids) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
            //添加到ViewPager这个View中
            myViewPager.addView(imageView);
        }
        //添加测试页面
        View test = View.inflate(this, R.layout.test, null);
        myViewPager.addView(test,2);

        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);//0-6的id
            if (i == 0){
                button.setChecked(true);
            }
            //添加到RadioGroup
            radioGroup.addView(button);
        }

        //设置RadioGroup 选中状态
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             *
             * @param group
             * @param checkedId 0-6之间
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //根据下标位置定位到具体的某个页面
                myViewPager.scrollToPager(checkedId);
            }
        });

        //设置监听页面的改变
        myViewPager.setOnChangeListener(new MyViewPager.OnChangeListener() {
            @Override
            public void onScrollToPager(int position) {
                radioGroup.check(position);
            }
        });
    }
}