package com.netserviceframe.netService.demo;

import com.google.gson.JsonSyntaxException;
import com.netserviceframe.netService.networkImpl.common.TaskFinishListener;
import com.netserviceframe.utils.CommonUtils;
import com.squareup.okhttp.Headers;

import java.io.IOException;

/**
 * 网络请求返回回调,此类根据业务流程具体书写
 */

public abstract class NetServiceCallBack<T extends BaseHttpResponseModel>  implements TaskFinishListener {
    private Class<T> t;//这个t就是server返回数据

    public NetServiceCallBack(Class<T> t) {
        this.t = t;
    }

    @Override
    public void onTaskSuccess(Headers headers, String result) {
        //此处也可以check下header内容，如有
        //解析数据
        try {
            T responseModel= CommonUtils.getGson().fromJson(result,t);
            onSuccess(responseModel);
        } catch (JsonSyntaxException e) {
            //TODO 解析失败处理
            sendFailureWithCode();
        }
    }

    @Override
    public void onTaskException(TaskExceptionFlag exceptionFlag) {
        //根据业务，处理失败情况
        sendFailureWithCode();
    }

    @Override
    public void onTaskFail(Headers headers, String requestBodyValue, IOException e) {
        //根据业务，处理失败情况
        sendFailureWithCode();
    }

    //处理错误或者异常--比如解析失败，header检查错误，会话超时等等
    private void sendFailureWithCode(){

    }
    protected abstract void onSuccess(T responseModel);
    protected abstract void onFail(ResponseExceptionModel exceptionModel);
}
