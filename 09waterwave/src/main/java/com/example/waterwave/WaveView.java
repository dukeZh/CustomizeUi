package com.example.waterwave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WaveView extends View {

    private int radio = 5;
    private Paint paint;
    private float downY;
    private float downX;

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        radio = 5;
        paint= new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(radio/3);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            radio += 5;

            int alpha = paint.getAlpha();
            alpha -= 5;
            if (alpha <0){
                alpha = 0;
            }
            paint.setAlpha(alpha);

            paint.setStrokeWidth(radio/3);
            invalidate();
        }
    };
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint.getAlpha() > 0 && downX>0 && downY>0){
            canvas.drawCircle(downX,downY,radio,paint);
            handler.sendEmptyMessageDelayed(0,50);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                downX = event.getX();
                downY = event.getY();
                initView();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
