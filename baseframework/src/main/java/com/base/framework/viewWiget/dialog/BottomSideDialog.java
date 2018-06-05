package com.base.framework.viewWiget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import com.base.framework.R;
import com.base.framework.utils.HideKeyBoardUtils;
import com.base.framework.utils.screen.ScreenUtils;


/**
 * 底部弹出dialog
 */
public class BottomSideDialog extends Dialog {
    private static int MODE_HEIGHT_1 = -1;
    private static int MODE_HEIGHT_2 = -1;
    private static int MODE_HEIGHT_3 = -1;
    private static int MODE_HEIGHT_4 = -1;//一半

    public enum HeightMode {
        MODE_NONE, MODE_ALL, MODE_ONE_THIRD, MODE_TWO_THIRDS, MODE_AUTO,MODE_HALF;
    }

    private final static int DEFAULT_HEIGHT = LayoutParams.MATCH_PARENT;

    private Context mContext;
    private View rootView;
    private ViewGroup contentViewGroup;
    private boolean isCancleOutside = true;
    private int mWindowWidth = LayoutParams.MATCH_PARENT;
    private HeightMode mode = HeightMode.MODE_HALF;

    public BottomSideDialog(Context context) {
        super(context, R.style.dialog_side);
        this.mContext = context;
        init();
    }


    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.side_base_dialog, null);
        setContentView(rootView);
        contentViewGroup = (ViewGroup) findViewById(R.id.fl_content_root);
    }

    private View contentView;

    public BottomSideDialog setDialogContentView(View view) {
        contentViewGroup.removeAllViewsInLayout();
        this.contentView = view;
        if (view == null) return this;
        setAddLayoutParams();
        contentViewGroup.addView(view);
        return this;
    }

    private void setAddLayoutParams() {
        if (contentView == null) {
            return;
        }
        final View view = contentView;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        if (mode != HeightMode.MODE_AUTO) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        view.setLayoutParams(layoutParams);
    }


    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        isCancleOutside = cancel;
    }

    /**
     * @param mode 高度类型
     * @return
     */

    public BottomSideDialog setHeightMode(final HeightMode mode) {
        this.mode = mode;
        setAddLayoutParams();
        return this;
    }

    private int getHeightByMode() {
        initModeHeight();
        int dialogHeight = 0;
        switch (mode) {
            case MODE_ALL:
                dialogHeight = MODE_HEIGHT_3;
                break;
            case MODE_ONE_THIRD:
                dialogHeight = MODE_HEIGHT_1;
                break;
            case MODE_TWO_THIRDS:
                dialogHeight = MODE_HEIGHT_2;
                break;
            case MODE_NONE:
                dialogHeight = DEFAULT_HEIGHT;
                break;
            case MODE_HALF:
                dialogHeight=MODE_HEIGHT_4;
                break;
            case MODE_AUTO:
                return -3;
            default:
                break;
        }
        return dialogHeight;
    }

    private void initModeHeight() {
        if (MODE_HEIGHT_1 < 0 || MODE_HEIGHT_2 < 0||MODE_HEIGHT_4<0) {
            // 去除状态栏高度
            int heightPixels = mContext.getResources().getDisplayMetrics().heightPixels
                    - ScreenUtils.getStatusHeight(mContext);
            MODE_HEIGHT_1 = heightPixels / 3;
            MODE_HEIGHT_4=heightPixels/2;
            MODE_HEIGHT_2 = MODE_HEIGHT_1 * 2;
            MODE_HEIGHT_3 = heightPixels;
        }
    }


    public void show() {
        setCanceledOnTouchOutside(isCancleOutside);
        LayoutParams attributes = getWindow().getAttributes();
        if (HeightMode.MODE_AUTO == mode) {
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            autoHeight();
        } else {
            attributes.height = getHeightByMode();
        }
        attributes.width = mWindowWidth;
        attributes.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(attributes);
        super.show();
    }


    @Override
    public void dismiss() {
        HideKeyBoardUtils.hiddenKeyboard(mContext, contentView);
        super.dismiss();
    }

//    private void autoHeight() {
//        if (contentView == null) return;
//        rootView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                rootView.getViewTreeObserver().removeOnPreDrawListener(this);
//                adjustHeight(rootView.getMeasuredHeight());
//                return true;
//            }
//        });
//    }

//    private void adjustHeight(int height) {
//        initModeHeight();
//        LayoutParams attributes = getWindow().getAttributes();
//        if (height > MODE_HEIGHT_2) {//全屏
//            attributes.height = MODE_HEIGHT_3;
//        } else if (height > MODE_HEIGHT_1&&height<MODE_HEIGHT_2) {//半屏
//            attributes.height = MODE_HEIGHT_4;
//        } else if(height > MODE_HEIGHT_4&&height<MODE_HEIGHT_3){//2/3屏幕
//            attributes.height = MODE_HEIGHT_2;
//        } else{
//            attributes.height=MODE_HEIGHT_4;
//        }
//        getWindow().setAttributes(attributes);
//    }


}
