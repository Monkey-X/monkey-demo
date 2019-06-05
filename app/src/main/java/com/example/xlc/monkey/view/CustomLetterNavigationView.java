package com.example.xlc.monkey.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.xlc.monkey.R;

import androidx.annotation.Nullable;

/**
 * @author:xlc
 * @date:2019/5/29
 * @descirbe:自定义字母导航栏 自定义字母导航栏
 * 1.测量控件尺寸
 * 2.绘制显示的内容
 * 3.处理滑动事件
 * 4.暴露接口
 * <p>
 * 定义自定义的属性
 */
public class CustomLetterNavigationView extends View {

    private static final String TAG = "CustomLetterNavigation";
    //导航栏的内容
    private String[] navigationContent;
    //导航栏的内容间隔
    private float contentDiv;
    //导航栏的文字大小
    private float contentTextSize;
    //导航栏的文字颜色
    private int contentTextColor;
    //导航栏按下时的背景颜色
    private int backgroundColor;
    //导航栏按下时的圆角度数
    private int backgroundAngle = 0;
    //导航栏按下时的文字颜色
    private int downContentTextColor;
    private TextPaint mTextPaint;
    private Paint mBackgroundPaint;

    private boolean eventActionState = false;
    private String currentLetter = "";

    public CustomLetterNavigationView(Context context) {
        super(context, null);
    }

