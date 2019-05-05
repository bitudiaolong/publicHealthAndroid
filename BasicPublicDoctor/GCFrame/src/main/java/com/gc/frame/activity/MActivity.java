package com.gc.frame.activity;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import com.gc.frame.Frame;
import com.gc.frame.manage.MHandler;

public abstract class MActivity extends Activity {
    protected MHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Frame.init(this);
        handler = new MHandler();
        String className = this.getClass().getName();
        handler.setId(className);
        handler.setMsglisnener(new MHandler.HandleMsgLisnener() {
            @Override
			public void onMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        if (getParent() == null) {
                            finish();
                        }
                        break;
				case 201:
					disposeMsg(msg.arg1, msg.obj);
                    default:
                        break;
                }
            }
        });
        Frame.HANDLES.add(handler);
        try {
            initcreate(savedInstanceState);
            create(savedInstanceState);
        }
        catch (Exception e) {
        }
        
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
    
    /** 
     * ToDo：向页面handler发�?�消息，�?规避MHandler已使用msg.what的常量字�? 
     * @author yu
     * @param msg 
     * @return void 
     * @throws 
     */
    public void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }
    
    public void setId(String id) {
        handler.setId(id);
    }
    
    public String getId() {
        return handler.getId();
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

	/**
	 * ToDo：关闭以参数id命名的页�?
	 * 
	 * @author yu
	 * @param id
	 * @return void
	 * @throws
	 */
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
