package com.example.xlc.monkey.utils;

import android.content.Context;

/**
 * created by xlc at 2018/8/8
 * dp和px 转换工具类
 */
public class DensityUtil {

    /**
     * dp 转为  px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {

        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (dpValue * scale + 0.5f));
    }

    /**
     * px 转为 dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2pix(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return ((int) (pxValue / scale + 0.5f));
    }
}
