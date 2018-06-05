package com.base.framework.utils;

import android.app.Activity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import com.base.framework.R;


/**
 *
 */
public class ActivitySwitcher {
	//
	private final static int DURATION = 200;
	private final static float DEPTH = 0.0f;

	/* ----------------------------------------------- */

	public interface AnimationFinishedListener {
		/**
		 * Called when the animation is finished.
		 */
		public void onAnimationFinished();
	}

	/* ----------------------------------------------- */

	public static void animationIn(View container, WindowManager windowManager) {
		animationIn(container, windowManager, null);
	}

	public static void animationIn(View container, WindowManager windowManager, AnimationFinishedListener listener) {
		apply3DRotation(90, 0, false, container, windowManager, listener);
	}

	public static void animationOut(View container, WindowManager windowManager) {
		animationOut(container, windowManager, null);
	}

	public static void animationOut(View container, WindowManager windowManager, AnimationFinishedListener listener) {
		apply3DRotation(0, -90, true, container, windowManager, listener);
	}

	/* ----------------------------------------------- */

	private static void apply3DRotation(float fromDegree, float toDegree, boolean reverse, View container, WindowManager windowManager, final AnimationFinishedListener listener) {
		Display display = windowManager.getDefaultDisplay();
		final float centerX = display.getWidth() / 2.0f;
		final float centerY = display.getHeight() / 2.0f;

		final Rotate3dAnimation a = new Rotate3dAnimation(fromDegree, toDegree, centerX, centerY, DEPTH, reverse);
		a.reset();
		a.setDuration(DURATION);
		a.setFillAfter(true);
		a.setInterpolator(new AccelerateInterpolator());
		if (listener != null) {
			a.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					listener.onAnimationFinished();
				}
			});
		}
		container.clearAnimation();
		container.startAnimation(a);
	}

	public static void animalLeftToRight(Activity act) {
		animalRightToLeft(act);
	}
	public static void animalRightToLeft(Activity act) {
		if (Integer.valueOf(android.os.Build.VERSION.SDK) > 5) {
			act.overridePendingTransition(R.anim.activity_in_right_to_left, R.anim.activity_stay);
		}
	}
	public static void animalTop2Bottom(Activity activity){
		if (Integer.valueOf(android.os.Build.VERSION.SDK) > 5) {
			activity.overridePendingTransition(R.anim.activity_stay, R.anim.activity_out_top_to_bottom);
		}
	}
	public static void animalBottom2Top(Activity activity){
		if (Integer.valueOf(android.os.Build.VERSION.SDK) > 5) {
			activity.overridePendingTransition(R.anim.activity_in_bottom_to_top, R.anim.activity_stay);
		}
	}
	public static void delayFinishActivity(final Activity activity){
		if(null==activity)return;
		new android.os.Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				activity.finish();
			}
		},500);
	}
}