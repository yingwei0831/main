package com.jhhy.cuiweitourism.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotRecommendGridViewAdapter;
import com.jhhy.cuiweitourism.adapter.OrdersPagerAdapter;
import com.jhhy.cuiweitourism.biz.ExchangeBiz;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.moudle.ADInfo;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.InnerTravelCityActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.ui.SearchActivity;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.TouchPanelLayoutModify;

import java.util.ArrayList;
import java.util.List;

public class Tab2Fragment_2 extends Fragment implements TouchPanelLayoutModify.IConfigCurrentPagerScroll, TouchPanelLayoutModify.OnViewUpdateListener, View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener, GestureDetector.OnGestureListener {

    private String TAG = Tab2Fragment_2.class.getSimpleName();

    //  轮播图片
    private List<ADInfo> infos = new ArrayList<ADInfo>();

    private ViewFlipper flipper;
    private LinearLayout layoutPoint;
    private List<String> imageUrls = new ArrayList<>();

    private ImageView[] indicators; // 轮播图片数组
    private int currentPosition = 0; // 轮播当前位置

    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 0;
    private GestureDetector mGestureDetector; // MyScrollView的手势
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private boolean isScrolling = false; // 滚动框是否滚动着
//    private int time = 4000; // 默认轮播时间
    private final int WHEEL = 100; // 转动
    private final int WHEEL_WAIT = 101; // 等待

    private TextView tvHotRecommendNext; //换一批
    private MyGridView gridViewHotRecommend;
    private HotRecommendGridViewAdapter hotAdapter;
    private List<CityRecommend> listHotRecommend = new ArrayList<>();

    private String[] mTitles = new String[]{"国内游", "出境游", "周边游"};
    private List<Fragment> mContent = new ArrayList<>();
    private ViewPager viewPager;
    private OrdersPagerAdapter pagerAdapter;

    private boolean loadErrorHot; //其中有一个下载失败，则为失败
    private boolean loadErrorBan; //其中有一个下载失败，则为失败

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e(TAG, "---------------handleMessage--------------- what = " + msg.what);
            switch (msg.what){
                case Consts.MESSAGE_EXCHANGE:
                    if (msg.arg1 == 1){
                        List<CityRecommend> listDest = (List<CityRecommend>) msg.obj;
                        if (listDest == null || listDest.size() == 0){
                            ToastUtil.show(getContext(), "没有热门推荐");
                            loadErrorHot = true;
                        }else{
                            listHotRecommend = listDest;
                            hotAdapter.setData(listHotRecommend);
                        }
                    }else if (msg.arg1 == 0){
                        ToastUtil.show(getContext(), (String) msg.obj);
                        loadErrorHot = true;
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
                    ToastUtil.show(getContext(), "请检查网络后重试");
                    break;

                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getContext(), "与服务器链接超时，请重试");
                    break;
                default:
                    break;
            }
        }
    };

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity() != null && !getActivity().isFinishing()) {
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

    private Tab2BottomContentFragment  bottomFragment1;
    private Tab2BottomContentFragment2 bottomFragment2;


    public Tab2Fragment_2() {
    }

    public static Tab2Fragment_2 newInstance(String param1, String param2) {
        Tab2Fragment_2 fragment = new Tab2Fragment_2();
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
        getInternetData();
        ((TouchPanelLayoutModify)mTouchPanelLayout).setConfigCurrentPagerScroll(this);
        ((TouchPanelLayoutModify)mTouchPanelLayout).setOnViewUpdateListener(this);

        setupView(mTouchPanelLayout);
        addListener();
        return mTouchPanelLayout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void getInternetData() {
        imageUrls.add("drawable://" + R.mipmap.travel_icon);
        //广告位
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
//        mark:index（首页）、line_index(国内游、出境游)、header（分类上方）、visa_index（签证）、customize_index(个性定制)
        ForeEndAdvertise ad = new ForeEndAdvertise("header");
        fbiz.foreEndGetAdvertisingPosition(ad, new BizGenericCallback<ArrayList<ForeEndAdvertisingPositionInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ForeEndAdvertisingPositionInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<ForeEndAdvertisingPositionInfo> array = model.body;
                    LogUtil.e(TAG,"foreEndGetAdvertisingPosition =" + array.toString());
                    refreshViewBanner(array);
                }else{
                    ToastCommon.toastShortShow(getContext(), null, "获取广告位数据失败, 点击广告位重试");
                    loadErrorBan = true;
                }
            }

            @Override
            public void onError(FetchError error) {
                loadErrorBan = true;
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getContext(), null, error.localReason + ", 点击广告位重试");
                }else{
                    ToastCommon.toastShortShow(getContext(), null, "获取广告位数据出错, 点击广告位重试");
                }
                LogUtil.e(TAG, "foreEndGetAdvertisingPosition: " + error.toString());
            }
        });
    }

    private void setupView(View view) {
        getRecommend();
        View ivSearchLeft = view.findViewById(R.id.iv_title_search_left);
        ivSearchLeft.setVisibility(View.GONE);
        TextView etSearchText = (TextView) view.findViewById(R.id.edit_search);
        etSearchText.setHint("输入你想去的地方");
        etSearchText.setOnClickListener(this);

        tvHotRecommendNext = (TextView) view.findViewById(R.id.tv_tab2_hot_recommend_next);
        gridViewHotRecommend = (MyGridView) view.findViewById(R.id.gv_tab2_top_hot_recommend);
        for(int i = 0; i < 2; i++){
            CityRecommend cityRecommend = new CityRecommend();
            cityRecommend.setCityName(" ");
            listHotRecommend.add(cityRecommend);
        }
        hotAdapter = new HotRecommendGridViewAdapter(getContext(), listHotRecommend);
        gridViewHotRecommend.setAdapter(hotAdapter);


        TabLayout tabIndicator = (TabLayout) view.findViewById(R.id.tab_tab2_indicator);
        //设置TabLayout的模式
        tabIndicator.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[0]));
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[1]));
        tabIndicator.addTab(tabIndicator.newTab().setText(mTitles[2]));

        viewPager = (ViewPager) view.findViewById(R.id.viewpager_tab2_bottom);
        viewPager.setOffscreenPageLimit(2);
        bottomFragment1 = new Tab2BottomContentFragment();
        bottomFragment2 = new Tab2BottomContentFragment2();
        mContent.add(bottomFragment1);
        mContent.add(bottomFragment2);
