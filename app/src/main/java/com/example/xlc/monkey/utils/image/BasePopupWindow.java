package com.example.xlc.monkey.utils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author:xlc
 * @date:2019/4/29
 * @descirbe:  抽象的PopupWindow 的基类
 */
public abstract class BasePopupWindow extends PopupWindow {

    private static  final String TAG = BasePopupWindow.class.getSimpleName();
    private Context mContext;
    public View mView;
    //屏幕灰度等级
    private float mLevel;

    public BasePopupWindow(Context context){
        super(context);

        this.mContext = context;
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        initAnimation();
        initSetting();
        initView();
        initListener();
        setContentView(mView);
    }


    public void setmLevel(float mLevel){

        this.mLevel = mLevel;
    }


    private void setBackGroundLevel(float level){
        Window window = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = level;
        window.setAttributes(params);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        setBackGroundLevel(1.0f);
    }

    @Override
    public void showAsDropDown(View anchor) {

        this.showAsDropDown(anchor,0,0);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {

        this.showAsDropDown(anchor,xoff,yoff, Gravity.TOP|Gravity.START);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        initBackGroundLevel();
    }

    /**
     * 初始化背景颜色灰度
     */
    private void initBackGroundLevel(){
        if (mLevel ==0) {
            mLevel = 0.4f;
        }
        setBackGroundLevel(mLevel);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        initBackGroundLevel();
    }

    /**
     * 设置界面事件的监听
     */
    protected abstract void initListener();


    /**
     * 初始化布局
     * 并将布局的View赋值给mView
     */
    protected abstract void initView();


    /**
     * 初始化PopupWindow的设置
     */
    public abstract void initSetting();



    /**
     * 设置默认的动画，需要设置其他动画直接重写并删除，super实现即可
     */
    public void initAnimation() {
//        setAnimationStyle(R.style.MyPopupWindow_alpha_style);
    }

}
