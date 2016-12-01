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
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.ISlideCallback;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotDestinationAdapter;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.biz.ExchangeBiz;
import com.jhhy.cuiweitourism.biz.InnerTravelMainBiz;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.fragment.InnerTravelFollowFragment;
import com.jhhy.cuiweitourism.fragment.InnerTravelFreeFragment;
import com.jhhy.cuiweitourism.moudle.ADInfo;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.moudle.HotDestination;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;

public class InnerActivity4 extends AppCompatActivity  implements ISlideCallback, XScrollView.IXScrollViewListener, SlideDetailsLayout.IConfigCurrentPagerScrollSlide, View.OnTouchListener, GestureDetector.OnGestureListener {

    private String TAG = InnerActivity4.class.getSimpleName();
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private SlideDetailsLayout mSlideDetailsLayout;

    private int type; // 1：国内游，2：出境游

    private XScrollView xScrollView;
    private GestureDetector mGestureDetector; // MyScrollView的手势?

    //顶部图片展示
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private ViewFlipper flipper;
    private LinearLayout layoutPoint;
    private List<String> imageUrls = new ArrayList<>();
    private ImageView[] indicators; // 轮播图片数组
    private int currentPosition = 0; // 轮播当前位置

    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 0;

    private final int WHEEL = 100; // 转动
    private final int WHEEL_WAIT = 101; // 等待
    private boolean isScrolling = false; // 滚动框是否滚动着
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换

    private View content; //XScrollView内容

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
                case WHEEL:
                    if(flipper.getChildCount() != 0){
                        if(!isScrolling){
                            //向前滑向后滑
                            showNextView();
                        }
                    }
                    releaseTime = System.currentTimeMillis();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, Consts.TIME_PERIOD);
                    break;
                case WHEEL_WAIT:
                    if(flipper.getChildCount() != 0){
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable, Consts.TIME_PERIOD);
                    }
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    break;
                default:
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!InnerActivity4.this.isFinishing()) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > Consts.TIME_PERIOD - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
//        ActionBar bar =  getSupportActionBar();
//        //自定义一个布局，并居中
//        bar.setDisplayShowCustomEnabled(true);
//        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
//        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影

        setContentView(R.layout.activity_inner4);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");

        mSlideDetailsLayout = (SlideDetailsLayout) findViewById(R.id.slidedetails);
        mSlideDetailsLayout.setConfigCurrentPagerScroll(this);
        getInternetData();
        setupView();
        addListener();
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //轮播全部取消
        handler.removeCallbacks(runnable);
        runnable = null;
        handler = null;
    }

    private void getInternetData() {
        imageUrls.add("drawable://" + R.drawable.ic_empty);
        //广告位
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
//        mark:index（首页）、line_index(国内游、出境游)、header（分类上方）、visa_index（签证）、customize_index(个性定制)
        ForeEndAdvertise ad = new ForeEndAdvertise("line_index");
        fbiz.foreEndGetAdvertisingPosition(ad, new BizGenericCallback<ArrayList<ForeEndAdvertisingPositionInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ForeEndAdvertisingPositionInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<ForeEndAdvertisingPositionInfo> array = model.body;
                    LogUtil.e(TAG,"foreEndGetAdvertisingPosition =" + array.toString());
                    refreshViewBanner(array);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取广告位数据失败");
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取广告位数据出错");
                }
                LogUtil.e(TAG, "foreEndGetAdvertisingPosition: " + error.toString());
            }
        });
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gvHotDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //热门目的地详情
                HotDestination item = listHotDestination.get(position);
                String cityId = item.getCityId();
                Bundle bundle = new Bundle();
                bundle.putString("cityId", cityId);
                bundle.putString("cityName", item.getCityName());
                InnerTravelCityActivity.actionStart(getApplicationContext(), bundle);
            }
        });
        gvHotRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //热门推荐详情，没有UI设计，此处不正确
//                CityRecommend item = listHotRecommend.get(i);
//                Bundle bundle = new Bundle();
//                bundle.putString("id", item.getCityId());
//                InnerTravelDetailActivity.actionStart(getApplicationContext(), bundle);
            }
        });
    }

    private void getData() {
        InnerTravelMainBiz biz = new InnerTravelMainBiz(getApplicationContext(), handler, Consts.MESSAGE_INNER_TRAVEL_HOT_DESTINATION);
        biz.getHotDestination(String.valueOf(type));

        ExchangeBiz bizE = new ExchangeBiz(getApplicationContext(), handler);
        bizE.getHotRecommend(String.valueOf(type));
    }

    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        if (type == 1){
            tvTitle.setText("国内游");
        }else if(type == 2){
            tvTitle.setText("出境游");
        }

        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        xScrollView = (XScrollView)findViewById(R.id.slidedetails_front);
        xScrollView.setPullRefreshEnable(true);
        xScrollView.setPullLoadEnable(false);
        xScrollView.setAutoLoadEnable(false);
        xScrollView.setIXScrollViewListener(this);
        xScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_inner_travel_top, null);

        if (content != null){
            View ivSearchLeft = content.findViewById(R.id.iv_title_search_left);
            ivSearchLeft.setVisibility(View.GONE);
            EditText etSearchText = (EditText) content.findViewById(R.id.edit_search);
            etSearchText.setHint("输入你想去的地方");

            gvHotDestination = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_hot);
            tvHotRecommendExchange = (TextView) content.findViewById(R.id.tv_hot_recommend_exchange);
            gvHotRecommend = (MyGridView) content.findViewById(R.id.gv_inner_travel_main_recommend);

