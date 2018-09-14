package com.example.xlc.monkey.mvp.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe:网络请求
 */
public class RetrofitClient {

    private static volatile RetrofitClient instance;
    private String baseUrl = "http://www.wanandroid.com/";

    private RetrofitClient() {
    }

    private APIService apiService;

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    /**
     * 设置header
     *
     * @return
     */
    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder().header("token", "");
                Request request1 = builder.build();
                return chain.proceed(request1);
            }
        };
    }


    /**
     * 打印日志的拦截器
     *
     * @return
     */
    private Interceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这行必须加，不然默认不打印log
        return interceptor;
    }

    /**
     *
     * @return
     */
    public APIService getApi() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(getHeaderInterceptor())
                .addInterceptor(getInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //设置网络请求适配器，使其支持RxJava与RxAndroid
                //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        return apiService;
    }

}
