package com.example.xlc.monkey.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {


    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getIntentData();
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    public void getIntentData() {

    }

    public abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();


    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
