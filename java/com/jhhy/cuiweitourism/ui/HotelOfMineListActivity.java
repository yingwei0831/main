package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelPagerAdapter;
import com.jhhy.cuiweitourism.biz.ScreenBiz;
import com.jhhy.cuiweitourism.fragment.InnerTravelCityFollowFragment;
import com.jhhy.cuiweitourism.fragment.InnerTravelCityFreedomFragment;
import com.jhhy.cuiweitourism.moudle.PriceArea;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowSearchLine;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelOfMineListActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = HotelOfMineListActivity.class.getSimpleName();
    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private final List<String> titles = new ArrayList<>(Arrays.asList("跟团游", "自由行"));
    private TabLayout indicatorInnerTravel;
    private ViewPager viewPager;
    private InnerTravelPagerAdapter mAdapter;

    private List<Fragment> mContent = new ArrayList<>();
    private InnerTravelCityFollowFragment followFragment;
    private InnerTravelCityFreedomFragment freedomFragment;

    //    private int tag;

    private String cityId; //城市id，可以确定国内、出境
    private String cityName;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hotel_mine);
//        getData();
        getInternetData();
        setupView();
        addListener();
        initDatas();
    }

    private void getInternetData() {

    }

    private void getData() {
//        Bundle bundle = getIntent().getExtras();
//        cityId = bundle.getString("cityId");
//        cityName = bundle.getString("cityName");
    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText(cityName);
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        View layout = findViewById(R.id.activity_inner_travel);
        indicatorInnerTravel = (TabLayout) findViewById(R.id.activity_inner_travel_indicator_top);
        viewPager = (ViewPager) findViewById(R.id.activity_inner_travel_viewpager);

    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);


    }

    private void initDatas() {
        //设置TabLayout的模式
        indicatorInnerTravel.setTabMode(TabLayout.MODE_FIXED);
        indicatorInnerTravel.setTabGravity(TabLayout.GRAVITY_CENTER);

        mAdapter = new InnerTravelPagerAdapter(getApplicationContext(), this.getSupportFragmentManager(), mContent, titles);

        followFragment = InnerTravelCityFollowFragment.newInstance(titles.get(0), cityId);
        freedomFragment = InnerTravelCityFreedomFragment.newInstance(titles.get(1), cityId);

        mContent.add(followFragment);
        mContent.add(freedomFragment);

        viewPager.setAdapter(mAdapter);
        //TabLayout加载viewpager
        indicatorInnerTravel.setupWithViewPager(viewPager);
        //自定义TabLayout 布局
        for (int i = 0; i < indicatorInnerTravel.getTabCount(); i++) {
            TabLayout.Tab tab = indicatorInnerTravel.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mAdapter.getTabView(i));
            }
        }
        indicatorInnerTravel.getTabAt(0).getCustomView().setSelected(true);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_CANCELED) {

        } else {

        }
    }


    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, HotelOfMineListActivity.class);
        if (data != null) {
            intent.putExtras(data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}