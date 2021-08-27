package com.example.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyToggleButton extends View implements View.OnClickListener {
    private Bitmap bottomPic;
    private Bitmap topPic;
    private int slideMaxWith;
    //滑动过程变量
    private int slideWith;
    private Paint paint;

    public MyToggleButton(Context context) {
        super(context,null);
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        bottomPic = BitmapFactory.decodeResource(getResources(),R.drawable.test2 );
        topPic = BitmapFactory.decodeResource(getResources(),R.drawable.test1 );
        slideMaxWith = bottomPic.getWidth() - topPic.getWidth();
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bottomPic.getWidth(),bottomPic.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
     //   super.onDraw(canvas);
        canvas.drawBitmap(bottomPic,0,0,paint);
        canvas.drawBitmap(topPic,slideWith,0,paint);

    }
    //默认不打开
    private boolean opened = false;
    // 默认点击事件生效
    // true 点击事件生效，滑动事件不生效
    // false 点击事件不生效，滑动事件生效
    private boolean isEnableClick = true;
    @Override
    public void onClick(View v) {
        if (isEnableClick){
            opened = !opened;
            flushView(opened);
        }
    }

    private void flushView(boolean opened) {
        if (opened){
            slideWith = slideMaxWith;
        }else {
            slideWith = 0;
        }
        invalidate();
    }
    //记录按下初始位置（变，用于绘制）
    private float startX;
    //记录按下初始位置 （不变.用于变化是否滑动）
    private float lastx;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);//执行父类的方法

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //记录当前位置
                lastx  = startX =  event.getX();
                isEnableClick = true;
                Log.e("MyToggleButton","startX_DOWN:"+startX);
                break;
            case MotionEvent.ACTION_MOVE:
                //计算结束指
                float endX =  event.getX();
                float endy =  event.getY();
                Log.e("MyToggleButton","endX:"+endX+"----"+"endy"+endy);
                //计算偏移量
                float  distancex  = endX- startX;
                slideWith += distancex;
                //屏蔽非法值（滑动超过宽度）
                if (slideWith <0){
                    slideWith = 0;
                }else
                if (slideWith > slideMaxWith){
                    slideWith = slideMaxWith;
                }
                //刷新
                invalidate();
                //数据还原
                startX = event.getX();

                if (Math.abs(endX-lastx)>5){
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("MyToggleButton","startX_UP:"+event.getX());
                if (!isEnableClick){
                    opened = slideWith > slideMaxWith / 2;
                    flushView(opened);
                }
                break;
        }
       return true;
    }
}
