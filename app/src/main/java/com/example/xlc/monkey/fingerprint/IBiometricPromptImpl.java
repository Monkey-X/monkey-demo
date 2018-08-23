package com.example.xlc.monkey.fingerprint;

import android.os.CancellationSignal;
import android.support.annotation.NonNull;

/**
 * @author:xlc
 * @date:2018/8/23
 * @descirbe:指纹识别的接口
 */
public interface IBiometricPromptImpl {
    //认证

    /**
     * @param cancel 取消当前指纹识别器的扫描操作
     * @param callback  指纹识别的结果回调
     */
    void authenticate(@NonNull CancellationSignal cancel, @NonNull BiometricPromptManager.OnBiomertricIdentifyCallback callback);
}
