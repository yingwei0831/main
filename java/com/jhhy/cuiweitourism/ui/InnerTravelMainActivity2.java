package com.jhhy.cuiweitourism.ui;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotDestinationAdapter;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.biz.ExchangeBiz;
import com.jhhy.cuiweitourism.biz.InnerTravelMainBiz;
import com.jhhy.cuiweitourism.fragment.InnerTravelFollowFragment;
import com.jhhy.cuiweitourism.fragment.InnerTravelFreeFragment;
import com.jhhy.cuiweitourism.model.CityRecommend;
import com.jhhy.cuiweitourism.model.HotDestination;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.markmao.pulltorefresh.widght.XScrollView;
import com.ns.fhvp.TouchPanelLayoutModify;

import java.util.ArrayList;
import java.util.List;


public class InnerTravelMainActivity2 extends BaseActivity implements XScrollView.IXScrollViewListener, View.OnClickListener {

    private String TAG = InnerTravelMainActivity2.class.getSimpleName();

    private TouchPanelLayoutModify mTouchPanelLayout;

    private XScrollView xScrollView;
    private View content;

    private MyGridView gvHotDestination; //热门目的地
    private List<HotDestination> listHotDestination = new ArrayList<>();
    private HotDestinationAdapter hotDestAdapter;

    private TextView tvHotRecommendExchange; //热门推荐，换一批
    private MyGridView gvHotRecommend; //热门推荐
    private List<CityRecommend> listHotRecommend = new ArrayList<>();
    private HotRecommendGridViewAdapter hotRecomAdapter;

    private String[] mTitles = new String[]{"跟团游", "自由行"};
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
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_inner_travel_main2);
            mTouchPanelLayout = (TouchPanelLayoutModify) findViewById(R.id.slidedetails);

            setupView();
            addListener();
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getData() {
        InnerTravelMainBiz biz = new InnerTravelMainBiz(getApplicationContext(), handler, Consts.MESSAGE_INNER_TRAVEL_HOT_DESTINATION);
        biz.getHotDestination("1");

        ExchangeBiz bizE = new ExchangeBiz(getApplicationContext(), handler);
        bizE.getHotRecommend("");
    }

    private void setupView() {
        xScrollView = (XScrollView)findViewById(R.id.fhvp_header);
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
        mContent.add(new InnerTravelFollowFragment());
        mContent.add(new InnerTravelFreeFragment());
        pagerAdapter = new OrdersPagerAdapter(getSupportFragmentManager(), mTitles, mContent);
        viewPager.setAdapter(pagerAdapter);
        tabIndicator.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mTouchPanelLayout.setMaxTransY(content.getMeasuredHeight());
    }

    private void addListener() {
        tvHotRecommendExchange.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        xScrollView.stopRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View view) {
        //换一批
    }
}
