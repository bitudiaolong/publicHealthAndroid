package com.gc.basicpublicdoctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.activity.WebViewActivity;
import com.gc.basicpublicdoctor.adapter.NewsRecommandListAdapter;
import com.gc.basicpublicdoctor.adapter.baserecycleviewadapter.decoration.SpaceItemDecoration;
import com.gc.basicpublicdoctor.base.BaseFragment;
import com.gc.basicpublicdoctor.bean.NewsRecommendListResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.HelpUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Author:Created by zhurui
 * Time:2018/7/17 下午2:36
 * Description:This is NewsRecommandMoreListFragment
 * 新闻资讯分类列表页面
 */
public class NewsRecommandMoreListFragment extends BaseFragment implements
        DialogProgressBar.OnProgressListener {
    public static final String EXTRA_TEXT = "extra_text";
    public static final String CURRENT_POSITION = "current_position";
    public static final String NEWS_RECOMMAND_TYPE = "news_recommand_type";
    private static final int PAGE_SIZE = 20;

    private View view;
    private RecyclerView mRvVideosList;
    /**
     * 暂无数据
     */
    private LinearLayout mLlEmpty;
    private ClassicsFooter mClassicsFooter;
    private SmartRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private NewsRecommandListAdapter newsRecommandListAdapter;
    private List<NewsRecommendListResponse.DataBean.ListBean> listBeans = new ArrayList<>();
    /**
     * 当前页
     */
    int currentPage = 1;
    /**
     * 总共的页数
     */
    int totalPageCount;

    private TextView mTvName;

    private int currentPosition;
    private String newsClassesId;

    DialogProgressBar progressBar;
    boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NewsRecommandMoreListFragment newInstance(int position, String newsClassesId) {
        NewsRecommandMoreListFragment fragment = new NewsRecommandMoreListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_POSITION, position);
        bundle.putString(NEWS_RECOMMAND_TYPE, newsClassesId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPosition = getArguments().getInt(CURRENT_POSITION);
            newsClassesId = getArguments().getString(NEWS_RECOMMAND_TYPE);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_reconmmand_more, container, false);

        initView(view);
        initData();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            newsClassesId = bundle.getString(EXTRA_TEXT);
////            mTvName.setText(newsClassesId);
//        }
    }

    private void initView(View view) {
        mRvVideosList = (RecyclerView) view.findViewById(R.id.rv_videos_list);
        mLlEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        mClassicsFooter = (ClassicsFooter) view.findViewById(R.id.classics_footer);
        mSwipeRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mTvName = (TextView) view.findViewById(R.id.tv_name);

//        progressBar = new DialogProgressBar(getContext(), "正在加载", 0, this);
//        progressBar.show();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRvVideosList.setLayoutManager(linearLayoutManager);
        newsRecommandListAdapter = new NewsRecommandListAdapter(getContext(), listBeans);
        newsRecommandListAdapter.setOnItemClickListener(new MyOnItemClickListener());
//        mRcyView.addItemDecoration(new DividerLinearItemDecoration(Color.GRAY, 2, Color.GRAY, 2));
        mRvVideosList.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.dp_1), true));
        mRvVideosList.setAdapter(newsRecommandListAdapter);
//        mSwipeRefreshLayout.setOnRefreshListener(new MyRefreshListener());
        mSwipeRefreshLayout.setOnRefreshLoadMoreListener(new RefreshLoadmoreListener());

    }

    private void initData() {
        getNewsRecommandList(Cons.doctorToken, Cons.userUid, newsClassesId, true);
    }

    public void changeNewsType(String newsClassesId) {
        getNewsRecommandList(Cons.doctorToken, Cons.userUid, newsClassesId, true);
    }

    /**
     * 卫计专线接口
     *
     * @author zhurui
     * @time 2018/7/18 下午1:43
     */
    public void getNewsRecommandList(String token, String userUid, String newsClassesId, final boolean isRefresh) {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", token);
        map.put("userUid", userUid);
        map.put("newsClassesId", newsClassesId);
        map.put("currentPage", String.valueOf(currentPage));
        map.put("pageSize", String.valueOf(PAGE_SIZE));

        new HLHttpUtils().post(map, Cons.NEWS_RECOMMAND_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<NewsRecommendListResponse>() {
            @Override
            public void onSuccess(NewsRecommendListResponse bean) {
                if (bean.getData() != null) {
                    if (isRefresh) {
                        mSwipeRefreshLayout.finishRefresh();
                        listBeans.clear();
                    } else {
                        mSwipeRefreshLayout.finishLoadMore();
                    }

                    if (bean.getData().getPage() != null) {
                        int currentPageTemp = bean.getData().getPage().getCurrentPage();
                        int totalPageCountTemp = bean.getData().getPage().getTotalPageCount();
                        currentPage = currentPageTemp;
                        totalPageCount = totalPageCountTemp;
                    }

                    if (bean.getData().getList() != null) {
                        mLlEmpty.setVisibility(View.GONE);
                        mRvVideosList.setVisibility(View.VISIBLE);
                        listBeans.addAll(bean.getData().getList());
                        newsRecommandListAdapter.notifyDataSetChanged();
                    }
                    if (listBeans.size() == 0) {
                        mLlEmpty.setVisibility(View.VISIBLE);
                        mRvVideosList.setVisibility(View.GONE);
                    }
                }
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
            }

            @Override
            public void onFailure(String code, String errormsg) {
                HelpUtil.showToast(getContext(), errormsg);
                if (isRefresh) {
                    mSwipeRefreshLayout.finishRefresh(false);
                } else {
                    mSwipeRefreshLayout.finishLoadMore(false);
                }
            }

            @Override
            public void result(String result) {

            }
        });
    }

    // recycleview点击事件
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

    class MyRefreshListener implements OnRefreshListener {

        @Override
        public void onRefresh(RefreshLayout refreshlayout) {
            getNewsRecommandList(Cons.doctorToken, Cons.userUid, newsClassesId, true);
        }
    }

    class RefreshLoadmoreListener implements OnRefreshLoadMoreListener {

        @Override
        public void onRefresh(RefreshLayout refreshlayout) {
            currentPage = 1;
            mSwipeRefreshLayout.setNoMoreData(true);
            getNewsRecommandList(Cons.doctorToken, Cons.userUid, newsClassesId, true);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            currentPage++;
            if (currentPage <= totalPageCount) {
                getNewsRecommandList(Cons.doctorToken, Cons.userUid, newsClassesId, false);
            } else {
                mSwipeRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
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
    public void exec(int callViewById) {

    }

    @Override
    public void finish(int callViewById) {

    }
}
