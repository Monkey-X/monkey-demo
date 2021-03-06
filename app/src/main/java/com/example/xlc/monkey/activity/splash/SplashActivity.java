package com.example.xlc.monkey.activity.splash;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.adapter.SplashAdapter;
import com.example.xlc.monkey.base.BaseActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.recycleview)
    RecyclerView mRecycleview;

    @Override
    protected void setBeforeView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //解决app安装第一次进入app内容后，按home键重启app的问题
        if (!isTaskRoot() && getIntent() != null) {
            String action = getIntent().getAction();
            if (getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
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
