package com.base.framework.utils.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * 检测网络是否可以连接工具类
 */
public class NetReachableUtil {

	private static ConnectivityManager getConnectMgr(Context context) {
		return (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	/**
	 * 判断网络是否连接
	 * @param context 上下文
	 * @return 是否连接
	 */
	public static boolean isReachable(Context context) {
		ConnectivityManager connManager = getConnectMgr(context);

		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isAvailable();

	}
	 
	/**
	 * 判断是否连接2G/3G网
	 * @param context  上下文
	 * @return  是否连接
	 */
	public static boolean isReachableViaWWAN(Context context) {
		ConnectivityManager connManager = getConnectMgr(context);
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return networkInfo != null && networkInfo.getState() == State.CONNECTED;
	}
	
	/**
	 * 判断是否连接WiFi
	 * @param context  上下文
	 * @return 是否连接
	 */
	public static boolean isReachableViaWiFi(Context context) {
		ConnectivityManager connManager = getConnectMgr(context);

		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return networkInfo != null && networkInfo.getState() == State.CONNECTED;
	}
	/**
	 * 清除Cookie
	 * 
	 * @param context  上下文
	 */
	public static void removeCookie(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		CookieSyncManager.getInstance().sync();
	}
}
