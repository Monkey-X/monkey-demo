package com.example.xlc.monkey.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.xlc.monkey.R;

/**
 * @author:xlc
 * @date:2018/8/31
 * @descirbe:进度圆环
 */
public class ProgressRing extends View {

    private int progressStartColor;
    private int progressEndColor;
    private int bgStartColor;
    private int bgMidColor;
    private int bgEndColor;
    private int progress;
    private float progressWidth;
    private int startAngle;
    private int sweepAngle;
    private boolean showAnim;


    private float unitAngle;
    private int curProgress = 0;
    private RectF mRectF;

    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private int mMeasuredHeight;
    private int mMeasuredWidth;


    public ProgressRing(Context context) {
        this(context, null);
    }

    public ProgressRing(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressRing);
        progressStartColor = typedArray.getColor(R.styleable.ProgressRing_pr_bg_start_color, Color.YELLOW);
        progressEndColor = typedArray.getColor(R.styleable.ProgressRing_pr_progress_end_color, Color.YELLOW);
        bgStartColor = typedArray.getColor(R.styleable.ProgressRing_pr_bg_start_color, Color.LTGRAY);
        bgMidColor = typedArray.getColor(R.styleable.ProgressRing_pr_bg_mid_color, Color.LTGRAY);
        bgEndColor = typedArray.getColor(R.styleable.ProgressRing_pr_bg_end_color, Color.LTGRAY);
        progress = typedArray.getInt(R.styleable.ProgressRing_pr_progress, 0);
        progressWidth = typedArray.getDimension(R.styleable.ProgressRing_pr_progress_width, 8f);
        startAngle = typedArray.getInt(R.styleable.ProgressRing_pr_start_angle, 150);
        sweepAngle = typedArray.getInt(R.styleable.ProgressRing_pr_sweep_angle, 240);
        showAnim = typedArray.getBoolean(R.styleable.ProgressRing_pr_show_anim, true);
        typedArray.recycle();

        unitAngle = (float) (sweepAngle / 100.0);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setStrokeWidth(progressWidth);

        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(progressWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();
        if (mRectF == null) {
            float halfProgressWidth = progressWidth / 2;
            mRectF = new RectF(getPaddingLeft() + halfProgressWidth
                    , getPaddingTop() + halfProgressWidth
                    , mMeasuredWidth - halfProgressWidth - getPaddingRight()
                    , mMeasuredHeight - halfProgressWidth - getPaddingBottom());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!showAnim) {
            curProgress = progress;
        }
        drawBg(canvas);
        drawProgress(canvas);
        if (curProgress < progress) {
            curProgress++;
            postInvalidate();
        }


    }

    private void drawProgress(Canvas canvas) {
        for (int i = 0, end = (int) (curProgress * unitAngle); i <= end; i++) {
            progressPaint.setColor(getGradient(i / (float) end, progressStartColor, progressEndColor));
            canvas.drawArc(mRectF, startAngle + i, 1, false, progressPaint);
        }
    }


    /**
     * 绘画进度之外的背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        int halfSweep = sweepAngle / 2;
        for (int i = sweepAngle, st = (int) (curProgress * unitAngle); i > st; --i) {
            if (i - halfSweep > 0) {
                bgPaint.setColor(getGradient((i - halfSweep) / halfSweep, bgMidColor, bgEndColor));
            } else {
                bgPaint.setColor(getGradient((halfSweep - i) / halfSweep, bgMidColor, bgStartColor));
            }

            canvas.drawArc(mRectF, startAngle + i, 1, false, bgPaint);
        }
    }

    private int getGradient(float fraction, int startColor, int endColor) {
        if (fraction > 1)
            fraction = 1;
        int alphaStart = Color.alpha(startColor);
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaEnd = Color.alpha(endColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaDifference = alphaEnd - alphaStart;
        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaCurrent = (int) (alphaStart + fraction * alphaDifference);
        int redCurrent = (int) (redStart + fraction * redDifference);
        int blueCurrent = (int) (blueStart + fraction * blueDifference);
        int greenCurrent = (int) (greenStart + fraction * greenDifference);
        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }


    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }
}
