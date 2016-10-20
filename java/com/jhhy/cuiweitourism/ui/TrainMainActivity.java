package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainStationFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainStationInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

public class TrainMainActivity extends BaseActionBarActivity {

    private String TAG = TrainMainActivity.class.getSimpleName();
    private TrainStationInfo stations; //火车站

    private TextView tvFromCity;
    private TextView tvToCity;
    private TextView tvFromDate;
    private TextView tvTrainType;
    private TextView tvSeatType;

    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_train_main);
        super.onCreate(savedInstanceState);
        getInternetData();
    }



    @Override
    protected void setupView() {
        super.setupView();
        tvFromCity =    (TextView) findViewById(R.id.tv_train_from_city);
        tvToCity =      (TextView) findViewById(R.id.tv_train_to_city);
        tvFromDate =    (TextView) findViewById(R.id.tv_train_from_time);
        tvTrainType =   (TextView) findViewById(R.id.tv_train_type);
        tvSeatType =    (TextView) findViewById(R.id.tv_train_seat_type);
        btnSearch = (Button) findViewById(R.id.btn_train_search);
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
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
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

                break;
            case R.id.tv_train_type: //车次类型

                break;
            case R.id.tv_train_seat_type: //席别类型

                break;
        }
    }

    private void selectFromCity() {

    }

    private void selectToCity() {

    }

    private void search() {

    }

    /**
     * 获取火车站
     */
    private void getInternetData() {
        LoadingIndicator.show(TrainMainActivity.this, getString(R.string.http_notice));
        //火车站
        TrainTicketActionBiz trainBiz = new TrainTicketActionBiz();
        TrainStationFetch stationFetch = new TrainStationFetch();
        trainBiz.trainStationInfo(stationFetch, new BizGenericCallback<TrainStationInfo>() {
            @Override
            public void onCompletion(GenericResponseModel<TrainStationInfo> model) {
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
