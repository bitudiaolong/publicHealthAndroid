package com.gc.basicpublicdoctor.service.rongimservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.gc.basicpublicdoctor.fragment.secondfragment.SecondTabFragment;

/**
 * Created by guanxindashi on 2018/5/25.
 */

public class MessageReceiver extends BroadcastReceiver {

    public interface TvChg {
        public void setChange();
    }

    private TvChg chg;

    public MessageReceiver(TvChg chg) {
        this.chg = chg;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SecondTabFragment.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
            String messge = intent.getStringExtra(SecondTabFragment.KEY_MESSAGE);
            String extras = intent.getStringExtra(SecondTabFragment.KEY_EXTRAS);
            StringBuilder showMsg = new StringBuilder();
            showMsg.append(SecondTabFragment.KEY_MESSAGE + " : " + messge + "\n");
            if (!TextUtils.isEmpty(extras)) {
                showMsg.append(SecondTabFragment.KEY_EXTRAS + " : " + extras + "\n");
            }

            chg.setChange();

        }
    }
}
