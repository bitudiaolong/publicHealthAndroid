package com.gc.basicpublicdoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleHolder;
import com.gc.basicpublicdoctor.bean.SignPersonalListResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/30 上午10:09
 * Description:This is SignPersonalListAdapter
 * 签约个人适配器
 */
public class SignPersonalListAdapter extends BaseRecycleAdapter<SignPersonalListResponse.DataBean.ListBean> {

    public SignPersonalListAdapter(Context context, List<SignPersonalListResponse.DataBean.ListBean> list, int layoutId, boolean isChat) {
        super(context, list, layoutId, isChat);
    }

    @Override
    public void bindView(BaseRecycleHolder baseRecycleHolder, final int position, final SignPersonalListResponse.DataBean.ListBean bean) {
        if (isChat) {
            baseRecycleHolder.setVisable(R.id.tv_chat, View.VISIBLE)
                    .setOnClickListener(R.id.tv_chat, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myChatOnItemClickListener.onItemChat(v, position);
                        }
                    });
        }
        baseRecycleHolder.setTextViewText(R.id.tv_name, bean.getName())
                .setTextViewText(R.id.tv_sex, bean.getSex())
                .setTextViewText(R.id.tv_age, bean.getAge() + "岁")
                .setTextViewText(R.id.tv_phone_number, bean.getPhone())
                .setTextViewText(R.id.tv_address, bean.getAddress())
                .setOnClickListener(R.id.tv_phone_number, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callPhone(bean.getPhone());
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

    private void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
