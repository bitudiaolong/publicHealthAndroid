package com.gc.frame;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.gc.frame.manage.Handles;

/**
* @ClassName:	Frame 
* @Description:	TODO
* @author		yu
* @version		
* @date		[2014�?12�?23�? 下午5:02:18] 
*/
public class Frame {
    public static Context CONTEXT;
    
    /**@Fields HANDLES : 控制�? */
    public static Handles HANDLES = new Handles();
    
    /**@Fields AUTOADDPARMS : 网络请求默认参数 */
    //    public static String[][] AUTOADDPARMS = null;
    
    /**@Fields AUTOPARMS : 默认传参 */
    public static HashMap<String, String> AUTOPARMS = new HashMap<String, String>();
    
    /**@Fields MAP : 百度地图1.3.5�? *//*
    @Deprecated
    public static MMap MAP;*/
    
    private static boolean Frame_inited = false;
    
    /**@Fields OnlyWifiLoadImage : 是否仅在WIFI状�?�下加载图片  默认是false*/
    public static boolean OnlyWifiLoadImage = false;
    
    /**@Fields ConnectionTimeOut : 请求超时时间 */
    public static int ConnectionTimeOut = 5000;
    
    /**@Fields SoTimeOut : 响应超时时间 */
    public static int SoTimeOut = 5000;
    
    /**@Fields maxCacheSize : �?大缓存空�?,默认50M */
    public static int maxCacheSize = 50 * 1024 * 1024;
    
    /**@Fields app_executor : 网络请求线程�? */
    private static Executor app_executor;
    
    /**@Fields UrlImageLoaderConfiguration : 初始化图片加载器配置 */
//    public static ImageLoaderConfiguration UrlImageLoaderConfiguration;
    
    /** 
     * ToDo：初始化框架
     * @author M2_
     * @param context 
     * @return void 
     * @throws 
     */
    public static void init(Context context) {
        if (!Frame_inited) {
            CONTEXT = context.getApplicationContext();
            reInit(context);
        }
        
    }
    
