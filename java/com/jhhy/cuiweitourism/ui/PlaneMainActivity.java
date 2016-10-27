package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.moudle.Line;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketCityFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class PlaneMainActivity extends BaseActionBarActivity implements RadioGroup.OnCheckedChangeListener {

    private String TAG = PlaneMainActivity.class.getSimpleName();

    private RadioGroup radioGroup; //出行类型
    private ImageView ivExchange; //交换出发地和目的地
    private TextView tvFromCity;
    private TextView tvToCity;
    private TextView tvFromDate;
    private LinearLayout layoutReturnDate; //返程时间
    private TextView tvReturnDate; //返程时间
    private Button btnSearch; //搜索

//    private String selectDate; //当前时间
    private String dateFrom; //选择的出发时间，显示在Textview 上
    private String dateReturn; //选择的返程时间，显示在Textview 上
    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市

    private PlaneTicketActionBiz planeBiz;
    public static ArrayList<PlaneTicketCityInfo> airportInner; //国内飞机场
    public static ArrayList<PlaneTicketCityInfo> airportOuter; //国际飞机场

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
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_main);
        super.onCreate(savedInstanceState);
        LoadingIndicator.show(PlaneMainActivity.this, getString(R.string.http_notice));
        getInternetData();
        getInternetDataOut();
    }

    @Override
    protected void setupView() {
        super.setupView();
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
        toCity = new PlaneTicketCityInfo();
        toCity.setName("大连");
        toCity.setCode("DLC");
        toCity.setAirportname("大连周水子国际机场");
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
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_train_exchange:
                exchange();
                break;
            case R.id.tv_plane_from_city: //出发城市
                selectFromCity();
                break;
            case R.id.tv_plane_to_city: //到达城市
                selectToCity();
                break;
            case R.id.tv_plane_from_time: //出发时间
                selectFromTime();
                break;
            case R.id.tv_plane_return_time: //出发时间
                selectReturnTime();
                break;
            case R.id.btn_plane_search:
                search();
                break;
        }
    }

    //搜索
    private void search() {
        Intent intent = new Intent(getApplicationContext(), PlaneListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("fromCity", fromCity);
        bundle.putSerializable("toCity", toCity);
        bundle.putString("dateFrom", dateFrom);
        bundle.putInt("type", type);
        if (type == 2) {
            bundle.putString("dateReturn", dateReturn);
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_PLANE_LIST);
    }

    //出发城市
    private void selectFromCity() {
        Intent intent = new Intent(getApplicationContext(), PlaneCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        //根据当前是单程/往返/询价，传入type; 1:国内 2:国际

        bundle.putInt("type", type);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_FROM_CITY);
    }

    //目的城市
    private void selectToCity() {
        Intent intent = new Intent(getApplicationContext(), PlaneCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        //根据当前是单程/往返/询价，传入type; 1:国内 2:国际 3:询价

        bundle.putInt("type", type);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_TO_CITY);
    }

    //返程时间
    private void selectReturnTime() {
        Intent intent = new Intent(getApplicationContext(), DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_RETURN_TIME);
    }

    //出发时间
    private void selectFromTime() {
        Intent intent = new Intent(getApplicationContext(), DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_START_TIME);
    }

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
                PlaneTicketCityInfo city = (PlaneTicketCityInfo) data.getExtras().getSerializable("selectCity");
                LogUtil.e(TAG, "selectCity = " + city);
                fromCity = city;
                tvFromCity.setText(city.getName());
            }
        } else if (requestCode == SELECT_TO_CITY){ //选择到达城市
            if (resultCode == RESULT_OK){
                PlaneTicketCityInfo city = (PlaneTicketCityInfo) data.getExtras().getSerializable("selectCity");
                LogUtil.e(TAG, "selectCity = " + city);
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
                layoutReturnDate.setVisibility(View.GONE);
                type = 1;
                break;
            case R.id.rb_plane_return: //往返
                layoutReturnDate.setVisibility(View.VISIBLE);
                type = 2;
                break;
            case R.id.rb_plane_inquiry: //询价
                layoutReturnDate.setVisibility(View.GONE);
                tvFromCity.setText("请选择");
                tvToCity.setText("请选择");
                type = 3;
                break;
        }
    }

    //交换出发城市和目的城市
    private void exchange() {
        PlaneTicketCityInfo tempCity;
        tempCity = toCity;
        toCity = fromCity;
        fromCity = tempCity;
        tvFromCity.setText(fromCity.getName());
        tvToCity.setText(toCity.getName());
    }

    private int type = 1; //1：单程 2：往返 3：询价
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
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-2);
                        }
                        LogUtil.e(TAG, "getPlaneTicketCityInfo: " + error.toString());
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
                            LogUtil.e(TAG,"getPlaneTicketCityInfo =" + airportOuter.toString());
                        }
                        outer = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }

                    @Override
                    public void onError(FetchError error) {
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-3);
                        }
                        LogUtil.e(TAG, "getPlaneTicketCityInfo: " + error.toString());
                        outer = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }
                });
            }
        }.start();
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, PlaneMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}
