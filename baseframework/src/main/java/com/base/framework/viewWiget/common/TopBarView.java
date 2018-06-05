package com.base.framework.viewWiget.common;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framework.R;


/**
 * 头部导航条  （左侧返回按钮 中间文字 右侧按钮）
 * @author eyding
 *
 */
public class TopBarView extends RelativeLayout{

	private int barHeight;//高度 - 固定
	
	private View view_back;
	private ImageView title_left_iv;
	private TextView titleName,titleRightTv;
	private View view_right;//
	private ImageView title_right_iv;
	
	public TopBarView(Context context) {
		this(context,null);
	}

	public TopBarView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	public TopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		initView();
	}

	private void initView(){
		barHeight = getResources().getDimensionPixelSize(R.dimen.px_88_to_dp);
		
		LayoutInflater.from(getContext()).inflate(R.layout.view_top_bar, this,true);
		setBackgroundColor(Color.BLUE);
		
		view_back = findViewById(R.id.back);
		title_left_iv =  findViewById(R.id.title_left_iv);
		titleName =  findViewById(R.id.titleName);
		view_right = findViewById(R.id.rightBtn);
		title_right_iv =  findViewById(R.id.title_right_iv);
		titleRightTv=  findViewById(R.id.title_right_tv);
	}
	
	@Override
	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		params.height = barHeight;
		super.setLayoutParams(params);
	}

	public void setLeftButtonVisible(boolean visible) {
		view_back.setVisibility(visible ? VISIBLE : GONE);
	}

	public TopBarView setBkg(int color){
		setBackgroundColor(color);
		return this;
	}
	
	public TopBarView rightVisiable(boolean isVis){
		view_right.setVisibility(isVis?View.VISIBLE:View.INVISIBLE);
		return this;
	}
	
	public TopBarView title(CharSequence title){
		
		titleName.setText(title);
		
		return this;
	}

	public TopBarView titleColor(int color){
		titleName.setTextColor(color);
		return this;
	}



	public TopBarView leftClickListener(OnClickListener leftClick){
		view_back.setOnClickListener(leftClick);
		return this;
	}

	public TopBarView rightClickListener(OnClickListener rightClick){
		
		view_right.setOnClickListener(rightClick);
		
		return this;
	}
	public TopBarView rightText(CharSequence rightText){
		if(!TextUtils.isEmpty(rightText)){
			titleRightTv.setText(rightText);
			titleRightTv.setVisibility(VISIBLE);
			title_right_iv.setVisibility(View.GONE);
		}
		return this;
	}
	public TopBarView rightImage(int id){
		titleRightTv.setVisibility(View.GONE);
		title_right_iv.setVisibility(View.VISIBLE);
		title_right_iv.setBackgroundResource(id);
		return this;
	}
	public TopBarView leftVisiable(boolean isVis){
		view_back.setVisibility(isVis?View.VISIBLE:View.INVISIBLE);
		return this;
	}

	public TopBarView leftImage(int id){
		title_left_iv.setImageResource(id);
		return this;
	}

}
