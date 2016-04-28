package com.yilongweather.app.surfaceview.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import com.yilongweather.app.staticcontents.StaticLocalBitmap;

import java.util.ArrayList;
import java.util.Collections;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    SurfaceHolder surfaceHolder;
    public int countRaindrop;
    WindowManager wm;
    int screenWidth;
    int screenHeight;
    private int RAIN_STATUS = 10000;
    private int CLOUND_STATUS = 10001;
    private int currentStatus = CLOUND_STATUS;

    private ArrayList<Clound> arrayClound = new ArrayList<Clound>();
    private ArrayList<Raindrop> arrayRaindrop = new ArrayList<Raindrop>();
    private Paint paint;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();


        //实例化SurfaceHolder对象
        surfaceHolder = this.getHolder();
        paint = new Paint();
        //为SurfaceHolder添加一个回调函数
        surfaceHolder.addCallback(this);
        this.setFocusable(true);
    }

    public void setCurrentWeatherStatus(String status) {

        if (status.equals("多云")) {
            initStatus(CLOUND_STATUS);
        }
        if (status.equals("下雨")) {
            initStatus(RAIN_STATUS);
        }
    }


    //下雨情况  和多云的情况
    private void initStatus(int state) {

        arrayClound.clear();
        arrayRaindrop.clear();
        Bitmap bitmap8 = StaticLocalBitmap.getBitmap(getContext(), 8);
        Bitmap bitmap4 = StaticLocalBitmap.getBitmap(getContext(), 4);
        Bitmap bitmapRain1 = StaticLocalBitmap.getBitmap(getContext(), 1);
        for (int x = 0; x < 3; x++) {
            Raindrop raindrop = new Raindrop(bitmapRain1);
            arrayRaindrop.add(raindrop);
        }

        for (int x = 0; x < 4; x++) {

            Clound clound = null;

            if (x == 0) {
                clound = new Clound(bitmap8, 0.8f);
                clound.setCenterPostion(screenWidth / 2, screenHeight / 2 + 100);
                if (state == RAIN_STATUS) {
                    clound.addRaindrop(arrayRaindrop.get(0));
                }
            }
            if (x == 1) {
                clound = new Clound(bitmap8, 0.8f);
                clound.setCenterPostion(screenWidth / 2 / 2, screenHeight / 2);
                if (state == RAIN_STATUS) {
                    clound.addRaindrop(arrayRaindrop.get(1));
                }
            }
            if (x == 2) {
                clound = new Clound(bitmap8, 0.8f);
                clound.setCenterPostion(screenWidth / 2 + (screenWidth / 2 / 2), screenHeight / 2);
                if (state == RAIN_STATUS) {
                    clound.addRaindrop(arrayRaindrop.get(2));
                }
            }
            if (x == 3) {
                clound = new Clound(bitmap4, 1.2f);
                clound.setCenterPostion(screenWidth / 2, screenHeight / 2 - 150);
            }

            arrayClound.add(clound);
        }
    }


    //绘图方法
    private void Draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (surfaceHolder == null || canvas == null) {
            return;
        }
        try {
            refreshBackground(canvas);

            for (Clound c : arrayClound) {
                c.drawClound(canvas);
            }
        } catch (Exception e) {

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }


    private void refreshBackground(Canvas canvas) {

        canvas.save();
        paint.setColor(Color.parseColor("#415c68"));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //外层云朵事件分发
        for (Clound c : arrayClound) {
            c.onTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Collections.reverse(arrayClound);
            boolean intercept = false;
            for (Clound c : arrayClound) {
                if (intercept) {
                    c.setIsclickCloundflag(false);
                }
                if (c.isclickCloundflag()) {
                    intercept = true;
                }
            }
            Collections.reverse(arrayClound);
        }

        return true;
    }

    Thread thread;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag = true;

        for (Raindrop rain : arrayRaindrop) {
            rain.surfaceCreated();
        }
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        for (Raindrop rain : arrayRaindrop) {
            rain.surfaceDestroyed();
        }

        if (thread != null) {
            thread.interrupt();
        }
    }

    //最小程序要30毫秒执行刷新一次
    private long refreshIntervalTime = 30;
    boolean flag = false; //实时更新界面

    @Override
    public void run() {

        while (flag) {
            long startTime = System.currentTimeMillis();
            Draw();
            logic();
            long endTime = System.currentTimeMillis();
            if ((endTime - startTime) < refreshIntervalTime) {
                try {
                    Log.i("surfaceview","endTime - startTime = "+(endTime - startTime)+"   refreshIntervalTime= "+refreshIntervalTime);
                    Thread.sleep(refreshIntervalTime - (endTime - startTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void logic() {
        countRaindrop++;
        if (countRaindrop % 14 == 0) {

//            每隔一段时间添加雨滴
            if (arrayRaindrop != null && arrayRaindrop.size() > 0) {
                for (Raindrop rain : arrayRaindrop) {
                    rain.addRaindrop(3000);
                }
            }
        }
    }
}