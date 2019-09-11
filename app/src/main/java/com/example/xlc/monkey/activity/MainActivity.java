package com.example.xlc.monkey.activity;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.xlc.monkey.R;
import com.example.xlc.monkey.activity.splash.SplashActivity;
import com.example.xlc.monkey.adapter.RecycleViewAdapter;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.sms.SmsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.flutter.facade.Flutter;

public class MainActivity extends BaseActivity implements RecycleViewAdapter.OnItemClickListener {

    @BindView(R.id.recycleview)
    RecyclerView mRecycleview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        String[] stringArray = getResources().getStringArray(R.array.main_item_name);
        List<String> items = Arrays.asList(stringArray);
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(items, MainActivity.this);
        recycleViewAdapter.setOnItemClickListener(this);
        mRecycleview.setAdapter(recycleViewAdapter);

        int cacheSize = 4*1024*1024;

        //LruCache算法，最近最少使用算法
        LruCache<String, Bitmap> lruCache = new LruCache<>(cacheSize);
        ArrayList<String> list = new ArrayList<>();
        LinkedList<String> objects = new LinkedList<>();
//        Glide.with(MainActivity.this).load("").into()
        IntentService
    }

    @Override
    protected void initData() {
        //        获取google提供的唯一id
        //        Executors.newSingleThreadExecutor().execute(new Runnable() {
        //            @Override
        //            public void run() {
        //                try {
        //                    final String gaid = AdvertisingIdClient.getGoogleAdId(getApplicationContext());
        //                    runOnUiThread(new Runnable() {
        //                        @Override
        //                        public void run() {
        //                            ToastUtil.showLongToast(MainActivity.this,gaid);
        //                        }
        //                    });
        //                } catch (Exception e) {
        //                    e.printStackTrace();
        //                    runOnUiThread(new Runnable() {
        //                        @Override
        //                        public void run() {
        //                            ToastUtil.showLongToast(MainActivity.this,"获取失败");
        //                        }
        //                    });
        //                }
        //            }
        //        });
    }

    @Override
    public void onClick(String msg, int position) {
        //点击条目
        switch (position) {
            case 0:
                startActivity(new Intent(this, TimeLineActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, ImageActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SplashActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, SmsActivity.class));
                break;
            case 4:
                //                startActivity(new Intent());
                openFlutter();
                break;
        }
    }


    public void openFlutter() {
        View flutterView = Flutter.createView(this, getLifecycle(), "route1");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(600, 800);
        layoutParams.leftMargin =100;
        layoutParams.topMargin = 200;
        addContentView(flutterView,layoutParams);
    }
}
