package com.example.xlc.monkey.fingerprint;

import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * @author:xlc
 * @date:2018/8/23
 * @descirbe: 针对version23以上的指纹识别类
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class BiometricPromptApi23 implements IBiometricPromptImpl {

    private static final String TAG = "BiometricPromptApi23";
    private Activity mActivity;
    private FingerprintManager mFingerprintManager;
    private BiometricPromptManager.OnBiomertricIdentifyCallback mManagerIdentifyCallback;
    private BiometricPromptDialog mDialog;
    private CancellationSignal mCancellationSignal;
    private FingerprintManager.AuthenticationCallback mFmAuthCallback = new FingerprintManageCallbackImpl();

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
        //指纹识别的回调
        mManagerIdentifyCallback = callback;
        //放弃或者终止指纹扫描
        mCancellationSignal = cancel;
        mDialog = BiometricPromptDialog.newInstance();
        mDialog.setOnBiometricPromptDialogActionCallback(new BiometricPromptDialog.OnBiometricPromptDialogActionCallback() {
            @Override
            public void onDialogDismiss() {
                //当dialog消失的时候，包括点击userOassword、点击cancel、和识别成功之后
                if (mCancellationSignal != null && mCancellationSignal.isCanceled()) {
                    mCancellationSignal.cancel();
                }
            }

            @Override
            public void onUsePassword() {
                //一些情况中，用户还可以选择使用密码
                if (mManagerIdentifyCallback != null) {
                    mManagerIdentifyCallback.onUsePassword();
                }
            }

            @Override
            public void onCancel() {
                //点击cancel键
                if (mManagerIdentifyCallback != null) {
                    mManagerIdentifyCallback.onCancel();
                }
            }
        });

        mDialog.show(mActivity.getFragmentManager(), "BiometricPromptApi23");

        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                mDialog.dismiss();
            }
        });

        try {
            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
            getFingerprintManager(mActivity).authenticate(cryptoObjectHelper.buildCryptoObject(),mCancellationSignal,0,mFmAuthCallback,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 指纹扫描的回调
     */
    private class FingerprintManageCallbackImpl extends FingerprintManager.AuthenticationCallback{
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Log.d(TAG, "onAuthenticationError() called with: errorCode = [" + errorCode + "], errString = [" + errString + "]");
            mDialog.setState(BiometricPromptDialog.STATE_ERROR);
            mManagerIdentifyCallback.onError(errorCode,errString.toString());
        }


        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            Log.d(TAG, "onAuthenticationFailed() called");
            mDialog.setState(BiometricPromptDialog.STATE_FAILED);
            mManagerIdentifyCallback.onFailed();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            Log.d(TAG, "onAuthenticationHelp() called with: helpCode = [" + helpCode + "], helpString = [" + helpString + "]");
            mDialog.setState(BiometricPromptDialog.STATE_FAILED);
            mManagerIdentifyCallback.onFailed();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            Log.i(TAG, "onAuthenticationSucceeded: ");
            mDialog.setState(BiometricPromptDialog.STATE_SUCCEED);
            mManagerIdentifyCallback.onSucceeded();
        }
    }

    /**
     * 判断设备在系统设置里面是否设置了指纹。
     *
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
     *
     * @return
     */
    public boolean isHardwareDetected() {
        if (mFingerprintManager != null) {
            return mFingerprintManager.isHardwareDetected();
        }
        return false;
    }
}
