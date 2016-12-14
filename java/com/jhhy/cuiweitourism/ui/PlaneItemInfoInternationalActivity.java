package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneItemInfoListAdapter;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInternationalChangeBack;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInternationalPolicyCheckRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalChangeBackRespond;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalPolicyCheckResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaneItemInfoInternationalActivity extends BaseActionBarActivity {

    private static final String TAG = "PlaneItemInfoInternationalActivity";
    private TextView tvFromAirport; //起飞机场
    private TextView tvToAirport;   //降落机场

    private TextView tvTransferFromAirport; //中转起飞机场
    private TextView tvTransferToAirport; //中转降落机场
    private TextView tvSecondFlightConsumingTime; //第二趟航班耗时
    private TextView tvSecondFlightCabin; //第二趟航班舱位类型
    private TextView tvSecibdFlightFromTime; //第二趟航班起飞时间
    private TextView tvSecibdFlightToTime; //第二趟航班到达时间

    private TextView tvStartTime;   //起飞时间
    private TextView tvArrivalTime; //降落时间

//    private TextView tvStartDate;   //起飞日期
//    private TextView tvArrivalDate; //降落日期

    private TextView tvTimeConsuming; //耗时
    private TextView tvPlaneInfo;   //飞机信息，餐饮

    private TextView tvCabinLevel; //舱位类型

    private PullToRefreshListView pullListView;
    private ListView listView;
    private List<PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin> list = new ArrayList<>();
    private PlaneItemInfoListAdapter adapter;

    private PlaneTicketInternationalInfo.PlaneTicketInternationalF flight; //航班信息——单程
//    private PlaneTicketInternationalInfo.PlaneTicketInternationalF flightRT; //航班信息——返程
    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市
    private String dateFrom; //出发日期
    private String dateReturn; //返程日期
    private String traveltype; //航程类型 OW（单程） RT（往返）
    private PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin cabin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_item_info_international);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        flight = (PlaneTicketInternationalInfo.PlaneTicketInternationalF) bundle.getSerializable("flight");
        fromCity = (PlaneTicketCityInfo) bundle.getSerializable("fromCity");
        toCity = (PlaneTicketCityInfo) bundle.getSerializable("toCity");
        dateFrom = bundle.getString("dateFrom");
        traveltype = bundle.getString("traveltype");
        if ("RT".equals(traveltype)){
//            flightRT = (PlaneTicketInternationalInfo.PlaneTicketInternationalF) bundle.getSerializable("flight2"); //返程
            dateReturn = bundle.getString("dateReturn");
        }
        cabin = PlaneListInternationalActivity.info.HMap.get(flight.F).cabin;
    }

    @Override
    protected void setupView() {
        super.setupView();

        tvTitle.setText(String.format("%s—%s", fromCity.getName(), toCity.getName()));

        tvFromAirport = (TextView) findViewById(R.id.tv_plane_start_station);
        tvToAirport = (TextView) findViewById(R.id.tv_plane_end_station);
        tvStartTime = (TextView) findViewById(R.id.tv_plane_start_time);
        tvArrivalTime = (TextView) findViewById(R.id.tv_plane_end_time);
//        RelativeLayout layoutDate = (RelativeLayout) findViewById(R.id.layout_plane_date);
//        layoutDate.setVisibility(View.GONE);
//        tvStartDate = (TextView) findViewById(R.id.tv_plane_start_date);
//        tvArrivalDate = (TextView) findViewById(R.id.tv_plane_arrival_time);
//        tvStartDate.setText(dateFrom);
//        tvArrivalDate.setText(Utils.getDateStrYMDE(flight.S1.toDate));

        tvTimeConsuming = (TextView) findViewById(R.id.tv_plane_order_time_consuming_second);
        tvCabinLevel = (TextView) findViewById(R.id.tv_plane_order_time_consuming);
        tvPlaneInfo = (TextView) findViewById(R.id.tv_plane_order_plane_date);
        //转机，返程

        String[] cabinTypes = cabin.passengerType.airportCabinType.split(",");
        StringBuffer sb = new StringBuffer();
        for (String cabinType1 : cabinTypes) {
            sb.append(PlaneListInternationalActivity.info.R.get(cabinType1)).append("|");
        }
        String cabinType =  sb.toString().substring(0, sb.length()-1);
        LogUtil.e(TAG, "中转次数 = " + flight.S1.transferFrequency);
        if ("0".equals(flight.S1.transferFrequency)){ //中转次数=0
            RelativeLayout layoutTransferAirport = (RelativeLayout) findViewById(R.id.layout_transfer_airport); //中转机场
            RelativeLayout layoutTransferTime = (RelativeLayout) findViewById(R.id.layout_transfer_time); //中转起飞到达时间
            layoutTransferAirport.setVisibility(View.GONE);
            layoutTransferTime.setVisibility(View.GONE);

            tvFromAirport.setText(String.format(Locale.getDefault(), "%s%s", fromCity.getAirportname(), flight.S1.fromAirportName));
            tvToAirport.setText(String.format(Locale.getDefault(), "%s%s", toCity.getAirportname(), flight.S1.toAirportName));
            tvStartTime.setText(flight.S1.fromTime);
            tvArrivalTime.setText(flight.S1.toTime);
            tvTimeConsuming.setText(Utils.getDiffMinuteStr(
                    String.format(Locale.getDefault(), "%s %s", flight.S1.fromDate,  flight.S1.fromTime),
                    String.format(Locale.getDefault(), "%s %s", flight.S1.toDate, flight.S1.toTime)));

            tvCabinLevel.setText(cabinType); //TODO 舱位类型
        } else {
            tvFromAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flight.S1.flightInfos.get(0).fromAirportCodeCheck).fullName, flight.S1.flightInfos.get(0).fromTerminal)); //起飞机场，起飞航站楼
            tvToAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flight.S1.flightInfos.get(0).toAirportCodeCheck).fullName, flight.S1.flightInfos.get(0).toTermianl)); //到达机场，到达航站楼
            tvStartTime.setText(flight.S1.flightInfos.get(0).fromTimeCheck);
            tvArrivalTime.setText(flight.S1.flightInfos.get(0).toTimeCheck);
            tvTimeConsuming.setText(Utils.getDiffMinuteStr(
                    String.format(Locale.getDefault(), "%s %s", flight.S1.flightInfos.get(0).fromDateCheck,  flight.S1.flightInfos.get(0).fromTimeCheck),
                    String.format(Locale.getDefault(), "%s %s", flight.S1.flightInfos.get(0).toDateCheck, flight.S1.flightInfos.get(0).toTimeCheck)));

            tvCabinLevel.setText(cabinType); //TODO 舱位类型

            tvTransferFromAirport = (TextView) findViewById(R.id.tv_plane_start_station_international);
            tvTransferToAirport = (TextView) findViewById(R.id.tv_plane_end_station_international);
            tvSecondFlightConsumingTime = (TextView) findViewById(R.id.tv_plane_order_time_consuming_second_international);
            tvSecondFlightCabin = (TextView) findViewById(R.id.tv_plane_order_time_consuming_international);
            tvSecibdFlightFromTime = (TextView) findViewById(R.id.tv_plane_start_time_international);
            tvSecibdFlightToTime = (TextView) findViewById(R.id.tv_plane_end_time_international);

            tvTransferFromAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flight.S1.flightInfos.get(1).fromAirportCodeCheck).fullName, flight.S1.flightInfos.get(1).fromTerminal)); //起飞机场，起飞航站楼
            tvTransferToAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flight.S1.flightInfos.get(1).toAirportCodeCheck).fullName, flight.S1.flightInfos.get(1).toTermianl)); //到达机场，到达航站楼
            tvSecondFlightConsumingTime.setText(Utils.getDiffMinuteStr(
                    String.format(Locale.getDefault(), "%s %s", flight.S1.flightInfos.get(1).fromDateCheck,  flight.S1.flightInfos.get(1).fromTimeCheck),
                    String.format(Locale.getDefault(), "%s %s", flight.S1.flightInfos.get(1).toDateCheck, flight.S1.flightInfos.get(1).toTimeCheck)));
            LogUtil.e(TAG, "舱位类型：" + cabin.passengerType.airportCabinType);
            tvSecondFlightCabin.setText(PlaneListInternationalActivity.info.R.get(cabin.passengerType.airportCabinType)); //舱位类型
            tvSecibdFlightFromTime.setText(flight.S1.flightInfos.get(1).fromTimeCheck);
            tvSecibdFlightToTime.setText(flight.S1.flightInfos.get(1).toTimeCheck);
        }
        PlaneTicketInternationalInfo.AircraftTypeInfo airline = PlaneListInternationalActivity.info.J.get(flight.S1.flightInfos.get(0).flightTypeCheck);
        LogUtil.e(TAG, "key = "+ cabin.passengerType.airportCabinCode +";   "+airline);
        String info = String.format("%s%s | %s (%s) | %s", PlaneListInternationalActivity.info.A.get(flight.S1.flightInfos.get(0).airlineCompanyCheck).shortName, flight.S1.flightInfos.get(0).flightNumberCheck,
                airline.typeName, airline.airframe, Utils.getDateStrYMDE(flight.S1.fromDate));
        tvPlaneInfo.setText(info); //

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

        list.add(cabin);
        adapter = new PlaneItemInfoListAdapter(getApplicationContext(), list) {
            @Override
            public void onItemTextViewClick(int position, View textView, int id) {
                itemViewClick(position, id);
            }
        };
        adapter.setType(1);
        listView.setAdapter(adapter);

    }

    //退改签说明，预定
    /**
     * @param position 位置
     * @param id 控件Id
     */
    private void itemViewClick(int position, int id) {
        switch (id){
            case R.id.tv_plane_ticket_refund: //TODO 退改签说明
                refundTicket();
                break;
            case R.id.btn_plane_ticket_reserve: //TODO 预定
                reserveTicker(position);
                break;
        }
    }

    private void reserveTicker(int position) {
        Intent intent = new Intent(getApplicationContext(), PlaneEditOrderInternationalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("flight", flight);
        bundle.putString("dateFrom", dateFrom);
        bundle.putSerializable("fromCity", fromCity);
        bundle.putSerializable("toCity", toCity);
        bundle.putSerializable("cabin", cabin);
        bundle.putString("travelType", traveltype);
        if ("RT".equals(traveltype)){
            bundle.putString("dateReturn", dateReturn);
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_PLANE_ORDER); //预定当前航班
    }

    private int EDIT_PLANE_ORDER = 9632; //编辑机票订单

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if (requestCode == EDIT_PLANE_ORDER){ //机票订单成功

            }
        }
    }

    @Override
    protected void addListener() {
        super.addListener();

    }

    /**
     * 退改签政策查询
     */
    private void refundTicket() {
        LoadingIndicator.show(PlaneItemInfoInternationalActivity.this, getString(R.string.http_notice));
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        PlaneTicketInternationalInfo.PassengerType passengerType = cabin.passengerType;

        List<PlaneTicketInternationalChangeBack.AirRulesRQBean> airRulesRQBeanList = new ArrayList<>();
        for (int i = 0; i < flight.S1.flightInfos.size(); i++) {
            PlaneTicketInternationalInfo.FlightInfo flightItem = flight.S1.flightInfos.get(i);
            PlaneTicketInternationalChangeBack.AirRulesRQBean airRulesRQBean =
                    new PlaneTicketInternationalChangeBack.AirRulesRQBean(flightItem.fromDateCheck, flightItem.fromTimeCheck,
                            passengerType.freightBaseCheck, passengerType.releasePriceFlightCompanyCheck, passengerType.fromAirportCheck,
                            passengerType.toAirportCheck, passengerType.changeBackSign);

            airRulesRQBeanList.add(airRulesRQBean);
        }
        if ("RT".equals(traveltype)){
            for (int i = 0; i < flight.S1.flightInfos.size(); i++) {
                PlaneTicketInternationalInfo.FlightInfo flightItem = flight.S2.flightInfos.get(i);
                PlaneTicketInternationalChangeBack.AirRulesRQBean airRulesRQBean =
                        new PlaneTicketInternationalChangeBack.AirRulesRQBean(flightItem.fromDateCheck, flightItem.fromTimeCheck,
                                passengerType.freightBaseCheck, passengerType.releasePriceFlightCompanyCheck, passengerType.fromAirportCheck,
                                passengerType.toAirportCheck, passengerType.changeBackSign);

                airRulesRQBeanList.add(airRulesRQBean);
            }
        }

        PlaneTicketInternationalChangeBack request = new PlaneTicketInternationalChangeBack(airRulesRQBeanList);
        biz.planeTicketInternationalPolicyInfo(request, new BizGenericCallback<PlaneTicketInternationalChangeBackRespond>() {
            @Override
            public void onCompletion(GenericResponseModel<PlaneTicketInternationalChangeBackRespond> model) {
                if ("0000".equals(model.headModel.res_code)){
                    Intent intent = new Intent(getApplicationContext(), PlaneChangeBackActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("refund", model.body.getZc()); //退
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastUtil.show(getApplicationContext(), model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "获取退改签政策失败，请重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }
}
