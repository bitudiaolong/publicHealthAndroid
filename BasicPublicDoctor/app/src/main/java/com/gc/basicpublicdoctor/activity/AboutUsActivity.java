package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.view.RoundImageView;
import com.gc.utils.AppUtils;

/**
 * Author:Created by zhurui
 * Time:2018/7/20 下午4:41
 * Description:This is AboutUsActivity
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {
    Context context;

    private View mView;
    /**
     * 版本号：1.0.0
     */
    private TextView mTvVersion;
    private RoundImageView roundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        context = this;

        showTitleName("关于我们", true);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        mView = (View) findViewById(R.id.view);
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        mTvVersion.setText("版本号：" + AppUtils.getVerName(context));

        roundImageView = (RoundImageView) findViewById(R.id.iv_logo);

        roundImageView
//                .setBorderWidth(3)≈
//                .setBorderColor(Color.RED)
                .setType(RoundImageView.TYPE_ROUND)
                .setLeftTopCornerRadius(10)
                .setRightTopCornerRadius(10)
                .setRightBottomCornerRadius(10)
                .setLeftBottomCornerRadius(10);
    }
}
