package com.gc.frame.widget.dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class MDialog extends AlertDialog implements View.OnClickListener {

	protected int pxDialogHeight;
	protected int pxDialogWidth;
	protected Object result;
	protected int calledByViewId;

	protected MDialog(Context context) {
		super(context);
		result = context;
		setCanceledOnTouchOutside(false);
	}

	public void setContentView(int layoutResID, boolean resize) {
		if (resize == true) {
			View view = getLayoutInflater().inflate(layoutResID, null);
			setContentView(view);
			return;
		}
		super.setContentView(layoutResID);
	}

	@Override
	public void setContentView(View view) {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				pxDialogWidth, pxDialogHeight);
		super.setContentView(view, params);
	}

	public void show(int topLeftPointX, int topLeftPointY) {
		int dialogCenterPointX = topLeftPointX + pxDialogWidth / 2;
		int dialogCenterPointY = topLeftPointY + pxDialogHeight / 2;

		Window window = this.getWindow();
		WindowManager m = getWindow().getWindowManager();
		Display d = m.getDefaultDisplay();
		int screenWidth = d.getWidth();
		int screenHeight = d.getHeight();

		WindowManager.LayoutParams lp = window.getAttributes();
		lp.x = dialogCenterPointX - screenWidth / 2;
		lp.y = dialogCenterPointY - screenHeight / 2;
		window.setGravity(Gravity.CENTER);
		window.setAttributes(lp);

		show();
	}

	@Override
	public void onClick(View v) {
	}

	protected void onResultClick(int which, String resultValue) {
		if (result instanceof OnDialogResultClickListener) {
			OnDialogResultClickListener resultObject = (OnDialogResultClickListener) result;
			resultObject.onDialogResultClick(calledByViewId, this.getClass(),
					which, resultValue);
		}

		this.dismiss();
	}

	protected void onResultClick(int which) {
		onResultClick(which, "");
	}

	public int getPxDialogHeight() {
		return pxDialogHeight;
	}

	public void setPxDialogHeight(int pxDialogHeight) {
		// float height = pxDialogHeight / 600f
		// * getContext().getResources().getDisplayMetrics().heightPixels;
		// this.pxDialogHeight = Math.round(height + 0.5f);
		this.pxDialogHeight = pxDialogHeight;
	}

	public int getPxDialogWidth() {
		return pxDialogWidth;
	}

	public void setPxDialogWidth(int pxDialogWidth) {
		// float width = pxDialogWidth / 800f
		// * getContext().getResources().getDisplayMetrics().widthPixels;
		// this.pxDialogWidth = Math.round(width + 0.5f);
		this.pxDialogWidth = pxDialogWidth;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public void setCalledByViewId(int viewId) {
		this.calledByViewId = viewId;
	}
}
