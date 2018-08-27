package com.example.xlc.monkey.sms;

/**
 * @author:xlc
 * @date:2018/8/27
 * @descirbe:
 */
public interface SmsEvent {

    /**
     * 接受到短信
     * @param phone
     * @param content
     */
    void smsReceive(String phone, String content);
}
