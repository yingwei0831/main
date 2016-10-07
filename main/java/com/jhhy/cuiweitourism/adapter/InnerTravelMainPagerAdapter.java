package com.jhhy.cuiweitourism.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/24.
 */
public class InnerTravelMainPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = InnerTravelMainPagerAdapter.class.getSimpleName();

    private List<String> titles;
    private List<Fragment> fragments;

    public InnerTravelMainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public InnerTravelMainPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments){
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.i(TAG, "position = " + position);
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        LogUtil.i(TAG, "count = " + fragments.size());
        return fragments.size();
    }
}
