package com.example.xlc.monkey.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.xlc.monkey.net.NetEvent;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,NetEvent {


    private Unbinder mUnbinder;
    public static NetEvent event;//实现网络变化的接口
    private int netState;//网络状态值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBeforeView();
        setContentView(getLayoutId());
        event = this;
        getIntentData();
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    //在setContentView之前执行
    protected void setBeforeView() {
    }

    protected void getIntentData() {

    }

    public abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     *  //获取网络变化后的状态值
     * @param netState
     */
    @Override
    public void onNetChange(int netState) {
        this.netState = netState;
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //动态权限申请后返回的结果交予easyPermission处理
//        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
//    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //获取权限失败
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //获取权限成功
    }

    /**
     * 设置状态栏
     */
    public void setStatusBarFullTransparant() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//全透明的状态栏 5.0 version 21
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //android 4.4 version 19
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    private View contentViewGroup;

    /**
     * 如果需要内容紧贴着StatusBar
     * 在对应的xml布局文件中，设置根布局fitSystemWindows = true;
     * Activity布局的根节点上设置android:fitsSystemWindows="true"
     * 避免布局会跑到状态栏和导航栏下面，与导航栏和状态栏重叠
     */
    public void setFitSystemWindow(boolean fitSystemWindow) {

        if (contentViewGroup ==null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    private int getStatusHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //通过反射的方式获取状态栏高度2018/04/17
    private int getStatusHeight(Context context) {//获得状态栏的高度
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String statusBarHeight = clazz.getField("status_bar_height").get(object).toString();
            int height = Integer.parseInt(statusBarHeight);
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    //判断是否有物理按钮NavigationBar,,通过反射的原理
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources resources = getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            }else if ("0".equals(navBarOverride)){
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    //获取设备底部导航栏高度
    public int getNavigationBarHeight(){
        Resources resources = getResources();
        int resourcesId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationHeight = resources.getDimensionPixelSize(resourcesId);
        return navigationHeight;
    }
}
