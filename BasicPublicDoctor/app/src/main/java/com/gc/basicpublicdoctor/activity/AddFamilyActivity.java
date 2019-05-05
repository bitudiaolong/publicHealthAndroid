package com.gc.basicpublicdoctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dilusense.customkeyboard.KeyboardIdentity;
import com.dilusense.customkeyboard.KeyboardNumber;
import com.dilusense.customkeyboard.MyKeyBoardView;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.orc.FileUtil;
import com.gc.basicpublicdoctor.orc.ORCOnLine;
import com.gc.basicpublicdoctor.tecent.TecentIdentification;
import com.gc.basicpublicdoctor.tecent.entity.IdentifyResult;
import com.gc.basicpublicdoctor.tecent.util.IDCardUtils;
import com.gc.basicpublicdoctor.widget.KeyboardUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.DateTimeUtil;
import com.gc.utils.GsonUtil;
import com.gc.utils.HelpUtil;
import com.gc.utils.IdCardUtil;
import com.gc.utils.KeyboardUtil;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;

public class AddFamilyActivity extends BaseActivity implements View.OnClickListener, DialogProgressBar.OnProgressListener {

    public static final int REQUESTCODE = 101;
    /**
     * 请输入身份证
     */
    private EditText mEtHuzhuCode;
    private LinearLayout mLlPhotocodeHuzhu;
    /**
     * 请输入身份证
     */
    private EditText mEtCode;
    private LinearLayout mLlPhotocode;
    /**  */
    private EditText mEtName;
    /**
     * 男
     */
    private RadioButton mRbSex0;
    /**
     * 女
     */
    private RadioButton mRbSex1;
    private RadioGroup mRgSex;
    /**
     * 请选择名族
     */
    private TextView mTxtRace;
    private LinearLayout mLinRace;
    /**
     * 请选择日期
     */
    private TextView mTvBroth;
    private LinearLayout mLinDate;
    /**
     * 请输入年龄
     */
    private EditText mEtAge;
    /**
     * 请输入联系方式
     */
    private EditText mEtPhone;
    /**
     * 请选择与户主的关系
     */
    private TextView mTxtGuanxi;
    private LinearLayout mLinGuanxi;
    /**
     * 请输入地址
     */
    private EditText mEtAddressNow;
    /**
     * 提交
     */
    private Button mBtnSubmit;
    /**
     * 扫描的是哪个身份证 0户主 1签约人
     */
    int flagScan = 0;
    DialogProgressBar progressBar;
    private String filePath = "";

    private String signCardSendPath = "";
    /**
     * 已经签约、已经添加的身份证号
     */
    ArrayList<String> lstHasAddOrSign = new ArrayList<>();
    KeyboardIdentity keyboardIdentity;
    KeyboardNumber keyboardNum;
    private TextView mTxtFocuse;
    MyKeyBoardView myKeyBoardView;
    LinearLayout layout_huzhu;
    String signType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        keyboardIdentity = new KeyboardIdentity(this);
        keyboardNum = new KeyboardNumber(this);

