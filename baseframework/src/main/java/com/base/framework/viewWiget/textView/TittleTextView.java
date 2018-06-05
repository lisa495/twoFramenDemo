package com.base.framework.viewWiget.textView;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 分散对齐的TextView,适用于个人中心的View等
 *
 * 使用时候在布局weight设置为0，思路：weight为0，则宽度就确定了
 *
 * @author zhaoyk
 * @date 2015/9/1
 */
public class TittleTextView extends LinearLayout {

	private Context mContext;
	//字体颜色
//	private int textColor=getResources().getColor(R.color.text_color_dark_gray);
	private int textColor= Color.parseColor("#999999");
	//字体大小
	private int size=16;

	public TittleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public TittleTextView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	private void init(){
		setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.HORIZONTAL);
		setFocusable(false);
		setClickable(false);
	}
	/**
	 * 设置显示内容，如果需要设置字体和颜色，需要在调用这个方法之前调用setTextColor();和setTextSize();
	 * 默认颜色未深灰色，字体大小16像素
	 * @param str  显示的字符串内容
	 */
	public void setContentText(String str){
		if(TextUtils.isEmpty(str))return;
		removeAllViews();
		char[] newChars=initNewChars(str.toCharArray());
		for (int i = 0; i < newChars.length; i++) {
			if(i%2==0){
				TextView textShowView=initTextView();
				textShowView.setTextColor(textColor);
				textShowView.setTextSize(size);
				textShowView.setText(String.valueOf(newChars[i]));
				addView(textShowView);
			}else{
				TextView spaceShowView=initTextView();
				LayoutParams layoutParams=new LayoutParams(0, LayoutParams.WRAP_CONTENT);
				layoutParams.weight=1;
				spaceShowView.setLayoutParams(layoutParams);
				addView(spaceShowView);
			}
		}
	}
	/**
	 * 初始化textview
	 * @return  TextView
	 */
	private TextView initTextView(){
		TextView textView=new TextView(mContext);
		LayoutParams layoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textView.setLayoutParams(layoutParams);
		return textView;
	}
	/**
	 * 设置字体颜色
	 * @param color  字体颜色
	 */
	public void setTextColor(int color){
		this.textColor=color;
	}
	/**
	 * 设置字体的大小
	 * @param size   字体大小
	 */
	public void setTextSize(int size){
		this.size=size;
	}
	/**
	 * 给传入的字符串隔个加空格
	 * @param chars  字符串
	 * @return  字符数组
	 */
	private  char[] initNewChars(char[] chars){
		int length=chars.length+chars.length-1;
		char[] newChars=new char[length];
		for(int i=0;i<newChars.length;i++){
			if(i%2==0){
				newChars[i]=chars[i/2];
			}else{
				newChars[i]=" ".charAt(0);
			}
		}
		return newChars;
	}
}
