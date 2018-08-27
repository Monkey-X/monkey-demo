package com.example.xlc.monkey.sms;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.xlc.monkey.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class SmsActivity extends AppCompatActivity implements SmsEvent {
    final private int OVERLAY_PERMISSION_REQ_CODE=1234;//申请悬浮窗权限码
    private static final int RC_WRITE_STORAGE = 1;
    public static SmsEvent event;
    @BindView(R.id.phoneNumber)
    TextView mPhoneNumber;
    @BindView(R.id.phoneContent)
    TextView mPhoneContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        event = this;
        if (EasyPermissions.hasPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})) {

        } else {
            EasyPermissions.requestPermissions(this, "", RC_WRITE_STORAGE, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS});
        }
        if(Build.VERSION.SDK_INT>=23) {
            insertDummyContactWrapper();
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void insertDummyContactWrapper() {
        if (!Settings.canDrawOverlays(SmsActivity.this)) {
            showMessageOKCancel("请在应用权限中允许\n\t打开悬浮窗权限",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //申请该权限时引导用户跳转到Setting中自己去开启权限开关
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,   Uri.parse("package:" + SmsActivity.this.getPackageName()));
                            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
                        }
                    });
        } else {
            // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("拒绝", null)
                .create()
                .show();
    }

    @Override
    public void smsReceive(final String phone, String content) {
        //获取到短信的信息
        mPhoneNumber.setText(phone);
        mPhoneContent.setText(content);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(phone);
        builder.setMessage(content);
        builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSms(phone, "你好啊", SmsActivity.this);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();

    }


    public void sendSms(String phone, String message, Context context) {
        SmsManager smsManager = SmsManager.getDefault();
        if (message.length() <= 70) {
            smsManager.sendTextMessage(phone, null, message, null, null);
        } else {
            ArrayList<String> strings = smsManager.divideMessage(message);
            for (String ss : strings) {
                smsManager.sendTextMessage(phone, null, ss, null, null);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {//权限未被允许

            } else {//权限被允许
                //在这里可以弹出悬浮框
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
