package com.example.xlc.monkey.utils.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author:xlc
 * @date:2019/4/11
 * @descirbe:图片加载的接口
 */
public interface ImageLoadInterface {

    /**
     * 显示路径中的图片（图片，文件中）
     * @param context
     * @param view
     * @param url
     * @param config     配置参数
     * @param imageLoadProcessInterface  加载过程监听
     */
    void display(Context context, ImageView view,String url,ImageConfig config,ImageLoadProcessInterface imageLoadProcessInterface);


    /**
     * 开始加载
     * @param context
     * @param url
     */
    void resumeLoad(Context context,String url);

    /**
     * 暂停加载
     * @param context
     * @param url
     */
    void pauseLoad(Context context,String url);


    /**
     * 清除一个资源的加载
     * @param context
     * @param imageView
     * @param url
     */
    void clearImageView(Context context,ImageView imageView,String url);
}