    /** 
     * ToDo：获取默认传�?,若AUTOPARMS为空则从SharedPreferences获取
     * @author M2_
     * @return 
     * @return Map<String,String> 
     * @throws 
     */
    public static HashMap<String, String> getAutoAddParms() {
        if (!isEmptyAutoParms())
            return AUTOPARMS;
        HashMap<String, String> autoaddparms = new HashMap<String, String>();
        SharedPreferences preferences = CONTEXT.getSharedPreferences("AutoAddParms", Context.MODE_PRIVATE);
        Map<String, ?> all2 = preferences.getAll();
        if (null == all2 || all2.isEmpty()) {
        }
        else {
            try {
                Map<String, String> all = (Map<String, String>) all2;
                for (Map.Entry<String, String> entry : all.entrySet()) {
                    autoaddparms.put(entry.getKey(), entry.getValue());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return autoaddparms;
    }
    
    /** 
     * ToDo：当前默认传参是否为�?
     * @author M2_
     * @return 
     * @return boolean 
     * @throws 
     */
    public static boolean isEmptyAutoParms() {
        return null == AUTOPARMS || AUTOPARMS.isEmpty();
    }
    
    /** 
     * ToDo：清除默认传�?
     * @author M2_ 
     * @return void 
     * @throws 
     */
    public static void clearAutoAddParms() {
        SharedPreferences preferences = CONTEXT.getSharedPreferences("AutoAddParms", Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.clear();
        edit.commit();
    }
    
    /** 
     * ToDo：设置默�?
     * @author M2_
     * @param autoaddparms null或empty 不做任何设置 ,否则保存到SharedPreferences
     * @return void 
     * @throws 
     */
    public static void setAutoAddParms(HashMap<String, String> autoaddparms) {
        if (null == autoaddparms && autoaddparms.isEmpty())
            return;
        clearAutoAddParms();
        SharedPreferences preferences = CONTEXT.getSharedPreferences("AutoAddParms", Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        for (Map.Entry<String, String> entry : autoaddparms.entrySet()) {
            edit.putString(entry.getKey(), entry.getValue());
        }
        AUTOPARMS = autoaddparms;
        edit.commit();
    }
    
    public static void reInit(Context context) {
//        INITCONFIG = new InitConfig(context);
//        if (null == UrlImageLoaderConfiguration) {
//            initImageLoader(context);
//            /*if (INITCONFIG.mMapKey != null && INITCONFIG.mMapKey.length() > 0) {
//                MAP = new MMap(context, null);
//            }*/
//        }
        Frame_inited = true;
    }
    
//    public static void initImageLoader(Context context) {
//        UrlImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .diskCacheSize(maxCacheSize)
//                //否定多种尺寸缓存
//                .denyCacheImageMultipleSizesInMemory()
//                //                .taskExecutor(getApp_executor())  统一网络请求线程�?
//                .tasksProcessingOrder(QueueProcessingType.FIFO)
//                .diskCache(new UnlimitedDiscCache(getDPath(context, "image")))
//                //加载线程先进先出
//                //                .writeDebugLogs()
//                //弱引�?
//                .memoryCache(new WeakMemoryCache())
//                .imageDownloader(new BaseImageDownloader(context, ConnectionTimeOut, SoTimeOut))
//                // Remove for release app
//                .build();
//        ImageLoader.getInstance().init(UrlImageLoaderConfiguration);
//    }
    
    /** 
     * ToDo：获取下载路�?
     * @author M2_
     * @param context
     * @param fileType
     * @return 
     * @return File 
     * @throws 
     */
//    public static File getDPath(Context context, String fileType) {
//        File file = null;
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            file = new File(Environment.getExternalStorageDirectory(), Frame.INITCONFIG.getTempPath() + File.separator
//                    + fileType);
//        }
//        else {
//            File fparent = context.getDir("frame", Context.MODE_WORLD_WRITEABLE);
//            if (!fparent.exists()) {
//                fparent.mkdir();
//            }
//            file = new File(fparent.getPath() + fileType);
//        }
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        return file;
//    }
    
    /** 
     * ToDo：获得线程池
     * @author M2_
     * @return 
     * @return Executor 
     * @throws 
     */
    public Executor getApp_executor() {
        return app_executor;
    }
    
    /** 
     * ToDo：自定义线程�?
     * @author M2_
     * @param app_executor 
     * @return void 
     * @throws 
     */
    public void setApp_executor(Executor app_executor) {
        this.app_executor = app_executor;
    }
    
    public int getMaxCacheSize() {
        return maxCacheSize;
    }
    
    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }
    
    /**
     * 关闭应用
     * @author ljl
     * @Description: TODO
     * @param  
     * @return void 
     * @throws
     */
    public static void destory() {
        HANDLES.closeAll();
    }
    
    public static int getConnectionTimeout() {
        return ConnectionTimeOut;
    }
    
    public static void setConnectionTimeout(int connectionTimeout) {
        ConnectionTimeOut = connectionTimeout;
    }
    
    public static int getSoTimeout() {
        return SoTimeOut;
    }
    
    public static void setSoTimeout(int soTimeout) {
        SoTimeOut = soTimeout;
    }
    
//    public static List<MContact> getContacts(Context context) {
//        return getContacts(context, null);
//    }
//    
//    public static List<MContact> getContacts(Context context, MContacts.OnContactAddListener onadd) {
//        MContacts conts = new MContacts();
//        return conts.getContact(context, onadd);
//    }
//    
//    public static Drawable getContantPhoto(Context context, MContact contact) {
//        MContacts conts = new MContacts();
//        return conts.getPhoto(context, contact);
//    }
    
    /** 
     * ToDo�? 补全get请求，URLEncoder转义参数
     * @author M2_
     * @param url
     * @param params
     * @return 
     * @return String 
     * @throws 
     */
    public static String getFullUrl(String url, String[][] params) {
        if (null == url)
            return "";
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (params != null && params.length > 0) {
            if (url.indexOf("?") < 0) {
                sb.append("?");
            }
            else {
                sb.append("&");
            }
            for (int i = 0; i < params.length; i++) {
                String[] param = params[i];
                if (param.length > 1) {
                    sb.append(URLEncoder.encode(param[0]) + "=" + URLEncoder.encode(param[1]));
                }
                if (i < params.length - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }
    
}
