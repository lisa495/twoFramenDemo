package com.base.framework.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * activity堆栈式管理
 *
 * @author zyk
 */
public class ActivityManager {
    private static Stack<Activity> activityStack;
    private volatile static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (null == instance) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 此方法只在BussActivity onDestory时调用，其他情况交由框架处理
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivityOnly(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishToActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {

                if (null != activityStack.get(i) && activityStack.get(i) != activity) {
                    finishActivity(activityStack.get(i));
                    //activityStack.remove(activityStack.get(i));

                }
            }
        }
        activityStack.clear();
        activityStack.add(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void popToActivity(Class<?> activityCls) {
        Activity targetActivity = getActivity(activityCls);
        if (targetActivity != null && activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = activityStack.peek();
                if (activity != targetActivity) {
                    activityStack.pop();
                    finishActivityOnly(activity);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {

        while (activityStack.size() != 0) {
            Activity activity = activityStack.pop();
            if (null != activity) {
                activity.finish();
                activity = null;
                //finishActivity(activity);
            }
        }
        //		for (int i = 0, size = activityStack.size(); i < size; i++) {
        //
        //
        //			if (null != activityStack.get(i)) {
        //				finishActivity(activityStack.get(i));
        //				break;
        //			}
        //		}
        activityStack.clear();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllButCurrentActivity() {
        if (activityStack == null || activityStack.size() < 2) {
            return;
        }
        for (int i = activityStack.size() - 2; i >= 0; i--) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                //break;
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     * @author lxw
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void quitApp(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
