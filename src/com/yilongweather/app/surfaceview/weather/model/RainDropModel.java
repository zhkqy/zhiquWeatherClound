package com.yilongweather.app.surfaceview.weather.model;

/**
 * Created by chenlei on 2016/4/28.
 */
public class RainDropModel {

   float startX, startY;  //开始的X,Y值
    float endX,endY;  //结束的X,Y值
    float editX,editY;  //中间改变的X ,Y值
    long duration = 0;   //时间
    long length = 0;//长度
    long startTime = 0;


    public RainDropModel(float startX, float startY, long duration) {
        this.startX = startX;
        this.startY = startY;
        this.editX = startX;
        this.editY = startY;
        this.duration = duration;

        Logic();
    }

    private void Logic() {
        if(duration<=0){
            startTime = 0;
        }else{
            startTime = System.currentTimeMillis();
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public float getEditX() {
        return editX;
    }

    public void setEditX(float editX) {
        this.editX = editX;
    }

    public float getEditY() {
        return editY;
    }

    public void setEditY(float editY) {
        this.editY = editY;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
