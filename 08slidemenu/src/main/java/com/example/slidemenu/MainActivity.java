package com.example.slidemenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<MyBean> arrayList;
    private MyMenuAdapter myMenuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arrayList.add(new MyBean("CONTENT"+i));
        }
        myMenuAdapter = new MyMenuAdapter();
        listView.setAdapter(myMenuAdapter);
    }

    private class MyMenuAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return arrayList == null ?0:arrayList.size();
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
                convertView = View.inflate(MainActivity.this,R.layout.item_layout , null);
                viewHolder = new ViewHolder();
                viewHolder.content = convertView.findViewById(R.id.item_content);
                viewHolder.delete = convertView.findViewById(R.id.item_menu);
                convertView.setTag(viewHolder);
            }else {
                 viewHolder = (ViewHolder) convertView.getTag();
            }
            MyBean myBean = arrayList.get(position);
            viewHolder.content.setText(myBean.getName());
            viewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, myBean.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlideLayout slideLayout = (SlideLayout) v.getParent();
                    slideLayout.closeMenu();
                   arrayList.remove(myBean);
                   notifyDataSetChanged();
                }
            });

            SlideLayout slideLayout = (SlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new myOnStateChangeListener());

            return convertView;
        }
    }

    static class ViewHolder{
        TextView content;
        TextView delete;
    }

    private SlideLayout slideLayout;

    private class  myOnStateChangeListener implements  SlideLayout.OnStateChangeListener{
        @Override
        public void onClose(SlideLayout layout) {
            if (slideLayout == layout){
                slideLayout = null;
            }
        }

        @Override
        public void onDown(SlideLayout layout) {
            if (slideLayout != null && slideLayout != layout){
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onOpen(SlideLayout layout) {
           slideLayout = layout;
        }
    }

}