package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
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
import com.jhhy.cuiweitourism.popupwindows.PopTrainSeatType;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

import cn.jeesoft.OptionsWindowHelper;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;

public class TrainEditOrderActivity extends AppCompatActivity implements View.OnClickListener, OnItemTextViewClick {

    private String TAG = TrainEditOrderActivity.class.getSimpleName();

    private View parent;

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
    private LinearLayout layoutContacts;; //联系人装载布局

    private EditText etLinkName; //联系人
    private EditText etLinkMobile; //联系电话

    private TextView tvPriceTotal; //订单金额
    private Button btnPay; //立即支付

    private TrainTicketDetailInfo detail;
    private int position;
    private String startDate;
    private TrainTicketDetailInfo.SeatInfo seatInfo; //选择的座位信息

    private TrainTicketActionBiz trainBiz; //火车票业务
    private TrainTicketOrderInfo info; //提交订单，返回信息

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    break;
                case -2:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "提交订单出错，请重试");
                    break;
                case 1: //订单生成成功，进入支付页面
                    Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", info);
                    bundle.putInt("type",14);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_PAY); //订单生成成功，去支付
                    break;
            }
        }
    };

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
        parent = findViewById(R.id.view_parent);
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
        layoutContacts = (LinearLayout) findViewById(R.id.layout_contacts);

        etLinkName = (EditText) findViewById(R.id.et_train_order_link_name); //联系人
        etLinkMobile = (EditText) findViewById(R.id.et_train_link_mobile); //联系电话

        tvPriceTotal = (TextView) findViewById(R.id.tv_edit_order_price); //订单总金额

        btnPay = (Button) findViewById(R.id.btn_edit_order_pay); //去往立即支付

        tvStartTime.setText(detail.departureTime);
        tvStartStation .setText(detail.departureStation);
        tvConsumingtime.setText(Utils.getDuration(detail.duration));
        tvArrivalTime.setText(detail.arrivalTime);
        tvArrivalStation.setText(detail.arrivalStation);
        tvTrainInfo.setText(String.format("%s%s", detail.trainTypeName, detail.trainNum));
        tvTicketDate.setText(startDate);

        trainBiz = new TrainTicketActionBiz();
        refreshview();
    }

    private void refreshview(){
        tvPriceTotal.setText(seatInfo.floorPrice);
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
            case R.id.tv_train_seat_info: //TODO 切换坐席，显示有车票的
//                detail.seatInfoArray;
                selectSeatType();
                break;
            case R.id.tv_train_add_passenger: //添加乘客
                Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 13);
//                if (listContact.size()== 0 && Integer.parseInt(seatInfo.seatCount))
                if (listContact.size() == 0){
                    bundle.putInt("number", 10);
                }else{
                    bundle.putInt("number", 10 - listContact.size());
                }
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
                break;
            case R.id.btn_edit_order_pay: //立即支付
                goToPay();
                break;
        }
    }

    private PopTrainSeatType changeSeatType;
    private String typeSeat; //座位类型
    private int seatTypeSelection; //座位index
    private ArrayList<TrainTicketDetailInfo.SeatInfo> mList;
//    private TrainTicketDetailInfo.SeatInfo mSeatInfo;

    //选择席别类型，弹窗
    private void selectSeatType() {
        if (changeSeatType == null) {
            if (detail.seatInfoArray != null){
                ArrayList<TrainTicketDetailInfo.SeatInfo> list = detail.seatInfoArray;
                mList = new ArrayList<>();
                for (int j = 0; j < list.size(); j ++){
                    TrainTicketDetailInfo.SeatInfo seatInfo = list.get(j);
                    if (Integer.parseInt(seatInfo.seatCount) > 0){
                        mList.add(seatInfo);
                    }
                }
            }
            changeSeatType = new PopTrainSeatType(getApplicationContext(), mList);
            changeSeatType.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    boolean commit = changeSeatType.getCommit();
                    if (commit){
                        seatInfo = changeSeatType.getSeatInfo();
                        seatTypeSelection = changeSeatType.getSelection();
                        //TODO 改变订单中所选座位
                        refreshview();
                    }
                }
            });
        }else{
            changeSeatType.refreshView(seatTypeSelection);
        }
        changeSeatType.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择常用联系人
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
//                listContact.clear();
                for (UserContacts contact : listSelection) {
                    TrainTicketOrderFetch.TicketInfo contactTrain = new TrainTicketOrderFetch.TicketInfo(
                            contact.getContactsName(), "2", contact.getContactsIdCard(), "1", seatInfo.seatCode, seatInfo.floorPrice);
                    View contactView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_train_contact, null);
                    TextView tvName = (TextView) contactView.findViewById(R.id.tv_contact_name);
                    TextView tvCardID = (TextView) contactView.findViewById(R.id.tv_contact_card_id);
                    final ImageView ivTrash = (ImageView) contactView.findViewById(R.id.iv_train_trash);
                    ImageView ivDetail = (ImageView) contactView.findViewById(R.id.iv_contact_view_detail);
                    ivTrash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onItemTextViewClick(listContact.size() - 1, ivTrash, ivTrash.getId());
                        }
                    });
                    ivDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
                    tvName.setText(contact.getContactsName());
                    tvCardID.setText(contact.getContactsIdCard());
                    layoutContacts.addView(contactView);
                    listContact.add(contactTrain);
                }
                tvAdultCount.setText(String.format("成人%d人", listContact.size()));
                tvPriceTotal.setText(String.format("%.2f", Float.parseFloat(seatInfo.floorPrice) * listContact.size()));
            }else if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY){ //去支付，支付成功
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private ArrayList<TrainTicketOrderFetch.TicketInfo> listContact = new ArrayList<>(); //乘车人列表

    @Override
    public void onItemTextViewClick(int position, View imageView, int id) {
        listContact.remove(position);
        layoutContacts.removeViewAt(position);
        tvAdultCount.setText(String.format("成人%d人", listContact.size()));
        tvPriceTotal.setText(String.format("%.2f", Float.parseFloat(seatInfo.floorPrice) * listContact.size()));
    }

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
        if (listContact.size() == 0){
            ToastUtil.show(getApplicationContext(), "请选择乘车人");
            LoadingIndicator.cancel();
            return;
        }
        //提交订单，进入支付页面
        final TrainTicketOrderFetch ticketOrderFetch = new TrainTicketOrderFetch(
                MainActivity.user.getUserId(), name, mobile, detail.departureStation, detail.arrivalStation, detail.trainNum,
                detail.departureDate, detail.departureTime, detail.arrivalDate, detail.arrivalTime, listContact, seatInfo.seatCode, String.valueOf(Float.parseFloat(seatInfo.floorPrice) * listContact.size()));
        new Thread(){
            @Override
            public void run() {
                super.run();
                trainBiz.trainTicketOrderSubmit(ticketOrderFetch, new BizGenericCallback<TrainTicketOrderInfo>() {
                    @Override
                    public void onCompletion(GenericResponseModel<TrainTicketOrderInfo> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
                            info = model.body;
                            LogUtil.e(TAG,"trainTicketOrderSubmit =" + info.toString());
                            handler.sendEmptyMessage(1);
                        }
                        LoadingIndicator.cancel();

                    }

                    @Override
                    public void onError(FetchError error) {
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-2);
                        }
                        LogUtil.e(TAG, "trainTicketOrderSubmit: " + error.toString());
                        LoadingIndicator.cancel();
                    }
                });
            }
        }.start();
    }


    //预定须知
    private void reserveNotice() {
        ToastCommon.toastShortShow(getApplicationContext(), null, "预定须知");
    }



}
