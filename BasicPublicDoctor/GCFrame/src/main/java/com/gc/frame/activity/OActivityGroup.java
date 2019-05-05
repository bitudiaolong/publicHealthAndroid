package com.gc.frame.activity;


import com.gc.frame.widget.GroupContextView;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

public abstract class OActivityGroup extends MActivity {
    protected LocalActivityManager mLocalActivityManager;
    
    private static final String STATES_KEY = "android:states";
    
    static final String PARENT_NON_CONFIG_INSTANCE_KEY = "android:parent_non_config_instance";
    
    protected GroupContextView contentLayout;
    
    public static final int FOCUS_AFTER_DESCENDANTS = 0x40000;
    
    public OActivityGroup() {
        this(true);
    }
    
    public OActivityGroup(boolean singleActivityMode) {
        mLocalActivityManager = new LocalActivityManager(this, singleActivityMode);
    }
    
    public void setContentLayout(GroupContextView amlayout) {
        this.contentLayout = amlayout;
    }
    
    protected View getIn(String tag, Intent intent) {
        IntentContentStrategy ics = new IntentContentStrategy(tag, intent);
        return ics.getContentView();
    }
    
    private static interface ContentStrategy {
        View getContentView();
    }
    
    private class IntentContentStrategy implements ContentStrategy {
        private final String mTag;
        
        private final Intent mIntent;
        
        private View mLaunchedView;
        
        private IntentContentStrategy(String tag, Intent intent) {
            mTag = tag;
            mIntent = intent;
        }
        
        public View getContentView() {
            Window w = getLocalActivityManager().startActivity(mTag, mIntent);
            View wd = w != null ? w.getDecorView() : null;
            if (wd != null) {
                wd.setVisibility(View.VISIBLE);
                wd.setFocusableInTouchMode(false);
                ViewGroup vg = (ViewGroup) wd;
                vg.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
                if (vg.getChildCount() > 0 && vg.getChildAt(0) instanceof FrameLayout) {
                    FrameLayout vm = (FrameLayout) vg.getChildAt(0);
                    vm.setForeground(null);
                }
            }
            mLaunchedView = wd;
            return mLaunchedView;
        }
    }
    
    public void finishActivity(String id) {
        getLocalActivityManager().destroyActivity(id, true);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle states = savedInstanceState != null ? (Bundle) savedInstanceState.getBundle(STATES_KEY) : null;
        mLocalActivityManager.dispatchCreate(states);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle state = mLocalActivityManager.saveInstanceState();
        if (state != null) {
            outState.putBundle(STATES_KEY, state);
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(isFinishing());
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        mLocalActivityManager.dispatchStop();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalActivityManager.dispatchDestroy(isFinishing());
    }
    
    public Activity getCurrentActivity() {
        return mLocalActivityManager.getCurrentActivity();
    }
    
    public final LocalActivityManager getLocalActivityManager() {
        return mLocalActivityManager;
    }
}
