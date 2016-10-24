package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainStationFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import cn.jeesoft.ArrayDataDemo;
import cn.jeesoft.OptionsWindowHelper;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;

public class TrainMainActivity extends BaseActionBarActivity {

    private String TAG = TrainMainActivity.class.getSimpleName();
    private View parent;
    private ArrayList<TrainStationInfo> stations; //火车站

    private ImageView ivExchange; //交换出发地和目的地
    private TextView tvFromCity;
    private TextView tvToCity;
    private TextView tvFromDate;
    private TextView tvTrainType;
    private TextView tvSeatType;

    private CharacterPickerWindow window; //车次类型，席别类型

    private Button btnSearch;

    private String selectDate; //选择的出发时间
    private TrainStationInfo fromCtiy; //出发城市
    private TrainStationInfo toCity; //到达城市
    private String typeTrain; //车型
    private String typeSeat; //座位类型

    private String codeTrain; //车次类型代码
    private String codeSeat; //席别类型代码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_train_main);
        super.onCreate(savedInstanceState);
        getInternetData();

    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.tab1_tablelayout_item5));
        parent = findViewById(R.id.view_parent);
        tvFromCity =    (TextView) findViewById(R.id.tv_train_from_city);
        tvToCity =      (TextView) findViewById(R.id.tv_train_to_city);
        tvFromDate =    (TextView) findViewById(R.id.tv_train_from_time);
        tvTrainType =   (TextView) findViewById(R.id.tv_train_type);
        tvSeatType =    (TextView) findViewById(R.id.tv_train_seat_type);
        btnSearch = (Button) findViewById(R.id.btn_train_search);
        ivExchange = (ImageView) findViewById(R.id.iv_train_exchange);
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
    //交换出发地和目的地
    private void exchange() {

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
            if (typeTrain == null || typeSeat == null){
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
        LoadingIndicator.show(TrainMainActivity.this, "请稍后...");
        Intent intent = new Intent(getApplicationContext(), TrainCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stations", stations);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_FROM_CITY);
    }
    //选择到达城市
    private void selectToCity() {
        LoadingIndicator.show(TrainMainActivity.this, "请稍后...");
        Intent intent = new Intent(getApplicationContext(), TrainCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("stations", stations);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_TO_CITY);
    }
    //搜索火车票
    private void search() {
        //TODO 打开另外车票列表页：先打开再搜？
        if (fromCtiy == null || toCity == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择出发城市或到达城市");
            return;
        }
        if (selectDate == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择出发时间");
            return;
        }
        TrainTicketFetch trainTicket = new TrainTicketFetch();
        trainTicket.setFromstation(fromCtiy.getName());
        trainTicket.setArrivestation(toCity.getName());
        trainTicket.setTraveltime(selectDate);
        if (typeTrain != null){
            Iterator it = ArrayDataDemo.TRAIN.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                if (typeTrain.equals(entry.getKey())) {
                    codeTrain = entry.getValue();
//                    trainTicket.setTraintype(codeTrain);
                    trainTicket.setTraintype("");
                    break;
                }
            }
        }
        if (typeSeat != null && typeTrain != null){
            Iterator it = ArrayDataDemo.DATAs.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String, Map<String, String>> entry = (Map.Entry<String, Map<String, String>>) it.next();
                if (typeTrain.equals(entry.getKey())) {
                    Map<String, String> value = entry.getValue();
                    Iterator valueInner = value.entrySet().iterator();
                    while(valueInner.hasNext()){
                        Map.Entry<String, String> entryInner = (Map.Entry<String, String>) valueInner.next();
                        if (typeSeat.equals(entryInner.getKey())) {
                            codeSeat = entryInner.getValue();
                            trainTicket.setTrainseattype(codeSeat);
                            break;
                        }
                    }

                }
            }
        }
        LogUtil.e(TAG, "typeTrain = " + typeTrain + ", codeTrain = " + ", typeSeat = " + typeSeat +", " +", codeSeat = " +codeSeat);
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
                tvFromDate.setText(selectDate);
            }
        }else if (requestCode == SELECT_FROM_CITY){ //选择出发城市
            if (resultCode == RESULT_OK){
                TrainStationInfo city = (TrainStationInfo) data.getExtras().getSerializable("selectCity");
                LogUtil.e(TAG, "selectCity = " + city);
                fromCtiy = city;
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
    private void getInternetData() {
        LoadingIndicator.show(TrainMainActivity.this, getString(R.string.http_notice));
        //火车站
        TrainTicketActionBiz trainBiz = new TrainTicketActionBiz();
        trainBiz.trainStationInfo(new BizGenericCallback<ArrayList<TrainStationInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<TrainStationInfo>> model) {
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    stations = model.body;
                    LogUtil.e(TAG,"trainStationInfo =" + stations.toString());
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求火车站信息出错，请返回重试");
                }
                LogUtil.e(TAG, "trainStationInfo: " + error.toString());
                LoadingIndicator.cancel();
            }
        });

    }



    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, TrainMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
