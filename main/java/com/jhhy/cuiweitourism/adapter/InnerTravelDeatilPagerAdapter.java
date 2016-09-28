package com.jhhy.cuiweitourism.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/14.
 */
public class InnerTravelDeatilPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;                         //fragment列表
    private String[] titles;                              //tab名的列表

//    public InnerTravelDeatilPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }

    public InnerTravelDeatilPagerAdapter(FragmentManager fm, Fragment[] fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % titles.length];
    }
}
