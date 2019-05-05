package com.gc.frame.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.frame.widget.R;



public class DialogProgressBar extends AlertDialog {
	private final Context cContext;
	private final String cMessage;
	protected OnProgressListener cExecObject;
	private ProgressTask cTask;
	private final int cCalledByViewId;

	private View view;
	private AlertDialog alertDialog;

	public DialogProgressBar(Context context, String message,
			int calledByViewId, OnProgressListener execObject) {
		super(context);
		cContext = context;
		cExecObject = execObject;
		cMessage = message;
		cCalledByViewId = calledByViewId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alertDialog = new AlertDialog.Builder(cContext,R.style.AlertDialogMine).create();
		View alert = getLayoutInflater()
				.inflate(R.layout.dialog_progress, null);
		alertDialog.setView(alert);
		// alertDialog.setContentView(alert);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();

		view = alertDialog.getWindow().getDecorView();
		alertDialog.getWindow().setContentView(R.layout.dialog_progress);

		TextView textView = (TextView) view
				.findViewById(R.id.textViewDialogMessage);
		textView.setText(cMessage);

		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				cContext, R.anim.load_animation);
		// 使用ImageView显示动画
		ImageView spaceshipImage = (ImageView) view.findViewById(R.id.img);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

	}

	@Override
	public void show() {
		// to start ProgressDialog
		super.show();
		dismiss();
		cTask = new ProgressTask(cExecObject, cCalledByViewId);
		cTask.execute();

	}

	public void hide() {
		if (cTask != null && cTask.isCancelled() == false) {
			cTask.cancel(true);
		}
		alertDialog.hide();
	}

	public void misDialog() {
		if (cTask != null && cTask.isCancelled() == false) {
			cTask.cancel(true);
		}
		alertDialog.dismiss();
	}

	@Override
	public void cancel() {
		if (cTask != null && cTask.isCancelled() == false) {
			cTask.cancel(true);
		}
		alertDialog.dismiss();
	}

	public class ProgressTask extends AsyncTask<Void, Integer, Void> {

		public ProgressTask(Object execObject, int calledByViewId) {
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			if (cExecObject instanceof OnProgressListener) {
				OnProgressListener execute = cExecObject;
				execute.exec(cCalledByViewId);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void arg0) {

//			dismissDialog();
			if (cExecObject instanceof OnProgressListener) {
				OnProgressListener execute = cExecObject;
				execute.finish(cCalledByViewId);
				
			}

		}

	}

}
