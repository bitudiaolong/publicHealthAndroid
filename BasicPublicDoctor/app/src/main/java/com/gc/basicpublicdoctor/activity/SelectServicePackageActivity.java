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

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.SignManagerFragmentPageAdapter;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.ServiceContentBean;
import com.gc.basicpublicdoctor.bean.ServicePackageListBean;
import com.gc.basicpublicdoctor.bean.ServicePackageResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.fragment.SelectServicePackageFragment;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.utils.HelpUtil;
import com.gc.utils.LogUtil;
import com.gc.utils.MyGagcLog;

import org.xutils.common.util.DensityUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:Created by zhurui
 * Time:2018/8/2 上午10:41
 * Description:This is SelectServicePackageActivity
 * 选择服务包页面
 */
public class SelectServicePackageActivity extends BaseActivity implements View.OnClickListener {
    Context context;
    private View mView;
    private ImageView mIvLabel;

    private TabLayout mTablayout;
    private ViewPager mViewpager;

    /**
     * 现场现金支付现场现金支付现场现金支付现场现金支付现场现金支付
     */
    private TextView mTvRemarks;
    private LinearLayout mLlRemarks;
    /**
     * ￥211.11
     */
    private TextView mTvTotalPrice;
    /**
     * 保    存
     */
    private TextView mTxtSubmit;
    private LinearLayout mLlBottom;

    private SignManagerFragmentPageAdapter adapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;

    List<String> servicePackageNameList = new ArrayList<>();
    List<String> servicePackageIdList = new ArrayList<>();
    List<ServicePackageListBean> servicePackageListBeanList = new ArrayList<>();
    List<ServiceContentBean> serviceContentBeanList = new ArrayList<>();

    // 选择的服务包和服务项列表
    private List<ServicePackageListBean> servicePackageListBeanListSelected = new ArrayList<>();

    // 返回的结果码
    private final static int REMARKSCODE = 10086;
    public final static int RESULTCODE = 10088;

