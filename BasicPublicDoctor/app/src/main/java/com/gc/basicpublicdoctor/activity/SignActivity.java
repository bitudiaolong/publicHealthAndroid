package com.gc.basicpublicdoctor.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.ServiceContentBean;
import com.gc.basicpublicdoctor.bean.ServicePackageListBean;
import com.gc.basicpublicdoctor.bean.SubmitSign;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.widget.PictureListLayout;
import com.gc.basicpublicdoctor.widget.SignInfoAddLayout;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.DateTimeUtil;
import com.gc.utils.HelpUtil;
import com.gc.utils.KeyboardUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.lankton.flowlayout.FlowLayout;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * Author:Created by zhurui
 * Time:2018/7/13 下午5:17
 * Description:This is SignActivitt
 */
public class SignActivity extends BaseActivity implements View.OnClickListener, DialogProgressBar.OnProgressListener {

    public static final String USER_SIGN_BACK = "USER_SIGN_BACK";
    public static final String USER_ADD = "USER_ADD";
    public static final String CLOW_SELECT = "CLOW_SELECT";
    public static final String PACKAGE_SELECT = "PACKAGE_SELECT";

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
    private SignInfoAddLayout mSignInfoClow;
    private SignInfoAddLayout mSignInfoServerPak;
    private PictureListLayout mTakePicture;
    private RelativeLayout mRelSignDate;
    /**
     * 2018-02-03
     */
    private TextView mTxtSignDate;
    private ImageView mImgUserSign;
    private LinearLayout mLinSignArea;
    /**
     * 提交
     */
    private TextView mTxtSubmit;
    SubmitSign submitSign;
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
    private FlowLayout mFlowLayoutClow;
    private LinearLayout mLlPackageCon;
    private TextView mTxtRemarks;
    private RelativeLayout mLlRemarks;
    private View mViewLineHeader;
    private View mViewLineFoot;


    String serviceTotalPrice;
    /**
     * 是否添加用户
     */
    boolean hasUser = false;
    /**
     * 是否选择人群分类
     */
    boolean hasClow = false;
    /**
     * 是否选择服务包
     */
    boolean hasPackage = false;
    /**
     * 是否签字
     */
    boolean isUserSign = false;
    /**
     * 是否是确认界面启动
     */
    boolean isFromConfimAdd = false;

    /**
     * 是否是确认界面启动新加,切是否允许修改户主身份证
     */
    boolean fromConfimAddEnableEditHouseCard = true;
    /**
     * 已经签约、已经添加的身份证号
     */
    ArrayList<String> lstHasAddOrSign = new ArrayList<>();
    /**
     * 是否是确认界面启动
     */
    boolean isFromConfimEdit = false;
    /**
     * 是否是确认界面启动,切是否允许修改户主身份证
     */
    boolean fromConfimEditEnableEditHouseCard = true;
    /**
     * 户主身份证
     */
    String householderIDCard;

