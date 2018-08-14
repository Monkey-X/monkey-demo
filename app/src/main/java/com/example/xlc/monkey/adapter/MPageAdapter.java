package com.example.xlc.monkey.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.xlc.monkey.fragment.FirstFragment;
import com.example.xlc.monkey.fragment.SecondFragment;

public class MPageAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitle = new String[]{"tab01", "tab02"};
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;

    public MPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (mFirstFragment == null) {
                mFirstFragment = new FirstFragment();
            }
            return mFirstFragment;
        }else if (position ==1){
            if (mSecondFragment == null) {
                mSecondFragment =new SecondFragment();
            }
            return mSecondFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
