package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.Order;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketOrderInfo;

public class SelectPaymentActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private TextView tvOrderPrice;
    private Order order;

    private Button tvAliPay;
    private Button tvWeChatPay;

    private int type; //11:热门活动支付,21:酒店支付
    private ActivityOrderInfo hotInfo; //热门活动订单
    private HotelOrderInfo hotelInfo; //酒店订单
    private TrainTicketOrderInfo trainInfo; //火车票订单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type");
            if (type == 11){
                hotInfo = (ActivityOrderInfo) bundle.getSerializable("order");
            }else if (type == 21){ //酒店
                hotelInfo = (HotelOrderInfo) bundle.getSerializable("order");
            }else if (type == 14){ //火车票
                trainInfo = (TrainTicketOrderInfo) bundle.getSerializable("order");
            }
            else {
                order = (Order) bundle.getSerializable("order");
            }
        }
    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText("翠微收银台");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        tvOrderPrice = (TextView) findViewById(R.id.tv_order_price);
        tvAliPay = (Button) findViewById(R.id.tv_ali_pay);
        tvWeChatPay = (Button) findViewById(R.id.tv_wechat_pay);
        if (type == 11){
            if (hotInfo != null){
                tvOrderPrice.setText(hotInfo.getPrice());
            }
        } else if (type == 21){
            if (hotelInfo != null){
                tvOrderPrice.setText(hotelInfo.getPrice());
            }
        } else if (type ==  14){
            if (trainInfo != null){
                tvOrderPrice.setText(trainInfo.getPrice());
            }
        }
        else{
            if (order != null) {
                tvOrderPrice.setText(order.getPrice());
            }
        }
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvAliPay.setOnClickListener(this);
        tvWeChatPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_ali_pay:
                aliPay();
                break;
            case R.id.tv_wechat_pay:

                break;
        }
    }

    //{"head":{"code":"Alipay_index"},"field":{"ordersn":"80489619661756"}}
    private void aliPay() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
