package com.example.xlc.monkey.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * @author xlc
 * @date: 2018/8/10  15:34
 * @description: 设置滚动的recycleview
 **/
public class ScrollLinearLayoutManager extends LinearLayoutManager {

    private float MILLISECONDS_PER_INCH = 5525f; //修改数据，越大速度越慢
    private Context context;

    public ScrollLinearLayoutManager(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {

                return ScrollLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                //返回滑动一个pixel需要多少毫秒
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    //用来设置滚动速度
    public void setSpeedSlow(float x) {
        //使用density，在不同分辨率设备上滑动速度相同
        //0.3f 可以根据不同需求自己修改
        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * 0.3f + x;
    }
}
