package com.yilongweather.app.surfaceview.weather;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TestSurfaceViewActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    // 视图内部类
    class MyView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder holder;
        private MyThread myThread;
        private TreeMap<Integer, TreeMap<Integer, Integer>> pointMap = new TreeMap<Integer, TreeMap<Integer, Integer>>();

        public MyView(Context context) {
            super(context);
            holder = MyView.this.getHolder();
            holder.addCallback(MyView.this);
            myThread = new MyThread();// 创建一个绘图线程
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            pointMap.clear();
            myThread.isRun = true;
            myThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            myThread.isRun = false;
        }

        private int x = 80;
        private int y = 80;

        //最小程序要30毫秒执行刷新一次
        private long refreshIntervalTime = 30;

        // 线程内部
        class MyThread extends Thread {
            public boolean isRun;

            public MyThread() {
                isRun = true;
            }

            @Override
            public void run() {

                while (isRun) {
                    long startTime = System.currentTimeMillis();
                    Draw();
                    long endTime = System.currentTimeMillis();
                    if ((endTime - startTime) < refreshIntervalTime) {
                        try {
                            Thread.sleep(refreshIntervalTime - (endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        //绘图方法
        private void Draw() {
            Canvas canvas = holder.lockCanvas();
            if (holder == null || canvas == null) {
                return;
            }
            try {
                mydraw(canvas);
                logic();
            } catch (Exception e) {

            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }

        public void mydraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);// 设置画布背景颜色
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setStrokeWidth(10);

            for (int count = 0; count < pointMap.size(); count++) {
                TreeMap<Integer, Integer> tempMap = pointMap.get(count);
                Iterator<Map.Entry<Integer, Integer>> it = tempMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Integer> entry = it.next();
                    canvas.drawPoint(entry.getKey(), entry.getValue(), p);
                }
            }
        }

        int loginCount = 0;
        int number = 0;

        private void logic() {
            loginCount++;
            if (loginCount % 14 == 0) {

                if (x > 450) {
                    y += 15;
                    x = 80;
                }
                TreeMap<Integer, Integer> map = new TreeMap<>();
                map.put(x += 15, y);
                pointMap.put(number++, map);
            }

        }
    }
}