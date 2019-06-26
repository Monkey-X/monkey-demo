package com.example.xlc.monkey.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author:xlc
 * @date:2019/6/26
 * @descirbe:修改RecyalerView的布局方式,实现无限循环滚动
 */
public class LooperLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "LooperLayoutManager";
    private boolean looperEnable = true;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public void setLooperEnable(boolean looperEnable) {
        this.looperEnable = looperEnable;
    }

    @Override
    public boolean canScrollHorizontally() {//打开横向滚动
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    /**
     * 对所有的itemView进行布局，一般在初始化或者notifyDataSetChanged()方法时调用
     * 有一个二级缓存，一级缓存scrap缓存，二级是recycler缓存
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        //preLayout主要支持动画，直接跳过
        if (state.isPreLayout()) {
            return;
        }
        //将视图分离放入scrap缓存中，已准备重新对view进行排版
        detachAndScrapAttachedViews(recycler);

        int autualWdith = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View itemView = recycler.getViewForPosition(i);//从缓存里获取itemView，缓存里没有就adapter中创建
            addView(itemView);
            //测量itemView的宽高
            measureChildWithMargins(itemView, 0, 0);
            int height = getDecoratedMeasuredHeight(itemView);
            int width = getDecoratedMeasuredWidth(itemView);
            //根据itemView的宽高进行布局
            layoutDecorated(itemView, autualWdith, 0, autualWdith + width, height);
            autualWdith += width;
            //如果当前布局过得itemView的宽度总和大于recyclerView的宽，则不再进行布局
            if (autualWdith > getWidth()) {
                break;
            }
        }
    }

    /**
     * //对recyclerView进行滑动操作
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //1.横向滑动的时候，对左右两边按顺序填充itemview
        int travl = fill(dx, recycler, state);
        if (travl ==0) {
            return 0;
        }

        //2.滚动
        offsetChildrenHorizontal(travl*-1);

        //3.回收已经离开界面的
        recyclerHideView(dx,recycler,state);

        return travl;
    }

    /***
     * 回收界面不可见的view
     * @param dx
     * @param recycler
     * @param state
     */
    private void recyclerHideView(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < getChildCount(); i++) {

            View view = getChildAt(i);
            if (view == null) {
                continue;
            }
            if (dx>0) {
                //向左滚动，移除一个左边不在内容里的view
                if (view.getRight() <0) {
                    removeAndRecycleView(view,recycler);
                }
            }else{
                //向右移动，移除一个右边不在内容里的view
                if (view.getLeft() >getWidth()) {
                    removeAndRecycleView(view,recycler);
                }
            }
        }
    }

    //左右滑动的时候，填充
    private int fill(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx > 0) {
            //向左滑动,可见的最后一个itemview完全滑进来，需要补充新的
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) {
                return 0;
            }
            //获取可见的最后一个itemview的索引
            int lastPos = getPosition(lastView);
            if (lastView.getRight() <= getWidth()) {
                View scrap = null;
                //如果是最后一个，则将下一个itemview设置成第一个，否则设置为当前索引的下一个
                if (lastPos == getItemCount() - 1) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }

                if (scrap == null) {
                    return dx;
                }
                //将新的itemview加进来并进行测量和布局
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);

                layoutDecorated(scrap, lastView.getRight(), 0, lastView.getRight() + width, height);
                return dx;
            }
        } else {
            //向右滚动
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstPos = getPosition(firstView);
            if (firstView.getLeft() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }

                addView(scrap,0);
                measureChildWithMargins(scrap,0,0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap,firstView.getLeft()-width,0,firstView.getLeft(),height);
            }

        }
        return dx;
    }
}
