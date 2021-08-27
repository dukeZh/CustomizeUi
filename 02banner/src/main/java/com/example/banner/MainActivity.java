package com.example.banner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * @author DELL
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout li_point_group;
    private ArrayList<ImageView> imageViews;
    private int[] picImages ={
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background};

    /**
     * 上一次高亮显示得位置
     */
    private int prePosition = 0;

    private String[] titles ={
            "111111",
            "222222",
            "333333",
            "444444"};

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem()+1;
            viewPager.setCurrentItem(item);
            //延迟发消息
            handler.sendEmptyMessageDelayed(0,4000);
        }
    };

    /**
     * 是否已经滚动
     */
    private boolean isScroll = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        tv_title = findViewById(R.id.tv_title);
        li_point_group = findViewById(R.id.ll_point_group);
        imageViews = new ArrayList<>();
        for (int i = 0; i < picImages.length ; i++) {
            //添加图片
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(picImages[i]);
            imageViews.add(imageView);
            //添加点
            ImageView point = new ImageView(this);
            //在代码中设置得都是像素 ，问题在所有的手机上都是8个像素
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (8*getResources().getDisplayMetrics().density), (int) (8*getResources().getDisplayMetrics().density));
            // 把8px 转换成8dp （dip 和dp 一个单位,1px是一个像素点）
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_selector);
            if (i == 0){
                //显示红色
                point.setEnabled(true);
            }else {
                //显示灰色
                point.setEnabled(false);
                params.leftMargin = 8;
            }
            li_point_group.addView(point);
        }

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置中间位置
        //保证是我们ImageViews 得整数倍
        int item = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageViews.size();
        viewPager.setCurrentItem(item);

        tv_title.setText(titles[prePosition]);

        //发消息
        handler.sendEmptyMessageDelayed(0,3000);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当页面滚动了得时候回调这个方放
         * @param position 当前页面得位置
         * @param positionOffset 我们滑动页面得百分比
         * @param positionOffsetPixels 在我们屏幕上滑动得像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中得时候回调
         * @param position 选中得位置
         */
        @Override
        public void onPageSelected(int position) {
            int realPosition = position%imageViews.size();
            //设置对应页面得文本信息
            tv_title.setText(titles[realPosition]);
            //把上一个高亮设置默认 灰色
            li_point_group.getChildAt(prePosition).setEnabled(false);
            //当前得设置为高亮 红色
            li_point_group.getChildAt(realPosition).setEnabled(true);
            prePosition = realPosition;
        }

        /**
         * 当页面滚动状态变化得时候回调这个方法
         * 静止->滑动
         * 滑动->静止
         * 静止->拖拽
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING){
                isScroll = true;
                handler.removeCallbacksAndMessages(null);
                Log.e(TAG,"SCROLL_STATE_DRAGGING =========");
            }else if (state == ViewPager.SCROLL_STATE_SETTLING){
                Log.e(TAG,"SCROLL_STATE_SETTLING =========");
            }else if (state == ViewPager.SCROLL_STATE_IDLE && isScroll){
                isScroll = false;
                Log.e(TAG,"SCROLL_STATE_IDLE =========");
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0,4000);
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter{

        /**
         * 页面数量
         * @return
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        /**
         *
         * @param container viewpager自身
         * @param position
         * @return
         */

        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public Object instantiateItem(@NonNull @org.jetbrains.annotations.NotNull ViewGroup container, int position) {
            int realPosition = position%imageViews.size();

            ImageView imageView = imageViews.get(realPosition);
            container.addView(imageView);
          //  Log.e(TAG,"instantiateItem=="+position+",==imageView"+imageView);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN://手指在这个控件上按下
                            Log.e(TAG,"OnTouchListener == 手指按下");
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE://手指在这个控件上移动
                            Log.e(TAG,"OnTouchListener == 手指移动");
                            break;
                        case MotionEvent.ACTION_CANCEL://手指在这个控件上取消
                            Log.e(TAG,"OnTouchListener == 手指取消");
//                            handler.removeCallbacksAndMessages(null);
//                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                        case MotionEvent.ACTION_UP://手指在这个控件上手指离开
                            Log.e(TAG,"OnTouchListener == 手指离开");
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag()%imageViews.size();
                    Toast.makeText(MainActivity.this, titles[position], Toast.LENGTH_SHORT).show();
                }
            });
            return imageView;
        }

        /**
         * 比较view和object 是否同一个类型
         * @param view 页面
         * @param object 这个方法instantiateItem 返回得结果
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull  Object object) {
            return view == object;
        }

        /**
         * 释放资源
         * @param container viewpager
         * @param position 要释放得位置
         * @param object 要释放得页面
         */
        @Override
        public void destroyItem(@NonNull @org.jetbrains.annotations.NotNull ViewGroup container, int position, @NonNull @org.jetbrains.annotations.NotNull Object object) {
           // Log.e(TAG,"destroyItem=="+position+",==Object"+object);
            container.removeView((View) object);
        }
    }
}
