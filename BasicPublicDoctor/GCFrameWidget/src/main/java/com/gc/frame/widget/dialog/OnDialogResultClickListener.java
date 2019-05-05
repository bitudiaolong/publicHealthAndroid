/*
 * Copyright (C) 2011 The CASIO Android Project
 * 
 * 
 */
package com.gc.frame.widget.dialog;

/**
 * this interface should be implemented by Activity who is calling the Dialog
 * Class or the sub class
 * 
 * @author Xu
 * 
 */
public interface OnDialogResultClickListener {

	/**
	 * @param calledByViewId
	 * @param dialog
	 * @param whichButton
	 * <br>
	 *            the button which is clicked;<br>
	 *            {@link DialogButton}.LEFT<br>
	 *            {@link DialogButton}.RIGHT<br>
	 *            {@link DialogButton}.MIDDLE<br>
	 * @param resultValue
	 *            result the inputing value
	 */
	public void onDialogResultClick(int calledByViewId,
			Class<? extends MDialog> dialogClass, int whichButton,
			String resultValue);
}
