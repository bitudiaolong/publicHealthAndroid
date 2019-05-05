package com.gc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpUtil {

    public static final String RegexIsMobileNO = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";

    public static final String RegexIsMobileNONew = "^((13[0-9])|(17[0-9])|(15[^4,\\\\D])|(18[0-9]))\\\\d{8}$";

    public static final String RegexIsEmail = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    public static final String RegexFormatPwd = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{4,10}$";

    public static String getuser(Context mContext, String string) {
        SharedPreferences preferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        return preferences.getString(string, "空");
    }

    public static DisplayMetrics getW(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static void savesp(Context context, String UserPwd, String ThumbnailHeadPic, String nickname, String signid, String uid, String useraccount, int isvalidated, String schoolCode) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        int IsValidated = isvalidated;
        editor.putString("ThumbnailHeadPic", ThumbnailHeadPic);
        editor.putString("NickName", nickname);
        editor.putString("SignId", signid);
        editor.putString("UID", uid);
        editor.putString("UserAccount", useraccount);
        editor.putString("UserPwd", UserPwd);
        editor.putInt("IsValidated", IsValidated);
        editor.putString("LoginState", "LoginIn");
        editor.putString("SchoolCode", schoolCode);
        editor.commit();

        // 存本地数据库
        // userReplace_Nick_Head_isvaldate( context, uid,ThumbnailHeadPic, nickname ,IsValidated+"");
    }

    public static void clearSharedPerfrence(Context mContext, String spName) {
        SharedPreferences preferences = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 验证是否是手机号
     *
     * @param mobileNO
     * @return
     */
    public static boolean isMobileNO(String mobileNO) {
        Pattern p = Pattern.compile(RegexIsMobileNONew);
        // ^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$
        // ^((13[0-9])|(15[^4,//D])|(18[0,5-9]))\\d{8}$
        Matcher m = p.matcher(mobileNO);
        return m.matches();
    }

    /**
     * 验证密码
     *
     * @return
     */
    public static boolean isPwd(String Pwd) {
        Pattern p = Pattern.compile(RegexFormatPwd);
        // ^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$
        // ^((13[0-9])|(15[^4,//D])|(18[0,5-9]))\\d{8}$
        Matcher m = p.matcher(Pwd);
        return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile(RegexIsEmail);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @param pixels 圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 转换图片成圆形        
     *
     * @param bitmap 传入Bitmap对象    
     * @return          
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 图片        
     *
     * @param bitmap 传入Bitmap对象    
     * @return          
     */
    public static Bitmap toBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = 0;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = 0;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 根据字符串获取二维码
     *
     * @return
     */

    public static float getMax(float[] d) {
        float max;
        for (int i = 0; i < d.length - 1; i++) { // 最多做n-1趟排序
            for (int j = 0; j < d.length - i - 1; j++) { // 对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                if (d[j] < d[j + 1]) { // 把小的值交换到后面
                    float t = d[j];
                    d[j] = d[j + 1];
                    d[j + 1] = t;
                }
            }
        }
        max = d[0];
        return max;
    }

    /**
     * 获取SharedPreference中的单个数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getSharedPreferences(Context context, String shName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shName, 0);
        String value = sharedPreferences.getString(key, "");
        return value;

    }

    /**
     * 保存sp文件数据boolean型
     *
     * @param context
     * @param shName
     * @param key
     * @param value
     */
    public static void setBooleanSharedPreferences(Context context, String shName, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shName, 0);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存sp文件数据string型
     *
     * @param context
     * @param shName
     * @param key
     * @param value
     */
    public static void setStringSharedPreferences(Context context, String shName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shName, 0);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存sp文件数据int型
     *
     * @param context
     * @param shName
     * @param key
     * @param value
     */
    public static void setIntSharedPreferences(Context context, String shName, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shName, 0);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取sp文件中的boolean型数据
     *
     * @param context
     * @param shName
     * @param key
     * @return
     */
    public static boolean getBooleanSharedPreferences(Context context, String shName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shName, 0);
        boolean value = sharedPreferences.getBoolean(key, true);
        return value;
    }

    /**
     * 获取sp文件中的int型数据
     *
     * @param context
     * @param shName
     * @param key
     * @return
     */
    public static int getIntSharedPreferences(Context context, String shName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(shName, 0);
        int value = sharedPreferences.getInt(key, -1);
        return value;
    }

    /*-----------取出http返回数据中的有用信息--------------*/
    public static String dataFormat(String body) {
        String str1 = "<string xmlns=\"http://tempuri.org/\">";
        String str2 = "</string>";
        String str3 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        if (body.contains(str1) && body.contains(str2) && body.contains(str3)) {
            return body.replace(str1, "").replace(str2, "").replace(str3, "").trim();
        } else {
            return body;
        }
    }

    public static String dataFormat_datalist(String body) {
        String str1 = "[";
        String str2 = "]";
        Boolean test1 = body.contains(str1);
        Boolean test2 = body.contains(str2);

        // if (body.contains(str1) && body.contains(str2) ) {
        String test3 = body.replace(str1, "").replace(str2, "").trim();
        return body.replace(str1, "").replace(str2, "").trim();
        // } else {
        // return null;
        // }
    }

    /**
     * MIUI专用
     *
     * @author zhurui
     * @time 2018/7/20 上午11:44
     */
    public static void showToast(Context context, String msg) {
        Toast mToast = new Toast(context);
        mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.show();
    }

    // public static void showToast(String showText) {
    // Toast.makeText(MyApplication.currentConext, showText,
    // Toast.LENGTH_SHORT).show();
    // }

    /**
     * 把应用对应的信息放到Map中
     *
     * @param mActivity
     * @return
     */
    public Map<String, ResolveInfo> getShareList(Activity mActivity) {
        final Map<String, ResolveInfo> appInfo = new HashMap<String, ResolveInfo>();
        List<ResolveInfo> appList = getShareTargets(mActivity);
        if (appList.size() > 0) {
            for (int i = 0; i < appList.size(); i++) {
                ResolveInfo tmp_ri = appList.get(i);
                ApplicationInfo apinfo = tmp_ri.activityInfo.applicationInfo;
                String tmp_appName = apinfo.loadLabel(mActivity.getPackageManager()).toString();
                System.out.println("tmp_appName=" + tmp_appName + ";tmp_ri=" + tmp_ri);
                if (tmp_appName.equals("微博")) {
                    appInfo.put(tmp_appName, tmp_ri);
                }
                if (tmp_appName.equals("微信")) {
                    appInfo.put(tmp_appName, tmp_ri);
                }
            }

        }
        return appInfo;
    }

    /**
     * 获取手机中所有可以分享的应用列表
     *
     * @param activity
     * @return
     */
    public static List<ResolveInfo> getShareTargets(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pm = activity.getPackageManager();
        return pm.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

    public static boolean isContainApp(Activity context, String packName) {
        List<ResolveInfo> list = getShareTargets(context);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo resolveInfo = list.get(i);
            ApplicationInfo apinfo = resolveInfo.activityInfo.applicationInfo;
            String pack = apinfo.packageName;
            System.out.println("pack=" + pack);
            if (packName.equals(pack)) {
                return true;
            }
        }

        return false;
    }

    public static void startApp(Context context, String packageName) {
        Intent intent = new Intent();
        PackageManager packageManager = context.getPackageManager();
        intent = packageManager.getLaunchIntentForPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }

    // public static File compress(Bitmap bitmap, String imageName) {
    // File file = new File(MyApplication.getTalkImagePath() + "/" + imageName);
    // try {
    // if (!file.exists()) {
    // file.createNewFile();
    // }
    // ByteArrayOutputStream bos = new ByteArrayOutputStream();
    // bitmap.compress(CompressFormat.JPEG, 85, bos);
    // byte[] bitmapdata = bos.toByteArray();
    // FileOutputStream fos = new FileOutputStream(file);
    // fos.write(bitmapdata);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return file;
    // }

    public static File compressPath(Bitmap bitmap, String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 85, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int be = 1;
        if (new File(filePath).length() > 500 * 1024) {
            // Calculate inSampleSize
            be = (int) (options.outHeight / (float) 200);
            if (be <= 0) {
                be = 1;
            }
        }

        options.inSampleSize = be;

        options.inJustDecodeBounds = false;

        // return BitmapFactory.decodeFile(filePath, options);
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取sd卡父文件路劲
     *
     * @return
     */
    public static String getSDPath() {
        String path = "";
        String sdPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 为true的话，sd卡存在
            sdPath = Environment.getExternalStorageDirectory().getPath();
            File file = new File(sdPath);
            path = file.getParentFile().getPath();
            Log.i("4", "path:" + path);
        } else {
            path = "/";
        }
        return path;
    }

    public static double getTimeValue(String time)
            throws Exception {
        double d = 0;
        String[] s = time.split(":");
        if (!s[1].startsWith("0")) {
            d = Double.parseDouble(s[0]) + Double.parseDouble(s[1]) / 60;
        } else {
            if (s[1].equals("00")) {
                d = Double.parseDouble(s[0]);
            } else {
                d = Double.parseDouble(s[0]) + Double.parseDouble(s[1].substring(1, 2)) / 60;
            }
        }

        return d;
    }

    /**
     * 获取List<double[]>中最大值
     *
     * @return
     */
    public static double getDMax(List<double[]> dataList) {
        double max = Double.MIN_VALUE;
        for (double[] ds : dataList) {
            for (double d : ds) {
                if (d > max) {
                    max = d;
                }
            }
        }
        return max;
    }

    /**
     * 获取List<double[]>数组中最小值
     *
     * @return
     */
    public static double getDMin(List<double[]> dataList) {
        double min = Double.MAX_VALUE;
        for (double[] ds : dataList) {
            for (double d : ds) {
                if (d < min) {
                    min = d;
                }
            }
        }
        return min;
    }

    /**
     * 获取当前日期
     *
     * @return
     * @throws Exception
     */

    public static String getDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 获取当前时间
     *
     * @return
     */

    public static String getTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String time = "";
        if (minute < 10) {
            time = hour + ":" + "0" + minute;
        } else {
            time = hour + ":" + minute;
        }
        return time;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    // 日期增加
    public static Date addDateOneDay(Date date, int n) {
        if (null == date) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date); // 设置当前日期
        c.add(Calendar.DATE, n); // 日期加1天
        date = c.getTime();
        return date;
    }

    public static String format(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins);

        return sbBuffer.toString();
    }

    // /**
    // * 1）几秒前 几分钟前 14:00 昨天 5月12日
    // *
    // * @param startDate
    // * 发布时间
    // * @param endDate
    // * 当前时间
    // * @return
    // */
    // public static String twoDatehuihua(Date startDate, Date endDate)
    // {
    //
    // if (startDate == null || endDate == null)
    // {
    // return null;
    // }
    //
    // boolean isNowDate = TimeUtil.isSameDayOfMillis(startDate.getTime(), endDate.getTime());
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // long timeLong = endDate.getTime() - startDate.getTime();
    // if (isNowDate)// 与当前时间同一天
    // {
    //
    // if (timeLong < 60 * 1000)
    // return timeLong / 1000 + "秒前";
    // else if (timeLong < 60 * 60 * 1000)
    // {
    // timeLong = timeLong / 1000 / 60;
    // return timeLong + "分钟前";
    // }
    // else if (timeLong < 60 * 60 * 24 * 1000)
    // {
    // // 显示会话 时分
    // sdf = new SimpleDateFormat("HH:mm");
    // return sdf.format(startDate);
    // }
    // }
    // if (timeLong < 60 * 60 * 24 * 1000 * 2)
    // {
    // return "昨天";
    // }
    // else
    // {
    // sdf = new SimpleDateFormat("yyyy-MM-dd");
    // return sdf.format(startDate);
    // }
    // }

    /*	*/

    /**
     * 今天 显示 10分钟 前 1小时前
     * 昨天
     * 2天前
     *
     * @return
     */
    /*
     * public static String twoDateSubscript(Date startDate, Date endDate) {
     *
     * if (startDate == null || endDate == null) {
     * return null;
     * }
     *
     * boolean isNowDate = TimeUtil.isSameDayOfMillis(startDate.getTime(),
     * endDate.getTime());
     * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * long timeLong = endDate.getTime() - startDate.getTime();
     * if (isNowDate)// 与当前时间同一天
     * {
     *
     * if (timeLong < 60 * 1000)
     * return timeLong / 1000 + "秒前";
     * else if (timeLong < 60 * 60 * 1000) {
     * timeLong = timeLong / 1000 / 60;
     * return timeLong + "分钟前";
     * } else if (timeLong < 60 * 60 * 24 * 1000) {
     * // 显示会话 时分
     * //sdf = new SimpleDateFormat("HH:mm");
     * //return sdf.format(startDate);
     * return timeLong/1000/60/60 +"小时前";
     * }
     * }
     * sdf = new SimpleDateFormat("yyyy-MM-dd");
     * return sdf.format(startDate);
     * }
     */
    public static String formatDisplayTime(String time, Date today, String pattern) {
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;

        if (time != null) {
            try {
                Date tDate = new SimpleDateFormat(pattern).parse(time);
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy");
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd");
                Date thisYear = new Date(thisYearDf.parse(thisYearDf.format(today)).getTime());
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime());
                Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
                    SimpleDateFormat halfDf = new SimpleDateFormat("MM月dd日");
                    long dTime = today.getTime() - tDate.getTime();
                    if (tDate.before(thisYear)) {
                        display = new SimpleDateFormat("yyyy/MM/dd").format(tDate) + " " + new SimpleDateFormat("HH:mm").format(tDate);
                    } else {

                        if (dTime < tMin) {
                            display = "刚刚";
                        } else if (dTime < tHour) {
                            display = (int) Math.ceil(dTime / tMin) + "分钟前";
                        } else if (dTime < tDay && tDate.after(yesterday)) {
                            display = (int) Math.ceil(dTime / tHour) + "小时前";
                        } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                            display = "昨天" + new SimpleDateFormat("HH:mm").format(tDate);
                        } else {
                            // display = halfDf.format(tDate);

                            display = new SimpleDateFormat("yyyy/MM/dd").format(tDate) + " " + new SimpleDateFormat("HH:mm").format(tDate);

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return display;
    }

    /**
     * 本地图片随机
     */
//    public static Integer getAvater() {
//        Integer[] HeadPics = {

//                R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3, R.drawable.avatar_4, R.drawable.avatar_5, R.drawable.avatar_6};
//        Random random = new Random();
//        int flagNum = random.nextInt(6);
    // System.out.println(flagNum);
//        return HeadPics[flagNum];

//    }
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return 文件后缀名
     */
    public static String getFileType(String fileName) {
        if (fileName != null) {
            int typeIndex = fileName.lastIndexOf(".");
            if (typeIndex != -1) {
                String fileType = fileName.substring(typeIndex + 1).toLowerCase();
                return fileType;
            }
        }
        return "";
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @param type
     * @return 是否是图片结果true or false
     */
    public static boolean isImage(String type) {
        if (type != null
                && (type.equals("jpg") || type.equals("gif") || type.equals("png") || type.equals("jpeg") || type.equals("bmp") || type.equals("wbmp") || type.equals("ico") || type.equals("jpe"))) {
            return true;
        }
        return false;
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @return 是否是图片结果true or false
     */
    public static boolean isImageType(String fileName) {
        String type = getFileType(fileName);
        if (type != null
                && (type.equals("jpg") || type.equals("gif") || type.equals("png") || type.equals("jpeg") || type.equals("bmp") || type.equals("wbmp") || type.equals("ico") || type.equals("jpe"))) {
            return true;
        }
        return false;
    }

    public static String format_chinese_date(String date) {
        String chineseDate = "";
        switch (Integer.parseInt(date)) {
            case 1:
                chineseDate = "一";
                break;
            case 2:
                chineseDate = "二";
                break;
            case 3:
                chineseDate = "三";
                break;
            case 4:
                chineseDate = "四";
                break;
            case 5:
                chineseDate = "五";
                break;
            case 6:
                chineseDate = "六";
                break;
            case 7:
                chineseDate = "七";
                break;
            case 8:
                chineseDate = "八";
                break;
            case 9:
                chineseDate = "九";
                break;
            case 10:
                chineseDate = "十";
                break;
            case 11:
                chineseDate = "十一";
                break;
            case 12:
                chineseDate = "十二";
                break;

            default:
                break;
        }
        return chineseDate;
    }

    public static String getCurrentVersion(Context context) {
        String versionStr = "1.0";
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            versionStr = info.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionStr;

    }

    /*----------判断网络状态---------------*/
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();

        return imsi;
    }

    private void getPhoneInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        String imsi = telephonyManager.getSubscriberId();
        String mtype = android.os.Build.MODEL;
    }

    public static Map<String, String> getBaseMapList() {
        Map<String, String> mapList = new HashMap<>();
        mapList.put("clientTime", String.valueOf(System.currentTimeMillis()));
        mapList.put("deviceType", "android");
        mapList.put("cityLcode", "110100");
        mapList.put("version", "1.0.0");
        mapList.put("deviceId", "12345678889");

        return mapList;
    }

    //list数组转string
    public static String join(String join, String[] strAry) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strAry.length; i++) {
            if (i == (strAry.length - 1)) {
                sb.append(strAry[i]);
            } else {
                sb.append(strAry[i]).append(join);
            }
        }

        return new String(sb);
    }

    //时间转换成分钟
    public static int getdatatime_allminutes(String xdata) {
        int xdata_hour = Integer.parseInt(xdata.split(":")[0]) * 60;
        int xdata_min = Integer.parseInt(xdata.split(":")[1]);
        int xdata_all = xdata_hour + xdata_min;
        return xdata_all;
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> test(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }

    //获取屏幕宽高
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    public static File cutScreen(Activity activity, String path) {
        try {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();
            // 获取状态栏高度
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            // 获取屏幕长和高
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            int height = activity.getWindowManager().getDefaultDisplay()
                    .getHeight();
            Bitmap b = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height
                    - statusBarHeight);
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }

            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            b.compress(Bitmap.CompressFormat.PNG, 50, fOut);
            fOut.close();
            return f;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
