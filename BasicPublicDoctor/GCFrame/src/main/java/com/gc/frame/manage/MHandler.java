package com.gc.frame.manage;


import android.os.Handler;
import android.os.Message;

public class MHandler extends Handler {
    
    /**@Fields MSG_LOAD : 关闭页面 */
    public static final int MSG_CLOSE = 0;
    
    /**@Fields MSG_LOAD : �?启一个加载框 */
    public static final int MSG_SHOW_DIALOG = 98;
    
    /**@Fields MSG_LOAD : 关闭�?个加载框 */
    public static final int MSG_CLOSE_DIALOG = 99;
    
    /**@Fields MSG_LOAD : 调用re_loadData */
    public static final int MSG_RELOAD = 100;
    
    /**@Fields MSG_LOAD : 调用loadData */
    public static final int MSG_LOAD = 200;
    
    /**@Fields MSG_LOAD : 调用disposMsg */
    public static final int MSG_DISPOSMSG = 201;
    
    public String id;
    
    public HandleMsgLisnener msglisnener;
    
    public int staus = 0;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 调用loadData
     * @param type
     * @param obj
     */
    public void send(int type, Object obj) {
        Message message = new Message();
        message.what = MSG_LOAD;
        message.arg1 = type;
        message.obj = obj;
        this.sendMessageL(message);
    }
    
    /**
     * 直接调用disposMsg
     * @param type
     * @param obj
     */
    public void sent(int type, Object obj) {
        Message message = new Message();
        message.what = MSG_DISPOSMSG;
        message.arg1 = type;
        message.obj = obj;
        this.sendMessageL(message);
    }
    
    /**
     * 直接调用disposMsg
     * @param type
     * @param obj
     */
    public void sent(Object obj) {
        Message message = new Message();
        message.what = MSG_DISPOSMSG;
        message.arg1 = 0;
        message.obj = obj;
        this.sendMessageL(message);
    }
    
    /**
     * 更新调用loaddate
     * @param typs
     */
    public void reload(int[] typs) {
        Message message = new Message();
        message.what = MSG_RELOAD;
        message.obj = typs;
        this.sendMessageL(message);
    }
    
    public boolean sendMessageL(Message msg) {
        return super.sendMessage(msg);
    }
    
    public void reload() {
        reload(null);
    }
    
    public void close() {
        this.sendEmptyMessage(MSG_CLOSE);
    }
    
    public interface HandleMsgLisnener {
        public void onMessage(Message msg);
    }
    
    public void setMsglisnener(HandleMsgLisnener msglisnener) {
        this.msglisnener = msglisnener;
    }
    
    @Override
    public synchronized void handleMessage(Message msg) {
        if (msglisnener != null) {
            msglisnener.onMessage(msg);
        }
    }
}
