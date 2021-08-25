package com.example.uidemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @author DELL
 */
public class TestUiDemo1 extends RelativeLayout {
    private RelativeLayout test;
    private RelativeLayout test2;
    private RelativeLayout test3;

    private boolean isShow = true;
    private boolean isShow2 = true;
    private boolean isShow3 = true;
    private Context context;
    private OnListener onListener;
    public interface OnListener{
        void onListener();
    }
    public TestUiDemo1(Context context) {
        super(context,null);
        this.context = context;
    }

    public TestUiDemo1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        setListener();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_test_ui1, null);
        test =view.findViewById(R.id.test);
        test2 =view.findViewById(R.id.test2);
        test3 =view.findViewById(R.id.test3);
        addView(view);
    }

    private void setListener() {
        MyClickListener myClickListener =  new MyClickListener();
        test.setOnClickListener(myClickListener);
        test2.setOnClickListener(myClickListener);
        test3.setOnClickListener(myClickListener);
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    class MyClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.test) {//如果二级菜单和三级菜单是现实，都设置隐藏
                if (isShow2) {
                    isShow2 = false;
                    hideView(test2);
                    if (isShow3) {
                        isShow3 = false;
                        hideView(test3, 200);
                    }
                } else {
                    isShow2 = true;
                    showView(test2);
                }
            } else if (id == R.id.test2) {
                if (isShow3) {
                    isShow3 = false;
                    hideView(test3);
                } else {
                    isShow3 = true;
                    showView(test3);
                }
            }
        }
    }

    void  action(){
        if (isShow) {
            isShow = false;
            hideView(test);
            if (isShow2) {
                //隐藏二级菜单
                isShow2 = false;
                hideView(test2, 200);
                if (isShow3) {
                    //隐藏三级级菜单
                    isShow3 = false;
                    hideView(test3, 400);
                }
            }
        } else {
            //如果一级二次是隐藏得，就显示
            //显示一级菜单
            isShow = true;
            showView(test);
            //显示二级菜单
            isShow2 = true;
            showView(test2, 200);
        }
    }

    public  void hideView(ViewGroup view) {
        hideView(view, 0);
    }

    public  void showView(ViewGroup view) {
        showView(view, 0);
    }

    public  void hideView(ViewGroup view, int startoffset) {
        //视图动画
//        RotateAnimation ra = new RotateAnimation(0, 180, view.getWidth() >> 1, view.getHeight());
//        ra.setDuration(500);//设置动画播放持续得时间
//        ra.setFillAfter(true);//动画停在播放完成得状态
//        ra.setStartOffset(startoffset);//延迟多久播放动画
//        view.startAnimation(ra);
//        //设置不可点击
//        view.setEnabled(false);
        //属性动画
        // view.setRotation();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.setStartDelay(startoffset);
        animator.setDuration(500);
        animator.start();

        view.setPivotX(view.getWidth() >> 1);
        view.setPivotY(view.getHeight());
    }

    public  void showView(ViewGroup view, int startOffset) {
        //视图动画
//        RotateAnimation ra = new RotateAnimation(180, 360, view.getWidth() >> 1, view.getHeight());
//        ra.setDuration(500);//设置动画播放持续得时间
//        ra.setFillAfter(true);//动画停在播放完成得状态
//        ra.setStartOffset(startOffset);//延迟多久播放动画
//        view.startAnimation(ra);
//        //设置可点击
//        view.setEnabled(true);
        //属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180, 360);
        animator.setStartDelay(startOffset);
        animator.setDuration(500);
        animator.start();

        view.setPivotX(view.getWidth() >> 1);
        view.setPivotY(view.getHeight());
    }
}
