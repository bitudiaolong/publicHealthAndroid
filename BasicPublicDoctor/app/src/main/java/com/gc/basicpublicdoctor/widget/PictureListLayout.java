package com.gc.basicpublicdoctor.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gc.basicpublicdoctor.BuildConfig;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.activity.ImageViewActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.utils.DateTimeUtil;
import com.gc.utils.DensityUtils;
import com.gc.utils.PhotoUtils;
import com.gc.utils.UUIDUtil;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PictureListLayout extends LinearLayout implements View.OnClickListener {
    /**
     * 权限申请 拍照
     */
    public static final int PERMISSIOCODE = 102;

    public static final int TAKE_PICTURE = 201;

    public static final int CURT_PICTURE = 202;
    Context context;
    ImageView imageView;
    File fileTemp;
    Uri uriTemp;
    List<String> lstImagePath;
    boolean isShow = false;

    public PictureListLayout(Context context) {
        this(context, null);
    }

    public PictureListLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        lstImagePath = new ArrayList<>();
        initView();
    }

    void initView() {
        imageView = new ImageView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 67), DensityUtils.dp2px(context, 67));
        lp.setMargins(DensityUtils.dp2px(context, 8), DensityUtils.dp2px(context, 9), 0, 0);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(R.drawable.icon_take_picture);
        imageView.setOnClickListener(this);
        this.addView(imageView);
    }


    public void takePictureBack() {
//        File fileCropUri = new File(Environment.getExternalStorageDirectory() + "/headSmall.jpg");
//        PhotoUtils.cropImageUri((Activity) context, uriTemp, Uri.fromFile(fileCropUri), 1, 1, 480, 480, 200);
//        lstImagePath.add(fileTemp.getAbsolutePath());
//        if (lstImagePath.size() == 2) {
//            imageView.setVisibility(GONE);
//        }
//        addView(initPictureItemView(fileTemp.getAbsolutePath()), 0);
//        invalidate();

        Luban.with(context)
                .load(fileTemp)
                .ignoreBy(100)// 忽略不压缩图片的大小
                .setTargetDir(Environment.getExternalStorageDirectory().getAbsolutePath())
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        file.renameTo(new File(fileTemp.getPath()));
                        lstImagePath.add(fileTemp.getAbsolutePath());
                        if (lstImagePath.size() == 2) {
                            imageView.setVisibility(GONE);
                        }
                        addView(initPictureItemView(fileTemp.getAbsolutePath()), 0);
                        invalidate();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }

    View initPictureItemView(final String path) {
        final View view = LayoutInflater.from(context).inflate(R.layout.picture_item, null);
        view.setTag(path);
        final ImageView imageViewShow = (ImageView) view.findViewById(R.id.img_show);
        imageViewShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageViewActivity.class);
                intent.putExtra("path", path);
                if (isShow) {
                    intent.putExtra("isFromNet", true);
                }
                context.startActivity(intent);
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 77), ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(DensityUtils.dp2px(context, 4), 0, 0, 0);
        view.setLayoutParams(lp);
        ImageView imageDel = view.findViewById(R.id.img_del);
        if (isShow) {
            imageDel.setVisibility(GONE);
            Picasso.with(context).load(path)
                    .placeholder(R.drawable.load_loading_gallery)
                    .error(R.drawable.load_fail_gallery)
                    .into(imageViewShow);
        } else {

            Bitmap bm = BitmapFactory.decodeFile(path);
            imageViewShow.setImageBitmap(bm);
//            imageViewShow.setImageURI(uriTemp);
            imageDel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    lstImagePath.remove(path);
                    removeView(view);
                    if (lstImagePath.size() < 2) {
                        imageView.setVisibility(View.VISIBLE);
                    }
//                    invalidate();
                }
            });
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (imageView == imageView) {
            AndPermission.with(context)
                    .requestCode(PERMISSIOCODE)
                    .permission(Permission.STORAGE, Permission.CAMERA)
                    .rationale(camerRationaleListener)
                    .callback(camerPermissionListener)
                    .start();
        }
    }

    /**
     * 显示申请权限弹出框
     */
    RationaleListener camerRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
            AndPermission.rationaleDialog(context, rationale).show();
        }
    };

    /**
     * 用户是否同意申请的权限
     */
    PermissionListener camerPermissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {

            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == PERMISSIOCODE) {
                if (hasSDCard() == false) {
                    return;
                }
                String imgName = DateTimeUtil.date2String(System.currentTimeMillis(), "yyyyMMddHHmmssSSS") + UUIDUtil.getRandom4() + Cons.IMG_SUFFIX;
                String pathE = Environment.getExternalStorageDirectory() + "/" + imgName;
                fileTemp = new File(pathE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uriTemp = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".FileProvider", fileTemp);//通过FileProvider创建一个content类型的Uri
                } else {
                    uriTemp = Uri.fromFile(fileTemp);
                }
                PhotoUtils.takePicture((Activity) context, uriTemp, TAKE_PICTURE);
            }

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
//            LogUtil.d(SignPackageActivity.class, "失败");

        }
    };

    /**
     * 判断是否有SD卡
     *
     * @return 有SD卡返回true，否则false
     */
    private boolean hasSDCard() {
        // 获取外部存储的状态
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 有SD卡
            return true;
        }
        return false;

    }

    public List<String> getLstImagePath() {
        return lstImagePath;
    }

    public void setLstImagePath(String pic1, String pic2) {
        this.removeAllViews();
        if (TextUtils.isEmpty(pic1) == false) {
            lstImagePath.add(pic1);
            addView(initPictureItemView(pic1), 0);
        }
        if (TextUtils.isEmpty(pic2) == false) {
            addView(initPictureItemView(pic2), 0);
            lstImagePath.add(pic2);
        }

        addView(imageView);
        if (TextUtils.isEmpty(pic1) == false && TextUtils.isEmpty(pic2) == false) {
            imageView.setVisibility(GONE);
        }
    }

    public void setShow(List<String> lstImagePath) {
        removeAllViews();
        isShow = true;
        for (String item : lstImagePath) {
            addView(initPictureItemView(item));
        }

    }
}
