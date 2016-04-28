package com.yilongweather.app.staticcontents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.yilongweather.R;

/**
 * Created by zhkqy on 15/7/23.
 */
public class StaticLocalBitmap {

    //    云彩的图片
    public static Bitmap getBitmap(Context context, int count) {
        if (count == 8) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ani_cloud_9);
        }
        if (count == 4) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ani_cloud_8);
        }
        if (count == 1) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.bitebi);
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.ani_cloud_8);
    }
}
