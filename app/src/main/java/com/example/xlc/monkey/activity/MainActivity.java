package com.example.xlc.monkey.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.activity.splash.SplashActivity;
import com.example.xlc.monkey.adapter.RecycleViewAdapter;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.sms.SmsActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

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
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(String msg, int position) {
        //点击条目
        switch (position) {
            case 0:
                startActivity(new Intent(this,TimeLineActivity.class));
                break;
            case 1:
                startActivity(new Intent(this,ImageActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,SplashActivity.class));
                break;
            case 3:
                startActivity(new Intent(this,SmsActivity.class));
                break;
        }
    }
}
