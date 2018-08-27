package com.example.xlc.monkey.fingerprint;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xlc.monkey.R;

/**
 * @author:xlc
 * @date:2018/8/23
 * @descirbe:指纹扫描的对话框
 */
public class BiometricPromptDialog extends DialogFragment {

    public static final int STATE_NORMAL = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_SUCCEED = 4;

    private Activity mActivity;

    private OnBiometricPromptDialogActionCallback mDialogActionCallback;
    private TextView mStateTv;
    private TextView mUsePassWordBtn;
    private TextView mCancelBtn;

    public interface OnBiometricPromptDialogActionCallback {
        void onDialogDismiss();

        void onUsePassword();

        void onCancel();
    }

    public static BiometricPromptDialog newInstance() {
        BiometricPromptDialog biometricPromptDialog = new BiometricPromptDialog();
        return biometricPromptDialog;
    }

    /**
     * 设置监听回调
     *
     * @param callback
     */
    public void setOnBiometricPromptDialogActionCallback(OnBiometricPromptDialogActionCallback callback) {
        mDialogActionCallback = callback;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupWindow(getDialog().getWindow());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.biometric_prompt_dialog, container);
        RelativeLayout rootView = view.findViewById(R.id.root_view);
        rootView.setClickable(false);

        mStateTv = view.findViewById(R.id.state_tv);
        mUsePassWordBtn = view.findViewById(R.id.use_password_btn);
        mCancelBtn = view.findViewById(R.id.cancel_btn);

        mUsePassWordBtn.setVisibility(View.GONE);
        mUsePassWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogActionCallback != null) {
                    mDialogActionCallback.onUsePassword();
                }
                dismiss();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialogActionCallback != null) {
                    mDialogActionCallback.onCancel();
                }
                dismiss();
            }
        });
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
        }
        return dialog;
    }

    private void setupWindow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.dimAmount = 0;//该变量指示后面窗口变暗的程度，1.0表示完全不透明。0.0表示没有变暗
            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;//设置这个后，dimAmount才能生效
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogActionCallback != null) {
            mDialogActionCallback.onDialogDismiss();
        }
    }


    public void setState(int state) {
        switch (state) {
            case STATE_NORMAL:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity,R.color.text_quaternary));
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_normal));
                mCancelBtn.setVisibility(View.VISIBLE);
                break;
            case STATE_FAILED:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity,R.color.text_red));
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_failed));
                mCancelBtn.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity,R.color.text_red));
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_error));
                mCancelBtn.setVisibility(View.GONE);
                break;
            case STATE_SUCCEED:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity,R.color.text_green));
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_succeeded));
                mCancelBtn.setVisibility(View.VISIBLE);

                mStateTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },500);
                break;
        }
    }
}
