package com.example.xlc.monkey.avoidonresult;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

import io.reactivex.Observable;

/**
 * @author:xlc
 * @date:2018/9/20
 * @descirbe:onActivityResult 的封装
 */
public class AvoidOnResult {

    private static final String TAG = "AvoidOnResult";
    private AvoidOnResultFragment mAvoidOnResultFragment;

    private static AvoidOnResult sAvoidOnResult;

    public static AvoidOnResult getInstance(Activity activity){
        if (sAvoidOnResult == null) {
            synchronized (AvoidOnResult.class){
                if (sAvoidOnResult ==null) {
                    sAvoidOnResult = new AvoidOnResult(activity);
                }
            }
        }
        return sAvoidOnResult;

    }

    private AvoidOnResult(Activity activity) {
        mAvoidOnResultFragment = getAvoidOnResultFragment(activity);
    }

    private AvoidOnResult(Fragment fragment) {
        this(fragment.getActivity());
    }

    private AvoidOnResultFragment getAvoidOnResultFragment(Activity activity) {
        AvoidOnResultFragment avoidOnResultFragment = findAvoidOnResultFragment(activity);
        if (avoidOnResultFragment == null) {
            avoidOnResultFragment = new AvoidOnResultFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().add(avoidOnResultFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return avoidOnResultFragment;
    }

    private AvoidOnResultFragment findAvoidOnResultFragment(Activity activity) {
        return (AvoidOnResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    //rxjava 调用
    public Observable<ActivityResultInfo> startForResult(Intent intent){
        return mAvoidOnResultFragment.startForResult(intent);
    }


    public Observable<ActivityResultInfo> startForResult(Class<?> clazz){
        Intent intent = new Intent(mAvoidOnResultFragment.getActivity(), clazz);
        return startForResult(intent);
    }


    public void startForResult(Intent intent,Callback callback){
        mAvoidOnResultFragment.startForResult(intent,callback);
    }

    public void startForResult(Class<?> clazz, Callback callback) {
        Intent intent = new Intent(mAvoidOnResultFragment.getActivity(), clazz);
        startForResult(intent, callback);
    }


    public interface Callback {
        void onActivityResult(int resultCode, Intent data);
    }


}
