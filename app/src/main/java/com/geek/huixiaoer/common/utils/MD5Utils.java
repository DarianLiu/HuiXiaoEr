package com.geek.huixiaoer.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 * @author Jason
 */
public class MD5Utils {
    private static String ENC_MD5 = "MD5";

    public MD5Utils() {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ENC_MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String compute(final String inStr) {
        byte[] byteArray = null;
        try {
            byteArray = inStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer hexValue = null;
        try {
            byte[] md5Bytes = MessageDigest.getInstance(ENC_MD5).digest(byteArray);
            hexValue = new StringBuffer();

            for (byte md5Byte : md5Bytes) {
                int val = (md5Byte) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hexValue.toString();
    }
}
