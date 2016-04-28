package com.yilongweather.app.surfaceview.weather;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.yilongweather.app.surfaceview.weather.model.RainDropModel;
import com.yilongweather.app.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 1.将雨滴添加到云朵里并获取到整个云朵位置
 * 2.根据位置先画水滴，在画云彩
 * 3.如果移动云朵，雨滴还是在原来的位置下降
 * <p/>
 * Created by zhkqy on 15/7/21.
 */
public class Raindrop {

    private int speed = 5;   //雨滴的下落的速度
    private Rect cloundRect;
    private ArrayList<RainDropModel> rainDropModelArray = new ArrayList<>();
    private Bitmap rainBitmap;
    int randomX = 0;
    int randomY = 0;

    Raindrop(Bitmap rainBitmap) {
        rainDropModelArray.clear();
        rainBitmap = Utils.bitmapScale(rainBitmap, 0.2f);
        this.rainBitmap = rainBitmap;
    }

    /**
     * 创建随机的雨滴下落位置
     */
    public void createRaindropLocation(long duration) {

        if (cloundRect != null) {
            int left = cloundRect.left;
            int top = cloundRect.top;
            int right = cloundRect.right;
            int bottom = cloundRect.bottom;


            randomX = left + (int) (Math.random() * (right - left + 1));
            randomY = top + (int) (Math.random() * (bottom - top + 1));

            rainDropModelArray.add(new RainDropModel(randomX, randomY, duration));
        }
    }


    public void setCloundRec(Rect cloundRect) {
        this.cloundRect = cloundRect;
    }

    public void drawRaindrop(Canvas canvas) {

        if (rainBitmap == null) {
            return;
        }

        Iterator<RainDropModel> it = rainDropModelArray.iterator();
        while (it.hasNext()) {
            RainDropModel entry = it.next();

            float randomX = entry.getEditX();
            float randomY = entry.getEditY();

            float totalLength = canvas.getHeight()-entry.getStartY();

            if (randomY >= canvas.getHeight()) {
                it.remove();
            } else {
              long middleTime =  System.currentTimeMillis()-entry.getStartTime();
                if(middleTime>entry.getDuration()){
                    randomY = canvas.getHeight();
                }else{
                    randomY =entry.getStartY()+ totalLength*middleTime/entry.getDuration();
                }
                entry.setEditY(randomY);
                canvas.drawBitmap(rainBitmap, randomX, randomY, null);
            }
        }

    }

    public void addRaindrop(long time) {
        if (flag) {
            createRaindropLocation(time);
        }
    }


    private boolean flag = false;

    public void surfaceCreated() {
        flag = true;
    }

    public void surfaceDestroyed() {
        flag = false;
    }

}
