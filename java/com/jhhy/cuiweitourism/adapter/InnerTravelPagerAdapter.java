package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

import java.util.List;

/**
 * Created by jiahe008 on 2016/8/10.
 */
public class InnerTravelPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment;                         //fragment列表
    private List<String> titles;                              //tab名的列表
    private Context context;

    public InnerTravelPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public InnerTravelPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.context = context;
        this.listFragment = fragments;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_circle_corner, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_custom_tab_text);
        tv.setText(titles.get(position));
        return v;
    }
}
