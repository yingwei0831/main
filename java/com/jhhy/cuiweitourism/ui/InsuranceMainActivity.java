package com.jhhy.cuiweitourism.ui;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelPagerAdapter;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.fragment.InsuranceFragment;
import com.jhhy.cuiweitourism.model.ADInfo;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketCityFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class InsuranceMainActivity extends BaseActionBarActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private static final String TAG = "InsuranceMainActivity";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private InnerTravelPagerAdapter mPagerAdapter;
    private List<Fragment> mListFragment;
    private List<String> mListTitle;

    private PlaneTicketActionBiz planeBiz;
    public static ArrayList<PlaneTicketCityInfo> airportInner; //国内飞机场
    public static ArrayList<PlaneTicketCityInfo> airportOuter; //国际飞机场
    private boolean inner;
    private boolean outer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_insurance_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupView() {
        super.setupView();
        imageUrls.add("drawable://" + R.drawable.ic_empty);
        tvTitle.setText(getString(R.string.tab1_insurance));
        mTabLayout = (TabLayout) findViewById(R.id.insurance_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_insurance);
        mListFragment = new ArrayList<>();
        mListFragment.add(InsuranceFragment.newInstance(0));
        mListFragment.add(InsuranceFragment.newInstance(1));
        mListTitle = new ArrayList<>();
        mListTitle.add(getString(R.string.tab1_tablelayout_item1));
        mListTitle.add(getString(R.string.tab1_tablelayout_item2));
        mPagerAdapter = new InnerTravelPagerAdapter(getApplicationContext(), getSupportFragmentManager(), mListFragment, mListTitle);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mGestureDetector = new GestureDetector(getApplicationContext(), this);
        flipper = (ViewFlipper)findViewById(R.id.viewflipper);
        layoutPoint =(LinearLayout)findViewById(R.id.layout_indicator_point);
        addImageView(imageUrls.size());
        addIndicator(imageUrls.size());
        setIndicator(currentPosition);
        flipper.setOnTouchListener(this);
        dianSelect(currentPosition);
        MyScrollView myScrollView = (MyScrollView)findViewById(R.id.viewflipper_myScrollview);
        myScrollView.setGestureDetector(mGestureDetector);

        planeBiz = new PlaneTicketActionBiz();
        getBannerData();
        getInternetDataInner();
        getInternetDataOuter();
        LoadingIndicator.show(this, getString(R.string.http_notice));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    @Override
    protected void addListener() {
        super.addListener();

    }

    //获取国内机场列表
    private void getInternetDataInner() {
        //飞机出发城市、到达城市
        new Thread(){
            @Override
            public void run() {
                super.run();
                PlaneTicketCityFetch fetch = new PlaneTicketCityFetch("D"); //国内飞机场
                planeBiz.getPlaneTicketCityInfo(fetch, new BizGenericCallback<ArrayList<PlaneTicketCityInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<PlaneTicketCityInfo>> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
//                            ArrayList<PlanTicketCityInfo> array = model.body;
                            airportInner = model.body;
                            LogUtil.e(TAG,"getPlaneTicketCityInfo =" + airportInner.toString());
                        }
                        inner = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }

                    @Override
                    public void onError(FetchError error) {
                        LogUtil.e(TAG, "getPlaneTicketCityInfo: " + error.toString());
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-2);
                        }
                        inner = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }
                });
            }
        }.start();
    }

    //获取国际机场列表
    private void getInternetDataOuter() {
        //飞机出发城市、到达城市
        new Thread(){
            @Override
            public void run() {
                super.run();
                PlaneTicketCityFetch fetch = new PlaneTicketCityFetch("I"); //国际飞机场
                planeBiz.getPlaneTicketCityInfo(fetch, new BizGenericCallback<ArrayList<PlaneTicketCityInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<PlaneTicketCityInfo>> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
                            airportOuter = model.body;
                            LogUtil.e(TAG," 2 getPlaneTicketCityInfo =" + airportOuter.toString());
                        }
                        outer = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }

                    @Override
                    public void onError(FetchError error) {
                        LogUtil.e(TAG, " 1 getPlaneTicketCityInfo: " + error.toString());
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-3);
                        }
                        outer = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }
                });
            }
        }.start();
    }

    private void getBannerData() {
        //广告位
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
//        mark:index（首页）、line_index(国内游、出境游)、header（分类上方）、visa_index（签证）、customize_index(个性定制)
        ForeEndAdvertise ad = new ForeEndAdvertise("visa_index");
        fbiz.foreEndGetAdvertisingPosition(ad, new BizGenericCallback<ArrayList<ForeEndAdvertisingPositionInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ForeEndAdvertisingPositionInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<ForeEndAdvertisingPositionInfo> array = model.body;
                    LogUtil.e(TAG,"foreEndGetAdvertisingPosition =" + array.toString());
                    refreshViewBanner(array);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    ToastUtil.show(getApplicationContext(), String.valueOf(msg.obj));
                    break;
                case -2:
                    ToastUtil.show(getApplicationContext(), "请求国内机场信息出错，请返回重试");
                    break;
                case -3:
                    ToastUtil.show(getApplicationContext(), "请求国际机场信息出错，请返回重试");
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
            }
        }
    };


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
        if (infos.size() < 2){
            return true;
        }
        if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
            showNextView();
            releaseTime = System.currentTimeMillis();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, Consts.TIME_PERIOD);
        }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
            showPreviousView();
            releaseTime = System.currentTimeMillis();
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, Consts.TIME_PERIOD);
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    //顶部图片展示
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private ViewFlipper flipper;
    private LinearLayout layoutPoint;
    private List<String> imageUrls = new ArrayList<>();
    private ImageView[] indicators; // 轮播图片数组
    private int currentPosition = 0; // 轮播当前位置

    private GestureDetector mGestureDetector; // MyScrollView的手势

    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 0;

    private final int WHEEL = 100; // 转动
    private final int WHEEL_WAIT = 101; // 等待
    private boolean isScrolling = false; // 滚动框是否滚动着
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换

    private void refreshViewBanner(ArrayList<ForeEndAdvertisingPositionInfo> array) {
        ArrayList<ADInfo> infosNew = new ArrayList<>();
        ForeEndAdvertisingPositionInfo item = array.get(0);
        ArrayList<String> picList = item.getT();
        ArrayList<String> linkList = item.getL();
        for (int j = 0; j < picList.size(); j++){
            ADInfo ad = new ADInfo();
            ad.setUrl(picList.get(j));
            ad.setContent(linkList.get(j));
            infosNew.add(ad);
        }
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
        if (infos.size() < 2){
            return;
        }
        handler.postDelayed(runnable, Consts.TIME_PERIOD);
    }
    private void addIndicator(int size){
        indicators = new ImageView[size];
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

    private void showNextView() {
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
    }
    private void showPreviousView() {
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

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!InsuranceMainActivity.this.isFinishing()) {
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
    protected void onDestroy() {
        super.onDestroy();
        airportInner.clear();
        airportInner = null;
        airportOuter.clear();
        airportOuter = null;
    }
}
