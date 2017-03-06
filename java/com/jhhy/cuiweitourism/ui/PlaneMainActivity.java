package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneInquiryAdapter;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.model.ADInfo;
import com.jhhy.cuiweitourism.model.PlaneInquiry;
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
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class PlaneMainActivity extends BaseActionBarActivity implements RadioGroup.OnCheckedChangeListener, OnItemTextViewClick ,View.OnTouchListener, GestureDetector.OnGestureListener {

    private String TAG = "PlaneMainActivity";

    private RadioGroup  radioGroup; //出行类型
    private ImageView   ivExchange; //交换出发地和目的地
    private TextView    tvFromCity;
    private TextView    tvToCity;
    private TextView    tvFromDate;
    private LinearLayout layoutReturnDate; //返程时间
    private TextView    tvReturnDate; //返程时间
    private Button      btnSearch; //搜索

//    private String selectDate; //当前时间
    private String dateFrom; //选择的出发时间，显示在Textview 上
    private String dateReturn; //选择的返程时间，显示在Textview 上
    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市

    private PlaneTicketActionBiz planeBiz;
    public static ArrayList<PlaneTicketCityInfo> airportInner; //国内飞机场
    public static ArrayList<PlaneTicketCityInfo> airportOuter; //国际飞机场

    private View layoutPlaneSearch; //询价整体布局
    private MyListView layoutPlaneInquiryParent; //询价包裹行程布局
    private LinearLayout layoutBtn; //询价btn
    private Button btnInquiry; //询价btn
    private LinearLayout tvAddInquiry; //增加一程询价
//    private LinearLayout layoutInquiryType; //行程类型

    private ArrayList<PlaneInquiry> listInquiry = new ArrayList<>(); //出发城市，目的城市，出发时间

    private int bottomSelectionPosition; //底部点击位置
    private PlaneInquiryAdapter adapter;

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

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!PlaneMainActivity.this.isFinishing()) {
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
        setContentView(R.layout.activity_plane_main);
        super.onCreate(savedInstanceState);
        getInternetData();
        getInternetDataOut();
        LoadingIndicator.show(PlaneMainActivity.this, getString(R.string.http_notice));
        getBannerData();
    }

    @Override
    protected void setupView() {
        super.setupView();
        imageUrls.add("drawable://" + R.drawable.ic_empty);
        planeBiz = new PlaneTicketActionBiz();
        tvTitle.setText(getString(R.string.tab1_tablelayout_plane));
        radioGroup = (RadioGroup) findViewById(R.id.rg_plane_type);
        ivExchange = (ImageView) findViewById(R.id.iv_train_exchange);
        tvFromCity =    (TextView) findViewById(R.id.tv_plane_from_city);
        tvToCity =      (TextView) findViewById(R.id.tv_plane_to_city);
        tvFromDate =    (TextView) findViewById(R.id.tv_plane_from_time);
        layoutReturnDate = (LinearLayout) findViewById(R.id.layout_return_time);
        layoutReturnDate.setVisibility(View.GONE);
        tvReturnDate =  (TextView) findViewById(R.id.tv_plane_return_time);
        btnSearch = (Button) findViewById(R.id.btn_plane_search);

        layoutPlaneSearch = findViewById(R.id.layout_plane_search); //搜索航班线路
        layoutBtn = (LinearLayout) findViewById(R.id.layout_inquiry_btn); //查询下一步
        btnInquiry = (Button) findViewById(R.id.btn_train_next_step); //查询下一步
        tvAddInquiry = (LinearLayout) findViewById(R.id.layout_add_one_query); //增加
//        layoutInquiryType = (LinearLayout) findViewById(R.id.layout_inquiry_type); //行程类型
        layoutPlaneInquiryParent = (MyListView) findViewById(R.id.layout_query_lines); //查询线路列表
        tvAddInquiry.setVisibility(View.GONE);
//        layoutInquiryType.setVisibility(View.GONE);
        layoutBtn.setVisibility(View.GONE);
        layoutPlaneInquiryParent.setVisibility(View.GONE);

        listInquiry.add(new PlaneInquiry());
        adapter = new PlaneInquiryAdapter(getApplicationContext(), listInquiry, this);
        layoutPlaneInquiryParent.setAdapter(adapter);

        tvFromCity.setText("北京");
        tvToCity.setText("大连");
        dateFrom = Utils.getCurrentTimeYMDE();
        dateReturn = dateFrom;
        tvFromDate.setText(dateFrom.substring(dateFrom.indexOf("-") + 1, dateFrom.indexOf(" ")));
        tvReturnDate.setText(dateReturn.substring(dateReturn.indexOf("-") + 1, dateReturn.indexOf(" ")));

        fromCity = new PlaneTicketCityInfo();
        fromCity.setName("北京");
        fromCity.setCode("PEK");
        fromCity.setAirportname("北京首都国际机场");
        fromCity.setIsdomc("D");
        toCity = new PlaneTicketCityInfo();
        toCity.setName("大连");
        toCity.setCode("DLC");
        toCity.setAirportname("大连周水子国际机场");
        toCity.setIsdomc("D");

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
    }

    @Override
    protected void addListener() {
        super.addListener();
        radioGroup.setOnCheckedChangeListener(this);
        ivExchange.setOnClickListener(this);
        tvFromCity.setOnClickListener(this);
        tvToCity.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvReturnDate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        tvAddInquiry.setOnClickListener(this);
        btnInquiry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_train_exchange:
                exchange();
                break;
            case R.id.tv_plane_from_city: //出发城市
                selectCity(SELECT_FROM_CITY);
                break;
            case R.id.tv_plane_to_city: //到达城市
                selectCity(SELECT_TO_CITY);
                break;
            case R.id.tv_plane_from_time: //出发时间
                selectTime(SELECT_START_TIME);
                break;
            case R.id.tv_plane_return_time: //返程时间
                selectTime(SELECT_RETURN_TIME);
                break;
            case R.id.btn_plane_search:
                search();
                break;
            case R.id.layout_add_one_query:
                addView();
                break;
            case R.id.btn_train_next_step: //询价
                inquiry();
                break;
        }
    }

    /**
     * 询价
     */
    private void inquiry() {
        for (int i = 0; i < listInquiry.size(); i++){
            PlaneInquiry item = listInquiry.get(i);
            if (item.getFromCity() == null ){
                ToastCommon.toastShortShow(getApplicationContext(), null, "出发城市不能为空");
                return;
            }else if (item.getArrivalCity() == null){
                ToastCommon.toastShortShow(getApplicationContext(), null, "目的城市不能为空");
                return;
            }else if (item.getFromDate() == null){
                ToastCommon.toastShortShow(getApplicationContext(), null, "出发时间不能为空");
                return;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listInquiry", listInquiry);
        PlaneInquiryEditOrderActivity.actionStart(this, bundle);
    }

    //搜索
    private void search() {

        if ("D".equals(fromCity.getIsdomc()) && "D".equals(toCity.getIsdomc())) { //国内机票,返程则查询两次
            Intent intent = new Intent(getApplicationContext(), PlaneListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("fromCity", fromCity);
            bundle.putSerializable("toCity", toCity);
            bundle.putString("dateFrom", dateFrom);
//            bundle.putString("traveltype", traveltype);
            bundle.putString("traveltype", "OW"); //此处说不要返程了！

            LogUtil.e(TAG, "dateFrom = " + dateFrom +", dateReturn = " + dateReturn);
//            if (type == 2) { //往返
//                if (TextUtils.isEmpty(dateReturn)){
//                    ToastUtil.show(getApplicationContext(), "请选择返程日期");
//                    return;
//                }
//                //判断返程日期与出发日期之间相差天数>0
//                if (Utils.getDiff(dateFrom.substring(0, dateFrom.indexOf(" ")), dateReturn.substring(0, dateReturn.indexOf(" "))) < 0){ //可能购买当天往返的机票
//                    ToastUtil.show(getApplicationContext(), "返程日期必须在出发日期之后");
//                    return;
//                }
//                bundle.putString("dateReturn", dateReturn);
//            }
            intent.putExtras(bundle);
            startActivityForResult(intent, VIEW_PLANE_LIST);
        } else { //国际机票
            Intent intent = new Intent(getApplicationContext(), PlaneListInternationalActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("fromCity", fromCity);
            bundle.putSerializable("toCity", toCity);
            bundle.putString("dateFrom", dateFrom);
            bundle.putString("traveltype", traveltype);
//            bundle.putInt("type", type); //单程，往返
            if (type == 2) { //往返，询价
                if (TextUtils.isEmpty(dateReturn)){
                    ToastUtil.show(getApplicationContext(), "返程日期不能为空");
                    return;
                }
                //判断返程日期与出发日期之间相差天数>0
                if (Utils.getDiff(dateFrom.substring(0, dateFrom.indexOf(" ")), dateReturn.substring(0, dateReturn.indexOf(" "))) < 0){ //可能购买当天往返的机票
                    ToastUtil.show(getApplicationContext(), "返程日期必须在出发日期之后");
                    return;
                }
                LogUtil.e(TAG, "dateReturn = " + dateReturn);
                bundle.putString("dateReturn", dateReturn);
            }
            intent.putExtras(bundle);
            startActivityForResult(intent, VIEW_PLANE_LIST);
        }
    }

    //出发城市
    private void selectCity(int requestCode) {
        if (airportInner == null || airportInner.size() == 0){
            ToastUtil.show(getApplicationContext(), "出发地获取失败，请返回重试");
            return;
        }
        Intent intent = new Intent(getApplicationContext(), PlaneCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        //根据当前是单程/往返/询价，传入type; 1:国内 2:国际 3:往返

        bundle.putInt("type", type);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    //目的城市
//    private void selectToCity() {
//        if (airportInner == null || airportInner.size() == 0){
//            ToastUtil.show(getApplicationContext(), "目的地获取失败，请返回重试");
//            return;
//        }
//        Intent intent = new Intent(getApplicationContext(), PlaneCitySelectionActivity.class);
//        Bundle bundle = new Bundle();
//        //根据当前是单程/往返/询价，传入type; 1:国内 2:国际 3:询价
//
//        bundle.putInt("type", type);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, SELECT_TO_CITY);
//    }

    //返程时间
    private void selectTime(int requestCode) {
        Intent intent = new Intent(getApplicationContext(), DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    //出发时间
//    private void selectStartTime() {
//        Intent intent = new Intent(getApplicationContext(), DatePickerActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("type", 2);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, SELECT_START_TIME);
//    }

    private int SELECT_START_TIME = 6528; //选择出发时间 10-24
    private int SELECT_RETURN_TIME = 6529; //选择返程时间 10-25
    private int SELECT_FROM_CITY = 6526; //选择出发城市
    private int SELECT_TO_CITY = 6527; //选择到达城市
    private int VIEW_PLANE_LIST = 6530; //选择到达城市

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_START_TIME){ //选择出发时间
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                if (type == 3){
                    setFromTime(bundle.getString("selectDate"));
                    return;
                }
                dateFrom = bundle.getString("selectDate");
                tvFromDate.setText(dateFrom.substring(0, dateFrom.indexOf(" ")));
            }
        }else if (requestCode == SELECT_RETURN_TIME){ //选择返程时间
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                dateReturn = bundle.getString("selectDate");
                tvReturnDate.setText(dateReturn.substring(0, dateReturn.indexOf(" ")));
            }
        }else if (requestCode == SELECT_FROM_CITY){ //选择出发城市
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                PlaneTicketCityInfo city = (PlaneTicketCityInfo) bundle.getSerializable("selectCity");
                if (type == 3){
                    setFromCity(city);
                    return;
                }
//                typeSearchFrom = bundle.getInt("typeSearch");
//                LogUtil.e(TAG, "selectCity = " + city +", typeSearchFrom = " + typeSearchFrom);
                fromCity = city;
                tvFromCity.setText(city.getName());
            }
        } else if (requestCode == SELECT_TO_CITY){ //选择到达城市
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                PlaneTicketCityInfo city = (PlaneTicketCityInfo) bundle.getSerializable("selectCity");
                if (type == 3){
                    setArrivalCity(city);
                    return;
                }
//                typeSearchTo = bundle.getInt("typeSearch");
//                LogUtil.e(TAG, "selectCity = " + city +", typeSearchTo = " + typeSearchTo);
                toCity = city;
                tvToCity.setText(city.getName());
            }
        }else if (requestCode == VIEW_PLANE_LIST){ //搜索
            if (resultCode == RESULT_OK){
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_plane_one_way: //单程
                layoutPlaneSearch.setVisibility(View.VISIBLE);
                layoutReturnDate.setVisibility(View.GONE);
                layoutBtn.setVisibility(View.GONE);
                layoutPlaneInquiryParent.setVisibility(View.GONE);
                tvAddInquiry.setVisibility(View.GONE);
//                layoutInquiryType.setVisibility(View.GONE);
                type = 1;
                traveltype = "OW";
                break;
            case R.id.rb_plane_return: //往返
                layoutPlaneSearch.setVisibility(View.VISIBLE);
                layoutReturnDate.setVisibility(View.VISIBLE);
                layoutBtn.setVisibility(View.GONE);
                layoutPlaneInquiryParent.setVisibility(View.GONE);
                tvAddInquiry.setVisibility(View.GONE);
//                layoutInquiryType.setVisibility(View.GONE);
                type = 2;
                traveltype = "RT";
                break;
            case R.id.rb_plane_inquiry: //询价
                layoutPlaneSearch.setVisibility(View.GONE);
                layoutBtn.setVisibility(View.VISIBLE);
                layoutPlaneInquiryParent.setVisibility(View.VISIBLE);
                tvAddInquiry.setVisibility(View.VISIBLE);
//                layoutInquiryType.setVisibility(View.VISIBLE);
                type = 3;
//                traveltype = "";
                break;
        }
    }

    /**
     * 增加询价行程
     */
    private void addView() { //默认当前询价行程的个数
        PlaneInquiry planeInquiry = new PlaneInquiry();
        if (listInquiry == null){
            listInquiry = new ArrayList<>();
        }
        listInquiry.add(planeInquiry);
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    /**
     * @param position 点击的联系人位置
     * @param textView 控件
     */
    @Override
    public void onItemTextViewClick(int position, View textView, int idNoUse) {
        LogUtil.e(TAG, "position = " + position );
        switch (textView.getId()){
            case R.id.iv_delete_one_query: //删除某个查询路线
                if (listInquiry.size() == 1){
                    ToastUtil.show(getApplicationContext(), "至少查询一条航线");
                    return;
                }
                listInquiry.remove(position);
                adapter.setData(listInquiry);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_plane_from_city: //出发城市
                bottomSelectionPosition = position;
                selectCity(SELECT_FROM_CITY);
                break;
            case R.id.tv_plane_to_city: //目的城市
                bottomSelectionPosition = position;
                selectCity(SELECT_TO_CITY);
                break;
            case R.id.iv_train_exchange: //交换出发城市/目的城市
                exchangeCity(position);
                break;
            case R.id.tv_plane_from_time: //出发时间
                bottomSelectionPosition = position;
                selectTime(SELECT_START_TIME);
                break;
        }
    }


    private void setFromTime(String selectDate) {
        String date = selectDate.substring(0, selectDate.indexOf(" "));
        PlaneInquiry item = listInquiry.get(bottomSelectionPosition);
        item.fromDate = date;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    private void setArrivalCity(PlaneTicketCityInfo city) {
        PlaneInquiry item = listInquiry.get(bottomSelectionPosition);
        item.arrivalCity = city;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    private void setFromCity(PlaneTicketCityInfo city) {
        PlaneInquiry item = listInquiry.get(bottomSelectionPosition);
        item.fromCity = city;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    private void exchangeCity(int position) {
        PlaneInquiry temp = listInquiry.get(position);
        PlaneTicketCityInfo tempCity = temp.arrivalCity;
        temp.arrivalCity = temp.fromCity;
        temp.fromCity = tempCity;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    //交换出发城市和目的城市
    private void exchange() {
        PlaneTicketCityInfo tempCity = toCity;
        toCity = fromCity;
        fromCity = tempCity;
        tvFromCity.setText(fromCity.getName());
        tvToCity.setText(toCity.getName());
    }

    private int type = 1; //1：单程 2：往返 3：询价
//    private int typeSearchFrom = 1; //1:国内，2：国外
//    private int typeSearchTo = 1; //1:国内，2：国外
    private String traveltype = "OW"; //航程类型 OW（单程） RT（往返）
    private boolean inner;
    private boolean outer;


    //获取国内机场列表
    private void getInternetData() {
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
    private void getInternetDataOut() {
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
//                            ArrayList<PlanTicketCityInfo> array = model.body;
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

    private void getBannerData() {
        //广告位
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
//        mark:index（首页）、line_index(国内游、出境游)、header（分类上方）、visa_index（签证）、customize_index(个性定制)
        ForeEndAdvertise ad = new ForeEndAdvertise("article_index");
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

    private void addImageView(int length) {
        for(int i=0; i < length; i++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls.get(i));
            info.setContent("图片-->" + i);
            infos.add(info);
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (airportInner != null) {
            airportInner.clear();
            airportInner = null;
        }
        if (airportOuter != null){
            airportOuter.clear();
            airportOuter = null;
        }
        if (listInquiry != null){
            listInquiry.clear();
            listInquiry = null;
        }
        planeBiz = null;
        fromCity = null;
        toCity = null;

        radioGroup= null;
        ivExchange= null;
        tvFromCity= null;
        tvToCity= null;
        tvFromDate= null;
        layoutReturnDate= null;
        tvReturnDate= null;
        btnSearch= null;
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, PlaneMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
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
        }else if(e2.getX() - e1.getX() > FLING_MIN_DISTANCE &&
                Math.abs(velocityX) > FLING_MIN_VELOCITY){
//            Log.i(TAG, "==============开始向右滑动了================");
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
}
