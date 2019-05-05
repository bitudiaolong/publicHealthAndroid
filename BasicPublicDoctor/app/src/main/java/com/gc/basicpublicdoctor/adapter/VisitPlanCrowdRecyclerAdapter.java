package com.gc.basicpublicdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.bean.VisitPlanCrowdListResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/27 下午1:39
 * Description:This is VisitPlanCrowdRecyclerAdapter
 * 随访计划人群列表适配器
 */
public class VisitPlanCrowdRecyclerAdapter extends RecyclerView.Adapter<VisitPlanCrowdRecyclerAdapter.NormalTextViewHolder> {
    private LayoutInflater mLayoutInflater;
    private NormalTextViewHolder normalTextViewHolder;
    private List<VisitPlanCrowdListResponse.DataBean.ListBean> datas;

    public VisitPlanCrowdRecyclerAdapter(Context context, List<VisitPlanCrowdListResponse.DataBean.ListBean> datas) {
        this.datas = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        normalTextViewHolder = new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_rl_visit_plan_crowd, parent, false));
        return normalTextViewHolder;
    }

    @Override
    public void onBindViewHolder(final NormalTextViewHolder holder, int position) {
        final View itemView = holder.itemView;

        holder.mTvSex.setText(datas.get(position).getSex());
        holder.mTvName.setText(datas.get(position).getName());
        holder.mTvAge.setText(datas.get(position).getAge() + "岁");
        holder.mTvPhoneNumber.setText(datas.get(position).getPhone());
        holder.mTvAddress.setText(datas.get(position).getAddress());
        holder.mTvClassicfication.setText(datas.get(position).getClassificationList());

        if (onItemClickListener != null) {
            holder.mTvPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.mTvPhoneNumber, layoutPos);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPos = holder.getLayoutPosition();
                    onItemClickListener.onViewItemClick(itemView, layoutPos);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvSex;
        private TextView mTvName;
        private TextView mTvAge;
        private TextView mTvPhoneNumber;
        private TextView mTvAddress;
        private TextView mTvClassicfication;
        private LinearLayout mLlClassicfication;
        private LinearLayout mLlSex;
        private TextView mTvChat;

        NormalTextViewHolder(View view) {
            super(view);
            mTvSex = view.findViewById(R.id.tv_sex);
            mTvName = view.findViewById(R.id.tv_name);
            mTvAge = view.findViewById(R.id.tv_age);
            mTvPhoneNumber = view.findViewById(R.id.tv_phone_number);
            mTvAddress = view.findViewById(R.id.tv_address);
            mTvClassicfication = view.findViewById(R.id.tv_classicfication);
            mLlClassicfication = view.findViewById(R.id.ll_classicfication);
            mLlClassicfication.setVisibility(View.VISIBLE);
            mLlSex = view.findViewById(R.id.ll_sex);
            mTvChat=view.findViewById(R.id.tv_chat);
            mTvChat.setVisibility(View.GONE);
//            mLlSex.setVisibility(View.GONE);
        }

    }

    public String getPhoneNum(int position) {
        String phoneNum = datas.get(position).getPhone();
        return phoneNum;
    }


    public interface MyOnItemClickListener {
        void onItemClick(View view, int position);

        void onViewItemClick(View view, int position);
    }

    private MyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
