package com.coagent.lyscs.stomachmanager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * 主页viewpager的fragment的adapter
 * Created by lyscs on 2017/5/31.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;

    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
