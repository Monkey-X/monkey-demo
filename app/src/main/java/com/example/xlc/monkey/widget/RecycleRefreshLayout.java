package com.example.xlc.monkey.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author:xlc
 * @date:2018/8/28
 * @descirbe:下拉刷新上啦加载更多 android.view.ViewConfiguration包含了一些方法和常量，用于设置UI的超时、大小、距离属性常量都是私有的，通过对应的get方法，获取配置值。
 */
public class RecycleRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    //滑动距离代滚动
    private int mTouchSlop;
    private SuperRefreshLayoutListener mListener;
    //是否正在加载
    private boolean mIsOnLoading = false;
    //是否加载更多
    private boolean mCanLoadMore = true;

    private boolean mHasMore = true;
    private float mYDown;
    private float mLastY;
    private int mBottomCount = 1;

    public RecycleRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public RecycleRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //满足这个像素距离，可以认为用户在滚动中
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (mListener != null && !mIsOnLoading) {
            mListener.onRefreshing();
        } else {
            setRefreshing(false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //初始化listview对象
        if (mRecyclerView == null) {
            getRecycleView();
        }
    }

    /**
     * 获取recyclerView
     */
    private void getRecycleView() {
        if (getChildCount() > 0) {
            View childView = getChildAt(0);
            if (!(childView instanceof RecyclerView)) {
                //                childView = findViewById(R.id.recyclerView);
            }
            if (childView != null && childView instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) childView;
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (canLoad() && mCanLoadMore) {
                            loadData();
                        } else if (isNextScrollBottom() && mListener != null && mCanLoadMore && !mIsOnLoading) {
                            mListener.onScrollToBottom();
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 判断是否到了最底部
     *
     * @return
     */
    private boolean isNextScrollBottom() {
        return (mRecyclerView != null && mRecyclerView.getAdapter() != null)
                && getLastVisiblePosition() == (mRecyclerView.getAdapter().getItemCount() - 1);
    }

    /**
     * 判断是否到了最底部
     *
     * @return
     */
    private boolean isScrollBottom() {
        return (mRecyclerView != null && mRecyclerView.getAdapter() != null)
                && getLastVisiblePosition() == (mRecyclerView.getAdapter().getItemCount() - mBottomCount);
    }

    /**
     * 获取recycleview可见的最后一项
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = mRecyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获取最大值
     *
     * @param lastPositions
     * @return
     */
    private int getMaxPosition(int[] lastPositions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : lastPositions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }

    private void loadData() {
        if (mListener != null) {
            setOnLoading(true);
            mListener.onLoadMore();
        }
    }

    /**
     * 设置正在加载
     *
     * @param loading
     */
    public void setOnLoading(boolean loading) {
        mIsOnLoading = loading;
        if (!mIsOnLoading) {
            mYDown = 0;
            mLastY = 0;
        }

    }


    /**
     * 加载结束时调用
     */
    public void onComplete(){
        setOnLoading(false);
        setRefreshing(false);
        mHasMore = true;
    }

    /**
     * 是否可加载更多
     * @param canLoadMore
     */
    public void setCanLoadMore(boolean canLoadMore){
        this.mCanLoadMore = canLoadMore;
    }

    /**
     * 是否正在加载
     *
     * @return
     */
    public boolean isLoading() {
        return mIsOnLoading;
    }

    /**
     * 距离底部几个条目时可以加载更多
     *
     * @param bottomCount
     */
    public void setBottomCount(int bottomCount) {
        this.mBottomCount = bottomCount;
    }

    /**
     * 是否可以加载更多，
     *
     * @return
     */
    private boolean canLoad() {
        return isScrollBottom() && !mIsOnLoading && isPullUp() && mHasMore;

    }

    /**
     * 是否是上啦操作
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;

    }


    /**
     * 添加加载和刷新
     *
     * @param listener
     */
    public void setSuperRefreshLayoutListener(SuperRefreshLayoutListener listener) {
        this.mListener = listener;
    }

    public interface SuperRefreshLayoutListener {

        void onRefreshing();

        void onLoadMore();

        void onScrollToBottom();
    }
}
