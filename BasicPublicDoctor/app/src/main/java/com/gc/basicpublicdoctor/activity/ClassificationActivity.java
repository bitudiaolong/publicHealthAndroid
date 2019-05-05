package com.gc.basicpublicdoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.widget.ClassificationItemLayout;
import com.gc.utils.HelpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.lankton.flowlayout.FlowLayout;

/**
 * 人群分类选择
 */
public class ClassificationActivity extends BaseActivity implements View.OnClickListener {


    private FlowLayout mFlowLayout;
    /**
     * 提交
     */
    private TextView mTxtSubmit;

    List<ClassificationItemLayout> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        showTitleName("选择人群分类", true);
        lst = new ArrayList<>();
        initView();
        initData();
    }


    private void initView() {

        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        List<String> lstData = Arrays.asList(getResources().getStringArray(R.array.classification));
        for (String item : lstData) {
            ClassificationItemLayout classificationItemLayout = new ClassificationItemLayout(context);
            classificationItemLayout.setTitleName(item);
            classificationItemLayout.setOnCheck(new OnItemSelect());
            mFlowLayout.addView(classificationItemLayout);

            lst.add(classificationItemLayout);
        }

        mTxtSubmit = (TextView) findViewById(R.id.txt_submit);
        mTxtSubmit.setOnClickListener(this);
        boolean isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> listSlect = getIntent().getStringArrayListExtra("clow");
                    for (String item : listSlect) {
                        for (ClassificationItemLayout bean : lst) {
                            if (item.equals(bean.getTitleName())) {
                                bean.setSelect(true);
                            }
                        }
                    }
                }
            },100);

        }
    }

    void initData() {

    }

    class OnItemSelect implements ClassificationItemLayout.OnItemCheck {

        @Override
        public void onCheck(String name, boolean isCheck) {
            if ("一般人群".equals(name)) {
                for (ClassificationItemLayout item : lst) {
                    if ("一般人群".equals(item.getTitleName()) == false) {
                        item.setSelectEnable(!isCheck);
                    }
                }
            } else {
                boolean flag = false;
                for (ClassificationItemLayout item : lst) {
                    if ("一般人群".equals(item.getTitleName()) == false) {
                        if (item.isSelect()) {
                            flag = true;
                            break;
                        }
                    }
                }
                lst.get(0).setSelectEnable(!flag);
            }

            if ("儿童".equals(name)) {
                for (ClassificationItemLayout item : lst) {
                    if ("孕产妇".equals(item.getTitleName()) || "老年人".equals(item.getTitleName())) {
                        item.setSelectEnable(!isCheck);
                    }
                }
            }

            if ("孕产妇".equals(name) || "老年人".equals(name)) {
                for (ClassificationItemLayout item : lst) {
                    if ("儿童".equals(item.getTitleName())) {
                        item.setSelectEnable(!isCheck);
                    }
                }
            }

            if ("建档立卡贫困户".equals(name)) {
                for (ClassificationItemLayout item : lst) {
                    if ("卡外贫困户".equals(item.getTitleName())) {
                        item.setSelectEnable(!isCheck);
                    }
                }
            }
            if ("卡外贫困户".equals(name)) {
                for (ClassificationItemLayout item : lst) {
                    if ("建档立卡贫困户".equals(item.getTitleName())) {
                        item.setSelectEnable(!isCheck);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.txt_submit:
                int count = mFlowLayout.getChildCount();
                ArrayList<String> lstSelect = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    ClassificationItemLayout view = (ClassificationItemLayout) mFlowLayout.getChildAt(i);
                    if (view.isSelect()) {
                        lstSelect.add(view.getTitleName());
                    }
                }
                if (lstSelect.size() == 0) {
                    HelpUtil.showToast(context, "请选择人群分类");
                    return;
                }
                Intent intent = new Intent();
                intent.setAction(SignActivity.CLOW_SELECT);
                intent.putStringArrayListExtra("clow", lstSelect);
                sendBroadcast(intent);
                finish();
                break;
        }
    }


}
