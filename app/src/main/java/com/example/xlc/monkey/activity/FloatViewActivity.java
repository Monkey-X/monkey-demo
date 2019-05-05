package com.example.xlc.monkey.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.base.BaseActivity;
import com.example.xlc.monkey.utils.DensityUtil;

import butterknife.BindView;

public class FloatViewActivity extends BaseActivity {


    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    @BindView(R.id.fab_like)
    FloatingActionButton mFabLike;
    @BindView(R.id.fab_write)
    FloatingActionButton mFabWrite;
    @BindView(R.id.fab_top)
    FloatingActionButton mFabTop;
    @BindView(R.id.gp_like)
    Group mGpLike;
    @BindView(R.id.gp_write)
    Group mGpWrite;
    @BindView(R.id.gp_top)
    Group mGpTop;

    //FloatingActionButton的宽高和高度是一致的
    private int width;

    //圆的半径
    private int radius;
    //动画的集合，控制动画的有序播放
    private AnimatorSet mAnimatorSet;

    @Override
    public int getLayoutId() {
        return R.layout.activity_float_view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //动态获取FloatingActionButtom的宽度
        mFabAdd.post(new Runnable() {
            @Override
            public void run() {
                width = mFabAdd.getMeasuredHeight();
            }
        });

        //xml里设置的半径
        radius = DensityUtil.dip2px(this,80);
    }

    @Override
    protected void initView() {
        setViewVisible(false);
        initListener();
    }

    private void initListener() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnimatorSet!=null && mAnimatorSet.isRunning()) {
                    return;
                }
                if (mGpLike.getVisibility() != View.VISIBLE) {
                    mAnimatorSet = new AnimatorSet();
                    ValueAnimator likeAnimator = getValueAnimator(mFabLike, false, mGpLike, 0);
                    ValueAnimator writeAnimator = getValueAnimator(mFabWrite, false, mGpWrite, 90);
                    ValueAnimator topAnimator = getValueAnimator(mFabTop, false, mGpTop, 90);
                    mAnimatorSet.playSequentially(likeAnimator,writeAnimator,topAnimator);
                    mAnimatorSet.start();
                }else{
                    mAnimatorSet = new AnimatorSet();
                    ValueAnimator likeAnimator = getValueAnimator(mFabLike, true, mGpLike, 0);
                    ValueAnimator writeAnimator = getValueAnimator(mFabWrite, true, mGpWrite, 90);
                    ValueAnimator topAnimator = getValueAnimator(mFabTop, true, mGpTop, 90);
                    mAnimatorSet.playSequentially(likeAnimator,writeAnimator,topAnimator);
                    mAnimatorSet.start();
                }
            }
        });
    }




    private ValueAnimator getValueAnimator(final FloatingActionButton button, final boolean reverse, final Group group, int angle){
            ValueAnimator animator;
        if (reverse) {
            animator = ValueAnimator.ofFloat(1,0);
        }else{
            animator = ValueAnimator.ofFloat(0,1);
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) button.getLayoutParams();
                params.circleRadius = (int) (radius*v);
                params.width = (int) (width*v);
                params.height = (int) (width*v);
                button.setLayoutParams(params);
            }
        });
        animator.addListener(new SimpleAnimation() {
            @Override
            public void onAnimationStart(Animator animation) {
                group.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (group == mGpLike && reverse) {
                    setViewVisible(false);
                }
            }
        });
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        return animator;
    }

    private void setViewVisible(boolean isShow) {
        mGpLike.setVisibility(isShow ? View.VISIBLE:View.GONE);
        mGpWrite.setVisibility(isShow? View.VISIBLE:View.GONE);
        mGpTop.setVisibility(isShow ? View.VISIBLE:View.GONE);
    }

    @Override
    protected void initData() {

    }


}

abstract  class  SimpleAnimation implements Animator.AnimatorListener{

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
