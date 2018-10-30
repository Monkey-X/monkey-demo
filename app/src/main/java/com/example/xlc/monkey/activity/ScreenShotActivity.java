package com.example.xlc.monkey.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实现截屏功能
 */
public class ScreenShotActivity extends BaseActivity {


    /**
     * 利用SDK 提供的 view。getDrawingCache() 方法
     * 1.设置view.setDrawingCacheEnabled(true);
     * 2.调用view.buildDrawingCache(true);
     * 3.生产bitmap ：Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
     * 4.最后在设置回去view.setDrawingCacheEnabled(false);
     */
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.image)
    ImageView mImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_shot;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.btn)
    public void onViewClicked() {

        View dview = getWindow().getDecorView();
        dview.setDrawingCacheEnabled(true);
        dview.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dview.getDrawingCache());
        mImage.setImageBitmap(bitmap);
        dview.setDrawingCacheEnabled(false);//clear drawing cache
    }
}
