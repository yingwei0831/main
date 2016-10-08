package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.fragment.Tab2BottomContentFragment;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.TouchPanelLayoutModify;

import java.util.ArrayList;
import java.util.List;

public class InnerTravelDetailActivity3 extends BaseActivity implements TouchPanelLayoutModify.IConfigCurrentPagerScroll, TouchPanelLayoutModify.OnViewUpdateListener
{

    private TouchPanelLayoutModify mTouchPanelLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String[] mTitles = new String[]{"商品详情", "费用说明", "行程描述", "预订须知"};
    private List<Fragment> mContent = new ArrayList<>(4);
    private OrdersPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_travel_detail3);
        mTouchPanelLayout = (TouchPanelLayoutModify) findViewById(R.id.touch_panel_layout_modify);
//        mTouchPanelLayout = (TouchPanelLayoutModify) LayoutInflater.from(this).inflate(R.layout.activity_inner_travel_detail3, null, false);
//        setContentView(mTouchPanelLayout);
        mTouchPanelLayout.setConfigCurrentPagerScroll(this);
        mTouchPanelLayout.setOnViewUpdateListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_inner_travel_tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager_inner_travel_content);

        viewPager.setOffscreenPageLimit(4);
        mContent.add(new Tab2BottomContentFragment());
        mContent.add(new Tab2BottomContentFragment());

        mContent.add(new Tab2BottomContentFragment());
        pagerAdapter = new OrdersPagerAdapter(getSupportFragmentManager(), mTitles, mContent);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public IPagerScroll getCurrentPagerScroll() {
        Fragment fragment = pagerAdapter.getCurrentFragment(viewPager.getCurrentItem());
        if (fragment != null && fragment instanceof IPagerScroll) {
            return (IPagerScroll) fragment;
        }
        return null;
    }

    @Override
    public float getActionBarHeight() {
        return 0;
    }

    @Override
    public void onAlphaChanged(int alpha) {

    }

    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, InnerTravelDetailActivity3.class);
        if(data !=null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
