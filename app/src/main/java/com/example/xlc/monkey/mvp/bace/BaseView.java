package com.example.xlc.monkey.mvp.bace;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe: mvp view的基类
 */
public interface BaseView {

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     * @param throwable
     */
    void onError(Throwable throwable);
}
