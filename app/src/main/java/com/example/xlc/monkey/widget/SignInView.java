package com.example.xlc.monkey.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.utils.DensityUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author:xlc
 * @date:2018/8/30
 * @descirbe:签到足迹控件
 */
public class SignInView extends View {

    private float strokeWidth = 10;
    private int monthDay = 31;
    private Bitmap mCheckBitmap;
    private Bitmap mUnCheckBitmap;
    private Bitmap mOpenGiftBitmap;
    private Bitmap mCloseGiftBitmap;
    private int backColor = Color.parseColor("#C3DEEA");
    private int rashColor = Color.parseColor("#B2CADB");
    private int textColor = Color.parseColor("#60ADE5");
    private List<Bitmap> mBitmapList = new LinkedList<>();
    private RectF oval = new RectF();
    private int mHeight;
    private int mWidth;
    private int signInCount = 9;
    private Paint mPaint;

    public SignInView(Context context) {
        this(context, null);
    }

    public SignInView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignInView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        strokeWidth = DensityUtil.dip2px(context, 6);
        mCheckBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_check);
        mUnCheckBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_uncheck);
        mOpenGiftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gift);
        mCloseGiftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gift_unopen);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置本月的天数
     *
     * @param monthDay
     */
    public void setMonthDays(int monthDay) {
        if (monthDay == 0) {
            this.monthDay = 31;
        } else {
            this.monthDay = monthDay;
        }
        postInvalidate();
    }

    /**
     * 设置一共签到了几天
     *
     * @param days
     */
    public void setProgress(int days) {
        this.signInCount = days;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(backColor);
        mPaint.setStrokeWidth(strokeWidth);
        int rowCount = (monthDay % 7 == 0 ? monthDay / 7 : monthDay / 7 + 1);
        int rowHeight = mHeight / rowCount;
        int startX = 0 + rowHeight / 2;
        int endX = mWidth - rowHeight / 2;
        int days = 0;
        for (int a = 0; a < rowCount; a++) {
            if (a + 1 == rowCount) {
                endX = (endX - startX) / 7 * (monthDay % 7 == 0 ? 7 : (monthDay % 7)) + mCheckBitmap.getWidth() / 2;
            }
            mPaint.setStrokeWidth(strokeWidth);
            int y = rowHeight * a + rowHeight / 2;
            canvas.drawLine(startX, y, endX, y, mPaint);
            mPaint.setColor(rashColor);
            mPaint.setStrokeWidth(1);
            canvas.drawLine(startX, y, endX, y, mPaint);

            //判断需要画出的是左半边还是右半边的半个圆弧
            if (a % 2 != 0) {
                if (a + 1 != rowCount) {
                    drawLeftOrRightArc(true, canvas, 0 + strokeWidth, y, 0 + rowHeight + strokeWidth, y + rowHeight);
                }
            } else {
                if (a + 1 != rowCount) {
                    drawLeftOrRightArc(false, canvas, endX - rowHeight / 2 - strokeWidth, y, endX + rowHeight / 2 - strokeWidth, y + rowHeight);
                }
            }
            //判断这条线上礼物的点，以及顺序是顺序还是倒序着画
            mBitmapList.clear();
            int lastDay = (monthDay % 7) == 0 ? 7 : (monthDay % 7);
            for (int b = 0; b < (a + 1 == rowCount ? (lastDay) : 7); b++) {
                days++;
                if (days <= signInCount) {
                    if (days == 3 || days == 8 || days == 14 || days == 21 || days == monthDay) {
                        mBitmapList.add(a % 2 != 0 ? 0 : mBitmapList.size(), mOpenGiftBitmap);
                    } else {
                        mBitmapList.add(a % 2 != 0 ? 0 : mBitmapList.size(), mCheckBitmap);
                    }
                }else{
                    if (days == 3 || days == 8 || days == 14 || days == 21 || days == monthDay)
                    {
                        mBitmapList.add(a % 2 != 0 ? 0 : mBitmapList.size(), mCloseGiftBitmap);
                    } else
                    {
                        mBitmapList.add(a % 2 != 0 ? 0 : mBitmapList.size(), mUnCheckBitmap);
                    }
                }
            }

            drawImgs(mBitmapList,startX,endX,y,canvas);
        }

    }

    /**
     * 判断绘制左边半圆弧，还是右边的半圆弧
     * @param isLeft
     * @param canvas
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private void drawLeftOrRightArc(boolean isLeft, Canvas canvas, float left, int top, float right, int bottom) {

        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(backColor);
        if (isLeft) {
            mPaint.setStyle(Paint.Style.STROKE);
            oval.setEmpty();
            oval.set(left, top, right, bottom);
            canvas.drawArc(oval, 90, 180, false, mPaint);
            mPaint.setStrokeWidth(1);
            mPaint.setColor(rashColor);
            canvas.drawArc(oval, 90, 180, false, mPaint);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            oval.setEmpty();
            oval.set(left, top, right, bottom);
            canvas.drawArc(oval, 270, 180, false, mPaint);
            mPaint.setStrokeWidth(1);
            mPaint.setColor(rashColor);
            canvas.drawArc(oval, 270, 180, false, mPaint);
        }

        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(backColor);
    }

    /**
     * 画出路线上的图片，勾选，礼物
     *
     * @param bitmapList
     * @param startx
     * @param endx
     * @param y
     * @param canvas
     */
    private void drawImgs(List<Bitmap> bitmapList, float startx, float endx, float y, Canvas canvas) {
        if (!bitmapList.isEmpty()) {
            startx = startx - bitmapList.get(0).getWidth() / 2;
            int count = bitmapList.size();
            float bitmap_width = (endx - startx) / (count - 1);
            for (int i = 0; i < count; i++) {
                Bitmap bitmap = bitmapList.get(i);
                canvas.drawBitmap(bitmap, startx + (bitmap_width * i), y - bitmap.getHeight() / 2, mPaint);
            }
        }
    }
}
