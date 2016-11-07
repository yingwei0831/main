package com.jhhy.cuiweitourism.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jhhy.cuiweitourism.http.NetworkUtil;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

/**
 * Created by jiahe008 on 2016/11/7.
 */
public class NetWorkReceiver extends BroadcastReceiver {

    private String TAG = NetWorkReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)){
            if(NetworkUtil.checkNetwork(context)){
                LogUtil.e(TAG, "网络已连接");
            }else{
                LogUtil.e(TAG, "网络已断开");
            }
            LogUtil.e(TAG, "action = " + action);
        }
    }
}
