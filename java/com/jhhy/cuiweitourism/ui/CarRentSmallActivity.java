package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.CarRentTimeSelectActivity;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.CarRentActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarSmallOrder;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class CarRentSmallActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private String TAG = CarRentSmallActivity.class.getSimpleName();
    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private TextView tvPickUpFromStation;
    private TextView tvSendToStation;

    private TextView tvPassenger;
    private TextView tvStartTime;
    private EditText etLineNumber;
    private TextView tvFromAddress;
    private TextView tvToAddress;
    private EditText etLinkName;
    private EditText etLinkMobile;

    private LinearLayout layoutLineNumber;
    private View viewLineNumber;
    private int type = 1; //1：接机/火车站 2：送机/火车站

    private RadioGroup radioGroupCar;
    private RadioButton rbBusiness;
    private RadioButton rbComfort;
    private RadioButton rbLuxury;

   private Button btnCommit; //提交订单

    private CarRentActionBiz carBiz = null;
    private String city; //当前城市，首页传过来的
    private int position = 0; //上一页传过来的，选择的车车类型

    private ArrayList<String> fromAddress; //出发地址
    private ArrayList<String> toAddress; //目的地址

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_rent_small);
        getData();
        setupView();
        addListener();
        initDatas();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        city = bundle.getString("city");
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("租车");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        tvPickUpFromStation = (TextView) findViewById(R.id.tv_pick_up_from_station);
        tvSendToStation     = (TextView) findViewById(R.id.tv_send_to_station);

        tvPassenger = (TextView) findViewById(R.id.tv_car_rent_small_passenger);
        tvStartTime = (TextView) findViewById(R.id.tv_car_rent_small_start_time);
        etLineNumber = (EditText) findViewById(R.id.et_car_rent_small_line_number);
        tvFromAddress = (TextView) findViewById(    R.id.tv_car_rent_small_start_addr);
        tvToAddress     = (TextView) findViewById(  R.id.tv_car_rent_small_to_addr);
        etLinkName = (EditText) findViewById(R.id.tv_car_rent_small_link_name);
        etLinkMobile = (EditText) findViewById(R.id.tv_car_rent_small_link_mobile);

        layoutLineNumber = (LinearLayout) findViewById(R.id.layout_line_number);
        viewLineNumber   = findViewById(R.id.line_line_number);

        radioGroupCar = (RadioGroup) findViewById(R.id.radio_group_car);
        rbBusiness  = (RadioButton) findViewById(R.id.rb_car_business);
        rbComfort   = (RadioButton) findViewById(R.id.rb_car_comfort);
        rbLuxury    = (RadioButton) findViewById(R.id.rb_car_luxury);

        btnCommit = (Button) findViewById(R.id.btn_car_rent_next);
        if (position == 1){
            rbBusiness.setChecked(true);
        }else if (position == 2){
            rbComfort.setChecked(true);
        }else if (position == 3){
            rbLuxury.setChecked(true);
        }

        carBiz = new CarRentActionBiz();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvPickUpFromStation.setOnClickListener(this);
        tvSendToStation.setOnClickListener(this);

        tvPassenger.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);

        tvFromAddress.setOnClickListener(this);
        tvToAddress  .setOnClickListener(this);

        btnCommit.setOnClickListener(this);
        radioGroupCar.setOnCheckedChangeListener(this);
    }

    private void initDatas() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.btn_car_rent_next: //提交订单
                commitCarOrder();
                break;
            case R.id.tv_pick_up_from_station: //接机/火车站
                type = 1;
                layoutLineNumber.setVisibility(View.VISIBLE);
                viewLineNumber  .setVisibility(View.VISIBLE);
                tvPickUpFromStation.setTextColor(getResources().getColor(R.color.colorActionBar));
                tvSendToStation    .setTextColor(Color.BLACK);
                break;
            case R.id.tv_send_to_station: //送机/火车站
                type = 2;
                layoutLineNumber.setVisibility(View.GONE);
                viewLineNumber  .setVisibility(View.GONE);
                tvPickUpFromStation.setTextColor(Color.BLACK);
                tvSendToStation    .setTextColor(getResources().getColor(R.color.colorActionBar));
                break;
            case R.id.tv_car_rent_small_passenger: //选择乘客
                Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("number", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
                break;
            case R.id.tv_car_rent_small_start_time: //选择出发时间
                startActivityForResult(new Intent(getApplicationContext(), CarRentTimeSelectActivity.class), SELECT_TIME);
                break;
            case R.id.tv_car_rent_small_start_addr: //选择出发地
                Intent intentStart = new Intent(getApplicationContext(), CarRentSelectAddressActivity.class);
                Bundle bundleStart = new Bundle();
                bundleStart.putString("city", city);
                intentStart.putExtras(bundleStart);
                startActivityForResult(intentStart, SELECT_FROM_ADDRESS);
                break;
            case R.id.tv_car_rent_small_to_addr: //选择目的地
                Intent intentTo = new Intent(getApplicationContext(), CarRentSelectAddressActivity.class);
                Bundle bundleTo = new Bundle();
                bundleTo.putString("city", city);
                intentTo.putExtras(bundleTo);
                startActivityForResult(intentTo, SELECT_TO_ADDRESS);
                break;
        }
    }


    private int SELECT_TIME = 2801; //选择用车时间
    private int SELECT_FROM_ADDRESS = 2802; //选择出发地
    private int SELECT_TO_ADDRESS = 2803; //选择目的地

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "requestCode = " + requestCode +", resultCode = " + resultCode);
        if (requestCode ==  Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择乘客
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
                tvPassenger.setText(listSelection.get(0).getContactsName()+"(" + listSelection.get(0).getContactsMobile()+")");
            }
        }
        else if (requestCode == SELECT_TIME){ //选择用车时间，不传表示现在用车
            if(resultCode == RESULT_OK){
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null){
                        String selectTime = bundle.getString("selectTime");
                        RtDepartureTime = selectTime;
                        tvStartTime.setText(RtDepartureTime);
                    }
                }
            }
        }else if (requestCode == SELECT_FROM_ADDRESS){ //选择出发地
            if(resultCode == RESULT_OK){
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null){
                        fromAddress = bundle.getStringArrayList("listSelect");
                        tvFromAddress.setText(fromAddress.get(2));
                    }
                }
            }
        }else if (requestCode == SELECT_TO_ADDRESS){ //选择目的地
            if(resultCode == RESULT_OK){
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null){
                        toAddress = bundle.getStringArrayList("listSelect");
                        tvToAddress.setText(toAddress.get(2));
                    }
                }
            }
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_car_business:

                break;
            case R.id.rb_car_comfort:

                break;
            case R.id.rb_car_luxury:

                break;
        }
    }

    private void commitCarOrder() {
        String linkName = etLinkName.getText().toString();
        String linkMobile = etLinkMobile.getText().toString();
        if (TextUtils.isEmpty(linkName) || TextUtils.isEmpty(linkMobile)){
            ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
            return;
        }
        if (fromAddress == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择出发地");
            return;
        }
        if (toAddress == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择目的地");
            return;
        }
        if (type == 1){ //航班号不能为空
            RtFltNo = etLineNumber.getText().toString();
            if (TextUtils.isEmpty(RtFltNo)){
                ToastCommon.toastShortShow(getApplicationContext(), null, "请输入航班号或车次");
                return;
            }
        }
        CarSmallOrder smallCarOrder = new CarSmallOrder(MainActivity.user.getUserId(),linkName,linkMobile,"201","0",
                fromAddress.get(0), fromAddress.get(1),fromAddress.get(5), fromAddress.get(4), fromAddress.get(2),fromAddress.get(3),
                toAddress.get(0), toAddress.get(1), toAddress.get(5), toAddress.get(4), toAddress.get(2), toAddress.get(3),
                RtDepartureTime, "100", Utils.getCurrentTime(), "100", "舒适型:大众帕萨特、凯美瑞等", "200", "12", "2.9", RtLineType);

//        carBiz.carRentSmallCarOrder(smallCarOrder, new BizCallback() {
//            @Override
//            public void onError(FetchError error) {
//                LogUtil.e(TAG, " carRentSmallCarOrder :" + error.toString());
//            }
//
//            @Override
//            public void onCompletion(BasicResponseModel model) {
//                SmallCarOrderResponse result =(SmallCarOrderResponse)model.body;
//                if (null != result){
//                    LogUtil.e(TAG, "carRentSmallCarOrder = " + result.toString());
//
//
//                }
//            }
//        });
    }

    private String RtDepartureTime = ""; //出发时间，不传表示现在用车
    private String RtFltNo = ""; //航班号或车次

    //TODO 需要确认，行程类型：JJ，接飞机场；JC，接火车站；SJ，送飞机场；SC，送火车站
    private String RtLineType = "";

    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, CarRentSmallActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
