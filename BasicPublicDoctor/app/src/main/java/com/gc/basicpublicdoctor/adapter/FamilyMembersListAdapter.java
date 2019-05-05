package com.gc.basicpublicdoctor.adapter;

import android.content.Context;
import android.view.View;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleHolder;
import com.gc.basicpublicdoctor.bean.FamilyMembersListResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/8/1 下午2:42
 * Description:This is FamilyMembersListAdapter
 * 家庭成员列表
 */
public class FamilyMembersListAdapter extends BaseRecycleAdapter<FamilyMembersListResponse.DataBean.ListBean> {

    public FamilyMembersListAdapter(Context context, List<FamilyMembersListResponse.DataBean.ListBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void bindView(BaseRecycleHolder baseRecycleHolder, final int position, final FamilyMembersListResponse.DataBean.ListBean bean) {
        View itemView = baseRecycleHolder.itemView;

        baseRecycleHolder.setTextViewText(R.id.tv_name, bean.getName())
                .setTextViewText(R.id.tv_sex, bean.getSex())
                .setTextViewText(R.id.tv_age, bean.getAge() + "岁")
                .setTextViewText(R.id.tv_relationship, bean.getRelationship())
                .setOnClickListener(R.id.tv_chat, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myChatOnItemClickListener.onItemChat(v, position);
                    }
                });
    }

    @Override
    public void nowStyle(BaseRecycleHolder baseRecycleHolder) {

    }

    public interface MyChatOnItemClickListener {
        void onItemChat(View view, int position);
    }

    private MyChatOnItemClickListener myChatOnItemClickListener;

    public void setMyChatOnItemClickListener(MyChatOnItemClickListener myChatOnItemClickListener) {
        this.myChatOnItemClickListener = myChatOnItemClickListener;
    }
}
