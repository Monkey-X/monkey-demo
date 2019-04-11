package com.example.xlc.monkey.utils;

/**
 * @author:xlc
 * @date:2019/3/25
 * @descirbe:
 */

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast mToast;

    private ToastUtil(){} ;


    //取消toast的显示
    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }


    /**
     * 短时间显示toast
     *
     * @param context
     * @param msg
     */
    public static void showShortToast(Context context, String msg) {

        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }

        mToast.show();
    }

    public static void showShortToast(Context context, int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }


    public static void showLongToast(Context context,String msg){
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_LONG);
        }else{
            mToast.setText(msg);
        }

        mToast.show();
    }

    public static void showLongToast(Context context,int resId){
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(),resId,Toast.LENGTH_LONG);
        }else{
            mToast.setText(resId);
        }

        mToast.show();
    }


    /**
     * 自定义toast的位置
     * @param context
     * @param msg
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static  void showLocationToast(Context context,String msg,int gravity,int xOffset,int yOffset){
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.setGravity(gravity,xOffset,yOffset);
        mToast.show();
    }


}
