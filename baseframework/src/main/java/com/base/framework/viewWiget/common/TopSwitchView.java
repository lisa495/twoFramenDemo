package com.base.framework.viewWiget.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.base.framework.R;
import com.base.framework.utils.clicklock.ClickLockUtils;
import com.base.framework.utils.density.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 顶部 多项项切换按钮
 * create by zhaoyk 2015-09-28
 */
public class TopSwitchView extends LinearLayout implements View.OnClickListener{
	
	private final static String tag = "QJSTopSwitchView";
	
	private Context mContext;
	/**
	 * 按钮s
	 */
	private List<Button> btsList;
	/**
	 * 每个按钮宽度
	 */
	private int itemWidth = 0;
	
	/*************************暂时写死属性，应该支持扩展*****************************************/
	/**
	 * 左边背景图片
	 */
	final int rs_bt_left = R.drawable.top_switch_item_left_bg;//左按钮
	/**
	 * 右边按钮图片
	 */
	final int rs_bt_right = R.drawable.top_switch_item_right_bg;//右按钮
    /**
     * 中间按钮图片
     */
	final int rs_bt_center = R.drawable.top_switch_item_center_bg;//中间
	/**
	 * 文字颜色
	 */
	final int tv_color = R.color.text_white_red_select;//文字
	/**
	 * 单按钮背景
	 */
	final int rs_bt_single = R.drawable.top_switch_item_center_bg;//单按钮--目前以中间的View样式呈现
	
    /**
     * 当前选中view
     */
	private Button cur_select_view;
	/**
	 * 点击listener
	 */
	private TopClickListener mTopClickListener;
	
	public TopSwitchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public TopSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public TopSwitchView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
   
	private void init(){
		setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.HORIZONTAL);
		setFocusable(true);
		setClickable(true);
		btsList = new ArrayList<>();
	}
	public void performClick(int pos) {
		if(btsList == null || btsList.size()<= pos){
			return;
		}
		btsList.get(pos).performClick();
	}
	
	/**
	 * 设置按钮显示文字
	 */
	public void setButtonText(Object ... buttons){
		removeAllViews();
		btsList.clear();
		if(buttons == null || buttons.length == 0){
			return;
		}
		int size = buttons.length;
		for(int n=0;n<size;n++){
			String item = buttons[n].toString();
			Button bt = new Button(mContext);
			bt.setText(item);
			bt.setTextSize(13);
			if(size == 1){
				bt.setBackgroundResource(rs_bt_single);
				bt.setSelected(true);
			}else if(size > 1 && n == 0){
				bt.setBackgroundResource(rs_bt_left);
			}else if(size >= 1 && n == size -1){
				bt.setBackgroundResource(rs_bt_right);
			}else{
				bt.setBackgroundResource(rs_bt_center);
			}
			bt.setTextColor(getResources().getColorStateList(tv_color));
			if(TextUtils.isEmpty(item)){
				bt.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT));
			}else{
				LayoutParams params=new LayoutParams(itemWidth, LayoutParams.MATCH_PARENT, 1);
				params.leftMargin= DensityUtil.dip2px(mContext, 5);
				params.rightMargin=DensityUtil.dip2px(mContext, 5);
				bt.setLayoutParams(params);
			}
			
			bt.setOnClickListener(this);
			addView(bt);
			btsList.add(bt);
		}
	}
	
	
	/**
	 * TODO  设置item的宽度，这里设置后应该更新每个button的宽度 ！！暂时不处理 设置item宽度必须在设置button显示之前
	 * @param width  宽度
	 */
	public void setItemWidth(int width){
		this.itemWidth = width;
	}

	@Override
	public void onClick(View v) {
		if(!ClickLockUtils.isCanClick(tag)){
			return;
		}
		if(v == cur_select_view){
			if(mTopClickListener != null){
				mTopClickListener.onReClick(cur_select_view.getText().toString(), btsList.indexOf(v));
			}
			return;
		}
		if(cur_select_view != null){
			cur_select_view.setSelected(false);
		}
		cur_select_view = (Button) v;
		cur_select_view.setSelected(true);
		if(mTopClickListener != null){
			mTopClickListener.onTopClick(cur_select_view.getText().toString(),  btsList.indexOf(v));
		}
	}

	/**
	 * 设置选中项
	 * @param pos  选中的位置
	 */
	public void setSelect(int pos){
		if(cur_select_view != null){
			cur_select_view.setSelected(false);
		}
		if(btsList == null || pos >= btsList.size()){
			return;
		}
		cur_select_view = btsList.get(pos);
		cur_select_view.setSelected(true);
	}
	/**
	 * 获取当前选中位置
	 * @return  当前选中的位置
	 */
	public int getSelectPos(){
		return btsList.indexOf(cur_select_view);
	}

	/**
	 * 设置点击点击事件
	 * @param clickListener  监听
	 */
	public void setTopClickListener(TopClickListener clickListener){
		this.mTopClickListener = clickListener;
	}
	/**
	 * @author zhaoyk
	 * 顶部点击事件
	 */
	public interface TopClickListener{
		void onTopClick(String tag, int pos);
		void onReClick(String tag, int pos);
	}
}
