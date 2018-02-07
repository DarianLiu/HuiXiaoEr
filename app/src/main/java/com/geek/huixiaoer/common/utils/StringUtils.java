package com.geek.huixiaoer.common.utils;

import android.text.TextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字符串处理工具类
 * Created by Administrator on 2017/10/25.
 */

public class StringUtils {

    /**
     * 将字符串数字保留两位小数，如果小数不足2位,会以0补足.
     *
     * @param str String
     */
    public static float strToFloat(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里
        return Float.parseFloat(decimalFormat.format(Float.parseFloat(str)));
    }

    public static boolean isEmpty(String var0) {
        return var0 == null || var0.trim().length() <= 0;
    }

    public static boolean isNotEmpty(String var0) {
        return var0 != null && var0.trim().length() > 0;
    }

    public static boolean isNotEmptyAndNull(String var0) {
        return var0 != null && var0.trim().length() > 0 && !"null".equals(var0);
    }

    public static boolean isEmptyAndNull(String var0) {
        return isEmpty(var0) || "null".equals(var0);
    }

    /**
     * 通过UrlConnection来获取
     * 通过截取url地址最后一个"/"来获取
     *
     * @param url 网络地址
     * @return fileName
     */
    public static String getFileName(String url) {
        String filename = "";
        boolean isok = false;
        // 从UrlConnection中获取文件名称
        try {
            URL myURL = new URL(url);

            URLConnection conn = myURL.openConnection();
            if (conn == null) {
                return null;
            }
            Map<String, List<String>> hf = conn.getHeaderFields();
            if (hf == null) {
                return null;
            }
            Set<String> key = hf.keySet();

            for (String skey : key) {
                List<String> values = hf.get(skey);
                for (String value : values) {
                    String result;
                    try {
                        result = new String(value.getBytes("ISO-8859-1"), "GBK");
                        int location = result.indexOf("filename");
                        if (location >= 0) {
                            result = result.substring(location
                                    + "filename".length());
                            filename = result
                                    .substring(result.indexOf("=") + 1);
                            isok = true;
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }// ISO-8859-1 UTF-8 gb2312
                }
                if (isok) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从路径中获取
        if ("".equals(filename)) {
            filename = url.substring(url.lastIndexOf("/") + 1);
        }
        return filename;
    }

    public static String formatMoney(double var0) {
        BigDecimal var2 = new BigDecimal(var0);
        return var2.setScale(2, 4).toString();
    }

    public static String formatMoney(String var0) {
        BigDecimal var1 = new BigDecimal(var0.trim());
        return var1.setScale(2, 4).toString();
    }

    public static float formatMoneyDouble(double var0) {
        BigDecimal var2 = new BigDecimal(var0);
        return var2.setScale(2, 4).floatValue();
    }

    public static float formatMoneyDouble(String var0) {
        BigDecimal var1 = new BigDecimal(var0);
        return var1.setScale(2, 4).floatValue();
    }

    /**
     * Whether or not the string is blank or null.
     *
     * @return true or false.
     */
    public static boolean isBlank(String argument) {
        int strLen;
        if (null == argument || 0 == (strLen = argument.length())) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (false == Character.isWhitespace(argument.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Whether or not the string is blank or null.
     *
     * @return true or false.
     */
    public static boolean isBlank(Object argument) {
        return null == argument || isBlank(argument.toString());
    }

    /**
     * Whether or not the string is not blank and not null.
     *
     * @return true or false.
     */
    public static boolean isNotBlank(String argument) {
        return !isBlank(argument);
    }

    /**
     * Whether or not the string is not blank and not null.
     *
     * @return true or false.
     */
    public static boolean isNotBlank(Object argument) {
        return !isBlank(argument);
    }

    /**
     * @param str the String to be trimmed, may be null
     * @return the trimmed string, {@code null} if null String input
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }


    /**
     * Whether or not the array is null or empty.
     *
     * @return true or false.
     */
    public static <L> boolean isEmpty(L[] arguments) {
        return null == arguments || 0 >= arguments.length;
    }

    /**
     * Whether or not the collection is null or empty.
     *
     * @return true or false.
     */
    public static <L> boolean isEmpty(Collection<L> arguments) {
        return null == arguments || arguments.isEmpty();
    }

    /**
     * Whether or not the map is null or empty.
     *
     * @return true or false.
     */
    public static <K, V> boolean isEmpty(Map<K, V> arguments) {
        return null == arguments || arguments.isEmpty();
    }

    /**
     * Whether or not the array is not null and not empty.
     *
     * @return true or false.
     */
    public static <L> boolean isNotEmpty(L[] arguments) {
        return !isEmpty(arguments);
    }

    /**
     * Whether or not the collection is not null and not empty.
     *
     * @return true or false.
     */
    public static <L> boolean isNotEmpty(Collection<L> arguments) {
        return !isEmpty(arguments);
    }

    /**
     * Whether or not the map is not null and not empty.
     *
     * @return true or false.
     */
    public static <K, V> boolean isNotEmpty(Map<K, V> arguments) {
        return !isEmpty(arguments);
    }


    /**
     * 字符串插入
     *
     * @param str       源字符串
     * @param index     位置
     * @param insertStr 需要插入的字符串
     * @return
     */
    public static String insert(String str, int[] index, String insertStr) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        char[] strs = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
            for (int f = 0; f < index.length; f++) {
                if (i == index[f]) {
                    sb.append(insertStr);
                }
            }
        }
        return sb.toString().trim();
    }

    /**
     * 20171220格式时间转换成2017-12-20
     */

    public static String getShortData(String shortStr) {
        return insert(shortStr, new int[]{3, 5}, "-");
    }
}
