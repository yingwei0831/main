package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.model.ADInfo;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelScreenBrandRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelPositionLocationResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelScreenBrandResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelScreenFacilities;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.HanziToPinyin;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class HotelMainActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener {

    private String TAG = HotelMainActivity.class.getSimpleName();
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private TextView tvAddress;
    private TextView tvLocation;

    private RelativeLayout layoutCheckIn;
    private RelativeLayout layoutCheckOut;
    private TextView tvCheckInDate;
    private TextView tvCheckOutDate;
    private TextView tvCheckInNotice;
    private TextView tvCheckOutNotice;

    private EditText etSearchText;
    private Button btnSearch;
    private Button btnMyOrder;
    private Button btnMyHotel;

    private String checkInDate;
    private String checkOutDate;
    private int stayDays;

    private HotelProvinceResponse.ProvinceBean selectCity; //固定城市

    private HotelActionBiz hotelBiz;
    public static List<HotelProvinceResponse.ProvinceBean> listHotelProvince; //省份


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影>5.0
        setContentView(R.layout.activity_hotel_main);
        getData();
        setupView();
        addListener();
    }

    private void getProvince() {
        LoadingIndicator.show(HotelMainActivity.this, getString(R.string.http_notice));
        hotelBiz.getHotelProvinceList(new BizGenericCallback<HotelProvinceResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelProvinceResponse> model) {
                if ("0000".equals(model.headModel.res_code)){
                    //进入省份页面
                    listHotelProvince = model.body.getItem();
                    selectCityByUser();
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastUtil.show(getApplicationContext(), model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "请求省份： " + error);
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "请求省份信息失败，请重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    private void getData() {
        selectCity = new HotelProvinceResponse.ProvinceBean();
        selectCity.setName("北京");
        selectCity.setCode("0101");
    }

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

    private void setupView() {
//        imageUrls.add("drawable://" + R.drawable.ic_empty);
        imageUrls.add("drawable://" + R.mipmap.travel_icon);

        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.hotel_title));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        mGestureDetector = new GestureDetector(getApplicationContext(), this);

        flipper = (ViewFlipper)findViewById(R.id.viewflipper);
        layoutPoint =(LinearLayout)findViewById(R.id.layout_indicator_point);

        addImageView(imageUrls.size());
        addIndicator(imageUrls.size());
        setIndicator(currentPosition);

        flipper.setOnTouchListener(this);

        dianSelect(currentPosition);
//        MyScrollView myScrollView = (MyScrollView)findViewById(R.id.viewflipper_myScrollview);
//        myScrollView.setGestureDetector(mGestureDetector);

        tvAddress = (TextView) findViewById(R.id.tv_location_name);
        tvLocation = (TextView) findViewById(R.id.tv_location_icon);
        layoutCheckIn = (RelativeLayout) findViewById( R.id.layout_hotel_check_in);
        layoutCheckOut = (RelativeLayout) findViewById(R.id.layout_hotel_check_out);
        tvCheckInNotice = (TextView) findViewById(R.id.tv_hotel_check_in_notice);
        tvCheckOutNotice = (TextView) findViewById(R.id.tv_hotel_check_out_notice);
        tvCheckInDate = (TextView) findViewById(R.id.tv_hotel_check_into_date);
        tvCheckOutDate = (TextView) findViewById(R.id.tv_hotel_left_out_date);

        etSearchText = (EditText) findViewById(R.id.et_hotel_search_text);
        btnSearch = (Button) findViewById(R.id.btn_commit);
        btnMyOrder = (Button) findViewById(R.id.btn_to_my_order);
        btnMyHotel = (Button) findViewById(R.id.btn_to_my_hotel);

        tvAddress.setText(selectCity.getName());
        checkInDate = Utils.getCurrentTimeYMD();
        checkOutDate = Utils.getTimeStrYMD(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        tvCheckInDate.setText("今天");
        tvCheckOutDate.setText("明天");
        stayDays = 1;

        hotelBiz = new HotelActionBiz();
    }

    private void addListener() {
        ivTitleLeft .setOnClickListener(this);
        tvLocation .setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        layoutCheckIn .setOnClickListener(this);
        layoutCheckOut.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnMyOrder.setOnClickListener(this);
        btnMyHotel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_location_name: //选择位置
                if (listHotelProvince == null) {
                    getProvince();
                }else{
                    selectCityByUser();
                }
                break;
            case R.id.tv_location_icon: //定位
                startActivity(new Intent(getApplicationContext(), LocationSourceActivity.class));
                break;
            case R.id.layout_hotel_check_in: //选择入住日期
                Intent intentFromDate = new Intent( getApplicationContext(), HotelCalendarActivity.class);
                Bundle bundleFrom = new Bundle();
                bundleFrom.putString("from", checkInDate);
                bundleFrom.putString("left", checkOutDate);
                intentFromDate.putExtras(bundleFrom);
                startActivityForResult(intentFromDate, SELECT_CHECK_IN_DATE);
                break;
            case R.id.layout_hotel_check_out: //选择离店日期
                Intent intentLeftDate = new Intent( getApplicationContext(), HotelCalendarActivity.class);
                Bundle bundleLeft = new Bundle();
                bundleLeft.putString("from", checkInDate);
                bundleLeft.putString("left", checkOutDate);
                intentLeftDate.putExtras(bundleLeft);
                startActivityForResult(intentLeftDate, SELECT_CHECK_IN_DATE);
                break;
            case R.id.btn_commit: //搜索
                search();
                break;
            case R.id.btn_to_my_order: //我的订单

                break;
            case R.id.btn_to_my_hotel: //我的酒店
                ToastCommon.toastShortShow(getApplicationContext(), null, "后台没有接口");
                break;
        }
    }

    private int SELECT_CHECK_IN_DATE = 6524; //选择入住日期
    private int SELECT_CHECK_IN_ADDRESS = 6525; //选择住店地址

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CHECK_IN_DATE){ //选择入住日期
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                checkInDate = bundle.getString("hotelInDate");
                checkOutDate = bundle.getString("hotelOutDate");
                stayDays = bundle.getInt("stayDays");
                if (Utils.getCurrentTimeYMD().equals(checkInDate)){
                    tvCheckInNotice.setText("今天入住");
                } else {
                    tvCheckInNotice.setText("入住");
                }
                tvCheckOutNotice.setText(String.format("离店 住%d晚", stayDays));
                tvCheckInDate.setText(String.format("%s月%s日", checkInDate.substring(checkInDate.indexOf("-") + 1, checkInDate.lastIndexOf("-")), checkInDate.substring(checkInDate.lastIndexOf("-"))));
                tvCheckOutDate.setText(String.format("%s月%s日", checkOutDate.substring(checkOutDate.indexOf("-") + 1, checkOutDate.lastIndexOf("-")), checkOutDate.substring(checkOutDate.lastIndexOf("-"))));
//                tvCheckOutDate.setText(checkOutDate);
            }
        } else if (requestCode == SELECT_CHECK_IN_ADDRESS){ //选择地址
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                HotelProvinceResponse.ProvinceBean city =  bundle.getParcelable("selectCity");
                LogUtil.e(TAG, "选择地址："+city);
                if (city != null) {
                    selectCity = city;
                    tvAddress.setText(city.getName());
                }
            }
        }
    }

    private void search() {
        Intent intentSearch = new Intent(getApplicationContext(), HotelListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("checkInDate", checkInDate);
        bundle.putString("checkOutDate", checkOutDate);
        bundle.putInt("stayDays", stayDays);
        bundle.putParcelable("selectCity", selectCity);
        String keyWords = etSearchText.getText().toString();
        if (!TextUtils.isEmpty(keyWords)){
            bundle.putString("keyWords", keyWords);
        }
        intentSearch.putExtras(bundle);
        startActivity(intentSearch);
    }

    private void selectCityByUser() {
        Intent intent = new Intent(getApplicationContext(), HotelCitySelectionActivity.class);

        startActivityForResult(intent, SELECT_CHECK_IN_ADDRESS);
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, HotelMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
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

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (HotelMainActivity.this != null && !HotelMainActivity.this.isFinishing()) {
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
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
    protected void onDestroy() {
        super.onDestroy();
        if (listHotelProvince != null) {
            listHotelProvince.clear();
            listHotelProvince = null;
        }
    }
}
