package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketOrderInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class TrainEditOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = TrainEditOrderActivity.class.getSimpleName();

    private TextView tvTitleRight;
    private TextView tvTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private TextView tvStartTime;       //出发时间
    private TextView tvStartStation;    //出发车站

    private TextView tvConsumingtime;   //耗时

    private TextView tvArrivalTime;     //到达时间
    private TextView tvArrivalStation;  //到达车站

    private TextView tvTrainInfo;       //列车信息
    private TextView tvTicketDate;      //乘车日期

    private TextView tvSeatType; //座位类型
    private TextView tvTicketPrice; //座位价格
    private TextView tvSeatSelector; //座位选择器

    private TextView tvAdultCount; //成人1人
    private TextView tvSelectorContacts; //添加乘客

    private EditText etLinkName; //联系人
    private EditText etLinkMobile; //联系电话

    private TextView tvPriceTotal; //订单金额
    private Button btnPay; //立即支付

    private TrainTicketDetailInfo detail;
    private int position;
    private String startDate;
    private TrainTicketDetailInfo.SeatInfo seatInfo; //选择的座位信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_simple, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影
        setContentView(R.layout.activity_train_edit_order);

        getData();
        setupView();
        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_simple_title);
        tvTitle.setText(getString(R.string.train_order_edit));
        tvTitleRight = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setText(getString(R.string.visa_reserve_notice));
        tvTitleRight.setTextColor(getResources().getColor(R.color.colorActionBar));
        tvTitleLeft = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_simple_title_left);

        tvStartTime = (TextView) findViewById( R.id.tv_train_start_time);
        tvStartStation = (TextView) findViewById( R.id.tv_train_start_station);
        tvConsumingtime = (TextView) findViewById( R.id.tv_train_order_time_consuming);
        tvArrivalTime = (TextView) findViewById( R.id.tv_train_end_time);
        tvArrivalStation = (TextView) findViewById( R.id.tv_train_end_station);
        tvTrainInfo = (TextView) findViewById( R.id.tv_train_order_train_name);
        tvTicketDate = (TextView) findViewById( R.id.tv_train_order_train_date);

        tvSeatType = (TextView) findViewById(R.id.tv_train_seat_type);
        tvTicketPrice = (TextView) findViewById(R.id.tv_train_cost_total);
        tvSeatSelector = (TextView) findViewById(R.id.tv_train_seat_info);

        tvAdultCount = (TextView) findViewById(R.id.tv_train_passenger_count); //人数
        tvSelectorContacts = (TextView) findViewById(R.id.tv_train_add_passenger); //添加乘客

        etLinkName = (EditText) findViewById(R.id.et_train_order_link_name); //联系人
        etLinkMobile = (EditText) findViewById(R.id.et_train_link_mobile); //联系电话

        tvPriceTotal = (TextView) findViewById(R.id.tv_edit_order_price); //订单总金额
        tvPriceTotal.setText(seatInfo.floorPrice);
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay); //去往立即支付

        tvStartTime.setText(detail.departureTime);
        tvStartStation .setText(detail.departureStation);
        tvConsumingtime.setText(Utils.getDuration(detail.duration));
        tvArrivalTime.setText(detail.arrivalTime);
        tvArrivalStation.setText(detail.arrivalStation);
        tvTrainInfo.setText(String.format("%s%s", detail.trainTypeName, detail.trainNum));
        tvTicketDate.setText(startDate);

        tvSeatType.setText(seatInfo.seatName);
        tvTicketPrice.setText(seatInfo.floorPrice);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detail = (TrainTicketDetailInfo) bundle.getSerializable("detail");
            position = bundle.getInt("position");
            startDate = bundle.getString("startDate");
            seatInfo = detail.seatInfoArray.get(position);
        }
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        tvSeatSelector.setOnClickListener(this);
        tvSelectorContacts.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_right: //预定须知
                reserveNotice();
                break;
            case R.id.tv_title_simple_title_left:
                finish();
                break;
            case R.id.tv_train_seat_info: //坐席

                break;
            case R.id.tv_train_add_passenger: //添加乘客
                Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
                break;
            case R.id.btn_edit_order_pay: //立即支付
                goToPay();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择常用联系人
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");

//                listContact.clear();
//                listContact.addAll(listSelection);
            }
        }
    }

    private List<TrainTicketOrderFetch.TicketInfo> listContact = new ArrayList<>(); //乘车人列表
    /**
     * 去支付页面
     */
    private void goToPay() {
        LoadingIndicator.show(TrainEditOrderActivity.this, getString(R.string.http_notice));
        String name = etLinkName.getText().toString();
        String mobile = etLinkMobile.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人不能为空");
            etLinkName.requestFocus();
            LoadingIndicator.cancel();
            return;
        }
        if(TextUtils.isEmpty(mobile) ){
            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人手机号码不能为空");
            etLinkMobile.requestFocus();
            LoadingIndicator.cancel();
            return;
        }

        //提交订单，并进入支付页面
//        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
//        biz.doReserveVisa(MainActivity.user.getUserId(), detail.getId(), detail.getVisatype(), detail.getPrice(),
//                selectGroupDeadline.getDate(), String.valueOf(countAdult), name, mobile, mail, needInvoice, invoiceCommit,
//                useIcon, detail.getJifentprice(), String.valueOf(priceIcon));

        TrainTicketOrderFetch.TicketInfo ticketInfo1 = new TrainTicketOrderFetch.TicketInfo("人1","2","211382198608262687","0","6","121.50");
        TrainTicketOrderFetch.TicketInfo ticketInfo2 = new TrainTicketOrderFetch.TicketInfo("人2","2","211382198608262698","0","6","121.50");
        ArrayList<TrainTicketOrderFetch.TicketInfo> array = new ArrayList<TrainTicketOrderFetch.TicketInfo>();
        array.add(ticketInfo1);
        array.add(ticketInfo2);

        TrainTicketOrderFetch ticketOrderFetch = new TrainTicketOrderFetch("","","","北京","凌源","2257","2016-09-30","12:20","2016-09-30","21:11",array,"6","243");

        TrainTicketActionBiz trainBiz = new TrainTicketActionBiz();
        trainBiz.trainTicketOrderSubmit(ticketOrderFetch, new BizGenericCallback<TrainTicketOrderInfo>() {
            @Override
            public void onCompletion(GenericResponseModel<TrainTicketOrderInfo> model) {
                TrainTicketOrderInfo info = model.body;
                LogUtil.e(TAG,"trainTicketOrderSubmit =" + info.toString());
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "trainTicketOrderSubmit: " + error.toString());
            }
        });
    }
    //预定须知
    private void reserveNotice() {
        ToastCommon.toastShortShow(getApplicationContext(), null, "预定须知");
    }
}
