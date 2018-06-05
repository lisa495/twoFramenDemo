package com.base.framework.utils.common;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公用的工具类
 */

public class CommonPublicUtils {

    //一个字符串有多少个字符
    public static double getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        //获取字符串，如果含有中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            String temp = s.substring(i, i + 1);
            //判断是否为中文
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }

        }
        return valueLength;
    }
    /**
     * 判断字符串是否为空或为"null"字符串
     *
     * @param str 要校验的字符串
     * @return 结果
     */
    public static boolean isEmptyOrNull(String str) {
        return TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str);
    }


    /**
     * 返回字符串是否包含全角字符
     *
     * @return true 包含 false 不包含
     */
    public static boolean isContainFullWidthChar(String str) {
        if (isEmptyOrNull(str)) {
            return false;
        }
        Matcher matcher = Pattern.compile("[\uFF00-\uFF19\uFF21-\uFF3A\uFF41-\uFF5A]").matcher(str);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 是否为合法的别名(别名：请输入半角英文、半角数字、中文，可包含_，最长20个字符)
     *
     * @param alias 别名
     * @return 检测结果
     */
    public static boolean checkAlias(String alias) {
        if (isEmptyOrNull(alias)) {
            return false;
        }

        return Pattern.matches("[a-zA-Z0-9\u4E00-\u9FFF_]{0,20}", alias);
    }

    /**
     * 获取字符串长度（一个汉字占2个字符）
     */
    public static int getStringLength(String s) {
        if (isEmptyOrNull(s)) {
            return 0;
        }
        char[] chs = s.toCharArray();
        int count = 0;
        for (int i = 0; i < chs.length; i++) {
            if ((chs[i] + "").getBytes().length >= 2) {
                count += 2;
            } else {
                count++;
            }
        }
        return count;
    }


    public static String subZeroAndDot(String s) {
        if (isEmptyOrNull(s)) {
            return "";
        }
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
