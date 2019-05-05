package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.NewsMoreFragmPagerAdapter;
import com.gc.basicpublicdoctor.adapter.NewsRecommandMoreTitleAdapter;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.basicpublicdoctor.bean.NewsClassesListResponse;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.fragment.NewsRecommandMoreListFragment;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.basicpublicdoctor.view.NoScrollViewPager;
import com.gc.basicpublicdoctor.widget.dialog.DialogProgressBar;
import com.gc.utils.DensityUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author:Created by zhurui
 * Time:2018/7/27 下午1:38
 * Description:This is NewsRecommendMoreActivity
 * 卫计专线页面 新闻
 */
public class NewsRecommendMoreActivity extends BaseActivity implements DialogProgressBar.OnProgressListener {
    private NewsRecommandMoreTitleAdapter newsRecommandMoreTitleAdapter;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private NewsMoreFragmPagerAdapter newsMoreFragmPagerAdapter;
    private NoScrollViewPager mViewPager;

    private List<NewsClassesListResponse.DataBean.ListBean> listBeans = new ArrayList<>();
    private List<String> listId;
    private List<String> mDataList;

    DialogProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reconmmand_more);

        listId = new ArrayList<>();
        mDataList = new ArrayList<>();

        showTitleName("卫计专线", true);

        progressBar = new DialogProgressBar(context, "正在加载", 0, this);

        getNewsClassesList();


//        mDataList = Arrays.asList(channel);

    }

    private void initData() {
        String[] channel = new String[mDataList.size()];
        for (int i = 0; i < listId.size(); i++) {
            channel[i] = listId.get(i);

//            NewsRecommandMoreListFragment newsRecommandMoreListFragment = new NewsRecommandMoreListFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString(NewsRecommandMoreListFragment.NEWS_RECOMMAND_TYPE, channel[i]);
//            newsRecommandMoreListFragment.setArguments(bundle);
            fragmentList.add(NewsRecommandMoreListFragment.newInstance((i), channel[i]));
        }

        newsRecommandMoreTitleAdapter = new NewsRecommandMoreTitleAdapter(mDataList);
        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);

        newsMoreFragmPagerAdapter = new NewsMoreFragmPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(newsMoreFragmPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {

                } else {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        mViewPager.setCurrentItem(0);
//        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator(channel);
    }

    /**
     * 导航栏控件
     *
     * @author zhurui
     * @time 2018/7/18 下午1:42
     */
    private void initMagicIndicator(final String[] channel) {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setScrollPivotX(1.0f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#787878"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#040404"));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
//                        Fragment fragment = newsMoreFragmPagerAdapter.getItem(index);
//                        if (fragment instanceof NewsRecommandMoreListFragment) {
//                            ((NewsRecommandMoreListFragment) fragment).changeNewsType(channel[index]);
//                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 0));
                indicator.setColors(Color.parseColor("#26b2cb"));
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(DensityUtils.dp2px(context, 2));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.shape_divider));
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    /**
     * 卫计专线资讯分类接口
     *
     * @author zhurui
     * @time 2018/7/18 下午1:43
     */
    public void getNewsClassesList() {
        Map<String, String> map = DoctorApplication.getBaseMapList();
        map.put("doctorToken", Cons.doctorToken);
        map.put("userUid", Cons.userUid);
        progressBar.show();
        new HLHttpUtils().post(map, Cons.NEWS_CLASSES_LIST()).setCallBack(new AbstarctGenericityHttpUtils.CallBack<NewsClassesListResponse>() {
            @Override
            public void onSuccess(NewsClassesListResponse bean) {
                if (bean.getData().getList() != null) {
                    listBeans.addAll(bean.getData().getList());

                    for (int i = 0; i < listBeans.size(); i++) {
                        mDataList.add(listBeans.get(i).getName());
                        listId.add(listBeans.get(i).getId());
                    }
                    initData();
                }
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);

            }

            @Override
            public void onFailure(String code, String errormsg) {
                handler.sendEmptyMessage(Cons.LOADING_DISMISS);
            }

            @Override
            public void result(String result) {

            }
        });
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
