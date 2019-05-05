package com.gc.basicpublicdoctor.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.basicpublicdoctor.R;
import com.gc.utils.KeyboardUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/10 上午10:51
 * Description:This is BaseActivity
 */
public class BaseActivity extends AppCompatActivity {
    protected Context context;

    protected ImmersionBar mImmersionBar;
    private InputMethodManager inputMethodManager;
    /**
     * 权限申请的返回code
     */
    int mRequestCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        initImmersionBar();
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtil.closeBoard(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }

    }

    /**
     * 点击空白处收起软键盘
     *
     * @author zhurui
     * @time 2018/7/20 上午9:41
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }


    public void showBackText(String title, View.OnClickListener onClickListener) {
        TextView textView = (TextView) findViewById(R.id.back_btn);
        if (textView != null) {
            textView.setText(title);
            textView.setOnClickListener(onClickListener);
            textView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param title
     * @param flag  是否显示返回键
     */
    public void showTitleName(String title, boolean flag) {
        TextView tvTitle = findViewById(R.id.tv_title_bar);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        ImageView layout = (ImageView) findViewById(R.id.left_Image);
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.lin_left);
        if (flag) {
            if (layout != null && rel != null) {
                layout.setVisibility(View.VISIBLE);
                rel.setVisibility(View.VISIBLE);
                rel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        } else {
            if (layout != null && rel != null) {
                layout.setVisibility(View.GONE);
                rel.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @param title
     * @param flag  是否显示返回键 登录 忘记密码页面透明
     */
    public void showTitleNameTransparent(String title, boolean flag) {
        View view = findViewById(R.id.view);
        if (view != null) {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        RelativeLayout toolBar = findViewById(R.id.tool_bar);
        if (toolBar != null) {
            toolBar.setBackgroundColor(Color.TRANSPARENT);
        }
        TextView tvTitle = findViewById(R.id.tv_title_bar);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        ImageView layout = (ImageView) findViewById(R.id.left_Image);
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.lin_left);
        if (flag) {
            if (layout != null && rel != null) {
                layout.setVisibility(View.VISIBLE);
                rel.setVisibility(View.VISIBLE);
                rel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        } else {
            if (layout != null && rel != null) {
                layout.setVisibility(View.GONE);
                rel.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @param title
     * @param flag            是否显示右侧按钮
     * @param onClickListener title右侧按钮点击事件
     */
    public void showRightBtn(String title, boolean flag, View.OnClickListener onClickListener) {
        TextView textView = (TextView) findViewById(R.id.Right_tv);
        if (flag) {
            if (textView != null && title != null) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(title);
                textView.setOnClickListener(onClickListener);
            }
        } else {
            if (textView != null) {
                textView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @param flag            是否显示右侧图片-随访计划人群分类
     * @param onClickListener title右侧图片点击事件
     */
    public void showRightImage(boolean flag, View.OnClickListener onClickListener) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.lin_right);
        if (flag) {
            if (relativeLayout != null) {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setOnClickListener(onClickListener);
            }
        }
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