    /**
     * 服务包选择广播
     */
    public static final String SELECT_SERVICE_PACKAGE = "SELECT_SERVICE_PACKAGE";
    MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_package);
        context = this;

        showTitleName("选择服务包", true);
        boolean isEdit = getIntent().getBooleanExtra("isEdit", false);

        getServicePackageList();
        initView();
        if (isEdit) {
            showData();
        }

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SELECT_SERVICE_PACKAGE);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    void showData() {
        mTvTotalPrice.setText(getIntent().getStringExtra("totalPrice"));
        mTvRemarks.setText(getIntent().getStringExtra("remarks"));
        servicePackageListBeanListSelected = (List<ServicePackageListBean>) getIntent().getSerializableExtra("serviceContentBeanList");
    }

    private void initView() {
        mView = (View) findViewById(R.id.view);
        mIvLabel = (ImageView) findViewById(R.id.iv_label);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mTvRemarks = (TextView) findViewById(R.id.tv_remarks);
        mLlRemarks = (LinearLayout) findViewById(R.id.ll_remarks);
        mLlRemarks.setOnClickListener(this);
        mTvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        mTxtSubmit = (TextView) findViewById(R.id.txt_submit);
        mTxtSubmit.setOnClickListener(this);
        mLlBottom = (LinearLayout) findViewById(R.id.ll_bottom);

    }

    private void initData() {
        int size = servicePackageNameList.size();
        for (int i = 0; i < size; i++) {
            mTablayout.addTab(mTablayout.newTab());
        }
//        mTablayout.addTab(mTablayout.newTab());
//        mTablayout.addTab(mTablayout.newTab());
//        mTablayout.addTab(mTablayout.newTab());

        // mTablayout添加中间分隔线
        LinearLayout linearLayout = (LinearLayout) mTablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.shape_divider));
        linearLayout.setDividerPadding(DensityUtil.dip2px(0));
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

    String servicePrice = "";

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.e("选择服务包广播==", "接收到广播" + intent.getAction());
            if (SELECT_SERVICE_PACKAGE.equals(intent.getAction())) {
                servicePackageListBeanListSelected.clear();
                long totalPrice = 0;
                for (ServicePackageListBean bean :
                        servicePackageListBeanList) {
                    List<ServiceContentBean> listTmp = new ArrayList<>();
                    long contntPrice = 0;
                    for (ServiceContentBean item :
                            bean.getServiceContent()) {
                        if (item.isSelected()) {
                            listTmp.add(item);
                            try {
                                contntPrice = contntPrice + item.getExpenses();
                                totalPrice = totalPrice + item.getExpenses();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (listTmp.size() > 0) {
                        ServicePackageListBean beanTmp = new ServicePackageListBean();
                        beanTmp.setServicePackageId(bean.getServicePackageId());
                        beanTmp.setServicePackageName(bean.getServicePackageName());
                        beanTmp.setTotalPrice(contntPrice);
                        beanTmp.setServiceContent(listTmp);
                        servicePackageListBeanListSelected.add(beanTmp);
                    }

                }
                DecimalFormat df = new DecimalFormat("#0.00");
                servicePrice = df.format(totalPrice / 100);
                mTvTotalPrice.setText("￥" + servicePrice);
            }
        }
    }

    private void initFragment() {
        int size = servicePackageNameList.size();
        for (int i = 0; i < size; i++) {
            SelectServicePackageFragment selectServicePackageFragment = new SelectServicePackageFragment();
            selectServicePackageFragment.setData(i, servicePackageListBeanList);
            fragmentList.add(selectServicePackageFragment);
        }
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

        for (int i = 0; i < size; i++) {
            mTablayout.getTabAt(i).setCustomView(tabIcon(servicePackageNameList.get(i), 0));
        }
//        mTablayout.getTabAt(0).setCustomView(tabIcon("待审核", 0));
//        mTablayout.getTabAt(1).setCustomView(tabIcon("待确认", 0));
//        mTablayout.getTabAt(2).setCustomView(tabIcon("已驳回", 0));
    }

    public void getServicePackageList() {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        new HLHttpUtils().post(map, Cons.GET_SERVICE_PACKAGE_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<ServicePackageResponse>() {
            @Override
            public void onSuccess(ServicePackageResponse bean) {
                if (bean.getData() == null) {
                    return;
                }
                ServicePackageResponse.DataBean dataBean = bean.getData();
                if (dataBean == null) {
                    return;
                }
                servicePackageListBeanList = dataBean.getServicePackageList();
                for (int i = 0; i < servicePackageListBeanList.size(); i++) {
                    servicePackageNameList.add(servicePackageListBeanList.get(i).getServicePackageName());
                    servicePackageIdList.add(servicePackageListBeanList.get(i).getServicePackageId());
                    if (servicePackageListBeanList.get(i).getServiceContent().size() > 0) {
                        serviceContentBeanList.addAll(servicePackageListBeanList.get(i).getServiceContent());
                    }
                    if (servicePackageListBeanListSelected.size() > 0) {
                        setPackageSelect(servicePackageListBeanList.get(i));
                    }
                }
                initData();
                initFragment();
            }

            @Override
            public void onFailure(String code, String errormsg) {

                MyGagcLog.getInstance().showLogE("errorcode[" + errormsg + "]errmsg[" + errormsg + "]");
                HelpUtil.showToast(context, errormsg);
            }

            @Override
            public void result(String result) {

            }
        });
    }


    void setPackageSelect(ServicePackageListBean bean) {
        for (ServicePackageListBean item : servicePackageListBeanListSelected) {
            if (item.getServicePackageId().equals(bean.getServicePackageId())) {
                setContentSelect(bean.getServiceContent(), item.getServiceContent());
            }
        }
    }

    void setContentSelect(List<ServiceContentBean> oldList, List<ServiceContentBean> selectList) {

        for (ServiceContentBean contentBeanOld : oldList) {
            for (ServiceContentBean contentBeanSelect : selectList) {
                if (contentBeanOld.getserviceId().equals(contentBeanSelect.getserviceId())) {
                    contentBeanOld.setSelected(true);
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_remarks:
                intent = new Intent(SelectServicePackageActivity.this, RemarkRecjectActivity.class);
                intent.putExtra("remarks", mTvRemarks.getText().toString().trim());
                intent.putExtra("entertype", "备注");
                startActivityForResult(intent, REMARKSCODE);
                break;
            case R.id.txt_submit:

                if (servicePackageListBeanListSelected.size() == 0) {
                    HelpUtil.showToast(context, "请选择服务包");
                    return;
                }

                intent = new Intent();
                intent.putExtra("serviceContentBeanList", (Serializable) servicePackageListBeanListSelected);
                intent.putExtra("remarks", mTvRemarks.getText().toString().trim());
                intent.putExtra("totalPrice", servicePrice);
                intent.setAction(SignActivity.PACKAGE_SELECT);
                sendBroadcast(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REMARKSCODE) {
            if (resultCode == RESULTCODE) {
                String remarks = data.getStringExtra("remarks");
                mTvRemarks.setText(remarks);
            }
        }
    }
}
