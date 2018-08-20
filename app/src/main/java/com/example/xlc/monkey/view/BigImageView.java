package com.example.xlc.monkey.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author xlc
 * @date 2018/8/20
 * 描述  加载超大图片，和手势滑动修改显示图片的区域
 */
public class BigImageView extends View {

    private Rect mRectF = new Rect();
    private static BitmapFactory.Options sOptions = new BitmapFactory.Options();
    private BitmapRegionDecoder mRegionDecoder;
    private int mImageWidth;
    private int mImageHeight;

    {
        sOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置图片的流
     *
     * @param inputStream
     */
    public void SetInputStream(InputStream inputStream) {
        try {
            mRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//当前只获取图片的大小，不申请内存
            BitmapFactory.decodeStream(inputStream, null, options);
            mImageWidth = options.outWidth;
            mImageHeight = options.outHeight;
            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int downX = 0;
    int downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int curX = (int) event.getX();
                int curY = (int) event.getY();
                int moveX = curX - downX;
                int moveY = curY - downY;
                onMove(moveX, moveY);
                downX = curX;
                downY = curY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void onMove(int moveX, int moveY) {
        if (mImageWidth > getWidth()) {
            mRectF.offset(-moveX, 0);
            checkWidth();
            invalidate();
        }
        if (mImageHeight > getHeight()) {
            mRectF.offset(0, -moveY);
            checkHeight();
            invalidate();
        }
    }

    private void checkHeight() {
        Rect rect = mRectF;
        if (rect.bottom > mImageHeight) {
            rect.bottom = mImageHeight;
            rect.top = mImageHeight - getHeight();
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    private void checkWidth() {
        Rect rect = mRectF;
        if (rect.right > mImageWidth) {
            rect.right = mImageWidth;
            rect.left = mImageWidth - getWidth();
        }

        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = width;
        mRectF.bottom = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRegionDecoder.decodeRegion(mRectF, sOptions);
    }
}
