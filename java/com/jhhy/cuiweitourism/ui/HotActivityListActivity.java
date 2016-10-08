package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotActivityListViewAdapter;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.popupwindows.InnerTravelPopupWindow;

import java.util.ArrayList;

public class HotActivityListActivity extends BaseActivity implements View.OnClickListener {

//    private final List<String> titles = Arrays.asList("跟团游", "自由游");
//    private TabLayout indicatorInnerTravel;
//    private ViewPager viewPager;
//    private FragmentPagerAdapter mAdapter;

//    private List<Fragment> mContent = new ArrayList<>();

//    private ArrayList<Travel> listFollow;
    private ArrayList<Travel> listFreedom;

    private PullToRefreshListView pullToRefreshListView;
    private HotActivityListViewAdapter adapter;

    private View layout;
    private TextView tvSortDefault;
    private TextView tvSortDays;
    private TextView tvStartTime;
    private TextView tvScreenPrice;

    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_activity_list);
        setupView();
        addListener();
        initDatas();
    }

    private void setupView() {
        layout = findViewById(R.id.activity_hot_activity_list);
//        indicatorInnerTravel = (TabLayout) findViewById(R.id.activity_inner_travel_indicator_top);
//        viewPager = (ViewPager) findViewById(R.id.activity_inner_travel_viewpager);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.activity_hot_activity_list_view);
        //这几个刷新Label的设置
        pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //上拉、下拉监听函数
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        tvSortDefault = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_sort_default);
        tvSortDays = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_trip_days);
        tvStartTime = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_start_time);
        tvScreenPrice = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_screen_price);
    }

    private void addListener() {
        tvSortDefault.setOnClickListener(this);
        tvSortDays.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvScreenPrice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1_hot_activity_list_sort_default:
                tag = 1;
//                new InnerTravelPopupWindow(this, layout, tag, );
                break;
            case R.id.tv_tab1_hot_activity_list_trip_days:
                tag = 2;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_tab1_hot_activity_list_start_time:
                tag = 3;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_tab1_hot_activity_list_screen_price:
                tag = 4;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;

        }
    }

    private void initDatas() {
        //设置TabLayout的模式
//        indicatorInnerTravel.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
//        indicatorInnerTravel.addTab(indicatorInnerTravel.newTab().setText(titles.get(0)));
//        indicatorInnerTravel.addTab(indicatorInnerTravel.newTab().setText(titles.get(1)));
//        mAdapter = new InnerTravelPagerAdapter(this.getSupportFragmentManager(), mContent, titles);

//        listFollow = new ArrayList<>();
//        for(int i = 0; i < 20; i++){
//            Travel travel = new Travel();
//            travel.setTravelTitle(getString(R.string.tab1_recommend_for_you_title));
//            travel.setTravelPrice("12100");
//            listFollow.add(travel);
//        }

        listFreedom = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            Travel travel = new Travel();
            travel.setTravelTitle(getString(R.string.tab1_recommend_for_you_title));
            travel.setTravelPrice("62598");
            travel.setAccount(i);
            listFreedom.add(travel);
        }
        adapter = new HotActivityListViewAdapter(getApplicationContext(), listFreedom);
        pullToRefreshListView.setAdapter(adapter);
//        for (int i = 0; i < titles.size(); i++) {
//            if (titles.get(i).equals("跟团游")) {
//                followTravelFragment = InnerTravelCityFollowFragment.newInstance(titles.get(i));
//                followTravelFragment.setData(listFollow);
//                mContent.add(followTravelFragment);
//            } else if (titles.get(i).equals("自由游")) {
//                freedomTravleFragment = InnerTravelCityFollowFragment.newInstance(titles.get(i));
//                freedomTravleFragment.setData(listFreedom);
//                mContent.add(freedomTravleFragment);
//            }
//        }

//        viewPager.setAdapter(mAdapter);
        //TabLayout加载viewpager
//        indicatorInnerTravel.setupWithViewPager(viewPager);
    }


    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, HotActivityListActivity.class);
        if(data !=null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
