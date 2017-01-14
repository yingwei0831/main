package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrderEditContactsAdapter;
import com.jhhy.cuiweitourism.dialog.NumberPickerActivity;
import com.jhhy.cuiweitourism.model.PhoneBean;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowNumberPicker;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;
import com.just.sun.pricecalendar.ToastCommon;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HotelEditOrderActivity extends BaseActionBarActivity implements PopupWindow.OnDismissListener, OnItemTextViewClick {

    private String TAG = HotelEditOrderActivity.class.getSimpleName();
    private View parent;

    private TextView tvHotelName;
    private TextView tvHotelPrepayRules; //酒店预付规则
    private TextView tvRoomType;
    private TextView tvCheckInDate;
    private TextView tvCheckOutInfo;
    private TextView tvRoomPrice;
    private TextView tvRoomNumber;
    private RelativeLayout layoutSelectRoom;
    private int roomNumber = 1;

    private EditText etHotelNotice;
    private EditText etLinkName;
    private EditText etLinkMobile;

    private TextView tvOrderPrice;
    private Button   btnPay;

    private String checkInDate ;
    private String checkOutDate;
    private int    stayDays    ;
    private HotelProvinceResponse.ProvinceBean selectCity;
//    private HotelDetailInfo hotelDetail;
    private HotelDetailResponse hotelDetail;
//    private int position;
    private HotelDetailResponse.HotelProductBean hotelProduct;
    private String totalPrice; //订单总价
    private int number; //一共几个房间
    private String totalPricePre; //总价:这间房在预定期间应该交付的价格，并未计算人数

    private TextView tvSelectorContacts; //添加乘客
    private MyListView listViewContacts; //联系人装载布局
    private OrderEditContactsAdapter adapter;
    private ArrayList<HotelOrderRequest.RoomBean.PassengerBean> listContact = new ArrayList<>(); //乘车人列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_edit_order);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                checkInDate = bundle.getString("checkInDate");
                checkOutDate = bundle.getString("checkOutDate");
                stayDays = bundle.getInt("stayDays");
                selectCity = bundle.getParcelable("selectCity");
                hotelDetail = HotelDetailActivity.hotelDetail;
//                position = bundle.getInt("position");
                hotelProduct = HotelDetailActivity.hotelProduct;
                number = bundle.getInt("number");
                totalPricePre = bundle.getString("totalPrice");
                LogUtil.e(TAG, "checkInDate = " + checkInDate +", checkOutDate = " + checkOutDate +", stayDays = " + stayDays +", selectCity = " + selectCity +", number = " + number);
            }
        }
    }

    @Override
    protected void setupView() {
        super.setupView();
        parent = findViewById(R.id.layout_parent);
        tvTitle.setText(getString(R.string.hotel_order_title));
        if (hotelDetail.getHotel().getPhone() != null && hotelDetail.getHotel().getPhone().length() != 0) {
            ivTitleRight.setImageResource(R.mipmap.icon_telephone_hollow);
            ivTitleRight.setVisibility(View.VISIBLE);
        }
        tvHotelPrepayRules = (TextView) findViewById(R.id.tv_hotel_prepay_rules);
        if (hotelProduct.getPrepayRule() != null && hotelProduct.getPrepayRule().size() != 0) {
            tvHotelPrepayRules.setText(hotelProduct.getPrepayRule().get(0).getDescription());
        }
//        tvTitleRight.setVisibility(View.VISIBLE); //预付规则

        tvHotelName = (TextView) findViewById(R.id.tv_hotel_name);
        tvRoomType = (TextView) findViewById(R.id.tv_room_type);
        tvCheckInDate = (TextView) findViewById(R.id.tv_check_in_date);
        tvCheckOutInfo = (TextView) findViewById(R.id.tv_left_info);
        tvRoomPrice = (TextView) findViewById(R.id.tv_room_price);
        tvRoomNumber = (TextView) findViewById(R.id.tv_room_number);
        layoutSelectRoom = (RelativeLayout) findViewById(R.id.layout_select_room_number);
        etHotelNotice = (EditText) findViewById(R.id.et_hotel_order_notice);
        etLinkName = (EditText) findViewById(R.id.et_hotel_order_link_name);
        etLinkMobile = (EditText) findViewById(R.id.et_hotel_link_mobile);

        tvOrderPrice = (TextView) findViewById(R.id.tv_edit_order_price);
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay);
        btnPay.setText(getString(R.string.plane_flight_inquiry_commit));

//        tvHotelName.setText(hotelDetail.getTitle());
//        tvRoomType.setText(hotelDetail.get);
        tvCheckInDate.setText(checkInDate);
        tvCheckOutInfo.setText(String.format(Locale.getDefault(), "%s 共%d晚", checkOutDate, stayDays));
