package com.example.xlc.monkey.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author:xlc
 * @date:2018/10/30
 * @descirbe: 线性加载动画
 */
public class LoadingLineView extends View {

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

    }

    private void init() {

    }


}
