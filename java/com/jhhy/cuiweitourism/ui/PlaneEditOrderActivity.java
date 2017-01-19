package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import com.jhhy.cuiweitourism.dialog.TourismCoinActivity;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneOrderOfChinaRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOfChinaChangeBack;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneOrderOfChinaResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
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

public class PlaneEditOrderActivity extends AppCompatActivity implements View.OnClickListener, OnItemTextViewClick, CompoundButton.OnCheckedChangeListener {

    private String TAG = "PlaneEditOrderActivity";

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
    private PlaneTicketInfoOfChina.FlightInfo flight; //航班信息
    private PlaneTicketInfoOfChina.SeatItemInfo seatInfo; //选择的座位信息

    private String traveltype; //航程类型 OW（单程） RT（往返）
    private String dateReturn; //返程日期
    private PlaneTicketInfoOfChina.FlightInfo flightBack; //返程航班
    private PlaneTicketInfoOfChina.SeatItemInfo seatInfoBack; //返程舱位座位

    private int priceDelayCost; //航班延误险价格
    private int priceAccidentCost; //航空意外险价格

    private TextView tvSelectIcon; //选择旅游币
    private TextView tvPayIcon; //旅游币个数
    private TextView tvTotalPrice; //商品总金额
    private int icon; //积分支付
//    private float totalPrice; //订单总金额

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
        setContentView(R.layout.activity_plane_edit_order);

        getData();
        setupView();
        addListener();
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
        TextView tvStartDate = (TextView) findViewById(R.id.tv_plane_start_date); //起飞日期
        TextView tvArrivalDate = (TextView) findViewById(R.id.tv_plane_arrival_time); //到达日期
        TextView tvTimeConsuming = (TextView) findViewById(R.id.tv_plane_order_time_consuming); //耗时
        TextView tvPlaneInfo = (TextView) findViewById(R.id.tv_plane_order_plane_date); //航班信息

        TextView tvTicketPrice = (TextView) findViewById(R.id.tv_plane_seat_price);
        TextView tvConstructionFuel = (TextView) findViewById(R.id.tv_plane_construction_fuel);
        tvRefundNotice = (TextView) findViewById(R.id.tv_plane_refund_notice);

        tvSelectorContacts = (TextView) findViewById(R.id.tv_plane_add_passenger); //添加乘客
        listViewContacts = (MyListView) findViewById(R.id.list_plane_contacts); //装载乘机人

        etLinkName = (EditText) findViewById(R.id.et_plane_order_link_name); //联系人
        etLinkMobile = (EditText) findViewById(R.id.et_plane_link_mobile); //联系电话

        cbDelayCost     = (CheckBox) findViewById(R.id.rb_plane_order_delay_insurance);
        cbAccidentCost  = (CheckBox) findViewById(R.id.rb_plane_order_accident_insurance);

