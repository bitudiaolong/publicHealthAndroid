package com.gc.basicpublicdoctor.fragment.thirdfragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.activity.AboutUsActivity;
import com.gc.basicpublicdoctor.activity.MainActivity;
import com.gc.basicpublicdoctor.activity.ModifyPasswordActivity;
import com.gc.basicpublicdoctor.activity.QrCodeShowActivity;
import com.gc.basicpublicdoctor.activity.SignExamineActivity;
import com.gc.basicpublicdoctor.activity.SignManagerActivity;
import com.gc.basicpublicdoctor.activity.VisitPlanActivity;
import com.gc.basicpublicdoctor.base.BaseFragment;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.view.RoundImageView;
import com.gc.basicpublicdoctor.view.XCRoundImageView;
import com.gc.utils.AppUtils;
import com.gc.utils.DensityUtils;
import com.gc.utils.SPUtils;
import com.squareup.picasso.Picasso;

import io.rong.imkit.RongIM;

/**
 * Author:Created by zhurui
 * Time:2018/7/10 上午10:53
 * Description:This is ThirdTabFragment
 */
public class ThirdTabFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private RoundImageView mXcIvHeadpic;
    /**
     * 贝利医生
     */
    private TextView mTvDocName;
    /**
     * 19912345678
     */
    private TextView mTvPhone;
    private TextView mTvSex;
    private TextView mTvAddress;
    private ImageView mIvSexPic;
    private LinearLayout mLlSignList;
    private LinearLayout mLlExamine;
    private LinearLayout mLlSignPlan;
    private LinearLayout mLlModifyPwd;
    private LinearLayout mLlAboutUs;
    private LinearLayout mLlLogout;
    private LinearLayout mLlQrCode;

    private TextView mTvVersion;
    private LinearLayout mLinHeaderInfo;

    public ThirdTabFragment() {
        // Required empty public constructor
    }

    public static ThirdTabFragment newInstance() {
        ThirdTabFragment fragment = new ThirdTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_third_tab, container, false);

        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initView(View view) {
        mXcIvHeadpic = (RoundImageView) view.findViewById(R.id.xc_iv_headpic);
        mXcIvHeadpic.setOnClickListener(this);
        mTvDocName = (TextView) view.findViewById(R.id.tv_doc_name);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mTvSex = (TextView) view.findViewById(R.id.tv_sex);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mIvSexPic = (ImageView) view.findViewById(R.id.iv_sex_pic);
        mLlSignList = (LinearLayout) view.findViewById(R.id.ll_sign_list);
        mLlSignList.setOnClickListener(this);
        mLlExamine = (LinearLayout) view.findViewById(R.id.ll_examine);
        mLlExamine.setOnClickListener(this);
        mLlSignPlan = (LinearLayout) view.findViewById(R.id.ll_sign_plan);
        mLlSignPlan.setOnClickListener(this);
        mLlModifyPwd = (LinearLayout) view.findViewById(R.id.ll_modify_pwd);
        mLlModifyPwd.setOnClickListener(this);
        mLlAboutUs = (LinearLayout) view.findViewById(R.id.ll_about_us);
        mLlAboutUs.setOnClickListener(this);
        mLlLogout = (LinearLayout) view.findViewById(R.id.ll_logout);
        mLlLogout.setOnClickListener(this);
        mLlQrCode = (LinearLayout) view.findViewById(R.id.ll_qr);
        mLlQrCode.setOnClickListener(this);
        mTvVersion = (TextView) view.findViewById(R.id.tv_version);
        mTvVersion.setText(AppUtils.getVerName(getContext()));
//        getApkVersion();
        mLinHeaderInfo = (LinearLayout) view.findViewById(R.id.lin_header_info);
        int screenWidth = DensityUtils.getScreenWidth(getContext());
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLinHeaderInfo.getLayoutParams();
        lp.setMargins(screenWidth / 2 - (int) getResources().getDimension(R.dimen.qb_px_360) / 2, 0, 0, DensityUtils.dp2px(getContext(), 60));

        if (TextUtils.isEmpty(Cons.userPicture) == false) {
            Picasso.with(getContext())
                    .load(Cons.userPicture)
                    .placeholder(R.drawable.bg_user_head_pic_default)
                    .error(R.drawable.bg_user_head_pic_default)
                    .into(mXcIvHeadpic);
        } else {
            mXcIvHeadpic.setImageResource(R.drawable.bg_user_head_pic_default);
        }
        mTvDocName.setText(Cons.userName);
        mTvPhone.setText(Cons.userPhone);
        mTvSex.setText(Cons.userSex);
        mTvAddress.setText(Cons.healthRoomName);

        if (Cons.userSex.contains("男")) {
            mIvSexPic.setBackground(getResources().getDrawable(R.drawable.icon_sex_man));
        } else {
            mIvSexPic.setBackground(getResources().getDrawable(R.drawable.icon_sex_women));
        }
    }

    /**
     * 获得apk版本name
     *
     * @author zhurui
     * @time 2018/7/17 上午10:12
     */
    private void getApkVersion() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            String version = info.versionName;
            mTvVersion.setText(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.xc_iv_headpic:
                break;
            case R.id.ll_sign_list:
                startActivity(new Intent(getContext(), SignManagerActivity.class));
                break;
            case R.id.ll_examine:
                startActivity(new Intent(getContext(), SignExamineActivity.class));
                break;
            case R.id.ll_sign_plan:
                startActivity(new Intent(getContext(), VisitPlanActivity.class));
                break;
            case R.id.ll_modify_pwd:
                startActivity(new Intent(getContext(), ModifyPasswordActivity.class));
                break;
            case R.id.ll_about_us:
//                Map<String, Boolean> map = new HashMap<String, Boolean>();
//                map.put(Conversation.ConversationType.PRIVATE.getName(), false);
//                RongIM.getInstance().startConversationList(getContext(), map);
                startActivity(new Intent(getContext(), AboutUsActivity.class));
                break;
            case R.id.ll_logout:
                // 保存用户token 账号 密码
                SPUtils.put(getContext(), "USERTOKEN", "");
                SPUtils.put(getContext(), "USERUID", "");
                SPUtils.put(getContext(), "USERPICTURE", "");
                SPUtils.put(getContext(), "USERPHONE", "");
                SPUtils.put(getContext(), "USERNAME", "");
                SPUtils.put(getContext(), "USERSEX", "");
                SPUtils.put(getContext(), "TEAMMEMBERS", "");
                SPUtils.put(getContext(), "HEALTHROOMNAME", "");
                SPUtils.put(getContext(), "DOCTEAMNAME", "");
                SPUtils.put(getContext(), "USERACCOUNT", "");
                SPUtils.put(getContext(), "USERPASSWORD", "");
                SPUtils.put(getContext(), "RONGTOKEN", "");

                RongIM.getInstance().logout();
                RongIM.getInstance().disconnect();

                Intent intent = new Intent(MainActivity.LOGOUT);
                getContext().sendBroadcast(intent);
                getActivity().finish();
                break;
            case R.id.ll_qr:
                startActivity(new Intent(getContext(), QrCodeShowActivity.class));
                break;
        }
    }
}
