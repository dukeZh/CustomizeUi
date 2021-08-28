package com.example.viewgroup;

import android.os.SystemClock;

public class MyScroller {
    /**
     * 起始坐标
     */
    private float startX;
    private float startY;
    /**
     * x轴移动距离
     */
    private int distanceX;
    /**
     * Y轴移动距离
     */
    private int distanceY;
    /**
     * 开始的时间（初始化时的系统开机时间）
     */
    private long startTime;
    /**
     * 是否移动完成
     * false 没有移动完成
     * true 移动结束
     */
    private boolean isFinish;
    /**
     * 总消化时间
     */
    private long totalTime = 500;

    /**
     * 得到当前坐标
     */
    private float currx;

    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();// 系统开机时间
        this.isFinish = false;
    }

    public float getCurrx() {
        return currx;
    }

    /**
     * 速度
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     * true: 正在移动
     * false：移动结束
     *
     * @return
     */
    public boolean computeScrollOffset() {
        if (isFinish) {
            return false;
        }
        //滑动的此刻时间
        long endTime = SystemClock.uptimeMillis();
        //滑动这一小段所花费的时间
        long passTime = endTime - startTime;
        if (passTime < totalTime) {
            //还没有移动结束
            //计算平均速度
            //float voleCity = distanceX / totalTime;
            //移动这个一小段对应的距离
            float distanceSmallX = passTime * distanceX / totalTime;
            //移动这一小段对应的横坐标
            currx = startX + distanceSmallX;

        } else {
            //移动结束
            isFinish = true;
            //滑动完成的当前位置
            currx = startX + distanceX;
        }
        return true;
    }
}
