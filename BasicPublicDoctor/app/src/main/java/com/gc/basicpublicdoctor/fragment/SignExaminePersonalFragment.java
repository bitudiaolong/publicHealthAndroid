package com.gc.basicpublicdoctor.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.activity.SignDetailActivity;
import com.gc.basicpublicdoctor.adapter.SignPersonalListAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.BaseRecycleAdapter;
import com.gc.basicpublicdoctor.base.BaseFragment;
import com.gc.basicpublicdoctor.bean.SignPersonalListResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.utils.HelpUtil;
import com.gc.utils.MyGagcLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:Created by zhurui
 * Time:2018/7/30 上午9:52
 * Description:This is SignManagerPersonalFragment
 * 签约管理 个人列表
 */
public class SignExaminePersonalFragment extends BaseFragment {

    private static final String SIGN_PERSONAL = "sign_personal";

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private SignPersonalListAdapter adapter;
    private List<SignPersonalListResponse.DataBean.ListBean> list = new ArrayList<>();
    private int layoutId = R.layout.item_rl_visit_plan_crowd;

    private int[] colors = {R.color.colorPrimary};

    private int getStatus = 0;
    private int currentPage = 1;
    private int pageSize = 20;
    private boolean isRef = true;

    private LinearLayout llEmpty;

    boolean isVisible = false;
    boolean isViewInitiated = false;
    boolean isDataInitiated = false;

    private String signState;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SignExaminePersonalFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SignExaminePersonalFragment newInstance(int position) {
        SignExaminePersonalFragment fragment = new SignExaminePersonalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SIGN_PERSONAL, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void searchInfo() {
        swipeRefreshLayout.setRefreshing(true);
        refreshData();
    }

    public void refreshData() {
        isRef = true;
        currentPage = 1;
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            getStatus = getArguments().getInt(SIGN_PERSONAL);
            if (getStatus == 0) {
                signState = "11";
            } else if (getStatus == 1) {
                signState = "12";
            } else {
                signState = "13";
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_manager, container, false);
        this.isViewInitiated = true;
        initView(view);
        initData();

        return view;
    }

    private void initView(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);

        adapter = new SignPersonalListAdapter(getContext(), list, layoutId, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new MyOnScrollListener());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new MyOnRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(colors);


        adapter.setOnItemClickListener(new MyOnItemClickListener());
    }

    private void initData() {
        getSignPersonalList(Cons.doctorToken, Cons.userUid, "1", signState, String.valueOf(currentPage), String.valueOf(pageSize), "");
    }

    public void showT() {
        HelpUtil.showToast(getContext(), "ssssss");
    }

    /**
     * 签约列表--家庭
     *
     * @param token
     * @param userUid
     * @param signType  1 个人
     * @param signState 签约状态（0待签约 1已签约 2已解约 9已过期 11 待审核 12 待确认 13 已驳回）
     */
    public void getSignPersonalList(String token, String userUid, final String signType, String signState, String currentPage, String pageSize, String keyword) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", token);
        map.put("userUid", userUid);
        map.put("signType", signType);
        map.put("signState", signState);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        map.put("keyword", keyword);
        new HLHttpUtils().post(map, Cons.SIGN_PERSONAL_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<SignPersonalListResponse>() {
            @Override
            public void onSuccess(SignPersonalListResponse bean) {
                if (isRef) {
                    llEmpty.setVisibility(View.GONE);
                    list.clear();
                    swipeRefreshLayout.setRefreshing(false);
                }
                SignPersonalListResponse.DataBean dataBean = bean.getData();
                if (dataBean != null) {
                    List<SignPersonalListResponse.DataBean.ListBean> dataBeanList = dataBean.getList();
                    if (dataBeanList != null && dataBeanList.isEmpty() == false) {
                        llEmpty.setVisibility(View.GONE);
                        list.addAll(dataBeanList);
                    } else {
                        if (isRef) {
                            llEmpty.setVisibility(View.VISIBLE);
                        }
                    }

                } else {
                    if (isRef) {
                        llEmpty.setVisibility(View.VISIBLE);
                    }
                }

                isLoadMore = false;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String code, String errormsg) {
                isLoadMore = false;
                if (isRef) {
                    llEmpty.setVisibility(View.VISIBLE);
                }
                MyGagcLog.getInstance().showLogE("errorcode[" + errormsg + "]errmsg[" + errormsg + "]");

                HelpUtil.showToast(getContext(), errormsg+"");
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void result(String result) {

            }
        });
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

    private void loadData() {
        isRef = false;
        isLoadMore = true;
        currentPage++;
        initData();
    }

    class MyOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            refreshData();
        }
    }

    private int lastVisibleItem = 0;
    private boolean isLoadMore = false;

    class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && isLoadMore == false && swipeRefreshLayout.isRefreshing() == false) {
                loadData();
            }
        }

    }

    class MyOnItemClickListener implements BaseRecycleAdapter.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Intent intent;
            if (getStatus == 0) {
                SignPersonalListResponse.DataBean.ListBean bean = list.get(position);
                intent = new Intent(getContext(), SignDetailActivity.class);
                intent.putExtra("signId", bean.getSignId());
                intent.putExtra("signState", bean.getSignState());
                startActivity(intent);
            } else if (getStatus == 1) {
                SignPersonalListResponse.DataBean.ListBean bean = list.get(position);
                intent = new Intent(getContext(), SignDetailActivity.class);
                intent.putExtra("signId", bean.getSignId());
                intent.putExtra("signState", bean.getSignState());
                startActivity(intent);
            } else {
                SignPersonalListResponse.DataBean.ListBean bean = list.get(position);
                intent = new Intent(getContext(), SignDetailActivity.class);
                intent.putExtra("signId", bean.getSignId());
                intent.putExtra("signState", bean.getSignState());
                startActivity(intent);
            }
        }
    }
}
