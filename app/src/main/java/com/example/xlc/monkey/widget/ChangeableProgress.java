package com.example.xlc.monkey.widget;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.example.xlc.monkey.R;

/**
 * @author:xlc
 * @date:2018/12/18
 * @descirbe:https://mp.weixin.qq.com/s/2vlSAtAUA_cWBGKsZ8cGLw 可变可旋转的水平进度条
 */
public class ChangeableProgress extends View {

    private int bgColor;
    private int borderColor;
    private int progressColor;

    private final String def_borderColor = "#239936";
    private final String def_bgColor = "#00000000";
    private final String def_progressColor = def_borderColor;

    private float viewWidth;
    private float viewHeight;

    private float borderWidth = 4;
    private int allInterval = 0;
    private int leftInterval;
    private int topInterval;
    private int rightInterval;
    private int bottomInterval;
    private boolean useAnimation = true;
    private float radius = 0;
    private float nowProgress = 30;
    //用于动画计算
    private float scaleProgress = nowProgress;
    private float maxProgress = 100;
    private int angle = 0;
    private int duration = 1200;
    private Shader borderShader;
    private Shader bgShader;
    private Shader progressShader;

    private TimeInterpolator interpolator = new DecelerateInterpolator();

    private Paint bgPaint;
    private Paint borderPaint;
    private Paint progressPaint;
    private Path progressPath;
    private Path resultPath;

    public ChangeableProgress(Context context) {
        super(context);
        init(null);
    }

    public ChangeableProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ChangeableProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        bgColor = Color.parseColor(def_bgColor);
        borderColor = Color.parseColor(def_borderColor);
        progressColor = Color.parseColor(def_progressColor);

        if (attrs == null) {
            return;
        }
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ChangeableProgress);
        viewWidth = a.getDimension(R.styleable.ChangeableProgress_viewWidth, 0);
        viewHeight = a.getDimension(R.styleable.ChangeableProgress_viewHeight, 0);
        bgColor = a.getColor(R.styleable.ChangeableProgress_bgColor, bgColor);
        borderColor = a.getColor(R.styleable.ChangeableProgress_borderColor, borderColor);
        progressColor = a.getColor(R.styleable.ChangeableProgress_progressColor, progressColor);

        borderWidth = a.getDimension(R.styleable.ChangeableProgress_borderWidth, 4);
        allInterval = (int) a.getDimension(R.styleable.ChangeableProgress_allInterval, 0);
        leftInterval = (int) a.getDimension(R.styleable.ChangeableProgress_leftInterval, 0);
        topInterval = (int) a.getDimension(R.styleable.ChangeableProgress_topInterval, 0);
        rightInterval = (int) a.getDimension(R.styleable.ChangeableProgress_rightInterval, 0);
        bottomInterval = (int) a.getDimension(R.styleable.ChangeableProgress_bottomInterval, 0);
        useAnimation = a.getBoolean(R.styleable.ChangeableProgress_useAnimation, true);
        radius = a.getDimension(R.styleable.ChangeableProgress_radius, 0);
        maxProgress = a.getFloat(R.styleable.ChangeableProgress_maxProgress, 100);
        nowProgress = a.getFloat(R.styleable.ChangeableProgress_nowProgress, 30);
        angle = a.getInt(R.styleable.ChangeableProgress_angle, 0);
        duration = a.getInt(R.styleable.ChangeableProgress_duration, 1200);

        if (maxProgress <= 0) {
            this.maxProgress = 0;
        }
        if (nowProgress > maxProgress) {
            this.nowProgress = maxProgress;
        } else if (nowProgress < 0) {
            this.nowProgress = 0;
        }

        scaleProgress = nowProgress;
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int mWidth = 400;
        int mHeight = 30;
        if (viewWidth + borderWidth > mWidth) {
            mWidth = (int) (viewWidth + borderWidth);
        }

        if (viewHeight + borderWidth > mHeight) {
            mHeight = (int) (viewHeight + borderWidth);
        }

        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (viewWidth == 0 && viewHeight == 0) {
            if (isHorizontal(angle)) {
                viewWidth = getWidth() - borderWidth;
                viewHeight = getHeight() - borderWidth;
            } else if (isVertical(angle)) {
                viewWidth = getHeight() - borderWidth;
                viewHeight = getWidth() - borderWidth;
            } else {
                viewWidth = 300;
                viewHeight = 20;
            }
        } else if (viewHeight == 0) {
            viewHeight = getHeight() - borderWidth;
        } else if (viewWidth == 0) {
            viewWidth = getWidth() - borderWidth;
        }
        radius =viewHeight/2;
        progressPath = new Path();
        resultPath = new Path();
        initPaint();
    }

    private void initPaint() {
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);

        bgPaint.setColor(bgColor);
        progressPaint.setColor(progressColor);

        float scaleAngle = angle%360;
        canvas.translate(getWidth()/2,getHeight()/2);
        if (scaleAngle>0) {
            canvas.rotate(scaleAngle);
        }
        drawBg(canvas);
    }

    private void drawBg(Canvas canvas) {
        if (bgShader!=null) {
            bgPaint.setShader(bgShader);
        }else{
            bgPaint.setShader(null);
        }

        RectF rectF=new RectF(-viewWidth/2,-viewHeight/2, viewWidth/2, viewHeight /2);
    }

    /**
     * 是否是水平
     *
     * @param angle
     */
    private boolean isHorizontal(int angle) {
        if (angle % 180 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是垂直水平
     *
     * @param angle
     * @return
     */
    private boolean isVertical(int angle) {
        if (isHorizontal(angle)) {
            return false;
        } else {
            if (angle % 90 == 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}
