package com.gc.basicpublicdoctor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/17 下午2:26
 * Description:This is NewsMoreFragmPagerAdapter
 * 资讯新闻适配器viewpager
 */
public class NewsMoreFragmPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;

    public NewsMoreFragmPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mlist.size();//有几个页面
    }

}
