package com.example.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SlideLayout extends FrameLayout {
    private View  contentView;
    private View  menuView;
    /**
     * 内容得宽
     */
    private int contentWidth;
    /**
     * 菜单得宽
     */
    private int menuWidth;
    /**
     * 高都是相同，取一个代替
     */
    private int viewHeight;

    /**
     * 滚动器
     */
    private Scroller scroller;
    private final String TAG = SlideLayout.class.getSimpleName();

    private OnStateChangeListener onStateChangeListener;

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    private void initView(Context context) {
        scroller = new Scroller(context);
    }

    //当我们得布局文件加载完成得时候回调这个方法
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        viewHeight = contentView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //指定菜单的位置
        menuView.layout(contentWidth,0,contentWidth+menuWidth,viewHeight);
    }

    private float startX;
    private float startY;

    private float downX;//只赋值一次
    private float downY;

    /**
     * true: 拦截孩子的事件，但会执行当前控件的onTouchEvent()方法
     * false: 不拦截孩子的事件，事件继续传递
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"SlideLayout-onTouchEvent-ACTION_DOWN");
                //1.按下记录坐标
                downX = startX = event.getX();
                if (onStateChangeListener != null){
                    onStateChangeListener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"SlideLayout-onTouchEvent-ACTION_MOVE");
                //1.记录结束值
                float endX = event.getX();
                float endY = event.getY();

                startX = event.getX();
                //在X轴和y轴滑动的距离
                float Dx = Math.abs(endX-downX);
                float Dy = Math.abs(endY-downY);
                if (Dx > Dy && Dx>8) {
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"SlideLayout-onTouchEvent-ACTION_DOWN");
                //1.按下记录坐标
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"SlideLayout-onTouchEvent-ACTION_MOVE");
                //1.记录结束值
                float endX = event.getX();
                float endY = event.getY();
                //计算偏移量
                float distanceX = endX - startX;
                float distanceY = endY - startY;

                int toScrollX = (int) (getScrollX()-distanceX);

                if (toScrollX<0){
                    toScrollX = 0;
                }else if (toScrollX > menuWidth){
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX,getScrollY());

                startX = event.getX();
                startY = event.getY();

                //在X轴和y轴滑动的距离
                float Dx = Math.abs(endX-downX);
                float Dy = Math.abs(endY-downY);
                if (Dx > Dy && Dx>8) {
                    //水平方向滑动
                    //侧滑
                    //反拦截-事件给SlideLayout
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"SlideLayout-onTouchEvent-ACTION_UP");
                int totalScrollX = getScrollX();//偏移量
                if (totalScrollX < menuView.getWidth()>>1){
                    //关闭菜单
                    closeMenu();
                }else {
                    //打开菜单
                    openMenu();
                }
                break;
        }

        return true;
    }

    public void openMenu() {
        int distanceX = menuWidth - getScrollX();
        scroller.startScroll(getScrollX(),getScrollY(),distanceX,getScrollY());
        invalidate();//强制刷新
        if (onStateChangeListener != null){
            onStateChangeListener.onOpen(this);
        }
    }

    public void closeMenu() {
        //---> 0
        int distanceX = - getScrollX();
        scroller.startScroll(getScrollX(),getScrollY(),distanceX,getScrollY());
        invalidate();//强制刷新
        if (onStateChangeListener != null){
            onStateChangeListener.onClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
        }
        invalidate();
    }

    /**
     * 监听SlideLayout 状态的改变
     */
    public interface  OnStateChangeListener{
        void  onClose(SlideLayout slideLayout);
        void  onDown(SlideLayout slideLayout);
        void  onOpen(SlideLayout slideLayout);
    }
    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener){
        this.onStateChangeListener = onStateChangeListener;
    }
}
