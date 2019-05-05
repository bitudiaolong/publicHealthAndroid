package com.gc.basicpublicdoctor.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.ConfirmRejectResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.utils.HelpUtil;

import java.util.Map;

public class RemarkRecjectActivity extends BaseActivity {

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
    /**
     * 请输入......
     */
    private EditText mEdtRemarks;
    private String input = "";

    // 驳回时候使用签约id
    private String signUid;
    // 是否可以点击修改 默认true 可以修改 false 不可修改
    boolean isClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_recject);

        initView();

        final String flags = getIntent().getStringExtra("entertype");
        signUid = getIntent().getStringExtra("signUid") == null ? "" : getIntent().getStringExtra("signUid");
        isClicked = getIntent().getBooleanExtra("isClicked", true);
        if ("备注".equals(flags)) {
            showTitleName("备注", true);
            input = getIntent().getStringExtra("remarks");

            if (!isClicked) {
                mEdtRemarks.setFocusable(false);
                mEdtRemarks.setFocusableInTouchMode(false);
                mEdtRemarks.setCursorVisible(false);
            }
            mEdtRemarks.setText(input);
            mEdtRemarks.setSelection(input.length());
            mEdtRemarks.setHint("请填写备注...");
        } else if ("驳回".equals(flags)) {
            showTitleName("驳回", true);
            mEdtRemarks.setHint("请填写驳回原因...");
        }

        showRightBtn("保存", true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("备注".equals(flags)) {
                    Intent intent = new Intent();
                    String remarks = input == null ? "" : input;
                    intent.putExtra("remarks", remarks);
                    setResult(SelectServicePackageActivity.RESULTCODE, intent);
                    finish();
                } else if ("驳回".equals(flags)) {
                    if (input.equals("") || input == null) {
                        HelpUtil.showToast(context, "驳回原因不能为空");
                    } else {
                        showAlertDialog();
                    }

                }

            }
        });
        if (!isClicked) {
            showRightBtn("", false, null);
        }
    }

    // 删除阻止
    public void showAlertDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("提示").setMessage("是否确定驳回？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getConfirmReject(signUid, input);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        alertDialog.show();
    }

    /**
     * 确认驳回接口
     */
    private void getConfirmReject(String signUid, String rejectReason) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("signUid", signUid);
        map.put("rejectReason", rejectReason);

        new HLHttpUtils().post(map, Cons.CONFIRM_REJECT()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<ConfirmRejectResponse>() {
            @Override
            public void onSuccess(ConfirmRejectResponse bean) {
                Intent intent = new Intent();
                intent.setAction(SignDetailActivity.BOHUI);
                intent.putExtra("rejectReason", input);
                sendBroadcast(intent);
                Intent intent1 = new Intent();
                intent1.setAction(SignExamineActivity.SIGN_EXAMINE_REFRESH);
                sendBroadcast(intent1);
                finish();
            }

            @Override
            public void onFailure(String code, String errormsg) {
                HelpUtil.showToast(context, "驳回失败，原因为:" + errormsg);
            }

            @Override
            public void result(String result) {

            }
        });


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
        mEdtRemarks = (EditText) findViewById(R.id.edt_remarks);

        mEdtRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input = s.toString();
                if (input.length() == 150) {
                    HelpUtil.showToast(context, "最多可输入150字!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}
