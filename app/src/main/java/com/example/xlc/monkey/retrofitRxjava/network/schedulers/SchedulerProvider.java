package com.example.xlc.monkey.retrofitRxjava.network.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:xlc
 * @date:2018/9/20
 * @descirbe:
 */
public class SchedulerProvider implements BaseSchedulerProvider {

    private SchedulerProvider() {
    }

    private static class SchedulerHolder {
        private static SchedulerProvider sSchedulerProvider = new SchedulerProvider();
    }

    public static SchedulerProvider getInstance() {
        return SchedulerHolder.sSchedulerProvider;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(io()).observeOn(ui());
            }
        };
    }
}
