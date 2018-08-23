package com.example.xlc.monkey.fingerprint;

import android.app.Activity;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;

/**
 * @author:xlc
 * @date:2018/8/23
 * @descirbe:version 28以上的指纹识别
 */
public class BiometricPromptApi28 implements IBiometricPromptImpl {

    private Activity mActivity;

    public BiometricPromptApi28(Activity activity) {
        this.mActivity = activity;

    }

    @Override
    public void authenticate(@NonNull CancellationSignal cancel, @NonNull BiometricPromptManager.OnBiomertricIdentifyCallback callback) {

    }
}
