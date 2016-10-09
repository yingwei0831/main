package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.CarRentTimeSelectActivity;
import com.jhhy.cuiweitourism.net.biz.CarRentActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentNextModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.BasicResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarNextResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarRentDetail;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.netcallback.BizCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class CarRentActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private String TAG = CarRentActivity.class.getSimpleName();

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private ArrayList<CarRentDetail> mList; //车型
    private RadioGroup radioGroup;
    private RadioButton rbCarriageKoste;
    private RadioButton rbCarriageKinglong35;
    private RadioButton rbCarriageKinglong55;

    private RadioGroup radioGroupDays;
//    private RadioButton rbCarriageDays1;
//    private RadioButton rbCarriageDays2;

    private TextView tvSelectFromAddr;
    private TextView tvSelectToAddr;

    private TextView tvSelectTime;
    private String rentTime; //用车时间

    private String rentType; //用车类型
    private String rentTypeText; //用车类型
    private String rentDay; //用车天数
    private String rentFromAddr; //用车出发地点
    private String rentToAddr; //用车目的地点

    private Button btnNext;
    private CarRentActionBiz carBiz; //租大车业务类

    private int position = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e(TAG, "-----------handleMessage-----------");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        getData();
        getInternetData();
        setupView();
        addListener();
    }

    private void getData() {
        position = getIntent().getExtras().getInt("position");
    }


    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("租车");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        radioGroup = (RadioGroup) findViewById(R.id.rg_rent_carriage);
        radioGroup.setVisibility(View.INVISIBLE);
        rbCarriageKoste = (RadioButton) findViewById(R.id.rb_carriage_koste);
        rbCarriageKinglong35 = (RadioButton) findViewById(R.id.rb_carriage_kinglong_35);
        rbCarriageKinglong55 = (RadioButton) findViewById(R.id.rb_carriage_kinglong_55);

        radioGroupDays = (RadioGroup) findViewById(R.id.rg_rent_days);

        tvSelectFromAddr =  (TextView) findViewById(R.id.tv_select_from_addr);
        tvSelectToAddr  =   (TextView) findViewById(R.id.tv_select_to_addr);

        tvSelectTime = (TextView) findViewById(R.id.tv_select_carriage_time);

        btnNext = (Button) findViewById(R.id.btn_car_rent_next);

    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvSelectTime.setOnClickListener(this);
        tvSelectFromAddr.setOnClickListener(this);
        tvSelectToAddr.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(this);
        radioGroupDays.setOnCheckedChangeListener(this);

        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_select_carriage_time: //选择用车时间
                startActivityForResult(new Intent(getApplicationContext(), CarRentTimeSelectActivity.class), SELECT_TIME);
                break;
            case R.id.tv_select_from_addr: //选择出发地址
                Intent intentFrom = new Intent(getApplicationContext(), CarRentInputAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                intentFrom.putExtras(bundle);
                startActivityForResult(intentFrom, INPUT_FROM_ADDRESS);
                break;
            case R.id.tv_select_to_addr: //选择目的地址
                Intent intentTo = new Intent(getApplicationContext(), CarRentInputAddressActivity.class);
                Bundle bundleTo = new Bundle();
                bundleTo.putInt("type", 2);
                intentTo.putExtras(bundleTo);
                startActivityForResult(intentTo, INPUT_TO_ADDRESS);
                break;
            case R.id.btn_car_rent_next:
                if (TextUtils.isEmpty(rentTime) || TextUtils.isEmpty(rentType) || TextUtils.isEmpty(rentDay) || TextUtils.isEmpty(rentFromAddr) || TextUtils.isEmpty(rentToAddr)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
                    return;
                }
                LoadingIndicator.show(CarRentActivity.this, getString(R.string.http_notice));
                CarRentNextModel model = new CarRentNextModel(rentType, rentDay, "北京市昌平区史各庄", "北京市大兴区瀛海镇");
                carBiz.carRentNextApi(model, new BizCallback() {
                    @Override
                    public void onError(FetchError error) {
                        LoadingIndicator.cancel();
                        LogUtil.e(TAG, " carRentNextApi :" + error.toString());
                    }

                    @Override
                    public void onCompletion(BasicResponseModel model) {
                        LoadingIndicator.cancel();
                        CarNextResponse next = (CarNextResponse) model.body;
                        LogUtil.e(TAG, "carRentNextApi = " + next.toString());

                        Intent intent = new Intent(getApplicationContext(), CarRentOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("rentTime", rentTime);
                        bundle.putString("rentType", rentType);
                        bundle.putString("rentTypeText", rentTypeText);
                        bundle.putString("rentDay", rentDay);
                        bundle.putString("rentFromAddr", rentFromAddr);
                        bundle.putString("rentToAddr", rentToAddr);
                        bundle.putString("rentPrice", next.getPrice());
                        bundle.putString("rentDistance", next.getWfjl());

                        intent.putExtras(bundle);
                        startActivityForResult(intent, ORDER_RENT_CAR);

                    }
                });

                break;
        }
    }

    private int SELECT_TIME = 2801; //选择用车时间
    private int ORDER_RENT_CAR = 2802; //进入用车订单
    private int INPUT_FROM_ADDRESS = 2803; //输入出发地址
    private int INPUT_TO_ADDRESS = 2804; //输入目的地址

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_TIME){
            if(resultCode == RESULT_OK){
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null){
                        String selectDate = bundle.getString("selectDate");
//                        LogUtil.e(TAG, "selectDate = " + selectDate);
                        rentTime = selectDate;
                        tvSelectTime.setText(rentTime);
                    }
                }
            }
        } else if (requestCode == ORDER_RENT_CAR){ //有可能订车
            if(resultCode == RESULT_OK){

            }
        }else if (requestCode == INPUT_FROM_ADDRESS){
            if(resultCode == RESULT_OK){
                rentFromAddr = data.getExtras().getString("address");
                tvSelectFromAddr.setText(rentFromAddr);
            }
        }else if (requestCode == INPUT_TO_ADDRESS){
            if(resultCode == RESULT_OK){
                rentToAddr = data.getExtras().getString("address");
                tvSelectToAddr.setText(rentToAddr);
            }
        }
    }

    private void getInternetData() {
        LoadingIndicator.show(CarRentActivity.this, getString(R.string.http_notice));
        carBiz = new CarRentActionBiz(getApplicationContext(), handler);
        //租车，获取大车车型

        carBiz.fetchCarRentalServiceDetail(new BizCallback() {
            @Override
            public void onCompletion(BasicResponseModel model) {
                LoadingIndicator.cancel();
                ArrayList<CarRentDetail> array = (ArrayList<CarRentDetail>) model.body;

                LogUtil.e(TAG, "fetchCarRentalServiceDetail = " + array.toString());
                mList = array;
                refreshView();
            }

            @Override
            public void onError(FetchError error) {
                LoadingIndicator.cancel();
                LogUtil.e(TAG, " fetchCarRentalServiceDetail :" + error.toString());
            }
        });
    }

    /**
     * 获取的大车类型，填充数据
     */
    private void refreshView() {

        SpannableString ss1 = new SpannableString(mList.get(0).getTitle()+mList.get(0).getSeatnum()+"座");
        ss1.setSpan(new AbsoluteSizeSpan(18), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new AbsoluteSizeSpan(12), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorActionBar)), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        rbCarriageKoste.setText(ss1);

        SpannableString ss2 = new SpannableString(mList.get(1).getTitle()+mList.get(1).getSeatnum()+"座");
        ss2.setSpan(new AbsoluteSizeSpan(18), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new AbsoluteSizeSpan(12), 4, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorActionBar)), 4, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        rbCarriageKinglong35.setText(ss2);

        SpannableString ss3 = new SpannableString(mList.get(2).getTitle()+mList.get(2).getSeatnum()+"座");
        ss3.setSpan(new AbsoluteSizeSpan(16), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(new AbsoluteSizeSpan(12), 4, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorActionBar)), 4, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        rbCarriageKinglong55.setText(ss3);
        radioGroup.setVisibility(View.VISIBLE);

        if (position == 1){
            rbCarriageKoste.setChecked(true);
        }else if (position == 2){
            rbCarriageKinglong35.setChecked(true);
        }else if (position == 3){
            rbCarriageKinglong55.setChecked(true);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id){
            case R.id.rb_carriage_koste:
                rentType = mList.get(0).getId();
                rentTypeText = mList.get(0).getTitle()+mList.get(0).getSeatnum()+"座";
                break;
            case R.id.rb_carriage_kinglong_35:
                rentType = mList.get(1).getId();
                rentTypeText = mList.get(1).getTitle()+mList.get(1).getSeatnum()+"座";
                break;
            case R.id.rb_carriage_kinglong_55:
                rentType = mList.get(2).getId();
                rentTypeText = mList.get(2).getTitle()+mList.get(2).getSeatnum()+"座";
                break;

            case R.id.rb_rent_days_one:
                rentDay = "1";
                break;
            case R.id.rb_rent_days_two:
                rentDay = "2";
                break;

        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, CarRentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
