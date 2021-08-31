package com.example.viewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
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

    private Scroller scroller;

    private OnChangeListener onChangeListener;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        scroller = new Scroller(context);
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
                 * distanceX正值 从右到左
                 * distanceX负值 从左到右
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
            //给一级子View 指定位置，绘制交给系统绘制，最终显示
            childView.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }

    }

    /**
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * 1.测量时候测量多次
     * 2.widthMeasureSpec 父层视图给当前视图的宽和模式
     * 系统的onMeasure 中所干的事
     * 1.根据 widthMeasureSpec 求得宽度width，和父view给的模式
     * 2.根据自身的宽度width 和自身的padding值，相减，求得子view 可以拥有的宽度newWidth
     * 3.根据newWidth 和模式求得一个新的MeasureSpec值：MeasureSpec.makeMeasureSpec(newSize,newMode);
     * 4.用新的MeasureSpec来计算子view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      //  System.out.println("widthMeasureSpec=="+widthMeasureSpec+"---heightMeasureSpec=="+heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        System.out.println("widthMeasureSpec=="+widthMeasureSpec+"--sizeWidth=="+sizeWidth+"--modeWidth=="+modeWidth);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        System.out.println("heightMeasureSpec=="+heightMeasureSpec+"--sizeHeight=="+sizeHeight+"--modeHeight=="+modeHeight);
        int newSizeWidth = MeasureSpec.makeMeasureSpec(sizeWidth,modeWidth);

        System.out.println("newSizeWidth=="+newSizeWidth);

        int newSizeHeight = MeasureSpec.makeMeasureSpec(sizeHeight,modeHeight);

        System.out.println("newSizeHeight=="+newSizeHeight);

       int childNum =  getChildCount();
        for (int i = 0; i < childNum; i++) {
            View child = getChildAt(i);
            //不断去测量自己的子视图
            child.measure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private float downX;
    private float downY;
    /**
     * 如果当前方法，返回true,拦截事件，将会触发当前控件的onTouchEvent()方法
     * 如果当前方法，返回false ，不拦截事件，事件继续传递给孩子
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        boolean result = false; //默认传递给孩子
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("onInterceptTouchEvent == MotionEvent.ACTION_DOWN");
                //1.记录开始值
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onInterceptTouchEvent == MotionEvent.ACTION_MOVE");

                //2.记录结束值
                float endX = ev.getX();
                float endY = ev.getY();

                //3.计算绝对值（计算滑动长度）
                float distanceX = Math.abs(endX -downX);
                float distanceY = Math.abs(endY -downY);
                if (distanceX > distanceY && distanceX > 5){
                    result = true;
                }else {
                    scrollToPager(currentIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onInterceptTouchEvent == MotionEvent.ACTION_UP");
                break;
        }

        return result;
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
                 System.out.println("onTouchEvent == MotionEvent.ACTION_DOWN");
                 break;
             case MotionEvent.ACTION_MOVE:
                 System.out.println("onTouchEvent == MotionEvent.ACTION_MOVE");
                 break;

             case MotionEvent.ACTION_UP:
                 System.out.println("onTouchEvent == MotionEvent.ACTION_UP");
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
    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0){
            tempIndex = 0;
        }
        if (tempIndex > getChildCount()-1){
            tempIndex = getChildCount()-1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;
        //回调当前位置
        if (onChangeListener != null){
            onChangeListener.onScrollToPager(currentIndex);
        }
        //计算总距离
        //getScrollX() 相当于到(0,0)的横向距离
        //currentIndex*getWidth() 当前屏幕右上坐标到（0，0）的横向距离
        //getScrollX(),currentIndex*getWidth() 采用绝对模式来计算滑动距离
        int distanceX = currentIndex * getWidth() - getScrollX();
        //移动到指定位置
//        scrollTo(currentIndex*getWidth(),getScrollY());
//        scroller.startScroll(getScrollX(),getScrollY(),distanceX,0);
        scroller.startScroll(getScrollX(),getScrollY(),distanceX,0,Math.abs(distanceX));

        //绘制  调用 onDraw();computeScroll();
        invalidate();
    }

    /**
     * 计算滑动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            //得到移动这个一小段对应的坐标
            float currx = scroller.getCurrX();
            scrollTo((int) currx,0);
            invalidate();
        }
    }


    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    /**
     * 监听页面的改变
     */
    public interface OnChangeListener {
        /**
         * 当页面改变时候回调
         * @param position 当前页面的位置
         */
        void onScrollToPager(int position);
    }
}
