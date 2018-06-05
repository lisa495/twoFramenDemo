package com.netserviceframe.netService.networkImpl.common;


import com.squareup.okhttp.Headers;

import java.io.IOException;

/**
 * @author zyk 2015/11/25
 *
 * 网络请求回调接口
 */
public interface TaskFinishListener {


    void onTaskSuccess(Headers headers,String result);


    void onTaskException(TaskExceptionFlag exceptionFlag);


    void onTaskFail(Headers headers,String requestBodyValue, IOException e);

    enum TaskExceptionFlag{
        NET_SERVICE_FAIL,//网络请求失败--http的状态码<200或者>300
        RETURN_EMPTY_CONTENT//网络成功，返回了空数据
    }

}
