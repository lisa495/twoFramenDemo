package com.base.framework.viewWiget.common;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @author zyk  网络加载view，分别以加载过程中(loading)、加载出错(error)、加载完成且有数据(loadedView)、加载完成但空数据(empty)呈现
 *         使用方法：网络加载完成后，直接调用 {@link BaseLoadPagerView#showView(T)}  },校验数据源根据其业务可复写{@link BaseLoadPagerView#checkLoadResult(T)}
 *         日期: 2017/10/16
 */
public abstract class BaseLoadPagerView<T> extends FrameLayout {
    private static final int STATE_UNLOADED = 0;//未知状态
    private static final int STATE_LOADING = 1;//正在加载状态
    private static final int STATE_ERROR = 2;//加载完毕，但是出错状态
    private static final int STATE_EMPTY = 3;//加载完毕，但是没有数据未知状态
    private static final int STATE_SUCCESS = 4;//加载成功状态
    private @LoadResult
    int currentState = STATE_UNLOADED;
    private View loadingView, loadedView, errorView, emptyView;
    private Context context;

    protected Object list;//数据源

    public BaseLoadPagerView(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public BaseLoadPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    //初始化view
    private void initView(Context context) {
        loadingView = createLoadingView(context);
        if (null != loadingView) {
            addView(loadingView);
        }
        View tempErrorView = createErrorView(context);
        if (null != tempErrorView) {
            errorView = tempErrorView;
            addView(errorView);
            errorView.setVisibility(View.GONE);
        }
        View tempEmptyView = createEmptyView(context);
        if (null != tempEmptyView) {
            emptyView = tempEmptyView;
            addView(emptyView);
            emptyView.setVisibility(View.GONE);
        }
    }
    @LoadResult
    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(@LoadResult int currentState) {
        this.currentState = currentState;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //初始化以正在加载为状态--调试用
//        currentState=STATE_LOADING;
//        showCurrentView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        currentState = STATE_UNLOADED;
    }

    //显示当前view
    private void showCurrentView(T list) {
        if (null != loadingView) {
            loadingView.setVisibility(currentState == STATE_LOADING ? VISIBLE : INVISIBLE);
        }
        if (null != errorView)
            errorView.setVisibility(currentState == STATE_ERROR ? VISIBLE : INVISIBLE);
        if (null != emptyView)
            emptyView.setVisibility(currentState == STATE_EMPTY ? VISIBLE : INVISIBLE);
        if (currentState == STATE_SUCCESS && null == loadedView) {
            View tempLoadedView = createLoadedView(context);
            if (null != tempLoadedView) {
                loadedView = tempLoadedView;
                addView(loadedView);
            }
        }
        if (currentState == STATE_SUCCESS) {
            loadedView.setVisibility(VISIBLE);
            updateLoadedView(list);
        }
    }

    public void showView(T list) {
        this.list=list;
        if (currentState == STATE_UNLOADED || currentState == STATE_ERROR) {
            currentState = STATE_LOADING;
//            LoadResult result = checkLoadResult(list);
            currentState = checkLoadResult(list);
            showCurrentView(list);
            return;
        }
        showCurrentView(list);
    }

    /**
     * 检查数据的结果，默认以集合呈现---子类根据其业务需要，可重写
     *
     * @param list --数据
     * @return 检查结果
     */
    protected int checkLoadResult(T list) {

        if (null == list) {
            return STATE_ERROR;
        }
        if (list instanceof List) {
            List lists = (List) list;
            if (lists.isEmpty()) {
                return STATE_EMPTY;
            } else {
                return STATE_SUCCESS;
            }
        } else {
            return STATE_SUCCESS;
        }
    }


//    public enum LoadResult {
//        ERROR(2), EMPTY(3), SUCCESS(4);
//        private int id;
//
//        LoadResult(int id) {
//            this.id = id;
//        }
//
//        public int getId() {
//            return id;
//        }
//    }
    @IntDef({STATE_UNLOADED,STATE_LOADING,STATE_ERROR,STATE_EMPTY,STATE_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    @interface LoadResult {}

    /**
     * 创建加载出错View
     *
     * @param context 上下文
     * @return 加载出错View
     */
    protected abstract View createErrorView(Context context);

    /**
     * 创建加载成功，但是空数据View
     *
     * @param context 上下文
     * @return 加载出错View
     */
    protected abstract View createEmptyView(Context context);

    /**
     * 创建加载中view --页面呈现
     *
     * @param context 上下文
     * @return 加载中view
     */
    protected abstract View createLoadingView(Context context);

    /**
     * 创建加载完成View
     *
     * @param context 上下文
     * @return 加载完成View
     */
    protected abstract View createLoadedView(Context context);

    /**
     * 更新加载完成view,并且有数据
     * @param t  数据源
     */
    protected abstract void updateLoadedView(T t);


}
