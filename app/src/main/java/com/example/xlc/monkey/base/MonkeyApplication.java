package com.example.xlc.monkey.base;

import android.app.Application;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;


/**
 * @author:xlc
 * @date:2018/9/4
 * @descirbe:
 */
public class MonkeyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger日志框架
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
        //将日志保存在文件中
        //Logger.addLogAdapter(new DiskLogAdapter());
    }
}
