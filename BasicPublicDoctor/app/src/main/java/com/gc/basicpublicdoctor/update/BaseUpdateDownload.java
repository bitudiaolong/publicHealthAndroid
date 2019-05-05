package com.gc.basicpublicdoctor.update;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.gc.basicpublicdoctor.BuildConfig;
import com.gc.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class BaseUpdateDownload {
    private static final String savePath = "/sdcard/update/huilife/";// 保存apk的文件夹
    private static final String saveFileName = savePath + "doctor_update.apk";
    private ProgressDialog progressDialog;
    Context context;


    public BaseUpdateDownload(Context context) {
        this.context = context;
    }

    public void downloadFile(final String url, final String getMd5) {
        Log.e("INFOD", "开始升级");
        final File mFile = new File(saveFileName);
        if (mFile.exists()) {
            mFile.delete();
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(saveFileName);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setMessage("亲，努力下载中。。。");
                progressDialog.show();
                progressDialog.setProgressNumberFormat("%1d kb/%2d kb");
                progressDialog.setMax((int) (total) / 1024);
                progressDialog.setProgress((int) current / 1024);
                LogUtil.e("progressDialog--setMax", "setMax[" + (int) (total) / 1024 + "]");
                LogUtil.e("progressDialog--setProgress", "setProgress[" + (int) current / 1024 + "】");
                LogUtil.e("progressDialog--loading", "TOTAL[" + total + "]   CURRENT[" + current + "]");
            }

            @Override
            public void onSuccess(File result) {
                Log.e("INFOD", "下载成功");
                Log.e("INFOD", " downloadApp onSuccess...filePath=="
                        + saveFileName);
                try {
                    String md5ByFile = md5(mFile);
                    Log.e("INFOD", "本地MD5值是；" + md5ByFile + "]得到的md5[" + getMd5 + "]");

                    if (md5ByFile.equalsIgnoreCase(getMd5)) {
                        Log.e("INFOD", "版本相同");
                        //   showUserValidationDialog();
                        // 不用提示直接安装
                        installUpdateApp();
                    } else {
                        Toast.makeText(context, "安装包校验错误", Toast.LENGTH_SHORT).show();
                        Log.e("INFOD", "版本不一致");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("INFOD", "下载失败ex[" + ex + "]");
                Toast.makeText(context, "下载失败:" + ex, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    // 用户确认
    public void showUserValidationDialog() {
        Log.e("INFOD", "用户确认");

//		DialogConfirm dialogConfirm = new DialogConfirm(this, "有重要功能更新，请点击确定进行升级！", 0,
//				this, "确定", "取消");
//		dialogConfirm.show();
        AlertDialog d = new AlertDialog.Builder(context).setTitle("新版本更新提示").setMessage("有重要功能更新，请点击确定进行升级！")

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        installUpdateApp();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }

                }).create();

//        d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        d.show();

    }

    // 安装程序
    public void installUpdateApp() {
        Log.e("INFOD", "saveFileName-----" + saveFileName);
        File apkfile = new File(saveFileName);
        Log.e("INFOD", "是否是空 的 ---" + !apkfile.exists());
        if (!apkfile.exists()) {
            return;
        }
        Log.e("INFOD", "打开安装页面");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri fileUri = Uri.fromFile(apkfile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".FileProvider", apkfile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            Log.e("INFOD", "VERSION_CODES--777777");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            Log.e("INFOD", "VERSION_CODES");
        }
        context.getApplicationContext().startActivity(intent);
        Log.e("INFOD", "getApplicationContext");
    }

    /**
     * 检测MD5值
     *
     * @return
     * @throws Exception
     */
    private boolean Md5Match() throws Exception {

        return true;
    }

    private String getMd5ByFile() throws FileNotFoundException {
        String value = null;
        File file = new File(saveFileName);
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
//			BigInteger bi = new BigInteger(1, md5.digest());
//			value = bi.toString(16);
            value = bytesToHexString(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static String md5(File file) {
        String result = "";
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            byte[] bytes = md5.digest();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
