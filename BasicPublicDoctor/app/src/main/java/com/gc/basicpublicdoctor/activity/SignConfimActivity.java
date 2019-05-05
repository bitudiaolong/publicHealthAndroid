package com.gc.basicpublicdoctor.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gagc.httplibrary.BaseResponse;
import com.gagc.httplibrary.UploadMoreResponse;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.FamilyMembersListResponse;
import com.gc.basicpublicdoctor.bean.ServicePackageListBean;
import com.gc.basicpublicdoctor.bean.SubmitSign;
import com.gc.basicpublicdoctor.bean.SubmitSignRequest;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.GsonUtil;
import com.gc.utils.HelpUtil;
import com.gc.utils.MD5Util;
import com.gc.utils.NetworkUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignConfimActivity extends BaseActivity implements View.OnClickListener
        , DialogProgressBar.OnProgressListener {
    public static final String SIGN_USER_ADD = "SIGN_USER_ADD";
    public static final String SIGN_USER_EDIT = "SIGN_USER_EDIT";
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
    SubmitSignRequest submitSignRequest;
    private LinearLayout mLinUserCon;
    private LinearLayout mLinUserConSign;
    /**
     * 户主身份证
     */
    String householderIDCard;

    /**
     * 记录当前编辑的对象
     */
    SubmitSign submitSignEdit;

    /**
     * 户主身份证下有没有已签约的家庭
     */
    boolean isHasSignFam = false;

    /**
     * 已经签过过的列表 包过已过期，待签约等
     */
    List<FamilyMembersListResponse.DataBean.ListBean> lstFromHouse = new ArrayList<>();
    /**
     * 提交
     */
    private Button mBtnSubmit;
    DialogProgressBar progressBar;

    private String signType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_confim);

        initView();
        initData();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SIGN_USER_ADD);
        intentFilter.addAction(SIGN_USER_EDIT);
        registerReceiver(broadcastReceiver, intentFilter);
        if (NetworkUtils.isAvailable(context)) {
            familyMembersList();
        }
        setUserView();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SIGN_USER_ADD.equals(intent.getAction())) {
                if (submitSignRequest.getSubmitSignList() == null) {
                    submitSignRequest.setSubmitSignList(new ArrayList<SubmitSign>());
                }
                SubmitSign submitSign = (SubmitSign) intent.getSerializableExtra("submitSign");
                submitSignRequest.getSubmitSignList().add(submitSign);
                householderIDCard = submitSign.getHouseholderIDCard();
                if (NetworkUtils.isAvailable(context)) {
                    familyMembersList();
                }
                setUserView();

            }
            if (SIGN_USER_EDIT.equals(intent.getAction())) {
                SubmitSign submitSign = (SubmitSign) intent.getSerializableExtra("submitSign");
                int position = submitSignRequest.getSubmitSignList().indexOf(submitSignEdit);
                submitSignRequest.getSubmitSignList().remove(submitSignEdit);
                if (position > -1) {
                    submitSignRequest.getSubmitSignList().add(position, submitSign);
                }
                setUserView();
            }
        }
    };

    void setUserView() {
        mLinUserCon.removeAllViews();
        for (SubmitSign submitSign : submitSignRequest.getSubmitSignList()) {
            mLinUserCon.addView(getUserView(submitSign));
        }

    }

    View getUserView(final SubmitSign submitSign) {
        View view = LayoutInflater.from(context).inflate(R.layout.confim_sign_user_info_item, mLinUserCon, false);
        TextView textViewName = view.findViewById(R.id.txt_user_name);
        TextView textClow = view.findViewById(R.id.txt_clow);
        TextView textPackage = view.findViewById(R.id.txt_package_names);

        textViewName.setText("姓名：" + submitSign.getUserName());
        String clows = "";
        for (String item : submitSign.getClassificationList()) {
            if (TextUtils.isEmpty(clows)) {
                clows = clows + item;
            } else {
                clows = clows + "、" + item;
            }
        }
        textClow.setText("所属人群：" + clows);
        String packageNames = "";
        for (ServicePackageListBean bean : submitSign.getServicePackageList()) {
            if (TextUtils.isEmpty(packageNames)) {
                packageNames = packageNames + bean.getServicePackageName();
            } else {
                packageNames = packageNames + "、" + bean.getServicePackageName();
            }
        }
        textPackage.setText("所选服务包：" + packageNames);

        TextView textViewEdit = (TextView) view.findViewById(R.id.txt_edit);
        TextView textViewDel = (TextView) view.findViewById(R.id.txt_del);
        textViewEdit.setVisibility(View.VISIBLE);
        textViewDel.setVisibility(View.VISIBLE);
        textViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignActivity.class);
                intent.putExtra("signType", signType);
                intent.putExtra("submitSign", (Serializable) submitSign);
                intent.putExtra("isFromConfimEdit", true);
                if (isHasSignFam) {
                    //如果该户主身份证下，存在已经签约的用户，编辑的时候不允许修改户主身份证
                    intent.putExtra("isEnableEditHouseCard", false);
                }
                submitSignEdit = submitSign;
                startActivity(intent);
            }
        });

        textViewDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(submitSign);
            }
        });

        return view;
    }

    // 删除阻止
    public void showAlertDialog(final SubmitSign submitSign) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("提示").setMessage("是否确定删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitSignRequest.getSubmitSignList().remove(submitSign);
                        if (submitSignRequest.getSubmitSignList().size() == 0) {
                            householderIDCard = "";
                        }
                        setUserView();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        alertDialog.show();
    }

    View getUserView(FamilyMembersListResponse.DataBean.ListBean submitSign) {
        View view = LayoutInflater.from(context).inflate(R.layout.confim_sign_user_info_item, mLinUserCon, false);
        TextView textViewName = view.findViewById(R.id.txt_user_name);
        TextView textClow = view.findViewById(R.id.txt_clow);
        TextView textPackage = view.findViewById(R.id.txt_package_names);

        textViewName.setText("姓名：" + submitSign.getName());
        String clows = "";
        for (String item : submitSign.getClassificationList()) {
            if (TextUtils.isEmpty(clows)) {
                clows = clows + item;
            } else {
                clows = clows + "、" + item;
            }
        }
        textClow.setText("所属人群：" + clows);
        String packageNames = "";
        for (String bean : submitSign.getServicePackageList()) {
            if (TextUtils.isEmpty(packageNames)) {
                packageNames = packageNames + bean;
            } else {
                packageNames = packageNames + "、" + bean;
            }
        }
        textPackage.setText("所选服务包：" + packageNames);

        return view;
    }

    private void initView() {
        showTitleName("确认签约", false);
        showBackText("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("提示").setMessage("内容尚未提交，是否离开？？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                alertDialog.show();
            }
        });
        mTxtDoctorName = (TextView) findViewById(R.id.txt_doctor_name);
        mTxtDoctorTuanduichengyuan = (TextView) findViewById(R.id.txt_doctor_tuanduichengyuan);
        mTxtDoctorPhone = (TextView) findViewById(R.id.txt_doctor_phone);
        mTxtDoctorJigou = (TextView) findViewById(R.id.txt_doctor_jigou);
        signType = getIntent().getStringExtra("signType");
        if ("2".equals(signType)) {
            showRightBtn("添加家人", true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SignActivity.class);
                    intent.putExtra("signType", signType);
                    intent.putExtra("isFromConfimAdd", true);
                    if (isHasSignFam && TextUtils.isEmpty(householderIDCard) == false || TextUtils.isEmpty(householderIDCard) == false) {
                        //如果该户主身份证下，存在已经签约的用户，编辑的时候不允许修改户主身份证
                        intent.putExtra("isEnableEditHouseCard", false);
                        intent.putExtra("householderIDCard", householderIDCard);
                        intent.putStringArrayListExtra("hasSignAddCard", getCardHasAddorSign());
                    }
                    startActivity(intent);
                }
            });
        }
        mLinUserCon = (LinearLayout) findViewById(R.id.lin_user_con);
        mLinUserConSign = (LinearLayout) findViewById(R.id.lin_user_con_sign);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
    }

    void initData() {
        setTeamInfo(Cons.userName, Cons.docTeamName, Cons.userPhone, Cons.healthRoomName);
        submitSignRequest = new SubmitSignRequest();
        submitSignRequest.setUserUid(Cons.userUid);
        submitSignRequest.setDoctorToken(Cons.doctorToken);
        SubmitSign submitSign = (SubmitSign) getIntent().getSerializableExtra("submitSign");
        if (submitSignRequest.getSubmitSignList() == null) {
            submitSignRequest.setSubmitSignList(new ArrayList<SubmitSign>());
        } else {
            submitSignRequest.getSubmitSignList().clear();
        }
        submitSignRequest.getSubmitSignList().add(submitSign);
        householderIDCard = submitSign.getHouseholderIDCard();
    }

    void setTeamInfo(String doctorName, String team, String phone, String place) {
        mTxtDoctorName.setText("家庭医生：" + doctorName);
        mTxtDoctorTuanduichengyuan.setText("医生团队：" + team);
        mTxtDoctorPhone.setText("联系电话：" + phone);
        mTxtDoctorJigou.setText("服务机构：" + place);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    void familyMembersList() {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("householderIDCard", householderIDCard);
        new HLHttpUtils().post(map, Cons.FAMILY_MEMBERS_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<FamilyMembersListResponse>() {
            @Override
            public void onSuccess(FamilyMembersListResponse bean) {

                if (bean.getData() == null) {
                    mLinUserConSign.setVisibility(View.GONE);
                    return;
                }
                if (bean.getData().getList() == null) {
                    mLinUserConSign.setVisibility(View.GONE);
                    return;
                }
                if (bean.getData().getList().size() == 0) {
                    mLinUserConSign.setVisibility(View.GONE);
                }
                mLinUserConSign.removeAllViews();
                lstFromHouse = bean.getData().getList();
                for (FamilyMembersListResponse.DataBean.ListBean item : lstFromHouse) {
                    mLinUserConSign.addView(getUserView(item));
                    mLinUserConSign.setVisibility(View.VISIBLE);
                    isHasSignFam = true;
                }

            }

            @Override
            public void onFailure(String code, String errormsg) {

                mLinUserConSign.setVisibility(View.GONE);
            }

            @Override
            public void result(String result) {
                // setUserView();
            }
        });
    }

    ArrayList<String> getCardHasAddorSign() {
        ArrayList<String> lstHasCard = new ArrayList<>();
        lstHasCard.clear();
        for (SubmitSign submitSign : submitSignRequest.getSubmitSignList()) {
            lstHasCard.add(submitSign.getIdentityCard());
        }

        for (FamilyMembersListResponse.DataBean.ListBean item : lstFromHouse) {
            if ("1".equals(item.getSignState())) {
                lstHasCard.add(item.getContractedUserIDCard());
            }
        }
        return lstHasCard;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_submit:
                if (NetworkUtils.isAvailable(context)) {
                    submit();
                } else {
                    HelpUtil.showToast(context, "网络不可用，请检查网络");
                }

                break;
        }
    }

    void submit() {
        if (submitSignRequest.getSubmitSignList() == null) {
            HelpUtil.showToast(context, "请添加家人");
            return;
        }
        if (submitSignRequest.getSubmitSignList().size() == 0) {
            HelpUtil.showToast(context, "请添加家人");
            return;
        }

        for (SubmitSign submitSign : submitSignRequest.getSubmitSignList()) {
            if (isSign(submitSign.getIdentityCard())) {
                HelpUtil.showToast(context, "“" + submitSign.getUserName() + "”已经签约");
                return;
            }
            int count = 0;
            for (SubmitSign submitSignBean : submitSignRequest.getSubmitSignList()) {
                if (submitSignBean.getIdentityCard().equals(submitSign.getIdentityCard())) {
                    count++;
                }
            }
            if (count > 1) {
                HelpUtil.showToast(context, "添加的成员存在相同的身份证，请检查");
                return;
            }
        }
        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
        progressBar.show();

//        Map<String, Map<String, String>> mapFilePath = new HashMap<>();
        List<String> lstFilePath = new ArrayList<>();
        for (SubmitSign submitSign : submitSignRequest.getSubmitSignList()) {
//            Map<String, String> mapPath = new HashMap<>();
//            mapPath.put("signPath", TextUtils.isEmpty(submitSign.getPictureSignName()) ? "" : submitSign.getPictureSignName());
//            mapPath.put("pic1", TextUtils.isEmpty(submitSign.getPictureOneName()) ? "" : submitSign.getPictureOneName());
//            mapPath.put("pic2",TextUtils.isEmpty(submitSign.getPictureTwoName()) ? "" : submitSign.getPictureTwoName());
            if (TextUtils.isEmpty(submitSign.getPictureSignName()) == false) {
                lstFilePath.add(submitSign.getPictureSignName());
            }
            if (TextUtils.isEmpty(submitSign.getPictureOneName()) == false) {
                lstFilePath.add(submitSign.getPictureOneName());
            }
            if (TextUtils.isEmpty(submitSign.getPictureTwoName()) == false) {
                lstFilePath.add(submitSign.getPictureTwoName());
            }


        }
        final Map<String, String> map = new HashMap<>();
        for (String item : lstFilePath) {
            map.put(MD5Util.getMd5ByFile(new File(item)), item);
        }

        new HLHttpUtils<UploadMoreResponse>().uploadFiles(Cons.GET_IMAGE_UPLOAD(), map, new AbstarctGenericityHttpUtils.CallBack<UploadMoreResponse>() {
            @Override
            public void onSuccess(UploadMoreResponse bean) {
//                HelpUtil.showToast(context, "上传成功");
                submitSign(bean, map);
            }

            @Override
            public void onFailure(String code, String errormsg) {
                HelpUtil.showToast(context, errormsg);
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
            }

            @Override
            public void result(String result) {

            }
        });
    }

    void submitSign(UploadMoreResponse bean, Map<String, String> mapFile) {
        final Map<String, String> newOldNames = new HashMap<>();
        for (SubmitSign submitSign : submitSignRequest.getSubmitSignList()) {

            if (mapFile.containsValue(submitSign.getPictureSignName())) {
                String md5Key = getKeyByValue(mapFile, submitSign.getPictureSignName());
                if (TextUtils.isEmpty(md5Key) == false) {
                    newOldNames.put(bean.getData().get(md5Key), submitSign.getPictureSignName());
                    submitSign.setPictureSignName(bean.getData().get(md5Key));
                }
            }
            if (mapFile.containsValue(submitSign.getPictureOneName())) {
                String md5Key = getKeyByValue(mapFile, submitSign.getPictureOneName());
                if (TextUtils.isEmpty(md5Key) == false) {
                    newOldNames.put(bean.getData().get(md5Key), submitSign.getPictureOneName());
                    submitSign.setPictureOneName(bean.getData().get(md5Key));
                }
            }
            if (mapFile.containsValue(submitSign.getPictureTwoName())) {
                String md5Key = getKeyByValue(mapFile, submitSign.getPictureTwoName());
                if (TextUtils.isEmpty(md5Key) == false) {
                    newOldNames.put(bean.getData().get(md5Key), submitSign.getPictureTwoName());
                    submitSign.setPictureTwoName(bean.getData().get(md5Key));
                }
            }
        }

        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", submitSignRequest.getDoctorToken());
        map.put("userUid", submitSignRequest.getUserUid());
        map.put("signType", signType);
        map.put("submitSignList", GsonUtil.toJson(submitSignRequest.getSubmitSignList()));
        new HLHttpUtils().post(map, Cons.SUBMIT_SIGN()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse bean) {
                HelpUtil.showToast(context, "签约成功");
                Intent intent = new Intent();
                intent.setAction(SignManagerActivity.SIGN_MANAGER_REFRESH);
                sendBroadcast(intent);
                finish();
            }

            @Override
            public void onFailure(String code, String errormsg) {
                HelpUtil.showToast(context, errormsg);
                for (SubmitSign submitSign : submitSignRequest.getSubmitSignList()) {
                    if (TextUtils.isEmpty(submitSign.getPictureSignName()) == false) {
                        submitSign.setPictureSignName(newOldNames.get(submitSign.getPictureSignName()));
                    }
                    if (TextUtils.isEmpty(submitSign.getPictureOneName()) == false) {
                        submitSign.setPictureOneName(newOldNames.get(submitSign.getPictureOneName()));
                    }
                    if (TextUtils.isEmpty(submitSign.getPictureTwoName()) == false) {
                        submitSign.setPictureTwoName(newOldNames.get(submitSign.getPictureTwoName()));
                    }
                }
            }

            @Override
            public void result(String result) {
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
            }
        });

    }

    String getKeyByValue(Map<String, String> map, String value) {
        String key = "";
        for (String itemKey : map.keySet()) {
            if (map.get(itemKey).equals(value)) {
                key = itemKey;
                break;
            }
        }
        return key;
    }

    boolean isSign(String card) {
        for (FamilyMembersListResponse.DataBean.ListBean item : lstFromHouse) {
            if (card.equals(item.getContractedUserIDCard()) && "1".equals(item.getSignState())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

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
