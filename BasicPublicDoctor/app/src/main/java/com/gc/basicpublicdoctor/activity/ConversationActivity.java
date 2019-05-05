package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.widget.WindowSoftModeAdjustResizeExecutor;

/**
 * Author:Created by zhurui
 * Time:2018/8/3 下午2:34
 * Description:This is ConversationActivity
 * 会话页面
 */
public class ConversationActivity extends BaseActivity {
    Context context;

    // 医生聊天id
    String targetId = "";
    // 医生聊天名字
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        context = this;
        WindowSoftModeAdjustResizeExecutor.assistActivity(this);

        initGetMSG();

        showTitleName(title, true);
    }

    private void initGetMSG() {
        Intent intent = getIntent();
        if (intent != null) {
            targetId = intent.getData().getQueryParameter("targetId");  // 获取id
            title = intent.getData().getQueryParameter("title");     // 昵称
        }
    }
}