//        tvRoomPrice.setText(hotelDetail.getRoom().get(position).getPrice());
        tvSelectorContacts = (TextView) findViewById(R.id.tv_plane_add_passenger); //添加乘客
        listViewContacts = (MyListView) findViewById(R.id.list_plane_contacts); //装载乘机人

        adapter = new OrderEditContactsAdapter(getApplicationContext(), listContact, this);
        adapter.setType(3);
        adapter.setCount(number);
        listViewContacts.setAdapter(adapter);
        tvRoomPrice.setText(totalPricePre); //酒店房间单价 hotelProduct.getPrice()
        tvHotelName.setText(hotelDetail.getHotel().getName());
        tvRoomType.setText(hotelProduct.getRoomName());

        calculatePrice();
    }

    @Override
    protected void addListener() {
        super.addListener();
        layoutSelectRoom.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        tvSelectorContacts.setOnClickListener(this);
        ivTitleRight.setOnClickListener(this);

//        listViewContacts.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
//            case R.id.tv_hotel_reserve_rules: //退订规则
//
//                break;
            case R.id.btn_edit_order_pay:
                gotoPay();
                break;
            case R.id.layout_select_room_number:
                Bundle bundle = new Bundle();
                bundle.putInt("value", roomNumber);
                NumberPickerActivity.actionStart(getApplicationContext(), bundle);
//                selectRoomNumber();
                break;
            case R.id.tv_plane_add_passenger: //添加入住人
                selectContact();
                break;
            case R.id.title_main_iv_right_telephone: //
                Utils.contact(getApplicationContext(), hotelDetail.getHotel().getPhone());
                break;
        }
    }

//    private PopupWindowNumberPicker picker;
//    private void selectRoomNumber() {
//        if (picker == null){
//            picker = new PopupWindowNumberPicker(HotelEditOrderActivity.this, parent);
//            picker.setOnDismissListener(this);
//        } else{
//            if (picker.isShowing()){
//                picker.dismiss();
//            } else {
//                picker.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//                picker.refreshView(roomNumber);
//            }
//        }
//    }

    private void gotoPay() {
        String name = etLinkName.getText().toString();
        String mobile = etLinkMobile.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(mobile)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人手机号不能为空");
            return;
        }
        if (listContact.size() != number){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择联系人信息");
            return;
        }

        LoadingIndicator.show(HotelEditOrderActivity.this, getString(R.string.http_notice));
        String earlierCheckInDate = "";
        if (Utils.getCurrentTimeYMD().equals(checkInDate)){
            Calendar c = Calendar.getInstance();
            if (c.get(Calendar.HOUR_OF_DAY) <23 ) {
                earlierCheckInDate = String.format(Locale.getDefault(), "%s %d%s", checkInDate, c.get(Calendar.HOUR_OF_DAY) + 1, ":00:00");
            }else{
                earlierCheckInDate = String.format(Locale.getDefault(), "%s %s", Utils.getTimeStrYMD(System.currentTimeMillis() + 24 * 60 * 60 * 1000), "00:30:00"); //第二天凌晨入住
            }
        }else{
            earlierCheckInDate = String.format(Locale.getDefault(), "%s %s", checkInDate, "08:00:00");
        }
        //"memberid":"11","productaid":"4","productname":"九寨沟喜来登大酒店",
        // "price":"800","usedate":"2016-10-10","dingnum":"1",
        // "linkman":"张三","linktel":"15210656332","linkemail":"","suitid":"5","departdate":"2016-10-20"
