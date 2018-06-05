package com.netserviceframe.netService.demo;

import java.io.Serializable;

/**
 *
 * 网络请求，响应异常信息model
 *
 * @author zhaoyk
 */

 public class ResponseExceptionModel implements Serializable{


    public String code;
    public String message;//错误信息
    public String msgType;//错误类型--区分与toast提示或者dialog提示  1；toast  2；dialog


}
