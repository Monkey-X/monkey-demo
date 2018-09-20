package com.example.xlc.monkey.avoidonresult;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * @author:xlc
 * @date:2018/9/20
 * @descirbe: 打开没有视图的fragment 处理startActivityForResult 返回的结果
 */
public class AvoidOnResultFragment extends Fragment{

    private Map<Integer,PublishSubject<ActivityResultInfo>> mSubjects = new HashMap<>();
    private Map<Integer,AvoidOnResult.Callback> mCallbackMap = new HashMap<>();
    public AvoidOnResultFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //s设备旋转时，保留fragment，不会随着activity一起被销毁
        setRetainInstance(true);
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent) {
        final PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mSubjects.put(subject.hashCode(), subject);//保留事件的发送者
                startActivityForResult(intent, subject.hashCode());
            }
        });
    }


    public void startForResult(Intent intent,AvoidOnResult.Callback callback){
        mCallbackMap.put(callback.hashCode(),callback);
        startActivityForResult(intent,callback.hashCode());//哈希值作为requestCode
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava方式的处理
        PublishSubject<ActivityResultInfo> subject = mSubjects.remove(requestCode);
        if (subject!=null) {
            subject.onNext(new ActivityResultInfo(resultCode,data));
            subject.onComplete();
        }

        //callback方式处理
        AvoidOnResult.Callback callback = mCallbackMap.remove(requestCode);
        if (callback!=null) {
            callback.onActivityResult(resultCode,data);
        }

    }
}
