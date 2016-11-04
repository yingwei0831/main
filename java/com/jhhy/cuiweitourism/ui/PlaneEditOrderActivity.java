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
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Locale;

public class PlaneEditOrderActivity extends AppCompatActivity implements View.OnClickListener, OnItemTextViewClick, CompoundButton.OnCheckedChangeListener {

    private String TAG = PlaneEditOrderActivity.class.getSimpleName();

    private TextView tvTitleLeft;
    private ActionBar actionBar;

    private TextView tvRefundNotice; //退改签说明

    private TextView tvSelectorContacts; //添加乘客
    private LinearLayout layoutContacts; //联系人装载布局

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

    private int priceDelayCost; //航班延误险价格
    private int priceAccidentCost; //航空意外险价格


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
//                    Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("order", info);
//                    bundle.putInt("type",14);
//                    intent.putExtras(bundle);
//                    startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_PAY); //订单生成成功，去支付
                    break;
            }
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
        layoutContacts = (LinearLayout) findViewById(R.id.layout_plane_contacts); //装载乘机人

        etLinkName = (EditText) findViewById(R.id.et_plane_order_link_name); //联系人
        etLinkMobile = (EditText) findViewById(R.id.et_plane_link_mobile); //联系电话

        cbDelayCost     = (CheckBox) findViewById(R.id.rb_plane_order_delay_insurance);
        cbAccidentCost  = (CheckBox) findViewById(R.id.rb_plane_order_accident_insurance);

        tvPriceTotal = (TextView) findViewById(R.id.tv_edit_order_price); //订单总金额
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", listContact.size() * Float.parseFloat(seatInfo.parPrice)));
        ivArrowTop = (ImageView) findViewById(R.id.iv_edit_order_arrow_top); //点击查看订单金额详情
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay); //去往立即支付

        tvFromAirport.setText(fromCity.getAirportname());
        tvToAirport.setText(toCity.getAirportname());
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

        tvTicketPrice.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(seatInfo.parPrice)));
        tvConstructionFuel.setText(String.format("￥%s/￥%s", flight.airportTax, flight.fuelTax));

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
            case R.id.tv_plane_refund_notice: //TODO 退改签说明
                ToastUtil.show(getApplicationContext(), "退改签说明");
                break;
            case R.id.tv_plane_add_passenger: //添加乘客
                Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 13);
                if (listContact.size() == 0){
                    bundle.putInt("number", 10);
                }else{
                    bundle.putInt("number", 10 - listContact.size());
                }
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
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
        tvPopPriceAdult.setText(String.format(Locale.getDefault(), "￥%.2f", Float.parseFloat(seatInfo.parPrice)));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择常用联系人
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
                if (listSelection != null) {
                    for (UserContacts contact : listSelection) {
                        TrainTicketOrderFetch.TicketInfo contactTrain = new TrainTicketOrderFetch.TicketInfo(
                                contact.getContactsName(), "2", contact.getContactsIdCard(), "1", seatInfo.seatCode, seatInfo.parPrice);
                        View contactView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_train_contact, null);
                        TextView tvName = (TextView) contactView.findViewById(R.id.tv_contact_name);
                        TextView tvCardID = (TextView) contactView.findViewById(R.id.tv_contact_card_id);
                        final ImageView ivTrash = (ImageView) contactView.findViewById(R.id.iv_train_trash);
                        ImageView ivDetail = (ImageView) contactView.findViewById(R.id.iv_contact_view_detail);
                        ivTrash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onItemTextViewClick(listContact.size() - 1, ivTrash, ivTrash.getId());
                            }
                        });
                        ivDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                        tvName.setText(contact.getContactsName());
                        tvCardID.setText(contact.getContactsIdCard());
                        layoutContacts.addView(contactView);
                        listContact.add(contactTrain);
                    }
                }
                int acPrice = 0, dcPrice = 0;
                if (cbAccidentCost.isChecked()){
                    acPrice = priceAccidentCost * listContact.size();
                }
                if (cbDelayCost.isChecked()){
                    dcPrice = priceDelayCost * listContact.size();
                }
                tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", (Float.parseFloat(seatInfo.parPrice) * listContact.size() + acPrice + dcPrice)));
            }else if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY){ //去支付，支付成功
                LogUtil.e(TAG, "订单支付成功");
            }
        }
    }

    private ArrayList<TrainTicketOrderFetch.TicketInfo> listContact = new ArrayList<>(); //乘车人列表

    @Override
    public void onItemTextViewClick(int position, View imageView, int id) {
        listContact.remove(position);
        layoutContacts.removeViewAt(position);
        int acPrice = 0, dcPrice = 0;
        if (cbAccidentCost.isChecked()){
            acPrice = priceAccidentCost * listContact.size();
        }
        if (cbDelayCost.isChecked()){
            dcPrice = priceDelayCost * listContact.size();
        }
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", (Float.parseFloat(seatInfo.parPrice) * listContact.size() + acPrice + dcPrice)));
    }

    /**
     * 去支付页面
     */
    private void goToPay() {
        LoadingIndicator.show(PlaneEditOrderActivity.this, getString(R.string.http_notice));
        String name = etLinkName.getText().toString();
        String mobile = etLinkMobile.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtil.show(getApplicationContext(), "联系人不能为空");
            etLinkName.requestFocus();
            LoadingIndicator.cancel();
            return;
        }
        if(TextUtils.isEmpty(mobile) ){
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

        //提交订单，进入支付页面
//        final TrainTicketOrderFetch ticketOrderFetch = new TrainTicketOrderFetch(
//                MainActivity.user.getUserId(), name, mobile, detail.departureStation, detail.arrivalStation, detail.trainNum,
//                detail.departureDate, detail.departureTime, detail.arrivalDate, detail.arrivalTime, listContact, seatInfo.seatCode, seatInfo.floorPrice);
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                trainBiz.trainTicketOrderSubmit(ticketOrderFetch, new BizGenericCallback<TrainTicketOrderInfo>() {
//                    @Override
//                    public void onCompletion(GenericResponseModel<TrainTicketOrderInfo> model) {
//                        if ("0001".equals(model.headModel.res_code)){
//                            Message msg = new Message();
//                            msg.what = -1;
//                            msg.obj = model.headModel.res_arg;
//                            handler.sendMessage(msg);
//                        }else if ("0000".equals(model.headModel.res_code)){
//                            info = model.body;
//                            LogUtil.e(TAG,"trainTicketOrderSubmit =" + info.toString());
//                            handler.sendEmptyMessage(1);
//                        }
//                        LoadingIndicator.cancel();
//
//                    }
//
//                    @Override
//                    public void onError(FetchError error) {
//                        if (error.localReason != null){
//                            Message msg = new Message();
//                            msg.what = -1;
//                            msg.obj = error.localReason;
//                            handler.sendMessage(msg);
//                        }else{
//                            handler.sendEmptyMessage(-2);
//                        }
//                        LogUtil.e(TAG, "trainTicketOrderSubmit: " + error.toString());
//                        LoadingIndicator.cancel();
//                    }
//                });
//            }
//        }.start();
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
        tvPriceTotal.setText(String.format(Locale.getDefault(), "%.2f", (Float.parseFloat(seatInfo.parPrice) * listContact.size() + acPrice + dcPrice)));
    }

    //预定须知
    private void reserveNotice() {
        ToastCommon.toastShortShow(getApplicationContext(), null, "预定须知");
    }


}
