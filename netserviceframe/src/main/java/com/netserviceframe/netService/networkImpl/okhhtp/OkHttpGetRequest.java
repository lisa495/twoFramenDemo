package com.netserviceframe.netService.networkImpl.okhhtp;

import android.text.TextUtils;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.Map;


/**
 * @author zyk 2015/11/25
 *         <p/>
 *         网络请求Request--GET请求
 */
public class OkHttpGetRequest extends BaseOKHttpRequest {
    protected OkHttpGetRequest(Object tag, String url, Map<String, String> params, Map<String, String> headers) {
        super(tag, url, params, headers,null);
    }


    @Override
    protected Request buildRequest() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can not be empty!");
        }
        //append params , if necessary
        url = appendParams(url, params);
        Request.Builder builder = new Request.Builder();
        //add headers , if necessary
        appendHeaders(builder, headers);
        builder.url(url).tag(tag);
        return builder.build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }


    private String appendParams(String url, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        for (String key : params.keySet()) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
