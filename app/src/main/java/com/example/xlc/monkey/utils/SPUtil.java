package com.example.xlc.monkey.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author:xlc
 * @date:2018/8/23
 * @descirbe: sp储存的工具类
 */
public class SPUtil {


    private static SharedPreferences sPreferences;

    public static final String KEY_BIOMETRIC_SWITCH_ANABLE="key_biometric_switch_enable";

    private SPUtil() {
    }

    //储存sp的初始化
    public static void initialize(Context context) {
        sPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void put(Context context, String key, Object object) {

        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        if (object instanceof String) {
            edit.putString(key, (String) object);
        }else if (object instanceof Integer){
            edit.putInt(key, (Integer) object);
        }else if (object instanceof Boolean){
            edit.putBoolean(key, (Boolean) object);
        }else if (object instanceof Float){
            edit.putFloat(key, (Float) object);
        }else if (object instanceof Long){
            edit.putLong(key, (Long) object);
        }else{
            edit.putString(key,object.toString());
        }
        edit.apply();//异步提交，没有返回值
//        edit.commit();//同步提交，有返回值
    }


    public static String getString(Context context,String key,String dfValue){
        return getSharedPreferences(context).getString(key,dfValue);
    }

    public static boolean getBoolean(Context context,String key,boolean dfValue){
        return getSharedPreferences(context).getBoolean(key,dfValue);
    }

    public static int getInt(Context context,String key,int dfValue){
        return getSharedPreferences(context).getInt(key,dfValue);
    }

    public static float getFloat(Context context,String key,float dfValue){
        return getSharedPreferences(context).getFloat(key,dfValue);
    }

    public static long getLong(Context context,String key,long dfValue){
        return getSharedPreferences(context).getLong(key,dfValue);
    }

    public static void remove(Context context,String key){
        getSharedPreferences(context).edit().remove(key);
    }

    public static boolean contains(Context context,String key){
        return getSharedPreferences(context).contains(key);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return sPreferences == null ? context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE) : sPreferences;
    }
}
