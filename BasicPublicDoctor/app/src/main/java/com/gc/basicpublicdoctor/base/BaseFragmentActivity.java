package com.gc.basicpublicdoctor.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/16 上午11:31
 * Description:This is BaseFragmentActivity
 */
public class BaseFragmentActivity extends FragmentActivity {
    protected ImmersionBar mImmersionBar;
    Context context;
    /**
     * 权限申请的返回code
     */
    int mRequestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initImmersionBar();
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    protected void getPermissions(int requestCode, String[]... permissionsArray) {
        this.mRequestCode = requestCode;
        AndPermission.with(this)
                .requestCode(requestCode)
                .permission(permissionsArray)
                .callback(new CamerPermissionListener())
                .rationale(new PermissionsrRationalListener())
                .start();
    }


    /**
     * 显示申请权限弹出框
     */
    class PermissionsrRationalListener implements RationaleListener {

        @Override
        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
            AndPermission.rationaleDialog(context, rationale).show();
        }
    }

    /**
     * 子类去实现
     *
     * @param requestCode
     */
    protected void onPermissionSuccess(int requestCode) {

    }

    /**
     * 用户同意申请的权限
     */
    class CamerPermissionListener implements PermissionListener {

        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            if (requestCode == mRequestCode) {
                onPermissionSuccess(requestCode);
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 101) {
                Toast.makeText(context, "需要照相机权限和文件的读写权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
