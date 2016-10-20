package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.NumberPickerActivity;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowNumberPicker;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

public class HotelEditOrderActivity extends BaseActionBarActivity implements PopupWindow.OnDismissListener {

    private String TAG = HotelEditOrderActivity.class.getSimpleName();
    private View parent;

    private TextView tvHotelName;
    private TextView tvRoomType;
    private TextView tvCheckInDate;
    private TextView tvCheckOutInfo;
    private TextView tvRoomPrice;
    private TextView tvRoomNumber;
    private RelativeLayout layoutSelectRoom;
    private int roomNumber = 1;

    private EditText etLinkName;
    private EditText etLinkMobile;

    private TextView tvOrderPrice;
    private Button   btnPay;

    private String checkInDate ;
    private String checkOutDate;
    private int    stayDays    ;
    private PhoneBean selectCity;
    private HotelDetailInfo hotelDetail;
    private int position;

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
                selectCity = (PhoneBean) bundle.getSerializable("selectCity");
                hotelDetail = (HotelDetailInfo) bundle.getSerializable("hotelDetail");
                position = bundle.getInt("position");
                LogUtil.e(TAG, "checkInDate = " + checkInDate +", checkOutDate = " + checkOutDate +", stayDays = " + stayDays +", selectCity = " + selectCity +", position = " + position);
            }
        }
    }

    @Override
    protected void setupView() {
        super.setupView();
        parent = findViewById(R.id.layout_parent);
        tvTitle.setText(getString(R.string.hotel_order_title));
        ivTitleRight.setImageResource(R.mipmap.icon_telephone_hollow);
        ivTitleRight.setVisibility(View.VISIBLE);

        tvHotelName = (TextView) findViewById(R.id.tv_hotel_name);
        tvRoomType = (TextView) findViewById(R.id.tv_room_type);
        tvCheckInDate = (TextView) findViewById(R.id.tv_check_in_date);
        tvCheckOutInfo = (TextView) findViewById(R.id.tv_left_info);
        tvRoomPrice = (TextView) findViewById(R.id.tv_room_price);
        tvRoomNumber = (TextView) findViewById(R.id.tv_room_number);
        layoutSelectRoom = (RelativeLayout) findViewById(R.id.layout_select_room_number);
        etLinkName = (EditText) findViewById(R.id.et_hotel_order_link_name);
        etLinkMobile = (EditText) findViewById(R.id.et_hotel_link_mobile);

        tvOrderPrice = (TextView) findViewById(R.id.tv_edit_order_price);
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay);

        tvHotelName.setText(hotelDetail.getTitle());
//        tvRoomType.setText(hotelDetail.get);
        tvCheckInDate.setText(checkInDate);
        tvCheckOutInfo.setText(String.format("%s 共%d晚", checkOutDate, stayDays));
        tvRoomPrice.setText(hotelDetail.getRoom().get(position).getPrice());
    }

    @Override
    protected void addListener() {
        super.addListener();
        layoutSelectRoom.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_edit_order_pay:
                gotoPay();
                break;
            case R.id.layout_select_room_number:
                Bundle bundle = new Bundle();
                bundle.putInt("value", roomNumber);
                NumberPickerActivity.actionStart(getApplicationContext(), bundle);
//                selectRoomNumber();
                break;
        }
    }

    private PopupWindowNumberPicker picker;

    private void selectRoomNumber() {
        if (picker == null){
            picker = new PopupWindowNumberPicker(HotelEditOrderActivity.this, parent);
            picker.setOnDismissListener(this);
        } else{
            if (picker.isShowing()){
                picker.dismiss();
            } else {
                picker.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                picker.refreshView(roomNumber);
            }
        }
    }

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
        LoadingIndicator.show(HotelEditOrderActivity.this, getString(R.string.http_notice));
        //"memberid":"11","productaid":"4","productname":"九寨沟喜来登大酒店",
        // "price":"800","usedate":"2016-10-10","dingnum":"1",
        // "linkman":"张三","linktel":"15210656332","linkemail":"","suitid":"5","departdate":"2016-10-20"
//        productaid 酒店id 、suitid 酒店产品id、departdate 离开时间
        //提交酒店订单
        HotelActionBiz hotelBiz = new HotelActionBiz();
        HotelOrderFetch fetch = new HotelOrderFetch(MainActivity.user.getUserId(), hotelDetail.getId(),hotelDetail.getTitle(),
                hotelDetail.getRoom().get(position).getPrice(), checkInDate, String.valueOf(roomNumber), name, mobile,"",
                hotelDetail.getRoom().get(position).getRoomid(), checkOutDate);
        hotelBiz.HotelSubmitOrder(fetch, new BizGenericCallback<HotelOrderInfo>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelOrderInfo> model) {
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    HotelOrderInfo info = model.body;
                    refreshView(info);
                    LogUtil.e(TAG,"HotelSubmitOrder =" + info.toString());
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "提交订单信息出错，请重试");
                }
                LogUtil.e(TAG, "HotelSubmitOrder: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    //去支付
    private void refreshView(HotelOrderInfo info) {
        Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", info);
        bundle.putInt("type", 21);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_PAY_HOTEL_ORDER); //订单生成成功，去支付
    }

    private int REQUEST_PAY_HOTEL_ORDER = 6593; //酒店订单支付

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PAY_HOTEL_ORDER){
            if (resultCode == RESULT_OK){ //酒店支付成功
                setResult(RESULT_OK);
                finish();
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
}
