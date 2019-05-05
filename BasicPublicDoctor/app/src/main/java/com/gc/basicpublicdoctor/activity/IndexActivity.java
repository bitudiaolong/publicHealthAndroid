package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.utils.LogUtil;
import com.gc.utils.SPUtils;

/**
 * Author:Created by zhurui
 * Time:2018/8/7 下午3:44
 * Description:This is IndexActivity
 * 引导页面
 */
public class IndexActivity extends BaseActivity {
    private String doctorToken;
    private String userUid;
    private String userPicture;
    private String userPhone;
    private String userName;
    private String userSex;
    private String teamMembers;
    private String healthRoomName;
    private String docTeamName;
    private String rongToken;
    private String doctorSignQRCode;
    Context context;
    private boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        context = this;
        doctorToken = (String) SPUtils.get(this, "USERTOKEN", "");
        userUid = (String) SPUtils.get(this, "USERUID", "");
        userPicture = (String) SPUtils.get(this, "USERPICTURE", "");
        userPhone = (String) SPUtils.get(this, "USERPHONE", "");
        userName = (String) SPUtils.get(this, "USERNAME", "");
        userSex = (String) SPUtils.get(this, "USERSEX", "");
        teamMembers = (String) SPUtils.get(this, "TEAMMEMBERS", "");
        healthRoomName = (String) SPUtils.get(this, "HEALTHROOMNAME", "");
        docTeamName = (String) SPUtils.get(this, "DOCTEAMNAME", "");
        rongToken = (String) SPUtils.get(this, "RONGTOKEN", "");
        doctorSignQRCode = (String) SPUtils.get(this, "DOCTORSIGNQRCODE", "");
        LogUtil.d("IndexActivity", "token" + doctorToken);
        isStart = (Boolean) SPUtils.get(this, "isFirstStart", true);
        if (isStart) {
            SPUtils.put(this, "isFirstStart", false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isStart) {
                    Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Cons.doctorToken = doctorToken;
                    Cons.userUid = userUid;
                    Cons.userPicture = userPicture;
                    Cons.userPhone = userPhone;
                    Cons.userName = userName;
                    Cons.userSex = userSex;
                    Cons.teamMembers = teamMembers;
                    Cons.healthRoomName = healthRoomName;
                    Cons.docTeamName = docTeamName;
                    Cons.doctorSignQRCode = doctorSignQRCode;
                    DoctorApplication.RY_TOKEN = rongToken;

                    if (TextUtils.isEmpty(Cons.doctorToken)) {
                        Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                        DoctorApplication.connect(DoctorApplication.RY_TOKEN);
                        startActivity(intent);
                    }
                }
                finish();
            }
        }, 3000);
    }

}
