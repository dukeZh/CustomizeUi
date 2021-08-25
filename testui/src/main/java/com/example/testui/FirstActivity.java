package com.example.testui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author DELL
 */
public class FirstActivity extends AppCompatActivity {
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
                Toast.makeText(FirstActivity.this, "11111111111", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
