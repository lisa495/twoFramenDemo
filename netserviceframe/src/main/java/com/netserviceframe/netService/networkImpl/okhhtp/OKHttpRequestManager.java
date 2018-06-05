package com.netserviceframe.netService.networkImpl.okhhtp;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.netserviceframe.log.Logger;
import com.netserviceframe.netService.networkImpl.common.TaskFinishListener;
import com.netserviceframe.netService.networkImpl.constant.HttpConstant;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author zyk 2015/11/24
 *         <p/>
 *         网络请求工具(OKHttp的管理类),网络实际都是由此发起
 *         post请求，设置了加密代理
 *         get请求，暂未做加密处理
 */
public class OKHttpRequestManager {

    private static volatile OKHttpRequestManager mInstance;
    private Handler mDelivery;
    private OkHttpClient okHttpClient;

    private EncryptDelegate encryptDelegate;//加密代理
    private DecryptDelegate decryptDelegate;//解密代理
    private boolean isSetEncryptDelegate;//是否设置过加密代理
    private boolean isSetDecryptDelegate;//是否设置过解密代理

    private OKHttpRequestManager() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        okHttpClient.setConnectTimeout(HttpConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(HttpConstant.READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(HttpConstant.WRITE_TIMEOUT, TimeUnit.SECONDS);
        mDelivery = new Handler(Looper.getMainLooper());
    }

    //得到单例实体
    public static OKHttpRequestManager getInstance() {
        if (null == mInstance) {
            synchronized (OKHttpRequestManager.class) {
                if(null==mInstance){
                    mInstance = new OKHttpRequestManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * Sets the socket factory used to secure HTTPS connections.
     *
     * @param sslSocketFactory --factory
     */
    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        okHttpClient.setSslSocketFactory(sslSocketFactory);
    }

    /**
     *
     * @param method        --请求方法类型
     * @param tag           --OKHttp的请求标记,如果为null--则默认将其自身Request作为标记
     * @param url           --请求的Url地址
     * @param params        --请求的参数(body部分或者键值对部分)--表单提交类型才会有用--Get请求也会用到(？后面拼接部分)
     * @param headers       --请求的header
     * @param requestParams --请求参数实体，body部分
     * @param listener      --请求回调监听
     */
    public void execute(RequestMethod method, Object tag, String url, Map<String, String> params, Map<String, String> headers, String requestParams, TaskFinishListener listener) {
        if (null == url) {
            throw new IllegalArgumentException("url can not be empty!");
        }
        BaseOKHttpRequest request;
        switch (method) {
            case GET:
                request = new OkHttpGetRequest(tag, url, params, headers);
                dealBusiness(request.request, listener);
                break;
            case POST:
                request = new OKHttpPostRequest(tag, url, null, headers, getEncryptStringValue(requestParams));
                dealBusiness(request.request, listener);
                break;
            default:
                request = new OKHttpPostRequest(tag, url, null, headers, getEncryptStringValue(requestParams));
                dealBusiness(request.request, listener);
                break;

        }
    }

    private void dealBusiness(Request request, TaskFinishListener listener) {
        final WeakReference<TaskFinishListener> weakReference = new WeakReference<>(listener);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Logger.log("请求失败:request:" + request.body().toString());
                TaskFinishListener listener = weakReference.get();
                if (listener == null) {
                    return;
                }
                dealRequestFail(request, e, listener);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Logger.log("成功响应:" + result);
                final TaskFinishListener listener = weakReference.get();
                if (listener == null) {
                    return;
                }
                dealRequestSuccess(response, listener);
            }
        });
    }

    private void dealRequestSuccess(final Response response, final TaskFinishListener listener) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (null != listener) {
                    if (response.isSuccessful()) {//code >= 200 && code < 300;
                        String result = response.body().toString();
                        if (TextUtils.isEmpty(result)) {
                            if (isSetEncryptDelegate && isSetDecryptDelegate && null != decryptDelegate) {
                                Logger.log("成功响应--解密后:" + result);
                                result = decryptDelegate.getDecryptString(result);
                            }
                            listener.onTaskSuccess(response.headers(), result);
                        } else {
                            Logger.log("成功响应，数据为空");
                            listener.onTaskException(TaskFinishListener.TaskExceptionFlag.RETURN_EMPTY_CONTENT);
                        }

                    } else {
                        listener.onTaskException(TaskFinishListener.TaskExceptionFlag.NET_SERVICE_FAIL);
                    }
                }

            }
        });
    }

    private void dealRequestFail(final Request request, final IOException e, final TaskFinishListener listener) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (null != listener) {
                    String resultBody = request.body().toString();
                    listener.onTaskFail(request.headers(), resultBody, e);
                }

            }
        });
    }


    /**
     * 取消缓存队列中未执行的任务
     *
     * @param tag 任务的标记,tag的设置是在请求Request#Build.tag()的方法
     */
    public void cancelTask(Object tag) {
        okHttpClient.cancel(tag);
    }

    //设置加密代理
    public void setEncryptDelegate(EncryptDelegate encryptDelegate) {
        if (null != encryptDelegate) {
            this.isSetEncryptDelegate = true;
        }
        this.encryptDelegate = encryptDelegate;
    }

    //设置解密代理
    public void setDecryptDelegate(DecryptDelegate decryptDelegate) {
        if (null != decryptDelegate) {
            this.isSetDecryptDelegate = true;
        }
        this.decryptDelegate = decryptDelegate;
    }

    //用设置的加密代理 加密请求报文，如果没有设置加密代理，则是明文
    private String getEncryptStringValue(String content) {
        if (null != encryptDelegate) {
            return encryptDelegate.getEncryptString(content);
        }
        return content;
    }


    //请求类型
    public enum RequestMethod {
        POST, GET

    }

    //加密代理
    public interface EncryptDelegate {
        /**
         * 加密明文
         *
         * @param decryptString 要加密的明文
         */
         String getEncryptString(String decryptString);
    }

    //解密代理
    public interface DecryptDelegate {
        /**
         * 对获取的密文进行处理
         *
         * @param decryptString 要解密的密文
         */
         String getDecryptString(String decryptString);
    }


}
