package com.gc.basicpublicdoctor.fragment.secondfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.adapter.NewsMoreFragmPagerAdapter;
import com.gc.basicpublicdoctor.base.BaseFragment;
import com.gc.basicpublicdoctor.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Author:Created by zhurui
 * Time:2018/7/10 上午10:53
 * Description:This is SecondTabFragment
 */
public class SecondTabFragment extends BaseFragment {
    public static final String HEAD_IMG = "headImg";
    public static final String REFRESH_RYTOKEN = "REFRESH_RYTOKEN";
    public static final String MESSAGE_RECEIVED_ACTION = "com.gc.basicpublicdoctor.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private View view;
    private View mView;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private NewsMoreFragmPagerAdapter newsMoreFragmPagerAdapter;
    private NoScrollViewPager mViewPager;

    public SecondTabFragment() {
        // Required empty public constructor
    }

    public static SecondTabFragment newInstance() {
        SecondTabFragment fragment = new SecondTabFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_second_tab, container, false);

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
        mView = (View) view.findViewById(R.id.view);
        mViewPager = (NoScrollViewPager) view.findViewById(R.id.view_pager);

        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                .build();
        fragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

        fragmentList.add(fragment);
        newsMoreFragmPagerAdapter = new NewsMoreFragmPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(newsMoreFragmPagerAdapter);
    }
}
