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
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.GetChatTokenResponse;
import com.gc.basicpublicdoctor.bean.LoginResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.HelpUtil;
import com.gc.utils.LogUtil;
import com.gc.utils.SPUtils;

import java.util.Map;

/**
 * Author:Created by zhurui
 * Time:2018/7/18 下午5:30
 * Description:This is LoginActivity
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener
        , DialogProgressBar.OnProgressListener {

    Context context;
    /**
     * 请输入账号
     */
    private EditText mEdtAccount;
    /**
     * 请输入密码
     */
    private EditText mEdtPassword;
    /**
     * 忘记密码
     */
    private TextView mTvForgetPwd;
    /**
     * 登    录
     */
    private Button mBtnLogin;

    private String userAccount;
    private String userPwd;

    DialogProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        showTitleNameTransparent("登录", false);
        initView();
    }

    private void initView() {
        mEdtAccount = (EditText) findViewById(R.id.edt_account);
        mEdtAccount.setSelection(mEdtAccount.getText().toString().length());
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mTvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        mTvForgetPwd.setOnClickListener(this);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
    }

    /**
     * 登录接口
     *
     * @author zhurui
     * @time 2018/7/18 下午1:43
     */
    public void getLogin(final String account, String password) {
//        Map<String, String> map = DoctorApplication.getBaseMapList();
//        map.put("account", account);
//        map.put("password", password);
//        progressBar.show();
//        String data = AndroidDes3Util.encode(GsonUtil.toJson(map));
//        String[] params = {
//                "data", data
//        };
//        HttpManager.postData("getLogin", params, Cons.URL_BASE + "sysaccount/login", new HttpCallBack() {
//            @Override
//            public void success(String result) {
//                MyGagcLog.getInstance().showLogE("1111-" + result);
//                try {
//                    result = AndroidDes3Util.decode(result);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                MyGagcLog.getInstance().showLogE(result);
//                LoginResponse bean = GsonUtil.gsonToBean(result, LoginResponse.class);
//                if (bean.getErrcode() == 200) {
//                    if (bean.getData() != null) {
//                        Cons.token = bean.getData().getToken();
//                        Cons.userUid = bean.getData().getUserUid();
//                        Cons.userPicture = bean.getData().getUserPicture();
//                        Cons.userPhone = bean.getData().getUserPhone();
//                        Cons.userName = bean.getData().getUserName();
//                        Cons.userSex = bean.getData().getUserSex();
//
//                        Cons.account = userAccount;
//                        Cons.password = userPwd;
//
//                        // 保存用户token 账号 密码
//                        SPUtils.put(context, "USERTOKEN", Cons.token);
//                        SPUtils.put(context, "USERACCOUNT", Cons.account);
//                        SPUtils.put(context, "USERPASSWORD", Cons.password);
//                    }
//                    handler.sendEmptyMessage(Cons.LOADING_DISMISS);
//                    startActivity(new Intent(context, MainActivity.class));
//                    finish();
//                } else {
//                    handler.sendEmptyMessage(Cons.LOADING_DISMISS);
//                    HelpUtil.showToast(context, bean.getErrmsg());
//                }
//
//            }
//
//            @Override
//            public void error(String errorCode, String errorMsg) {
//                HelpUtil.showToast(context, errorMsg);
//            }
//        });
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("account", account);
        map.put("password", password);
        progressBar.show();
        new HLHttpUtils().post(map, Cons.LOGIN()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse bean) {
                if (bean.getData() != null) {
                    Cons.doctorToken = bean.getData().getDoctorToken();
                    Cons.userUid = bean.getData().getUserUid();
                    Cons.userPicture = bean.getData().getUserPicture();
                    Cons.userPhone = bean.getData().getUserPhone();
                    Cons.userName = bean.getData().getUserName();
                    Cons.userSex = bean.getData().getUserSex();
                    Cons.teamMembers = bean.getData().getTeamMembers();
                    Cons.healthRoomName = bean.getData().getHealthRoomName();
                    Cons.docTeamName = bean.getData().getDocTeamName();
                    Cons.doctorSignQRCode = bean.getData().getDoctorSignQRCode();
                    Cons.account = userAccount;
                    Cons.password = userPwd;

                    getChatToken(bean.getData().getDoctorToken(), bean.getData().getUserUid());

                    // 保存用户token 账号 密码
                    SPUtils.put(context, "USERTOKEN", Cons.doctorToken);
                    SPUtils.put(context, "USERUID", Cons.userUid);
                    SPUtils.put(context, "USERPICTURE", Cons.userPicture);
                    SPUtils.put(context, "USERPHONE", Cons.userPhone);
                    SPUtils.put(context, "USERNAME", Cons.userName);
                    SPUtils.put(context, "USERSEX", Cons.userSex);
                    SPUtils.put(context, "TEAMMEMBERS", Cons.teamMembers);
                    SPUtils.put(context, "HEALTHROOMNAME", Cons.healthRoomName);
                    SPUtils.put(context, "DOCTEAMNAME", Cons.docTeamName);
                    SPUtils.put(context, "USERACCOUNT", Cons.account);
                    SPUtils.put(context, "USERPASSWORD", Cons.password);
                    SPUtils.put(context, "DOCTORSIGNQRCODE", Cons.doctorSignQRCode);
                }
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
     * @author zhurui
     * @time 2018/8/3 上午11:14
     * 获取融云token
     */
    public void getChatToken(String token, String userUid) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", token);
        map.put("userUid", userUid);
        progressBar.show();
        new HLHttpUtils().post(map, Cons.GET_CHAT_TOKEN()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<GetChatTokenResponse>() {
            @Override
            public void onSuccess(GetChatTokenResponse bean) {
                if (bean.getData() != null) {
                    DoctorApplication.RY_TOKEN = bean.getData().getUserToken();
                    DoctorApplication.connect(bean.getData().getUserToken());

                    SPUtils.put(context, "RONGTOKEN", DoctorApplication.RY_TOKEN);
                }
            }

            @Override
            public void onFailure(String code, String errormsg) {
                LogUtil.e("getChatToken", "errormsg==" + errormsg);
            }

            @Override
            public void result(String result) {

            }
        });
    }

    /**
     * 输入判断
     *
     * @author zhurui
     * @time 2018/7/20 下午1:47
     */
    public boolean validateInfo(String account, String password) {

        // 判断用户名是否为空D
        if (TextUtils.isEmpty(account)) {
            HelpUtil.showToast(context, "请输入账号");
            return false;
        }
        // 判断密码是否为空
        if (TextUtils.isEmpty(password)) {
            HelpUtil.showToast(context, "请输入密码");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(context, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                userAccount = mEdtAccount.getText().toString().trim() == null ? "" : mEdtAccount.getText().toString().trim();
                userPwd = mEdtPassword.getText().toString().trim() == null ? "" : mEdtPassword.getText().toString().trim();
                if (!validateInfo(userAccount, userPwd)) {
                    return;
                }
                getLogin(mEdtAccount.getText().toString().trim(), mEdtPassword.getText().toString().trim());
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
