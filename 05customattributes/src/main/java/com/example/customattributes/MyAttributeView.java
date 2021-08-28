package com.example.customattributes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyAttributeView extends View {
    private Context context;
    private String name;
    private int age;
    private Bitmap bg;
    public MyAttributeView(Context context) {
        super(context,null);
    }

    public MyAttributeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //获取属性得三种方式
        //1.用命名空间获取
        String myName =  attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_name");
        String myBg =   attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_bg");
        String myAge =  attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_age");
        System.out.println("age=="+myAge+",name=="+myName+",bg=="+myBg);
        //2.遍历属性集合
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            System.out.println(attrs.getAttributeName(i)+"===="+attrs.getAttributeValue(i));
        }

        //3.使用系统工具，获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyAttributeView);
//        for (int i = 0; i < typedArray.getIndexCount(); i++) {
//            int index =  typedArray.getIndex(i);
//            switch (index){
//                case R.styleable.MyAttributeView_my_name:
//                    name = typedArray.getString(index);
//                    break;
//                case R.styleable.MyAttributeView_my_age:
//                    age = typedArray.getInt(index,0);
//                    break;
//                case R.styleable.MyAttributeView_my_bg:
//                    Drawable drawable = typedArray.getDrawable(index);
//                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//                    bg = bitmapDrawable.getBitmap();
//                    break;
//            }
//        }
        //4.使用系统直接获取属性
        name = typedArray.getString(R.styleable.MyAttributeView_my_name);
        age = typedArray.getInt(R.styleable.MyAttributeView_my_age,0);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)typedArray.getDrawable(R.styleable.MyAttributeView_my_bg);
        bg = bitmapDrawable.getBitmap();
        //记得回收
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize( DisplayUntil.px2dip(context,12));
      //  paint.setAntiAlias(true);
        canvas.drawText(name+"==="+age,50,50,paint);
        canvas.drawBitmap(bg,50,60,paint);
        bg.recycle();
    }
}
