package com.netserviceframe.netService.networkImpl.common;

import com.netserviceframe.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyk 2015/11/24
 *
 * 网络请求参数基类--请求类
 *
 * 注:此类参考的依据是由伊通的请求框架类#YTRequestParams
 *
 */
public class HttpParamsList implements Serializable {

    private Map<String,String> header;//header部分  暂不增加,根据需要可扩展增加
    private Map<String,String> body;//请求参数内容部分--当为get请求时候，为后面url后面拼接部分


    public HttpParamsList() {
        header=new HashMap<>();
        body=new HashMap<>();
    }
    public HttpParamsList putHeaderValue(String key,String value){
        header.put(key, value);
        return this;
    }
    public HttpParamsList putBodyString(String key, String value){
        body.put(key, value);
        return this;
    }
    public HttpParamsList putBodyInt(String key, Integer value){
        body.put(key, String.valueOf(value));
        return this;
    }
    public HttpParamsList putBodyFloat(String key, Float value){
        body.put(key, String.valueOf(value));
        return this;
    }
    public HttpParamsList putBodyDouble(String key, Double value){
        body.put(key, String.valueOf(value));
        return this;
    }
    public HttpParamsList putBodyList(String key, List<?> value){
        body.put(key, CommonUtils.getGson().toJson(value));
        return this;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }



}
