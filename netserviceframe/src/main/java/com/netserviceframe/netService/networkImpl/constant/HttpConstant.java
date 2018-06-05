package com.netserviceframe.netService.networkImpl.constant;

/**
 * Created by eyding on 15/12/16.
 * 网络相关常量类  eg:连接时间,响应超时等
 */
public class HttpConstant {

    //连接服务器时间--单位秒
    public static long CONNECT_TIMEOUT=60;
    //Sets the maximum time to wait for an input stream read to complete before giving up
    //设置等待输入流读取完成最大等待时间--响应服务器输入流的时间---(单位秒)--默认0;
    public static long READ_TIMEOUT=20;
    //Sets the default write timeout for new connections.
    //新链接的写入超时--单位秒
    public static long WRITE_TIMEOUT=20;

}
