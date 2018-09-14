package com.example.xlc.monkey.mvp.net;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe:RxJava 线程调度
 */
public class RxScheduler {

    /**
     * 统一线程处理
     * @param <T> 指定的泛型类型
     * @return
     */
    public static <T>FlowableTransformer<T,T> Flo_io_main(){
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());//切换到主线程执行
            }
        };
    }

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T>ObservableTransformer<T,T> Obs_io_main(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {

                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
