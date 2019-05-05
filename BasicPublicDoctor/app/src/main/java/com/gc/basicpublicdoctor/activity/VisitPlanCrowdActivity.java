package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.VisitPlanCrowdRecyclerAdapter;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.VisitPlanCrowdListResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.HelpUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:Created by zhurui
 * Time:2018/7/27 下午1:36
 * Description:This is VisitPlanCrowdActivity
 * 随访计划人群列表
 */
public class VisitPlanCrowdActivity extends BaseActivity implements DialogProgressBar.OnProgressListener, View.OnClickListener {

    private View mView;
    private TextView mLeftTv;
    /**
     * 返回
     */
    private TextView mBackBtn;
    private ImageView mLeftImage;
    private RelativeLayout mLinLeft;
    private ImageView mLeftImageOnResult;
    /**
     * 标题
     */
    private TextView mTvTitleBar;
    private ImageView mTitleImage;
    /**
     * 开始签约
     */
    private TextView mRightTv;
    /**
     * +
     */
    private TextView mRightTv2;
    private ImageView mRightIamge;
    private RelativeLayout mLinRight;
    private RelativeLayout mToolBar;

    private Context context;
    DialogProgressBar progressBar;

    /**
     * 人群列表的recyclerview
     */
    private String classification = "";
    private String crowType = "";
    private int currentPage = 1;
    private int totalPageCount = 0;
    private static final int pageSize = 20;


    private RecyclerView mRlCrowList;
    private VisitPlanCrowdRecyclerAdapter mVisitPlanCrowdRecyclerAdapter;
    private List<VisitPlanCrowdListResponse.DataBean.ListBean> listBeans = new ArrayList<VisitPlanCrowdListResponse.DataBean.ListBean>();
    private List<VisitPlanCrowdListResponse.DataBean.ListBean> lstTemp = new ArrayList<VisitPlanCrowdListResponse.DataBean.ListBean>();
    /**
     * 一般人群
     */
    private TextView mTvNormalCrowd;
    /**
     * 儿童
     */
    private TextView mTvChild;
    /**
     * 孕产妇
     */
    private TextView mTvMaternal;
    /**
     * 高血压
     */
    private TextView mTvHighBloodPressure;
    /**
     * 精神疾病
     */
    private TextView mTvMentalDisease;
    /**
     * 糖尿病
     */
    private TextView mTvDiabetes;
    /**
     * 脑卒中
     */
    private TextView mTvStroke;
    /**
     * 冠心病
     */
    private TextView mTvCoronaryHeartDisease;
    /**
     * 慢肺栓
     */
    private TextView mTvSlowLungSuppository;
    /**
     * 残疾人
     */
    private TextView mTvDisabled;
    /**
     * 建档立卡贫困户
     */
    private TextView mTvCreateProfileLowIncome;
    /**
     * 卡外贫困户
     */
    private TextView mTvBeyondProfileLowIncome;
    /**
     * 计划生育特殊家庭
     */
    private TextView mTvSpecialBirthPlanFamily;

    private int[] colors = {R.color.colorPrimary};


    private int[] crowdIds = {R.id.tv_normal_crowd, R.id.tv_child, R.id.tv_maternal, R.id.tv_high_blood_pressure, R.id.tv_mental_disease,
            R.id.tv_diabetes, R.id.tv_stroke, R.id.tv_coronary_heart_disease, R.id.tv_slow_lung_suppository,
            R.id.tv_disabled, R.id.tv_create_profile_low_income, R.id.tv_beyond_profile_low_income, R.id.tv_special_birth_plan_family,
            R.id.tv_old_people, R.id.tv_dibaohu, R.id.tv_wubaohu,R.id.tv_phthisis,R.id.tv_respiratory_disease};
    private int[] crowdCornerIds = {R.id.iv_normal_crowd_corner, R.id.iv_child_corner, R.id.iv_maternal_corner,
            R.id.iv_high_blood_pressure_corner, R.id.iv_mental_disease, R.id.iv_diabetes_corner, R.id.iv_stroke_corner,
            R.id.iv_coronary_heart_disease_corner, R.id.iv_slow_lung_suppository_corner, R.id.iv_disabled_corner, R.id.iv_create_profile_low_income_corner,
            R.id.iv_beyond_profile_low_income_corner, R.id.iv_special_birth_plan_family_corner,
            R.id.iv_old_people, R.id.iv_dibaohu, R.id.iv_wubaohu,R.id.iv_phthisis_corner,R.id.iv_respiratory_disease};
    private String classficationTemp = "";
    private TextView deftv;
    private ImageView defiv;
    private String[] crowdStrings = {"一般人群", "儿童", "孕产妇", "高血压", "严重精神障碍", "糖尿病", "脑卒中", "冠心病", "慢阻肺",
            "残疾人", "建档立卡贫困户", "卡外贫困", "计划生育特殊家庭", "老年人", "低保户", "五保户","肺结核","慢性呼吸道疾病"};


