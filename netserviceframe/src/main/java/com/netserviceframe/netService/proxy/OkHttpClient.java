package com.netserviceframe.netService.proxy;

import android.content.Context;

import com.netserviceframe.netService.networkImpl.common.HttpParamsList;
import com.netserviceframe.netService.networkImpl.common.TaskFinishListener;
import com.netserviceframe.netService.networkImpl.okhhtp.OKHttpRequestManager;
import com.netserviceframe.netService.networkImpl.okhhtp.OkHttpSSLSocketFactory;
import com.netserviceframe.utils.CommonUtils;

import java.util.Map;

/**
 * 网络请求实际类
 */

public class OkHttpClient implements IHttpNetWork {


    private OKHttpRequestManager okHttpRequestManager;
    @Override
    public void executePost(Object tag, String url, HttpParamsList paramsModel, TaskFinishListener listener) {
        OKHttpRequestManager.RequestMethod method=OKHttpRequestManager.RequestMethod.POST;
        Map<String,String> headers=null!=paramsModel?paramsModel.getHeader():null;
        Map<String,String> body=null!=paramsModel?paramsModel.getBody():null;
        String requestParams= CommonUtils.getGson().toJson(body);
        getOkHttpRequestManager().execute(method,tag,url,null,headers,requestParams,listener);
    }

    @Override
    public void executeGet(Object tag, String url, HttpParamsList paramsModel, TaskFinishListener listener) {
        OKHttpRequestManager.RequestMethod method=OKHttpRequestManager.RequestMethod.GET;
        Map<String,String> headers=null!=paramsModel?paramsModel.getHeader():null;
        Map<String,String> params=null!=paramsModel?paramsModel.getBody():null;
        getOkHttpRequestManager().execute(method,tag,url,params,headers,null,listener);
    }

    @Override
    public void cancelRequest(Object tag) {
        getOkHttpRequestManager().cancelTask(tag);
    }

    @Override
    public void setEncryptDelegate(OKHttpRequestManager.EncryptDelegate encryptDelegate) {
        getOkHttpRequestManager().setEncryptDelegate(encryptDelegate);
    }

    @Override
    public void setDecryptDelegate(OKHttpRequestManager.DecryptDelegate decryptDelegate) {
        getOkHttpRequestManager().setDecryptDelegate(decryptDelegate);
    }

    @Override
    public void setSslSocketFactory(Context context, String name) {
        getOkHttpRequestManager().setSslSocketFactory(OkHttpSSLSocketFactory.getSSLSocketFactory(context,name));
    }
    private OKHttpRequestManager getOkHttpRequestManager(){
        if(null==okHttpRequestManager){
            okHttpRequestManager=OKHttpRequestManager.getInstance();
        }
        return okHttpRequestManager;
    }
}
