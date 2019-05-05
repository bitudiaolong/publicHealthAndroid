package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gagc.httplibrary.BaseResponse;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.HelpUtil;
import com.gc.utils.SPUtils;

import java.util.Map;

import io.rong.imkit.RongIM;

/**
 * Author:Created by zhurui
 * Time:2018/7/23 上午9:32
 * Description:This is ModifyPasswordActivity
 * 修改密码页面
 */
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener
        , DialogProgressBar.OnProgressListener {

    Context context;

    private View mView;
    /**
     * 请输入旧密码
     */
    private EditText mEdtOldPwd;
    /**
     * 请输入新密码
     */
    private EditText mEdtNewPwd;
    /**
     * 请再次输入新密码
     */
    private EditText mEdtNewPwdTwo;
    /**
     * 保    存
     */
    private Button mBtnSave;


    private String oldPwd;
    private String newPwd;
    private String newPwdTwo;

    DialogProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        context = this;

        showTitleNameTransparent("修改密码", true);
        initView();
    }

    private void initView() {
        mView = (View) findViewById(R.id.view);
        mEdtOldPwd = (EditText) findViewById(R.id.edt_old_pwd);
        mEdtNewPwd = (EditText) findViewById(R.id.edt_new_pwd);
        mEdtNewPwdTwo = (EditText) findViewById(R.id.edt_new_pwd_two);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);

        progressBar = new DialogProgressBar(context, "正在加载", 0, this);

    }

    /**
     * 修改密码接口
     *
     * @author zhurui
     * @time 2018/7/23 上午9:35
     */
    public void getModifyPassword(String token, String userUid, String oldPassword, String newPassword) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", token);
        map.put("userUid", userUid);
        map.put("oldPassword", oldPassword);
        map.put("newPassword", newPassword);
        progressBar.show();
        new HLHttpUtils().post(map, Cons.MODIFY_PASSWORD()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse bean) {
                // 保存用户token 账号 密码
                SPUtils.put(context, "USERTOKEN", "");
                SPUtils.put(context, "USERUID", "");
                SPUtils.put(context, "USERPICTURE", "");
                SPUtils.put(context, "USERPHONE", "");
                SPUtils.put(context, "USERNAME", "");
                SPUtils.put(context, "USERSEX", "");
                SPUtils.put(context, "TEAMMEMBERS", "");
                SPUtils.put(context, "HEALTHROOMNAME", "");
                SPUtils.put(context, "DOCTEAMNAME", "");
                SPUtils.put(context, "USERACCOUNT", "");
                SPUtils.put(context, "USERPASSWORD", "");
                SPUtils.put(context, "RONGTOKEN", "");

                RongIM.getInstance().logout();
                RongIM.getInstance().disconnect();

                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                HelpUtil.showToast(context, "密码修改成功");

                Intent intent = new Intent(MainActivity.LOGOUT);
                sendBroadcast(intent);
                finish();
            }

            @Override
            public void onFailure(String code, String errormsg) {
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                HelpUtil.showToast(context, errormsg);
            }

            @Override
            public void result(String result) {

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Cons.LOADING_DISMISS:
                    if (progressBar != null) {
                        progressBar.misDialog();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 输入判断
     *
     * @author zhurui
     * @time 2018/7/20 下午1:47
     */
    public boolean validateInfo(String oldPwd, String newPwd, String newPwdTwo) {

        // 判断手机号是否为空D
        if (TextUtils.isEmpty(oldPwd)) {
            HelpUtil.showToast(context, "请输入旧密码");
            return false;
        }
        // 判断验密码是否为空
        if (TextUtils.isEmpty(newPwd)) {
            HelpUtil.showToast(context, "请输入新密码");
            return false;
        }
        // 判断二次密码是否为空
        if (TextUtils.isEmpty(newPwdTwo)) {
            HelpUtil.showToast(context, "请再次输入新密码");
            return false;
        }
        // 判断密码是否一致
        if (!newPwdTwo.equals(newPwd)) {
            HelpUtil.showToast(context, "两次密码输入不一致");
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                oldPwd = mEdtOldPwd.getText() == null ? "" : mEdtOldPwd.getText().toString();
                newPwd = mEdtNewPwd.getText() == null ? "" : mEdtNewPwd.getText().toString();
                newPwdTwo = mEdtNewPwdTwo.getText() == null ? "" : mEdtNewPwdTwo.getText().toString();
                if (!validateInfo(oldPwd, newPwd, newPwdTwo)) {
                    return;
                }
                getModifyPassword(Cons.doctorToken, Cons.userUid, oldPwd, newPwd);
                break;
            default:
                break;
        }
    }

    @Override
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

    }

}
