package com.gc.basicpublicdoctor.fragment.firstfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.activity.NewsRecommendMoreActivity;
import com.gc.basicpublicdoctor.activity.SignExamineActivity;
import com.gc.basicpublicdoctor.activity.SignManagerActivity;
import com.gc.basicpublicdoctor.activity.VisitPlanActivity;
import com.gc.basicpublicdoctor.activity.WebViewActivity;
import com.gc.basicpublicdoctor.adapter.NewsRecommandListAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.decoration.SpaceItemDecoration;
import com.gc.basicpublicdoctor.base.BaseFragment;
import com.gc.basicpublicdoctor.bean.NewsRecommendListResponse;
import com.gc.basicpublicdoctor.bean.SigningStatisticsResponse;
import com.gc.utils.DensityUtils;
import com.gc.utils.HelpUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/10 上午10:53
 * Description:This is FirstTabFragment
 */
public class FirstTabFragment extends BaseFragment implements View.OnClickListener, FristTabView {

    private LinearLayout mLlSign;
    private LinearLayout mLlExamine;
    private LinearLayout mLlVisit;
    /**
     * 1080
     */
    private TextView mTvNewSignNum;
    private LinearLayout mLlNewSign;
    /**
     * 1200
     */
    private TextView mTvCumulativeNum;
    private LinearLayout mLlCumulative;
    /**
     * 1200
     */
    private TextView mTvOverdueNum;
    private LinearLayout mLlOverdue;
    /**
     * 1050
     */
    private TextView mTvAuditsNum;
    private LinearLayout mLlAudits;
    private RelativeLayout mRlNewsReconmmand;

    private RecyclerView mRcyView;
    private TextView mTxtNodata;

    private SmartRefreshLayout mSwipeRefreshLayout;
    private NestedScrollView mSvMainContent;
    private LinearLayout mLlChangeColor;


    private int mScrollY = 0;

    private View mViewHeader;
    private LinearLayout mLinHeader;
    private ClassicsHeader mClassicsHeader;

    private NewsRecommandListAdapter newsRecommandListAdapter;
    private View view;

    public FirstTabFragment() {

    }

