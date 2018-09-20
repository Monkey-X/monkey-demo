package com.example.xlc.monkey.retrofitRxjava.network.request;

import com.example.xlc.monkey.retrofitRxjava.network.bean.JavaBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author:xlc
 * @date:2018/9/19
 * @descirbe: 封装请求的接口
 */
public interface Request {

    public static String BASE ="https://www.baidu.com";

    @POST("?service=User.getList")
    Observable<Response<List<JavaBean>>> getList(@Query("userId") String userId);
}
