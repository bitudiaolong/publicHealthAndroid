package com.gc.basicpublicdoctor.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Author:Created by zhurui
 * Time:2018/7/10 上午10:51
 * Description:This is BaseFragment
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String getCurrentFragmentName(BaseFragment fragment) {
        String fragName = fragment.getClass().toString();
        fragName = fragName.substring(fragName.lastIndexOf(".") + 1, fragName.length());
        return fragName;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

}
