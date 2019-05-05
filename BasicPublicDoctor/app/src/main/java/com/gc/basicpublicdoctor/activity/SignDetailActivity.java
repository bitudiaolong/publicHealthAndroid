package com.gc.basicpublicdoctor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gagc.httplibrary.BaseResponse;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.ServiceContentBean;
import com.gc.basicpublicdoctor.bean.ServicePackageListBean;
import com.gc.basicpublicdoctor.bean.SignDetailResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.PictureListLayout;
import com.gc.basicpublicdoctor.widget.SignInfoAddLayout;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.DateTimeUtil;
import com.gc.utils.GsonUtil;
import com.gc.utils.HelpUtil;
import com.gc.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.lankton.flowlayout.FlowLayout;
import cn.qqtheme.framework.picker.DatePicker;

public class SignDetailActivity extends BaseActivity implements View.OnClickListener, DialogProgressBar.OnProgressListener {

    public static final String BOHUI = "BOHUI";
    /**
     * 正在请求...
     */
    private TextView mTxtTips;
    private LinearLayout mLlEmpty;
    /**
     * 家庭医生：乔爱华
     */
    private TextView mTxtDoctorName;
    /**
     * 团队成员：黄球
     */
    private TextView mTxtDoctorTuanduichengyuan;
    /**
     * 联系电话：18067885988
     */
    private TextView mTxtDoctorPhone;
    /**
     * 服务机构：河海街道社区服务中心
     */
    private TextView mTxtDoctorJigou;
    private SignInfoAddLayout mSignInfoBase;
    /**
     * 姓名：张三
     */
    private TextView mTxtUserBaseName;
    /**
     * 性别：男
     */
    private TextView mTxtUserBaseSex;
    /**
     * 出生日期：dddd
     */
    private TextView mTxtUserBaseBirthday;
    /**
     * 姓名：张三
     */
    private TextView mTxtUserBaseGuanxi;
    private LinearLayout mLinUserBaseInfo;
    private SignInfoAddLayout mSignInfoClow;
    private FlowLayout mFlowLayoutClow;
    private LinearLayout mLinSignInfoClow;
    private SignInfoAddLayout mSignInfoServerPak;
    private View mViewLineHeader;
    private LinearLayout mLlPackageCon;
    /**
     * 备注
     */
    private TextView mTvRemarkStorage;
    private TextView mTxtRemarks;
    private RelativeLayout mLlRemarks;
    private View mViewLineFoot;
    private LinearLayout mLinSignInfoServerPak;
    private ImageView ivTimeArrow;
    /**
     * 签约时间
     */
    private TextView mTxt1;
    /**
     * 2018-02-03
     */
    private TextView mTxtSignDate;
    private RelativeLayout mRelSignDate;
    private PictureListLayout mTakePicture;
    private ImageView mImgUserSign;
    private LinearLayout mLinSignArea;
    /**
     * 保存
     */
    private TextView mTxtSubmit;
    private LinearLayout mLlMain;

    SignDetailResponse signDetailResponse;
    DialogProgressBar progressBar;
    String signId;
    String signState;

    /**
     * 是否选择人群分类
     */
    boolean hasClow = false;
    /**
     * 是否选择服务包
     */
    boolean hasPackage = false;

