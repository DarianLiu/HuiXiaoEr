package com.geek.huixiaoer.common.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 字符串处理工具类
 * Created by Administrator on 2017/10/25.
 */
public class StringUtils {
    public static String stringUTF8(String str) {
        String strUTF8 = null;
        try {
//            String strGBK = URLEncoder.encode(str, "GBK");
            strUTF8 = URLEncoder.encode(str, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strUTF8;
    }

    /**
     * Bitmap转String，并进行压缩
     **/
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        bitmap.recycle();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
