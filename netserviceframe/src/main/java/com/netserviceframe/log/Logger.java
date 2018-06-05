package com.netserviceframe.log;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zyk 2015/12/07
 *         <p>
 *         日志的打印类
 */
public class Logger {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;
    private static final int LEVEL = DEBUG;//日志级别
    private static final String Tag = "BSBLogger";
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");


    public static void log(String content) {
        log(null,content);
    }
    public static void log(String tag,String content){
        boolean isPrintLog = true;
        if (!isPrintLog) {
            return;
        }
        //getStackTrace()[3],取第四个的原因是前两个分别为vm和Thread的方法，下标2是当前的d()方法，调用d()的方法的下标为3。
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];

        String tagContent=Tag+"--["+format.format(new Date())+"]--["+ getFileName(element)+" "+element.getMethodName()+"] "+element.getLineNumber()+"行";

        switch (LEVEL){
            case VERBOSE:
                if(TextUtils.isEmpty(tag)){
                    Log.v(tagContent,content);
                }else {
                    Log.v(tag,tagContent+":"+content);
                }

                break;
            case DEBUG:
                if(TextUtils.isEmpty(tag)){
                    Log.d(tagContent,content);
                }else {
                    Log.d(tag,tagContent+":"+content);
                }
                break;
            case INFO:
                if(TextUtils.isEmpty(tag)){
                    Log.i(tagContent,content);
                }else {
                    Log.i(tag,tagContent+":"+content);
                }
                break;
            case WARN:
                if(TextUtils.isEmpty(tag)){
                    Log.w(tagContent,content);
                }else {
                    Log.w(tag,tagContent+":"+content);
                }
                break;
            case ERROR:
                if(TextUtils.isEmpty(tag)){
                    Log.e(tagContent,content);
                }else {
                    Log.e(tag,tagContent+":"+content);
                }
                break;
            case NOTHING:
                if(TextUtils.isEmpty(tag)){
                    Log.d(tagContent,content);
                }else {
                    Log.d(tag,tagContent+":"+content);
                }
                break;
        }
    }


    private static String getFileName(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        if(fileName.contains(".")){
            String stringArray[] = fileName.split("\\.");
            return  stringArray[0];
        }
        return fileName;
    }
}
