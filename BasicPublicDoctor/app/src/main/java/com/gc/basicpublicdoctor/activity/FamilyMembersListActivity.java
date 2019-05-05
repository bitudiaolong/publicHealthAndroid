package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.FamilyMembersListAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleAdapter;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.FamilyMembersListResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.HelpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Author:Created by zhurui
 * Time:2018/8/1 上午10:40
 * Description:This is FamilyMembersListActivity
 * 家庭成员列表
 */
public class FamilyMembersListActivity extends BaseActivity implements View.OnClickListener
        , DialogProgressBar.OnProgressListener, RongIM.UserInfoProvider {

    Context context;

    private View mView;
    private RecyclerView mRcyView;
    private FamilyMembersListAdapter adapter;
    private List<FamilyMembersListResponse.DataBean.ListBean> list = new ArrayList<>();
    private int layoutId = R.layout.item_family_members;

    DialogProgressBar progressBar;

    String targedId;
    String titleName;
    String householderIDCard;
    String doType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members_list);
        context = this;

        showTitleName("家庭成员", true);
        showRightBtn("添加家人", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignActivity.class);
                intent.putExtra("signType", "2");//家庭
                intent.putExtra("householderIDCard", householderIDCard);//家庭
                startActivity(intent);
            }
        });

        initView();
    }

    private void initView() {
        doType = getIntent().getStringExtra("doType");
        mView = (View) findViewById(R.id.view);
        mRcyView = (RecyclerView) findViewById(R.id.rcy_view);

        adapter = new FamilyMembersListAdapter(context, list, layoutId);

        mRcyView.setLayoutManager(new LinearLayoutManager(context));
        mRcyView.setAdapter(adapter);
        adapter.setMyChatOnItemClickListener(new MyOnItemClickListener());
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FamilyMembersListResponse.DataBean.ListBean bean = list.get(position);
                Intent intent = new Intent(context, SignDetailActivity.class);
                intent.putExtra("signId", bean.getSignId());
                intent.putExtra("signState", bean.getSignState());
                intent.putExtra("doType",doType);
                startActivity(intent);
            }
        });

        householderIDCard = getIntent().getStringExtra("householderIDCard");
        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
        getFamilyMembersList(householderIDCard);
    }

    @Override
    public UserInfo getUserInfo(String s) {
        return null;
    }

    class MyOnItemClickListener implements FamilyMembersListAdapter.MyChatOnItemClickListener {
        @Override
        public void onItemChat(View view, int position) {
            targedId = list.get(position).getRongCloudToken();
            titleName = list.get(position).getName();
            RongIM.getInstance().setMessageAttachedUserInfo(true);
            String userRYHeadImage = Cons.userPicture;
            if (TextUtils.isEmpty(userRYHeadImage)) {
                userRYHeadImage = "http://img.gagctv.com/18061117064285628";
            }
//            RongIM.getInstance().setCurrentUserInfo(new UserInfo(targedId, titleName, Uri.parse(userRYHeadImage)));
//            RongIM.getInstance().startPrivateChat(context, targedId, titleName);

            RongIM.setUserInfoProvider(FamilyMembersListActivity.this, true);
            RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, targedId, titleName);
        }
    }

    /**
     * 家庭成员列表
     *
     * @author zhurui
     * @time 2018/8/1 上午11:07
     */
    public void getFamilyMembersList(String householderIDCard) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("householderIDCard", householderIDCard);
        progressBar.show();
        new HLHttpUtils().post(map, Cons.FAMILY_MEMBERS_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<FamilyMembersListResponse>() {
            @Override
            public void onSuccess(FamilyMembersListResponse bean) {
                if (bean.getData() != null) {
                    if (bean.getData().getList() != null) {
                        list.addAll(bean.getData().getList());
                    }
                }
                adapter.notifyDataSetChanged();
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
