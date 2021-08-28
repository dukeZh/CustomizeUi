package com.example.viewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 自定义Viewpager
 */
public class MyViewPager extends ViewGroup {
    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化-把想要得办法给重新
     * 3.在onTouchEvent()把事件传递给手势识别器
     * @param context
     * @param attrs
     */
    //1.定义手势识别器
    private GestureDetector detector;
    /**
     * 当前页面下表位置
     */
    private int currentIndex;

    private MyScroller myScroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        myScroller = new MyScroller();
        //2.实例化手势识别器
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(context, "长按屏幕", Toast.LENGTH_SHORT).show();
            }

            /**
             *
             * @param e1 按下的事件
             * @param e2 离开的事件
             * @param distanceX 在x轴滑动的距离
             * @param distanceY 在y轴滑动的聚离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                /**
                 * x：要在x轴上移动的距离
                 * y：要在y轴上移动的距离
                 */
                scrollBy((int) distanceX,0);
//                scrollBy((int) distanceX,getScrollY());
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context, "双击屏幕", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //遍历我们得孩子，给每个孩子指定屏幕坐标
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private float startX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
         super.onTouchEvent(event);
         //3.把事件传递给手势
         detector.onTouchEvent(event);
         switch (event.getAction()){
             case MotionEvent.ACTION_DOWN:
                 //1.记录坐标
                 startX = event.getX();
                 break;
             case MotionEvent.ACTION_MOVE:

                 break;

             case MotionEvent.ACTION_UP:
                 //2.来到新的坐标
                 float endx = event.getX();

                 //下标位置
                 int tempIndex = currentIndex;

                 if (startX - endx > getWidth()/2){
                     //显示下一个页面
                     tempIndex++;
                 }else if (endx -startX > getWidth()/2){
                     //显示上一个页面
                     tempIndex--;
                 }
                 //根据下标位置移动到指定的页面
                 scrollToPager(tempIndex);
                 break;
         }
         return true;
    }

    /**
     * 屏蔽非法值（越界）
     * @param tempIndex
     */
    private void scrollToPager(int tempIndex) {
        if (tempIndex < 0){
            tempIndex = 0;
        }
        if (tempIndex > getChildCount()-1){
            tempIndex = getChildCount()-1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;

        //getScrollX() 相当于到(0,0)的横向距离
        //currentIndex*getWidth() 当前屏幕右上坐标到（0，0）的横向距离
        //getScrollX(),currentIndex*getWidth() 采用绝对模式来计算滑动距离
        int distanceX = currentIndex*getWidth() - getScrollX();
        //移动到指定位置
//        scrollTo(currentIndex*getWidth(),getScrollY());
        myScroller.startScroll(getScrollX(),getScrollY(),distanceX,0);

        //绘制  调用 onDraw();computeScroll();
        invalidate();
    }

    /**
     * 计算滑动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (myScroller.cuputeScrolloffset()){
            scrollTo((int) myScroller.getCurrx(),0);
            invalidate();
        }
    }
}
