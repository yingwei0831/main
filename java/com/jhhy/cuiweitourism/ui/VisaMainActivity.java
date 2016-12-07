package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaHotAnyCountryGridAdapter;
import com.jhhy.cuiweitourism.adapter.VisaHotCountryGridAdapter;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.moudle.ADInfo;
import com.jhhy.cuiweitourism.moudle.CityRecommend;
import com.jhhy.cuiweitourism.moudle.VisaHotCountry;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.biz.VisaActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaCountry;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaHot;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaCountryInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaHotInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.view.MyGridView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;
import com.markmao.pulltorefresh.widght.XScrollView;

import java.util.ArrayList;
import java.util.List;

public class VisaMainActivity extends BaseActivity implements XScrollView.IXScrollViewListener, GestureDetector.OnGestureListener, View.OnClickListener, View.OnTouchListener {

    private String TAG = VisaMainActivity.class.getSimpleName();
    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private XScrollView mScrollView;
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
//    private int time = 4000; // 默认轮播时间

    private View content;

    private MyGridView gvHotVisaCountry; //热门签证国家(top)
    private VisaHotCountryGridAdapter adapterHotCountry;
    private List<VisaCountryInfo> listHotCountry = new ArrayList<>();

    private Button btnVisaViewMore; //查看全部国家和地区

    private MyGridView gvHotVisaAny; //热门签证（bottom）
    private VisaHotAnyCountryGridAdapter adapterHotAny;
    private List<VisaHotInfo> listHotAny = new ArrayList<>();


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadingIndicator.cancel();
            switch (msg.what){
                case Consts.MESSAGE_VISA_HOT: //热门签证（bottom）
                    if (msg.arg1 == 1){
                        listHotAny = (List<VisaHotInfo>) msg.obj;
                        if (listHotAny != null || listHotAny.size() > 0){
                            adapterHotAny.setData(listHotAny);
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取服务器数据失败");
                        }
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
                    break;
                case Consts.MESSAGE_VISA_HOT_COUNTRY: //热门签证国家（top）
                    if (msg.arg1 == 1){
                        listHotCountry = (List<VisaCountryInfo>) msg.obj;
                        if (listHotCountry != null || listHotCountry.size() > 0){
                            adapterHotCountry.setData(listHotCountry);
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取服务器数据失败");
                        }
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
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
                default:
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!VisaMainActivity.this.isFinishing()) {
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

    private VisaActionBiz biz; //热门签证国家，查看全部国家和地区


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa_main);
        getInternetData();
        setupView();
        LoadingIndicator.show(VisaMainActivity.this, getString(R.string.http_notice));
        getHotVisaCountry();
        getHotVisaAny();
        addListener();
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

    private void setupView(){
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.tab1_tablelayout_item8));
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        mScrollView = (XScrollView) findViewById(R.id.scroll_view_visa);
        mScrollView.setPullRefreshEnable(false);
        mScrollView.setPullLoadEnable(false);
        mScrollView.setAutoLoadEnable(false);
//        mScrollView.setIXScrollViewListener(this);
//        mScrollView.setRefreshTime(Utils.getCurrentTime());

        content = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_visa_main_content, null);
        if (content != null){
            gvHotVisaCountry = (MyGridView) content.findViewById(R.id.gv_visa_hot_country);
            gvHotVisaCountry.setFocusable(false);
            gvHotVisaCountry.setFocusableInTouchMode(false);

            adapterHotCountry = new VisaHotCountryGridAdapter(getApplicationContext(), listHotCountry);
            gvHotVisaCountry.setAdapter(adapterHotCountry);

            btnVisaViewMore = (Button) content.findViewById(R.id.btn_visa_view_all);

            gvHotVisaAny = (MyGridView) content.findViewById(R.id.gv_hot_country_any);
            adapterHotAny = new VisaHotAnyCountryGridAdapter(getApplicationContext(), listHotAny);
            gvHotVisaAny.setAdapter(adapterHotAny);

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
        mScrollView.setView(content);
        biz = new VisaActionBiz();
    }

    private void getHotVisaAny() { //热门签证
//        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
//        biz.getHotVisa();
        VisaHot visaHot = new VisaHot();
        visaHot.setCountryCode("");
        visaHot.setDistributionArea("");
        biz.getVisaHotList(visaHot, new BizGenericCallback<ArrayList<VisaHotInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<VisaHotInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    listHotAny = model.body;
                    LogUtil.e(TAG,"visaHotInfo: " + listHotAny.toString());
                    adapterHotAny.setData(listHotAny);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "热门签证数据出错");
                }
                LogUtil.e(TAG, "visaHotInfo: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    private void getHotVisaCountry() { //热门签证国家
//        VisaBiz biz = new VisaBizsaBiz(getApplicationContext(), handler);
//        biz.getHotCountry();

        VisaCountry visaCountry = new VisaCountry();
        visaCountry.setIsHot("Y");
        biz.getVisaCountry(visaCountry, new BizGenericCallback<ArrayList<VisaCountryInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<VisaCountryInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    listHotCountry = model.body;
                    LogUtil.e(TAG,"visaCountryInfo: " + listHotCountry.toString());
                    adapterHotCountry.setData(listHotCountry);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "热门签证国家数据出错");
                }
                LoadingIndicator.cancel();
                LogUtil.e(TAG, "visaCountryInfo: " + error.toString());
            }
        });
    }

    private void addListener(){
        ivTitleLeft.setOnClickListener(this);

        btnVisaViewMore.setOnClickListener(this);

        //热门签证国家
        gvHotVisaCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //进入国家下面的各种签证 列表

                Intent intent = new Intent(getApplicationContext(), VisaListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nationId", listHotCountry.get(i).getCountryCode());
                bundle.putString("nationName", listHotCountry.get(i).getCountryName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //热门签证
        gvHotVisaAny.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.e(TAG, "position = " + i +", l = " + l); //进入详情
                String id = listHotAny.get(i).getVisaId();
                Intent intent = new Intent(getApplicationContext(), VisaItemDetailActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_VISA_DETAIL);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.btn_visa_view_all: //查看全部国家和地区
                startActivity(new Intent(getApplicationContext(), SelectAllCountryAreaActivity.class));
                break;
        }
    }

    private final int VIEW_VISA_DETAIL = 1801; //热门签证详情

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_VISA_DETAIL){ //底部，热门签证，详情
            if (RESULT_OK == resultCode){

            }
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

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
        if (infos.size() < 2){
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
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

    private void addImageView(int length) {
        for(int i=0; i < length; i++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls.get(i));
            info.setContent("图片-->" + i);
            infos.add(info);
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, VisaMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