    private LinearLayout mLinTakePicture;
    private LinearLayout mLinSign;
    private LinearLayout mLinDoctorSelect;
    private LinearLayout mLinSelectDate;
    private View mView1;
    private View mView2;
    private TextView mTxtBohui;
    private LinearLayout mLinBohui;
    String signType = "1";
    String doType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail);
        initIntent();
        initView();
        initData();
        signDetail();
        initBoradcast();
        setSignState();
    }

    void initData() {
        setTeamInfo(Cons.userName, Cons.docTeamName, Cons.userPhone, Cons.healthRoomName);
        mSignInfoBase.setTitle("居民基本信息");
        mSignInfoClow.setTitle("所属人群");
        mSignInfoServerPak.setTitle("服务包");
        mTxtSignDate.setText(DateTimeUtil.date2String(System.currentTimeMillis(), "yyyy-MM-dd"));
    }

    void initBoradcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SignActivity.USER_SIGN_BACK);
        intentFilter.addAction(SignActivity.USER_ADD);
        intentFilter.addAction(SignActivity.CLOW_SELECT);
        intentFilter.addAction(SignActivity.PACKAGE_SELECT);
        intentFilter.addAction(SignExamineActivity.SIGN_EXAMINE_REFRESH);
        intentFilter.addAction(SignDetailActivity.BOHUI);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SignDetailActivity.BOHUI.equals(intent.getAction())) {
                String rejectReason = intent.getStringExtra("rejectReason");
                mSignInfoBase.setTextViewReadShow(new OnReadClickListener());
                mSignInfoBase.setBtnGone();
                mTxtSubmit.setVisibility(View.GONE);
                mLinSignInfoClow.setVisibility(View.GONE);
                mLinSelectDate.setVisibility(View.GONE);
                mLinTakePicture.setVisibility(View.GONE);
                mLinSign.setVisibility(View.GONE);
                mView2.setVisibility(View.GONE);
                mLinSignInfoServerPak.setVisibility(View.GONE);
                mLinBohui.setVisibility(View.VISIBLE);
                mTxtBohui.setText("驳回原因：" + rejectReason);

            }

            if (SignActivity.CLOW_SELECT.equals(intent.getAction())) {
                ArrayList<String> list = intent.getStringArrayListExtra("clow");
                signDetailResponse.getData().setClassificationList(list);
                showClow();
            }
            if (SignActivity.PACKAGE_SELECT.equals(intent.getAction())) {
                List<ServicePackageListBean> packageSelected = (List<ServicePackageListBean>) intent.getSerializableExtra("serviceContentBeanList");
                signDetailResponse.getData().setServicePackageList(packageSelected);
                signDetailResponse.getData().setRemarks(intent.getStringExtra("remarks"));
                showPackage();
            }

        }
    };

    String serviceTotalPrice;

    void showPackage() {
        mSignInfoServerPak.setBtnName("修改");
        mLlPackageCon.removeAllViews();
        if (TextUtils.isEmpty(signDetailResponse.getData().getRemarks()))
            mLlRemarks.setVisibility(View.GONE);
        else
            mLlRemarks.setVisibility(View.VISIBLE);
        mTxtRemarks.setText(signDetailResponse.getData().getRemarks());
        if (TextUtils.isEmpty(signDetailResponse.getData().getRemarks()) == false) {
            mLlRemarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignDetailActivity.this, RemarkRecjectActivity.class);
                    intent.putExtra("entertype", "备注");
                    intent.putExtra("remarks", signDetailResponse.getData().getRemarks());
                    intent.putExtra("isClicked", false);
                    startActivity(intent);
                }
            });
        }
        mViewLineHeader.setVisibility(View.VISIBLE);
        mViewLineFoot.setVisibility(View.VISIBLE);
        long serviceTotal = 0l;
        for (ServicePackageListBean bean : signDetailResponse.getData().getServicePackageList()) {
            View view = LayoutInflater.from(context).inflate(R.layout.select_package_layout_item, mLlPackageCon, false);
            TextView textViewName = view.findViewById(R.id.txt_package_name);
            TextView textViewPrice = view.findViewById(R.id.txt_price);
            textViewName.setText(bean.getServicePackageName());
            long expenses = 0L;
            for (ServiceContentBean item : bean.getServiceContent()) {
                expenses = expenses + item.getExpenses();
            }
            serviceTotal = serviceTotal + expenses;
            DecimalFormat df = new DecimalFormat("#0.00");
            textViewPrice.setText("￥" + df.format(expenses / 100));
            mLlPackageCon.addView(view);
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        serviceTotalPrice = df.format(serviceTotal / 100d);
        mSignInfoServerPak.setPrice(serviceTotalPrice);
        hasPackage = true;
    }

    void showClow() {
        mSignInfoClow.setBtnName("修改");
        mFlowLayoutClow.removeAllViews();
        mFlowLayoutClow.setVisibility(View.VISIBLE);
        for (String item : signDetailResponse.getData().getClassificationList()) {
            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.select_package_item, mFlowLayoutClow, false);
            textView.setText(item);
            mFlowLayoutClow.addView(textView);
        }
        hasClow = true;
    }

    void initIntent() {
        signId = getIntent().getStringExtra("signId");//必填
        signState = getIntent().getStringExtra("signState");//必填
        //（0待签约 1已签约 2已解约 9已过期 11 待审核 12 待确认 13 已驳回）
        if ("1".equals(signState.trim()) || "9".equals(signState.trim())
                || "已签约".equals(signState.trim()) || "已过期".equals(signState.trim())) {
            showTitleName("签约详情", true);
        } else {
            showTitleName("审核详情", true);
        }
//        else if ("11".equals(signState) || "12".equals(signState)) {
//            showTitleName("审核详情", true);
//        } else if ("13".equals(signState) || "已驳回".equals(signState)) {
//            showTitleName("审核详情", true);
//        }

    }

    void setSignState() {
        if ("1".equals(signState.trim()) || "9".equals(signState.trim())
                || "已签约".equals(signState.trim()) || "已过期".equals(signState.trim())) {
            mSignInfoBase.setBtnGone();
            mSignInfoClow.setBtnGone();
            mSignInfoServerPak.setBtnGone();
            mRelSignDate.setClickable(false);
            mTxtSubmit.setVisibility(View.GONE);
            ivTimeArrow.setVisibility(View.GONE);
            mLinSignArea.setClickable(false);
        } else if ("11".equals(signState.trim()) || "待审核".equals(signState.trim())) {
            mSignInfoBase.setBtnName("驳回");
            mSignInfoBase.setTextViewReadShow(new OnReadClickListener());
            mLinTakePicture.setVisibility(View.GONE);
            mLinSign.setVisibility(View.GONE);
            mTxtSubmit.setText("提交");
            mRelSignDate.setClickable(true);
            mRelSignDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDate();
                }
            });

        } else if ("13".equals(signState.trim()) || "已驳回".equals(signState.trim())) {
            mSignInfoBase.setTextViewReadShow(new OnReadClickListener());
            mSignInfoBase.setBtnGone();
            mTxtSubmit.setVisibility(View.GONE);
            mLinSignInfoClow.setVisibility(View.GONE);
            mLinSelectDate.setVisibility(View.GONE);
            mLinTakePicture.setVisibility(View.GONE);
            mLinSign.setVisibility(View.GONE);
            mView2.setVisibility(View.GONE);
            mLinSignInfoServerPak.setVisibility(View.GONE);
            mLinBohui.setVisibility(View.VISIBLE);
        } else if ("12".equals(signState.trim()) || "待确认".equals(signState.trim())) {
            mSignInfoBase.setBtnGone();
            mSignInfoBase.setTextViewReadShow(new OnReadClickListener());
            mLinTakePicture.setVisibility(View.GONE);
            mLinSign.setVisibility(View.GONE);
            mSignInfoClow.setBtnGone();
            ivTimeArrow.setVisibility(View.GONE);
            mSignInfoServerPak.setBtnGone();
            mTxtSubmit.setVisibility(View.GONE);
        }
    }

    private void initView() {

        mTxtTips = (TextView) findViewById(R.id.txt_tips);
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mTxtDoctorName = (TextView) findViewById(R.id.txt_doctor_name);
        mTxtDoctorTuanduichengyuan = (TextView) findViewById(R.id.txt_doctor_tuanduichengyuan);
        mTxtDoctorPhone = (TextView) findViewById(R.id.txt_doctor_phone);
        mTxtDoctorJigou = (TextView) findViewById(R.id.txt_doctor_jigou);
        mSignInfoBase = (SignInfoAddLayout) findViewById(R.id.sign_info_base);
        mSignInfoBase.setAddOnClickListener(new OnAddClickListener(1));
        mTxtUserBaseName = (TextView) findViewById(R.id.txt_user_base_name);
        mTxtUserBaseSex = (TextView) findViewById(R.id.txt_user_base_sex);
        mTxtUserBaseBirthday = (TextView) findViewById(R.id.txt_user_base_birthday);
        mTxtUserBaseGuanxi = (TextView) findViewById(R.id.txt_user_base_guanxi);
        mLinUserBaseInfo = (LinearLayout) findViewById(R.id.lin_user_base_info);
        mSignInfoClow = (SignInfoAddLayout) findViewById(R.id.sign_info_clow);
        mSignInfoClow.setAddOnClickListener(new OnAddClickListener(2));
        mFlowLayoutClow = (FlowLayout) findViewById(R.id.flow_layout_clow);
        mLinSignInfoClow = (LinearLayout) findViewById(R.id.lin_sign_info_clow);
        mSignInfoServerPak = (SignInfoAddLayout) findViewById(R.id.sign_info_server_pak);
        mSignInfoServerPak.setAddOnClickListener(new OnAddClickListener(3));
        mViewLineHeader = (View) findViewById(R.id.view_line_header);
        mLlPackageCon = (LinearLayout) findViewById(R.id.ll_package_con);
        mTvRemarkStorage = (TextView) findViewById(R.id.tv_remark_storage);
        mTxtRemarks = (TextView) findViewById(R.id.txt_remarks);
        mLlRemarks = (RelativeLayout) findViewById(R.id.ll_remarks);
        mViewLineFoot = (View) findViewById(R.id.view_line_foot);
        mLinSignInfoServerPak = (LinearLayout) findViewById(R.id.lin_sign_info_server_pak);
        mTxt1 = (TextView) findViewById(R.id.txt1);
        mTxtSignDate = (TextView) findViewById(R.id.txt_sign_date);
        mRelSignDate = (RelativeLayout) findViewById(R.id.rel_sign_date);
        mTakePicture = (PictureListLayout) findViewById(R.id.take_picture);
        mImgUserSign = (ImageView) findViewById(R.id.img_user_sign);
        mLinSignArea = (LinearLayout) findViewById(R.id.lin_sign_area);
        mTxtSubmit = (TextView) findViewById(R.id.txt_submit);
        mTxtSubmit.setOnClickListener(this);
        mLlMain = (LinearLayout) findViewById(R.id.ll_main);
        mLinTakePicture = (LinearLayout) findViewById(R.id.lin_take_picture);
        mLinSign = (LinearLayout) findViewById(R.id.lin_sign);
        mLinDoctorSelect = (LinearLayout) findViewById(R.id.lin_doctor_select);
        mLinSelectDate = (LinearLayout) findViewById(R.id.lin_select_date);
        mView1 = (View) findViewById(R.id.view_1);
        mView2 = (View) findViewById(R.id.view_2);
        mTxtBohui = (TextView) findViewById(R.id.txt_bohui);
        mLinBohui = (LinearLayout) findViewById(R.id.lin_bohui);
        ivTimeArrow = (ImageView) findViewById(R.id.iv_time_arrow);
    }

    void setTeamInfo(String doctorName, String team, String phone, String place) {
        mTxtDoctorName.setText("家庭医生：" + doctorName);
        mTxtDoctorTuanduichengyuan.setText("医生团队：" + team);
        mTxtDoctorPhone.setText("联系电话：" + phone);
        mTxtDoctorJigou.setText("服务机构：" + place);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.txt_submit:
                submit();
                break;
        }
    }

    void submit() {
        if (NetworkUtils.isAvailable(context) == false) {
            HelpUtil.showToast(context, "网络不可用，请检查网络");
            return;
        }

        if (hasClow == false) {
            HelpUtil.showToast(context, "请选择人群分类");
            return;
        }
        if (hasPackage == false) {
            HelpUtil.showToast(context, "请选择服务包");
            return;
        }

        String clow = "";
        for (String item : signDetailResponse.getData().getClassificationList()) {
            if (TextUtils.isEmpty(clow)) {
                clow = clow + item;
            } else {
                clow = clow + "," + item;
            }
        }

        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("signUid", signId);
        map.put("remarks", mTxtRemarks.getText().toString());
        map.put("classificationList", clow);
        map.put("signTime", mTxtSignDate.getText().toString());
        map.put("servicePackageList", GsonUtil.toJson(signDetailResponse.getData().getServicePackageList()));
        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
        progressBar.show();
        new HLHttpUtils().post(map, Cons.CONFIRM_SUBMIT()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse bean) {
                HelpUtil.showToast(context, "提交成功");
                Intent intent = new Intent(SignExamineActivity.SIGN_EXAMINE_REFRESH);
                sendBroadcast(intent);

                finish();
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

    void signDetail() {
        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
        progressBar.show();
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("signId", signId);
        new HLHttpUtils().post(map, Cons.SIGN_DETAIL()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<SignDetailResponse>() {
            @Override
            public void onSuccess(SignDetailResponse bean) {
                signDetailResponse = bean;
                mLlEmpty.setVisibility(View.GONE);
                mLlMain.setVisibility(View.VISIBLE);
                //用户基本信息
                setUserBaseInfo();
                if ("待审核".equals(signState.trim()) || "11".equals(signState.trim())) {
                    return;
                }
                if ("13".equals(signState.trim()) || "已驳回".equals(signState.trim())) {
                    mTxtBohui.setText("驳回原因：" + bean.getData().getRejectReason());
                    return;
                }
                setClow();
                setPackage();
                setOther();
            }

            @Override
            public void onFailure(String code, String errormsg) {
                mLlEmpty.setVisibility(View.VISIBLE);
                mLlMain.setVisibility(View.GONE);
                mTxtTips.setText("服务器异常，请稍后再试");
                HelpUtil.showToast(context, errormsg);
            }

            @Override
            public void result(String result) {
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
            }
        });
    }


    /**
     * 设置用户的基本信息
     */
    void setUserBaseInfo() {
        if (signDetailResponse == null) {
            return;
        }
        mLinUserBaseInfo.setVisibility(View.VISIBLE);
        mTxtUserBaseName.setText("姓名：" + signDetailResponse.getData().getUserName());
        mTxtUserBaseSex.setText("性别：" + signDetailResponse.getData().getSex());
        mTxtUserBaseBirthday.setText("出生日期：" + signDetailResponse.getData().getBirthday());
        signType = signDetailResponse.getData().getSignType();
        if (!TextUtils.isEmpty(signType)) {
            if (signType.equals("1"))
                mTxtUserBaseGuanxi.setVisibility(View.GONE);
            else
                mTxtUserBaseGuanxi.setText("与户主关系：" + signDetailResponse.getData().getRelationship());
        }

    }

    /**
     * 设置人群分类
     */
    void setClow() {
        //人群分类
        mFlowLayoutClow.removeAllViews();
        mFlowLayoutClow.setVisibility(View.VISIBLE);
        for (String item : signDetailResponse.getData().getClassificationList()) {
            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.select_package_item, mFlowLayoutClow, false);
            textView.setText(item);
            mFlowLayoutClow.addView(textView);
        }
    }

    /**
     * 设置服务包
     */
    void setPackage() {
        mLlPackageCon.removeAllViews();
        mLlRemarks.setVisibility(View.VISIBLE);
        mTxtRemarks.setText(signDetailResponse.getData().getRemarks());
        if (TextUtils.isEmpty(signDetailResponse.getData().getRemarks()) == false) {
            mLlRemarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignDetailActivity.this, RemarkRecjectActivity.class);
                    intent.putExtra("entertype", "备注");
                    intent.putExtra("remarks", signDetailResponse.getData().getRemarks());
                    intent.putExtra("isClicked", false);
                    startActivity(intent);
                }
            });
        }
        mViewLineHeader.setVisibility(View.VISIBLE);
        mViewLineFoot.setVisibility(View.VISIBLE);
        for (ServicePackageListBean beanItem : signDetailResponse.getData().getServicePackageList()) {
            View view = LayoutInflater.from(context).inflate(R.layout.select_package_layout_item, mLlPackageCon, false);
            TextView textViewName = view.findViewById(R.id.txt_package_name);
            TextView textViewPrice = view.findViewById(R.id.txt_price);
            textViewName.setText(beanItem.getServicePackageName());
            DecimalFormat df = new DecimalFormat("#0.00");
            textViewPrice.setText("￥" + df.format(beanItem.getTotalPrice() / 100));
            mLlPackageCon.addView(view);
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        String serviceTotalPrice = df.format(signDetailResponse.getData().getServiceTotalPrice() / 100d);
        mSignInfoServerPak.setPrice(serviceTotalPrice);
    }

    /**
     * 日期选择
     */
    void showDate() {
        DatePicker datePicker = new DatePicker(this);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        datePicker.setRangeStart(year, mouth + 1, day);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String date = year + "-" + month + "-" + day;
                mTxtSignDate.setText(date);
            }
        });
        datePicker.show();
    }

    /**
     * 设置其他 ，签约时间，拍照，签字
     */
    void setOther() {
        mTxtSignDate.setText(signDetailResponse.getData().getSignDate());
        List<String> lstImagePath = new ArrayList<>();
        if (TextUtils.isEmpty(signDetailResponse.getData().getPictureOneName()) == false) {
            lstImagePath.add(signDetailResponse.getData().getPictureOneName());
        }
        if (TextUtils.isEmpty(signDetailResponse.getData().getPictureTwoName()) == false) {
            lstImagePath.add(signDetailResponse.getData().getPictureTwoName());
        }
        if (lstImagePath.size() == 0) {
            mLinTakePicture.setVisibility(View.GONE);
        } else {
            mTakePicture.setShow(lstImagePath);
        }

        if (TextUtils.isEmpty(signDetailResponse.getData().getPictureSignName()) == false) {
            Picasso.with(context).load(signDetailResponse.getData().getPictureSignName()).into(mImgUserSign);
        }
    }

    class OnAddClickListener implements View.OnClickListener {

        int type;

        public OnAddClickListener(int type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            if (type == 1) {
                if (mSignInfoBase.getBtnName().equals("驳回")) {
                    Intent intent = new Intent(context, RemarkRecjectActivity.class);
                    intent.putExtra("entertype", "驳回");
                    intent.putExtra("signUid", signId);
                    startActivity(intent);
                    return;
                }
            } else if (type == 2) {
                if ("11".equals(signState.trim()) || "待审核".equals(signState.trim())) {
                    Intent intent = new Intent(context, ClassificationActivity.class);
                    if (mSignInfoClow.getBtnName().equals("修改")) {
                        intent.putExtra("isEdit", true);
                        intent.putStringArrayListExtra("clow", (ArrayList<String>) signDetailResponse.getData().getClassificationList());
                    }
                    startActivity(intent);
                }
            } else if (type == 3) {
                if ("11".equals(signState.trim()) || "待审核".equals(signState.trim())) {
                    Intent intent = new Intent(context, SelectServicePackageActivity.class);
                    if (mSignInfoServerPak.getBtnName().equals("修改")) {
                        intent.putExtra("isEdit", true);
                        intent.putExtra("serviceContentBeanList", (Serializable) signDetailResponse.getData().getServicePackageList());
                        intent.putExtra("remarks", signDetailResponse.getData().getRemarks());
                        intent.putExtra("totalPrice", serviceTotalPrice);
                    }
                    startActivity(intent);
                }

            }
        }
    }

    class OnReadClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, AddFamilyActivity.class);
            intent.putExtra("isRead", true);
            intent.putExtra("householderIDCard", signDetailResponse.getData().getHouseholderIDCard());
            intent.putExtra("identityCard", signDetailResponse.getData().getIdentityCard());
            intent.putExtra("userName", signDetailResponse.getData().getUserName());
            intent.putExtra("sex", signDetailResponse.getData().getSex());
            intent.putExtra("raceName", signDetailResponse.getData().getRaceName());
            intent.putExtra("birthday", signDetailResponse.getData().getBirthday());
            intent.putExtra("age", signDetailResponse.getData().getAge());
            intent.putExtra("userPhone", signDetailResponse.getData().getUserPhone());
            intent.putExtra("relationship", signDetailResponse.getData().getRelationship());
            intent.putExtra("userAddress", signDetailResponse.getData().getUserAddress());
            intent.putExtra("signType", signType);
            startActivity(intent);
        }
    }

    @Override
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

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
}
