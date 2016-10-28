package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneItemInfoListAdapter;
import com.jhhy.cuiweitourism.adapter.PlaneListAdapter;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlaneItemInfoActivity extends BaseActionBarActivity {

    private TextView tvFromAirport; //起飞机场
    private TextView tvToAirport;   //降落机场

    private TextView tvStartTime;   //起飞时间
    private TextView tvArrivalTime; //降落时间

    private TextView tvStartDate;   //起飞日期
    private TextView tvArrivalDate; //降落日期

    private TextView tvTimeConsuming; //耗时
    private TextView tvPlineInfo;   //飞机信息，餐饮

    private PullToRefreshListView pullListView;
    private ListView listView;
    private List<PlaneTicketInfoOfChina.SeatItemInfo> list;
    private PlaneItemInfoListAdapter adapter;

    private PlaneTicketInfoOfChina.FlightInfo flight; //航班信息
    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市
    private String dateFrom; //出发日期


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_item_info);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        flight = (PlaneTicketInfoOfChina.FlightInfo) bundle.getSerializable("flight");
        fromCity = (PlaneTicketCityInfo) bundle.getSerializable("fromCity");
        toCity = (PlaneTicketCityInfo) bundle.getSerializable("toCity");
        dateFrom = bundle.getString("dateFrom");
        list = flight.getSeatItems();
    }

    @Override
    protected void setupView() {
        super.setupView();

        tvTitle.setText(String.format("%s—%s", fromCity.getName(), toCity.getName()));

        tvFromAirport = (TextView) findViewById(R.id.tv_plane_start_station);
        tvToAirport = (TextView) findViewById(R.id.tv_plane_end_station);
        tvStartTime = (TextView) findViewById(R.id.tv_plane_start_time);
        tvArrivalTime = (TextView) findViewById(R.id.tv_plane_end_time);
        tvStartDate = (TextView) findViewById(R.id.tv_plane_start_date);
        tvArrivalDate = (TextView) findViewById(R.id.tv_plane_arrival_time);
        tvTimeConsuming = (TextView) findViewById(R.id.tv_plane_order_time_consuming);
        tvPlineInfo = (TextView) findViewById(R.id.tv_plane_order_plane_date);

        tvFromAirport.setText(fromCity.getAirportname());
        tvToAirport.setText(toCity.getAirportname());
        tvStartTime.setText(flight.depTime);
        tvArrivalTime.setText(flight.arriTime);
        tvStartDate.setText(dateFrom);
        tvArrivalDate.setText(flight.param1);
        tvTimeConsuming.setText("耗时");
        tvPlineInfo.setText(String.format("%s | %s %s", flight.flightNo, flight.planeType, flight.meal.equals("true")?"有餐饮":"无餐饮"));

        pullListView = (PullToRefreshListView) findViewById(R.id.list_plane_detail);
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

        adapter = new PlaneItemInfoListAdapter(getApplicationContext(), list) {
            @Override
            public void onItemTextViewClick(int position, View textView, int id) {
                itemViewClick(position, id);
            }
        };
        listView.setAdapter(adapter);

    }

    //退改签说明，预定
    /**
     * @param position 位置
     * @param id 控件Id
     */
    private void itemViewClick(int position, int id) {
        switch (id){
            case R.id.tv_plane_ticket_refund: //退改签说明

                break;
            case R.id.btn_plane_ticket_reserve: //预定

                break;
        }
    }

    @Override
    protected void addListener() {
        super.addListener();

    }
}
