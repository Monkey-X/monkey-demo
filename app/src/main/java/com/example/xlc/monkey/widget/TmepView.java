package com.example.xlc.monkey.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.xlc.monkey.R;

/**
 * @author:xlc
 * @date:2018/10/8
 * @descirbe:自定义温度指示条
 */
public class TmepView extends View {

    private Context mContext;
    private Paint mPaint;
    private float selection;

    private int width;
    private int height;
    //圆角矩形的宽高
    private int defaultTextSize = 30;
    private int defaultTempHeight = dipToPx(20);
    private int textSpace = dipToPx(5);
    private TextPaint mTextPaint;
    private Path mPath;
    private Paint paint;
    //设置温度的最大范围
    private float maxCount = 100f;
    //设置当前温度
    private float currentCount = 20f;
    //分段颜色
    private static final int[] SECTION_COLORS = {Color.GREEN, Color.YELLOW, Color.RED};

    //指针的宽高
    private int defaultIndicatorWidth = dipToPx(10);
    private int defaultIndicatorHeight = dipToPx(8);
    private RectF mRectProgressBg;
    private LinearGradient mShader;


    public TmepView(Context context) {
        this(context, null);
    }

    public TmepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TmepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        //圆角矩形paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //文本paint
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(defaultTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mContext.getResources().getColor(R.color.text_green));
        //三角形指针
        mPath = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //确定圆角矩形的范围，在view的最底部，top的位置为总高度-圆角矩形的高度
        mRectProgressBg = new RectF(0, height - defaultTempHeight, width, height);
        mShader = new LinearGradient(0, height - defaultTempHeight, width, height, SECTION_COLORS, null, Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        //绘制圆角矩形  defaultTempHeight/2 确定圆角的圆心位置
        canvas.drawRoundRect(mRectProgressBg, defaultTempHeight / 2, defaultTempHeight / 2, mPaint);

        selection = currentCount / maxCount;
        //绘制指针  指针的位置在当前温度的位置，也就是三角形的顶点落在当前温度的位置

        //定义三角形的左边点的坐标
        mPath.moveTo(width * selection - defaultIndicatorWidth / 2, height - defaultTempHeight);
        mPath.lineTo(width * selection + defaultIndicatorWidth / 2, height - defaultTempHeight);
        mPath.lineTo(width * selection, height - defaultTempHeight - defaultIndicatorHeight);
        mPath.close();
        paint.setShader(mShader);
        canvas.drawPath(mPath, paint);
        //绘制文本
        String text = currentCount + "°c";
        canvas.drawText(text, width * selection, height - defaultTempHeight - defaultIndicatorHeight - textSpace, mTextPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            width = widthSpecSize;
        } else {
            width = 0;
        }
        //主要确定view的整体高度，渐变长条的高度+文本的高度+文本与指针的间隙
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            height = defaultTextSize + defaultTempHeight + defaultIndicatorHeight + textSpace;
        } else {
            height = heightSpecSize;
        }

        setMeasuredDimension(width, height);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 设置最大的温度值
     *
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * 设置当前的温度
     *
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        if (currentCount > maxCount) {
            this.currentCount = maxCount - 5;
        } else if (currentCount < 0f) {
            this.currentCount = 0f + 5;
        } else {
            this.currentCount = currentCount;
        }
        invalidate();
    }


    /**
     * 设置温度指针的大小
     *
     * @param width
     * @param height
     */
    public void setIndicatorSize(int width, int height) {
        this.defaultIndicatorWidth = width;
        this.defaultIndicatorHeight = height;
    }

    /**
     * 设置温度计的高度
     *
     * @param height
     */
    public void setTempHeight(int height) {
        this.defaultTempHeight = height;
    }


    /**
     * 设置文字的大小
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.defaultTextSize = textSize;
    }


    public float getMaxCount(){
        return maxCount;
    }


    public float getCurrentCount(){
        return currentCount;
    }

}
