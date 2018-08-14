package com.example.xlc.monkey.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.xlc.monkey.R;
import com.example.xlc.monkey.adapter.MPageAdapter;
import com.example.xlc.monkey.base.BaseActivity;

import butterknife.BindView;

public class ScrollingActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scrolling;
    }

    @Override
    protected void initView() {
        mViewpager.setOffscreenPageLimit(2);
        mViewpager.setAdapter(new MPageAdapter(getSupportFragmentManager()));
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    protected void initData() {

    }


}
