package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrderEditContactsAdapter;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneOrderOfChinaRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInternationalChangeBack;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInternationalPolicyCheckRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOfChinaChangeBack;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOrderInternational;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneOrderOfChinaResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalChangeBackRespond;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalPolicyCheckResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketOfChinaChangeBackRespond;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaneEditOrderInternationalActivity extends AppCompatActivity implements View.OnClickListener, OnItemTextViewClick, CompoundButton.OnCheckedChangeListener {

    private String TAG = "PlaneEditOrderInternationalActivity";

    private TextView tvTitleLeft;
    private ActionBar actionBar;

    private TextView tvRefundNotice; //退改签说明

    private TextView tvSelectorContacts; //添加乘客
    private MyListView listViewContacts; //联系人装载布局
    private OrderEditContactsAdapter adapter;

    private EditText etLinkName; //联系人
    private EditText etLinkMobile; //联系电话

    private CheckBox cbDelayCost; //航班延误险
    private CheckBox cbAccidentCost; //航空意外险

    private TextView tvPriceTotal; //订单金额
    private ImageView ivArrowTop; //订单金额详情
    private Button btnPay; //立即支付
    private PlaneTicketActionBiz planeBiz;

    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市
    private String dateFrom; //出发日期
    private PlaneTicketInternationalInfo.PlaneTicketInternationalF  flight; //航班信息
    private PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin cabin;


    private TextView tvTransferFromAirport; //中转起飞机场
    private TextView tvTransferToAirport; //中转降落机场
    private TextView tvSecondFlightConsumingTime; //第二趟航班耗时
    private TextView tvSecondFlightCabin; //第二趟航班舱位类型
    private TextView tvSecondFlightFromTime; //第二趟航班起飞时间
    private TextView tvSecondFlightToTime; //第二趟航班到达时间

//    private PlaneTicketInfoOfChina.SeatItemInfo seatInfo; //选择的座位信息

    private int priceDelayCost; //航班延误险价格
    private int priceAccidentCost; //航空意外险价格

    private String travelType; //形成类型：往返，单程

    private PlaneTicketInternationalPolicyCheckResponse checkResponse; //验价返回数据
    private PlaneOrderOfChinaResponse info;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    break;
                case -2:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "提交订单出错，请重试");
                    break;
                case 1: //订单生成成功，进入支付页面
                    Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", info);
                    bundle.putInt("type",15);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_PAY); //订单生成成功，去支付
                    break;
            }
            LoadingIndicator.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
        }
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_simple, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影
        setContentView(R.layout.activity_plane_edit_order_international);

        getData();
        checkData();
        setupView();
        addListener();
    }

    /**
     * 国际机票验价
     */
    private void checkData() {
        List<List<PlaneTicketInternationalPolicyCheckRequest.IFlight>> interFlights = new ArrayList<>();
        PlaneTicketActionBiz planeBiz = new PlaneTicketActionBiz();

        List<PlaneTicketInternationalPolicyCheckRequest.IFlight> iFlightsSingle = new ArrayList<>(); //单程
        ArrayList<PlaneTicketInternationalInfo.FlightInfo> iFlightS1 = flight.S1.flightInfos;
        for (int i = 0; i < iFlightS1.size(); i++){
            PlaneTicketInternationalInfo.FlightInfo flightInfo = iFlightS1.get(i);
            PlaneTicketInternationalPolicyCheckRequest.IFlight iFlight = new PlaneTicketInternationalPolicyCheckRequest.IFlight(String.valueOf(i), "S1",
                    cabin.passengerType.mainCarrierCheck, flightInfo.toDateCheck, flightInfo.toTimeCheck, flightInfo.fromAirportCodeCheck, flightInfo.toAirportCodeCheck, flightInfo.airlineCompanyCheck,
                    cabin.passengerType.airportCabinCode, cabin.passengerType.airportCabinType, flightInfo.toDateCheck, flightInfo.toTimeCheck, flightInfo.flightNumberCheck, flightInfo.toAirportCodeCheck, flightInfo.toTermianl);
            iFlightsSingle.add(iFlight);
        }
        interFlights.add(iFlightsSingle);

        if ("RT".equals(travelType)) {
            List<PlaneTicketInternationalPolicyCheckRequest.IFlight> iFlightsMultiply = new ArrayList<>(); //往返
            ArrayList<PlaneTicketInternationalInfo.FlightInfo> iFlightS2 = flight.S2.flightInfos;
            for (int i = 0; i < iFlightS2.size(); i++) {
                PlaneTicketInternationalInfo.FlightInfo flightInfo = iFlightS2.get(i);
                PlaneTicketInternationalPolicyCheckRequest.IFlight iFlight = new PlaneTicketInternationalPolicyCheckRequest.IFlight(String.valueOf(i), "S1",
                        cabin.passengerType.mainCarrierCheck, flightInfo.toDateCheck, flightInfo.toTimeCheck, flightInfo.fromAirportCodeCheck, flightInfo.toAirportCodeCheck, flightInfo.airlineCompanyCheck,
                        cabin.passengerType.airportCabinCode, cabin.passengerType.airportCabinType, flightInfo.toDateCheck, flightInfo.toTimeCheck, flightInfo.flightNumberCheck, flightInfo.toAirportCodeCheck, flightInfo.toTermianl);
                iFlightsSingle.add(iFlight);
            }
            interFlights.add(iFlightsMultiply);
        }

        PlaneTicketInternationalPolicyCheckRequest request = new PlaneTicketInternationalPolicyCheckRequest(travelType, "1E", "ALL", interFlights);
        planeBiz.planeTicketInternationalPolicyCheck(request, new BizGenericCallback<PlaneTicketInternationalPolicyCheckResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<PlaneTicketInternationalPolicyCheckResponse> model) {
                if ("0001".equals(model.headModel.res_code)){
                    ToastUtil.show(getApplicationContext(), model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    checkResponse = model.body;
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "验价失败，请返回重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }
    private void setupView() {
        TextView tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_simple_title);
        tvTitle.setText(getString(R.string.train_order_edit));
        TextView tvTitleRight = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setVisibility(View.GONE);
        tvTitleLeft = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_simple_title_left);

        TextView tvFromAirport = (TextView) findViewById(R.id.tv_plane_start_station); //起飞机场
        TextView tvToAirport = (TextView) findViewById(R.id.tv_plane_end_station); //到达机场
        TextView tvStartTime = (TextView) findViewById(R.id.tv_plane_start_time); //起飞时间
        TextView tvArrivalTime = (TextView) findViewById(R.id.tv_plane_end_time); //到达时间
//        TextView tvStartDate = (TextView) findViewById(R.id.tv_plane_start_date); //起飞日期
//        TextView tvArrivalDate = (TextView) findViewById(R.id.tv_plane_arrival_time); //到达日期
        TextView tvTimeConsuming = (TextView) findViewById(R.id.tv_plane_order_time_consuming_second); //耗时
        TextView tvCabinLevel = (TextView) findViewById(R.id.tv_plane_order_time_consuming); //舱位类型

        TextView tvPlaneInfo = (TextView) findViewById(R.id.tv_plane_order_plane_date); //航班信息

        LogUtil.e(TAG, "舱位类型 = " + cabin.passengerType.airportCabinType);
        String[] cabinTypes = cabin.passengerType.airportCabinType.split(",");
        StringBuffer sb = new StringBuffer();
        for (String cabinType1 : cabinTypes) {
            sb.append(PlaneListInternationalActivity.info.R.get(cabinType1)).append("|");
        }
        String cabinType =  sb.toString().substring(0, sb.length()-1);
        LogUtil.e(TAG, "中转次数 = " + flight.S1.transferFrequency);
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
        LogUtil.e(TAG, "中转次数 = " + flight.S1.transferFrequency);
        if ("0".equals(flight.S1.transferFrequency)){ //中转次数=0

        } else {
            tvTransferFromAirport = (TextView) findViewById(R.id.tv_plane_start_station_international);
            tvTransferToAirport = (TextView) findViewById(R.id.tv_plane_end_station_international);
            tvSecondFlightConsumingTime = (TextView) findViewById(R.id.tv_plane_order_time_consuming_second_international);
            tvSecondFlightCabin = (TextView) findViewById(R.id.tv_plane_order_time_consuming_international);
            tvSecondFlightFromTime = (TextView) findViewById(R.id.tv_plane_start_time_international);
            tvSecondFlightToTime = (TextView) findViewById(R.id.tv_plane_end_time_international);

            tvTransferFromAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flight.S1.flightInfos.get(1).fromAirportCodeCheck).fullName, flight.S1.flightInfos.get(1).fromTerminal)); //起飞机场，起飞航站楼
            tvTransferToAirport.setText(String.format(Locale.getDefault(), "%s%s",
                    PlaneListInternationalActivity.info.P.get(flight.S1.flightInfos.get(1).toAirportCodeCheck).fullName, flight.S1.flightInfos.get(1).toTermianl)); //到达机场，到达航站楼
            tvSecondFlightConsumingTime.setText(Utils.getDiffMinuteStr(
                    String.format(Locale.getDefault(), "%s %s", flight.S1.flightInfos.get(1).fromDateCheck,  flight.S1.flightInfos.get(1).fromTimeCheck),
                    String.format(Locale.getDefault(), "%s %s", flight.S1.flightInfos.get(1).toDateCheck, flight.S1.flightInfos.get(1).toTimeCheck)));
            LogUtil.e(TAG, "舱位类型（去程/回程）：" + cabin.passengerType.airportCabinType);
            tvSecondFlightCabin.setText(PlaneListInternationalActivity.info.R.get(cabin.passengerType.airportCabinType)); //舱位类型
            tvSecondFlightFromTime.setText(flight.S1.flightInfos.get(1).fromTimeCheck);
            tvSecondFlightToTime.setText(flight.S1.flightInfos.get(1).toTimeCheck);
        }
        PlaneTicketInternationalInfo.AircraftTypeInfo airline = PlaneListInternationalActivity.info.J.get(flight.S1.flightInfos.get(0).flightTypeCheck);
        LogUtil.e(TAG, "飞机类型：  " + airline);
        String info = String.format("%s%s | %s (%s) | %s",
                PlaneListInternationalActivity.info.A.get(flight.S1.flightInfos.get(0).airlineCompanyCheck).shortName, flight.S1.flightInfos.get(0).flightNumberCheck,
                airline.typeName, airline.airframe, Utils.getDateStrYMDE(flight.S1.fromDate));
        tvPlaneInfo.setText(info); //航班信息

        TextView tvTicketPrice = (TextView) findViewById(R.id.tv_plane_seat_price); //票价
        TextView tvTax = (TextView) findViewById(R.id.tv_plane_tax_price); //税费
        TextView tvPrice = (TextView) findViewById(R.id.tv_plane_total_price); //总价
        tvTicketPrice.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(cabin.baseFare.faceValueTotal))); //票价(票面价)
        tvTax.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(cabin.passengerType.taxTypeCodeMap.get("XT").price))); //税费
        tvPrice.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(cabin.totalFare.taxTotal))); //总价（单张票总价）

        tvRefundNotice = (TextView) findViewById(R.id.tv_plane_refund_notice); //退改签说明

        tvSelectorContacts = (TextView) findViewById(R.id.tv_plane_add_passenger); //添加乘客
        listViewContacts = (MyListView) findViewById(R.id.list_plane_contacts); //装载乘机人

        etLinkName = (EditText) findViewById(R.id.et_plane_order_link_name); //联系人
        etLinkMobile = (EditText) findViewById(R.id.et_plane_link_mobile); //联系电话

        cbDelayCost     = (CheckBox) findViewById(R.id.rb_plane_order_delay_insurance);
        cbAccidentCost  = (CheckBox) findViewById(R.id.rb_plane_order_accident_insurance);

        tvPriceTotal = (TextView) findViewById(R.id.tv_edit_order_price); //订单总金额
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", listContact.size() * (Float.parseFloat(cabin.totalFare.taxTotal))));
        ivArrowTop = (ImageView) findViewById(R.id.iv_edit_order_arrow_top); //点击查看订单金额详情
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay); //去往立即支付

        adapter = new OrderEditContactsAdapter(getApplicationContext(), listContact, this);
        adapter.setType(1);
        listViewContacts.setAdapter(adapter);

        /*tvFromAirport.setText(String.format(Locale.getDefault(), "%s%s", fromCity.getAirportname(), "--".equals(flight.orgJetquay)?"":flight.orgJetquay));
        tvToAirport.setText(String.format(Locale.getDefault(), "%s%s", toCity.getAirportname(), "--".equals(flight.dstJetquay)?"":flight.dstJetquay));
        tvStartTime.setText(String.format("%s:%s", flight.depTime.substring(0, 2), flight.depTime.substring(2)));
        tvArrivalTime.setText(String.format("%s:%s", flight.arriTime.substring(0, 2), flight.arriTime.substring(2)));
        tvStartDate.setText(dateFrom);
        tvArrivalDate.setText(Utils.getDateStrYMDE(flight.param1));
        tvTimeConsuming.setText(Utils.getDiffMinute(flight.depTime, flight.arriTime));
        String info = String.format("%s | %s %s", flight.flightNo, flight.planeType, flight.meal.equals("true")?"有餐饮":"无餐饮");
        SpannableStringBuilder sb = new SpannableStringBuilder(info);
        ForegroundColorSpan mealSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorMeal));
        sb.setSpan(mealSpan, info.lastIndexOf(" ") + 1, info.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvPlaneInfo.setText(sb);*/

        planeBiz = new PlaneTicketActionBiz();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            flight = (PlaneTicketInternationalInfo.PlaneTicketInternationalF) bundle.getSerializable("flight");
            fromCity = (PlaneTicketCityInfo) bundle.getSerializable("fromCity");
            toCity = (PlaneTicketCityInfo) bundle.getSerializable("toCity");
            dateFrom = bundle.getString("dateFrom");
            cabin = (PlaneTicketInternationalInfo.PlaneTicketInternationalHFCabin) bundle.getSerializable("cabin");
            travelType = bundle.getString("travelType");
        }
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvRefundNotice.setOnClickListener(this); //退改签说明
        tvSelectorContacts.setOnClickListener(this); //选择联系人
        ivArrowTop.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        cbAccidentCost.setOnCheckedChangeListener(this);
        cbDelayCost.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_left:
                finish();
                break;
            case R.id.tv_plane_refund_notice: //退改签说明
                changeBack();
                break;
            case R.id.tv_plane_add_passenger: //添加乘客
                if (MainActivity.logged) {
                    Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 13);
                    if (listContact.size() == 0) {
                        bundle.putInt("number", 10);
                    } else {
                        bundle.putInt("number", 10 - listContact.size());
                    }
                    intent.putExtras(bundle);
                    startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
                }else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 2);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
                break;
            case R.id.iv_edit_order_arrow_top: //订单金额详情
                viewOrderDetail();
                break;
            case R.id.btn_edit_order_pay: //立即支付
                goToPay();
                break;
        }
    }

    private PopupWindow popupWindow;
    private int popUpHeight;
    private int[] location;

    //订单金额详情弹窗
    private void viewOrderDetail() {
        if (popupWindow == null) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_plane_order_price_detail, null);
            setupPopView(view);
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popUpHeight = view.getMeasuredHeight();
            location = new int[2];
            // 允许点击外部消失
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            // 获得位置
            ivArrowTop.getLocationOnScreen(location);
        }
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            popupWindow.showAtLocation(ivArrowTop, Gravity.NO_GRAVITY, 0, location[1] - popUpHeight);
        }
        popShow();
    }

    private void popShow() {
        tvPopPlaneInfo.setText(String.format(Locale.getDefault(), "%s%s (%s)",
                PlaneListInternationalActivity.info.A.get(flight.S1.flightInfos.get(0).airlineCompanyCheck).shortName,
                flight.S1.flightInfos.get(0).flightNumberCheck,
                PlaneListInternationalActivity.info.R.get(cabin.passengerType.airportCabinType)));

        tvPopPriceAdult.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(cabin.baseFare.faceValueTotal)));
        tvPopNumberAdult.setText(String.format(Locale.getDefault(), "x %d人", listContact.size()));
        tvPopPriceAdditional.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(cabin.passengerType.taxTypeCodeMap.get("XT").price)));
        tvPopNumberAdditional.setText(String.format(Locale.getDefault(), "x %d人", listContact.size()));
