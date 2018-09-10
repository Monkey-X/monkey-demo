package com.example.xlc.monkey.activity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author:xlc
 * @date:2018/9/5
 * @descirbe: rxjava 的测试类
 */
public class RxJavaTest {
    public static void main(String[] args) {
        //消息发送者 上游
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    System.out.println("服务员从厨师那取得 扁食");
                    e.onNext("扁食");
                }

                if (!e.isDisposed()) {
                    System.out.println("服务员从厨师那取得 拌面");
                    e.onNext("拌面");
                }
                if (!e.isDisposed()) {
                    System.out.println("服务员从厨师那取得 蒸饺");
                    e.onNext("蒸饺");
                }

                if (!e.isDisposed()) {
                    System.out.println("厨师告知服务员菜上完了");
                    e.onComplete();
                }

            }
        });

        //消息订阅者 下游
        Observer<String> observer = new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable disposable) {
                this.disposable = disposable;
                System.out.println("来个套餐！！");
            }

            @Override
            public void onNext(String s) {
                if (s.equals("拌面")) {
                    //不再订阅消息了
                    disposable.dispose();
                }
                System.out.println("服务员端给客户" + s);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("服务员告诉顾客菜上好了");
            }
        };
        //建立联系
        observable.subscribe(observer);
    }



   public void createObservable(){}
}

//RxJava  异步+链式编程
//Schedulers.io() 是子线程  io线程可以重用空闲线程
//Schedulers.newThread() 是子线程 io()比newThread() 更有效率
//RxJava 四个基本概念：1.Observable 可观察者 即被观察者  2.Observer 观察者 3.subscribe 订阅 4.事件
//RxJava onComplete和onError 唯一并且互斥

//subscribeOn() 指定上游发送事件的线程  observeOn() 指定下游接受事件的线程
//多次调用 subscribeOn() 只有一次有效；  每调用一次 observeOn() 下游的线程就会切换一次
//RxJava的内置线程
//Schedulers.io() io操作的线程，网络，读写文件
//Schedulers.computation CPU计算密集型的操作，大量计算
//Schedulers.newThread()  常规线程
//AndroidSchedulers.mainThread() 代表Android的主线程