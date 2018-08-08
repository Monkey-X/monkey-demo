package com.example.xlc.monkey.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * created by xlc at 2018/8/8
 * 字符串工具類
 */
public class StringUtil {

    private final static Pattern emailer = Pattern.compile("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");


    private final static Pattern img_url =Pattern.compile(".*?(gif|jpeg|png|jpg|bmp)");

    private  final static  Pattern URL = Pattern.compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");


    /**
     * 判斷是不是一個合法的email地址
     * @param str
     * @return
     */
    public static boolean isEmail(String str){
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        return emailer.matcher(str).matches();
    }

    /**
     * 判斷是不是一個合法的url地址
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url){
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        return img_url.matcher(url).matches();
    }

    /**
     * 判斷一個合法的鏈接地址
     * @param url
     * @return
     */
    public static boolean isUrl(String url){
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        return URL.matcher(url).matches();
    }



    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuilder res = new StringBuilder();
        BufferedReader read = new BufferedReader(new InputStreamReader(is));
        try {
            String line;
            while ((line = read.readLine()) != null) {
                res.append(line).append("<br>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res.toString();
    }


    /***
     * 截取字符串
     *
     * @param start 从那里开始，0算起
     * @param num   截取多少个
     * @param str   截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > length) {
            start = length;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > length) {
            end = length;
        }
        return str.substring(start, end);
    }



    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    @Deprecated
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

}
