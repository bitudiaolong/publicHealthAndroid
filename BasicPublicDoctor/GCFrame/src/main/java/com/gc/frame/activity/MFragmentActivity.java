package com.gc.frame.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.gc.frame.Frame;
import com.gc.frame.manage.MHandler;


public abstract class MFragmentActivity extends FragmentActivity {
    protected MHandler handler;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Frame.init(this);
        handler = new MHandler();
        String className = this.getClass().getName();
        handler.setId(className);
        handler.setMsglisnener(new MHandler.HandleMsgLisnener() {
            public void onMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (getParent() == null) {
                            finish();
                        }
                        break;
				case 200:
					disposeMsg(msg.arg1, msg.obj);
                    default:
                        break;
                }
            }
        });
        Frame.HANDLES.add(handler);
        try {
            if (null == savedInstanceState && null != getIntent()) {
                savedInstanceState = getIntent().getExtras();
            }
            initcreate(savedInstanceState);
            create(savedInstanceState);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (null != getIntent() && null != getIntent().getExtras())
            outState.putAll(getIntent().getExtras());
        super.onSaveInstanceState(outState);
    }
    
	/**
	 * ToDo：消息通知回调
	 * 
	 * @author yu
	 * @param type
	 * @param obj
	 * @return void
	 * @throws
	 */
	public void disposeMsg(int type, Object obj) {
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Frame.HANDLES.remove(handler);
        destroy();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }
    
    @Override
    protected void onResume() {
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
    
    public String getId() {
        return handler.getId();
    }
    
    
    public Object runLoad(int type, Object obj) {
        return null;
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
