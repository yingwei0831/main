package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneItemInfoListAdapter;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOfChinaChangeBack;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketOfChinaChangeBackRespond;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.List;
import java.util.Locale;

public class PlaneItemInfoBackActivity extends BaseActionBarActivity {

    private String TAG = "PlaneItemInfoActivity";

    private TextView tvFromAirport; //起飞机场
    private TextView tvToAirport;   //降落机场

    private TextView tvStartTime;   //起飞时间
    private TextView tvArrivalTime; //降落时间

    private TextView tvStartDate;   //起飞日期
    private TextView tvArrivalDate; //降落日期

    private TextView tvTimeConsuming; //耗时
    private TextView tvPlaneInfo;   //飞机信息，餐饮

    private PullToRefreshListView pullListView;
    private ListView listView;
    private List<PlaneTicketInfoOfChina.SeatItemInfo> list; //舱位信息
    private PlaneItemInfoListAdapter adapter;

    private PlaneTicketInfoOfChina.FlightInfo flight; //航班信息 去程
    private int positionSeat;

    private PlaneTicketInfoOfChina.FlightInfo flightBack; //航班信息 返程

    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市
    private String dateFrom; //出发日期
    private String dateReturn; //返程日期
    private String traveltype; //航程类型 OW（单程） RT（往返）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_item_info);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();

        flight = (PlaneTicketInfoOfChina.FlightInfo) bundle.getSerializable("flight");
        positionSeat = bundle.getInt("positionSeat");

        flightBack = (PlaneTicketInfoOfChina.FlightInfo) bundle.getSerializable("flightBack");

        fromCity = (PlaneTicketCityInfo) bundle.getSerializable("fromCity");
        toCity = (PlaneTicketCityInfo) bundle.getSerializable("toCity");
        dateFrom = bundle.getString("dateFrom");
        traveltype = bundle.getString("traveltype");
        if ("RT".equals(traveltype)){
            dateReturn = bundle.getString("dateReturn");
        }
        list = flightBack.getSeatItems();
    }

    @Override
    protected void setupView() {
        super.setupView();

        tvTitle.setText(String.format("%s—%s", toCity.getName(), fromCity.getName()));

        tvFromAirport = (TextView) findViewById(R.id.tv_plane_start_station);
        tvToAirport = (TextView) findViewById(R.id.tv_plane_end_station);
        tvStartTime = (TextView) findViewById(R.id.tv_plane_start_time);
        tvArrivalTime = (TextView) findViewById(R.id.tv_plane_end_time);
        tvStartDate = (TextView) findViewById(R.id.tv_plane_start_date); //
        tvArrivalDate = (TextView) findViewById(R.id.tv_plane_arrival_time);
        tvTimeConsuming = (TextView) findViewById(R.id.tv_plane_order_time_consuming);
        tvPlaneInfo = (TextView) findViewById(R.id.tv_plane_order_plane_date);

        tvFromAirport.setText(String.format(Locale.getDefault(), "%s%s", toCity.getAirportname(), "--".equals(flightBack.orgJetquay)?"":flightBack.orgJetquay));
        tvToAirport.setText(String.format(Locale.getDefault(), "%s%s", fromCity.getAirportname(), "--".equals(flightBack.dstJetquay)?"":flightBack.dstJetquay));
        LogUtil.e(TAG, "flight.orgJetquay = " + flightBack.orgJetquay +", flight.dstJetquay = " + flightBack.dstJetquay);
        tvStartTime.setText(String.format("%s:%s", flightBack.depTime.substring(0, 2), flightBack.depTime.substring(2)));
        tvArrivalTime.setText(String.format("%s:%s", flightBack.arriTime.substring(0, 2), flightBack.arriTime.substring(2)));
        tvStartDate.setText(dateReturn);
        tvArrivalDate.setText(Utils.getDateStrYMDE(flightBack.param1));
        tvTimeConsuming.setText(Utils.getDiffMinute(flightBack.depTime, flightBack.arriTime));
        String info = String.format("%s | %s %s", flightBack.flightNo, flightBack.planeType, flightBack.meal.equals("true")?"有餐饮":"无餐饮");
        SpannableStringBuilder sb = new SpannableStringBuilder(info);
        ForegroundColorSpan mealSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorMeal));
        sb.setSpan(mealSpan, info.lastIndexOf(" ") + 1, info.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvPlaneInfo.setText(sb);

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
                changeBack(position);
                break;
            case R.id.btn_plane_ticket_reserve: //预定
                reserveTicker(position);
                break;
        }
    }

    private void changeBack(int position) {
        LoadingIndicator.show(PlaneItemInfoBackActivity.this, getString(R.string.http_notice));
        PlaneTicketOfChinaChangeBack changeBack = new PlaneTicketOfChinaChangeBack(flightBack.flightNo, flightBack.getSeatItems().get(position).seatCode,
                dateReturn.substring(0, dateReturn.indexOf(" ")), flightBack.param1, flightBack.dstCity);
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        biz.planeTicketOfChinaChangeBack(changeBack, new BizGenericCallback<PlaneTicketOfChinaChangeBackRespond>() {
            @Override
            public void onCompletion(GenericResponseModel<PlaneTicketOfChinaChangeBackRespond> model) {
                if ("0000".equals(model.headModel.res_code)){
                    Intent intent = new Intent(getApplicationContext(), PlaneChangeBackActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("refund", model.body.getReturnX().getModifyAndRefundStipulateList().getRefundStipulate()); //退
                    bundle.putString("change", model.body.getReturnX().getModifyAndRefundStipulateList().getChangeStipulate()); //改qian
                    bundle.putString("notice", model.body.getReturnX().getModifyAndRefundStipulateList().getModifyStipulate());  //注意
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

    /**
     * 预定；如果返程，则进入选择返程
     */
    private void reserveTicker(int position) {
        Intent intent = new Intent(getApplicationContext(), PlaneEditOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("flight", flight); //去程
        bundle.putInt("positionSeat", positionSeat);

        bundle.putSerializable("flightBack", flightBack); //返程
        bundle.putInt("positionSeatBack", position);

        bundle.putString("dateFrom", dateFrom);
        bundle.putSerializable("fromCity", fromCity);
        bundle.putSerializable("toCity", toCity);
        bundle.putString("traveltype", traveltype);
        bundle.putString("dateReturn", dateReturn);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_PLANE_ORDER); //订单页
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
}
