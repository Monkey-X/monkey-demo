package com.example.xlc.monkey.activity;

import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.adapter.SplashAdapter;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.view.ScrollLinearLayoutManager;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.recycleview)
    RecyclerView mRecycleview;

    @Override
    protected void setBeforeView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mRecycleview.setLayoutManager(new ScrollLinearLayoutManager(this));
        mRecycleview.setAdapter(new SplashAdapter(SplashActivity.this));
        mRecycleview.smoothScrollToPosition(Integer.MAX_VALUE / 2);
//        ToastUtil.showLongToast(this,"我在这里");
    }

    @Override
    protected void initData() {

    }

}
