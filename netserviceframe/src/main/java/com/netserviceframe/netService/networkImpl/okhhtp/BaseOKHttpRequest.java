package com.netserviceframe.netService.networkImpl.okhhtp;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.Map;

/**
 * @author zyk 2015/11/25
 *         <p>
 *         网络请求Request基类--此类的作用是声明Request和RequestBody;
 */
public abstract class BaseOKHttpRequest {

    protected Request request;//请求
    protected RequestBody requestBody;//请求body
    protected Object tag;
    protected String url;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected Object content;
    protected int flag=-1;

    public BaseOKHttpRequest(Object tag, String url, Map<String, String> params, Map<String, String> headers, Object content) {
        this.tag = tag;
        this.url = url;
        this.params = params;
        this.headers = headers;
        this.content=content;
        if(null!=content){
            if(content instanceof String){
                flag=0;
            }else if(content instanceof byte[]){
                flag=1;
            }else if(content instanceof File){
                flag=2;
            }else if(content instanceof MediaType){
                flag=3;
            }
        }
        prepareInvoked();
    }

    //对requestBody的包装,子类根据需要可复写
    protected RequestBody wrapRequestBody(RequestBody requestBody) {
        return requestBody;
    }

    //添加header
    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    /**
     * 构建请求Request
     *
     * @return Request
     */
    protected abstract Request buildRequest();

    /**
     * 构建请求body
     *
     * @return RequestBody
     */

    protected abstract RequestBody buildRequestBody();

    //异步准备工作
    private void prepareInvoked() {
        requestBody = buildRequestBody();
        requestBody = wrapRequestBody(requestBody);
        request = buildRequest();
    }
}
