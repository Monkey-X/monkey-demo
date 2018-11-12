package com.example.xlc.monkey.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @author:xlc
 * @date:2018/10/30
 * @descirbe: 线性加载动画
 */
public class LoadingLineView extends View {

    /**
     * 画笔宽度（等于view高度）
     */
    private int paintWidth;

    /**
     * 底色
     */
    private int bgColor = 0xFFe1e5e8;

    /**
     * loading的颜色
     */
    private int loadingColor = 0xFFf66b12;




    public LoadingLineView(Context context) {
        this(context,null);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAtt(attrs);
        init();
    }

    /**
     * 实现颜色等参数可配置
     * @param attrs
     */
    private void getAtt(AttributeSet attrs) {
        int paintWidth = dp2px(2);
    }

    private void init() {

    }




    public int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,getResources().getDisplayMetrics());
    }


}
