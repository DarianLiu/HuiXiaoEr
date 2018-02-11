package com.geek.huixiaoer.common.utils;

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
}
