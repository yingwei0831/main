package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

public class ReserveNoticeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private TextView tvNotice; //预订须知
    private TextView tvContract; //合同
    private TextView tvNotification; //特别提示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_notice);
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);
        tvTitleTop.setText("预定须知");

        tvNotice = (TextView) findViewById(R.id.tv_reserve_notice);
        tvContract = (TextView) findViewById(R.id.tv_reserve_contract);
        tvNotification = (TextView) findViewById(R.id.tv_reserve_notification);

        ivTitleLeft.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        tvContract.setOnClickListener(this);
        tvNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_reserve_notice:

                break;
            case R.id.tv_reserve_contract:

                break;
            case R.id.tv_reserve_notification:

                break;
        }
    }
}
