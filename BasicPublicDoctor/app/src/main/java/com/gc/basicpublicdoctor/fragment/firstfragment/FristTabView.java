package com.gc.basicpublicdoctor.fragment.firstfragment;


import com.gc.basicpublicdoctor.bean.NewsRecommendListResponse;
import com.gc.basicpublicdoctor.bean.SigningStatisticsResponse;

import java.util.List;

public interface FristTabView {

    void showToast(String msg);

    void showSignInfo(SigningStatisticsResponse bean);

    void showNews(NewsRecommendListResponse bean);

    void refreshNews(List<NewsRecommendListResponse.DataBean.ListBean> lstNews);

    void noNews();

    void loadMore(List<NewsRecommendListResponse.DataBean.ListBean> lstNews);

    void loadNoreNoMoreData();
}
