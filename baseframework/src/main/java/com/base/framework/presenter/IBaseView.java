package com.base.framework.presenter;

/**
 * view基类
 */

public interface IBaseView {

    /**
     * 显示加载框
     * @param msg 加载看的消息
     */
    void showLoading(String msg);

    /**
     * 隐藏对话框
     */
    void hideLoading();


}
