package com.example.xlc.monkey.constant;


import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author:xlc
 * @date:2018/9/4
 * @descirbe: 危险权限组常量类
 */
@SuppressLint("InlineApi")
public final class PermissionConstants {

    //日历
    public static final String CALENDAR = Manifest.permission_group.CALENDAR;
    //照相机
    public static final String CAMERA = Manifest.permission_group.CAMERA;
    //联系人
    public static final String CONTACTS = Manifest.permission_group.CONTACTS;
    //位置信息
    public static final String LOCATION = Manifest.permission_group.LOCATION;
    //麦克风
    public static final String MICROPHONE = Manifest.permission_group.MICROPHONE;
    //打电话的权限
    public static final String PHONE = Manifest.permission_group.PHONE;
    //传感器
    public static final String SENSORS = Manifest.permission_group.SENSORS;
    //短信
    public static final String SMS = Manifest.permission_group.SMS;
    //外部储存
    public static final String STORAGE = Manifest.permission_group.STORAGE;

    private static final String[] GROUP_CALENDAR = {
            Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR
    };
    private static final String[] GROUP_CAMERA = {
            Manifest.permission.CAMERA
    };
    private static final String[] GROUP_CONTACTS = {
            Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS
    };
    private static final String[] GROUP_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final String[] GROUP_MICROPHONE = {
            Manifest.permission.RECORD_AUDIO
    };
    private static final String[] GROUP_PHONE = {
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.CALL_PHONE,
            Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS
    };
    private static final String[] GROUP_SENSORS = {
            Manifest.permission.BODY_SENSORS
    };
    private static final String[] GROUP_SMS = {
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS,
    };
    private static final String[] GROUP_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    //定义自己的注解
    @StringDef({CALENDAR,CAMERA,CONTACTS,LOCATION,MICROPHONE,PHONE,SENSORS,SMS,STORAGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Permission{}


    public static String[] getPermissions(@Permission final String permission){
        switch (permission) {
            case CALENDAR:
                return GROUP_CALENDAR;
            case CAMERA:
                return GROUP_CAMERA;
            case CONTACTS:
                return GROUP_CONTACTS;
            case LOCATION:
                return GROUP_LOCATION;
            case MICROPHONE:
                return GROUP_MICROPHONE;
            case PHONE:
                return GROUP_PHONE;
            case SENSORS:
                return GROUP_SENSORS;
            case SMS:
                return GROUP_SMS;
            case STORAGE:
                return GROUP_STORAGE;
        }
        return new String[]{permission};
    }
}
