package com.gc.frame.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class GroupLayout extends FrameLayout implements GroupContextView {

	private int move_direction = 0;

	private int nmovex = 0, nmovey = 0;

	Thread thread = null;

	private OnCurrentViewMoveListener onMoveListener;

	private int movespeed = 15;

	private Object currentObject;

	private int currentId, ncurrentId;

	private OnTouchListener onTouchListener;

	private Handler hand = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				scrollTo(msg.arg1, msg.arg2);
				break;
			case 1:
				while (GroupLayout.this.getChildCount() > 1) {
					GroupLayout.this.removeViewAt(0);
				}
				scrollTo(0, 0);
				break;
			}
		}
	};

	public GroupLayout(Context context) {
		super(context);
	}

	public GroupLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		for (int index = 0; index < getChildCount(); index++) {
			final View child = getChildAt(index);
			child.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int w = r - l, h = b - t, count = 0;
		for (int i = 0; i < this.getChildCount(); i++) {
			View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				count++;
			}
		}
		int in = 0;
		int skip = 0;
		for (int i = 0; i < count; i++) {
			View child = this.getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				int left = 0, top = 0;
				switch (move_direction) {
				case 0: // left
					left = in * w;
					child.layout(left, -skip, left + w, h);
					break;
				case 4: // right
					left = -in * w;
					child.layout(left, -skip, left + w, h);
					break;
				case 1: // down
					top = -in * h;
					child.layout(0, top - skip, w, top + h);
					break;
				case 3: // up
					top = in * h;
					child.layout(0, top - skip, w, top + h);
					break;
				}
				in++;
			}
		}
	}

	public void adView(View view, int ncurrentId, int currentid,
			Object currentObject, int move_direction) {
		if (thread != null) {
			thread.interrupt();
		}
		this.move_direction = move_direction;
		boolean bol = this.getChildCount() == 0;
		this.removeView(view);
		this.addView(view);
		if (bol) {
			if (onMoveListener != null) {
				onMoveListener.moveNow(movespeed, movespeed, move_direction,
						ncurrentId, currentid, currentObject);
			}
		} else {
			thread = new autoMove();
			thread.start();
		}
		this.currentObject = currentObject;
		this.currentId = currentid;
		this.ncurrentId = ncurrentId;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (this.onTouchListener != null) {
			this.onTouchListener.onTouch(this, ev);
		}
		return super.dispatchTouchEvent(ev);
	}

	private class autoMove extends Thread {
		public void run() {
			int stepx = 0, stepy = 0;
			switch (move_direction) {
			case 0:
				stepx = GroupLayout.this.getWidth() / movespeed;
				break;
			case 4:
				stepx = -GroupLayout.this.getWidth() / movespeed;
				break;
			case 1:
				stepy = -GroupLayout.this.getHeight() / movespeed;
				break;
			case 3:
				stepy = GroupLayout.this.getHeight() / movespeed;
				break;
			}
			for (int i = 0; i < movespeed; i++) {
				nmovex += stepx;
				nmovey += stepy;
				hand.sendMessage(hand.obtainMessage(0, nmovex, nmovey));
				if (onMoveListener != null) {
					onMoveListener.moveNow(i, movespeed, move_direction,
							ncurrentId, currentId, currentObject);
				}
				try {
					sleep(20);
				} catch (InterruptedException e) {
					return;
				}
			}
			nmovex = 0;
			nmovey = 0;
			if (onMoveListener != null) {
				onMoveListener.moveNow(movespeed, movespeed, move_direction,
						ncurrentId, currentId, currentObject);
			}
			hand.sendMessage(hand.obtainMessage(1, nmovex, nmovey));
			thread = null;
		}
	}

	public int getMovespeed() {
		return movespeed;
	}

	public void setMovespeed(int movespeed) {
		this.movespeed = movespeed;
	}

	public void setCurrentView(OnCurrentViewMoveListener currentView) {
		this.onMoveListener = currentView;
	}

	public void setOnTouchListener(OnTouchListener onTouchListener) {
		this.onTouchListener = onTouchListener;
	}

}
