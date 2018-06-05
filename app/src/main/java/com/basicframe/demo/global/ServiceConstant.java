package com.basicframe.demo.global;

import com.basicframe.demo.BuildConfig;

/**
 * 服务常量类
 */

public class ServiceConstant {

    public static boolean isRelease=!BuildConfig.DEBUG;//是否生产环境
    // 服务协议
    private static String SERVICE_PROTOCOL = "http";
    // 服务名称
    public static String SERVICE_NAME = "dbank";// XADBank
    // 服务XX
    public static String SERVICE_CHANNEL ="";
    // 服务端口
    public static String SERVICE_PORT = "";//
    //服务地址等等
}
