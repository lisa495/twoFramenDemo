package com.base.framework.presenter;

/**
 * presenter基类
 */

public class BasePresenter<T extends IBaseView> {
    protected T mView;
    private BasePresenter(){}//防止外部new无参数的构造函数
    public BasePresenter(T t){
        attach(t);
    }
    private void attach(T baseView){
        mView = baseView;
//        return baseView;
//        mView.onFail(IBaseView.TYPE_DIALOG);
    }
    public void detach(){
//        mView = null;//应该取消网络请求任务 然后view赋值为null 防止内存泄露
    }
}
