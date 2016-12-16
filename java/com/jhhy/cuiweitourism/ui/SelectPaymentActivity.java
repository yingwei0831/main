package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.PayActionBiz;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CarRentOrderResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneOrderOfChinaResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketOrderInfo;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.pay.PayResult;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Locale;

public class SelectPaymentActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = SelectPaymentActivity.class.getSimpleName();

    private static final int SDK_PAY_FLAG = 8881; //阿里支付
    private ImageView ivTitleLeft;

    private Order order;

    private String ordersn = null;
    private String orderPrice = null;
    private Button tvAliPay;
    private Button tvWeChatPay;

    private int type; //11:热门活动支付,21:酒店支付, 16:租车订单, 14:国内火车票订单； 15：国内飞机票订单；17：国外
    private ActivityOrderInfo hotInfo; //热门活动订单
    private HotelOrderInfo hotelInfo; //酒店订单
    private TrainTicketOrderInfo trainInfo; //火车票订单
    private PlaneOrderOfChinaResponse planeOfChinaInfo; //飞机票订单

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_PAY_ALI: //点击阿里支付，提交ordersn，获得数据
                    if (msg.arg1 == 1){
                        String partner = String.valueOf(msg.obj);
                        String newstring = partner.replace("'", "\"");
                        LogUtil.e(TAG, "newstring = " + newstring);
                        startPay(newstring);
                    }else if (msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), String.valueOf(msg.obj));
                    }
                    LoadingIndicator.cancel();
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    LoadingIndicator.cancel();
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
                    LoadingIndicator.cancel();
                    break;
                case -1:
                    ToastUtil.show(getApplicationContext(), "数据解析异常，请重试");
                    LoadingIndicator.cancel();
                    break;
                case SDK_PAY_FLAG:
                    LoadingIndicator.cancel();
                    LogUtil.e(TAG, "result = " + String.valueOf(msg.obj));

                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtil.show(SelectPaymentActivity.this, "支付成功");
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.show(SelectPaymentActivity.this, "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtil.show(SelectPaymentActivity.this, "支付失败");
                        }
                    }
                    break;
            }
        }
    };

    private void startPay(final String partner) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(SelectPaymentActivity.this);
                String result = alipay.pay(partner, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

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
                if (hotInfo != null){
                    ordersn = hotInfo.getOrdersn();
                    orderPrice = hotInfo.getPrice();
                }
            }else if (type == 21){ //酒店
                hotelInfo = (HotelOrderInfo) bundle.getSerializable("order");
                if (hotelInfo != null){
                    ordersn = hotelInfo.getOrdersn();
                    orderPrice = hotelInfo.getPrice();
                }
            }else if (type == 14){ //火车票
                trainInfo = (TrainTicketOrderInfo) bundle.getSerializable("order");
                if (trainInfo != null){
                    ordersn = trainInfo.getOrdersn();
                    orderPrice = trainInfo.getPrice();
                }
            }else if (type == 15){ //国内机票
                planeOfChinaInfo = (PlaneOrderOfChinaResponse)bundle.getSerializable("order");
                if (planeOfChinaInfo != null){
                    ordersn = planeOfChinaInfo.getOrdersn();
                    orderPrice = String.valueOf(String.format(Locale.getDefault(), "%.2f", planeOfChinaInfo.getTotalprice()));
                }
            }
            else if (type == 16 || type == 17){
                CarRentOrderResponse carRentOrderResponse = (CarRentOrderResponse) bundle.getSerializable("order");
                if (carRentOrderResponse != null){
                    ordersn = carRentOrderResponse.getOrdersn();
                    orderPrice = carRentOrderResponse.getPrice();
                }
            }
            else {
                order = (Order) bundle.getSerializable("order");
                if (order != null) {
                    ordersn = order.getOrderSN();
                    orderPrice = order.getPrice();
                }
            }
        }
    }

    private void setupView() {
        TextView tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText("翠微收银台");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        TextView tvOrderPrice = (TextView) findViewById(R.id.tv_order_price);
        tvAliPay = (Button) findViewById(R.id.tv_ali_pay);
        tvWeChatPay = (Button) findViewById(R.id.tv_wechat_pay);

        tvOrderPrice.setText(orderPrice);
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
                aliPay();
                break;
            case R.id.tv_wechat_pay:
                //TODO 微信付款
//                weChatPay();
//                setResult(RESULT_OK);
//                finish();
                break;
        }
    }

//    private void weChatPay() {
//        // 注册APPID
//        final IWXAPI msgApi = WXAPIFactory.createWXAPI(getApplicationContext(), null);
//        // 将该app注册到微信
//        msgApi.registerApp("wxfa0103db1944bda3");
//    }

    private void aliPay() {
        LoadingIndicator.show(SelectPaymentActivity.this, getString(R.string.http_notice));
        PayActionBiz biz = new PayActionBiz(getApplicationContext(), handler);
        if (type == 15){ //国内机票支付
            biz.getPlaneOfChinaPayInfo(MainActivity.user.getUserId(), ordersn);
        } else if (type == 17){ //国际机票支付
            biz.getPlaneInternationalPayInfo(MainActivity.user.getUserId(), ordersn);
        }
        else {
            biz.getPayInfo(ordersn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
