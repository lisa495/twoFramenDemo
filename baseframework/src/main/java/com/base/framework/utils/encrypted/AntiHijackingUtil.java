package com.base.framework.utils.encrypted;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 防劫持工具类 --  梆梆有专门的做这个
 *
 * 原理：增加了一个信任的白名单，以正在运行的app包名作为判断依据
 */
public class AntiHijackingUtil {
	
	public static final String TAG = "AntiHijackingUtil";
	
	// 白名单列表
	private static List<String> safePackages;
	
	static {
		safePackages = new ArrayList<String>();
	}
	
	public static void configSafePackages(List<String> packages) {
		return;
	}
	
	/**
	 * 检测当前Activity是否安全
	 */
	public static boolean checkActivity(Context context) {
		boolean safe = false;
		
		ActivityManager activityManager = 
				(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		String runningActivityPackageName="";
		List<ActivityManager.RunningTaskInfo> list=activityManager.getRunningTasks(1);
		if(null!=list&&!list.isEmpty()){
			runningActivityPackageName=list.get(0).topActivity.getPackageName();
		}
		if (runningActivityPackageName.equals(context.getPackageName())) {
			safe = true;
		}
		
		// 白名单比对
		for (String safePack : safePackages) {
			if (safePack.equals(runningActivityPackageName)) {
				safe = true;
			}
		}
		return safe;		
	}
}
