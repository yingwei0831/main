package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.just.sun.pricecalendar.ToastCommon;

public class HotelEditOrderActivity extends BaseActionBarActivity {

    private String TAG = HotelEditOrderActivity.class.getSimpleName();

    private TextView tvHotelName;
    private TextView tvRoomType;
    private TextView tvCheckInDate;
    private TextView tvCheckOutInfo;
    private TextView tvRoomPrice;
    private TextView tvRoomNumber;

    private EditText etLinkName;
    private EditText etLinkMobile;

    private TextView tvOrderPrice;
    private Button   btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_edit_order);
        super.onCreate(savedInstanceState);
        getData();
    }

    private void getData() {

    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.hotel_order_title));
        ivTitleRight.setImageResource(R.mipmap.icon_telephone_hollow);
        ivTitleRight.setVisibility(View.VISIBLE);

        tvHotelName = (TextView) findViewById(R.id.tv_hotel_name);
        tvRoomType = (TextView) findViewById(R.id.tv_room_type);
        tvCheckInDate = (TextView) findViewById(R.id.tv_check_in_date);
        tvCheckOutInfo = (TextView) findViewById(R.id.tv_left_info);
        tvRoomPrice = (TextView) findViewById(R.id.tv_room_price);
        tvRoomNumber = (TextView) findViewById(R.id.tv_room_number);
        etLinkName = (EditText) findViewById(R.id.et_hotel_order_link_name);
        etLinkMobile = (EditText) findViewById(R.id.et_hotel_link_mobile);

        tvOrderPrice = (TextView) findViewById(R.id.tv_edit_order_price);
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay);
    }

    @Override
    protected void addListener() {
        super.addListener();
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_edit_order_pay:
                gotoPay();
                break;
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


        //"memberid":"11","productaid":"4","productname":"九寨沟喜来登大酒店",
        // "price":"800","usedate":"2016-10-10","dingnum":"1",
        // "linkman":"张三","linktel":"15210656332","linkemail":"","suitid":"5","departdate":"2016-10-20"
        //提交酒店订单
        HotelActionBiz hotelBiz = new HotelActionBiz();
        HotelOrderFetch fetch = new HotelOrderFetch(MainActivity.user.getUserId(),"4","九寨沟喜来登大酒店","800","2016-10-10","1","张三","15210656332","","5","2016-10-20");
        hotelBiz.HotelSubmitOrder(fetch, new BizGenericCallback<HotelOrderInfo>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelOrderInfo> model) {
                HotelOrderInfo info = model.body;
                LogUtil.e(TAG,"HotelSubmitOrder =" + info.toString());
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "HotelSubmitOrder: " + error.toString());
            }
        });
    }
}
