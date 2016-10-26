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
import com.jhhy.cuiweitourism.net.models.FetchModel.PlanTicketCityFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlanTicketCityInfo;
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
    private String selectFromDate; //选择的出发时间，显示在Textview 上
    private String selectReturnDate; //选择的返程时间，显示在Textview 上
    private PlanTicketCityInfo fromCity; //出发城市
    private PlanTicketCityInfo toCity; //到达城市

    private PlaneTicketActionBiz planeBiz;
    private ArrayList<PlanTicketCityInfo> airportInner; //国内飞机场
    private ArrayList<PlanTicketCityInfo> airportOuter; //国际飞机场

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
        selectFromDate = Utils.getCurrentTimeYMDE();
        selectReturnDate = selectFromDate;
        tvFromDate.setText(selectFromDate.substring(selectFromDate.indexOf("-") + 1, selectFromDate.indexOf(" ")));
        tvReturnDate.setText(selectReturnDate.substring(selectReturnDate.indexOf("-") + 1, selectReturnDate.indexOf(" ")));
        fromCity = new PlanTicketCityInfo();
        fromCity.setName("北京");
        toCity = new PlanTicketCityInfo();
        toCity.setName("大连");
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

    }

    //出发城市
    private void selectFromCity() {

    }

    //目的城市
    private void selectToCity() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_START_TIME){ //选择出发时间
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                selectFromDate = bundle.getString("selectDate");
                tvFromDate.setText(selectFromDate.substring(0, selectFromDate.indexOf(" ")));
            }
        }else if (requestCode == SELECT_RETURN_TIME){ //选择返程时间
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                selectReturnDate = bundle.getString("selectDate");
                tvReturnDate.setText(selectReturnDate.substring(0, selectReturnDate.indexOf(" ")));
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_plane_one_way: //单程
                layoutReturnDate.setVisibility(View.GONE);
                break;
            case R.id.rb_plane_return: //往返
                layoutReturnDate.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_plane_inquiry: //询价
                layoutReturnDate.setVisibility(View.GONE);
                break;
        }
    }

    //交换出发城市和目的城市
    private void exchange() {

    }

    //获取机场列表
    private void getInternetData() {
        //飞机出发城市、到达城市
        new Thread(){
            @Override
            public void run() {
                super.run();
                PlanTicketCityFetch fetch = new PlanTicketCityFetch("D"); //国内飞机场
                planeBiz.getPlaneTicketCityInfo(fetch, new BizGenericCallback<ArrayList<PlanTicketCityInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<PlanTicketCityInfo>> model) {
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
                        LoadingIndicator.cancel();
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
                        LoadingIndicator.cancel();
                    }
                });
            }
        }.start();
    }

    //获取机场列表
    private void getInternetDataOut() {
        //飞机出发城市、到达城市
        new Thread(){
            @Override
            public void run() {
                super.run();
                PlanTicketCityFetch fetch = new PlanTicketCityFetch("I"); //国际飞机场
                planeBiz.getPlaneTicketCityInfo(fetch, new BizGenericCallback<ArrayList<PlanTicketCityInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<PlanTicketCityInfo>> model) {
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
                        LoadingIndicator.cancel();
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
                        LoadingIndicator.cancel();
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
