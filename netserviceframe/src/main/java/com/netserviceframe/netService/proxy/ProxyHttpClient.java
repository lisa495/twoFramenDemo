package com.netserviceframe.netService.proxy;

import android.content.Context;
import com.netserviceframe.netService.networkImpl.common.HttpParamsList;
import com.netserviceframe.netService.networkImpl.common.TaskFinishListener;
import com.netserviceframe.netService.networkImpl.okhhtp.OKHttpRequestManager;

/**
 * OkHttp的代理类
 */

public class ProxyHttpClient implements IHttpNetWork {
    private OkHttpClient okHttpClient;

    public ProxyHttpClient(OkHttpClient okHttpClient) {
        if(null==okHttpClient){
            throw new RuntimeException("okHttpClient must not be null");
        }
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void executePost(Object tag, String url, HttpParamsList paramsModel, TaskFinishListener listener) {
        okHttpClient.executePost(tag,url,paramsModel,listener);
    }

    @Override
    public void executeGet(Object tag, String url, HttpParamsList paramsModel, TaskFinishListener listener) {
        okHttpClient.executeGet(tag,url,paramsModel,listener);
    }

    @Override
    public void cancelRequest(Object tag) {
        okHttpClient.cancelRequest(tag);
    }

    @Override
    public void setEncryptDelegate(OKHttpRequestManager.EncryptDelegate encryptDelegate) {
        okHttpClient.setEncryptDelegate(encryptDelegate);
    }

    @Override
    public void setDecryptDelegate(OKHttpRequestManager.DecryptDelegate decryptDelegate) {
        okHttpClient.setDecryptDelegate(decryptDelegate);
    }

    @Override
    public void setSslSocketFactory(Context context, String name) {
        okHttpClient.setSslSocketFactory(context,name);
    }
}
