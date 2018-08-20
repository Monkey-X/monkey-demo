package com.example.xlc.monkey.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.xlc.monkey.base.BaseActivity;

/**
 * 监听手机网络是否改变的广播接收器
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    private NetEvent mNetEvent = BaseActivity.event;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetWorkUtil.getNetWorkState(context);
            mNetEvent.onNetChange(netWorkState);
        }
    }
}
