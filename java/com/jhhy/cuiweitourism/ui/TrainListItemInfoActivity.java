package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.TrainItemInfoListAdapter;
import com.jhhy.cuiweitourism.adapter.TrainListAdapter;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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

    private PullToRefreshListView pullListView;
    private ListView listView;
    private TrainItemInfoListAdapter adapter;
    private List<TrainTicketDetailInfo.SeatInfo> list = new ArrayList<>();

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
        tvTitle.setText(String.format("%s%s", detail.trainTypeName, detail.trainNum));

        tvStartTime = (TextView) findViewById( R.id.tv_train_start_time);
        tvStartStation = (TextView) findViewById( R.id.tv_train_start_station);
        tvConsumingtime = (TextView) findViewById( R.id.tv_train_order_time_consuming);
        tvArrivalTime = (TextView) findViewById( R.id.tv_train_end_time);
        tvArrivalStation = (TextView) findViewById( R.id.tv_train_end_station);
        tvTrainInfo = (TextView) findViewById( R.id.tv_train_order_train_name);
        tvTicketDate = (TextView) findViewById( R.id.tv_train_order_train_date);

        pullListView = (PullToRefreshListView) findViewById(R.id.list_train_detail);
        //这几个刷新Label的设置
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView = pullListView.getRefreshableView();

        //上拉、下拉监听函数
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        list = detail.seatInfoArray;
        adapter = new TrainItemInfoListAdapter(getApplicationContext(), list){
            @Override
            public void onItemTextViewClick(int position, View button, int id) {
                Intent intent = new Intent(getApplicationContext(), TrainEditOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail", detail);
                bundle.putInt("position", position);
                bundle.putString("startDate", startDate);
                intent.putExtras(bundle);
                startActivityForResult(intent, RESERVE_TICKET); //预定
            }
        };
        listView.setAdapter(adapter);

        tvStartTime.setText(detail.departureTime);
        tvStartStation .setText(detail.departureStation);
        tvConsumingtime.setText(Utils.getDuration(Long.parseLong(detail.duration)));
        tvArrivalTime.setText(detail.arrivalTime);
        tvArrivalStation.setText(detail.arrivalStation);
        tvTrainInfo.setText(String.format("%s%s", detail.trainTypeName, detail.trainNum));
        tvTicketDate.setText(startDate);

    }

    private int RESERVE_TICKET = 8904; //预定

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESERVE_TICKET){
            if (resultCode == RESULT_OK){

            }
        }
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
