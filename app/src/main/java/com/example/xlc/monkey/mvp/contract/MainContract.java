package com.example.xlc.monkey.mvp.contract;

import com.example.xlc.monkey.mvp.bace.BaseView;
import com.example.xlc.monkey.mvp.bean.BaseObjectBean;
import com.example.xlc.monkey.mvp.bean.LoginBean;

import io.reactivex.Flowable;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe: 契约类
 */
public interface MainContract {

    interface Model{
        Flowable<BaseObjectBean<LoginBean>> login(String username,String password);
    }

    interface View extends BaseView{

        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void onSuccess(BaseObjectBean<LoginBean> bean);
    }

    interface Presenter{


        void login(String username,String password);
    }
}