        initView();
        showTitleName("居民基本信息", true);
        initData();
        initListener();
        mTxtFocuse.requestFocus();
    }

    private void initView() {
        myKeyBoardView = (MyKeyBoardView) findViewById(R.id.keyboard_view);
        mEtHuzhuCode = (EditText) findViewById(R.id.et_huzhu_code);
        KeyboardUtils.bindEditTextEvent(keyboardIdentity, mEtHuzhuCode);
        mLlPhotocodeHuzhu = (LinearLayout) findViewById(R.id.ll_photocode_huzhu);
        mLlPhotocodeHuzhu.setOnClickListener(this);
        mEtCode = (EditText) findViewById(R.id.et_code);
        KeyboardUtils.bindEditTextEvent(keyboardIdentity, mEtCode);
        mLlPhotocode = (LinearLayout) findViewById(R.id.ll_photocode);
        mLlPhotocode.setOnClickListener(this);
        mEtName = (EditText) findViewById(R.id.et_name);
        mRbSex0 = (RadioButton) findViewById(R.id.rb_sex0);
        mRbSex1 = (RadioButton) findViewById(R.id.rb_sex1);
        mRgSex = (RadioGroup) findViewById(R.id.rg_sex);
        mTxtRace = (TextView) findViewById(R.id.txt_race);
        mLinRace = (LinearLayout) findViewById(R.id.lin_race);
        mLinRace.setOnClickListener(this);
        mTvBroth = (TextView) findViewById(R.id.tv_broth);
        mLinDate = (LinearLayout) findViewById(R.id.lin_date);
        mLinDate.setOnClickListener(this);
        mEtAge = (EditText) findViewById(R.id.et_age);
        KeyboardUtils.bindEditTextEvent(keyboardNum, mEtAge);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        KeyboardUtils.bindEditTextEvent(keyboardNum, mEtPhone);
        mTxtGuanxi = (TextView) findViewById(R.id.txt_guanxi);
        mLinGuanxi = (LinearLayout) findViewById(R.id.lin_guanxi);
        mLinGuanxi.setOnClickListener(this);
        mEtAddressNow = (EditText) findViewById(R.id.et_address_now);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
        layout_huzhu = (LinearLayout) findViewById(R.id.layout_huzhu);
        mTxtFocuse = (TextView) findViewById(R.id.txt_focuse);
    }

    private void initData() {
        boolean isRead = getIntent().getBooleanExtra("isRead", false);
        boolean isEdit = getIntent().getBooleanExtra("isEdit", false);
        boolean enableEditHouseCard = getIntent().getBooleanExtra("enableEditHouseCard", true);
        signType = getIntent().getStringExtra("signType");
        if (signType.equals("1")) {
            layout_huzhu.setVisibility(View.GONE);
            mLinGuanxi.setVisibility(View.GONE);
        } else if (signType.equals("2")) {
            layout_huzhu.setVisibility(View.VISIBLE);
            mLinGuanxi.setVisibility(View.VISIBLE);
        }
        if (isRead) {
            mEtHuzhuCode.setFocusable(false);
            mEtHuzhuCode.setEnabled(false);
            mLlPhotocodeHuzhu.setClickable(false);
            mLlPhotocodeHuzhu.setVisibility(View.GONE);
            mEtCode.setFocusable(false);
            mEtCode.setEnabled(false);
            mLlPhotocode.setClickable(false);
            mLlPhotocode.setVisibility(View.GONE);
            mEtName.setClickable(false);
            mEtName.setFocusable(false);

            mRgSex.setClickable(false);
            mRbSex1.setClickable(false);
            mRbSex0.setClickable(false);
            mLinRace.setClickable(false);
            mLinDate.setClickable(false);
            mEtAge.setEnabled(false);
            mEtAge.setFocusable(false);
            mEtPhone.setFocusable(false);
            mEtPhone.setEnabled(false);
            mLinGuanxi.setClickable(false);
            mLinGuanxi.setVisibility(View.GONE);
            mEtAddressNow.setFocusable(false);
            mBtnSubmit.setVisibility(View.GONE);
            showValue();
            return;
        }
        if (isEdit == false) {
            lstHasAddOrSign = getIntent().getStringArrayListExtra("hasSignAddCard");
            if (enableEditHouseCard == false) {
                String householderIDCard = getIntent().getStringExtra("householderIDCard");
                mEtHuzhuCode.setText(householderIDCard);
                mEtHuzhuCode.setEnabled(false);
                mEtHuzhuCode.setFocusable(false);
                mLlPhotocodeHuzhu.setVisibility(View.GONE);
            }
            return;
        }
        showValue();

    }

    void showValue() {
        mEtHuzhuCode.setText(getIntent().getStringExtra("householderIDCard"));
        mEtCode.setText(getIntent().getStringExtra("identityCard"));
        mEtName.setText(getIntent().getStringExtra("userName"));
        String sex = getIntent().getStringExtra("sex");
        if ("男".equals(sex)) {
            mRgSex.check(R.id.rb_sex0);
        } else {
            mRgSex.check(R.id.rb_sex1);
        }
        mEtAge.setText(getIntent().getStringExtra("age"));

        mTxtRace.setText(getIntent().getStringExtra("raceName"));
        mTvBroth.setText(getIntent().getStringExtra("birthday"));
        mEtPhone.setText(getIntent().getStringExtra("userPhone"));
        mTxtGuanxi.setText(getIntent().getStringExtra("relationship"));
        mEtAddressNow.setText(getIntent().getStringExtra("userAddress"));
        signCardSendPath = getIntent().getStringExtra("signCardSendPath");

        mEtHuzhuCode.setEnabled(false);
        mEtHuzhuCode.setFocusable(false);
        mLlPhotocodeHuzhu.setVisibility(View.GONE);
    }

    private void initListener() {
        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeMessages(200);
                handler.sendEmptyMessageAtTime(200, 2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_photocode_huzhu:
                flagScan = 0;
                getPermissions(REQUESTCODE, Permission.CAMERA, Permission.STORAGE);
                break;
            case R.id.ll_photocode:
                flagScan = 1;
                getPermissions(REQUESTCODE, Permission.CAMERA, Permission.STORAGE);
                break;
            case R.id.lin_race:
                keyboardIdentity.hideKeyboard();
                keyboardNum.hideKeyboard();
                showRace();
                break;
            case R.id.lin_date:
                keyboardIdentity.hideKeyboard();
                keyboardNum.hideKeyboard();
                showDate();
                break;
            case R.id.lin_guanxi:
                keyboardIdentity.hideKeyboard();
                keyboardNum.hideKeyboard();
                showGuanXi();
                break;
            case R.id.btn_submit:
                View view = getCurrentFocus();
                if (view != null) {
                    view.clearFocus();
                }
                save();
                break;
        }
    }


    void save() {
        String str_HuzhuCode = mEtHuzhuCode.getText().toString();
        String str_Code = mEtCode.getText().toString();
        String str_Name = mEtName.getText().toString();
        String str_Age = mEtAge.getText().toString();
        String str_Phone = mEtPhone.getText().toString();
        String str_Guanxi = mTxtGuanxi.getText().toString();
        String str_AddressNow = mEtAddressNow.getText().toString();
        if (signType.equals("1")) {
            str_HuzhuCode = str_Code;
            str_Guanxi = "本人";
        } else {
            if (TextUtils.isEmpty(mEtHuzhuCode.getText().toString())) {
                HelpUtil.showToast(context, "请填写户主身份证");
//            KeyboardUtil.showBoard(mEtHuzhuCode);
                mEtHuzhuCode.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(IdCardUtil.IDCardValidate(mEtHuzhuCode.getText().toString())) == false) {
                HelpUtil.showToast(context, "户主身份证不合法");
                mEtHuzhuCode.requestFocus();
//            KeyboardUtil.showBoard(mEtHuzhuCode);
                return;
            }
        }
        if (TextUtils.isEmpty(str_Code)) {
            HelpUtil.showToast(context, "请填写签约人身份证");
            mEtCode.requestFocus();
//            KeyboardUtil.showBoard(mEtCode);
            return;
        } else if (TextUtils.isEmpty(IdCardUtil.IDCardValidate(str_Code)) == false) {
            HelpUtil.showToast(context, "签约人身份证不合法");
            mEtCode.requestFocus();
//            KeyboardUtil.showBoard(mEtCode);
            return;
        }
        if (TextUtils.isEmpty(str_Name)) {
            HelpUtil.showToast(context, "请填写姓名");
            KeyboardUtil.showBoard(mEtName);
            return;
        }
        if (mRbSex0.isChecked() == false && mRbSex1.isChecked() == false) {
            HelpUtil.showToast(context, "请选择性别");
            return;
        }
        if (TextUtils.isEmpty(mTxtRace.getText().toString())) {
            HelpUtil.showToast(context, "请选择民族");
            showRace();
            return;
        }
        if (TextUtils.isEmpty(mTvBroth.getText().toString())) {
            HelpUtil.showToast(context, "请选择出生日期");
            showDate();
            return;
        }

        if (str_Age.length() == 0) {
            HelpUtil.showToast(context, "请输入年龄");
            // KeyboardUtil.showBoard(mEtAge);
            mEtAge.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(str_Phone)) {
            HelpUtil.showToast(context, "请输入联系电话");
//            KeyboardUtil.showBoard(mEtPhone);
            mEtPhone.requestFocus();
            return;
        }
        if (str_Phone.length() != 11) {
            HelpUtil.showToast(context, "手机号码格式不对");
//            KeyboardUtil.showBoard(mEtPhone);
            mEtPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(str_Guanxi)) {
            HelpUtil.showToast(context, "请选择与户主关系");
            showGuanXi();
            return;
        }
        if (TextUtils.isEmpty(str_AddressNow)) {
            HelpUtil.showToast(context, "请输入地址");
            KeyboardUtil.showBoard(mEtAddressNow);
            return;
        }
        if (str_HuzhuCode.equals(str_Code) && str_Guanxi.equals("本人") == false) {
            HelpUtil.showToast(context, "户主和签约人身份证相同，关系请选择本人");
            showGuanXi();
            return;
        }
        if (str_HuzhuCode.equals(str_Code) == false && str_Guanxi.equals("本人")) {
            HelpUtil.showToast(context, "户主和签约人身份证不相同，关系不能选择本人");
            showGuanXi();
            return;
        }

        if (lstHasAddOrSign != null && lstHasAddOrSign.contains(str_Code)) {
            HelpUtil.showToast(context, "该身份证已经添加或签约");
            KeyboardUtil.showBoard(mEtCode);
            return;
        }
        myKeyBoardView.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.setAction(SignActivity.USER_ADD);
        intent.putExtra("householderIDCard", str_HuzhuCode);
        intent.putExtra("relationship", str_Guanxi);
        intent.putExtra("identityCard", str_Code);
        intent.putExtra("userName", str_Name);
        if (mRbSex0.isChecked()) {
            intent.putExtra("sex", "男");
        } else {
            intent.putExtra("sex", "女");
        }
        intent.putExtra("age", str_Age);
        intent.putExtra("raceName", mTxtRace.getText().toString());
        intent.putExtra("birthday", mTvBroth.getText().toString());
        intent.putExtra("userPhone", str_Phone);
        intent.putExtra("userAddress", str_AddressNow);
        intent.putExtra("identityCardImage", signCardSendPath);
        sendBroadcast(intent);
        finish();
    }

    /**
     * 民族选择
     */
    void showRace() {
        List<String> lstRace = Arrays.asList(getResources().getStringArray(R.array.race));
        SinglePicker<String> singlePicker = new SinglePicker<String>(this, lstRace);
        singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                mTxtRace.setText(item);
            }
        });
        singlePicker.show();
    }

    /**
     * 关系选择
     */
    void showGuanXi() {
        List<String> lstRace = Arrays.asList(getResources().getStringArray(R.array.guanxi));
        SinglePicker<String> singlePicker = new SinglePicker<String>(this, lstRace);
        singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                mTxtGuanxi.setText(item);
            }
        });
        singlePicker.show();
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
        datePicker.setRangeStart(year - 200, 1, 1);
        datePicker.setRangeEnd(year, mouth + 1, day);

        datePicker.setSelectedItem(year - 30, 1, 1);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String date = year + "-" + month + "-" + day;
                mTvBroth.setText(date);
            }
        });
        datePicker.show();
    }


    @Override
    protected void onPermissionSuccess(int requestCode) {
        ORCOnLine.newInstance().IDCardScan(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ORCOnLine.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            File file = FileUtil.getSaveEnvFile();  //FileUtil.getSaveFile(getApplicationContext());
            filePath = file.getAbsolutePath();  //FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            scanFile(filePath);

        }
    }

    /**
     * 扫描身份证
     *
     * @param filePath
     */
    private void scanFile(String filePath) {
        progressBar = new DialogProgressBar(context, "正在加载", 0, this);
        progressBar.show();
        TecentIdentification.newInstance(Cons.APPID, Cons.SECRETID, Cons.SECRETKEY, Cons.USERID)
                .uploadIDCardByPath(filePath, TecentIdentification.CARD_TYPE_FRONT, new TecentIdentification.IndentificationCallBack() {
                    @Override
                    public void onSuccess(String result) {

                        handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                        IdentifyResult identifyResult = GsonUtil.gsonToBean(result, IdentifyResult.class);
                        if (identifyResult != null) {
                            if (identifyResult.getErrorcode() == 0) {
                                parseResult(identifyResult);
                            } else {
                                Log.e("infoD", "Error code[" + identifyResult.getErrorcode() + "]msg[" + identifyResult.getErrormsg() + "]");
                                HelpUtil.showToast(context, "识别信息为空，请重新拍照");
                            }
                        } else {
                            HelpUtil.showToast(context, "识别信息为空，请重新拍照");
                        }
                    }

                    @Override
                    public void onFiled(String msg) {
                        Log.e("infoD", "onFiled: " + msg);

                        handler.sendEmptyMessage(Cons.LOADING_DISMISS);
                        HelpUtil.showToast(context, "身份证扫描失败，请重新拍照");
                    }
                });
    }


    private void parseResult(IdentifyResult identifyResult) {
        if (flagScan == 0) {
            String idCardNO = identifyResult.getId();
            mEtHuzhuCode.setText(idCardNO);
            return;
        }
        mEtName.setText(identifyResult.getName());
        String idCardNO = identifyResult.getId();
        mEtCode.setText(idCardNO);
        String sex = identifyResult.getSex();
        if ("男".equalsIgnoreCase(sex)) {
            mRbSex0.setChecked(true);
        } else {
            mRbSex1.setChecked(true);
        }
        String source = identifyResult.getNation(); //民族
        mTxtRace.setText(source + "族");

        // 日期
        Date date = IDCardUtils.getBrothDate(idCardNO);
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            mTvBroth.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
        }

        // 年龄
        String age = String.valueOf(IDCardUtils.getAge(idCardNO));
        mEtAge.setText(age);

        // 户籍住址
        mEtAddressNow.setText(identifyResult.getAddress());
        signCardSendPath = filePath;
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
                case 200:
                    getInfoByCard();
                    break;
                default:
                    break;
            }

        }
    };

    void getInfoByCard() {
        String card = mEtCode.getText().toString();
        if (TextUtils.isEmpty(IdCardUtil.IDCardValidate(card))) {
            String sex = IdCardUtil.getSex(card);
            if ("1".equals(sex)) {
                mRgSex.check(R.id.rb_sex0);
            } else {
                mRgSex.check(R.id.rb_sex1);
            }
            mEtAge.setText(String.valueOf(IdCardUtil.getAge(IdCardUtil.getBirthday(card))));
            String birthday = IdCardUtil.getBirthday(card);
            birthday = DateTimeUtil.stringFromat(birthday, "yyyyMMdd", "yyyy-MM-dd");
            mTvBroth.setText(birthday);
        }
    }

    @Override
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

    }
}
