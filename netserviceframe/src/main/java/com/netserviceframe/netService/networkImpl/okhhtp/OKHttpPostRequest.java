package com.netserviceframe.netService.networkImpl.okhhtp;

import android.text.TextUtils;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.Map;


/**
 * @author zyk 2015/11/25
 *
 * 网络请求Request--POST请求
 */
public class OKHttpPostRequest extends BaseOKHttpRequest {
    private String sContent=null;
    private byte[] bytes=null;
    private File file=null;
    private MediaType mediaType=null;

    private int type = 0;
    private static final int TYPE_PARAMS = 1;
    private static final int TYPE_STRING = 2;
    private static final int TYPE_BYTES = 3;
    private static final int TYPE_FILE = 4;

    private final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");
    private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain;charset=utf-8");

    public OKHttpPostRequest(Object tag, String url, Map<String, String> params, Map<String, String> headers, String content){
        super(tag, url, params, headers, content);

    }
    public OKHttpPostRequest(Object tag, String url, Map<String, String> params, Map<String, String> headers, byte[] bytes){
        super(tag, url, params, headers,bytes);
    }
    public OKHttpPostRequest(Object tag, String url, Map<String, String> params, Map<String, String> headers, File file){
        super(tag, url, params, headers,file);
    }
    public OKHttpPostRequest(Object tag, String url, Map<String, String> params, Map<String, String> headers, MediaType mediaType){
        super(tag, url, params, headers,mediaType);
    }
    private void initType(int flag){

    }

    @Override
    protected Request buildRequest() {
        if (TextUtils.isEmpty(url))
        {
            throw new IllegalArgumentException("url can not be empty!");
        }
        Request.Builder builder = new Request.Builder();
        appendHeaders(builder, headers);
        builder.url(url).tag(tag).post(requestBody);
        return builder.build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        validParams();
        RequestBody requestBody = null;

        switch (type) {
            case TYPE_PARAMS:
                FormEncodingBuilder builder = new FormEncodingBuilder();
                addParams(builder, params);
                requestBody = builder.build();
                break;
            case TYPE_BYTES:
                requestBody = RequestBody.create(mediaType != null ? mediaType : MEDIA_TYPE_STREAM, bytes);
                break;
            case TYPE_FILE:
                requestBody = RequestBody.create(mediaType != null ? mediaType : MEDIA_TYPE_STREAM, file);
                break;
            case TYPE_STRING:
                requestBody = RequestBody.create(mediaType != null ? mediaType : MEDIA_TYPE_STRING, sContent);
                break;
        }
        return requestBody;
    }
    private void addParams(FormEncodingBuilder builder, Map<String, String> params) {
        if (builder == null)
        {
            throw new IllegalArgumentException("builder can not be null .");
        }

        if (params != null && !params.isEmpty())
        {
            for (String key : params.keySet())
            {
                builder.add(key, params.get(key));
            }
        }
    }
    private void validParams() {
        switch (flag){
            case 0:
                sContent= (String) content;
                break;
            case 1:
                bytes=(byte[])content;
                break;
            case 2:
                file=(File)content;
                break;
            case 3:
                mediaType= (MediaType) content;
                break;
        }
        int count = 0;
        if (null!=params&& !params.isEmpty()) {
            type = TYPE_PARAMS;
            count++;
        }
        if (null!=sContent) {
            type = TYPE_STRING;
            count++;
        }
        if (null!=bytes) {
            type = TYPE_BYTES;
            count++;
        }
        if (null!=file) {
            type = TYPE_FILE;
            count++;
        }
        if (count <= 0 || count > 1) {
            throw new IllegalArgumentException("the params , content , file , bytes must has one and only one .");
        }
    }
}
