package com.yilongweather.app.surfaceview.weather;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import com.yilongweather.app.utils.Utils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 1. 根据绘画保存云朵各个角的位置
 * 2. ontouch时候 判断时候点击了云朵
 * 3. 如果点击了云朵 则根据偏移量移动云朵
 * <p/>
 * Created by zhkqy on 15/7/21.
 */
public class Clound {
    Raindrop raindrop; //    雨滴
    private int initX = 0, initY = 0;  // 初始化云朵的位置
    private Rect cloundRect;
    private int xcha = 0, ycha = 0;  //计算偏移的差值
    private Bitmap cloudBitmap;
    private boolean isclickCloundflag = false;  //是否点中了云朵

    /**
     * 事件分发用到
     */

    public void setIsclickCloundflag(boolean isclickCloundflag) {
        this.isclickCloundflag = isclickCloundflag;
    }

    public boolean isclickCloundflag() {
        return isclickCloundflag;
    }


    Clound(Bitmap cloudBitmap, float scale) {

        cloudBitmap = Utils.bitmapScale(cloudBitmap, scale);
        this.cloudBitmap = cloudBitmap;
    }

    /**
     * 触摸事件
     */
    public void onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                int x = (int) event.getX();
                int y = (int) event.getY();

                if (cloundRect != null) {
                    xcha = x - cloundRect.left;
                    ycha = y - cloundRect.top;
                    isclickCloundflag = isClickClound(x, y);
                } else {
                    isclickCloundflag = false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (isclickCloundflag) {
                    int mX = (int) event.getX();
                    int mY = (int) event.getY();
                    initX = mX - xcha;
                    initY = mY - ycha;
                }
                break;

            case MotionEvent.ACTION_UP:
                isclickCloundflag = false;
                break;
        }
    }

    //    检测是否点中了云朵
    public boolean isClickClound(int x, int y) {
        if (cloundRect != null) {
            int left = cloundRect.left;
            int top = cloundRect.top;
            int right = cloundRect.right;
            int bottom = cloundRect.bottom;

            if ((left < x && x < right) && (top < y && y < bottom)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 直接设置左上标的点
     *
     * @param initX
     * @param initY
     */
    public void setPostion(int initX, int initY) {
        this.initX = initX;
        this.initY = initY;
    }

    /**
     * 设置初始值的中心点
     *
     * @param centerX
     * @param centerY
     */
    public void setCenterPostion(int centerX, int centerY) {

        this.initX = centerX - cloudBitmap.getWidth() / 2;
        this.initY = centerY - cloudBitmap.getHeight() / 2;
    }

    public void drawClound(Canvas canvas) {
        canvas.save();

        cloundFlutter();

        cloundRect = new Rect();
        cloundRect.left = initX;
        cloundRect.top = initY;
        cloundRect.right = initX + cloudBitmap.getWidth();
        cloundRect.bottom = initY + cloudBitmap.getHeight();

        if (raindrop != null) {
            raindrop.setCloundRec(cloundRect);
            raindrop.drawRaindrop(canvas);
        }


        canvas.drawBitmap(cloudBitmap, initX, initY, null);
        canvas.restore();
    }

    //    圆圈上每个点的位置
    private TreeMap<Integer, TreeMap<Integer, Integer>> circlePointMap = new TreeMap<Integer, TreeMap<Integer, Integer>>();
    //    圆心坐标
    private int mPointX = 0, mPointY = 0;
    //    半径
    private int mRadius = 6;
    //    每两个点间隔的角度
    private int mDegreeDelta = 5;
    //    当前的角度
    private int CurrentDegree = 0;

    //是否第一次计算圆上坐标   也就是云朵没有点击要执行一次集合添加数据 好让后续云朵动态滚动
    private boolean isOneComputeCoordinate = true;

    private int count = 0;   //随机获取云朵飘动上的任意一点   让买个云朵飘动不一样

    /**
     * 云朵飘动
     */
    public void cloundFlutter() {

        //没点中云朵则飘动
        if (!isclickCloundflag) {
            if (isOneComputeCoordinate) {
                computeCoordinates();
                isOneComputeCoordinate = false;
                count = (int) (Math.random() * (circlePointMap.size() - 1));
            }

            if (count < circlePointMap.size()) {

                TreeMap<Integer, Integer> tempMap = circlePointMap.get(count);
                Iterator<Map.Entry<Integer, Integer>> it = tempMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Integer> entry = it.next();

                    this.initX = entry.getKey();
                    this.initY = entry.getValue();
                }
                count++;
            } else {
                count = 0;
            }

//            Log.i("clound", "circlePointMap  " + circlePointMap.size());
        } else {
            isOneComputeCoordinate = true;
            CurrentDegree = 0;
        }


    }


    /**
     * 计算圆上角度添加到集合里
     */
    private void computeCoordinates() {

        circlePointMap.clear();
        int everyDegree = 360 / mDegreeDelta;

        for (int x = 0; x < everyDegree; x++) {
            CurrentDegree += mDegreeDelta;

            if (CurrentDegree <= 360) {

                int mPointX = initX;
                int mPointY = initY;

                int x1 = mPointX + (int) (mRadius * Math.cos(Math.toRadians(CurrentDegree)));
                int y1 = mPointY + (int) (mRadius * Math.sin(Math.toRadians(CurrentDegree)));

                TreeMap<Integer, Integer> tempMap = new TreeMap<Integer, Integer>();
                tempMap.put(x1, y1);
                circlePointMap.put(x, tempMap);
            } else {
                break;
            }
        }
    }


    /**
     * 1.360除以夹角获取每个角的度数
     * 2.获取每个角的
     */


    public void addRaindrop(Raindrop raindrop) {
        this.raindrop = raindrop;
    }

}
