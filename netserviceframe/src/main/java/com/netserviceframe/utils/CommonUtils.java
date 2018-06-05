package com.netserviceframe.utils;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * 通用工具类
 */

public class CommonUtils {
    private static Gson json;
    //获得Gson对象
    public static Gson getGson() {
        if(null==json){
            final int sdk = Build.VERSION.SDK_INT;
            if (sdk >= 23) {
                GsonBuilder gsonBuilder = new GsonBuilder()
                        .excludeFieldsWithModifiers(
                                Modifier.FINAL,
                                Modifier.TRANSIENT,
                                Modifier.STATIC);
                json= gsonBuilder.create();
            } else {
                json= new Gson();
            }
            return json;
        }
        return json;
    }
}
