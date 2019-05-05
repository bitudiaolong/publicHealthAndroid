package com.gc.basicpublicdoctor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.SignManagerFragmentPageAdapter;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.fragment.SignExaminePersonalFragment;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/8/6 上午10:54
 * Description:This is SignExamineActivity
 * 签约审核列表页面
 */
public class SignExamineActivity extends BaseActivity {
    Context context;
    private View mView;
    private ImageView mIvLabel;

    private TabLayout mTablayout;
    private ViewPager mViewpager;

    private SignManagerFragmentPageAdapter adapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;

    /**
     * 签约审核刷新广播
     */
    public static final String SIGN_EXAMINE_REFRESH = "SIGN_EXAMINE_REFRESH";
    private MyBroadcastReceiver myBroadcastReceiver;

    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_examine);
        context = this;

        showTitleName("签约审核", true);

        initView();
        initData();
        initFragment();

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SIGN_EXAMINE_REFRESH);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    private void initView() {
        mView = (View) findViewById(R.id.view);
        mIvLabel = (ImageView) findViewById(R.id.iv_label);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mTablayout.addTab(mTablayout.newTab());
        mTablayout.addTab(mTablayout.newTab());
        mTablayout.addTab(mTablayout.newTab());

        // mTablayout添加中间分隔线
        LinearLayout linearLayout = (LinearLayout) mTablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.shape_divider));
        linearLayout.setDividerPadding(DensityUtil.dip2px(0));
    }

    private void initData() {
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SIGN_EXAMINE_REFRESH.equals(intent.getAction())) {
                 ((SignExaminePersonalFragment) fragmentList.get(currentPage)).refreshData();
//                ((SignExaminePersonalFragment) fragmentList.get(1)).refreshData();
//                ((SignExaminePersonalFragment) fragmentList.get(2)).refreshData();
            }
        }
    }

    private View tabIcon(String name, int resId) {
        View newTab = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView tv = (TextView) newTab.findViewById(R.id.tv_label);
//        ImageView iv = (ImageView) newTab.findViewById(R.id.iv_label);
        tv.setText(name);
//        if (resId != 0) {
//            iv.setImageResource(resId);
//        }
        return newTab;
    }

    private void initFragment() {
        fragmentList.add(SignExaminePersonalFragment.newInstance(0));
        fragmentList.add(SignExaminePersonalFragment.newInstance(1));
        fragmentList.add(SignExaminePersonalFragment.newInstance(2));

        fragmentManager = getSupportFragmentManager();
        adapter = new SignManagerFragmentPageAdapter(fragmentManager, fragmentList);
        mViewpager.setAdapter(adapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                ((SignExaminePersonalFragment) fragmentList.get(position)).refreshData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTablayout.setupWithViewPager(mViewpager);

        mTablayout.getTabAt(0).setCustomView(tabIcon("待审核", 0));
        mTablayout.getTabAt(1).setCustomView(tabIcon("待确认", 0));
        mTablayout.getTabAt(2).setCustomView(tabIcon("已驳回", 0));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
