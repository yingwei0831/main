package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotDestinationGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.InnerTravelMainPagerAdapter;
import com.jhhy.cuiweitourism.fragment.InnerTravelFollowFragment;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class InnerTravelMainActivity3 extends BaseActivity {

    private MyGridView gvHotDestination; //热门目的地
    private HotDestinationGridViewAdapter gvHotDestAdapter;
    private List<String> citiesHotDest;

    private MyGridView gvHotRecommend; //热门推荐
    private HotRecommendGridViewAdapter gvHotRecomAdapter;
    private List<CityRecommend> citiesHotRecom;

//    private TabLayout tabs;
//    private ViewPager viewPager;
//    private InnerTravelMainPagerAdapter mPagerAdapter;
//    private List<String> titles = new ArrayList<>();
//    private List<Fragment> fragments = new ArrayList<>();

//    private InnerTravelFollowFragment fragment1;
//    private InnerTravelFollowFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_travel_main_3);
        setupView();
        initData();
    }



    private void setupView() {
//        tabs = (TabLayout) findViewById(R.id.tabs_inner_travel_main);
//        viewPager = (ViewPager) findViewById(R.id.viewpager_inner_travel_main);

        gvHotDestination = (MyGridView) findViewById(R.id.gv_inner_travel_main_hot);

        gvHotRecommend = (MyGridView) findViewById(R.id.gv_inner_travel_main_recommend);
    }


    private void initData() {
//        titles.add("跟团游");
//        titles.add("自由游");

//        fragment1 = InnerTravelFollowFragment.newInstance(titles.get(0));
//        fragment2 = InnerTravelFollowFragment.newInstance(titles.get(1));
//        fragments.add(fragment1);
//        fragments.add(fragment2);

        citiesHotDest = new ArrayList<>();
        citiesHotDest.add("北京");
        citiesHotDest.add("南京");
        citiesHotDest.add("西安");
        citiesHotDest.add("海南");
        citiesHotDest.add("云南");
        citiesHotDest.add("深圳");
        citiesHotDest.add("厦门");
        citiesHotDest.add("香港");
        citiesHotDest.add("上海");
        citiesHotDest.add("杭州");
        gvHotDestAdapter = new HotDestinationGridViewAdapter(this, citiesHotDest);
        gvHotDestination.setAdapter(gvHotDestAdapter);


        citiesHotRecom = new ArrayList<>();
        for(int i = 0; i < 2; i ++){
            CityRecommend city = new CityRecommend();
            city.setCityName("深圳" + i);
            citiesHotRecom.add(city);
        }
        gvHotRecomAdapter = new HotRecommendGridViewAdapter(getApplicationContext(), citiesHotRecom);
        gvHotRecommend.setAdapter(gvHotRecomAdapter);


        //设置TabLayout的模式
//        tabs.setTabMode(TabLayout.MODE_FIXED);
//        //为TabLayout添加tab名称
//        tabs.addTab(tabs.newTab().setText(titles.get(0)));
//        tabs.addTab(tabs.newTab().setText(titles.get(1)));
//
//        mPagerAdapter = new InnerTravelMainPagerAdapter(getSupportFragmentManager(), titles, fragments);
//        viewPager.setAdapter(mPagerAdapter);
//        tabs.setupWithViewPager(viewPager); //TabLayout加载viewpager,setupWithViePager必须在ViewPager.setAdapter()之后调用

    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, InnerTravelMainActivity3.class);
        if(bundle !=null){
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