    public CustomLetterNavigationView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomLetterNavigationView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultData();
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLetterNavigationView);
        contentTextColor = typedArray.getColor(R.styleable.CustomLetterNavigationView_customTextColorDefault, contentTextColor);
        backgroundColor = typedArray.getColor(R.styleable.CustomLetterNavigationView_customBackgroundColorDown, backgroundColor);
        downContentTextColor = typedArray.getColor(R.styleable.CustomLetterNavigationView_customTextColorDown, downContentTextColor);
        contentTextSize = typedArray.getDimension(R.styleable.CustomLetterNavigationView_customTextSize, contentTextSize);
        contentDiv = typedArray.getFloat(R.styleable.CustomLetterNavigationView_customLetterDivHeight, contentDiv);
        backgroundAngle = typedArray.getInt(R.styleable.CustomLetterNavigationView_customBackgroundAngle, backgroundAngle);
        typedArray.recycle();
    }

    private void initDefaultData() {
        navigationContent = new String[]{"搜", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        contentDiv = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        contentTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        contentTextColor = Color.parseColor("#333333");
        downContentTextColor = Color.WHITE;
        backgroundColor = Color.parseColor("#d7d7d7");
        backgroundAngle = 0;
        //绘制文字的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(contentTextSize);
        mTextPaint.setColor(contentTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //绘制背景的画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 对空间的宽高做出适应，
         * 大于内容的最小尺寸，显示控件尺寸
         * 反之使用内容的最小尺寸
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取尺寸的尺寸
        int actualWidth = MeasureSpec.getSize(widthMeasureSpec);
        int actualHeight = MeasureSpec.getSize(heightMeasureSpec);
        int contentLength = getContentLength();
        //计算一个文字的尺寸
        Rect rect = measureTextSize();
        //内容的最小宽度
        float contentWidth = rect.width() + contentDiv * 2;
        //内容的最小高度
        float contentHeight = rect.height() * contentLength + contentDiv * (contentLength + 3);
        if (MeasureSpec.AT_MOST == widthMode) {//包裹内容
            actualWidth = (int) contentWidth + getPaddingLeft() + getPaddingRight();
        } else if (MeasureSpec.EXACTLY == widthMode) {
            //宽度限制
            if (actualWidth < contentWidth) {
                actualWidth = (int) contentWidth + getPaddingLeft() + getPaddingRight();
            }
        }

        if (MeasureSpec.AT_MOST == heightMode) {//高度包裹内容
            actualHeight = (int) contentHeight + getPaddingTop() + getPaddingBottom();
        } else if (MeasureSpec.EXACTLY == heightMode) {
            if (actualHeight < contentHeight) {
                actualHeight = (int) contentHeight + getPaddingTop() + getPaddingBottom();
            }
        }
        setMeasuredDimension(actualWidth, actualHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 绘制背景 drawRoundRect
         * 绘制显示的文本
         *    定位每个文字的坐标
         *    X轴宽度的一半
         *    Y轴每个字符的高度乘以绘制字符的数目
         */
        int viewWidth = getWidth();
        //绘制背景
        RectF rectF = new RectF(0, 0, viewWidth, getHeight());

        if (eventActionState) {
            mTextPaint.setColor(downContentTextColor);
            mBackgroundPaint.setColor(backgroundColor);
            canvas.drawRoundRect(rectF, backgroundAngle, backgroundAngle, mBackgroundPaint);
        } else {
            mTextPaint.setColor(contentTextColor);
            mBackgroundPaint.setColor(backgroundColor);
            Drawable background = getBackground();
            if (background instanceof ColorDrawable) {
                mBackgroundPaint.setColor(((ColorDrawable) background).getColor());
            }
            canvas.drawRoundRect(rectF, backgroundAngle, backgroundAngle, mBackgroundPaint);
        }

        //开始绘制文本
        float textX = viewWidth / 2;
        int contentLength = getContentLength();
        float heightShould = (getHeight() - contentDiv * 2 - getPaddingTop() - getPaddingBottom()) / contentLength;
        for (int i = 0; i < contentLength; i++) {
            float startY = ((i + 1) * heightShould) + getPaddingTop();
            //绘制文字
            canvas.drawText(navigationContent[i], textX, startY, mTextPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventActionState = true;
                invalidate();
                if (mOnNavigationScrollerListener != null) {
                    mOnNavigationScrollerListener.onDown();
                }
                scrpllCount(eventY);
                break;
            case MotionEvent.ACTION_MOVE:
                scrpllCount(eventY);
                break;
            case MotionEvent.ACTION_UP:
                eventActionState = false;
                invalidate();
                if (mOnNavigationScrollerListener != null) {
                    mOnNavigationScrollerListener.onUp();
                }
                break;
        }
        return true;
    }

    /**
     * 滑动计算，获取字符，返回字符
     * @param eventY
     */
    private void scrpllCount(float eventY) {
        Rect rect = measureTextSize();
        int index = (int) ((eventY - getPaddingTop() - getPaddingBottom() - contentDiv * 2) / (rect.height() + contentDiv));
        if (index >= 0 && index < getContentLength()) {
            String newLetter = navigationContent[index];
            //防止重复触发返回
            if (currentLetter.equals(newLetter)) {
                currentLetter = newLetter;
                if (mOnNavigationScrollerListener != null) {
                    mOnNavigationScrollerListener.onScroll(currentLetter, index);
                }
            }
        }
    }

    /**
     * 测量一个文字的尺寸
     */
    private Rect measureTextSize() {
        Rect rect = new Rect();
        if (mTextPaint != null) {
            mTextPaint.getTextBounds("田", 0, 1, rect);
        }
        return rect;
    }

    /**
     * 获取内容的长度
     *
     * @return
     */
    private int getContentLength() {
        if (navigationContent != null) {
            return navigationContent.length;
        }
        return 0;
    }

    /**
     * 设置导航栏的内容
     *
     * @param content
     */
    public void setNavigationContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            navigationContent = null;
            navigationContent = new String[content.length()];
            for (int i = 0; i < content.length(); i++) {
                navigationContent[i] = String.valueOf(content.charAt(i));
            }
        }
        requestLayout();
    }

    private OnNavigationScrollerListener mOnNavigationScrollerListener;

    public void setOnNavigationScrollerListener(OnNavigationScrollerListener onNavigationScrollerListener) {
        this.mOnNavigationScrollerListener = onNavigationScrollerListener;
    }


    public interface OnNavigationScrollerListener {
        void onDown();

        void onScroll(String letter, int position);

        void onUp();
    }
}
