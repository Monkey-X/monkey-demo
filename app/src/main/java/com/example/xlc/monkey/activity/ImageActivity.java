package com.example.xlc.monkey.activity;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.utils.MyGlideEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.xlc.monkey.ImagesCompress.FileUtil.runOnUiThread;

public class ImageActivity extends BaseActivity {

    private static final int RC_WRITE_STORAGE = 2;
    private static final int REQUEST_CODE_CHOOSE = 23;

    @BindView(R.id.skipImage)
    Button mSkipImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        //        findViewById()
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        mSkipImage.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        String.valueOf(1);
        Integer.parseInt("1");
//        IntentService
        new Handler();
        HashSet<Object> objects = new HashSet<>();
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        ArrayList<String> strings1 = new ArrayList<>();
        LinkedList<String> strings2 = new LinkedList<>();
        Vector<String> strings3 = new Vector<>();
        TreeSet<String> strings4 = new TreeSet<>();
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();


        LruCache<String, String> stringStringLruCache = new LruCache<String, String>(10);
        //        strings.add()
        //        LocalBroadcastManager.getInstance(this).sendBroadcast()
        //        Glide.with(this).load()
        //        WindowManager windowManager = getWindowManager();
        //        Window window = getWindow();
        //        Dialog dialog = new Dialog(this);
        ////        dialog.setContentView();
        //dialog.show();
        //dialog.dismiss();
//        ToastUtil.showLongToast();
//        NetWorkManager.getInstance().init().
        EventBus.getDefault().register(this);
//EventBus.getDefault().post();

    }

    @Override
    protected void initData() {
        mSkipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
            mSkipImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
    }

    @Override
    public boolean supportRequestWindowFeature(int featureId) {
        return super.supportRequestWindowFeature(featureId);
    }

    @OnClick(R.id.skipImage)
    public void onViewClicked() {
        skipToImage();
    }

    @AfterPermissionGranted(RC_WRITE_STORAGE)
    private void skipToImage() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {//动态申请权限
            toMatisse();
        } else {
            EasyPermissions.requestPermissions(this, "", RC_WRITE_STORAGE, perms);
        }
    }

    private void toMatisse() {
        //Matisse在glide 4.0以及4.0之后的版本会报错，自定义图片加载的引擎
        //知乎的图片的选择器
        Matisse.from(this)
                .choose(MimeType.ofAll())//图片类型
                .countable(true)//true选中后显示数字，false选中后显示对号
                .maxSelectable(9)//可选的最大的数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(new CaptureStrategy(true, "com.example.xlc.monkey.fileprovider"))//true 表示拍照的图片储存在公共目录，false表示储存私有目录 ，第二个参数适配7.0系统
                .imageEngine(new MyGlideEngine())//图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