//            gvHotDestination.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listHotDestination));
//            gvHotRecommend.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listHotRecommend));
            hotDestAdapter = new HotDestinationAdapter(getApplicationContext(), listHotDestination);
            gvHotDestination.setAdapter(hotDestAdapter);

            hotRecomAdapter = new HotRecommendGridViewAdapter(getApplicationContext(), listHotRecommend);
            gvHotRecommend.setAdapter(hotRecomAdapter);

            mGestureDetector = new GestureDetector(getApplicationContext(), this);

            flipper = (ViewFlipper)content.findViewById(R.id.viewflipper);
            layoutPoint =(LinearLayout)content.findViewById(R.id.layout_indicator_point);

            addImageView(imageUrls.size());
            addIndicator(imageUrls.size());
            setIndicator(currentPosition);

            flipper.setOnTouchListener(this);

            dianSelect(currentPosition);
            MyScrollView myScrollView = (MyScrollView)content.findViewById(R.id.viewflipper_myScrollview);
            myScrollView.setGestureDetector(mGestureDetector);
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

    private void refreshViewBanner(ArrayList<ForeEndAdvertisingPositionInfo> array) {
        ArrayList<ADInfo> infosNew = new ArrayList<>();
//        for (int i = 0; i < array.size(); i++){
        ForeEndAdvertisingPositionInfo item = array.get(0);
        ArrayList<String> picList = item.getT();
        ArrayList<String> linkList = item.getL();
        for (int j = 0; j < picList.size(); j++){
            ADInfo ad = new ADInfo();
            ad.setUrl(picList.get(j));
            ad.setContent(linkList.get(j));
            infosNew.add(ad);
        }
//        }
        updateBanner(infosNew);
    }

    private void updateBanner(ArrayList<ADInfo> listsBanner) {
        infos = listsBanner;
        flipper.removeAllViews();
        for (int i = 0; i < infos.size(); i++) {
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
        addIndicator(infos.size());
        setIndicator(0);
        handler.postDelayed(runnable, Consts.TIME_PERIOD);
    }

    private void addIndicator(int size){
//        if(indicators == null) {
        indicators = new ImageView[size];
//        }
        layoutPoint.removeAllViews();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_cycle_viewpager_indicator, null);
            indicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
            layoutPoint.addView(view);
        }

    }

    private void setIndicator(int current){
        for(int i = 0; i < indicators.length; i++) {
            if(i == current) {
                indicators[current].setImageResource(R.drawable.icon_point_pre);
            }else{
                indicators[i].setImageResource(R.drawable.icon_point);
            }
        }
    }

    private void addImageView(int length) {
        for(int i=0; i < length; i++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls.get(i));
            info.setContent("图片-->" + i);
            infos.add(info);
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
    }

    /**
     * 对应被选中的点的图片
     * @param id
     */
    private void dianSelect(int id) {
        indicators[id].setImageResource(R.drawable.icon_point_pre);
    }
    /**
     * 对应未被选中的点的图片
     * @param id
     */
    private void dianUnselect(int id){
        indicators[id].setImageResource(R.drawable.icon_point);
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, InnerActivity4.class);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
//            Log.i(TAG, "==============开始向左滑动了================");
            showNextView();
            releaseTime = System.currentTimeMillis();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, Consts.TIME_PERIOD);
            return true;
        }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
//            Log.i(TAG, "==============开始向右滑动了================");
            showPreviousView();
            releaseTime = System.currentTimeMillis();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, Consts.TIME_PERIOD);
            return true;
        }else{
            return false;
        }
    }

    private void showNextView() {
//        Log.i(TAG, "========showNextView=======向左滑动=======");
        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_out));
        flipper.showNext();
        currentPosition++;
        if(currentPosition == flipper.getChildCount()){
            dianUnselect(currentPosition - 1);
            currentPosition = 0;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition - 1);
            dianSelect(currentPosition);
        }
//		Log.i(TAG, "==============第"+currentPage+"页==========");
    }

    private void showPreviousView() {
//        Log.i(TAG, "========showPreviousView=======向右滑动=======");
        dianSelect(currentPosition);
        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_right_out));
        flipper.showPrevious();
        currentPosition--;
        if(currentPosition == -1){
            dianUnselect(currentPosition + 1);
            currentPosition = flipper.getChildCount() - 1;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition + 1);
            dianSelect(currentPosition);
        }
//		Log.i(TAG, "==============第"+currentPage+"页==========");
    }
}
