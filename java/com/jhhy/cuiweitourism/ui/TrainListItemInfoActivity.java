package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;

public class TrainListItemInfoActivity extends BaseActionBarActivity {

    private TrainTicketDetailInfo detail;
    private String startDate;

    private TextView tvStartTime;       //出发时间
    private TextView tvStartStation;    //出发车站

    private TextView tvConsumingtime;   //耗时

    private TextView tvArrivalTime;     //到达时间
    private TextView tvArrivalStation;  //到达车站

    private TextView tvTrainInfo;       //列车信息
    private TextView tvTicketDate;      //乘车日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_train_list_item_info);
        getData();
        super.onCreate(savedInstanceState);

    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detail = (TrainTicketDetailInfo) bundle.getSerializable("detail");
            startDate = bundle.getString("startDate");
        }
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvStartTime = (TextView) findViewById( R.id.tv_train_start_time);
        tvStartStation = (TextView) findViewById( R.id.tv_train_start_station);
        tvConsumingtime = (TextView) findViewById( R.id.tv_train_order_time_consuming);
        tvArrivalTime = (TextView) findViewById( R.id.tv_train_end_time);
        tvArrivalStation = (TextView) findViewById( R.id.tv_train_end_station);
        tvTrainInfo = (TextView) findViewById( R.id.tv_train_order_train_name);
        tvTicketDate = (TextView) findViewById( R.id.tv_train_order_train_date);

        tvStartTime.setText(detail.departureTime);
        tvStartStation .setText(detail.departureStation);
        tvConsumingtime.setText(detail.duration);
        tvArrivalTime.setText(detail.arrivalTime);
        tvArrivalStation.setText(detail.arrivalStation);
        tvTrainInfo.setText(String.format("%s%s", detail.trainTypeName, detail.trainNum));
        tvTicketDate.setText(startDate);

    }

    @Override
    protected void addListener() {
        super.addListener();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
