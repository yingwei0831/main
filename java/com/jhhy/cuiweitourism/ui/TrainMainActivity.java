package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.circleviewpager.ViewFactory;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.model.ADInfo;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndAdvertise;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyScrollView;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jeesoft.ArrayDataDemo;
import cn.jeesoft.OptionsWindowHelper;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;

public class TrainMainActivity extends BaseActionBarActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private String TAG = TrainMainActivity.class.getSimpleName();
    private View parent;
    public static ArrayList<TrainStationInfo> stations; //火车站

    private ImageView ivExchange; //交换出发地和目的地
    private TextView tvFromCity;
    private TextView tvToCity;
    private TextView tvFromDate;
    private TextView tvTrainType;
    private TextView tvSeatType;

    private CharacterPickerWindow window; //车次类型，席别类型

    private Button btnSearch;

    private String selectDate; //选择的出发时间
    private TrainStationInfo fromCity; //出发城市
    private TrainStationInfo toCity; //到达城市
    private String typeTrain; //车型
    private String typeSeat; //座位类型

    private String codeTrain; //车次类型代码
    private String codeSeat; //席别类型代码

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
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    break;
                case -2:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求火车站信息出错，请返回重试");
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
            if (!TrainMainActivity.this.isFinishing()) {
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
        setContentView(R.layout.activity_train_main);
        super.onCreate(savedInstanceState);
        getTrainData();
        getInternetData();
    }

    @Override
    protected void setupView() {
        super.setupView();
        imageUrls.add("drawable://" + R.drawable.ic_empty);
        parent = findViewById(R.id.view_parent);
        tvTitle.setText(getString(R.string.tab1_tablelayout_item5));
        tvFromCity =    (TextView) findViewById(R.id.tv_train_from_city);
        tvToCity =      (TextView) findViewById(R.id.tv_train_to_city);
        tvFromDate =    (TextView) findViewById(R.id.tv_train_from_time);
        tvTrainType =   (TextView) findViewById(R.id.tv_train_type);
        tvSeatType =    (TextView) findViewById(R.id.tv_train_seat_type);
        btnSearch = (Button) findViewById(R.id.btn_train_search);
        ivExchange = (ImageView) findViewById(R.id.iv_train_exchange);

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

        tvFromCity.setText("北京");
        tvToCity.setText("上海");
        selectDate = Utils.getCurrentTimeYMDE();
        tvFromDate.setText(selectDate.substring(selectDate.indexOf("-") + 1, selectDate.indexOf(" ")));
        fromCity = new TrainStationInfo();
        fromCity.setName("北京");
        toCity = new TrainStationInfo();
        toCity.setName("上海");
        tvTrainType.setText("不限");
        typeTrain = "";
        tvSeatType.setText("不限");
        typeSeat = "";
    }

    @Override
    protected void addListener() {
        super.addListener();
        tvFromCity.setOnClickListener(this);
        tvToCity.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvTrainType.setOnClickListener(this);
        tvSeatType.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        ivExchange.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_train_exchange:
                exchange();
                break;
            case R.id.btn_train_search:
                search();
                break;
            case R.id.tv_train_from_city: //出发城市
                selectFromCity();
                break;
            case R.id.tv_train_to_city: //到达城市
                selectToCity();
                break;
            case R.id.tv_train_from_time: //出发时间
                selectFromTime();
                break;
            case R.id.tv_train_type: //车次类型
                selectSeatType();
                break;
            case R.id.tv_train_seat_type: //席别类型
                selectSeatType();
                break;
        }
    }
    //交换出发城市和目的城市
    private void exchange() {
        TrainStationInfo tempCity;
        tempCity = toCity;
        toCity = fromCity;
        fromCity = tempCity;
        tvFromCity.setText(fromCity.getName());
        tvToCity.setText(toCity.getName());
    }

    //选择席别类型，弹窗
    private void selectSeatType() {
        if (window == null) {
            //初始化
            window = OptionsWindowHelper.builder(TrainMainActivity.this, new OptionsWindowHelper.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(String province, String city, String area) {
                    LogUtil.e("main", province + "," + city + ", " + area);
                    typeTrain = province; //车次类型
                    typeSeat = city; //座位类型
                    tvTrainType.setText(typeTrain);
                    tvSeatType.setText(typeSeat);
                }
            });
        }
        if (window.isShowing()){
            window.dismiss();
        }else{
            // 弹出
            window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            if (typeTrain == null || typeSeat == null || typeTrain.length() == 0 || typeSeat.length() == 0){
                window.setSelectOptions(0, 0);
            }else {
                window.setSelectOptions(typeTrain, typeSeat);
            }
        }
    }


    //选择出发时间
    private void selectFromTime() {
        Intent intent = new Intent(getApplicationContext(), DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_START_TIME);
    }
    //选择出发城市
    private void selectFromCity() {
        Intent intent = new Intent(getApplicationContext(), TrainCitySelectionActivity.class);
        startActivityForResult(intent, SELECT_FROM_CITY);
    }
    //选择到达城市
    private void selectToCity() {
        Intent intent = new Intent(getApplicationContext(), TrainCitySelectionActivity.class);
        startActivityForResult(intent, SELECT_TO_CITY);
    }
    //搜索火车票
    private void search() {
        if (fromCity == null || toCity == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择出发城市或到达城市");
            return;
        }
        if (selectDate == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择出发时间");
            return;
        }
        TrainTicketFetch trainTicket = new TrainTicketFetch();
        trainTicket.setFromstation(fromCity.getName());
        trainTicket.setArrivestation(toCity.getName());
        trainTicket.setTraveltime(selectDate);
        if (typeTrain != null){
            if ("不限".equals(typeTrain)){
                trainTicket.setTraintype(codeTrain);
            }else {
                Iterator it = ArrayDataDemo.TRAIN.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                    if (typeTrain.equals(entry.getKey())) {
                        codeTrain = entry.getValue();
//                        trainTicket.setTraintype(codeTrain);
                        trainTicket.setTraintype(typeTrain);
                        break;
                    }
                }
            }
        }
        if (typeSeat != null && typeTrain != null){
            if ("不限".equals(typeSeat)){
                trainTicket.setTrainseattype(codeSeat);
            }else {
                Iterator it = ArrayDataDemo.DATAs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Map<String, String>> entry = (Map.Entry<String, Map<String, String>>) it.next();
                    if (typeTrain.equals(entry.getKey())) {
                        Map<String, String> value = entry.getValue();
                        Iterator valueInner = value.entrySet().iterator();
                        while (valueInner.hasNext()) {
                            Map.Entry<String, String> entryInner = (Map.Entry<String, String>) valueInner.next();
                            if (typeSeat.equals(entryInner.getKey())) {
                                codeSeat = entryInner.getValue();
//                                trainTicket.setTrainseattype(codeSeat);
                                trainTicket.setTrainseattype(typeSeat); //二等座
                                break;
                            }
                        }

                    }
                }
            }
        }
        LogUtil.e(TAG, "typeTrain = " + typeTrain + ", codeTrain = " + codeTrain + ", typeSeat = " + typeSeat +", " +", codeSeat = " +codeSeat);
        trainTicket.setTraintype(typeTrain);
        trainTicket.setTrainseattype(typeSeat);
        Intent intent = new Intent(getApplicationContext(), TrainListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticket", trainTicket);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_TICKET_LIST);
    }

    private int SELECT_FROM_CITY = 6526; //选择出发城市
    private int SELECT_TO_CITY = 6527; //选择到达城市
    private int SELECT_START_TIME = 6528; //选择出发时间 10-24
    private int VIEW_TICKET_LIST = 6529; //查看车票列表

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_START_TIME){ //选择出发时间
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                selectDate = bundle.getString("selectDate");
//                tvFromDate.setText(selectDate);
                tvFromDate.setText(selectDate.substring(0, selectDate.indexOf(" ")));
            }
        }else if (requestCode == SELECT_FROM_CITY){ //选择出发城市
            if (resultCode == RESULT_OK){
                TrainStationInfo city = (TrainStationInfo) data.getExtras().getSerializable("selectCity");
                LogUtil.e(TAG, "selectCity = " + city);
                fromCity = city;
                tvFromCity.setText(city.getName());
            }
        } else if (requestCode == SELECT_TO_CITY){ //选择到达城市
            if (resultCode == RESULT_OK){
                TrainStationInfo city = (TrainStationInfo) data.getExtras().getSerializable("selectCity");
                LogUtil.e(TAG, "selectCity = " + city);
                toCity = city;
                tvToCity.setText(city.getName());
            }
        } else if (requestCode == VIEW_TICKET_LIST){ //查看车次列表
            if (resultCode == RESULT_OK){

            }
        }

    }

    /**
     * 获取火车站
     */
    private void getTrainData() {
        LoadingIndicator.show(TrainMainActivity.this, getString(R.string.http_notice));
        new Thread(){
            @Override
            public void run() {
                super.run();
                //火车站
                TrainTicketActionBiz trainBiz = new TrainTicketActionBiz();
                trainBiz.trainStationInfo(new BizGenericCallback<ArrayList<TrainStationInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<TrainStationInfo>> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
                            stations = model.body;
                            LogUtil.e(TAG,"trainStationInfo =" + stations.toString());
                        }
                        LoadingIndicator.cancel();
                    }

                    @Override
                    public void onError(FetchError error) {
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
//                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                        }else{
//                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求火车站信息出错，请返回重试");
                            handler.sendEmptyMessage(-2);
                        }
                        LogUtil.e(TAG, "trainStationInfo: " + error.toString());
                        LoadingIndicator.cancel();
                    }
                });

            }
        }.start();
    }

    private void getInternetData() {
        //广告位
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
//        mark:index（首页）、line_index(国内游、出境游)、header（分类上方）、visa_index（签证）、customize_index(个性定制)
        ForeEndAdvertise ad = new ForeEndAdvertise("help_show");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stations = null;
        handler.removeCallbacks(runnable);
        runnable = null;
        handler = null;
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

    private void addImageView(int length) {
        for(int i=0; i < length; i++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls.get(i));
            info.setContent("图片-->" + i);
            infos.add(info);
            flipper.addView(ViewFactory.getImageView(getApplicationContext(), infos.get(i).getUrl()));
        }
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

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, TrainMainActivity.class);
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
}
