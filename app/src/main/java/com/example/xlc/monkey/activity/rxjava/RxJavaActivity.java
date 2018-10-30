package com.example.xlc.monkey.activity.rxjava;

import android.annotation.SuppressLint;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * rxjava 的学习操作符
 * 测试类
 */
public class RxJavaActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_java;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    /**
     * rxjava的map操作符
     * 可以将上游的事件转换成任意的类型
     * 对上游发送的每一个事件应用一个函数，使得每一个事件都按照指定的函数去变化
     */
    public void setMapOperator() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d(s);
            }
        });
    }

    /**
     * sample操作符 每隔指定的时间就从上游中取出一个事件发送给下游
     */

    /**
     * flatMap 操作符
     * 将上游Observable事件变换为多个Observable发送事件，在将事件合并后放在另一个事件上游里面
     * 上游每个事件，flatmap都会创建一个新的通道，然后发送转换之后的新的事件，下游接受到是这些新的通道发送的数据
     * 并不保证事件的顺序
     * 需要保证事件的顺序可以使用 concatMap 操作符
     */
    @SuppressLint("CheckResult")
    public void setFlatMapOperator() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d(s);
            }
        });
    }

    /**
     * zip 操作符
     * 通过一个函数将多个Observable 发送事件结合到一起，然后将组合的事件进行发送
     * 按照严格的顺序应用这个函数
     * 没有足够的事件组合时，下游不会收到剩下的事件了
     */
    public void setZipOperator() {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Logger.d("emit 1");
                emitter.onNext(1);
                Logger.d("emit 2");
                emitter.onNext(2);
                Logger.d("emit 3");
                emitter.onNext(3);
                Logger.d("emit 4");
                emitter.onNext(4);
                Logger.d("emit complete");
                emitter.onComplete();
            }
        });

        Observable<String> observable1 = observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Logger.d("emit A");
                emitter.onNext("A");
                Logger.d("emit B");
                emitter.onNext("B");
                Logger.d("emit C");
                emitter.onNext("C");
                Logger.d("emit complete2");
                emitter.onComplete();
            }
        });
        Observable.zip(observable1, observable, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s+integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                    Logger.d("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                    Logger.d("onNext: "+s);
            }

            @Override
            public void onError(Throwable throwable) {
                Logger.d("onError");
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        });
    }
}

