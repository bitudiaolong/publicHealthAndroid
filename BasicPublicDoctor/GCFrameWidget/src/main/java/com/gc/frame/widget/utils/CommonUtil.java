package com.gc.frame.widget.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CommonUtil {
	private static DecimalFormat df = new DecimalFormat("######0.00");

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}

	/**
	 * 获取存储卡路径
	 * 
	 * @author 李吉
	 * @Title: getSDPath
	 * @param @return
	 * @return String
	 * @throws
	 * @date [2015年3月20日 下午4:22:29]
	 */
	public static String getSDPath() {
		File sdDir = null;
		String sdpath = "";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			sdpath = sdDir.toString();
		} else {
			sdpath = "";
		}
		return sdpath;

	}

	/**
	 * 获取图片宽度
	 * 
	 * @param context
	 * @param resID
	 *            图片ID
	 * @return
	 */
	public static int getImageWidth(Context context, int resID) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resID);
		return bitmap.getWidth();
	}

	/**
	 * 获取图片高度
	 * 
	 * @param context
	 * @param resID
	 *            图片ID
	 * @return
	 */
	public static int getImageHeight(Context context, int resID) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resID);
		return bitmap.getHeight();
	}

	/**
	 * 获取listview高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {  
            // pre-condition  
            return;  
        }  
  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  

			if (listItem != null) {
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
        }  
  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
        listView.setLayoutParams(params);  
    }
	
	/**
	 * 判断字符串是否是符合合适的字符串
	 * 
	 * @param str_input
	 * @param rDateFormat
	 * @return boolean
	 */
	public static boolean isDate(String str_input, String rDateFormat) {
		if (TextUtils.isEmpty(str_input) == false) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			}
			catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 获得符合格式的时间字符串
	 * 
	 * @param format_date
	 * @param dateTiem
	 * @return String
	 */
	public static String getDataTimeString(String format_date, String dateTiem) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format_date);

		try {
			return dateFormat.format(dateFormat.parse(dateTiem));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String formatZeroLeft(int args) {
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		String str = String.format("%02d", args);
		return str; // 01
	}

	public static String formatPrice(String price) {
		if (TextUtils.isEmpty(price) == false) {
			// return String
			// .valueOf(((int) (Double.parseDouble(price) * 100)) / 100.0);
			return df.format(Double.parseDouble(price));
		} else {
			return "0.00";
		}
	}

	public static void hideKeyboard(Context context) {
		if (((Activity) context).getCurrentFocus() != null
				&& ((Activity) context).getCurrentFocus().getWindowToken() != null) {
			InputMethodManager manager = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(((Activity) context)
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static byte[] getImgData(String picpathcrop) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			// 处理图片宽高
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(picpathcrop, options);
			InputStream is = new FileInputStream(picpathcrop);
			byte[] buff = new byte[1024];
			if (is != null) {
				while (is.read(buff, 0, buff.length) != -1) {
					baos.write(buff, 0, buff.length);
				}
				is.close();
				baos.flush();
				data = baos.toByteArray();
			}
		} catch (IOException e1) {
			Log.e("log", "get img", e1);
		}
		return data;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().densityDpi;
		return (int) (dpValue * (scale / 160) + 0.5f);
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
