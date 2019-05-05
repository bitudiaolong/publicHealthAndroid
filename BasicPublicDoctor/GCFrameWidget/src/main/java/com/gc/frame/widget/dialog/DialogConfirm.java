package com.gc.frame.widget.dialog;

import java.util.LinkedHashMap;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gc.frame.widget.R;

public class DialogConfirm extends MDialog {

	private static final int PX_DIALOG_WINDOW_HEIGHT = 285;
	private static final int PX_DIALOG_WINDOW_WIDTH = 570;
	private static final int NOT_GRAVITY = -1;
	private static final int DEFAULT_SIZE = 20;
	protected int idLayout;
	protected int idButtonLeft;
	protected int idButtonMiddle;
	protected int idButtonRight;
	protected int idMessage;
	protected LinkedHashMap<Integer, String> items;

	protected View view;
	protected String message;
	private String textButtonLeft;
	private String textButtonRight;
	protected String title;
	private int gravity;
	private int messageSize;

	/**
	 * 
	 * @param context
	 * @param calledByViewId
	 * @param idLayout
	 * @param idButtonRight
	 * @param items
	 *            ep:items.put(R.id.textViewtitle,"确认")
	 */
	public DialogConfirm(Context context, int calledByViewId, int idLayout,
			int idMessage, int idButtonRight,
			LinkedHashMap<Integer, String> items) {
		this(context, calledByViewId, idLayout, idMessage, 0, idButtonRight,
				items);
	}

	/**
	 * 
	 * @param context
	 * @param calledByViewId
	 * @param idLayout
	 * @param idButtonLeft
	 * @param idButtonRight
	 * @param items
	 *            ep:items.put(R.id.textViewtitle,"确认")
	 */
	public DialogConfirm(Context context, int calledByViewId, int idLayout,
			int idMessage, int idButtonLeft, int idButtonRight,
			LinkedHashMap<Integer, String> items) {
		super(context);
		this.calledByViewId = calledByViewId;
		this.idLayout = idLayout;
		this.idButtonLeft = idButtonLeft;
		this.idButtonRight = idButtonRight;
		this.idMessage = idMessage;
		this.items = items;
		gravity = NOT_GRAVITY;
		messageSize = DEFAULT_SIZE;
	}

	public DialogConfirm(Context context, int calledByViewId, String title,
			String message, String textButtonLeft, String textButtonRight) {
		super(context);
		setPxDialogHeight(PX_DIALOG_WINDOW_HEIGHT);
		setPxDialogWidth(PX_DIALOG_WINDOW_WIDTH);
		this.message = message;

		this.textButtonLeft = textButtonLeft;
		this.textButtonRight = textButtonRight;
		this.calledByViewId = calledByViewId;
		gravity = NOT_GRAVITY;
		messageSize = DEFAULT_SIZE;
	}

	/**
	 * 设置Message显示的位置
	 * 
	 * @param gravity
	 *            Gravity.CENTER、Gravity.RIGHT、Gravity.LEFT、Gravity.TOP
	 */
	public void setMessageGravity(int gravity) {
		if (idLayout == 0) {
			this.gravity = gravity;
			if (this.isShowing() == true) {
				setGravity();
			}
		}
	}

	/**
	 * 设定Message的文字大小
	 * 
	 * @param size
	 */
	public void setMessageSize(int size) {
		if (idLayout == 0) {
			this.messageSize = size;
			if (this.isShowing() == true) {
				setTextSize(idMessage, messageSize);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (idLayout == 0) {
			setContentView(R.layout.dialog_confirm);
			initialize();
		} else {
			setContentView(idLayout);
			initializeCustomize();
		}
	}

	private void initializeCustomize() {
		view = getWindow().getDecorView();
		for (int id : items.keySet()) {
			if (0 < id) {
				TextView textView = (TextView) view.findViewById(id);
				textView.setText(items.get(id));
				if (id == idButtonLeft
						|| id == idButtonRight) {
					textView.setOnClickListener(this);
				}
			}
		}
	}

	public void initialize() {
		view = getWindow().getDecorView();

		TextView textViewMessageContent = (TextView) view
				.findViewById(R.id.tv_msg);
		textViewMessageContent.setText(message);

		Button btn_left = (Button) view.findViewById(R.id.btn_left);
		Button btn_right = (Button) view.findViewById(R.id.btn_right);


			btn_left.setOnClickListener(this);
			btn_left.setText(textButtonLeft);

		btn_right.setOnClickListener(this);
		btn_right.setText(textButtonRight);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		int which = DialogButton.LEFT;
		if (id == R.id.btn_left || id == idButtonLeft) {
			which = DialogButton.LEFT;
		} else if (id == R.id.btn_right || id == idButtonRight) {
			which = DialogButton.RIGHT;
		}

		onResultClick(which);
	}

	private void setGravity() {
		if (gravity != NOT_GRAVITY) {
			TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
			tv_msg.setGravity(gravity);
		}
	}

	private void setTextSize(int id, int size) {
		if (0 < size) {
			TextView textView = (TextView) view.findViewById(id);
			textView.setTextSize(size);
		}

	}

	@Override
	public void show() {
		super.show();
		if (idLayout == 0) {
			setGravity();
			setTextSize(idMessage, messageSize);
		}
	}

}
