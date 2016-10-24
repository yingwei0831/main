package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.just.sun.pricecalendar.ToastCommon;

public class TrainEditOrderActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvTitleLeft;
    private TextView tvTitleRight;

    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;
//    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_train_edit_order);

        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText("个人资料");
        tvTitleRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setText("保存");
        tvTitleRight.setTextColor(getResources().getColor(R.color.colorActionBar));
        tvTitleLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);
        addListener();
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_right:
                reserveNotice();
                break;
            case R.id.tv_title_simple_title_left:
                finish();
                break;
        }
    }

    //预定须知
    private void reserveNotice() {
        ToastCommon.toastShortShow(getApplicationContext(), null, "预定须知");
    }
}
