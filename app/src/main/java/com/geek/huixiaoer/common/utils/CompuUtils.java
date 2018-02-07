package com.geek.huixiaoer.common.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 精确计算工具类，提供加减乘除的计算
 *
 * @author jqlin
 */
public class CompuUtils {
    /**
     * 常量--0
     */
    public static final int ZERO = 0;
    /**
     * 常量--1
     */
    public static final int ONE = 1;
    /**
     * 常量-- -1
     */
    public static final int MINUS_ONE = -1;


    /**
     * 大小比较
     *
     * @param a
     * @param b
     * @return 返回1， 表示a大于b <br/>
     * 返回0 ，表示a等于b <br/>
     * 返回-1，表示a小于b
     * @author jqlin
     */
    public static int compareTo(String a, String b) {
        return new BigDecimal(a).compareTo(new BigDecimal(b));
    }

    /**
     * 加法运算
     *
     * @param a 被加数
     * @param b 加数
     * @author jqlin
     */
    public static BigDecimal add(String a, String b) {
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        return new BigDecimal(a).add(new BigDecimal(b), mc);
    }

    /**
     * 累加运算
     *
     * @param vals
     * @return
     * @author jqlin
     */
    public static int add(int... vals) {
        if (vals == null || vals.length <= 0) {
            return 0;
        }

        int sum = 0;
        for (int val : vals) {
            sum = sum + val;
        }

        return sum;
    }

    /**
     * 累加运算
     *
     * @param vals
     * @return
     * @author jqlin
     */
    public static long add(long... vals) {
        if (vals == null || vals.length <= 0) {
            return 0L;
        }

        long sum = 0L;
        for (long val : vals) {
            sum = sum + val;
        }

        return sum;
    }

    /**
     * 减法运算
     *
     * @param a 被减数
     * @param b 减数
     * @author jqlin
     */
    public static BigDecimal subtract(String a, String b) {
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        return new BigDecimal(a).subtract(new BigDecimal(b), mc);
    }

    /**
     * 乘法运算
     *
     * @param a 被乘数
     * @param b 乘数
     * @author jqlin
     */
    public static BigDecimal multiply(String a, String b) {
        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);
        return new BigDecimal(a).multiply(new BigDecimal(b), mc);
    }

    /**
     * 除法运算(保留两位小数)
     *
     * @param a 被除数
     * @param b 除数
     * @author jqlin
     */
    public static String divide(String a, String b) {
        if (Double.parseDouble(a) == 0 || Double.parseDouble(b) == 0) {
            return "0.00";
        }
        return new BigDecimal(a).divide(new BigDecimal(b), 2, BigDecimal.ROUND_HALF_UP).toString();
    }

}