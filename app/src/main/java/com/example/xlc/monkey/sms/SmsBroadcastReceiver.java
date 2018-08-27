package com.example.xlc.monkey.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * @author:xlc
 * @date:2018/8/27
 * @descirbe: 监听短信的广播
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private SmsEvent mSmsEvent = SmsActivity.event;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdusObjects = (Object[]) bundle.get("pdus");
            SmsMessage[] smsMessages = new SmsMessage[pdusObjects.length];
            for (int i = 0; i < pdusObjects.length; i++) {
                smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdusObjects[i]);

            }

            for (SmsMessage smsMessage : smsMessages) {
                String address = smsMessage.getDisplayOriginatingAddress();
                String content = smsMessage.getDisplayMessageBody();
                String phone = address.replace("+86", "");
                System.out.print(phone + "  " + content);
                mSmsEvent.smsReceive(phone, content);
            }
        }

    }
}
