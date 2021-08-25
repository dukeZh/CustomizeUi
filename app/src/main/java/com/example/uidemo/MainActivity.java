package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * @author DELL
 */
public class MainActivity extends AppCompatActivity {
    private  TestUiDemo1 testUiDemo1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initView();
    }

    private void initView() {
        testUiDemo1 = new TestUiDemo1(this);
        testUiDemo1.setOnListener(new TestUiDemo1.OnListener() {
            @Override
            public void onListener() {
                Toast.makeText(MainActivity.this, "11111111111", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
