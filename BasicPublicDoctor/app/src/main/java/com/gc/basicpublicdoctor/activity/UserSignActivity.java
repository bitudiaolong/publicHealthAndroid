package com.gc.basicpublicdoctor.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.widget.SignView;
import com.gc.utils.DateTimeUtil;
import com.gc.utils.HelpUtil;
import com.gc.utils.ImageUtils;
import com.gc.utils.UUIDUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

public class UserSignActivity extends BaseActivity implements View.OnClickListener {
    public static final int PERMISSIOCODE = 102;
    private LinearLayout mTxtSignBack;
    private TextView mTxtSignConfim;
    private SignView mSignView;
    /**
     * 撤销
     */
    private TextView mTvUndo;
    /**
     * 清除
     */
    private TextView mTvDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign);
        initView();
    }

    private void initView() {
        mTxtSignBack = (LinearLayout) findViewById(R.id.txt_sign_back);
        mTxtSignBack.setOnClickListener(this);
        mTxtSignConfim = (TextView) findViewById(R.id.txt_sign_confim);
        mTxtSignConfim.setOnClickListener(this);
        mSignView = (SignView) findViewById(R.id.sign_view);
        AndPermission.with(context)
                .requestCode(PERMISSIOCODE)
                .permission(Permission.STORAGE)
                .rationale(camerRationaleListener)
                .callback(camerPermissionListener)
                .start();

        mTvUndo = (TextView) findViewById(R.id.tv_undo);
        mTvUndo.setOnClickListener(this);
        mTvDelete = (TextView) findViewById(R.id.tv_delete);
        mTvDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.txt_sign_back:
                super.onBackPressed();
                break;
            case R.id.txt_sign_confim:
                if (mSignView.canUndo() == false) {
                    HelpUtil.showToast(context, "还没有签字");
                    return;
                }
                Bitmap bitmap = mSignView.buildBitmap();

                String imgName = DateTimeUtil.date2String(System.currentTimeMillis(), "yyyyMMddHHmmssSSS") + UUIDUtil.getRandom4() + Cons.IMG_SUFFIX;
                String pathE = context.getExternalFilesDir(null).getAbsolutePath();
                String path = ImageUtils.saveFile(bitmap, pathE + "/" + imgName);
                if (TextUtils.isEmpty(path) == false) {
                    Intent intent = new Intent();
                    intent.putExtra("path", path);
                    intent.setAction(SignActivity.USER_SIGN_BACK);
                    sendBroadcast(intent);
                    finish();
                } else {
                    HelpUtil.showToast(context, "保存图片失败");
                }

                break;
            case R.id.tv_undo:
                mSignView.undo();
                break;
            case R.id.tv_delete:
                mSignView.clear();
                break;
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

            }

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            finish();

        }
    };
}