        tvSelectIcon = (TextView) findViewById(R.id.tv_travel_edit_order_icon); //选择旅游币
        tvPayIcon = (TextView) findViewById(R.id.tv_inner_travel_total_price_icon); //旅游币个数
        tvTotalPrice = (TextView) findViewById(R.id.tv_inner_travel_currency_price); //商品总金额
        tvPriceTotal = (TextView) findViewById(R.id.tv_edit_order_price); //订单总金额
        tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f",
                listContact.size() * (
                        Float.parseFloat(seatInfo.settlePrice) + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax))));
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f",
                listContact.size() * (
                        Float.parseFloat(seatInfo.settlePrice) + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax)) - icon));
        ivArrowTop = (ImageView) findViewById(R.id.iv_edit_order_arrow_top); //点击查看订单金额详情
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay); //去往立即支付

        adapter = new OrderEditContactsAdapter(getApplicationContext(), listContact, this);
        listViewContacts.setAdapter(adapter);

        tvFromAirport.setText(String.format(Locale.getDefault(), "%s%s", fromCity.getAirportname(), "--".equals(flight.orgJetquay)?"":flight.orgJetquay));
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
        tvPlaneInfo.setText(sb);

        tvTicketPrice.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(seatInfo.settlePrice))); //parPrice,settlePrice
        tvConstructionFuel.setText(String.format("￥%s/￥%s", flight.airportTax, flight.fuelTax)); //机建燃油费

        planeBiz = new PlaneTicketActionBiz();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            flight = (PlaneTicketInfoOfChina.FlightInfo) bundle.getSerializable("flight");
            fromCity = (PlaneTicketCityInfo) bundle.getSerializable("fromCity");
            toCity = (PlaneTicketCityInfo) bundle.getSerializable("toCity");
            dateFrom = bundle.getString("dateFrom");
            int positionSeat = bundle.getInt("positionSeat");
            seatInfo = flight.getSeatItems().get(positionSeat);

            traveltype = bundle.getString("traveltype");
            if ("RT".equals(traveltype)){
                dateReturn = bundle.getString("dateReturn");
                flightBack = (PlaneTicketInfoOfChina.FlightInfo) bundle.getSerializable("flightBack");
                int positionSeatBack = bundle.getInt("positionSeatBack");
                seatInfoBack = flightBack.getSeatItems().get(positionSeatBack);
            }
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
        tvSelectIcon.setOnClickListener(this);
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
            case R.id.tv_travel_edit_order_icon: //TODO 选择旅游币
                selectIcon();
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

    /**
     * 选择旅游币
     */
    private void selectIcon() {
        if (MainActivity.logged) {
            if (listContact.size() > 0) {
                float a = listContact.size() * (
                        Float.parseFloat(seatInfo.settlePrice) + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax));
                Intent intent = new Intent(getApplicationContext(), TourismCoinActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("needScore", String.format(Locale.getDefault(), "%d", (int) a - 1)); //本次订单可以用的最多旅游币
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_COIN);
            }else{
                ToastCommon.toastShortShow(getApplicationContext(), null, "请先添加乘机人");
            }
        }else{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("type", 2);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
    }


    private PopupWindow popupWindow;
    private int popUpHeight;
    private int[] location;

    //订单金额详情弹窗
    private void viewOrderDetail() {
        if (popupWindow == null) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_plane_order_price_detail, null);
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
            setupPopView(view);
        }
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            popupWindow.showAtLocation(ivArrowTop, Gravity.NO_GRAVITY, 0, location[1] - popUpHeight);
        }
        popShow();
    }

    private void popShow() {
        tvPopPlaneInfo.setText(String.format(Locale.getDefault(), "%s (%s)", flight.flightNo, seatInfo.getSeatMsg()));
        tvPopPriceAdult.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(seatInfo.settlePrice)));
        tvPopNumberAdult.setText(String.format(Locale.getDefault(), "x %d人", listContact.size()));
        tvPopPriceAdditional.setText(String.format(Locale.getDefault(), "￥%d", Integer.parseInt(flight.airportTax) + Integer.parseInt(flight.fuelTax)));
        tvPopNumberAdditional.setText(String.format(Locale.getDefault(), "x %d人", listContact.size()));
        tvPopPriceEnsurance.setText(String.format(Locale.getDefault(), "￥%d", (priceAccidentCost + priceDelayCost) * listContact.size()));
        tvPopNumberEnsurance.setText(String.format(Locale.getDefault(), "x %d份", listContact.size()));
        tvPopNumberPassenger.setText(String.format(Locale.getDefault(), "x %d", listContact.size()));
    }

    private TextView tvPopPlaneInfo; //飞机信息
    private TextView tvPopPriceAdult; //成人价格
    private TextView tvPopNumberAdult; //成人数量
    private TextView tvPopPriceAdditional; //机建燃油费
    private TextView tvPopNumberAdditional; //机建燃油费数量
    private TextView tvPopPriceEnsurance; //保险价格
    private TextView tvPopNumberEnsurance; //保险数量
    private TextView tvPopNumberPassenger; //乘机人数

    private void setupPopView(View view) {
        tvPopPlaneInfo = (TextView) view.findViewById(R.id.tv_plane_info);
        tvPopPriceAdult = (TextView) view.findViewById(R.id.tv_plane_price_adult);
        tvPopNumberAdult = (TextView) view.findViewById(R.id.tv_plane_number_adult);
        tvPopPriceAdditional = (TextView) view.findViewById(R.id.tv_plane_price_additional);
        tvPopNumberAdditional = (TextView) view.findViewById(R.id.tv_plane_number_additional);
        tvPopPriceEnsurance = (TextView) view.findViewById(R.id.tv_plane_price_insurance);
        tvPopNumberEnsurance = (TextView) view.findViewById(R.id.tv_plane_number_insurance);
        tvPopNumberPassenger = (TextView) view.findViewById(R.id.tv_plane_number_passenger);
    }

    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择常用联系人
                boolean doubleContact = false;
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
                if (listSelection != null && listSelection.size() != 0) {
                    for (UserContacts contact : listSelection) {
                        //判断联系人是否在选，再加入联系人列表
                        if (listContact.size() != 0){
                            for (int j = 0; j < listContact.size(); j++){
                                TrainTicketOrderFetch.TicketInfo contactSelect = listContact.get(j);
                                if (contactSelect.getPsgName().equals(contact.getContactsName())){ //如果存在姓名，则跳出循环，如果循环执行完毕，则执行添加
                                    doubleContact = true;
                                    break;
                                }
                            }
                            if (!doubleContact){
                                TrainTicketOrderFetch.TicketInfo contactTrain = new TrainTicketOrderFetch.TicketInfo(
                                        contact.getContactsName(), "2", contact.getContactsIdCard(), "1", seatInfo.seatCode, seatInfo.settlePrice);
                                listContact.add(contactTrain);
                            }
                        }else{
                            TrainTicketOrderFetch.TicketInfo contactTrain = new TrainTicketOrderFetch.TicketInfo(
                                    contact.getContactsName(), "2", contact.getContactsIdCard(), "1", seatInfo.seatCode, seatInfo.settlePrice);
                            listContact.add(contactTrain);
                        }
                    }
                    adapter.setData(listContact);
                    adapter.notifyDataSetChanged();
                }
                int acPrice = 0, dcPrice = 0;
                if (cbAccidentCost.isChecked()){
                    acPrice = priceAccidentCost * listContact.size();
                }
                if (cbDelayCost.isChecked()){
                    dcPrice = priceDelayCost * listContact.size();
                }
                tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f",
                        listContact.size() * (
                                Float.parseFloat(seatInfo.settlePrice) + acPrice + dcPrice + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax)) - icon)); //订单总金额
                tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f",
                        listContact.size() * (
                                Float.parseFloat(seatInfo.settlePrice) + acPrice + dcPrice + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax)))); //商品金额
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
            }else if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_COIN){ //选择旅游币
                Bundle bundle = data.getExtras();
                icon = bundle.getInt("score");
                tvSelectIcon.setText(String.format(Locale.getDefault(), "%s个", icon));
                tvPayIcon.setText(String.valueOf(icon));
                int acPrice = 0, dcPrice = 0;
                if (cbAccidentCost.isChecked()){
                    acPrice = priceAccidentCost * listContact.size();
                }
                if (cbDelayCost.isChecked()){
                    dcPrice = priceDelayCost * listContact.size();
                }
                tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f",
                        listContact.size() * (
                                Float.parseFloat(seatInfo.settlePrice) + acPrice + dcPrice + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax)) - icon)); //订单总金额
                tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f",
                        listContact.size() * (
                                Float.parseFloat(seatInfo.settlePrice) + acPrice + dcPrice + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax)))); //商品金额
            }
        }else{
            if (requestCode == REQUEST_LOGIN) { //登录
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        }
    }

    private ArrayList<TrainTicketOrderFetch.TicketInfo> listContact = new ArrayList<>(); //乘车人列表

    @Override
    public void onItemTextViewClick(int position, View view, int id) {
        switch (view.getId()){
            case R.id.iv_train_trash: //删除
                listContact.remove(position);
                adapter.setData(listContact);
                adapter.notifyDataSetChanged();

                break;
            case R.id.iv_contact_view_detail: //详情/更换联系人
//                ToastUtil.show(getApplicationContext(), "详情");
                break;

        }

        int acPrice = 0, dcPrice = 0;
        if (cbAccidentCost.isChecked()){
            acPrice = priceAccidentCost * listContact.size();
        }
        if (cbDelayCost.isChecked()){
            dcPrice = priceDelayCost * listContact.size();
        }
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f",
                listContact.size() * (
                        Float.parseFloat(seatInfo.settlePrice) + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax) + acPrice + dcPrice) - icon)); //订单金额
        tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f",
                listContact.size() * (
                        Float.parseFloat(seatInfo.settlePrice) + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax) + acPrice + dcPrice))); //商品金额
    }

    /**
     * 去支付页面
     */
    private void goToPay() {
        LoadingIndicator.show(PlaneEditOrderActivity.this, getString(R.string.http_notice));
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

        LoadingIndicator.show(PlaneEditOrderActivity.this, getString(R.string.http_notice));
        //国内机票，提交订单，进入支付页面
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<PlaneOrderOfChinaRequest.PassengersBean> passengers = new ArrayList<>();
                for (int i = 0; i < listContact.size(); i++) {
                    TrainTicketOrderFetch.TicketInfo contact = listContact.get(i);
                    String pType = "6";
                    if ("1".equals(contact.CardType) || "2".equals(contact.CardType)){
                        pType = "1";
                    }else if ("4".equals(contact.CardType)){
                        pType = "5";
                    }else if ("5".equals(contact.CardType)){
                        pType = "2";
                    } else { // if ("3".equals(contact.CardType)){
                        pType = "6";
                    }
                    PlaneOrderOfChinaRequest.PassengersBean passengersBean = new PlaneOrderOfChinaRequest.PassengersBean("", contact.CardNo, pType, contact.PsgName, "0", "");
                    passengers.add(passengersBean);
                }
                List<PlaneOrderOfChinaRequest.SegmentsBean> segments = new ArrayList<>();
                PlaneOrderOfChinaRequest.SegmentsBean segmentsBean = new PlaneOrderOfChinaRequest.SegmentsBean(flight.dstCity, flight.arriTime, flight.orgCity, dateFrom.substring(0, dateFrom.indexOf(" ")), flight.depTime, flight.flightNo, flight.planeType, seatInfo.seatCode);
                segments.add(segmentsBean);

                PlaneOrderOfChinaRequest.PnrInfoBean pnrInfoBean = new PlaneOrderOfChinaRequest.PnrInfoBean(flight.airportTax, flight.fuelTax, seatInfo.parPrice, passengers, segments);
                PlaneOrderOfChinaRequest request = new PlaneOrderOfChinaRequest(
                        seatInfo.policyData.policyId, name, mobile, pnrInfoBean, MainActivity.user.getUserId(),
                        seatInfo.policyData.commisionPoint, seatInfo.policyData.commisionMoney, seatInfo.settlePrice, String.valueOf(icon));
                planeBiz.planeTicketOrderOfChina(request, new BizGenericCallback<PlaneOrderOfChinaResponse>() {
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
                });
            }
        }.start();
    }

    private void changeBack() {
        LoadingIndicator.show(PlaneEditOrderActivity.this, getString(R.string.http_notice));
        PlaneTicketOfChinaChangeBack changeBack = new PlaneTicketOfChinaChangeBack(
                flight.flightNo, seatInfo.seatCode, dateFrom.substring(0, dateFrom.indexOf(" ")), flight.param1, flight.dstCity);
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
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", ((Float.parseFloat(seatInfo.settlePrice) + Float.parseFloat(flight.airportTax) + Float.parseFloat(flight.fuelTax)) * listContact.size() + acPrice + dcPrice)));
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
        tvPopPriceEnsurance = null;
        tvPopNumberEnsurance = null;
        tvPopNumberPassenger = null;
    }
}
