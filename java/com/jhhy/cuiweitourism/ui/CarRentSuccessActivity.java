package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarRentOrderResponse;

/**
 * 租车成功
 */
public class CarRentSuccessActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private CarRentOrderResponse carRentOrderResponse;

    private Button btnPay;

    private TextView tvOrderNo;
    private TextView tvOrderTime;
    private TextView tvOrderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rent_success);
        getData();
        setupView();
        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("租车成功");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        btnPay = (Button) findViewById(R.id.btn_car_rent_next);
        tvOrderNo   = (TextView) findViewById(R.id.tv_order_no);
        tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        tvOrderPrice = (TextView) findViewById(R.id.tv_order_price);

        tvOrderNo    .setText(carRentOrderResponse.getOrdersn());
        tvOrderTime  .setText(carRentOrderResponse.getUsetime());
        tvOrderPrice .setText(carRentOrderResponse.getPrice());
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                carRentOrderResponse = (CarRentOrderResponse) bundle.getSerializable("order");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.btn_car_rent_next:
                //TODO 去支付
                Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", carRentOrderResponse);
                bundle.putInt("type", 16);
                intent.putExtras(bundle);
                startActivityForResult(intent, GO_TO_PAY);
                break;
        }
    }

    private int GO_TO_PAY = 6357; //去付款

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if (requestCode == GO_TO_PAY){
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, CarRentSuccessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
