package com.hyd.animationart.utils;

/**
 * Created by hydCoder on 2020/3/5.
 * 以梦为马，明日天涯。
 */
public class Utils {

    /**
     * 计算三个double值中中间的值
     * @param value1 第一个值
     * @param value2 第二个值
     * @param value3 第三个值
     * @return 中间值
     */
    public static double clamp(double value1, double value2, double value3) {
        return Math.min(Math.max(value1, value2), value3);
    }

    public static double mapValueFromRangeToRange(double value1, double value2, double value3, double value4, double value5) {
        return (value1 - value2) / (value3 - value2) * (value5 - value4) + value4;
    }
}
