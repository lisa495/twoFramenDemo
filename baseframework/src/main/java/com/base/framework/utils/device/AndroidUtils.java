package com.base.framework.utils.device;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.util.List;
import java.util.UUID;

/**
 * android 设备相关工具类
 */

public class AndroidUtils {

    /**
     * 取得操作系统版本号 OSVersion
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    private final static String APK_CHANNEL = "APK_CHANNEL";//TODO 此字段值对应于清单文件中的配置

    /**
     * 获取当前apk的渠道号,请保证清单文件中的mete值和APK_CHANNEL一致
     */
    public static String getAPKChannel(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(APK_CHANNEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";

    }

    /****
     * 获取手机序列号,用户不赋予权限时候就赋值UUID
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context ctx) {
        String deviceID = "";
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return UUID.randomUUID().toString();
        }
        if ((tm != null ? tm.getDeviceId() : null) != null) {
            deviceID = tm.getDeviceId();
        } else {
            deviceID = getUUID();
        }
        return deviceID;
    }
    //获得uuid
    private static String getUUID() {

        return UUID.randomUUID().toString();
    }

    /**
     * 取得应用的版本号
     */
    public static String getAPKVersion(Context ctx) {
        PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    ctx.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getMetaData(Context context, String keyMetaData) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        return appInfo.metaData.getString(keyMetaData);
    }


    public double[] getLocation(Context context) {
        //获取地理位置管理器
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
//        List<String> providers = locationManager.getProviders(true);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String locationProvider = locationManager.getBestProvider(criteria, true);
//
        if (null != locationProvider) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(locationProvider);
                if (null != location) {
                    return new double[]{location.getLatitude(), location.getLongitude()};
                }
            }
        }
        return new double[]{0.0, 0.0};
    }

    /**
     * 程序是否在前台运行
     *
     * @return 是否前台
     */
    public static boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否开启GPS定位
     * @param context  上下文
     * @return true or false
     */
    public static boolean isOPenGPS(Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
//		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//		if (gps || network) {
//			return true;
//		}
//
//		return false;
    }

    // 获取根目录路径
    public static String getSDPath() {
        boolean hasSDCard = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        // 如果有sd卡，则返回sd卡的目录
        if (hasSDCard) {
            return Environment.getExternalStorageDirectory().getPath();
        } else
            // 如果没有sd卡，则返回存储目录
            return Environment.getDownloadCacheDirectory().getPath();
    }




}
