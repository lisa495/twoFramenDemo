package com.base.framework.utils.sharepreferrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * create by zyk  on 2015/10/10.
 */
public class SharePreferenceUtils {

    private static String appName="HomePage";
    private SharePreferenceUtils() {
    }

    private static SharedPreferences sp;
    private static SharePreferenceUtils instance = new SharePreferenceUtils();

    //获取实例对象
    public static SharePreferenceUtils getInstance(Context context) {
        sp = context.getSharedPreferences(appName, Context.MODE_PRIVATE);
        return instance;
    }

    //保存数据到sp中
    public void saveString(String key, String value) {
        if (TextUtils.isEmpty(value)) return;
        sp.edit().putString(key, value).apply();//异步保存
    }

    //从sp中获取数据
    public String getStringValue(String key) {
        return sp.getString(key, "");
    }

    //保存boolean
    public void saveBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();//异步保存
    }

    //获取boolean
    public boolean getBooleanValue(String key) {
        return sp.getBoolean(key, true);
    }



}
