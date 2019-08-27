package com.example.xlc.monkey.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;

import com.example.xlc.monkey.R;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class JsBridgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
        BridgeWebView webview = findViewById(R.id.webView);
        //js 调用java的方法
        webview.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //将js调用java ,java运行后的结果通过function的oncallback方法传递给js
                function.onCallBack("submitFormWeb exe,response data from java");
            }
        });
        //java 调用js
        webview.callHandler("callJs", "java call js", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });


//        LayoutInflater.from(this).inflate();

        HandlerThread handlerThread = new HandlerThread("cc");
//        AsyncQueryHandler
        Gson gson = new Gson();
//        gson.fromJson()
//        gson.toJson()th
        OkHttpClient build = new OkHttpClient.Builder().build();
        new Request.Builder()
        //AsyncTask的使用，使用静态内部类的模式
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
        Executors.newScheduledThreadPool(2);
    }


    static  class MyAsyncTask extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(Object o) {
            super.onCancelled(o);
        }
    }



}