//        mContent.add(new Tab2BottomContentFragment3());
        pagerAdapter = new OrdersPagerAdapter(getChildFragmentManager(), mTitles, mContent);
        viewPager.setAdapter(pagerAdapter);
        tabIndicator.setupWithViewPager(viewPager);

        mGestureDetector = new GestureDetector(getActivity(), this);

        flipper = (ViewFlipper)view.findViewById(R.id.viewflipper);
        layoutPoint =(LinearLayout)view.findViewById(R.id.layout_indicator_point);

        addImageView(imageUrls.size());
        addIndicator(imageUrls.size());
        setIndicator(currentPosition);

        flipper.setOnTouchListener(this);

        dianSelect(currentPosition);
        MyScrollView myScrollView = (MyScrollView)view.findViewById(R.id.viewflipper_myScrollview);
        myScrollView.setGestureDetector(mGestureDetector);
    }

    private void addListener() {
        tvHotRecommendNext.setOnClickListener(this);
        gridViewHotRecommend.setOnItemClickListener(this);

        flipper.setOnClickListener(this);
    }

    public void refreshView(){
        getInternetData();
        getRecommend();
        bottomFragment1.getData("1");
        bottomFragment2.getData("2");
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
            case R.id.viewflipper:
                if (loadErrorBan){
                    getInternetData();
                }else if (loadErrorHot){
                    getRecommend();
                }
                break;
            case R.id.edit_search: //搜索
                Bundle bundleSearch = new Bundle();
//                bundleSearch.putSerializable("selectCity", selectCity);
                SearchActivity.actionStart(getContext(), bundleSearch);
                break;
        }
    }

    private void getRecommend(){
        ExchangeBiz biz = new ExchangeBiz(getContext(), handler);
        biz.getHotRecommend("");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //热门推荐，点击
        Intent intent = new Intent(getContext(), InnerTravelCityActivity.class); //选择的城市旅游列表
        Bundle bundle = new Bundle();
        bundle.putString("cityId", listHotRecommend.get(i).getCityId());
        bundle.putString("cityName", listHotRecommend.get(i).getCityName());
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return  mGestureDetector.onTouchEvent(motionEvent);
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
            LogUtil.e(TAG, "adInfo = " + ad.toString());
        }
//        }
        updateBanner(infosNew);
    }
    private void updateBanner(ArrayList<ADInfo> listsBanner) {
        infos = listsBanner;
        flipper.removeAllViews();
        for (int i = 0; i < infos.size(); i++) {
            flipper.addView(ViewFactory.getImageView(getContext(), infos.get(i).getUrl()));
        }
        addIndicator(infos.size());
        setIndicator(0);
        if (listsBanner.size() <= 1){
            return;
        }
        handler.postDelayed(runnable, Consts.TIME_PERIOD);
    }

    private void addIndicator(int size){
//        if(indicators == null) {
        indicators = new ImageView[size];
//        }
        layoutPoint.removeAllViews();
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_cycle_viewpager_indicator, null);
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
            flipper.addView(ViewFactory.getImageView(getContext(), infos.get(i).getUrl()));
        }
    }

    /**
     * 对应被选中的点的图片
     * @param id
     */
    private void dianSelect(int id) {
        indicators[id].setImageResource(R.drawable.icon_point_pre);
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
        if (infos.size() <= 1){
            return false;
        }
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
        isScrolling = true;
        flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out));
        flipper.showNext();
        currentPosition++;
        if(currentPosition == flipper.getChildCount()){
            dianUnselect(currentPosition-1);
            currentPosition = 0;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition-1);
            dianSelect(currentPosition);
        }
        releaseTime = System.currentTimeMillis();
        isScrolling = false;
//        Log.i(TAG, "==============第"+currentPosition+"页==========");
    }

    private void showPreviousView() {
        // TODO Auto-generated method stub
//        Log.i(TAG, "========showPreviousView=======向右滑动=======");
        isScrolling = true;
//		thread.suspend();
        dianSelect(currentPosition);
        flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out));
        flipper.showPrevious();
        currentPosition--;
        if(currentPosition == -1){
            dianUnselect(currentPosition+1);
            currentPosition = flipper.getChildCount()-1;
            dianSelect(currentPosition);
        }else{
            dianUnselect(currentPosition+1);
            dianSelect(currentPosition);
        }
        releaseTime = System.currentTimeMillis();
        isScrolling = false;
//		Log.i(TAG, "==============第"+currentPage+"页==========");
//		thread.resume();
    }

    /**
     * 对应未被选中的点的图片
     * @param id
     */
    private void dianUnselect(int id){
        indicators[id].setImageResource(R.drawable.icon_point);
    }

}
