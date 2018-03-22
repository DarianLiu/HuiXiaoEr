package com.geek.huixiaoer.common.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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

//    /**
//     * 定义分割常量
//     * #用于list中每个元素间的分割
//     * |用于map中每一个kv对间的分割
//     * =用于map中key与value间的分割
//     */
//    private static final String SEP1 = ",";
//    private static final String SEP2 = "|";
//    private static final String SEP3 = "=";
//
//    /**
//     * Map转换String
//     *
//     * @param map :需要转换的Map
//     * @return String转换后的字符串
//     */
//    public static String MapToString(Map<?, ?> map) {
//        StringBuffer sb = new StringBuffer();
//        // 遍历map
//        for (Object obj : map.keySet()) {
//            if (obj == null) {
//                continue;
//            }
//            Object key = obj;
//            Object value = map.get(key);
//            if (value instanceof List<?>) {
//                sb.append(key.toString() + SEP1 + ListToString((List<?>) value));
//                sb.append(SEP2);
//            } else if (value instanceof Map<?, ?>) {
//                sb.append(key.toString() + SEP1 + MapToString((Map<?, ?>) value));
//                sb.append(SEP2);
//            } else {
//                sb.append(key.toString() + SEP3 + value.toString());
//                sb.append(SEP2);
//            }
//        }
//        return "M" + sb.toString();
//    }
//
//    /**
//           * String转换Map
//           *
//           * @param mapText
//           *            :需要转换的字符串
//           * @return Map<?,?>
//           */
//    public static Map<String, Object> StringToMap(String mapText) {
//
//         if (mapText == null || mapText.equals("")) {
//             return null;
//
//        }
//         mapText = mapText.substring(1);
//
//         Map<String, Object> map = new HashMap<String, Object>();
//         String[] text = mapText.split("\\" + SEP2); // 转换为数组
//         for (String str : text) {
//             String[] keyText = str.split(SEP3); // 转换key与value的数组
//             if (keyText.length < 1) {
//                 continue;
//            }
//             String key = keyText[0]; // key
//             String value = keyText[1]; // value
//             if (value.charAt(0) == 'M') {
//                 Map<?, ?> map1 = StringToMap(value);
//                 map.put(key, map1);
//            } else if (value.charAt(0) == 'L') {
//                 List<?> list = StringToList(value);
//                 map.put(key, list);
//
//            } else {
//                 map.put(key, value);
//
//            }
//
//        }
//         return map;
//    }
}
