package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ISlideCallback;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotDestinationAdapter;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.biz.ExchangeBiz;
import com.jhhy.cuiweitourism.biz.InnerTravelMainBiz;
import com.jhhy.cuiweitourism.fragment.InnerTravelFollowFragment;
import com.jhhy.cuiweitourism.fragment.InnerTravelFreeFragment;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.moudle.HotDestination;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;

public class InnerActivity4 extends AppCompatActivity  implements ISlideCallback, XScrollView.IXScrollViewListener, SlideDetailsLayout.IConfigCurrentPagerScrollSlide {

    private String TAG = InnerActivity4.class.getSimpleName();
    private SlideDetailsLayout mSlideDetailsLayout;

    private int type; // 1：国内游，2：出境游

    private XScrollView xScrollView;
    private View content;

    private MyGridView gvHotDestination; //热门目的地
    private List<HotDestination> listHotDestination = new ArrayList<>();
    private HotDestinationAdapter hotDestAdapter;

    private TextView tvHotRecommendExchange; //热门推荐，换一批
    private MyGridView gvHotRecommend; //热门推荐
    private List<CityRecommend> listHotRecommend = new ArrayList<>();
    private HotRecommendGridViewAdapter hotRecomAdapter;

    private String[] mTitles = new String[]{"跟团游", "自由游"};
    private TabLayout tabIndicator; //跟团游，自由游
    private ViewPager viewPager;
    private List<Fragment> mContent = new ArrayList<>();
    private OrdersPagerAdapter pagerAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_INNER_TRAVEL_HOT_DESTINATION:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                    }else{
                        List<HotDestination> listDest = (List<HotDestination>) msg.obj;
                        if (listDest == null || listDest.size() == 0){
                            ToastUtil.show(getApplicationContext(), "没有热门目的地");
                        }else{
                            listHotDestination = listDest;
                            hotDestAdapter.setData(listHotDestination);
                        }

                    }
                    break;
                case Consts.MESSAGE_EXCHANGE:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                    }else{
                        List<CityRecommend> listDest = (List<CityRecommend>) msg.obj;
                        if (listDest == null || listDest.size() == 0){
                            ToastUtil.show(getApplicationContext(), "没有热门推荐");
                        }else{
                            listHotRecommend = listDest;
                            hotRecomAdapter.setData(listHotRecommend);
                        }

                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        ActionBar bar =  getSupportActionBar();
        //自定义一个布局，并居中
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        setContentView(R.layout.activity_inner4);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");

        mSlideDetailsLayout = (SlideDetailsLayout) findViewById(R.id.slidedetails);
        mSlideDetailsLayout.setConfigCurrentPagerScroll(this);
        setupView();
        addListener();
        getData();
    }

    private void addListener() {
        gvHotDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HotDestination item = listHotDestination.get(position);
                String cityId = item.getCityId();
                Bundle bundle = new Bundle();
                bundle.putString("cityId", cityId);
                InnerTravelCityActivity.actionStart(getApplicationContext(), bundle);
            }
        });
    }

    private void getData() {
        InnerTravelMainBiz biz = new InnerTravelMainBiz(getApplicationContext(), handler, Consts.MESSAGE_INNER_TRAVEL_HOT_DESTINATION);
        biz.getHotDestination("1");

        ExchangeBiz bizE = new ExchangeBiz(getApplicationContext(), handler);
        bizE.getHotRecommend();

    }

    private void setupView() {
        xScrollView = (XScrollView)findViewById(R.id.slidedetails_front);
        xScrollView.setPullRefreshEnable(true);
        xScrollView.setPullLoadEnable(false);
        xScrollView.setAutoLoadEnable(false);
        xScrollView.setIXScrollViewListener(this);
        xScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_inner_travel_top, null);

        if (content != null){
            gvHotDestination = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_hot);
            tvHotRecommendExchange = (TextView) content.findViewById(R.id.tv_hot_recommend_exchange);
            gvHotRecommend = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_recommend);

//            gvHotDestination.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listHotDestination));
//            gvHotRecommend.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listHotRecommend));
            hotDestAdapter = new HotDestinationAdapter(getApplicationContext(), listHotDestination);
            gvHotDestination.setAdapter(hotDestAdapter);

            hotRecomAdapter = new HotRecommendGridViewAdapter(getApplicationContext(), listHotRecommend);
            gvHotRecommend.setAdapter(hotRecomAdapter);
        }
        xScrollView.setView(content);

        tabIndicator = (TabLayout) findViewById(R.id.tab_tab2_indicator);
        //设置TabLayout的模式
        tabIndicator.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[0]));
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[1]));
        viewPager = (ViewPager) findViewById(R.id.viewpager_tab2_bottom);
        viewPager.setOffscreenPageLimit(2);

        InnerTravelFollowFragment follow = InnerTravelFollowFragment.newInstance(mTitles[0], type);
        mContent.add(follow);
        InnerTravelFreeFragment free = InnerTravelFreeFragment.newInstance(mTitles[1], type);
        mContent.add(free);

        pagerAdapter = new OrdersPagerAdapter(getSupportFragmentManager(), mTitles, mContent);
        viewPager.setAdapter(pagerAdapter);
        tabIndicator.setupWithViewPager(viewPager);

    }

    @Override
    public void openDetails(boolean smooth) {
        mSlideDetailsLayout.smoothOpen(smooth);
    }

    @Override
    public void closeDetails(boolean smooth) {
        mSlideDetailsLayout.smoothClose(smooth);
    }

    @Override
    public void onRefresh() {
        xScrollView.stopRefresh();
    }

    @Override
    public void onLoadMore() {

    }


    @Override
    public SlideDetailsLayout.IPagerScrollSlide getCurrentPagerScroll() {
        Fragment fragment = pagerAdapter.getCurrentFragment(viewPager.getCurrentItem());
        if (fragment != null && fragment instanceof SlideDetailsLayout.IPagerScrollSlide) {
            return (SlideDetailsLayout.IPagerScrollSlide) fragment;
        }
        return null;
    }

    @Override
    public float getActionBarHeight() {
        return 0;
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, InnerActivity4.class);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}
