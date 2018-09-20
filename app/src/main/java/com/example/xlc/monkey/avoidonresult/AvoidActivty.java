package com.example.xlc.monkey.avoidonresult;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.activity.MainActivity;
import com.example.xlc.monkey.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class AvoidActivty extends BaseActivity {


    @BindView(R.id.rxjava)
    Button mRxjava;
    @BindView(R.id.callback)
    Button mCallback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_avoid_activty;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.rxjava, R.id.callback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rxjava:
                Rxjava();
                break;
            case R.id.callback:
                Callback();
                break;
        }
    }

    /**
     * callback 的方式
     */
    private void Callback() {
        AvoidOnResult.getInstance(this).startForResult(MainActivity.class, new AvoidOnResult.Callback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                if (resultCode == Activity.RESULT_OK) {
                    //返回成功的数据
                }
            }
        });
    }

    /**
     * rxjava的方式
     */
    private void Rxjava() {
      AvoidOnResult.getInstance(this).startForResult(MainActivity.class).subscribe(new Consumer<ActivityResultInfo>() {
          @Override
          public void accept(ActivityResultInfo activityResultInfo) throws Exception {
              if (activityResultInfo.getResultCode() == Activity.RESULT_OK) {
                  Intent data = activityResultInfo.getData();
                  //获取返回来的数据
              }
          }
      });

    }
}
