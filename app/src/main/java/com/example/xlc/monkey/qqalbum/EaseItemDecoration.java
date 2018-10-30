package com.example.xlc.monkey.qqalbum;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

/**
 * @author:xlc
 * @date:2018/10/29
 * @descirbe: itemDecoration制作日期分割线
 */
public class EaseItemDecoration extends RecyclerView.ItemDecoration {

    private List<? extends AlbumBean> mDatas;
    private final Paint mPaint;
    private final Rect mBounds;
    private final Drawable mDivider;
    private final int mTitleHeight;
    private final int mTitileFontSize;
    private final LayoutInflater mInflater;
    private final int mWidth;


    private static int COLOR_TITLE_BG = Color.parseColor("#ffffff");
    private static int COLOR_TITLE_FONT = Color.parseColor("#333333");
    private static int COLOR_DATE_COLOR = Color.parseColor("#666666");


    public EaseItemDecoration(Context context, List<? extends AlbumBean> datas) {
        mDatas = datas;
        mPaint = new Paint();
        mBounds = new Rect();
        TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, context.getResources().getDisplayMetrics());
        mTitileFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitileFontSize);
        mPaint.setAntiAlias(true);
        mInflater = LayoutInflater.from(context);
        mWidth = getScreenWidth(context) / 3;
    }

    public void setDatas(List<? extends AlbumBean> datas) {
        this.mDatas = mDatas;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                if (position == 0) {
                    drawTitleArea(c, left, right, child, params, position);
                } else {
                    if (null != mDatas.get(position).getSubId() && !mDatas.get(position).getSubId().equals(mDatas.get(position - 1).getSubId())) {
                        drawTitleArea(c, left, right, child, params, position);
                    }
                }
            }
        }
    }

    /**
     * 绘制title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        String date = mDatas.get(position).getTitle();
        mPaint.getTextBounds(date, 0, date.length(), mBounds);
        c.drawText(date, (mWidth * 3) / 2 - mBounds.width() / 2, child.getTop() - params.topMargin - mTitleHeight / 2, mPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1 && position < mDatas.size()) {
            if (position + 1 < mDatas.size()) {
                if (null != mDatas.get(position).getSubId() && !mDatas.get(position).getSubId().equals(mDatas.get(position + 1).getSubId())) {
                    int d = 2 - (mDatas.get(position).value + position) % 3;
                    if (isNewLine(position, mDatas)) {
                        outRect.set(0, mTitleHeight, mWidth * d + mDivider.getIntrinsicHeight(), mDivider.getIntrinsicHeight());
                    } else {
                        outRect.set(0, 0, mWidth * d + mDivider.getIntrinsicHeight(), mDivider.getIntrinsicHeight());
                    }
                } else {
                    if (isNewLine(position, mDatas)) {
                        outRect.set(0, mTitleHeight, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicHeight());
                    } else {
                        outRect.set(0, 0, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicHeight());
                    }
                }
            } else {
                if (isNewLine(position, mDatas)) {
                    outRect.set(0, mTitleHeight, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicHeight());
                } else {
                    outRect.set(0, 0, mDivider.getIntrinsicHeight(), mDivider.getIntrinsicHeight());
                }
            }
        }
    }

    private boolean isNewLine(int position, List<? extends AlbumBean> datas) {
        boolean isNew = false;
        if (position == 0 || position == 1 || position == 2) {
            return true;
        }

        int size = position > 2 ? position - 3 : 0;
        for (int i = size; i < position; i++) {
            if (null != mDatas.get(i).getSubId() && !mDatas.get(i).getSubId().equals(mDatas.get(i + 1).getSubId())) {
                isNew = true;
                break;
            }
        }
        return isNew;
    }

    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
}
