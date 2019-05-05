package com.gc.frame.activity;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

public abstract class MActivityGroup extends OActivityGroup {
    private List<MTabSpec> childList = new ArrayList<MTabSpec>();
    
    /**@Fields currentId : TODO */
    private int currentId = 0;
    
    /**@Fields lastcurrent : TODO */
    private int lastcurrent = -1;
    
    /**@Fields defaul : 默认页面ID */
    private int defaul = -1;
    
    private long time = 0;
    
    private int orientation = 0;
    
    private ViewFlipper containerFlipper;
    
    private boolean remstate = true, inited = false;
    
    private OnCurrAct mOnCurrAct;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }
    
    /** 
     * ToDo：切换Activity
     * @author M2_
     * @param id
     * @param intent 
     * @return void 
     * @throws 
     */
    public void switchActivity(String id, Intent intent) {
        if (System.currentTimeMillis() - time < 200) {
            return;
        }
        String lid = id;
        time = System.currentTimeMillis();
        id += time;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Window window = getLocalActivityManager().startActivity(id, intent);
        View v = window.getDecorView();
        v.setTag(new Tags(id, lid));
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        v.setLayoutParams(param);
        try {
            {
                TranslateAnimation leftin = new TranslateAnimation(TranslateAnimation.INFINITE,
                        containerFlipper.getWidth(), TranslateAnimation.INFINITE, 0, 0, 0, 0, 0);
                leftin.setDuration(200);
                containerFlipper.setInAnimation(leftin);
            }
            {
                TranslateAnimation leftin = new TranslateAnimation(TranslateAnimation.INFINITE, 0,
                        TranslateAnimation.INFINITE, -containerFlipper.getWidth(), 0, 0, 0, 0);
                leftin.setDuration(200);
                containerFlipper.setOutAnimation(leftin);
            }
        }
        catch (NotFoundException e) {
            e.printStackTrace();
        }
        containerFlipper.addView(v, new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        containerFlipper.showNext();
        if (mOnCurrAct != null) {
            mOnCurrAct.onCurr(false, ((Tags) v.getTag()).id, ((Tags) v.getTag()).tag);
        }
    }
    
    /** 
     * ToDo：回上个页面
     * @author M2_ 
     * @return void 
     * @throws 
     */
    public void back() {
        if (containerFlipper.getChildCount() > 1) {
            View view = containerFlipper.getCurrentView();
            String id = ((Tags) view.getTag()).id;
            {
                TranslateAnimation leftin = new TranslateAnimation(TranslateAnimation.INFINITE,
                        -containerFlipper.getWidth(), TranslateAnimation.INFINITE, 0, 0, 0, 0, 0);
                leftin.setDuration(200);
                containerFlipper.setInAnimation(leftin);
            }
            {
                TranslateAnimation leftin = new TranslateAnimation(TranslateAnimation.INFINITE, 0,
                        TranslateAnimation.INFINITE, containerFlipper.getWidth(), 0, 0, 0, 0);
                leftin.setDuration(200);
                containerFlipper.setOutAnimation(leftin);
            }
            containerFlipper.showPrevious();
            containerFlipper.removeView(view);
            if (mOnCurrAct != null) {
                mOnCurrAct.onCurr(containerFlipper.getChildCount() == 1, ((Tags) containerFlipper.getCurrentView()
                        .getTag()).id, ((Tags) containerFlipper.getCurrentView().getTag()).tag);
            }
            finishActivity(id);
        }
        else {
            finish();
        }
    }
    
    /** 
     * ToDo：根据参数id设置当前页面
     * @author M2_
     * @param id 
     * @return void 
     * @throws 
     */
    public void setCurrent(int id) {
        if (inited) {
            MTabSpec mtabSpec = findChild(id);
            setCurrent(mtabSpec);
        }
        else {
            setDefault(id);
        }
    }
    
    public void setCurrent(MTabSpec mtabSpec) {
        if (mtabSpec != null) {
            if (currentId != mtabSpec.id) {
                mtabSpec.initView();
                int move_direction = 0;
                if (lastcurrent < mtabSpec.ind) {
                    move_direction = orientation == 0 ? 0 : 1;
                }
                else {
                    move_direction = orientation == 0 ? 4 : 3;
                }
                contentLayout.adView(mtabSpec.viewFlipper, currentId, mtabSpec.id, mtabSpec.ind, move_direction);
                containerFlipper = mtabSpec.viewFlipper;
                lastcurrent = mtabSpec.ind;
                currentId = mtabSpec.id;
                if (mOnCurrAct != null) {
                    mOnCurrAct.onCurr(containerFlipper.getChildCount() == 1, ((Tags) containerFlipper.getCurrentView()
                            .getTag()).id, ((Tags) containerFlipper.getCurrentView().getTag()).tag);
                }
            }
        }
    }
    
    public MTabSpec findChild(int id) {
        for (MTabSpec mts : childList) {
            if (mts.id == id) {
                return mts;
            }
        }
        return null;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (containerFlipper != null && containerFlipper.getChildCount() > 1) {
                back();
                return true;
            }
        }
        if (onkeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    public boolean onkeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    
    /** 
     * ToDo：初始化MActivityGroup
     * @author M2_
     * @param savedInstanceState 
     * @return void 
     * @throws 
     */
    private void init(Bundle savedInstanceState) {
        MTabSpec def = findChild(defaul);
        if (def == null) {
            if (childList.size() > 0) {
                def = childList.get(0);
            }
        }
        else {
            setCurrent(def);
        }
        setCurrent(def);
        inited = true;
    }
    
    /** 
     * ToDo：添加子页面
     * @author M2_
     * @param id    标志
     * @param tab   标题
     * @param intent 
     * @return void 
     * @throws 
     */
    public void addChild(int id, String tab, Intent intent) {
        MTabSpec mtabspec = new MTabSpec(id, tab, intent);
        childList.add(mtabspec);
        initInd();
    }
    
    private void initInd() {
        for (int i = 0; i < childList.size(); i++) {
            MTabSpec mtabspec = childList.get(i);
            mtabspec.ind = i;
        }
        lastcurrent = currentId;
    }
    
    private class MTabSpec {
        public int id = 0;
        
        public String tab = "";
        
        public Intent intent;
        
        public ViewFlipper viewFlipper;
        
        public int ind = 0;
        
        private MTabSpec(int id, String tab, Intent intent) {
            this.id = id;
            this.tab = tab;
            this.intent = intent;
        }
        
        public void initView() {
            if (viewFlipper == null) {
                viewFlipper = new ViewFlipper(MActivityGroup.this);
                viewFlipper.setId(id);
            }
            else if (viewFlipper.getChildCount() <= 1 || !remstate) {
                getIn(tab, intent);
                return;
            }
            else {
                getIn(tab, intent);
                return;
            }
            View view = getIn(tab, intent);
            view.setTag(new Tags(id + "", tab));
            viewFlipper.addView(view, new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        }
    }
    
    public void setDefault(int defaul) {
        this.defaul = defaul;
    }
    
    /** 
     * ToDo：获取当前页面的ID
     * @author M2_
     * @return 
     * @return int 
     * @throws 
     */
    public int getCurrentId() {
        return currentId;
    }
    
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    
    public void setRemstate(boolean remstate) {
        this.remstate = remstate;
    }
    
    public void setOnCurrAct(OnCurrAct mOnCurrAct) {
        this.mOnCurrAct = mOnCurrAct;
    }
    
    /** 
     * @Title: OnCurrAct 
     * @ToDo:  页面切换监听回调
     * @author M2_
     * @version v 1.0
     * @date [2015年1月19日下午1:48:00]
     */
    public interface OnCurrAct {
        /** 
         * ToDo：页面切换监听回调
         * @author M2_
         * @param istop
         * @param id
         * @param tag 
         * @return void 
         * @throws 
         */
        public void onCurr(boolean istop, String id, Object tag);
    }
    
    /** 
     * @Title: Tags 
     * @ToDo: 子页面标记对
     * @author M2_
     * @version v 1.0
     * @date [2015119日下1:48:48]
     */
    private class Tags {
        public String id;
        
        public String tag;
        
        public Tags(String id, String tag) {
            this.id = id;
            this.tag = tag;
        }
    }
}