    public static FirstTabFragment getInstance() {
        FirstTabFragment fragment = new FirstTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    FirstTabPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_first_tab, container, false);
        presenter = new FirstTabPresenter(this);
        initView(view);
        initData();
        return view;

    }

    private void initView(View view) {
        mLlSign = (LinearLayout) view.findViewById(R.id.ll_sign);
        mLlSign.setOnClickListener(this);
        mLlExamine = (LinearLayout) view.findViewById(R.id.ll_examine);
        mLlExamine.setOnClickListener(this);
        mLlVisit = (LinearLayout) view.findViewById(R.id.ll_visit);
        mLlVisit.setOnClickListener(this);
        mTvNewSignNum = (TextView) view.findViewById(R.id.tv_new_sign_num);
        mLlNewSign = (LinearLayout) view.findViewById(R.id.ll_new_sign);
        mLlNewSign.setOnClickListener(this);
        mTvCumulativeNum = (TextView) view.findViewById(R.id.tv_cumulative_num);
        mLlCumulative = (LinearLayout) view.findViewById(R.id.ll_cumulative);
        mLlCumulative.setOnClickListener(this);
        mTvOverdueNum = (TextView) view.findViewById(R.id.tv_overdue_num);
        mLlOverdue = (LinearLayout) view.findViewById(R.id.ll_overdue);
        mLlOverdue.setOnClickListener(this);
        mTvAuditsNum = (TextView) view.findViewById(R.id.tv_audits_num);
        mLlAudits = (LinearLayout) view.findViewById(R.id.ll_audits);
        mLlAudits.setOnClickListener(this);
        mRlNewsReconmmand = (RelativeLayout) view.findViewById(R.id.rl_news_reconmmand);
        mRlNewsReconmmand.setOnClickListener(this);

        mRcyView = (RecyclerView) view.findViewById(R.id.rcy_view);
        mTxtNodata = (TextView) view.findViewById(R.id.txt_nodata);
        mSwipeRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSvMainContent = (NestedScrollView) view.findViewById(R.id.sv_main_content);
        mLlChangeColor = (LinearLayout) view.findViewById(R.id.ll_change_color);
        mViewHeader = (View) view.findViewById(R.id.view_header);
        mLinHeader = (LinearLayout) view.findViewById(R.id.lin_header);
        mClassicsHeader = (ClassicsHeader) view.findViewById(R.id.classics_header);

        mRcyView.setFocusable(false);
        mRcyView.setFocusableInTouchMode(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRcyView.setLayoutManager(linearLayoutManager);
        newsRecommandListAdapter = new NewsRecommandListAdapter(getContext());
        newsRecommandListAdapter.setOnItemClickListener(new MyOnItemClickListener());
        mRcyView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_1), true));
        mRcyView.setAdapter(newsRecommandListAdapter);

        mSwipeRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSwipeRefreshLayout.setNoMoreData(true);
                initData();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore();
            }

            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {
                Log.e("mSwipeRefreshLayout", "onHeaderPulling:percent= " + percent);
                if (percent == 0f) {
                    mLlChangeColor.setAlpha(1);
                } else {
                    mLlChangeColor.setAlpha(0);
                }

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int footerHeight, int extendHeight) {
                if (percent == 0f) {
                    mLlChangeColor.setAlpha(1);
                } else {
                    mLlChangeColor.setAlpha(0);
                }
                Log.e("mSwipeRefreshLayout", "onHeaderReleasing:percent= " + percent);
            }


        });
        mSvMainContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(70);
            private int color = ContextCompat.getColor(getContext(), R.color.app_toolBar) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    mLlChangeColor.setAlpha(1f * mScrollY / h);
                    mLlChangeColor.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                }
                lastScrollY = scrollY;
            }
        });

        int height = DensityUtils.getStatusHeight(getContext()) + DensityUtils.getActionBarHeight(getContext()) + DensityUtils.dp2px(getContext(), 50);
        RelativeLayout.LayoutParams lpViewHeader = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mViewHeader.setLayoutParams(lpViewHeader);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLinHeader.getLayoutParams();
        lp.setMargins(DensityUtils.dp2px(getContext(), 16), height - DensityUtils.dp2px(getContext(), 50), DensityUtils.dp2px(getContext(), 16), 0);
        mClassicsHeader.setSpinnerStyle(SpinnerStyle.Scale);
    }

    private void initData() {
        presenter.signingStatistics();
        presenter.refreshNewsList();
    }

    @Override
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg) == false && getContext() != null) {
            HelpUtil.showToast(getContext(), msg);
        }
    }

    @Override
    public void showSignInfo(SigningStatisticsResponse bean) {
        mTvNewSignNum.setText(bean.getData().getNewSigningNumber() + "");
        mTvCumulativeNum.setText(bean.getData().getCumulativeNumber() + "");
        mTvOverdueNum.setText(bean.getData().getOverdueNumber() + "");
        mTvAuditsNum.setText(bean.getData().getAuditsNumber() + "");
    }

    @Override
    public void showNews(NewsRecommendListResponse bean) {
        newsRecommandListAdapter.refreshNews(bean.getData().getList());
    }

    @Override
    public void refreshNews(List<NewsRecommendListResponse.DataBean.ListBean> lstNews) {
        mRcyView.setVisibility(View.VISIBLE);
        mTxtNodata.setVisibility(View.GONE);
        newsRecommandListAdapter.refreshNews(lstNews);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.finishRefresh(true);
        }
    }

    @Override
    public void noNews() {
        mRcyView.setVisibility(View.GONE);
        mTxtNodata.setVisibility(View.VISIBLE);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.finishRefresh(true);
        }
    }

    @Override
    public void loadMore(List<NewsRecommendListResponse.DataBean.ListBean> lstNews) {
        newsRecommandListAdapter.loadMore(lstNews);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.finishLoadMore(true);
        }
    }

    @Override
    public void loadNoreNoMoreData() {
        {
            mSwipeRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    class MyOnItemClickListener implements NewsRecommandListAdapter.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            String url = newsRecommandListAdapter.getItemByPosition(position).getDetailUrl();
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("flags", "卫计专线");
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_sign:
                startActivity(new Intent(getContext(), SignManagerActivity.class));
                break;
            case R.id.ll_examine:
                startActivity(new Intent(getContext(), SignExamineActivity.class));
                break;
            case R.id.ll_visit:
                startActivity(new Intent(getContext(), VisitPlanActivity.class));
                break;
            case R.id.ll_new_sign:
                break;
            case R.id.ll_cumulative:
                break;
            case R.id.ll_overdue:
                break;
            case R.id.ll_audits:
                break;
            case R.id.rl_news_reconmmand:
                startActivity(new Intent(getContext(), NewsRecommendMoreActivity.class));
                break;
            default:
                break;
        }
    }
}


