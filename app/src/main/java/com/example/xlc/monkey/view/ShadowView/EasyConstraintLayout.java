package com.example.xlc.monkey.view.ShadowView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author:xlc
 * @date:2019/7/4
 * @descirbe:自定义Layout,让子View支持圆角属性 1.通过父布局控制子view的圆角和阴影行为，重写drawChild
 * 2.通过paint的shadowlayer属性把子View的阴影先画上
 * 3.阴影需要将子view自身大小裁剪，然后在画子view，并裁剪圆角部分
 */
public class EasyConstraintLayout extends ConstraintLayout {

    private Paint shadowPaint;
    private Paint clipPaint;

    public EasyConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化paint，关闭硬件加速
        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setDither(true);
        shadowPaint.setFilterBitmap(true);
        shadowPaint.setStyle(Paint.Style.FILL);

        clipPaint = new Paint();
        clipPaint.setAntiAlias(true);
        clipPaint.setDither(true);
        clipPaint.setFilterBitmap(true);
        clipPaint.setStyle(Paint.Style.FILL);
        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    public ConstraintLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        boolean ret = false;
        if (lp instanceof EasyLayoutParams) {
            EasyLayoutParams elp = (EasyLayoutParams) lp;
            LayoutParamsData data = elp.getData();
            if (isInEditMode()) {//编辑器的预览模式，采用裁剪
                canvas.save();
                canvas.clipPath(data.widgetPath);
                ret = super.drawChild(canvas, child, drawingTime);
                canvas.restore();
                return ret;
            }

            if (!data.hasShadow && !data.needClip) {
                return super.drawChild(canvas, child, drawingTime);
            }
            //为解决锯齿问题，正式环境采用xfermode
            if (data.hasShadow) {
                int count = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);//获取图层
                shadowPaint.setShadowLayer(data.shadowEvaluation, data.shadowDx, data.shadowDy, data.shadowColor);
                shadowPaint.setColor(data.shadowColor);
                canvas.drawPath(data.widgetPath, shadowPaint);
                shadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                shadowPaint.setColor(Color.WHITE);
                canvas.drawPath(data.widgetPath, shadowPaint);
                shadowPaint.setXfermode(null);
                canvas.restoreToCount(count);
            }
            if (data.needClip) {//是否需要圆角的裁剪
                int count = canvas.saveLayer(child.getLeft(), child.getTop(), child.getRight(), child.getBottom(), null, Canvas.ALL_SAVE_FLAG);
                ret = super.drawChild(canvas, child, drawingTime);
                clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                clipPaint.setColor(Color.WHITE);
                canvas.drawPath(data.clipPath,clipPaint);
                clipPaint.setXfermode(null);
                canvas.restoreToCount(count);
            }
        }

        return ret;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //初始化LayoutParamsData的Paths
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            View v = getChildAt(i);
            ViewGroup.LayoutParams lp = v.getLayoutParams();
            if (lp instanceof EasyLayoutParams) {
                EasyLayoutParams elp = (EasyLayoutParams) lp;
                elp.getData().initPaths(v);
            }
        }
    }


    static class LayoutParams extends ConstraintLayout.LayoutParams implements EasyLayoutParams {
        private final LayoutParamsData mData;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            //获取自定义的属性
            mData = new LayoutParamsData(c, attrs);
        }

        @Override
        public LayoutParamsData getData() {
            return mData;
        }
    }
}
