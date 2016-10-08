package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.Order;

public class SelectPaymentActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvOrderPrice;
    private Order order;

    private TextView tvAliPay;
    private TextView tvWeChatPay;

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
            order = (Order) bundle.getSerializable("order");
        }
    }

    private void setupView() {
        tvOrderPrice = (TextView) findViewById(R.id.tv_order_price);
        tvAliPay = (TextView) findViewById(R.id.tv_ali_pay);
        tvWeChatPay = (TextView) findViewById(R.id.tv_wechat_pay);
        if (order != null) {
            tvOrderPrice.setText(order.getPrice());
        }
    }

    private void addListener() {
        tvAliPay.setOnClickListener(this);
        tvWeChatPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