    private LinearLayout mLlEmpty;
    private LinearLayout mLlMain;
    /**
     * 没有找到相关信息
     */
    private TextView mTxtTips;
    private String signState;
    private String signType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_activity);
        showTitleName("发起签约", true);
        initView();
        initData();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(USER_SIGN_BACK);
        intentFilter.addAction(USER_ADD);
        intentFilter.addAction(CLOW_SELECT);
        intentFilter.addAction(PACKAGE_SELECT);
        intentFilter.addAction(SignExamineActivity.SIGN_EXAMINE_REFRESH);
        registerReceiver(broadcastReceiver, intentFilter);

    }

    void showUserSign() {
        Bitmap imageBitmap = BitmapFactory.decodeFile(submitSign.getPictureSignName());
        mImgUserSign.setImageBitmap(imageBitmap);
        mImgUserSign.setTag(submitSign.getPictureSignName());
        isUserSign = true;
    }

    void showClow() {
        mSignInfoClow.setBtnName("修改");
        mFlowLayoutClow.removeAllViews();
        mFlowLayoutClow.setVisibility(View.VISIBLE);
        for (String item : submitSign.getClassificationList()) {
            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.select_package_item, mFlowLayoutClow, false);
            textView.setText(item);
            mFlowLayoutClow.addView(textView);
        }
        hasClow = true;
    }

    void showPackage() {
        mSignInfoServerPak.setBtnName("修改");
        mLlPackageCon.removeAllViews();
        mLlRemarks.setVisibility(View.VISIBLE);
        mTxtRemarks.setText(submitSign.getRemarks());
        if (TextUtils.isEmpty(submitSign.getRemarks()) == false) {
            mLlRemarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignActivity.this, RemarkRecjectActivity.class);
                    intent.putExtra("entertype", "备注");
                    intent.putExtra("remarks", submitSign.getRemarks());
                    intent.putExtra("isClicked", false);
                    startActivity(intent);
                }
            });
        }
        mViewLineHeader.setVisibility(View.VISIBLE);
        mViewLineFoot.setVisibility(View.VISIBLE);
        long serviceTotal = 0l;
        for (ServicePackageListBean bean : submitSign.getServicePackageList()) {
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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (USER_SIGN_BACK.equals(intent.getAction())) {
                String path = intent.getStringExtra("path");
                submitSign.setPictureSignName(path);
                showUserSign();
            }
            if (USER_ADD.equals(intent.getAction())) {
                submitSign.setHouseholderIDCard(intent.getStringExtra("householderIDCard"));
                submitSign.setIdentityCard(intent.getStringExtra("identityCard"));
                submitSign.setUserName(intent.getStringExtra("userName"));
                submitSign.setSex(intent.getStringExtra("sex"));
                submitSign.setAge(intent.getStringExtra("age"));
                submitSign.setRaceName(intent.getStringExtra("raceName"));
                submitSign.setBirthday(intent.getStringExtra("birthday"));
                submitSign.setUserPhone(intent.getStringExtra("userPhone"));
                submitSign.setRelationship(intent.getStringExtra("relationship"));
                submitSign.setUserAddress(intent.getStringExtra("userAddress"));
                setUserBaseInfo();
            }
            if (CLOW_SELECT.equals(intent.getAction())) {
                ArrayList<String> list = intent.getStringArrayListExtra("clow");
                submitSign.setClassificationList(list);
                showClow();
            }
            if (PACKAGE_SELECT.equals(intent.getAction())) {
                List<ServicePackageListBean> packageSelected = (List<ServicePackageListBean>) intent.getSerializableExtra("serviceContentBeanList");
                submitSign.setServicePackageList(packageSelected);
                submitSign.setRemarks(intent.getStringExtra("remarks"));
                showPackage();
            }
            if (SignExamineActivity.SIGN_EXAMINE_REFRESH.equals(intent.getAction())) {
                finish();
            }
        }
    };


    private void initView() {
        mTxtDoctorName = (TextView) findViewById(R.id.txt_doctor_name);
        mTxtDoctorTuanduichengyuan = (TextView) findViewById(R.id.txt_doctor_tuanduichengyuan);
        mTxtDoctorPhone = (TextView) findViewById(R.id.txt_doctor_phone);
        mTxtDoctorJigou = (TextView) findViewById(R.id.txt_doctor_jigou);
        mSignInfoBase = (SignInfoAddLayout) findViewById(R.id.sign_info_base);
        mSignInfoBase.setAddOnClickListener(new OnAddClickListener(1));
        mSignInfoClow = (SignInfoAddLayout) findViewById(R.id.sign_info_clow);
        mSignInfoClow.setAddOnClickListener(new OnAddClickListener(2));
        mSignInfoServerPak = (SignInfoAddLayout) findViewById(R.id.sign_info_server_pak);
        mSignInfoServerPak.setAddOnClickListener(new OnAddClickListener(3));
        mTakePicture = (PictureListLayout) findViewById(R.id.take_picture);
        mRelSignDate = (RelativeLayout) findViewById(R.id.rel_sign_date);
        mRelSignDate.setOnClickListener(this);
        mTxtSignDate = (TextView) findViewById(R.id.txt_sign_date);
        mImgUserSign = (ImageView) findViewById(R.id.img_user_sign);
        mLinSignArea = (LinearLayout) findViewById(R.id.lin_sign_area);
        mLinSignArea.setOnClickListener(this);
        mTxtSubmit = (TextView) findViewById(R.id.txt_submit);
        mTxtSubmit.setOnClickListener(this);
        mTxtUserBaseName = (TextView) findViewById(R.id.txt_user_base_name);
        mTxtUserBaseSex = (TextView) findViewById(R.id.txt_user_base_sex);
        mTxtUserBaseBirthday = (TextView) findViewById(R.id.txt_user_base_birthday);
        mTxtUserBaseGuanxi = (TextView) findViewById(R.id.txt_user_base_guanxi);
        mLinUserBaseInfo = (LinearLayout) findViewById(R.id.lin_user_base_info);
        mFlowLayoutClow = (FlowLayout) findViewById(R.id.flow_layout_clow);
        mLlPackageCon = (LinearLayout) findViewById(R.id.ll_package_con);
        mTxtRemarks = (TextView) findViewById(R.id.txt_remarks);
        mLlRemarks = (RelativeLayout) findViewById(R.id.ll_remarks);
        mViewLineHeader = (View) findViewById(R.id.view_line_header);
        mViewLineFoot = (View) findViewById(R.id.view_line_foot);
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mLlMain = (LinearLayout) findViewById(R.id.ll_main);
        mTxtTips = (TextView) findViewById(R.id.txt_tips);
    }

    void initData() {
        signType = getIntent().getStringExtra("signType");
        householderIDCard = getIntent().getStringExtra("householderIDCard");

        mLlEmpty.setVisibility(View.GONE);
        mLlMain.setVisibility(View.VISIBLE);
        isFromConfimAdd = getIntent().getBooleanExtra("isFromConfimAdd", false);
        if (isFromConfimAdd) {
            fromConfimAddEnableEditHouseCard = getIntent().getBooleanExtra("isEnableEditHouseCard", true);
            householderIDCard = getIntent().getStringExtra("householderIDCard");
            lstHasAddOrSign = getIntent().getStringArrayListExtra("hasSignAddCard");
        }
        isFromConfimEdit = getIntent().getBooleanExtra("isFromConfimEdit", false);
        if (isFromConfimEdit) {
            submitSign = (SubmitSign) getIntent().getSerializableExtra("submitSign");
            fromConfimEditEnableEditHouseCard = getIntent().getBooleanExtra("isEnableEditHouseCard", true);
            setUserBaseInfo();
            showUserSign();
            showClow();
            showPackage();
            mTakePicture.setLstImagePath(TextUtils.isEmpty(submitSign.getPictureOneName()) ? "" : submitSign.getPictureOneName(), TextUtils.isEmpty(submitSign.getPictureTwoName()) ? "" : submitSign.getPictureTwoName());
        } else {
            submitSign = new SubmitSign();
        }
//        } else {
////            submitSign = new SubmitSign();
////            signState = getIntent().getStringExtra("signState");
////            if (TextUtils.isEmpty(signState)) {
////                showTitleName("签约详情", true);
////                showSignDetail();
////            } else {
////                //签约状态（0待签约 1已签约 2已解约 9已过期 11 待审核 12 待确认 13 已驳回）
////                if ("待审核".equals(signState) || "11".equals(signState)) {
////                    showTitleName("审核详情", true);
////                    if (NetworkUtils.isAvailable(context)) {
////                        signDetail();
////                    } else {
////                        mTxtTips.setText("没有网络");
////                    }
////                } else {
////                    showSignDetail();
////                }
////            }
//        }

        setTeamInfo(Cons.userName, Cons.docTeamName, Cons.userPhone, Cons.healthRoomName);
        mSignInfoBase.setTitle("居民基本信息");
        mSignInfoClow.setTitle("所属人群");
        mSignInfoServerPak.setTitle("服务包");
        mTxtSignDate.setText(DateTimeUtil.date2String(System.currentTimeMillis(), "yyyy-MM-dd"));
    }