//        tvPopPriceEnsurance.setText(String.format(Locale.getDefault(), "￥%d", (priceAccidentCost + priceDelayCost) * listContact.size()));
//        tvPopNumberEnsurance.setText(String.format(Locale.getDefault(), "x %d份", listContact.size()));
        tvPopNumberPassenger.setText(String.format(Locale.getDefault(), "x %d", listContact.size()));
    }

    private TextView tvPopPlaneInfo; //飞机信息
    private TextView tvPopPriceAdult; //成人价格
    private TextView tvPopNumberAdult; //成人数量
    private TextView tvPopPriceAdditional; //机建燃油费
    private TextView tvPopNumberAdditional; //机建燃油费数量
    private TextView tvPopNumberPassenger; //乘机人数

    private void setupPopView(View view) {
        tvPopPlaneInfo = (TextView) view.findViewById(R.id.tv_plane_info);
        tvPopPriceAdult = (TextView) view.findViewById(R.id.tv_plane_price_adult);
        tvPopNumberAdult = (TextView) view.findViewById(R.id.tv_plane_number_adult);
        TextView tvPopTaxTitle = (TextView) view.findViewById(R.id.tv_tax_title);
        tvPopTaxTitle.setText("税费");
        tvPopPriceAdditional = (TextView) view.findViewById(R.id.tv_plane_price_additional);
        tvPopNumberAdditional = (TextView) view.findViewById(R.id.tv_plane_number_additional);
        TextView tvInsuranceTitle = (TextView) view.findViewById(R.id.tv_plane_price_insurance_title);
        TextView tvPopPriceInsurance = (TextView) view.findViewById(R.id.tv_plane_price_insurance);
        TextView tvPopNumberInsurance = (TextView) view.findViewById(R.id.tv_plane_number_insurance);
        tvInsuranceTitle.setVisibility(View.GONE);
        tvPopPriceInsurance.setVisibility(View.GONE);
        tvPopNumberInsurance.setVisibility(View.GONE);
        tvPopNumberPassenger = (TextView) view.findViewById(R.id.tv_plane_number_passenger);
    }

    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择常用联系人
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
                if (listSelection != null) {
                    for (UserContacts contact : listSelection) {
                        PlaneTicketOrderInternational.PassengersBean contactTrain =
                                new PlaneTicketOrderInternational.PassengersBean(
                                        contact.getContactsName(), "1", "2", contact.getContactsIdCard(), contact.getContactsMobile(), cabin.passengerType.faceValue, cabin.baseFare.faceValueTotal, "", "", "", cabin.passengerType.taxTypeCodeMap.get("XT").price);
                        listContact.add(contactTrain);
                    }
                    adapter.setData(listContact);
                    adapter.notifyDataSetChanged();
                }
                calculateTotalPrice();
            }else if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY){ //去支付，支付成功
                LogUtil.e(TAG, "订单支付成功");
            }else if (requestCode == REQUEST_LOGIN) { //登录成功
                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    MainActivity.logged = true;
                    MainActivity.user = user;
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
            }
        }else{
            if (requestCode == REQUEST_LOGIN) { //登录
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        }
    }

    private void calculateTotalPrice() {
        int acPrice = 0, dcPrice = 0;
        if (cbAccidentCost.isChecked()){
            acPrice = priceAccidentCost * listContact.size();
        }
        if (cbDelayCost.isChecked()){
            dcPrice = priceDelayCost * listContact.size();
        }
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", listContact.size() * (Float.parseFloat(cabin.totalFare.taxTotal) + acPrice + dcPrice)));
    }

    private ArrayList<PlaneTicketOrderInternational.PassengersBean> listContact = new ArrayList<>(); //乘车人列表

    @Override
    public void onItemTextViewClick(int position, View view, int id) {
        switch (view.getId()){
            case R.id.iv_train_trash: //删除
                listContact.remove(position);
                adapter.setData(listContact);
                adapter.notifyDataSetChanged();
                calculateTotalPrice();
                break;
            case R.id.iv_contact_view_detail: //详情
//                ToastUtil.show(getApplicationContext(), "详情");
                break;
        }
    }

    /**
     * 去支付页面
     */
    private void goToPay() {
        LoadingIndicator.show(PlaneEditOrderInternationalActivity.this, getString(R.string.http_notice));
        final String name = etLinkName.getText().toString();
        final String mobile = etLinkMobile.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtil.show(getApplicationContext(), "联系人不能为空");
            etLinkName.requestFocus();
            LoadingIndicator.cancel();
            return;
        }
        if(TextUtils.isEmpty(mobile)){
            ToastUtil.show(getApplicationContext(), "联系人手机号码不能为空");
            etLinkMobile.requestFocus();
            LoadingIndicator.cancel();
            return;
        }else if (mobile.length() != 11){
            ToastUtil.show(getApplicationContext(), "请检查联系人手机号码");
            etLinkMobile.requestFocus();
            return;
        }
        if (listContact.size() == 0){
            ToastUtil.show(getApplicationContext(), "请选择乘车人");
            LoadingIndicator.cancel();
            return;
        }

        LoadingIndicator.show(PlaneEditOrderInternationalActivity.this, getString(R.string.http_notice));
        //国际机票，提交订单，进入支付页面
        new Thread(){
            @Override
            public void run() {
                super.run();

                List<PlaneOrderOfChinaRequest.SegmentsBean> segments = new ArrayList<>();
//                PlaneOrderOfChinaRequest.SegmentsBean segmentsBean = new PlaneOrderOfChinaRequest.SegmentsBean(flight.dstCity, flight.arriTime, flight.orgCity, dateFrom.substring(0, dateFrom.indexOf(" ")), flight.depTime, flight.flightNo, flight.planeType, seatInfo.seatCode);
//                segments.add(segmentsBean);

//                PlaneOrderOfChinaRequest.PnrInfoBean pnrInfoBean = new PlaneOrderOfChinaRequest.PnrInfoBean(flight.airportTax, flight.fuelTax, seatInfo.parPrice, passengers, segments);
//                PlaneTicketOrderInternational request = new PlaneTicketOrderInternational(MainActivity.user.getUserId(), name, mobile, "",
//                        seatInfo.policyData.policyId, name, mobile, pnrInfoBean, MainActivity.user.getUserId(), seatInfo.policyData.commisionPoint, seatInfo.policyData.commisionMoney, seatInfo.settlePrice);
                /*planeBiz.planeTicketOrderInternational(request, new BizGenericCallback<PlaneOrderOfChinaResponse>() {
                    @Override
                    public void onCompletion(GenericResponseModel<PlaneOrderOfChinaResponse> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
                            info = model.body;
                            handler.sendEmptyMessage(1);
                        }
                        LogUtil.e(TAG,"planeTicketOfChinaOrderSubmit: " + model.body.toString());
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
                        LogUtil.e(TAG, "planeTicketOfChinaOrderSubmit: " + error.toString());
                    }
                });*/
            }
        }.start();
    }

    private void changeBack() {
        LoadingIndicator.show(PlaneEditOrderInternationalActivity.this, getString(R.string.http_notice));
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        PlaneTicketInternationalInfo.PassengerType passengerType = cabin.passengerType;
        PlaneTicketInternationalChangeBack.AirRulesRQBean airRulesRQBean =
                new PlaneTicketInternationalChangeBack.AirRulesRQBean(flight.S1.fromDate, flight.S1.fromTime,
                        passengerType.freightBaseCheck, passengerType.releasePriceFlightCompanyCheck, passengerType.fromAirportCheck,
                        passengerType.toAirportCheck, passengerType.changeBackSign);
        List<PlaneTicketInternationalChangeBack.AirRulesRQBean> airRulesRQBeanList = new ArrayList<>();
        airRulesRQBeanList.add(airRulesRQBean);
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.rb_plane_order_delay_insurance:
                cbDelayCost.setChecked(b);
                break;
            case R.id.rb_plane_order_accident_insurance:
                cbAccidentCost.setChecked(b);
                break;
        }
        int acPrice = 0, dcPrice = 0;
        if (cbAccidentCost.isChecked()){
            acPrice = priceAccidentCost * listContact.size();
        }
        if (cbDelayCost.isChecked()){
            dcPrice = priceDelayCost * listContact.size();
        }
//        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", ((Float.parseFloat(seatInfo.parPrice) + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax)) * listContact.size() + acPrice + dcPrice)));
    }

    //预定须知
    private void reserveNotice() {
        ToastCommon.toastShortShow(getApplicationContext(), null, "预定须知");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popupWindow = null;
        tvPopPlaneInfo = null;
        tvPopPriceAdult = null;
        tvPopNumberAdult = null;
        tvPopPriceAdditional = null;
        tvPopNumberAdditional = null;
//        tvPopPriceEnsurance = null;
//        tvPopNumberEnsurance = null;
        tvPopNumberPassenger = null;
    }
}
