package com.gc.basicpublicdoctor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleHolder;
import com.gc.basicpublicdoctor.bean.ServiceContentBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/8/2 下午1:41
 * Description:This is SelectServicePackageListAdapter
 * 服务包项目列表适配器
 */
public class SelectServicePackageListAdapter extends BaseRecycleAdapter<ServiceContentBean> {

    public SelectServicePackageListAdapter(Context context, List<ServiceContentBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void bindView(BaseRecycleHolder baseRecycleHolder, final int position, final ServiceContentBean bean) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String price = df.format(bean.getExpenses() / 100);
        baseRecycleHolder.setTextViewText(R.id.tv_service_item, bean.getServiceItem())
                .setTextViewText(R.id.tv_unit, bean.getUnit())
                .setTextViewText(R.id.tv_project_content, bean.getProjectContent())
                .setTextViewText(R.id.tv_expenses, price + "元")
                .setOnCheckListener(R.id.cb_select, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        myOnCheckedChangeListener.onItemCheck(buttonView, position, isChecked);
                    }
                })
                .setCheckBox(R.id.cb_select, bean.isSelected());

        if (position == list.size() - 1) {
            baseRecycleHolder.setVisable(R.id.tv_line, View.GONE);
        }
    }

    @Override
    public void nowStyle(BaseRecycleHolder baseRecycleHolder) {

    }

    public interface MyOnCheckedChangeListener {
        void onItemCheck(View view, int position, boolean isCheck);
    }

    private MyOnCheckedChangeListener myOnCheckedChangeListener;

    public void setMyOnCheckedChangeListener(MyOnCheckedChangeListener myOnCheckedChangeListener) {
        this.myOnCheckedChangeListener = myOnCheckedChangeListener;
    }
}