//    void showSignDetail() {
//        mSignInfoBase.setBtnGone();
//        mSignInfoClow.setBtnGone();
//        mSignInfoServerPak.setBtnGone();
//        mRelSignDate.setClickable(false);
//        mTxtSubmit.setVisibility(View.GONE);
//        mLinSignArea.setClickable(false);
//        if (NetworkUtils.isAvailable(context)) {
//            signDetail();
//        } else {
//            mTxtTips.setText("没有网络");
//        }
//    }

    void setTeamInfo(String doctorName, String team, String phone, String place) {
        mTxtDoctorName.setText("家庭医生：" + doctorName);
        mTxtDoctorTuanduichengyuan.setText("医生团队：" + team);
        mTxtDoctorPhone.setText("联系电话：" + phone);
        mTxtDoctorJigou.setText("服务机构：" + place);
    }

    void setUserBaseInfo() {
        mSignInfoBase.setBtnName("修改");
        mLinUserBaseInfo.setVisibility(View.VISIBLE);
        mTxtUserBaseName.setText("姓名：" + submitSign.getUserName());
        mTxtUserBaseSex.setText("性别：" + submitSign.getSex());
        mTxtUserBaseBirthday.setText("出生日期：" + submitSign.getBirthday());
        if (signType.equals("1")) {
            mTxtUserBaseGuanxi.setVisibility(View.GONE);
        } else {
            mTxtUserBaseGuanxi.setVisibility(View.VISIBLE);
            mTxtUserBaseGuanxi.setText("与户主关系：" + submitSign.getRelationship());
        }
        hasUser = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureListLayout.TAKE_PICTURE) {
            mTakePicture.takePictureBack();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rel_sign_date:
                dateSelect();
                break;
            case R.id.lin_sign_area:
                showSignActivity();
                break;
            case R.id.txt_submit:
                submit();
                break;
        }
    }

    void submit() {
        if (hasUser == false) {
            HelpUtil.showToast(context, "请添加居民身份");
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
        if (mTakePicture.getLstImagePath().size() == 0) {
            HelpUtil.showToast(context, "请拍照");
            return;
        }
        if (isUserSign == false) {
            HelpUtil.showToast(context, "请签字");
            return;
        }
        submitSign.setSignTime(mTxtSignDate.getText().toString());
        submitSign.setUserUid(Cons.userUid);
        submitSign.setPictureOneName(mTakePicture.getLstImagePath().get(0));
        if (mTakePicture.getLstImagePath().size() == 2) {
            submitSign.setPictureTwoName(mTakePicture.getLstImagePath().get(1));
        } else {
            submitSign.setPictureTwoName("");
        }
        Intent intent = new Intent();
        intent.putExtra("submitSign", (Serializable) submitSign);


        if (isFromConfimAdd) {
            intent.setAction(SignConfimActivity.SIGN_USER_ADD);
            sendBroadcast(intent);
        } else if (isFromConfimEdit) {
            intent.setAction(SignConfimActivity.SIGN_USER_EDIT);
            sendBroadcast(intent);
        } else {
            intent.putExtra("signType", signType);
            intent.setClass(context, SignConfimActivity.class);
            startActivity(intent);

        }
        finish();
    }

    void showSignActivity() {
        Intent intent = new Intent(this, UserSignActivity.class);
        startActivity(intent);
    }

    /**
     * 日期选择
     */
    void dateSelect() {
        DatePicker datePicker = new DatePicker(this);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        datePicker.setRangeStart(year, mouth + 1, day);
//        datePicker.setRangeEnd(year, mouth + 1, day);
        datePicker.setSelectedItem(year, mouth + 1, day);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String date = year + "-" + month + "-" + day;
                mTxtSignDate.setText(date);
            }
        });
        datePicker.show();
    }
