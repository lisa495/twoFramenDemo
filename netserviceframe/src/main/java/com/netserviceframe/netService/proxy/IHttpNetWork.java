package com.netserviceframe.netService.proxy;

import android.content.Context;

import com.netserviceframe.netService.networkImpl.common.HttpParamsList;
import com.netserviceframe.netService.networkImpl.common.TaskFinishListener;
import com.netserviceframe.netService.networkImpl.okhhtp.OKHttpRequestManager;

/**
 * @author zyk 2015/11/24
 *         <p>
 *         网络请求接口代理接口类
 */
public interface IHttpNetWork {


    void executePost(Object tag, String url, HttpParamsList paramsModel, TaskFinishListener listener);

    void executeGet(Object tag, String url, HttpParamsList paramsModel, TaskFinishListener listener);

    void cancelRequest(Object tag);

    void setEncryptDelegate(OKHttpRequestManager.EncryptDelegate encryptDelegate);

    void setDecryptDelegate(OKHttpRequestManager.DecryptDelegate decryptDelegate);

    void setSslSocketFactory(Context context, String name);

}
