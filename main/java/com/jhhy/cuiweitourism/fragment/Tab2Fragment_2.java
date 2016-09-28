package com.jhhy.cuiweitourism.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.biz.ExchangeBiz;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.ui.InnerTravelCityActivity;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.TouchPanelLayoutModify;

import java.util.ArrayList;
import java.util.List;


public class Tab2Fragment_2 extends Fragment implements TouchPanelLayoutModify.IConfigCurrentPagerScroll, TouchPanelLayoutModify.OnViewUpdateListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;


    private TextView tvHotRecommendNext; //换一批
    private MyGridView gridViewHotRecommend;
    private HotRecommendGridViewAdapter hotAdapter;
    private List<CityRecommend> listHotRecommend = new ArrayList<>();

    private String[] mTitles = new String[]{"国内游", "出境游", "周边游"};
    private List<Fragment> mContent = new ArrayList<>();
    private TabLayout tabIndicator;
    private ViewPager viewPager;
    private OrdersPagerAdapter pagerAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_EXCHANGE:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getContext(), (String) msg.obj);
                    }else{
                        List<CityRecommend> listDest = (List<CityRecommend>) msg.obj;
                        if (listDest == null || listDest.size() == 0){
                            ToastUtil.show(getContext(), "没有热门推荐");
                        }else{
                            listHotRecommend = listDest;
                            hotAdapter.setData(listHotRecommend);
                        }

                    }
                    break;
            }
        }
    };

    public Tab2Fragment_2() {

    }


    public static Tab2Fragment_2 newInstance(String param1, String param2) {
        Tab2Fragment_2 fragment = new Tab2Fragment_2();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mTouchPanelLayout = inflater.inflate(R.layout.fragment_tab2_2, container, false);
        ((TouchPanelLayoutModify)mTouchPanelLayout).setConfigCurrentPagerScroll(this);
        ((TouchPanelLayoutModify)mTouchPanelLayout).setOnViewUpdateListener(this);

        setupView(mTouchPanelLayout);
        addListener();
        return mTouchPanelLayout;
    }

    private void setupView(View view) {
        getRecommend();
        tvHotRecommendNext = (TextView) view.findViewById(R.id.tv_tab2_hot_recommend_next);
        gridViewHotRecommend = (MyGridView) view.findViewById(R.id.gv_tab2_top_hot_recommend);
        for(int i = 0; i < 2; i++){
            CityRecommend cityRecommend = new CityRecommend();
            cityRecommend.setCityName("巴厘岛 "+i);
            listHotRecommend.add(cityRecommend);
        }
        hotAdapter = new HotRecommendGridViewAdapter(getContext(), listHotRecommend);
        gridViewHotRecommend.setAdapter(hotAdapter);


        tabIndicator = (TabLayout) view.findViewById(R.id.tab_tab2_indicator);
        //设置TabLayout的模式
        tabIndicator.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[0]));
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[1]));
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[2]));

        viewPager = (ViewPager) view.findViewById(R.id.viewpager_tab2_bottom);
        viewPager.setOffscreenPageLimit(3);
        mContent.add(new Tab2BottomContentFragment());
        mContent.add(new Tab2BottomContentFragment2());
        mContent.add(new Tab2BottomContentFragment3());
        pagerAdapter = new OrdersPagerAdapter(getChildFragmentManager(), mTitles, mContent);
        viewPager.setAdapter(pagerAdapter);
        tabIndicator.setupWithViewPager(viewPager);
    }

    private void addListener() {
        tvHotRecommendNext.setOnClickListener(this);
        gridViewHotRecommend.setOnItemClickListener(this);
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
        return 0; //getActionBar().getHeight();
    }

    @Override
    public void onAlphaChanged(int alpha) {
//        if (mAbBg != null) {
//            mAbBg.setAlpha(alpha);
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab2_hot_recommend_next: //热门推荐，缓一缓
                getRecommend();
                break;
        }
    }

    private void getRecommend(){
        ExchangeBiz biz = new ExchangeBiz(getContext(), handler);
        biz.getHotRecommend();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //热门推荐，点击
        Intent intent = new Intent(getContext(), InnerTravelCityActivity.class); //选择的城市旅游列表
        Bundle bundle = new Bundle();
        bundle.putString("cityId", listHotRecommend.get(i).getCityId());
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_VIEW_LIST);
    }

    private int REQUEST_CODE_VIEW_LIST = 2101;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VIEW_LIST){
            if (resultCode == Activity.RESULT_OK){

            }
        }
    }
}
