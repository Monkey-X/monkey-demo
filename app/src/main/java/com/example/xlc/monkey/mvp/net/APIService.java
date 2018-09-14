package com.example.xlc.monkey.mvp.net;

import com.example.xlc.monkey.mvp.bean.BaseObjectBean;
import com.example.xlc.monkey.mvp.bean.LoginBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe:retrofit网络请求的接口
 */
public interface APIService {
    /**
     * 登陆
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Flowable<BaseObjectBean<LoginBean>> login(@Field("username") String username,
                                              @Field("password") String password);
}
