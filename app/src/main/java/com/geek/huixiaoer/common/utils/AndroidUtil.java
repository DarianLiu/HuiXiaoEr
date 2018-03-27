package com.geek.huixiaoer.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * android 常用方法
 * Created by Administrator on 2015/4/15.
 */
public class AndroidUtil {

    /**
     * 从assets读取文件
     *
     * @param fileName
     * @return
     */
    public static String readAssert(Context context,  String fileName){
        String jsonString="";
        String resultString="";
        try {
            InputStream inputStream=context.getResources().getAssets().open(fileName);
            byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString=new String(buffer,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 设置字体大小
     *
     * @param textView
     * @param str1
     * @param size1
     * @param str2
     * @param size2
     */
    public static void setTextSize(TextView textView, String str1, int size1, String str2, int size2) {
        SpannableStringBuilder style = new SpannableStringBuilder(str1 + str2);
        style.setSpan(new AbsoluteSizeSpan(size1, true), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(size2, true), str1.length(), str1.length() + str2.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }

    /**
     * 设置文本风格
     *
     * @param str1
     * @param size1
     * @param color1
     * @param str2
     * @param size2
     * @param color2
     * @return
     */
    public static SpannableStringBuilder setTextStyle(String str1, int size1, int color1, String str2, int size2, int color2) {
        SpannableStringBuilder style = new SpannableStringBuilder(str1 + str2);
        style.setSpan(new AbsoluteSizeSpan(size1, true), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(size2, true), str1.length(), str1.length() + str2.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color1), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color2), str1.length(), str1.length() + str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 设置textview 颜色和文字大小 注意：保持后面的数组大小一样
     *
     * @param textView
     * @param strs
     * @param colors
     * @param textSizes
     */
    public static void setTextSizeColor(TextView textView, String[] strs, int[] colors, int[] textSizes) {
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append(str);
        }
        SpannableStringBuilder style = new SpannableStringBuilder(builder.toString());
        int count = strs.length;
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < count; i++) {
            endIndex += strs[i].length();
            if (colors != null) {
                style.setSpan(new ForegroundColorSpan(colors[i]), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (textSizes != null) {
                style.setSpan(new AbsoluteSizeSpan(textSizes[i], true), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            startIndex += strs[i].length();
        }
        textView.setText(style);
    }

    /**
     * 从文件中获取Uri
     * 解决Android 7.0之后的Uri安全问题
     */
    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider
                    .getUriForFile(context.getApplicationContext(), "com.zbiti.yuntu.qyj.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp( Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp( Context context, float pxValue) {
        final float fontScale =context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getAppVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                if (pi != null) {
                    return pi.versionCode;
                }
            } catch (Exception ex) {
            }
        }
        return -1;
    }
}
