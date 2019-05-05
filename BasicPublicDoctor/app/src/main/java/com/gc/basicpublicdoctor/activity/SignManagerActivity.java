package com.gc.basicpublicdoctor.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.SignManagerFragmentPageAdapter;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.fragment.SignManagerFragment;
import com.gc.basicpublicdoctor.fragment.SignManagerPersonalFragment;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/24 下午1:59
 * Description:This is SignManagerActivity
 * 签约列表页面
 */
public class SignManagerActivity extends BaseActivity {
    Context context;
    private View mView;
    private ImageView mIvLabel;
    /**
     * 请输入身份证/手机号/姓名
     */
    public EditText mEtSearch;
    private TabLayout mTablayout;
    private ViewPager mViewpager;

    private SignManagerFragmentPageAdapter adapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;
    /**
     * 签约管理刷新
     */
    public static final String SIGN_MANAGER_REFRESH = "SIGN_MANAGER_REFRESH";
    private MyBroadcastReceiver myBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_manager);
        context = this;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        showTitleName("签约列表", true);

        initView();
        initData();
        initFragment();

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SIGN_MANAGER_REFRESH);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    private void initView() {
        mView = (View) findViewById(R.id.view);
        mIvLabel = (ImageView) findViewById(R.id.iv_label);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mEtSearch.addTextChangedListener(new MyTextChangeListener());
        mEtSearch.setOnEditorActionListener(new SearchOnEditorActionListener());

        mTablayout.addTab(mTablayout.newTab());
        mTablayout.addTab(mTablayout.newTab());

        // mTablayout添加中间分隔线
        LinearLayout linearLayout = (LinearLayout) mTablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.shape_divider));
        linearLayout.setDividerPadding(DensityUtil.dip2px(0));
    }

    private void initData() {
        showRightBtn("开始签约", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] array = new String[]{"家庭", "个人"};
                new AlertDialog.Builder(context)
                        .setTitle("选择家庭或个人")
                        .setCancelable(false)
                        .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(context, SignActivity.class);
                                if (which == 0) {
                                    intent.putExtra("signType", "2");//家庭
                                } else if (which == 1) {
                                    intent.putExtra("signType", "1");//个人
                                }
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
             /*   final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("提示").setMessage("选择家庭或个人？")
                        .setPositiveButton("家庭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, SignActivity.class);
                                intent.putExtra("signType", "2");
                                startActivity(intent);
                            }
                        }).setNegativeButton("个人", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, SignActivity.class);
                                intent.putExtra("signType", "1");
                                startActivity(intent);
                            }
                        }).create();

                alertDialog.show();*/

            }
        });
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SIGN_MANAGER_REFRESH.equals(intent.getAction())) {
                ((SignManagerFragment) fragmentList.get(0)).refreshData();
                ((SignManagerPersonalFragment) fragmentList.get(1)).refreshData();
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
        fragmentList.add(SignManagerFragment.newInstance(0));
        fragmentList.add(SignManagerPersonalFragment.newInstance(1));

        fragmentManager = getSupportFragmentManager();
        adapter = new SignManagerFragmentPageAdapter(fragmentManager, fragmentList);
        mViewpager.setAdapter(adapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                } else {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTablayout.setupWithViewPager(mViewpager);

        mTablayout.getTabAt(0).setCustomView(tabIcon("家庭", 0));
        mTablayout.getTabAt(1).setCustomView(tabIcon("个人", 0));
    }

    private void searchInfo() {
        Fragment fragment = adapter.getItem(mViewpager.getCurrentItem());
        if (fragment instanceof SignManagerFragment) {
            ((SignManagerFragment) fragment).searchInfo();
        } else if (fragment instanceof SignManagerPersonalFragment) {
            ((SignManagerPersonalFragment) fragment).searchInfo();
        }
    }

    class MyTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int n = s.toString().trim().length();
            if (n >= 0) {
                searchInfo();
            }
        }
    }

    class SearchOnEditorActionListener implements EditText.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                KeyboardUtil.closeBoard(context);
                return false;
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
