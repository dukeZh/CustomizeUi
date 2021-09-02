package com.example.addressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private TextView mTvWord;
    private IndexView indexView;
    private ArrayList<Person> peoples;
    private Handler handler = new Handler();
    private ListView lv_main;
    private IndexAdapter indexAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setOnListener();
    }

    private void setOnListener() {
        indexView.setOnChangeIndexListener(new IndexView.OnChangeListener() {
            @Override
            public void onChangeListener(String word) {
                upWord(word);
                updateListView(word);
            }
        });
    }

    private void updateListView(String word) {
        for (int i = 0; i < peoples.size(); i++) {
            String listWord = peoples.get(i).getPinyin().substring(0,1);
            if (listWord.equals(word)){
                // i 是 listView 中得位置
                lv_main.setSelection(i);//定位到listView中得某个位置
                //一旦满足就直接结束，不然有相同会继续走
                return;
            }
        }
    }

    private void initData() {
        peoples = new ArrayList<>();
        peoples.add(new Person("张巍巍"));
        peoples.add(new Person("柏乐"));
        peoples.add(new Person("蔡丹尼"));
        peoples.add(new Person("曹京"));
        peoples.add(new Person("周俊梅"));
        peoples.add(new Person("翟海"));
        peoples.add(new Person("徐涛"));
        peoples.add(new Person("韩志义"));
        peoples.add(new Person("夏飞"));
        peoples.add(new Person("王晓亮"));
        peoples.add(new Person("张锁平"));

        //排序
        Collections.sort(peoples, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getPinyin().compareTo(o2.getPinyin());
            }
        });
    }

    private void initView() {
        mTvWord = findViewById(R.id.tv_word);
        indexView =  findViewById(R.id.iv_words);
        lv_main  = findViewById(R.id.lv_main);
        indexAdapter = new IndexAdapter();
        lv_main.setAdapter(indexAdapter);
    }

    private void upWord(String word) {
        mTvWord.setVisibility(View.VISIBLE);
        mTvWord.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"------------");
                mTvWord.setVisibility(View.GONE);
            }
        },1000);
    }

    private class IndexAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return peoples ==null?0:peoples.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = View.inflate(MainActivity.this,R.layout.item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_word = convertView.findViewById(R.id.tv_word);
                viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String name = peoples.get(position).getName();
            String word = peoples.get(position).getPinyin().substring(0,1);
            viewHolder.tv_word.setText(word);
            viewHolder.tv_name.setText(name);

            if (position == 0){
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            }else {
                //得到前一个位置对应得字母，如果当前得字母和上一个相同，隐藏，否则显示
                String perWord = peoples.get(position-1).getPinyin().substring(0,1);
                if (word.equals(perWord)){
                    viewHolder.tv_word.setVisibility(View.GONE);
                }else {
                    viewHolder.tv_word.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }
    }

    static  class ViewHolder{
        TextView tv_word;
        TextView tv_name;
    }
}