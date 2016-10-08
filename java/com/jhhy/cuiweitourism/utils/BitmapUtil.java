package com.jhhy.cuiweitourism.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by jiahe008 on 2016/9/12.
 */
public class BitmapUtil {
    /**
     * 对图像进行Base64编码
     * @param bitmap
     * @return
     */
    public static String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return string;
    }

    /**
     * 按照指定尺寸加載圖片
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static Bitmap loadBitmap(String path, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        int xScale = opts.outWidth / width;
        int yScale = opts.outHeight / height;
        opts.inSampleSize = xScale > yScale ? xScale : yScale;
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, opts);
    }
}
