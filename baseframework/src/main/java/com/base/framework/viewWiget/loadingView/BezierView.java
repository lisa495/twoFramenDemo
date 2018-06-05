package com.base.framework.viewWiget.loadingView;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.base.framework.R;


/**
 * loading加载view 来回摆动的view
 */

public class BezierView extends View {
    private int mWidth = 0;
    private int mHeight = 0;
    private Circle big = new Circle();//固定位置的圆形，居中
    private Circle small = new Circle();//移动的圆形
    private Paint mPaint;
    private float mCircleConnectLength;
    private float mBigRadio = 0;

    public BezierView(Context context) {
        super(context);
        init(context,null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        mPaint = new Paint();
        int color = Color.BLUE;
        mWidth = context.getResources().getDimensionPixelSize(R.dimen.bezier_width);
        mHeight = context.getResources().getDimensionPixelSize(R.dimen.bezier_height);
        int duration = 1500;
        if(attrs!=null){
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BezierView);
            color = a.getColor(R.styleable.BezierView_circle_color,color);
            mWidth = a.getDimensionPixelSize(R.styleable.BezierView_bWidth,mWidth);
            mHeight = a.getDimensionPixelSize(R.styleable.BezierView_bHeight,mHeight);
            duration = a.getInteger(R.styleable.BezierView_duration,duration);
            a.recycle();
        }

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        big.x = mWidth/2f;
        big.y = mHeight/2f;
        mBigRadio = big.radio = mHeight/4f;


        mCircleConnectLength = big.x/1.3f;


        small.radio = mHeight/8f;
        small.x = small.radio;//小圆x位置不断变化
        small.y = mHeight/2f;
//      动态变化小圆的x位置
        startAnim(duration);
    }

    private void startAnim(long duration){
        ValueAnimator animator = ValueAnimator.ofFloat(small.x,mWidth-small.radio);//范围为View整个宽度
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(duration);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);

        animator.start();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                small.x = (float) animation.getAnimatedValue();
//                Logger.wtf("small.x="+small.x);
//                invalidate();
                invalidate(0,0,mWidth,mHeight);
            }
        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(small.x,small.y,small.radio,mPaint);
        canvas.drawCircle(big.x,big.y,big.radio,mPaint);
        if(isEnableConnect()){
            //绘制贝塞尔曲线

            Path path = bezierPath(30,60);
            canvas.drawPath(path,mPaint);

        }
//        else{//超出范围
//            canvas.drawCircle(small.x,small.y,small.radio,mPaint);
//        }
    }


    private boolean isEnableConnect(){
        float distance = (float) Math.sqrt(Math.pow(small.x - big.x, 2) + Math.pow(small.y - big.y, 2));


        float scale = 0.1f -  0.1f * (distance / mCircleConnectLength);
        big.radio = mBigRadio * (1 + scale);

        if(distance<mCircleConnectLength){
            return true;
        }
        return false;
    }


    private Path bezierPath(float offset1, float offset2){
//        float degrees =(float) Math.toDegrees(Math.atan(Math.abs(cy2 - cy1) / Math.abs(cx2 - cx1)));

        /* 根据大圆与小圆的相对位置求四个点 */
        float differenceX = big.x - small.x;

        /* 两条贝塞尔曲线的四个端点 */
        float x1,y1,x2,y2,x3,y3,x4,y4;
        if (differenceX > 0) {//小圆在大圆左边
            x2 = small.x + small.radio * (float) Math.cos(Math.toRadians(offset2));
            y2 = small.y + small.radio * (float) Math.sin(Math.toRadians(offset2));
            x4 = small.x + small.radio * (float) Math.cos(Math.toRadians(offset2));
            y4 = small.y - small.radio * (float) Math.sin(Math.toRadians(offset2));
            x1 = big.x - big.radio * (float) Math.cos(Math.toRadians(offset1));
            y1 = big.y + big.radio * (float) Math.sin(Math.toRadians(offset1));
            x3 = big.x - big.radio * (float) Math.cos(Math.toRadians(offset1));
            y3 = big.y - big.radio * (float) Math.sin(Math.toRadians(offset1));
        } else {//if (differenceX < 0)小圆在大圆右边
            x2 = small.x - small.radio * (float) Math.cos(Math.toRadians(offset2));
            y2 = small.y + small.radio * (float) Math.sin(Math.toRadians(offset2));
            x4 = small.x - small.radio * (float) Math.cos(Math.toRadians(offset2));
            y4 = small.y - small.radio * (float) Math.sin(Math.toRadians(offset2));
            x1 = big.x+ big.radio * (float) Math.cos(Math.toRadians(offset1));
            y1 = big.y + big.radio * (float) Math.sin(Math.toRadians(offset1));
            x3 = big.x + big.radio * (float) Math.cos(Math.toRadians(offset1));
            y3 = big.y - big.radio* (float) Math.sin(Math.toRadians(offset1));
        }

        float anchorX1,anchorY1,anchorX2,anchorY2;
        anchorX1 = (x2 + x3) / 2;
        anchorY1 = (y2 + y3) / 2;
        anchorX2 = (x1 + x4) / 2;
        anchorY2 = (y1 + y4) / 2;

//      画粘连体
        Path path = new Path();
        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX1, anchorY1, x2, y2);
        path.lineTo(x4, y4);
        path.quadTo(anchorX2, anchorY2, x3, y3);
        path.lineTo(x1, y1);
        return path;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSizeAndState(mWidth,widthMeasureSpec,MeasureSpec.UNSPECIFIED),resolveSizeAndState(mHeight,heightMeasureSpec,MeasureSpec.UNSPECIFIED));
    }

    class Circle{
        float x,y,radio;
    }
}
