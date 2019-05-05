package com.gc.basicpublicdoctor.fragment.firstfragment;


import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.bean.NewsRecommendListResponse;
import com.gc.basicpublicdoctor.bean.SigningStatisticsResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.utils.LogUtil;

import java.util.Map;


public class FirstTabPresenter {
    FristTabView fristTabView;
    int totalPageCount = 1;
    int currentPage = 1;
    private static final int PAGE_SIZE = 20;


    public FirstTabPresenter(FristTabView fristTabView) {
        this.fristTabView = fristTabView;
    }


    public void signingStatistics() {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        new HLHttpUtils().post(map, Cons.SIGNING_STATISTICS()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<SigningStatisticsResponse>() {
            @Override
            public void onSuccess(SigningStatisticsResponse bean) {
                if (bean.getData() != null) {
                    fristTabView.showSignInfo(bean);
                }

            }

            @Override
            public void onFailure(String code, String errormsg) {

                fristTabView.showToast(errormsg);
            }

            @Override
            public void result(String result) {

            }
        });
    }

    public void getNewsRecommandList() {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("newsClassesId", "");
        map.put("currentPage", String.valueOf(currentPage));
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        new HLHttpUtils().post(map, Cons.NEWS_RECOMMAND_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<NewsRecommendListResponse>() {
            @Override
            public void onSuccess(NewsRecommendListResponse bean) {
                fristTabView.showNews(bean);
            }

            @Override
            public void onFailure(String code, String errormsg) {
                fristTabView.showToast(errormsg);
            }

            @Override
            public void result(String result) {

            }
        });
    }

    public void refreshNewsList() {
        currentPage = 1;
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("newsClassesId", "");
        map.put("currentPage", String.valueOf(currentPage));
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        new HLHttpUtils().post(map, Cons.NEWS_RECOMMAND_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<NewsRecommendListResponse>() {
            @Override
            public void onSuccess(NewsRecommendListResponse bean) {
                if (bean.getData().getList() != null || bean.getData().getList().size() != 0) {
                    fristTabView.refreshNews(bean.getData().getList());
                } else {
                    fristTabView.noNews();
                }
                if (bean.getData().getPage() != null) {
                    totalPageCount = bean.getData().getPage().getTotalPageCount();
                }
            }

            @Override
            public void onFailure(String code, String errormsg) {
                LogUtil.d("refreshNewsList", "errormsg==" + errormsg);
                fristTabView.noNews();
            }

            @Override
            public void result(String result) {
                LogUtil.e("infoD", "newsList_result: " + result);
            }
        });
    }

    public void loadMore() {
        currentPage++;
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        map.put("newsClassesId", "");
        map.put("currentPage", String.valueOf(currentPage));
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        new HLHttpUtils().post(map, Cons.NEWS_RECOMMAND_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<NewsRecommendListResponse>() {
            @Override
            public void onSuccess(NewsRecommendListResponse bean) {
                if (bean.getData().getList() != null || bean.getData().getList().size() != 0) {
                    fristTabView.loadMore(bean.getData().getList());
                } else {
                    fristTabView.loadNoreNoMoreData();
                }
                if (bean.getData().getPage() != null) {
                    totalPageCount = bean.getData().getPage().getTotalPageCount();
                }
            }

            @Override
            public void onFailure(String code, String errormsg) {
                LogUtil.d("refreshNewsList", "errormsg==" + errormsg);
                fristTabView.loadNoreNoMoreData();
            }

            @Override
            public void result(String result) {
                LogUtil.e("infoD", "newsList_result: " + result);
            }
        });
    }
}
