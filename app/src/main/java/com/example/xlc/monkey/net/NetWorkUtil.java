package com.example.xlc.monkey.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断网络的工具类
 */
public class NetWorkUtil {

    /**
     * 没有网络
     */
    private static final int NETWORK_NONE = -1;

    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;

    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;


    public static int getNetWorkState(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }

        return NETWORK_NONE;

    }
}
