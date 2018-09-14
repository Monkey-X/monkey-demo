package com.example.xlc.monkey.mvp.bace;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe: mvp的p层的基类
 */
public class BasePresenter<v extends BaseView> {

    protected v mView;


    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view
     */
    public void attachView(v view) {
        this.mView = view;
    }

    /**
     * 解除绑定的view
     */
    public void detachView() {
        this.mView = null;
    }


    /**
     * view 是否绑定
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }
}
