package com.example.xlc.monkey.mvp;

import com.example.xlc.monkey.mvp.bean.BaseObjectBean;
import com.example.xlc.monkey.mvp.bean.LoginBean;
import com.example.xlc.monkey.mvp.contract.MainContract;
import com.example.xlc.monkey.mvp.net.RetrofitClient;

import io.reactivex.Flowable;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe:
 */
public class MainModel implements MainContract.Model{


    @Override
    public Flowable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return RetrofitClient.getInstance().getApi().login(username,password);
    }
}
