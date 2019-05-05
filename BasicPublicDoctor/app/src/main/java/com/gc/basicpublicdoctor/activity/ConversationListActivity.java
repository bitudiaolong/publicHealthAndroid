package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.ChatUserInfoResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.WindowSoftModeAdjustResizeExecutor;

import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Author:Created by zhurui
 * Time:2018/8/4 下午1:21
 * Description:This is ConversationListActivity
 * 聚合会话页面
 */
public class ConversationListActivity extends BaseActivity {
    Context context;

    // 医生聊天id
    String targetId = "";
    // 医生聊天名字
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        context = this;
        WindowSoftModeAdjustResizeExecutor.assistActivity(this);

        showTitleName("会话列表", true);
        initGetMSG();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initGetMSG();
    }

    private void initGetMSG() {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations == null) {
                    return;
                } else {
                    if (conversations.size() == 0) {
                        return;
                    }
                }

                int conversationsSize = conversations.size();
                Log.e("infoD", "cconversationsSize+++++" + conversationsSize);
                for (int i = 0; i < conversationsSize; i++) {
                    if (i < conversationsSize - 1) {
                        targetId = targetId + conversations.get(i).getTargetId() + ",";
                    } else if (i == conversationsSize - 1) {
                        targetId = targetId + conversations.get(i).getTargetId();
                    }

                }
                Log.e("infoD", "targetId+++++" + targetId);
                getChatUserInfo(targetId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }

    /**
     * 获取融云聊天列表头像昵称信息
     *
     * @author zhurui
     * @time 2018/8/4 下午1:31
     */
    public void getChatUserInfo(final String userChatIds) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("userChatIds", userChatIds);
        new HLHttpUtils().post(map, Cons.GET_CHAT_USER_INFO()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<ChatUserInfoResponse>() {
            @Override
            public void onSuccess(ChatUserInfoResponse bean) {
                if (bean == null) {
                    return;
                }
                if (bean.getData() != null) {
                    if (bean.getData().getUserChatList() != null && bean.getData().getUserChatList().isEmpty() == false) {
                        List<ChatUserInfoResponse.DataBean.UserChatListBean> userChatListBeanList = bean.getData().getUserChatList();
                        for (int i = 0; i < userChatListBeanList.size(); i++) {
                            ChatUserInfoResponse.DataBean.UserChatListBean userChatListBean = userChatListBeanList.get(i);
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(userChatListBean.getUserChatId(), userChatListBean.getUserChatName(), Uri.parse(userChatListBean.getUserChatHeadUrl())));
                        }
                    }
                }
            }

            @Override
            public void onFailure(String code, String errormsg) {
            }

            @Override
            public void result(String result) {

            }
        });
    }
}
