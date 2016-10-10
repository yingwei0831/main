package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.Order;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityOrderInfo;

public class SelectPaymentActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private TextView tvOrderPrice;
    private Order order;

    private TextView tvAliPay;
    private TextView tvWeChatPay;

    private int type; //11:热门活动支付
    private ActivityOrderInfo hotInfo; //热门活动订单

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
            }else {
                order = (Order) bundle.getSerializable("order");
            }
        }
    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText("翠微收银台");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        tvOrderPrice = (TextView) findViewById(R.id.tv_order_price);
        tvAliPay = (TextView) findViewById(R.id.tv_ali_pay);
        tvWeChatPay = (TextView) findViewById(R.id.tv_wechat_pay);
        if (type == 11){
            if (hotInfo != null){
                tvOrderPrice.setText(hotInfo.getPrice());
            }
        }else{
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

                break;
            case R.id.tv_wechat_pay:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
