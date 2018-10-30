package com.example.xlc.monkey.utils;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * @author:xlc
 * @date:2018/9/26
 * @descirbe: intent 打开各种文件 doc pdf 音频
 */
public class IntentUtil {


    /**
     * 获取用于打开html文件的intent
     *
     * @param param html的地址
     * @return
     */
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param)
                .buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content")
                .encodedPath(param)
                .build();

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;

    }


    /**
     * 获取用于打开图片文件的intent
     *
     * @param param 图片文件的地址
     * @return
     */
    public static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intnet.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * 获取用于打开PDF文件的intent
     *
     * @param param pdf文件的路径
     * @return
     */
    public static Intent getPDFFileIntent(String param) {
        Intent intent = new Intent("android.intnet.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");

        return intent;
    }

    /**
     * 获取一个用于打开word文件的intent
     *
     * @param param 文件的路径
     * @return
     */
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addCategory("android.intent.category.DEFAULT");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param));

        intent.setDataAndType(uri, "application/msword");

        return intent;
    }


    /**
     * 获取一个用于打开PPT文件的intent
     *
     * @param param ppt文件的地址
     * @return
     */
    public static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addCategory("android.intent.category.DEFAULT");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param));

        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");

        return intent;
    }


    /**
     * 获取打开Excel文件的intent
     * @param param  Excel文件的路径
     * @return
     */
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addCategory("android.intent.category.DEFAULT");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param));

        intent.setDataAndType(uri, "application/vnd.ms-excel");

        return intent;

    }

    /**
     * 获取用于打开CHM文件的intent
     * @param param CHM文件的地址
     * @return
     */
    public static Intent getChmFileIntent( String param ) {

        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addCategory("android.intent.category.DEFAULT");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param ));

        intent.setDataAndType(uri, "application/x-chm");

        return intent;
    }

    /**
     * 获取一个用于打开视频文件的intent
     * @param param 视频文件的地址
     * @return
     */
    public static Intent getVideoFileIntent( String param ) {

        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("oneshot", 0);

        intent.putExtra("configchange", 0);

        Uri uri = Uri.fromFile(new File(param ));

        intent.setDataAndType(uri, "video/*");

        return intent;
    }


    /**
     * 获取一个打开音频文件的intent
     * @param param  音频文件的路径
     * @return
     */
    public static Intent getAudioFileIntent( String param ) {

        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("oneshot", 0);

        intent.putExtra("configchange", 0);

        Uri uri = Uri.fromFile(new File(param ));

        intent.setDataAndType(uri, "audio/*");

        return intent;

    }

}
