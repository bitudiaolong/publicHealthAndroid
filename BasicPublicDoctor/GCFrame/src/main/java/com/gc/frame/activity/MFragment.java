package com.gc.frame.activity;

import java.util.List;

import com.gc.frame.Frame;
import com.gc.frame.manage.MHandler;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MFragment extends Fragment {
    protected MHandler handler;
    
    protected View contextView = null;
    
    private LayoutInflater inflater;
    
    private ViewGroup viewgroup;
    
    public void setContentView(int contextview) {
        this.contextView = inflater.inflate(contextview, viewgroup, false);
    }
    
    public void setContextView(View contextview) {
        this.contextView = contextview;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        handler = new MHandler();
        String className = this.getClass().getName();
        handler.setId(className);
        handler.setMsglisnener(new MHandler.HandleMsgLisnener() {
            @Override
			public void onMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        removeFragment(MFragment.this);
                        break;
                    default:
                        break;
                }
            }
        });
        Frame.HANDLES.add(handler);
        try {
            if (null == savedInstanceState && null != getArguments()) {
                savedInstanceState = getArguments();
            }
            initcreate(savedInstanceState);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (null != getArguments()) {
			outState.putAll(getArguments());
		}
        super.onSaveInstanceState(outState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.viewgroup = container;
        try {
            create(savedInstanceState);
        }
        catch (Exception e) {
        }
        return this.contextView;
    }
    
    public View findViewById(int id) {
        if (contextView == null) {
            return null;
        }
        return contextView.findViewById(id);
    }
    
    /** 
     * ToDo：删除Fragment
     * @author M2_
     * @param fragment 非null的Fragment
     * @return void 
     * @throws 
     */
    protected void removeFragment(MFragment fragment) {
        FragmentActivity parent = getActivity();
        FragmentTransaction fragmentTransaction = parent.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment);
		fragmentTransaction.commitAllowingStateLoss();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Frame.HANDLES.remove(handler);
        destroy();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        pause();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        resume();
    }
    
    
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }
    
    
    public void close(String id) {
        Frame.HANDLES.close(id);
    }
    
    public List<MHandler> get(String id) {
        return Frame.HANDLES.get(id);
    }
    
    public MHandler getOne(String id) {
        if (Frame.HANDLES.get(id).size() > 0) {
            return Frame.HANDLES.get(id).get(0);
        }
        return null;
    }
    
    public void setId(String id) {
        handler.setId(id);
    }
    
    public String getHId() {
        return handler.getId();
    }
    
    protected void destroy() {
    }
    
    protected void resume() {
    };
    
    protected void pause() {
    };
    
    protected abstract void create(Bundle savedInstanceState);
    
    protected void initcreate(Bundle savedInstanceState) {
    }
    
}
