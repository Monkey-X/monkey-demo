package com.example.xlc.monkey.utils.image;

/**
 * @author:xlc
 * @date:2019/4/11
 * @descirbe:图片加载过程的回调
 */


public interface ImageLoadProcessInterface {


    /**
     * 开始加载
     */
    void onLoadStarted();

    /**
     * 资源准备妥当
     */
    void onResourceReady();

    /**
     * 资源已经释放
     */
    void onLoadCleared();

    /**
     * 资源加载失败
     */
    void onLoadFailed();
}
