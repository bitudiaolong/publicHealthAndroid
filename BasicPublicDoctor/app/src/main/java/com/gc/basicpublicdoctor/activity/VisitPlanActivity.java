package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.VisitPlanResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.HelpUtil;

import java.util.Map;

/**
 * Author:Created by zhurui
 * Time:2018/7/27 下午1:36
 * Description:This is VisitPlanActivity
 * 随访计划页面
 */
public class VisitPlanActivity extends BaseActivity implements DialogProgressBar.OnProgressListener, View.OnClickListener {

    DialogProgressBar progressBar;
    private Context context;
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
     * 保存
     */
    private TextView mRightTv;
    /**
     * +
     */
    private TextView mRightTv2;
    private ImageView mRightIamge;
    private RelativeLayout mLinRight;
    private RelativeLayout mToolBar;
    /**
     * 80
     */
    private TextView mTvNum1;
    private LinearLayout mLlLine1;
    /**
     * 80
     */
    private TextView mTvNum2;
    private LinearLayout mLlLine2;
    /**
     * 80
     */
    private TextView mTvNum3;
    private LinearLayout mLlLine3;
    /**
     * 80
     */
    private TextView mTvNum4;
    private LinearLayout mLlLine4;


    /**
     * 随访未进行、本日应随访、本周应随访、逾期随访
     */
    private int firstVisit;
    private int dayVisit;
    private int weekVisit;
    private int auditsVisit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_plan);
        context = this;
        showTitleName("随访计划", true);
        initView();
    }

    private void initView() {

        mView = (View) findViewById(R.id.view);
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
        mTvNum1 = (TextView) findViewById(R.id.tv_num_1);
        mLlLine1 = (LinearLayout) findViewById(R.id.ll_line_1);
        mLlLine1.setOnClickListener(this);
        mTvNum2 = (TextView) findViewById(R.id.tv_num_2);
        mLlLine2 = (LinearLayout) findViewById(R.id.ll_line_2);
        mLlLine2.setOnClickListener(this);
        mTvNum3 = (TextView) findViewById(R.id.tv_num_3);
        mLlLine3 = (LinearLayout) findViewById(R.id.ll_line_3);
        mLlLine3.setOnClickListener(this);
        mTvNum4 = (TextView) findViewById(R.id.tv_num_4);
        mLlLine4 = (LinearLayout) findViewById(R.id.ll_line_4);
        mLlLine4.setOnClickListener(this);

        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
        getVisitPlan();
    }

    /**
     * 随访记录查询
     *
     * @author zhurui
     * @time 2018/7/18 下午1:43
     */
    public void getVisitPlan() {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        progressBar.show();
        new HLHttpUtils().post(map, Cons.VISIT_PLAN()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<VisitPlanResponse>() {
            @Override
            public void onSuccess(VisitPlanResponse bean) {
                if (bean.getData() != null) {
                    firstVisit = bean.getData().getFirstVisit();
                    mTvNum1.setText(String.valueOf(firstVisit));
                    dayVisit = bean.getData().getDayVisit();
                    mTvNum2.setText(String.valueOf(dayVisit));
                    weekVisit = bean.getData().getWeekVisit();
                    mTvNum3.setText(String.valueOf(weekVisit));
                    auditsVisit = bean.getData().getAuditsVisit();
                    mTvNum4.setText(String.valueOf(auditsVisit));

                }
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);

            }

            @Override
            public void onFailure(String code, String errormsg) {
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                HelpUtil.showToast(context, errormsg);
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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_line_1:
                intent = new Intent(context, VisitPlanCrowdActivity.class);
                intent.putExtra("crowtype", "1");
                startActivity(intent);
                break;
            case R.id.ll_line_2:
                intent = new Intent(context, VisitPlanCrowdActivity.class);
                intent.putExtra("crowtype", "2");
                startActivity(intent);
                break;
            case R.id.ll_line_3:
                intent = new Intent(context, VisitPlanCrowdActivity.class);
                intent.putExtra("crowtype", "3");
                startActivity(intent);
                break;
            case R.id.ll_line_4:
                intent = new Intent(context, VisitPlanCrowdActivity.class);
                intent.putExtra("crowtype", "4");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

    }

}