//

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

    }

    class OnAddClickListener implements View.OnClickListener {

        int type;

        public OnAddClickListener(int type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            if (type == 1) {
//                if (mSignInfoBase.getBtnName().equals("驳回")) {
//                    Intent intent = new Intent(context, RemarkRecjectActivity.class);
//                    intent.putExtra("entertype", "驳回");
//                    intent.putExtra("signUid", signId);
//                    startActivity(intent);
//                    return;
//                }
                Intent intent = new Intent(context, AddFamilyActivity.class);
                intent.putExtra("signType", signType);
                intent.putStringArrayListExtra("hasSignAddCard", lstHasAddOrSign);
                if (mSignInfoBase.getBtnName().equals("修改")) {
                    intent.putExtra("isEdit", true);
                    intent.putExtra("householderIDCard", submitSign.getHouseholderIDCard());
                    intent.putExtra("identityCard", submitSign.getIdentityCard());
                    intent.putExtra("userName", submitSign.getUserName());
                    intent.putExtra("sex", submitSign.getSex());
                    intent.putExtra("raceName", submitSign.getRaceName());
                    intent.putExtra("birthday", submitSign.getBirthday());
                    intent.putExtra("age", submitSign.getAge());
                    intent.putExtra("userPhone", submitSign.getUserPhone());
                    intent.putExtra("relationship", submitSign.getRelationship());
                    intent.putExtra("userAddress", submitSign.getUserAddress());
                    intent.putExtra("identityCardImage", submitSign.getIdentityCardImage());
                    if (isFromConfimEdit && fromConfimEditEnableEditHouseCard == false) {
                        intent.putExtra("enableEditHouseCard", fromConfimEditEnableEditHouseCard);
                    }
                } else {
                    if (isFromConfimAdd && fromConfimAddEnableEditHouseCard == false) {
                        intent.putExtra("enableEditHouseCard", fromConfimAddEnableEditHouseCard);
                        intent.putExtra("householderIDCard", householderIDCard);
                    }
                    if (TextUtils.isEmpty(householderIDCard) == false) {
                        intent.putExtra("householderIDCard", householderIDCard);
                        intent.putExtra("enableEditHouseCard", false);
                    }
                }
                startActivity(intent);
            } else if (type == 2) {
                Intent intent = new Intent(context, ClassificationActivity.class);
                if (mSignInfoClow.getBtnName().equals("修改")) {
                    intent.putExtra("isEdit", true);
                    intent.putStringArrayListExtra("clow", (ArrayList<String>) submitSign.getClassificationList());
                }
                startActivity(intent);
            } else if (type == 3) {
                Intent intent = new Intent(context, SelectServicePackageActivity.class);
                if (mSignInfoServerPak.getBtnName().equals("修改")) {
                    intent.putExtra("isEdit", true);
                    intent.putExtra("serviceContentBeanList", (Serializable) submitSign.getServicePackageList());
                    intent.putExtra("remarks", submitSign.getRemarks());
                    intent.putExtra("totalPrice", serviceTotalPrice);
                }
                intent.setAction(SignActivity.PACKAGE_SELECT);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("test", "----------横屏------------");
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("test", "----------竖屏------------");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onpause", "onpause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
    }

}
