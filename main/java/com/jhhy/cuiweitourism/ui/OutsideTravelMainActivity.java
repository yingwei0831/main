package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotDestinationGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

public class OutsideTravelMainActivity extends BaseActivity implements XScrollView.IXScrollViewListener, GestureDetector.OnGestureListener, View.OnClickListener, ArgumentOnClick {

    private MyGridView gvHotDestination; //热门目的地
    private HotDestinationGridViewAdapter gvHotDestAdapter;
    private List<String> citiesHotDest;

    private MyGridView gvHotRecommend; //热门推荐
    private HotRecommendGridViewAdapter gvHotRecomAdapter;
    private List<CityRecommend> citiesHotRecom;


    private List<String> titles = new ArrayList<>();


    private GestureDetector mGestureDetector; // mScrollView的手势
    private LinearLayout layoutIndicatorTop; //顶部悬浮导航栏
    private RelativeLayout layoutTitle; //顶部Title
    private XScrollView mScrollView;
    private View content;
    private LinearLayout layoutIndicator;//底部GridView的导航栏(跟团游，自由游)
    private TextView tvFollow; //跟团游导航按钮
    private TextView tvFreedom; //自由游导航按钮
    private View viewFollow; //绿色条
    private View viewFreedom; //绿色条
    private MyGridView gridViewFollow; //底部Gridview
    private Tab1GridViewAdapter gvAdapter;
    private List<Travel> listsFollow = new ArrayList<>();
    private List<Travel> listsFreedom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_travel_main);
        setupView();
        addListener();
        initData();
    }


    private void setupView() {
        for(int i = 0; i < 11; i++){
            Travel travel = new Travel();
            travel.setTravelPrice("2258");
            listsFollow.add(travel);
        }
        for(int i = 0; i < 11; i++){
            Travel travel = new Travel();
            travel.setTravelPrice("8256");
            listsFreedom.add(travel);
        }

        layoutTitle = (RelativeLayout) findViewById(R.id.layout_inner_travel_main_title);
        layoutIndicatorTop = (LinearLayout) findViewById(R.id.layout_inner_travel_main_indicator_top);

        mScrollView = (XScrollView)findViewById(R.id.scroll_view);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_inner_travel_main_content, null);
        if (null != content) {
            gvHotDestination = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_hot); //热门目的地

            gvHotRecommend = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_recommend); //热门推荐

            layoutIndicator = (LinearLayout) content.findViewById(R.id.layout_inner_travel_main_indicator); //底部GridView的导航栏
            tvFollow = (TextView) content.findViewById(R.id.tv_inner_travel_main_follow);
            tvFreedom = (TextView) content.findViewById(R.id.tv_inner_travel_main_freedom);
            viewFollow = content.findViewById(R.id.indicator_follow);
            viewFreedom = content.findViewById(R.id.indicator_freedom);

            gridViewFollow = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_content); //底部GridView
            gridViewFollow.setFocusable(false);
            gridViewFollow.setFocusableInTouchMode(false);

            gvAdapter = new Tab1GridViewAdapter(getApplicationContext(), listsFollow, this);
            gridViewFollow.setAdapter(gvAdapter);

            mGestureDetector = new GestureDetector(getApplicationContext(), this);
        }
        mScrollView.setView(content);

        /**
         * 此处是为了顶部导航栏的显示和隐藏
         */
        mScrollView.setOnXScrollChangedI(new XScrollView.onXScrollChangedI() {
            @Override
            public void onXScrollChangedImpl(int l, int t, int oldl, int oldt) {
                int[] s = new int[2];
                layoutIndicator.getLocationOnScreen(s);
                int statusHeight = Utils.getStatusBarHeight(getApplicationContext());
                int titleHeight = layoutTitle.getHeight();
                if(statusHeight + titleHeight >= s[1]){
                    layoutIndicatorTop.setVisibility(View.VISIBLE);
                }else{
                    layoutIndicatorTop.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_inner_travel_main_follow: //跟团游
                gvAdapter.setData(listsFollow);
                tvFollow.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewFollow.setVisibility(View.VISIBLE);
                tvFreedom.setTextColor(getResources().getColor(android.R.color.black));
                viewFreedom.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_inner_travel_main_freedom: //国内游
                gvAdapter.setData(listsFreedom);
                tvFreedom.setTextColor(getResources().getColor(R.color.colorTab1RecommendForYouArgument));
                viewFreedom.setVisibility(View.VISIBLE);
                tvFollow.setTextColor(getResources().getColor(android.R.color.black));
                viewFollow.setVisibility(View.INVISIBLE);
                break;

        }
    }



    private void addListener() {
        gvHotDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickHotDestinationItem(adapterView, view, i, l);
            }
        });

        tvFollow.setOnClickListener(this);
        tvFreedom.setOnClickListener(this);
    }

    private void initData() {
        titles.add("跟团游");
        titles.add("自由游");

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

    }

    /**
     * 热门目的地推荐回调
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    private void clickHotDestinationItem(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString(Consts.KEY_INNER_CITY_NAME, citiesHotDest.get(i));
        InnerTravelCityActivity.actionStart(getApplicationContext(), bundle);
    }

    /**
     * 下拉刷新回调
     */
    @Override
    public void onRefresh() {

    }

    /**
     * 上拉加载回调
     */
    @Override
    public void onLoadMore() {

    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, OutsideTravelMainActivity.class);
        if(bundle !=null){
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @param motionEvent1
     * @param v
     * @param v1
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * GestureDetector.OnGestureListener 回调
     * @param motionEvent
     * @param motionEvent1
     * @param v
     * @param v1
     * @return
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        //讨价还价
    }
}
