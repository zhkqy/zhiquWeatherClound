package com.yilongweather.app.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by zhkqy on 15/7/23.
 */
public class Utils {

    //按比例缩放 bitmap
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

}
