package com.gc.basicpublicdoctor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.gc.utils.MyGagcLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/27 下午1:39
 * Description:This is SignManagerFragmentPageAdapter
 * 签约管理viewpager适配器
 */
public class SignManagerFragmentPageAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;    // 创建 FragmentManager
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> tags;

    public SignManagerFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.tags = new ArrayList<>();
        this.fragmentManager = fm;
        this.fragmentList = fragmentList;
    }

    public void setNewFragments(List<Fragment> fragments) {
        if (this.tags != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < tags.size(); i++) {
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(tags.get(i)));
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
            tags.clear();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MyGagcLog.getInstance().showLogE("instantiateItem position[" + position + "]");
        tags.add(String.valueOf(position));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragmentList.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
