package com.gc.basicpublicdoctor.adapter;

import android.content.Context;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleHolder;
import com.gc.basicpublicdoctor.bean.SignFamilyListResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/27 下午1:40
 * Description:This is SignFamilyListAdapter
 * 签约家庭适配器
 */
public class SignFamilyListAdapter extends BaseRecycleAdapter<SignFamilyListResponse.DataBean.ListBean> {

    public SignFamilyListAdapter(Context context, List<SignFamilyListResponse.DataBean.ListBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void bindView(BaseRecycleHolder baseRecycleHolder, int position, final SignFamilyListResponse.DataBean.ListBean bean) {
        baseRecycleHolder.setTextViewText(R.id.tv_house_id, bean.getHouseholderIDCard())
                .setTextViewText(R.id.tv_house_name, bean.getHouseholderName())
                .setTextViewText(R.id.tv_family_members, bean.getFamilyMembers());

    }

    @Override
    public void nowStyle(BaseRecycleHolder baseRecycleHolder) {

    }
}
