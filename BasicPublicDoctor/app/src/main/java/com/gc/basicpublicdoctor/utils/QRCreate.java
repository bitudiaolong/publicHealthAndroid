package com.gc.basicpublicdoctor.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Hashtable;

/**
 * Created by WUQING on 2017/9/7.
 */

public class QRCreate {

    public static QRCreate qrCreate;

    private QRCreate(){}
    public static QRCreate getInstance(){
        if (qrCreate == null){
            qrCreate = new QRCreate();
        }
        return qrCreate;
    }

    public Bitmap createBitMap(String contents,int width,int height){
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            result = multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE,
                    width,height);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
            Log.e("baseQR", "createBitMap: WriterException "+e);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            Log.e("baseQR", "createBitMap: IllegalArgumentException "+e);
        }
        return bitmap;
    }

    public Bitmap createQRImage(String url,int QR_WIDTH,int QR_HEIGHT){
        return createQRImage(url, QR_WIDTH, QR_HEIGHT,10);
    }

    //黑白色二维码
    public Bitmap createQRImage(String url, int QR_WIDTH, int QR_HEIGHT,int paddingMinSize) {
        try {
            // 判断URL合法�?
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];

            boolean isFirstBlackPoint = false;
            int startX = 0;
            int startY = 0;

            // 下面这里按照二维码的算法，
            // 两个for循环是图片横列扫描的结果
//			int[] rgb = setGradientColor("#03a9f4");
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        if (isFirstBlackPoint == false){
                            isFirstBlackPoint = true;
                            startX = x;
                            startY = y;
                        }
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            if (startX <= paddingMinSize){
                return bitmap;
            }

            int x1 = startX - paddingMinSize;
            int y1 = startY - paddingMinSize;
            if (x1 < 0 || y1 < 0){
                return bitmap;
            }

            int w1 = QR_WIDTH - x1 * 2;
            int h1 = QR_HEIGHT - y1 * 2;
            Bitmap bitmapQR = Bitmap.createBitmap(bitmap,x1,y1,w1,h1);

            return bitmapQR;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 要转换的地址或字符串,可以是中文
    public Bitmap createQRColorImage(String url, int QR_WIDTH, int QR_HEIGHT,int paddingMinSize) {
        try {
            // 判断URL合法�?
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];

            boolean isFirstBlackPoint = false;
            int startX = 0;
            int startY = 0;

            // 下面这里按照二维码的算法，
            // 两个for循环是图片横列扫描的结果
            int[] rgb = setGradientColor("#03a9f4");
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {

                        if (isFirstBlackPoint == false){
                            isFirstBlackPoint = true;
                            startX = x;
                            startY = y;
                        }

                        // 设置二维码颜色渐变
                        int r = (int) (rgb[0] - (rgb[0] - 13.0)
                                / bitMatrix.getHeight() * (y + 1));
                        int g = (int) (rgb[1] - (rgb[1] - 72.0)
                                / bitMatrix.getHeight() * (y + 1));
                        int b = (int) (rgb[2] - (rgb[2] - 107.0)
                                / bitMatrix.getHeight() * (y + 1));
                        Color color = new Color();
                        int colorInt = color.argb(255, r, g, b);
                        pixels[y * QR_WIDTH + x] = bitMatrix.get(x, y) ? colorInt
                                : 16777215;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;// 白色
                    }
//					if (bitMatrix.get(x, y)) {
//						pixels[y * QR_WIDTH + x] = 0xFF0094FF;
//					} else {
//						pixels[y * QR_WIDTH + x] = 0xffffffff;
//					}
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            if (startX <= paddingMinSize){
                return bitmap;
            }

            int x1 = startX - paddingMinSize;
            int y1 = startY - paddingMinSize;
            if (x1 < 0 || y1 < 0){
                return bitmap;
            }

            int w1 = QR_WIDTH - x1 * 2;
            int h1 = QR_HEIGHT - y1 * 2;
            Bitmap bitmapQR = Bitmap.createBitmap(bitmap,x1,y1,w1,h1);

            return bitmapQR;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int[] setGradientColor(String colorStr) {
        int[] rgbs = new int[3];
        rgbs[0] = Integer.parseInt(colorStr.substring(1, 3), 16);
        rgbs[1] = Integer.parseInt(colorStr.substring(3, 5), 16);
        rgbs[2] = Integer.parseInt(colorStr.substring(5, 7), 16);
        return rgbs;
    }

}
