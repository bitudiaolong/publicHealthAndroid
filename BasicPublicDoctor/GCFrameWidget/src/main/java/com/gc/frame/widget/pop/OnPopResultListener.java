package com.gc.frame.widget.pop;


public interface OnPopResultListener {

	/**
	 * Callback method to be invoked when popupwindow dismissed.
	 * 
	 * @param calledByID
	 * @param value
	 */
	void onPopResult(int calledByID, Object value);

}