    /**
     * 重置
     */
    private Button mBtnReset;
    /**
     * 确认
     */
    private Button mBtnConfirm;
    private NavigationView mNavView;
    private DrawerLayout mDrawerLayout;

    private ImageView mIvNormalCrowdCorner;
    private ImageView mIvChildCorner;
    private ImageView mIvMaternalCorner;
    private ImageView mIvHighBloodPressureCorner;
    private ImageView mIvMentalDiseaseCorner;
    private ImageView mIvDiabetesCorner;
    private ImageView mIvStrokeCorner;
    private ImageView mIvCoronaryHeartDiseaseCorner;
    private ImageView mIvSlowLungSuppositoryCorner;
    private ImageView mIvDisabledCorner;
    private ImageView mIvCreateProfileLowIncomeCorner;
    private ImageView mIvBeyondProfileLowIncomeCorner;
    private ImageView mIvSpecialBirthPlanFamilyCorner;
    private SmartRefreshLayout mSmartRefreshLayout;
    private LinearLayout mLinEmpty;
    /**
     * 老年人
     */
    private TextView mTvOldPeople;
    private ImageView mIvOldPeople;
    /**
     * 低保户
     */
    private TextView mTvDibaohu;
    private ImageView mIvDibaohu;
    /**
     * 五保户
     */
    private TextView mTvWubaohu;
    private ImageView mIvWubaohu;
    /**
     * 肺结核
     */
    private TextView mTvPhthisis;
    private ImageView mIvPhthisisCorner;
    /**
     * 慢性呼吸道疾病
     */
    private TextView mTvRespiratoryDisease;
    private ImageView mIvRespiratoryDisease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_plan_crowd);
        context = this;

        Intent intent = getIntent();
        crowType = intent.getStringExtra("crowtype");

        showTitleName("人群列表", true);


        initView();
        initScreen();
        initData();


    }

    private void initView() {
        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
        mView = (View) findViewById(R.id.view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        /**
         * 人群分类页面 drawerlayout未弹出之前
         */
        mLeftTv = (TextView) findViewById(R.id.left_tv);
        mBackBtn = (TextView) findViewById(R.id.back_btn);
        mLeftImage = (ImageView) findViewById(R.id.left_Image);
        mLinLeft = (RelativeLayout) findViewById(R.id.lin_left);
        mLeftImageOnResult = (ImageView) findViewById(R.id.left_Image_onResult);
        mTvTitleBar = (TextView) findViewById(R.id.tv_title_bar);
        mTitleImage = (ImageView) findViewById(R.id.title_Image);
        mRightTv = (TextView) findViewById(R.id.Right_tv);
        mRightTv2 = (TextView) findViewById(R.id.Right_tv2);
        mRightIamge = (ImageView) findViewById(R.id.Right_Iamge);
        mLinRight = (RelativeLayout) findViewById(R.id.lin_right);
        mToolBar = (RelativeLayout) findViewById(R.id.tool_bar);
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smart_refresh_layout);
        mRlCrowList = (RecyclerView) findViewById(R.id.rl_crow_list);
        mLinEmpty = (LinearLayout) findViewById(R.id.lin_empty);


        /**
         * 抽屉页
         */
        mNavView = (NavigationView) findViewById(R.id.nav_view);

        /**+9
         * 人群分类类型 Tv以及右下角corner
         */
        mTvNormalCrowd = (TextView) findViewById(R.id.tv_normal_crowd);
        mTvChild = (TextView) findViewById(R.id.tv_child);
        mTvMaternal = (TextView) findViewById(R.id.tv_maternal);
        mTvHighBloodPressure = (TextView) findViewById(R.id.tv_high_blood_pressure);
        mTvMentalDisease = (TextView) findViewById(R.id.tv_mental_disease);
        mTvDiabetes = (TextView) findViewById(R.id.tv_diabetes);
        mTvStroke = (TextView) findViewById(R.id.tv_stroke);
        mTvCoronaryHeartDisease = (TextView) findViewById(R.id.tv_coronary_heart_disease);
        mTvSlowLungSuppository = (TextView) findViewById(R.id.tv_slow_lung_suppository);
        mTvDisabled = (TextView) findViewById(R.id.tv_disabled);
        mTvCreateProfileLowIncome = (TextView) findViewById(R.id.tv_create_profile_low_income);
        mTvBeyondProfileLowIncome = (TextView) findViewById(R.id.tv_beyond_profile_low_income);
        mTvSpecialBirthPlanFamily = (TextView) findViewById(R.id.tv_special_birth_plan_family);

        mIvNormalCrowdCorner = (ImageView) findViewById(R.id.iv_normal_crowd_corner);
        mIvChildCorner = (ImageView) findViewById(R.id.iv_child_corner);
        mIvMaternalCorner = (ImageView) findViewById(R.id.iv_maternal_corner);
        mIvHighBloodPressureCorner = (ImageView) findViewById(R.id.iv_high_blood_pressure_corner);
        mIvMentalDiseaseCorner = (ImageView) findViewById(R.id.iv_mental_disease);
        mIvDiabetesCorner = (ImageView) findViewById(R.id.iv_diabetes_corner);
        mIvStrokeCorner = (ImageView) findViewById(R.id.iv_stroke_corner);
        mIvCoronaryHeartDiseaseCorner = (ImageView) findViewById(R.id.iv_coronary_heart_disease_corner);
        mIvSlowLungSuppositoryCorner = (ImageView) findViewById(R.id.iv_slow_lung_suppository_corner);
        mIvDisabledCorner = (ImageView) findViewById(R.id.iv_disabled_corner);
        mIvCreateProfileLowIncomeCorner = (ImageView) findViewById(R.id.iv_create_profile_low_income_corner);
        mIvBeyondProfileLowIncomeCorner = (ImageView) findViewById(R.id.iv_beyond_profile_low_income_corner);
        mIvSpecialBirthPlanFamilyCorner = (ImageView) findViewById(R.id.iv_special_birth_plan_family_corner);
        mTvOldPeople = (TextView) findViewById(R.id.tv_old_people);
        mIvOldPeople = (ImageView) findViewById(R.id.iv_old_people);
        mTvDibaohu = (TextView) findViewById(R.id.tv_dibaohu);
        mIvDibaohu = (ImageView) findViewById(R.id.iv_dibaohu);
        mTvWubaohu = (TextView) findViewById(R.id.tv_wubaohu);
        mIvWubaohu = (ImageView) findViewById(R.id.iv_wubaohu);
        mTvPhthisis = (TextView) findViewById(R.id.tv_phthisis);
        mIvPhthisisCorner = (ImageView) findViewById(R.id.iv_phthisis_corner);
        mTvRespiratoryDisease = (TextView) findViewById(R.id.tv_respiratory_disease);
        mIvRespiratoryDisease = (ImageView) findViewById(R.id.iv_respiratory_disease);


        setCrowdList(mTvNormalCrowd, mIvNormalCrowdCorner);
        setCrowdList(mTvChild, mIvChildCorner);
        setCrowdList(mTvMaternal, mIvMaternalCorner);
        setCrowdList(mTvHighBloodPressure, mIvHighBloodPressureCorner);
        setCrowdList(mTvMentalDisease, mIvMentalDiseaseCorner);
        setCrowdList(mTvDiabetes, mIvDiabetesCorner);
        setCrowdList(mTvStroke, mIvStrokeCorner);
        setCrowdList(mTvCoronaryHeartDisease, mIvCoronaryHeartDiseaseCorner);
        setCrowdList(mTvSlowLungSuppository, mIvSlowLungSuppositoryCorner);
        setCrowdList(mTvOldPeople, mIvOldPeople);
        setCrowdList(mTvWubaohu, mIvWubaohu);
        setCrowdList(mTvDibaohu, mIvDibaohu);
        setCrowdList(mTvDisabled, mIvDisabledCorner);
        setCrowdList(mTvCreateProfileLowIncome, mIvCreateProfileLowIncomeCorner);
        setCrowdList(mTvBeyondProfileLowIncome, mIvBeyondProfileLowIncomeCorner);
        setCrowdList(mTvSpecialBirthPlanFamily, mIvSpecialBirthPlanFamilyCorner);
        setCrowdList(mTvPhthisis,mIvPhthisisCorner);
        setCrowdList(mTvRespiratoryDisease,mIvRespiratoryDisease);


        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);

        getVisitPlanCrowdList(classification, crowType, true);
        if (listBeans == null) {

        }



    }


    private void initData() {
        showRightImage(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mNavView);
            }
        });

        mRlCrowList.setLayoutManager(new LinearLayoutManager(this));
        mVisitPlanCrowdRecyclerAdapter = new VisitPlanCrowdRecyclerAdapter(context, listBeans);
        mRlCrowList.setAdapter(mVisitPlanCrowdRecyclerAdapter);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                currentPage++;
                if (currentPage <= totalPageCount) {
                    getVisitPlanCrowdList(classification, crowType, false);
                } else {
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                }


            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                currentPage = 1;
                mSmartRefreshLayout.setNoMoreData(true);
                getVisitPlanCrowdList(classification, crowType, true);

            }
        });


        mVisitPlanCrowdRecyclerAdapter.setOnItemClickListener(new VisitPlanCrowdRecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                callPhone(mVisitPlanCrowdRecyclerAdapter.getPhoneNum(position));
            }

            @Override
            public void onViewItemClick(View view, int position) {
            }
        });


    }

    private void initScreen() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < crowdIds.length; i++) {
                    deftv = findViewById(crowdIds[i]);
                    if (deftv.isSelected()) {
                        if (TextUtils.isEmpty(classficationTemp)) {
                            classficationTemp = crowdStrings[i];
                        } else {
                            classficationTemp = crowdStrings[i] + "," + classficationTemp;
                        }

                    }
                }
                currentPage=1;
                getVisitPlanCrowdList(classficationTemp, crowType, true);
                mVisitPlanCrowdRecyclerAdapter.notifyDataSetChanged();
                classficationTemp = "";
                mDrawerLayout.closeDrawer(mNavView);
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < crowdIds.length; i++) {
                    deftv = findViewById(crowdIds[i]);
                    deftv.setSelected(false);
                    defiv = findViewById(crowdCornerIds[i]);
                    defiv.setVisibility(View.GONE);
                }
                classification = "";
            }
        });

    }


    private void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }


    /**
     * 随访计划人群列表查询
     */
    public void getVisitPlanCrowdList(String classification, String crowType, final boolean isResresh) {

        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("classification", classification);
        map.put("crowdType", crowType);
        map.put("currentPage", String.valueOf(currentPage));
        map.put("pageSize", String.valueOf(pageSize));
        progressBar.show();
        new HLHttpUtils().post(map, Cons.CROWD_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<VisitPlanCrowdListResponse>() {
            @Override
            public void onSuccess(VisitPlanCrowdListResponse bean) {
                if (bean.getData() != null) {
                    if (isResresh) {
                        mSmartRefreshLayout.finishRefresh(true);
                        mSmartRefreshLayout.setNoMoreData(false);
                        listBeans.clear();
                    } else {
                        mSmartRefreshLayout.finishLoadMore(true);
                    }
                }
                if (bean.getData().getPage() != null) {
                    int currentpageTmp = Integer.parseInt(bean.getData().getPage().getCurrentPage());
                    int totalpageCountTemp = bean.getData().getPage().getTotalPageCount();
                    currentPage = currentpageTmp;
                    totalPageCount = totalpageCountTemp;
                }
                if (bean.getData().getList() != null) {
                    mLinEmpty.setVisibility(View.GONE);
                    mRlCrowList.setVisibility(View.VISIBLE);
                    listBeans.addAll(bean.getData().getList());
                    mVisitPlanCrowdRecyclerAdapter.notifyDataSetChanged();

                }
                if (bean.getData().getList().size() == 0) {
                    mLinEmpty.setVisibility(View.VISIBLE);
                    mRlCrowList.setVisibility(View.GONE);
                }


                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
            }

            @Override
            public void onFailure(String code, String errormsg) {
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                HelpUtil.showToast(context, errormsg);
                if (isResresh) {
                    mSmartRefreshLayout.finishRefresh(false);
                } else {
                    mSmartRefreshLayout.finishLoadMore(false);
                }
            }

            @Override
            public void result(String result) {

            }
        });


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Cons.LOADING_DISMISS:
                    if (progressBar != null) {
                        progressBar.misDialog();
                    }
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

    }

    /**
     * @param tv  当前textview
     * @param tvc textview右下角的corner
     */
    public void setCrowdList(final TextView tv, final ImageView tvc) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv.isSelected()) {
                    tv.setSelected(false);
                    tvc.setVisibility(View.GONE);
                } else {
                    tv.setSelected(true);
                    tvc.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}
