package com.base.framework.utils.moneyconvert;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 金钱转换加格式化
 */
public class NumberConvertUtil {

    private static HashMap<String, String> sNumMap;
    private static HashMap<Integer, String> sUnitMap;


    private NumberConvertUtil() {
    }

    /**
     * 保留两位小数
     * @param number 数字
     * @return 格式化后
     */
    public static String formatNumberWith2Point(double number){
        DecimalFormat  df = new DecimalFormat("######0.00");
        return  df.format(number);
    }

    /**
     * 保留两位小数
     * @param number  数值
     * @return  格式化后
     */
    public static String formatNumberWith2Point(String number){
        if (TextUtils.isEmpty(number)){
            return  "0.00";
        }
        try{
            double d = Double.parseDouble(number);
            return formatNumberWith2Point(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "0.00";
    }

    /**
     * 取整数部分
     * @param number 数值
     * @return 格式化后
     */
    public static String formatNumberWithGlobal(double number){
        DecimalFormat  df = new DecimalFormat("#");
        return  df.format(number);
    }

    /**
     * 去除逗号
     * @param s 数值前
     * @return  去除逗号后
     */
    public static String replaceComma(String s){
        String result = null;
        if (!TextUtils.isEmpty(s)){
            result =  s.replace(",","");
        }
        return  result;
    }
    //判断是不是都是数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String formatNumberWithZero(String str){
        if(TextUtils.isEmpty(str))return null;
        if(isNumeric(str)){
            return str.replaceAll("^(0+)", "");
        }
        return "";
    }

    private static void initMap() {
        sNumMap = new HashMap<>();
        sNumMap.put("0", "零");
        sNumMap.put("1", "壹");
        sNumMap.put("2", "贰");
        sNumMap.put("3", "叁");
        sNumMap.put("4", "肆");
        sNumMap.put("5", "伍");
        sNumMap.put("6", "陆");
        sNumMap.put("7", "柒");
        sNumMap.put("8", "捌");
        sNumMap.put("9", "玖");
        sUnitMap = new HashMap<>();
        sUnitMap.put(2, "拾");
        sUnitMap.put(3, "佰");
        sUnitMap.put(4, "仟");
        sUnitMap.put(5, "万");
        sUnitMap.put(6, "拾");
        sUnitMap.put(7, "佰");
        sUnitMap.put(8, "仟");
        sUnitMap.put(9, "亿");
    }

    public static void destory() {
        if (sNumMap != null) {
            sNumMap.clear();
            sNumMap = null;
        }
        if (sUnitMap != null) {
            sUnitMap.clear();
            sUnitMap = null;
        }
    }

    /**
     * 金钱转换--数字变为汉字
     * @param num 钱
     * @return 汉字
     */
    public static String transNum(String num) {
        if (num != null && num.length() > 9) {
            return "您已超过最大可投资额度";
        }
        if (sUnitMap == null || sNumMap == null) {
            initMap();
        }
        //ArrayList<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        if (num == null || "0".equals(num) || "0.0".equals(num)) {
            sb.append(sNumMap.get("0")).append("元整");
            return sb.toString();
        }

        int intNum;
        if (num.contains(".")) {
            intNum = num.indexOf(".");
            if (num.endsWith(".0")) {
                num = num.replace(".0", "");
            }
        } else {
            intNum = num.length();
        }
        char[] chs = num.toCharArray();
        final int maxLen = chs.length;
        String temp = "0";
        for (int i = 0; i < maxLen; i++) {
            temp = String.valueOf(chs[i]);
            if ("0".equals(temp)) {
                intNum--;
                if (intNum > 1) {
                    switch (intNum) {
                        case 4:
                            //list.add(VOICE_NUM_THOU);
                            sb.append(sUnitMap.get(5));
                            break;
                        case 8:
                            //list.add(VOICE_NUM_HUND);
                            sb.append(sUnitMap.get(9));
                            break;


                        default:
                            break;
                    }
                }
                continue;
            } else if (!".".equals(temp) && i > 0 && '0' == chs[i - 1]) {
                sb.append(sNumMap.get("0"));
            } else if (".".equals(temp) && num.startsWith("0.")) {
                sb.append(sNumMap.get("0"));
                intNum--;
            }
            sb.append(sNumMap.get(temp));
            if (intNum > 1) {
                sb.append(sUnitMap.get(intNum));
            }
            intNum--;
        }
        return sb.append("元整").toString();
    }
}
