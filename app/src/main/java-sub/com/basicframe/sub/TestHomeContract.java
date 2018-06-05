package com.basicframe.sub;

import com.base.framework.presenter.BasePresenter;
import com.base.framework.presenter.IBaseView;

/**
 * 测试类
 */

public class TestHomeContract {
    interface HomeView extends IBaseView{

    }
    public class HomePresent extends BasePresenter<HomeView>{

        public HomePresent(HomeView homeView) {
            super(homeView);
        }
        public void test(){
            mView.showLoading("正在加载");
        }
    }
}
