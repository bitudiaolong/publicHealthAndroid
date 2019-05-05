package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.utils.QRCreate;
import com.gc.basicpublicdoctor.utils.StringUtils;
import com.gc.basicpublicdoctor.view.RoundImageView;
import com.gc.utils.AppUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.xutils.common.util.DensityUtil;

import java.util.Hashtable;

/**
 * Created by Administrator on 2018/9/3.
 */

public class QrCodeShowActivity extends BaseActivity {
    Context context;
    RelativeLayout rl_left;
    ImageView iv;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        context = this;
        rl_left = (RelativeLayout) findViewById(R.id.rl_left);
        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv = (ImageView) findViewById(R.id.iv);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        //生成二维码
        url = Cons.doctorSignQRCode;
        if (!StringUtils.isBank(url)) {
            Bitmap bitmap = QRCreate.getInstance().createQRImage(
                    url,
                    DensityUtil.dip2px(306.0f),
                    DensityUtil.dip2px(306.0f)
            );
            if (bitmap != null) {
                iv.setImageBitmap(bitmap);
            }
        }
//        iv.setImageBitmap(createQRImage(url, length, length));


    }

    public static Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            Log.e("bit", "createQRImage: " + bitmap.toString());
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
