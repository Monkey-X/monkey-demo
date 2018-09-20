package com.example.xlc.monkey.retrofitRxjava.network.response;

import com.example.xlc.monkey.retrofitRxjava.network.exception.ApiException;
import com.example.xlc.monkey.retrofitRxjava.network.exception.CustomException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author:xlc
 * @date:2018/9/19
 * @descirbe:对返回的数据进行处理，区分异常情况
 */
public class ResponseTransformer {

//为什么会报错啊

//    public static <T> ObservableTransformer<Response<T>, T> handleResult() {
//        return new ObservableTransformer<Response<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<Response<T>> observable) {
//                return observable.onErrorResumeNext(new ErrorResumeFunction<>()).flatMap(new ResponseFunction<>());
//            }
//        };
//    }


    /**
     * 非服务器产生的异常，比如本地无网络请求，json数据解析异常
     * @param <T>
     */

    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends Response<T>>> {

        @Override
        public ObservableSource<? extends Response<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }


    /**
     * 服务器返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     * @param <T>
     */

    private static class ResponseFunction<T> implements Function<Response<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(Response<T> tResponse) throws Exception {
            int code = tResponse.getCode();
            String message = tResponse.getMsg();
            if (code == 200) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new ApiException(code, message));
            }
        }
    }

}
