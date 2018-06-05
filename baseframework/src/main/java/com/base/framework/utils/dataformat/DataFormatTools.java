package com.base.framework.utils.dataformat;

import java.text.SimpleDateFormat;

/**
 * android 格式化日期  所有的日期，服务端全部是以long值返回
 */

public class DataFormatTools {
    private static final SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat fmtDateYear = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat fmtDateMonth = new SimpleDateFormat("MM月dd日");
    private static final SimpleDateFormat fmtDateMonthValue = new SimpleDateFormat("MM-dd日");
    private static final SimpleDateFormat fmtDateYearMonthValue = new SimpleDateFormat("yyyy年MM月dd日");
    private static final SimpleDateFormat fmtDateYearMonthNoValue = new SimpleDateFormat("yyyyMMdd");
    /**
     * @return 格式化日期,格式:yyyy-MM-dd HH:mm:ss
     */
    public static String formatData(long data){
        try {
            return fmtDate.format(data);
        } catch (Exception e) {
           return "";
        }
    }
    /**
     * @return 格式化日期,格式:yyyy-MM-dd
     */
    public static String fmtDateYear(long data){
        try {
            return fmtDateYear.format(data);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * @return 格式化日期,格式:MM月dd日
     */
    public static String fmtDateMonth(long data){
        try {
            return fmtDateMonth.format(data);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * @return 格式化日期,格式:MM-dd日
     */
    public static String fmtDateMonthValue(long data){
        try {
            return fmtDateMonthValue.format(data);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * @return 格式化日期,格式:yyyy年MM月dd日
     */
    public static String fmtDateYearMonthValue(long data){
        try {
            return fmtDateYearMonthValue.format(data);
        } catch (Exception e) {
            return "";
        }
    }
    /**
      * @return 格式化日期,格式:yyyyMMdd
     */
    public static String fmtDateYearMonthNoValue(long data){
        try {
            return fmtDateYearMonthNoValue.format(data);
        } catch (Exception e) {
            return "";
        }
    }

}
