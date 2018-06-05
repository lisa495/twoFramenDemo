package com.basicframe.demo;

import android.content.Intent;
import android.view.View;
import com.base.framework.activity.ReBaseActivity;
import com.base.framework.presenter.BasePresenter;
import com.base.framework.viewWiget.common.TopBarView;

public  class MainActivity extends ReBaseActivity {


    @Override
    protected void beforeInitView(Intent intent) {

    }

    /**
     *  默认处理一些，具体根据自己当前设置,重写这个{@link #initTopBarView(TopBarView)}方法
     *
     *  如果topBarView和需求不一致，自雷根据具体需求可重写此方法
     */
    @Override
    protected View createTitleView() {
        TopBarView topBarView=new TopBarView(this);
        topBarView.leftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initTopBarView(topBarView);
        return topBarView;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView(View contentView) {

    }

    @Override
    protected void loadData(BasePresenter mPresenter) {

    }

    @Override
    protected void setListener() {

    }

    //初始化topBarView，
    protected void initTopBarView(TopBarView topBarView){}



}
