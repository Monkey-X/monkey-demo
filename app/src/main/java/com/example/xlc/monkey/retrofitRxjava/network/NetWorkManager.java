package com.example.xlc.monkey.retrofitRxjava.network;

import com.example.xlc.monkey.retrofitRxjava.network.request.Request;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author:xlc
 * @date:2018/9/19
 * @descirbe: retrofit和rxjava 网络封装
 */
public class NetWorkManager {


    private static Retrofit retrofit;
    private static volatile Request request = null;
    /**
     * 静态单例内部类
     * @return
     */
    public static NetWorkManager getInstance() {
        return ManagerHolder.sNetWorkManager;
    }

    private static class ManagerHolder {
        private static NetWorkManager sNetWorkManager = new NetWorkManager();
    }


    /**
     * 初始化必要对象和参数
     */
    public void init(){
        //这里可以设置一些网络请求公共的参数
        OkHttpClient client = new OkHttpClient.Builder().build();

        //初始化retrofit
       retrofit =  new Retrofit.Builder()
                .client(client)
                .baseUrl(Request.BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//这个参数是决定你的返回值是 observable 还是 calll
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Request getRequest(){
        if (request == null) {
            synchronized (Request.class){
                request = retrofit.create(Request.class);
            }
        }
        return request;
    }

}
