package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gagc.httplibrary.BaseResponse;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.HelpUtil;
import com.gc.utils.LogUtil;

import java.util.Map;

/**
 * Author:Created by zhurui
 * Time:2018/7/18 下午5:30
 * Description:This is ForgetPasswordActivity
 * 忘记密码页面
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener
        , DialogProgressBar.OnProgressListener {

    Context context;
    /**
     * 请输入手机号
     */
    private EditText mEdtPhone;
    /**
     * 请输入验证码
     */
    private EditText mEdtVerification;
    /**
     * 获取验证码
     */
    private TextView mTvObtain;
    /**
     * 请输入新密码
     */
    private EditText mEdtPassword;
    /**
     * 请再次输入新密码
     */
    private EditText mEdtPasswordTwo;
    /**
     * 保    存
     */
    private Button mBtnSave;

    private String userPhone;
    private String verificationCode;
    private String newPwd;
    private String newPwdTwo;

    DialogProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        context = this;

        showTitleNameTransparent("忘记密码", true);
        initView();
    }

    private void initView() {
        mEdtPhone = (EditText) findViewById(R.id.edt_phone);
        mEdtVerification = (EditText) findViewById(R.id.edt_verification);
        mTvObtain = (TextView) findViewById(R.id.tv_obtain);
        mTvObtain.setOnClickListener(this);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mEdtPasswordTwo = (EditText) findViewById(R.id.edt_password_two);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);

        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
    }

    /**
     * 获取验证码接口
     *
     * @author zhurui
     * @time 2018/7/20 下午2:11
     */
    public void getCode(String phone) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("phone", phone);
        progressBar.show();
        new HLHttpUtils().post(map, "").setCallBack(new AbstarctGenericityHttpUtils.CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse bean) {
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                HelpUtil.showToast(context, "验证码已发送");
                timer.start();
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

    /**
     * 忘记密码接口
     *
     * @author zhurui
     * @time 2018/7/20 下午2:11
     */
    public void getForgotPassword(String phone, String verificationCode, String newPassword) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("phone", phone);
        map.put("verificationCode", verificationCode);
        map.put("newPassword", newPassword);
        progressBar.show();
        new HLHttpUtils().post(map, "").setCallBack(new AbstarctGenericityHttpUtils.CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse bean) {
                Cons.account = mEdtPhone.getText().toString().trim();
                Cons.password = mEdtPassword.getText().toString().trim();

                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                HelpUtil.showToast(context, "密码更新成功");
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

    // 倒计时计数
    private final CountDownTimer timer = new CountDownTimer(120000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mTvObtain.setText("重新发送" + (millisUntilFinished / 1000) + "s");
            LogUtil.e("LoginActivity", "短信验证码倒计时");
        }

        @Override
        public void onFinish() {
            mTvObtain.setEnabled(true);
            mTvObtain.setText("重新获取");
            mTvObtain.setClickable(true);
        }
    };

    /**
     * 输入判断
     *
     * @author zhurui
     * @time 2018/7/20 下午1:47
     */
    public boolean validateInfo(String userPhone, String verificationCode, String newPwd, String newPwdTwo) {

        // 判断手机号是否为空D
        if (TextUtils.isEmpty(userPhone)) {
            HelpUtil.showToast(context, "请输入手机号");
            return false;
        }
        // 判断手机号码格式是否正确
        if (userPhone.length() != 11) {
            HelpUtil.showToast(context, "手机号码格式不正确");
            return false;
        }
        // 判断验证码是否为空
        if (TextUtils.isEmpty(verificationCode)) {
            HelpUtil.showToast(context, "请输入验证码");
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
            default:
                break;
            case R.id.tv_obtain:
                userPhone = mEdtPhone.getText() == null ? "" : mEdtPhone.getText().toString();
                if (!validateInfo(userPhone, "1", "1", "1")) {
                    return;
                }
                timer.start();
                getCode(userPhone);
                break;
            case R.id.btn_save:
                userPhone = mEdtPhone.getText() == null ? "" : mEdtPhone.getText().toString();
                verificationCode = mEdtVerification.getText() == null ? "" : mEdtVerification.getText().toString();
                newPwd = mEdtPassword.getText() == null ? "" : mEdtPassword.getText().toString();
                newPwdTwo = mEdtPasswordTwo.getText() == null ? "" : mEdtPasswordTwo.getText().toString();
                if (!validateInfo(userPhone, verificationCode, newPwd, newPwdTwo)) {
                    return;
                }
                getForgotPassword(userPhone, verificationCode, newPwd);

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
