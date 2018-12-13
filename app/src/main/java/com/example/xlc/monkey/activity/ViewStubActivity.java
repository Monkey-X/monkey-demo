package com.example.xlc.monkey.activity;

import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.utils.ToastUtil;

/**
 * ViewStub控件 惰性装载控件
 */
public class ViewStubActivity extends BaseActivity implements View.OnClickListener {


    private ViewStub mViewStub;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_stub;
    }

    @Override
    protected void initView() {
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        mViewStub = findViewById(R.id.stub);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mViewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                ToastUtil.showLongToast(ViewStubActivity.this,"ViewStub is loaded!");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
        }
    }
}
