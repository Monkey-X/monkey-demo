package com.example.xlc.monkey.view.ShadowView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.xlc.monkey.R;

/**
 * @author:xlc
 * @date:2019/7/4
 * @descirbe:获取自定义的属性和一定公共的方法
 */
public class LayoutParamsData {

    int radius;
    int shadowColor;
    int shadowDx;
    int shadowDy;
    int shadowEvaluation;
    RectF widgetRect;
    Path widgetPath;
    Path clipPath;
    boolean needClip;
    boolean hasShadow;

    public LayoutParamsData(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EasyLayout);
        radius = a.getDimensionPixelOffset(R.styleable.EasyLayout_layout_radius, 0);
        shadowDx = a.getDimensionPixelOffset(R.styleable.EasyLayout_layout_shadowDx, 0);
        shadowDy = a.getDimensionPixelOffset(R.styleable.EasyLayout_layout_shadowDy, 0);
        shadowColor = a.getColor(R.styleable.EasyLayout_layout_shadowColor, 0x99999999);
        shadowEvaluation = a.getDimensionPixelOffset(R.styleable.EasyLayout_layout_shadowEvaluation, 0);
        a.recycle();
        needClip = radius > 0;
        hasShadow = shadowEvaluation > 0;
    }

    /**
     * 将裁剪阴影的path和裁剪子view的path保存起来
     * @param v
     */
    public void initPaths(View v) {
        widgetRect = new RectF(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        widgetPath = new Path();
        clipPath = new Path();
        clipPath.addRect(widgetRect,Path.Direction.CCW);
        clipPath.addRoundRect(widgetRect,radius,radius,Path.Direction.CW);
        widgetPath.addRoundRect(widgetRect,radius,radius,Path.Direction.CW);
    }
}
