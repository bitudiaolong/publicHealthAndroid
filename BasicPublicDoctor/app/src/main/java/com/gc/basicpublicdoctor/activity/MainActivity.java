package com.gc.basicpublicdoctor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.ViewPagerAdapter;
import com.gc.basicpublicdoctor.base.BaseFragmentActivity;
import com.gc.basicpublicdoctor.bean.ChatUserInfoResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.fragment.firstfragment.FirstTabFragment;
import com.gc.basicpublicdoctor.fragment.secondfragment.SecondTabFragment;
import com.gc.basicpublicdoctor.fragment.thirdfragment.ThirdTabFragment;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.update.BaseUpdate;
import com.gc.basicpublicdoctor.view.NoScrollViewPager;
import com.gc.utils.HelpUtil;
import com.gc.utils.LogUtil;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

/**
 * Author:Created by zhurui
 * Time:2018/7/09 上午10:50
 * Description:This is MainActivity
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    /**
     * 后台返回401（非法token），代表在其他端被登录，需重新登录action
     */
    public static final String IN_TOKEN = "IN_TOKEN";
    /**
     * 退出mainactivity广播
     */
    public static final String LOGOUT = "LOGOUT";

    Context context;

    /**
     * fragment集合
     */
    private List<Fragment> listFragment;

    private int currentPoition;
    /**
     * viewPager viewpager适配器
     */
    private NoScrollViewPager viewPager;
    private ViewPagerAdapter viewPagerAapter;

    /**
     * 首页tab
     */
    FirstTabFragment firstTabFragment;

    /**
     * 咨询tab
     */
    SecondTabFragment secondTabFragment;

    /**
     * 我的tab
     */
    ThirdTabFragment thirdTabFragment;

    /**
     * 首页
     */
    private ImageView mIvHome;
    private TextView mTvHome;
    private LinearLayout mLlHome;
    /**
     * 咨询
     */
    private ImageView mIvConsultation;
    private TextView mTvConsultation;
    private LinearLayout mLlConsultation;
    /**
     * 我的
     */
    private ImageView mIvMe;
    private TextView mTvMe;
    private LinearLayout mLlMe;
    /**
     * 退出程序时间
     */
    private long mExitTime;
    private MyBroadcastReceiver myBroadcastReceiver;

    private TextView mTvNoRead;
    private RelativeLayout mRlNoRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
        checkUpdate();

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LOGOUT);
        intentFilter.addAction(IN_TOKEN);
        registerReceiver(myBroadcastReceiver, intentFilter);

        try {
            RongPushClient.checkManifest(context);
        } catch (RongException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mRlNoRead = (RelativeLayout) findViewById(R.id.rl_no_read);
        mTvNoRead = (TextView) findViewById(R.id.tv_no_read);

        mIvHome = (ImageView) findViewById(R.id.iv_home);
        mTvHome = (TextView) findViewById(R.id.tv_home);
        mLlHome = (LinearLayout) findViewById(R.id.ll_home);
        mLlHome.setOnClickListener(this);
        mIvConsultation = (ImageView) findViewById(R.id.iv_consultation);
        mTvConsultation = (TextView) findViewById(R.id.tv_consultation);
        mLlConsultation = (LinearLayout) findViewById(R.id.ll_consultation);
        mLlConsultation.setOnClickListener(this);
        mIvMe = (ImageView) findViewById(R.id.iv_me);
        mTvMe = (TextView) findViewById(R.id.tv_me);
        mLlMe = (LinearLayout) findViewById(R.id.ll_me);
        mLlMe.setOnClickListener(this);

        viewPager = findViewById(R.id.id_viewpager);
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(3);

        listFragment = new ArrayList<>();
        firstTabFragment = new FirstTabFragment();
        listFragment.add(firstTabFragment);
        secondTabFragment = new SecondTabFragment();
        listFragment.add(secondTabFragment);
        thirdTabFragment = new ThirdTabFragment();
        listFragment.add(thirdTabFragment);
        viewPagerAapter = new ViewPagerAdapter(getSupportFragmentManager(), listFragment);
        viewPager.setAdapter(viewPagerAapter);
        viewPager.setOnPageChangeListener(new TabOnPageChangeListener());

        initGetMSG();
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.d("MainActivity广播==", "接收到广播" + intent.getAction());
            if (LOGOUT.equals(intent.getAction())) {
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (IN_TOKEN.equals(intent.getAction())) {
//                HelpUtil.showToast(context, "您的帐号在其他地方登录了，请重新登录");
//                intent = new Intent(context, LoginActivity.class);
//                startActivity(intent);
//                finish();
                handler.removeMessages(1002);
                handler.sendEmptyMessageDelayed(1002, 500);
//                viewPager.setCurrentItem(0);
            }
        }
    }

    private Handler handler = new Handler() {
        Intent intent;

        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Cons.LOADING_DISMISS:
                    break;
                case 1002:
                    intent = new Intent(context, LoginActivity.class);
                    // 清除栈中所有activitty
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                    finish();
                    break;
                default:
                    break;
            }

        }
    };

    class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            resetTextViewColor();
            resetImageViewSrc();
            switch (position) {
                case 0:
                    mTvHome.setTextColor(Color.parseColor("#39B4D1"));
                    mIvHome.setBackground(getResources().getDrawable(R.drawable.icon_home_home_s));
                    currentPoition = 0;
                    break;
                case 1:
                    mTvConsultation.setTextColor(Color.parseColor("#39B4D1"));
                    mIvConsultation.setBackground(getResources().getDrawable(R.drawable.icon_home_consultation_s));
                    currentPoition = 1;
                    break;
                case 2:
                    mTvMe.setTextColor(Color.parseColor("#39B4D1"));
                    mIvMe.setBackground(getResources().getDrawable(R.drawable.icon_home_me_s));
                    currentPoition = 2;
                    break;
            }
        }

        private void resetImageViewSrc() {
            mIvHome.setBackground(getResources().getDrawable(R.drawable.icon_home_home_n));
            mIvConsultation.setBackground(getResources().getDrawable(R.drawable.icon_home_consultation_n));
            mIvMe.setBackground(getResources().getDrawable(R.drawable.icon_home_me_n));
//                mMeImageView.setImageResource(R.drawable.me1);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffPx) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    }

    private void resetTextViewColor() {
        mTvHome.setTextColor(Color.parseColor("#969696"));
        mTvConsultation.setTextColor(Color.parseColor("#969696"));
        mTvMe.setTextColor(Color.parseColor("#969696"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.ll_consultation:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.ll_me:
                viewPager.setCurrentItem(2, true);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                HelpUtil.showToast(context, "再按一次退出程序");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(MyUnReadMessageObserver, Conversation.ConversationType.PRIVATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(MyUnReadMessageObserver);
        unregisterReceiver(myBroadcastReceiver);
    }

    /**
     * 检测更新
     */
    private void checkUpdate() {
        getPermissions(100, Permission.STORAGE);

    }

    @Override
    protected void onPermissionSuccess(int requestCode) {
        BaseUpdate.update(context);
    }

    private IUnReadMessageObserver MyUnReadMessageObserver = new IUnReadMessageObserver() {
        @Override
        public void onCountChanged(int i) {
            if (i > 0) {
                mRlNoRead.setVisibility(View.VISIBLE);
                if (i > 99) {
                    mTvNoRead.setText(String.valueOf(i) + "+");
                } else {
                    mTvNoRead.setText(String.valueOf(i));
                }
            } else {
                mRlNoRead.setVisibility(View.GONE);
            }
            initGetMSG();
        }
    };

    // 医生聊天id
    String targetId = "";

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
                targetId = "";
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