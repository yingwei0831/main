package com.jhhy.cuiweitourism.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.ui.HotActivityListActivity;
import com.jhhy.cuiweitourism.ui.InnerTravelMainActivity;
import com.jhhy.cuiweitourism.ui.PersonalizedCustomActivity;
import com.jhhy.cuiweitourism.ui.SearchRouteActivity;
import com.jhhy.cuiweitourism.ui.StartActivityEditActivity;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.TouchPanelLayoutModify;

import java.util.ArrayList;
import java.util.List;


public class Tab1Fragment_3 extends Fragment implements TouchPanelLayoutModify.IConfigCurrentPagerScroll, TouchPanelLayoutModify.OnViewUpdateListener, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private PullToRefreshScrollView scrollView;
    private TextView tvInnerTravel; //国内游
    private TextView tvStartActivity; //发起活动

    private TextView tvSearchRoute; //找路线
    private RelativeLayout layoutPersionalizedCustom; //热门活动
    private RelativeLayout layoutHotActivity; //热门活动

    private String[] mTitles = new String[]{"全部", "国内游", "出境游", "周边游"};
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private OrdersPagerAdapter pagerAdapter;
    private List<Fragment> mContent = new ArrayList<>();

    public Tab1Fragment_3() {
        // Required empty public constructor
    }


    public static Tab1Fragment_3 newInstance(String param1, String param2) {
        Tab1Fragment_3 fragment = new Tab1Fragment_3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mTouchPanelLayout = inflater.inflate(R.layout.fragment_tab1_fragment_3, container, false);
        ((TouchPanelLayoutModify)mTouchPanelLayout).setConfigCurrentPagerScroll(this);
        ((TouchPanelLayoutModify)mTouchPanelLayout).setOnViewUpdateListener(this);

        setupView(mTouchPanelLayout);
        addListener();
        return mTouchPanelLayout;
    }

    private void setupView(View view) {
        scrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_to_refresh_scrollview_top);
        scrollView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        scrollView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        scrollView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        scrollView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        tvInnerTravel = (TextView) view.findViewById(R.id.tv_tab1_inner_travel); //国内游
        tvStartActivity = (TextView) view.findViewById(R.id.tv_tab1_start_activity); //发起活动
        tvSearchRoute = (TextView) view.findViewById(R.id.tv_tab1_search_route_activity); //找路线
        layoutPersionalizedCustom = (RelativeLayout) view.findViewById(R.id.layout_personalized_custom); //个性定制
        layoutHotActivity = (RelativeLayout) view.findViewById(R.id.layout_hot_activity); //热门活动

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_tab1_bottom);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager_tab1_bottom);

        viewPager.setOffscreenPageLimit(3);
        mContent.add(new Tab1BottomContentFragment());
        mContent.add(new Tab1BottomContentFragment());
        mContent.add(new Tab1BottomContentFragment());
        pagerAdapter = new OrdersPagerAdapter(getChildFragmentManager(), mTitles, mContent);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void addListener() {
        tvInnerTravel.setOnClickListener(this);
        tvStartActivity.setOnClickListener(this);
        layoutPersionalizedCustom.setOnClickListener(this);
        layoutHotActivity.setOnClickListener(this);
        tvSearchRoute.setOnClickListener(this);

        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                PullToRefreshBase.Mode x = scrollView.getCurrentMode();
                if(PullToRefreshBase.Mode.PULL_FROM_END.equals(x)){ //上拉加载

                }else if(PullToRefreshBase.Mode.PULL_FROM_START.equals(x)){ //下拉刷新

                }
                scrollView.onRefreshComplete();
                //执行刷新函数
//                new GetDataTask().execute();
            }
        });
    }


    @Override
    public IPagerScroll getCurrentPagerScroll() {
        return null;
    }

    @Override
    public float getActionBarHeight() {
        return 0;
    }

    @Override
    public void onAlphaChanged(int alpha) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1_inner_travel: //国内游
                InnerTravelMainActivity.actionStart(getContext(), null);
//                InnerTravelMainActivity3 b;
                break;
            case R.id.tv_tab1_start_activity: //发起活动
                StartActivityEditActivity.actionStart(getContext(), null);
                break;
            case R.id.layout_personalized_custom: //个性定制
                PersonalizedCustomActivity.actionStart(getContext(), null);
                break;
            case R.id.layout_hot_activity: //热门活动
                HotActivityListActivity.actionStart(getContext(), null);
                break;
            case R.id.tv_tab1_search_route_activity: //找路线
                SearchRouteActivity.actionStart(getContext(), null);
                break;
        }
    }
}
