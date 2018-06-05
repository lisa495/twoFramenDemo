package com.base.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.base.framework.R;
import com.base.framework.presenter.BasePresenter;
import com.base.framework.utils.ActivityManager;

/**
 *  activity的基类
 */

public abstract class ReBaseActivity<T extends BasePresenter> extends FragmentActivity {

//    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null!=getIntent()){
            beforeInitView(getIntent());
        }
        T mPresenter=createPresenter();
        // 加载view布局
        setCommContentView(getContentResID());
        loadData(mPresenter);
        setListener();
        addStack();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        AntiHijackingUtil.checkActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeStack();
    }

    private LinearLayout createContainer(){
        LayoutInflater inflater = getLayoutInflater();
        return (LinearLayout) inflater.inflate(R.layout.activity_base_container,null);
    }
    private void setCommContentView(@LayoutRes int contentResID){
        LinearLayout container=createContainer();
        View header= createTitleView();
        View contentView = getLayoutInflater().inflate(contentResID,container,false);
        initContentView(contentView);
        if(null!=header){
            container.addView(header);
        }
        container.addView(contentView);
        super.setContentView(container);
    }

    private void addStack(){
        ActivityManager.getInstance().addActivity(this);
    }
    private void removeStack(){
        ActivityManager.getInstance().removeActivity(this);
    }

    /**
     *获取上个页面传递的参数，如没有可不用处理
     * @param intent intent此处作用是传递参数
     */
    protected abstract void beforeInitView(Intent intent);

    /**
     * 创建标题view
     * @return 标题对象View
     */
    protected abstract View createTitleView();

    /**
     * 创建当前present
     * @return 当前的present
     */
    protected abstract T createPresenter();

    /**
     * 获取内容布局资源
     * @return 布局文件
     */
    protected abstract int getContentResID();

    /**
     * 初始化内容view
     * @param contentView  内容view
     */
    protected abstract void initContentView(View contentView);

    /**
     * 加载数据
     * @param mPresenter 加载数据的发起者
     */
    protected abstract void loadData(T mPresenter);

    /**
     * 给当前页面的view设置监听
     */
    protected abstract void setListener();


}