//        productaid 酒店id 、suitid 酒店产品id、departdate 离开时间
        //提交酒店订单
        HotelActionBiz hotelBiz = new HotelActionBiz();
        List<HotelOrderRequest.RoomBean> rooms = new ArrayList<>();
        for (int i = 0; i < number; i++){
            HotelOrderRequest.RoomBean room = new HotelOrderRequest.RoomBean();
            room.setRoomNum(String.valueOf(number));
            List<HotelOrderRequest.RoomBean.PassengerBean> listP = new ArrayList<>();
            listP.add(listContact.get(i));
            room.setP(listP);
            rooms.add(room);
        }
        HotelOrderRequest request = new HotelOrderRequest(MainActivity.user.getUserId(),
                HotelDetailActivity.hotelDetail.getHotel().getHotelID(), HotelDetailActivity.hotelDetail.getHotel().getName(),
                HotelDetailActivity.hotelDetail.getHotel().getTraffic(), HotelDetailActivity.hotelDetail.getHotel().getCityCode(), HotelDetailActivity.hotelDetail.getHotel().getCityName(),
                hotelProduct.getRoomTypeID(), hotelProduct.getRoomName(), hotelProduct.getProductID(), hotelProduct.getName(),
                checkInDate, checkOutDate, "Chinese", "Prepay", String.valueOf(number), String.valueOf(listContact.size()), earlierCheckInDate, String.format(Locale.getDefault(), "%s %s", checkInDate, "23:59:00"), //Chinese，Prepay
                "RMB", totalPrice, getMyIP(), "FALSE", "NotAllowedConfirm", etHotelNotice.getText().toString(), etHotelNotice.getText().toString(), //totalPrice 单价/总价
                "false", //发票
                new HotelOrderRequest.ContactBean(name, mobile), hotelProduct.getPrice(), hotelProduct.getPrice(), hotelProduct.getMeals(), hotelProduct.getPrice(), //basePrice
                hotelProduct.getPlanType(), "2", "0", hotelProduct.getRoomImgUrl());

        request.setRooms(rooms);
        hotelBiz.setHotelOrderToCuiwei(request, new BizGenericCallback<HotelOrderResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelOrderResponse> model) {
                if ("0000".equals(model.headModel.res_code)){
                    HotelOrderResponse orderResponse = model.body;
                    ToastCommon.toastShortShow(getApplicationContext(), null, "提交订单成功");
                    LogUtil.e(TAG,"setHotelOrder =" + orderResponse.toString());
                    hotelOrderPay(orderResponse);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "提交订单信息出错，请重试");
                }
                LogUtil.e(TAG, "setHotelOrder: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    /**
     * 酒店订单支付
     */
    private void hotelOrderPay(HotelOrderResponse order) {
        Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        bundle.putInt("type", 21);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_PAY_HOTEL_ORDER);
    }

    private int REQUEST_PAY_HOTEL_ORDER = 6593; //酒店订单支付
    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PAY_HOTEL_ORDER){
            if (resultCode == RESULT_OK){ //酒店支付成功
                setResult(RESULT_OK);
                finish();
            }
        }else if (requestCode == REQUEST_LOGIN) { //登录成功
            if (resultCode == RESULT_OK) {
                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    MainActivity.logged = true;
                    MainActivity.user = user;
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
            }else{
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        }else if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择联系人
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
                if (listSelection != null) {
                    for (UserContacts contact : listSelection) {
                        HotelOrderRequest.RoomBean.PassengerBean contactTrain = new HotelOrderRequest.RoomBean.PassengerBean(contact.getContactsName(), contact.getContactsMobile());
                        listContact.add(contactTrain);
                    }
                    adapter.setData(listContact);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, HotelEditOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    public void onDismiss() {
        //TODO 弹框消失事件

    }

    /**
     * @param position 点击的联系人位置
     * @param textView 控件
     * @param id       textView 的id
     */
    @Override
    public void onItemTextViewClick(int position, View textView, int id) {
        LogUtil.e(TAG, "-------------onItemTextViewClick-----------");
        switch (textView.getId()){
            case R.id.tv_contact_name: //姓名
            case R.id.iv_contact_view_detail: //右箭头
                //选择联系人
                selectContact();
                break;
            case R.id.iv_train_trash: //左侧删除符号
                //删除联系人
                if (position < listContact.size()) {
                    listContact.remove(position);
                    adapter.notifyDataSetChanged();
                    calculatePrice();
                }
                break;
        }
    }

    /**
     * 计算价格
     */
    private void calculatePrice() {
        totalPrice = String.format(Locale.getDefault(), "%.2f", Float.parseFloat(totalPricePre) * number);
        tvOrderPrice.setText(totalPrice); //订单总价
    }

    /**
     * 选择联系人
     */
    private void selectContact() {
        if (MainActivity.logged) {
            if (listContact.size() == number){
                ToastCommon.toastShortShow(getApplicationContext(), null, "最多选择" + number + "位联系人");
                return;
            }
            Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
            Bundle bundlePassenger = new Bundle();
            bundlePassenger.putInt("type", 13);
            if (listContact.size() == 0) {
                bundlePassenger.putInt("number", number);
            } else {
                bundlePassenger.putInt("number", number - listContact.size());
            }
            intent.putExtras(bundlePassenger);
            startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
        }else{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            Bundle bundlePassenger = new Bundle();
            bundlePassenger.putInt("type", 2);
            intent.putExtras(bundlePassenger);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
    }

    private String getMyIP(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable()){
            int type = activeNetwork.getType();
            if (type == ConnectivityManager.TYPE_MOBILE){
                Log.e(TAG, "onCreate: " + getIPAddress(true));
                return getIPAddress(true);
            }else if (type == ConnectivityManager.TYPE_WIFI){
                Log.e(TAG, "onCreate: " + getIpByWIFI()); //获取的是局域网IP？
                return getIpByWIFI();
            }
        }
        return "";
    }

    public String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } // for now eat exceptions
        return "";
    }

    /**
     * 通过wifi获取手机的IP地址
     * @return string
     */
    public String getIpByWIFI() {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAG = null;
        parent = null;
        tvHotelName = null;
        tvRoomType = null;
        tvCheckInDate = null;
        tvCheckOutInfo = null;
        tvRoomPrice = null;
        tvRoomNumber = null;
        layoutSelectRoom = null;

        etHotelNotice = null;
        etLinkName = null;
        etLinkMobile = null;
        tvOrderPrice = null;
        btnPay = null;
        selectCity = null;
        hotelDetail = null;
        hotelProduct = null;
        tvSelectorContacts = null;
        listViewContacts = null;
        adapter = null;
        if (listContact != null){
            listContact.clear();
            listContact = null;
        }
    }
}
