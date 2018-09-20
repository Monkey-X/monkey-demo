package com.example.xlc.monkey.retrofitRxjava.network.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

/**
 * @author:xlc
 * @date:2018/9/20
 * @descirbe:提供线程切换
 */
public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();//计算线程

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();
}
