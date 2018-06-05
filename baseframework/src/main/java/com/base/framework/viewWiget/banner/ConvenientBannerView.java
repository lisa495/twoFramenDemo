package com.base.framework.viewWiget.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.framework.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;


/**
 * 页面翻转控件，极方便的广告栏
 */

public class ConvenientBannerView extends LinearLayout {

    public ConvenientBannerView(Context context) {
        this(context, null);
    }

    public ConvenientBannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConvenientBannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(21)
    public ConvenientBannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private View root;
    private Banner banner;
    private ImageView iv_place_hold;

    private void initView(Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.view_banner, this, true);
        banner = (Banner) findViewById(R.id.banner);
        iv_place_hold = (ImageView) findViewById(R.id.iv_place_hold);
    }

    /**
     * 设置轮播图 URL集合，加载轮播图
     *
     * @param imageUrls 例如：Arrays.asList("http://xxx.com/img1.jpg","http://img2.jpg")
     * @param failResID 加载失败后显示的本地图片资源的ID
     */
    public void show(@NonNull List<String> imageUrls, int failResID, int placeHoldResID) {
        if (imageUrls.isEmpty()) {
            Log.d(ConvenientBannerView.class.getSimpleName(), "数据集合为空");
            return;
        }

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new CustomImageLoader(failResID, placeHoldResID));
        //设置图片集合
        banner.setImages(imageUrls);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        iv_place_hold.setVisibility(GONE);
    }

    /**
     * 添加子轮播图点击处理
     *
     * @param listener
     */
    public void setOnBannerListener(OnBannerListener listener) {
        banner.setOnBannerListener(listener);
    }


    /**
     * 设置此banner控件的高度——控件宽度等于屏幕宽度，控件高度为屏幕宽度的90/320
     *
     * @param screenWidth
     */
    public void resetSize(float screenWidth) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) banner.getLayoutParams();
        layoutParams.height = (int) (90f / 320 * screenWidth);
        banner.setLayoutParams(layoutParams);
    }

    /**
     * call at activity.onStart
     */
    public void startAutoPlay() {
        banner.startAutoPlay();
    }

    /**
     * call at activity.onStop
     */
    public void stopAutoPlay() {
        banner.stopAutoPlay();
    }
}
