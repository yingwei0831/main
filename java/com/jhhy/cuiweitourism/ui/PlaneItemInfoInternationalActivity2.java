package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneInfoInternationalAdapter;
import com.jhhy.cuiweitourism.adapter.PlaneItemInfoListAdapter;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInternationalChangeBack;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalChangeBackRespond;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaneItemInfoInternationalActivity2 extends BaseActionBarActivity {

    private String TAG = "PlaneItemInfoInternationalActivity2";

    private MyListView listViewS1;
    private List<PlaneTicketInternationalInfo.FlightInfo> listS1 = new ArrayList<>();
    private PlaneInfoInternationalAdapter adapterS1;

    private MyListView listViewS2;
    private List<PlaneTicketInternationalInfo.FlightInfo> listS2 = new ArrayList<>();
    private PlaneInfoInternationalAdapter adapterS2;

    private PlaneTicketInternationalInfo.PlaneTicketInternationalF flight; //航班信息——单程
    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市
    private String dateFrom; //出发日期
    private String dateReturn; //返程日期
    private String traveltype; //航程类型 OW（单程） RT（往返）
    private PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin cabin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_item_info_international_2);
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
            dateReturn = bundle.getString("dateReturn");
        }
        cabin = PlaneListInternationalActivity.info.HMap.get(flight.F).cabin;
    }

    @Override
    protected void setupView() {
        super.setupView();

        tvTitle.setText(String.format("%s—%s", fromCity.getName(), toCity.getName()));

        View footer = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_plane_seat_type, null);
        TextView tvSeat = (TextView) footer.findViewById(R.id.tv_train_ticket_seat); //经济舱
        TextView tvSeatNo = (TextView) footer.findViewById(R.id.tv_seat_number_international); //8张
        TextView tvRefund = (TextView) footer.findViewById(R.id.tv_plane_ticket_refund); //退改签说明
        TextView tvPrice = (TextView) footer.findViewById(R.id.tv_plane_ticket_price); //￥10000
        TextView tvTax = (TextView) footer.findViewById(R.id.tv_tax_price); //税费:￥10000
        Button btnReserve = (Button) footer.findViewById(R.id.btn_plane_ticket_reserve); //预定
        tvTax.setVisibility(View.VISIBLE);

        tvRefund.setOnClickListener(this);
        btnReserve.setOnClickListener(this);

        tvPrice.setText(String.format("￥%s", cabin.baseFare.faceValueTotal)); //票面价
        tvTax.setText(String.format("税费:￥%s", cabin.passengerType.taxTypeCodeMap.get("XT").price)); //税费xxx
        if (Integer.parseInt(cabin.passengerType.cabinCount) >= 9){
            tvSeatNo.setVisibility(View.GONE);
        }else{
            tvSeatNo.setVisibility(View.VISIBLE);
            tvSeatNo.setText(String.format(Locale.getDefault(), "%s张", cabin.passengerType.cabinCount)); //8张
        }

        String[] cabinTypes = cabin.passengerType.airportCabinType.split("/");
        String[] cabinTypeS1 = cabinTypes[0].split(","); //单程
        StringBuffer sb = new StringBuffer();
        for (String cabinType1 : cabinTypeS1) {
            sb.append(" ").append(PlaneListInternationalActivity.info.R.get(cabinType1)).append(" |");
        }
        if ("RT".equals(traveltype)){
            String[] cabinTypeS2 = cabinTypes[1].split(","); //返程
            for (String cabinType1 : cabinTypeS2) {
                sb.append(" ").append(PlaneListInternationalActivity.info.R.get(cabinType1)).append(" |");
            }
        }
        String cabinType =  sb.toString().substring(1, sb.length()-2);
        tvSeat.setText(cabinType); //经济舱

        listViewS1 = (MyListView) findViewById(R.id.list_plane_detail_s1);
        listS1.addAll(flight.S1.flightInfos);
        adapterS1 = new PlaneInfoInternationalAdapter(getApplicationContext(), listS1, cabin);
        adapterS1.setTravelType("");
        listViewS1.setAdapter(adapterS1);

        listViewS2 = (MyListView) findViewById(R.id.list_plane_detail_s2);
        if ("RT".equals(traveltype)){
            listS2.addAll(flight.S2.flightInfos);
            adapterS2 = new PlaneInfoInternationalAdapter(getApplicationContext(), listS2, cabin);
            adapterS2.setTravelType(traveltype);
            listViewS2.setAdapter(adapterS2);
            listViewS2.addFooterView(footer);
        }else{
            listViewS1.addFooterView(footer);
            listViewS2.setVisibility(View.GONE);
        }

        LogUtil.e(TAG, "中转次数 = " + flight.S1.transferFrequency);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_plane_ticket_refund: //退改签说明
                refundTicket();
                break;
            case R.id.btn_plane_ticket_reserve: //预定
                reserveTicker();
                break;
        }
    }

    private void reserveTicker() {
        Intent intent = new Intent(getApplicationContext(), PlaneEditOrderInternationalActivity2.class);
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
        LoadingIndicator.show(PlaneItemInfoInternationalActivity2.this, getString(R.string.http_notice));
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
