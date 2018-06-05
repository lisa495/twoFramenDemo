package com.base.framework.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 获取按钮   倒计时 工具类
 */
public class TimerTaskUtil {

    private volatile static TimerTaskUtil instance;
    private TextView textView;
    private int time=60;
    private String afterValue;//倒计时完毕后显示的信息
    private TimerTaskUtil() {}

    public static TimerTaskUtil getInstance() {
        if (instance == null) {
            synchronized (TimerTaskUtil.class) {
                if (null == instance) {
                    instance = new TimerTaskUtil();
                }
            }
        }
        return instance;
    }

    class MyCount extends CountDownTimer {


        MyCount(long millisInFuture, long countDownInterval) {
            // 1.倒计时总时间 2. 间隔时间
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (textView != null) {
                textView.setText(String.valueOf(time));
                time--;
            }
        }

        @Override
        public void onFinish() {
            textView.setText(afterValue);
            textView.setClickable(true);
            cancel();
        }

    }

    /**
     * 开始倒计时
     * @param tv 倒计时作用的view
     * @param afterValue 倒计时完毕显示的文字信息
     * @param totalTime 倒计时的总时间
     */
    public void startCountDown(TextView tv,String afterValue,int totalTime) {
        this.textView = tv;
        this.afterValue=afterValue;
        if(totalTime>0){
            this.time=totalTime;
        }

        textView.setClickable(false);
        MyCount smsCount = new MyCount(time * 1000, 1000);
        smsCount.start();
    }
}
