package com.example.addressbook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 快速索引
 * 绘制快速索引得字母
 * 1.26个字母放入集合中
 * 2.在onMeasure()计算每条得高itemHeight和宽itemWidth
 * 3.在onDraw()计算wordWidth,wordHeight,wordX,wordY
 */
public class IndexView extends View {

    private OnChangeListener onChangeListener;
    /**
     * 每条得宽高
     */
    private int itemWidth;
    private int itemHeight;

    private final String[] words ={
            "A","B","C","D","E","F","G","H","I","J",
            "K","L","M","N","O","P","Q","R","S","T",
            "U","V","W","X","Y","Z"
    };

    private Paint paint;
    public IndexView(Context context) {
        super(context,null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight()/words.length;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {

            if (touchIndex == i){
                paint.setColor(Color.GRAY);
            }else {
                paint.setColor(Color.WHITE);
            }

            String word  = words[i];//A,B,C
            Rect rect = new Rect();
            paint.getTextBounds(word,0,1,rect);
            //字母的宽和高
            int wordWidth = rect.width();
            int wordHeight = rect.height();

            //计算每个字母在视图的坐标位置
            float wordX = (itemWidth >> 1) -wordWidth;
            float wordY = (itemHeight >> 1) + (wordHeight >> 1) + i*itemHeight;

            canvas.drawText(word,wordX,wordY,paint);
        }
    }
    private int touchIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //字母的索引
           int index  = (int) (event.getY()/itemHeight);
                if (touchIndex != index){
                    touchIndex = index;
                    invalidate();
                    if (onChangeListener != null && touchIndex < words.length){
                        onChangeListener.onChangeListener(words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
      return true;
    }

    public void setOnChangeIndexListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public interface OnChangeListener {
      void  onChangeListener(String word);
    }
}
