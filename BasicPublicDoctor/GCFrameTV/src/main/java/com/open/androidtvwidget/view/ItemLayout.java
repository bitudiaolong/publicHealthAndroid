package com.open.androidtvwidget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.gc.frame.tv.R;

public class ItemLayout extends RelativeLayout {
	private boolean isFlag;

	public boolean isFlag() {
		return isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	public ItemLayout(Context context) {
		super(context);
	}

	public ItemLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray custom = getContext().obtainStyledAttributes(attrs,
				R.styleable.ItemLayout);
		isFlag = custom.getBoolean(R.styleable.ItemLayout_isFlag,
				true);
	}

	@Override
	public void dispatchSetSelected(boolean selected) {

		if (selected && isFlag) {
			ItemLayout.this.animate().scaleX(1.05f).scaleY(1.05f)
					.setDuration(250).start();
			// ObjectAnimator anim = ObjectAnimator//
			// .ofFloat(this, "zhy", 1.0F, 1.05F)//
			// .setDuration(250);//
			// anim.start();
			// anim.addUpdateListener(new AnimatorUpdateListener() {
			// @Override
			// public void onAnimationUpdate(ValueAnimator animation) {
			// float cVal = (Float) animation.getAnimatedValue();
			// ItemLayout.this.setScaleX(cVal);
			// ItemLayout.this.setScaleY(cVal);
			// Log.i("ItemLayout",
			// "---------------onAnimationUpdate-----true--------");
			// }
			// });
		} else {
			ItemLayout.this.animate().scaleX(1.0f).scaleY(1.0f)
					.setDuration(250)
					.start();

			// ObjectAnimator anim = ObjectAnimator//
			// .ofFloat(this, "zhy", 1.05F, 1.0F)//
			// .setDuration(0);//
			// anim.start();
			// anim.addUpdateListener(new AnimatorUpdateListener() {
			// @Override
			// public void onAnimationUpdate(ValueAnimator animation) {
			// float cVal = (Float) animation.getAnimatedValue();
			// ItemLayout.this.setScaleX(cVal);
			// ItemLayout.this.setScaleY(cVal);
			// Log.i("ItemLayout",
			// "---------------onAnimationUpdate-----false--------");
			// }
			// });
		}
		super.dispatchSetSelected(selected);

	}

}
