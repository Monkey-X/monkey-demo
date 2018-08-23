package com.example.xlc.monkey.fingerprint;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;

import com.example.xlc.monkey.utils.SPUtil;

/**
 * @author:xlc
 * @date:2018/8/22
 * @descirbe:指纹识别
 */
public class BiometricPromptManager {

    private Activity mActivity;
    private IBiometricPromptImpl mImpl;

    /**
     * 生物识别回调接口
     */
    public interface OnBiomertricIdentifyCallback {
        void onUsePassword();

        void onSucceeded();

        void onFailed();

        void onError(int code, String reason);

        void onCancel();
    }

    /**
     * 获取管理类的事例
     *
     * @param activity
     * @return
     */
    public static BiometricPromptManager from(Activity activity) {
        return new BiometricPromptManager(activity);
    }


    public BiometricPromptManager(Activity activity) {
        this.mActivity = activity;
        if (isAboveApi28()) {
            mImpl = new BiometricPromptApi28(activity);
        } else if (isAboveApi23()) {
            mImpl = new BiometricPromptApi23(activity);
        }
    }

    /**
     * 指纹信息认证
     *
     * @param callback
     */
    public void authenticate(@NonNull OnBiomertricIdentifyCallback callback) {

        mImpl.authenticate(new CancellationSignal(), callback);
    }

    /**
     * 指纹信息认证
     *
     * @param cancel
     * @param callback
     */
    public void authenticate(@NonNull CancellationSignal cancel, @NonNull OnBiomertricIdentifyCallback callback) {

        mImpl.authenticate(cancel, callback);
    }

    private boolean isAboveApi23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private boolean isAboveApi28() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1;
    }


    /**
     * 判断是否可以进行生物识别
     * @return
     */
    public boolean isBiometricPromptEnable() {
        return isAboveApi23()
                && isHardwareDetected()
                && hasEnrolledFingerprints()
                && isKeyguardSecure();
    }


    /**
     * 判断设备在系统设置里面是否设置了指纹。
     *
     * @return
     */
    public boolean hasEnrolledFingerprints() {
        if (isAboveApi28()) {
            // TODO: 2018/8/23 这是api23的判断方法，以后会有只针对api28的判断方法
            FingerprintManager manager = mActivity.getSystemService(FingerprintManager.class);
            return manager != null && manager.hasEnrolledFingerprints();
        } else if (isAboveApi23()) {
            return ((BiometricPromptApi23) mImpl).hasEnrolledFingerprints();
        } else {
            return false;
        }
    }

    /**
     * 判断系统是否硬件支持
     *
     * @return
     */
    public boolean isHardwareDetected() {
        if (isAboveApi28()) {
            FingerprintManager manager = mActivity.getSystemService(FingerprintManager.class);
            return manager != null && manager.isHardwareDetected();
        } else if (isAboveApi23()) {
            return ((BiometricPromptApi23) mImpl).isHardwareDetected();
        } else {
            return false;
        }
    }

    /**
     * 判断系统有没有设置锁屏
     *
     * @return
     */
    public boolean isKeyguardSecure() {

        KeyguardManager keyguardManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            return true;
        }
        return false;
    }

    /**
     * app设置是否开启指纹识别
     * @return
     */
    public boolean isBiometricSettingEnable(){
        return SPUtil.getBoolean(mActivity,SPUtil.KEY_BIOMETRIC_SWITCH_ANABLE,false);
    }

    /**
     * 保存是否可以生物识别的状态
     * @param enable
     */
    public void setBiometricSettingEnable(boolean enable){
        SPUtil.put(mActivity,SPUtil.KEY_BIOMETRIC_SWITCH_ANABLE,enable);
    }

}
