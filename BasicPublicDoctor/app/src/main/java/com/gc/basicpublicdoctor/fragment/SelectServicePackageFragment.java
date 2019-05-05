package com.gc.basicpublicdoctor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.activity.SelectServicePackageActivity;
import com.gc.basicpublicdoctor.adapter.SelectServicePackageListAdapter;
import com.gc.basicpublicdoctor.base.BaseFragment;
import com.gc.basicpublicdoctor.bean.ServiceContentBean;
import com.gc.basicpublicdoctor.bean.ServicePackageListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/8/2 上午10:41
 * Description:This is SelectServicePackageFragment
 * 选择服务包服务项页面
 */
public class SelectServicePackageFragment extends BaseFragment {

    private static final String SELECT_PACKAGE = "select_package";
    private static final String SELECT_PACKAGE_ID = "select_package_id";
    private static final String SELECT_PACKAGE_NAME = "select_package_name";

    // 选择的服务包和服务项列表
    private List<ServicePackageListBean> servicePackageListBeanListSelected = new ArrayList<>();

    private SelectServicePackageListAdapter adapter;
    private List<ServicePackageListBean> servicePackageListBeanList;
    private List<ServiceContentBean> serviceContentBeanList = new ArrayList<>();
    private int layoutId = R.layout.item_select_service_package;

    private int positon = 0;
    private int getStatus = 0;
    private String servicePackageId;
    private String servicePackageName;

    boolean isVisible = false;

    private View view;
    private CheckBox mCbSelectAll;
    private TextView mTvLine;
    private RecyclerView mRecy;
    private LinearLayout mLlEmpty;
    private LinearLayout mLlTitleCheckBox;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelectServicePackageFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }

    public void setData(int position, List<ServicePackageListBean> servicePackageListBeanList) {
        this.positon = position;
        this.servicePackageListBeanList = servicePackageListBeanList;
    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SelectServicePackageFragment newInstance(int position, String servicePackageId, String servicePackageName,
                                                           List<ServicePackageListBean> servicePackageListBeanList) {
        SelectServicePackageFragment fragment = new SelectServicePackageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECT_PACKAGE, position);
        bundle.putString(SELECT_PACKAGE_ID, servicePackageId);
        bundle.putString(SELECT_PACKAGE_NAME, servicePackageName);
        bundle.putSerializable("servicePackageListBeanList", (Serializable) servicePackageListBeanList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            getStatus = getArguments().getInt(SELECT_PACKAGE);
//            servicePackageId = getArguments().getString(SELECT_PACKAGE_ID);
//            servicePackageName = getArguments().getString(SELECT_PACKAGE_NAME);
//            servicePackageListBeanList = (List<ServicePackageResponse.DataBean.ServicePackageListBean>) getArguments().getSerializable("servicePackageListBeanList");
//            serviceContentBeanList = servicePackageListBeanList.get(getStatus).getServiceContent();
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_service_package, container, false);

        serviceContentBeanList = servicePackageListBeanList.get(positon).getServiceContent();

        initView(view);
        initData();

        return view;
    }

    private void initView(View view) {
        mCbSelectAll = (CheckBox) view.findViewById(R.id.cb_select_all);
        mTvLine = (TextView) view.findViewById(R.id.tv_line);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mLlEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        mLlTitleCheckBox = (LinearLayout) view.findViewById(R.id.ll_title_check_box);

        adapter = new SelectServicePackageListAdapter(getContext(), serviceContentBeanList, layoutId);

        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mRecy.setHasFixedSize(true);
        mRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecy.setAdapter(adapter);

        if (serviceContentBeanList.size() == 0) {
            mLlTitleCheckBox.setVisibility(View.INVISIBLE);
        }
        boolean flag = true;
        for (ServiceContentBean bean : serviceContentBeanList) {
            if (bean.isSelected() == false) {
                flag = false;
                break;
            }
        }
        if (flag) {
            mCbSelectAll.setChecked(true);
        }
        // 主选框
        mCbSelectAll.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        // item
        adapter.setMyOnCheckedChangeListener(new SelectServicePackageListAdapter.MyOnCheckedChangeListener() {
            @Override
            public void onItemCheck(View view, int position, boolean isCheck) {
                serviceContentBeanList.get(position).setSelected(isCheck);

                if (!isCheck) {
                    mCbSelectAll.setOnCheckedChangeListener(null);
                    mCbSelectAll.setChecked(false);
                    mCbSelectAll.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
                }
                Intent intent = new Intent(SelectServicePackageActivity.SELECT_SERVICE_PACKAGE);
                getActivity().sendBroadcast(intent);
            }
        });

    }

    private void initData() {
    }

    class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            for (ServiceContentBean bean : serviceContentBeanList) {
                bean.setSelected(isChecked);
            }
            Intent intent = new Intent(SelectServicePackageActivity.SELECT_SERVICE_PACKAGE);
            getActivity().sendBroadcast(intent);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
