package com.example.xlc.monkey.net;

import android.accounts.NetworkErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author:xlc
 * @date:2018/9/18
 * @descirbe:  HttpURLConnection的用法。
 */
public class NetUtil {

    public static String post(String url,String content){
        HttpURLConnection conn = null;
        try {
            URL mUrl = new URL(url);//创建一个URL对象
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("POST");//设置请求方式为post
            conn.setReadTimeout(5000);//设置读取超时为5秒
            conn.setConnectTimeout(10000);//设置连接网络超时为10秒
            conn.setDoOutput(true);//设置此方法，允许向服务器输出内容
            //post请求的参数
            String data = content;
            //获得一个输出流，向服务器写数据，默认情况下，系统不允许向服务器输出内容
            OutputStream out = conn.getOutputStream();//获得一个输出流，向服务器写数据
            out.write(data.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();//调用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String response = getStringFromInputStream(is);
                return response;
            }else{
                throw new NetworkErrorException("response status is "+responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (conn !=null) {
                conn.disconnect();//关闭连接
            }
        }
        return null;
    }


    public static String get(String url){
        HttpURLConnection conn = null;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String response = getStringFromInputStream(is);
                return response;
            }else{
                throw new NetworkErrorException("response status is "+responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn!=null) {
                conn.disconnect();
            }
        }

        return null;
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;

        while ((len = is.read(buffer)) != -1) {
                os.write(buffer,0,len);
        }
        is.close();
        String state = os.toString();//把流中的数据转换成字符串，采用的编码utf-8 模拟器默认编码
        os.close();
        return state;
    }
}
