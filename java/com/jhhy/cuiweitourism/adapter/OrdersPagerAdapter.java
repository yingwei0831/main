package com.jhhy.cuiweitourism.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/8/18.
 */
public class OrdersPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private List<Fragment> fragments;                         //fragment列表

    public OrdersPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % titles.length];
    }

    public Fragment getCurrentFragment(int currentItem) {
        if (fragments != null) {
            return fragments.get(currentItem);
        } else {
            return null;
        }
    }
}
