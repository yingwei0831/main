package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;


public class CarRentInputAddressActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvTitleLeft;
    private TextView tvTitleRight;

    private EditText etCurrentPwd;
    private EditText etNewPwd;
    private EditText etConfirmPwd;

    private int type; //1:fromAddress 2:toAddress

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rent_input_address);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        type = getIntent().getExtras().getInt("type");
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setText("保存");
        tvTitleRight.setTextColor(getResources().getColor(R.color.colorActionBar));
        tvTitleLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);

        etCurrentPwd = (EditText) findViewById(R.id.et_current_pwd);
        etNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        etConfirmPwd = (EditText) findViewById(R.id.et_new_pwd_again);

        if (type == 1){
            tvTitle.setText("出发地点");
        }else if (type == 2){
            tvTitle.setText("目的地点");
//            etNewPwd.setVisibility(View.GONE);
//            etConfirmPwd.setVisibility(View.GONE);
//            etCurrentPwd.setHint("请输入北京市内地址信息");
        }
        etCurrentPwd.setEnabled(false);
        etCurrentPwd.setText(getString(R.string.car_rent_city));
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_right:
                save();
                break;
            case R.id.tv_title_simple_title_left:
                finish();
                break;
        }
    }

    private void save() {
        String city = etCurrentPwd.getText().toString();
        StringBuffer sb = new StringBuffer();
//        if (type == 1){
            String county = etNewPwd.getText().toString();
            String address = etConfirmPwd.getText().toString();
            if (TextUtils.isEmpty(city) || TextUtils.isEmpty(county) || TextUtils.isEmpty(address)){
                ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
                return;
            }
            sb.append(city.trim()).append(county.trim()).append(address.trim());
//        }else if (type == 2){
//            if (TextUtils.isEmpty(city)){
//                ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
//                return;
//            }
//            sb.append(city.trim());
//        }
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("address", sb.toString());
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        finish();
    }

}
