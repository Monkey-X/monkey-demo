package com.example.xlc.monkey.fingerprint;

import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * @author:xlc
 * @date:2018/8/23
 * @descirbe: 针对version23以上的指纹识别类
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class BiometricPromptApi23 implements IBiometricPromptImpl {

    private Activity mActivity;
    private FingerprintManager mFingerprintManager;

    public BiometricPromptApi23(Activity activity) {
        mActivity = activity;
        mFingerprintManager = getFingerprintManager(activity);
    }


    //获取指纹认证的管理器
    private FingerprintManager getFingerprintManager(Activity activity) {
        if (mFingerprintManager == null) {
            mFingerprintManager = activity.getSystemService(FingerprintManager.class);
        }
        return mFingerprintManager;
    }

    @Override
    public void authenticate(@NonNull CancellationSignal cancel, @NonNull BiometricPromptManager.OnBiomertricIdentifyCallback callback) {

    }

    /**
     * 判断设备在系统设置里面是否设置了指纹。
     * @return
     */
    public boolean hasEnrolledFingerprints() {

        if (mFingerprintManager != null) {
            return mFingerprintManager.hasEnrolledFingerprints();
        }
        return false;
    }

    /**
     * 判断设备是否支持指纹扫描
     * @return
     */
    public boolean isHardwareDetected(){
        if (mFingerprintManager !=null) {
          return  mFingerprintManager.isHardwareDetected();
        }
        return false;
    }
}
