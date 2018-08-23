package com.example.xlc.monkey.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取系统的一些信息，手机短信，手机通讯录，手机相册
 */
public class SystemInfoUtil {

    /**
     * 获取系统的短信信息
     * content://sms/ 所有短息
     * content://sms/inbox 收件箱
     * content://sms/outbox 发件箱
     * content://sms/sent 已发送
     * content://sms/failed 发送失败
     * content://sms/draft 草稿
     *
     * @param context
     * @return
     */
    public static List<Map<String, String>> getSystemSmsInfo(Context context) {
        Cursor cursor = null;
        try {
            //查询所有的短信
            cursor = context.getContentResolver()
                    .query(
                            Uri.parse("content://sms"),
                            new String[]{"_id", "address", "body", "date", "person", "type"},
                            null, null,
                            "date desc");
            if (cursor != null) {
                List<Map<String, String>> smsList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String body = cursor.getString(cursor.getColumnIndex("body"));//获取短信的信息
                    String person = cursor.getString(cursor.getColumnIndex("person"));//陌生人为null
                    String id = cursor.getString(cursor.getColumnIndex("_id"));//短消息序列
                    String address = cursor.getString(cursor.getColumnIndex("address"));//发件人的地址，手机号
                    String date = cursor.getString(cursor.getColumnIndex("date"));//日期
                    String type = cursor.getString(cursor.getColumnIndex("type"));//类型 1接受到， 2已发出
                    HashMap<String, String> smsMap = new HashMap<>();
                    smsMap.put("body", body);
                    smsMap.put("person", person);
                    smsMap.put("address", address);
                    smsMap.put("_id", id);
                    smsMap.put("date", date);
                    smsMap.put("type", type);
                    smsList.add(smsMap);
                }
                //返回所有的短信
                return smsList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * 获取系统的联系人信息
     *
     * @param context
     * @return
     */
    public static List<Map<String, String>> getSystemContactInfo(Context context) {

        List<Map<String, String>> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");//查询地址
            Uri data_uri = Uri.parse("content://com.android.contacts/data");
            //先查询raw_contacts,查询contact_id
            cursor = resolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(0);
//                cursor.getString(cursor.getColumnIndex("contact_id"));getColumnIndex : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
                //根据contact_id查询view_data表中的数据
                Cursor c = resolver.query(data_uri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contact_id}, null);
                HashMap<String, String> map = new HashMap<>();
                while (c.moveToNext()) {
                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    //根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        map.put("phone",data1);//电话
                    }else if (mimetype.equals("vnd.android.cursor.item/name")){
                        map.put("name",data1);
                    }
                }
                list.add(map);
                c.close();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
