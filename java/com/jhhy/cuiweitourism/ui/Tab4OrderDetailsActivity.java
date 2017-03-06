package com.jhhy.cuiweitourism.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneInfoInternationalAdapter;
import com.jhhy.cuiweitourism.biz.OrderActionBiz;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.model.TypeBean;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderDetailRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneInternationalOrderDetailRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOfChinaCancelOrderRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOfChinaOrderRefundRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOrderInternationalRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainOrderListFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainOrderRefundRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainOrderToOtherPlatRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketDetailInternationalResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketOrderDetailOfChinaResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketOrderDetailResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;
import com.jhhy.cuiweitourism.view.TravelerInfoClass;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tab4OrderDetailsActivity extends BaseActionBarActivity {

    private String TAG = getClass().getSimpleName();
    private String orderSN;
    private int type = -1; //0:1:2:3:4:5:   订单状态
    private String typeId; //订单类型   type 0.全部订单、1.线路、14私人定制、202活动、     2.酒店、8签证、82(国内机票)/83(国际机票)、80火车票(接口)
    private String sanfangorderno1; //订单第三方id 火车票
    private String sanfangorderno2; //订单第三方id 火车票
    private Order order;

    private TextView tvOrderTitle;
    private TextView tvOrderSN;
    private TextView tvOrderTime;
    private LinearLayout layoutOrderTime;
    private TextView tvOrderCount; //人数
    private TextView tvOrderCountTitle; //
    private TextView tvOrderStatus;

    private LinearLayout layoutContact; //联系人
    private TextView tvOrderLinkName;
    private TextView tvOrderLinkMobile;

    private View layoutTravel; //游客信息布局
    private TextView tvTravelTitle; //乘客信息
    private LinearLayout layoutTravelers; //游客信息，根据游客数量创建

    private LinearLayout layoutInvoice; //发票信息，如果没有发票，则不显示
    private TextView tvInvoiceTitle;
    private TextView tvInvoiceReceiver;
    private TextView tvInvoiceMobile;
    private TextView tvInvoiceAddress;

    private LinearLayout layoutTravelIcon; //旅游币，如果使用显示，不适用隐藏
    private TextView tvTravelIconNotice; //账户共xxx个旅游币，本次使用xxx个旅游币折扣
    private TextView tvTravelIconCount;

    private LinearLayout layoutBottomAction; //先隐藏，详情请求成功，则显示
    private TextView tvPrice; //订单总价
    private Button btnAction;

    public static HotelOrderDetailResponse hotelOrderDetail;
    private TrainTicketOrderDetailResponse trainOrderDetail;
    private ArrayList<TypeBean> mList = new ArrayList<>();
    private PlaneTicketOrderDetailOfChinaResponse planeTicketOrderDetailOfChina; //国内机票详情
    private int planeTicketOfChinaType = 0; // 1:退款；2:取消订单；3:支付
    private PlaneTicketDetailInternationalResponse planeTicketOrderDetailInternational; //国际机票详情
    private int planeTypeInternational = 0; // 1:支付；2:取消订单
    private MyListView mListViewPlane; //国际/国内机票航段列表

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Consts.MESSAGE_ORDER_DETAIL: //订单详情
                    if (msg.arg1 == 1) {
                        order = (Order) msg.obj;
                        if (order != null) {
                            layoutBottomAction.setVisibility(View.VISIBLE);
                            refreshView();
                        } else {
                            ToastCommon.toastShortShow(getApplicationContext(), null, "订单详情为空");
                        }
                    } else {
                        ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
                    }
                    break;
                case Consts.MESSAGE_ORDER_CANCEL_REFUND: //取消退款
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1) {
                        setResult(RESULT_OK);
                        finish();
                    }
                    break;
            }
            LoadingIndicator.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tab4_order_details);
        getData();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.orders_detail_title));

        tvOrderTitle = (TextView) findViewById(R.id.tv_order_title);
        tvOrderSN = (TextView) findViewById(R.id.tv_order_sn);
        tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        layoutOrderTime = (LinearLayout) findViewById(R.id.layout_order_time);
        tvOrderCount = (TextView) findViewById(R.id.tv_order_count);
        tvOrderCountTitle = (TextView) findViewById(R.id.tv_order_detail_count_person);
        tvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        layoutContact = (LinearLayout) findViewById(R.id.layout_order_contact);
        tvOrderLinkName = (TextView) findViewById(R.id.tv_order_link_name);
        tvOrderLinkMobile = (TextView) findViewById(R.id.tv_order_link_mobile);

        layoutTravel = findViewById(R.id.layout_travelers);
        tvTravelTitle = (TextView) findViewById(R.id.tv_order_passenger);
        layoutTravelers = (LinearLayout) findViewById(R.id.layout_order_detail_visitors);
        layoutInvoice = (LinearLayout) findViewById(R.id.layout_invoice);
        tvInvoiceTitle = (TextView) findViewById(R.id.tv_invoice_title);
        tvInvoiceReceiver = (TextView) findViewById(R.id.tv_order_invoice_receiver);
        tvInvoiceMobile = (TextView) findViewById(R.id.tv_order_invoice_mobile);
        tvInvoiceAddress = (TextView) findViewById(R.id.tv_order_invoice_address);

        layoutTravelIcon = (LinearLayout) findViewById(R.id.layout_travel_icon);
        tvTravelIconNotice = (TextView) findViewById(R.id.tv_order_icon_notice);
        tvTravelIconCount = (TextView) findViewById(R.id.tv_order_icon_count);

        layoutBottomAction = (LinearLayout) findViewById(R.id.layout_bottom_action);
        layoutBottomAction.setVisibility(View.GONE);
        tvPrice = (TextView) findViewById(R.id.tv_order_detail_price);
        btnAction = (Button) findViewById(R.id.btn_order_detail_action);
        mListViewPlane = (MyListView) findViewById(R.id.list_plane_info);
        switch (type) {
            case 0:
                btnAction.setText("取消退款");
                break;
            case 1:
                btnAction.setText("立即付款");
                break;
            case 2:
                btnAction.setText("申请退款");
                break;
            case 3:
                layoutBottomAction.setVisibility(View.GONE);
                break;
            case 4:
                layoutBottomAction.setVisibility(View.GONE);
                break;
            case 5:
                btnAction.setText("去评价");
                break;
        }
        if ("3".equals(typeId)) { //游客信息隐藏
            layoutTravel.setVisibility(View.GONE);
        }else if ("80".equals(typeId)){
            layoutContact.setVisibility(View.GONE);
        }
    }

    /**
     * 线路订单详情
     */
    private void refreshView() {
        tvOrderTitle.setText(order.getProductName());
        tvOrderSN.setText(order.getOrderSN());
        tvOrderTime.setText(order.getAddTime());
        int people = 0;
        if ("null".equals(order.getAdultNum()) || "null".equals(order.getChildNum())) {

        } else {
            int adult = Integer.parseInt(order.getAdultNum());
            int child = Integer.parseInt(order.getChildNum());
            StringBuffer count = new StringBuffer().append(adult).append("成人");
            if (child != 0) {
                count.append(" ").append(child).append("儿童");
            }
            tvOrderCount.setText(count.toString());
            people = adult + child;
        }
        if ("0".equals(order.getStatus())) {
            tvOrderStatus.setText("正在退款");

            tvPrice.setText(order.getPrice());
        } else if ("1".equals(order.getStatus())) {
            tvOrderStatus.setText("等待付款");

            tvPrice.setText(order.getPrice());
        } else if ("2".equals(order.getStatus())) {
            tvOrderStatus.setText("付款成功");

            tvPrice.setText(order.getPrice());
        } else if ("3".equals(order.getStatus())) {
            tvOrderStatus.setText("已取消");

        } else if ("4".equals(order.getStatus())) {
            tvOrderStatus.setText("已退款");

        } else if ("5".equals(order.getStatus())) {
            tvOrderStatus.setText("交易完成");

            tvPrice.setText(order.getPrice());
            LogUtil.e(TAG, "是否评论 = " + order.getStatusComment());
        }
        tvOrderLinkName.setText(order.getLinkMan());
        tvOrderLinkMobile.setText(order.getLinkMobile());
        List<UserContacts> travelers = order.getTravelers();
        if (people == 0){
            layoutTravelers.setVisibility(View.GONE);
        } else {
            if (travelers != null && travelers.size() != 0) {
                for (int i = 0; i < people; i++) {
                    TravelerInfoClass traveler = new TravelerInfoClass(getApplicationContext());
                    traveler.setShowView(travelers.get(i));
                    LogUtil.e(TAG, travelers.get(i).toString());
                    layoutTravelers.addView(traveler);
                }
            }
        }
        if (order.getInvoice() == null) {
            layoutInvoice.setVisibility(View.GONE);
            layoutTravel.setVisibility(View.GONE);
        } else {
            tvInvoiceTitle.setText(order.getInvoice().getTitle());
            tvInvoiceReceiver.setText(order.getInvoice().getReceiver());
            tvInvoiceMobile.setText(order.getInvoice().getMobile());
            tvInvoiceAddress.setText(order.getInvoice().getAddress());
        }

        if (Integer.parseInt(MainActivity.user.getUserScore()) == 0) {
            layoutTravelIcon.setVisibility(View.GONE);
        } else {
            tvTravelIconNotice.setText("账户共" + MainActivity.user.getUserScore() + "个旅游币，本次使用" + order.getUseTravelIcon() + "个旅游币折扣");
            tvTravelIconCount.setText(order.getUseTravelIcon());
        }
        layoutBottomAction.setVisibility(View.VISIBLE);
    }

    @Override
    protected void addListener() {
        super.addListener();
        btnAction.setOnClickListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                orderSN = bundle.getString("orderSN");
                type = bundle.getInt("type");
                typeId = bundle.getString("typeId");
                sanfangorderno1 = bundle.getString("sanfangorderno1");
                sanfangorderno2 = bundle.getString("sanfangorderno2");
                LoadingIndicator.show(this, getString(R.string.http_notice));
                if ("82".equals(typeId)) { //机票（国内） 详情
                    getPlaneOrderDetail();
                } else if ("83".equals(typeId)){ //机票（国际）详情
                    getPlaneOrderDetailInternational();
                } else if ("2".equals(typeId)){ //酒店 详情
                    getHotelDetail();
                } else if ("80".equals(typeId)){ //火车票 详情
                    getTrainDetail();
                } else {
                    OrderActionBiz biz = new OrderActionBiz(getApplicationContext(), handler);
                    biz.getOrderDetail(orderSN);
                }
            }
        }
    }

    /**
     * 国内机票订单详情
     */
    private void getPlaneOrderDetailInternational() {
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        PlaneInternationalOrderDetailRequest fetch = new PlaneInternationalOrderDetailRequest(orderSN);
        biz.planeTicketInternationalOrderDetail(fetch, new BizGenericCallback<PlaneTicketDetailInternationalResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<PlaneTicketDetailInternationalResponse> model) {
                LogUtil.e(TAG, "planeTicketInternationalOrderDetail: " + model.body);
                 planeTicketOrderDetailInternational = model.body;
                try {
                    refreshPlaneTicketInternationalView();
                }catch (Exception e){
                    e.printStackTrace();
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "planeTicketInternationalOrderDetail: " + error);
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取国际机票订单详情出错，请返回重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    /**
     * 机票订单详情（国内）
     */
    private void getPlaneOrderDetail() {
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        biz.planeTicketOrderDetailOfChina(new TrainOrderToOtherPlatRequest(orderSN), new BizGenericCallback<PlaneTicketOrderDetailOfChinaResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<PlaneTicketOrderDetailOfChinaResponse> model) {
                LogUtil.e(TAG, "planeTicketOrderDetailOfChina: " + model.body);
                planeTicketOrderDetailOfChina = model.body;
                try {
                    refreshPlaneTicketOfChinaView();
                }catch (Exception e){
                    e.printStackTrace();
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "planeTicketOrderDetailOfChina: " + error);
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取国内机票订单详情出错，请返回重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    /**
     * 火车票详情
     */
    private void getTrainDetail() {
        //TODO
        TrainTicketActionBiz biz = new TrainTicketActionBiz();
        TrainOrderListFetch fetch = new TrainOrderListFetch(sanfangorderno1, sanfangorderno2);
        biz.trainTicketOrderQuery(fetch, new BizGenericCallback<TrainTicketOrderDetailResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<TrainTicketOrderDetailResponse> model) {
                LogUtil.e(TAG, "trainTicketOrderQuery: " + model.body);
                trainOrderDetail = model.body;
                refreshTrainView(trainOrderDetail);
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "trainTicketOrderQuery: " + error);
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取火车票订单详情出错，请返回重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    /**
     * 酒店订单详情
     */
    private void getHotelDetail() {
        HotelActionBiz hotelActionBiz = new HotelActionBiz();
        HotelOrderDetailRequest request = new HotelOrderDetailRequest(orderSN);
        hotelActionBiz.getHotelOrderDetail(request, new BizGenericCallback<HotelOrderDetailResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelOrderDetailResponse> model) {
                LogUtil.e(TAG, "getHotelOrderDetail: " + model);
                layoutBottomAction.setVisibility(View.VISIBLE);
                hotelOrderDetail = model.body;
                hotelOrderDetail.setOrderSN(orderSN);
                refreshHotelView(hotelOrderDetail);
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "getHotelOrderDetail: " + error);
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取酒店订单详情出错，请返回重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                return;
        }

        if ("2".equals(typeId)){ //酒店 取消订单
            Intent intent = new Intent(getApplicationContext(), RequestRefundActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putSerializable("order", hotelOrderDetail);
            bundle.putInt("type", 2);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CANCEL);
            return;
        }
        else if ("80".equals(typeId)){ //火车票 退票
//            selectPassengers();
            showRefundDialog(80);
            return;
        } else if ("82".equals(typeId)){ //国内机票
            //TODO
            LogUtil.e(TAG, "planeTicketOfChinaType = " + planeTicketOfChinaType);
            if (planeTicketOfChinaType == 1){ //退款
//                selectPlaneOfChinaPassengers();
                showRefundDialog(82);
            }else if (planeTicketOfChinaType == 2){ //取消订单
                planeTicketOfChinaCancelOrder();
            }else if (planeTicketOfChinaType == 3){ //支付
                payOrderPlaneTicket(orderSN, String.format(Locale.getDefault(), "%.2f", planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getPaymentInfo().getTotalPay()), 23);
            }
            return;
        }else if ("83".equals(typeId)){ //国际机票
            if (planeTypeInternational == 1){ //支付
                payOrderPlaneTicket(orderSN, String.format(Locale.getDefault(), "%s", planeTicketOrderDetailInternational.getPrice()), 24);
            }else if (2 == planeTypeInternational){ //取消订单
                cancelOrder();
            }
            return;
        }
        switch (order.getStatus()) {
            case "0": //正在退款——>取消退款
                cancelOrder();
                break;
            case "1": //等待付款——>签约付款
                Intent intentPay = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                Bundle bundlePay = new Bundle();
                bundlePay.putSerializable("order", order);
                intentPay.putExtras(bundlePay);
                startActivityForResult(intentPay, REQUEST_PAY);
                break;
            case "2": //付款成功——>申请退款
//                RequestRefundActivity
                Intent intent = new Intent(getApplicationContext(), RequestRefundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                bundle.putInt("type", -1);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_REFUND);
                break;
            case "3": //已取消
                break;
            case "4": //已退款
                break;
            case "5": //交易完成——>去评价
//                RequestRefundActivity
                Intent intentComment = new Intent(getApplicationContext(), RequestRefundActivity.class);
                Bundle bundleComment = new Bundle();
                bundleComment.putSerializable("order", order);
                bundleComment.putInt("type", 1);
                intentComment.putExtras(bundleComment);
                startActivityForResult(intentComment, REQUEST_COMMENT);
                break;
        }
    }

    private void showRefundDialog(final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //, android.R.style.Theme_Material_Light_Dialog
        builder.setTitle(getString(R.string.refund_notice_title));
        builder.setIcon(R.drawable.app_logo);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (type == 80){
                    cancelTrainOrder();
                }else if ( type  == 82){
                    refundPlaneTicketOfChina();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 取消订单
     */
    private void cancelOrder() {
        OrderActionBiz biz = new OrderActionBiz(getApplicationContext(), handler);
        biz.requestCancelRefund(order.getOrderSN());
    }

    /**
     * 机票支付 23国内，24国际
     */
    private void payOrderPlaneTicket(String orderSN, String format, int value) {
        Intent intentPay = new Intent(getApplicationContext(), SelectPaymentActivity.class);
        Bundle bundlePay = new Bundle();
        bundlePay.putString("ordersn", orderSN);
        bundlePay.putString("orderPrice", format);
        bundlePay.putInt("type", value);
        intentPay.putExtras(bundlePay);
        startActivityForResult(intentPay, REQUEST_PAY);
    }

    /**
     * 国内机票取消订单
     */
    private void planeTicketOfChinaCancelOrder() {
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        PlaneTicketOfChinaCancelOrderRequest fetch = new PlaneTicketOfChinaCancelOrderRequest(orderSN, MainActivity.user.getUserId());
        biz.planeTicketCancelOrder(fetch, new BizGenericCallback<Object>() {
            @Override
            public void onCompletion(GenericResponseModel<Object> model) {
                ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg); //model.body = null;
                btnAction.setVisibility(View.GONE);
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "取消国内机票订单出错，请重试");
                }
                LoadingIndicator.cancel();
            }
        });
    }

    /**
     * 国内机票申请退款
     */
    private void refundPlaneTicketOfChina() {
        LoadingIndicator.show(this, getString(R.string.http_notice));
        PlaneTicketActionBiz biz = new PlaneTicketActionBiz();
        PlaneTicketOfChinaOrderRefundRequest fetch = new PlaneTicketOfChinaOrderRefundRequest(orderSN, MainActivity.user.getUserId(), //planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getLiantuoOrderNo()
                String.format(Locale.getDefault(), "%s-%s",
                        planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getFlightInfoList().get(0).getDepCode(),
                        planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getFlightInfoList().get(0).getArrCode()));
        List<String> tuipiaoren = new ArrayList<>();
        List<String> ticketNo = new ArrayList<>();
        for (int i = 0; i < planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getPassengerList().size(); i++) {
            PlaneTicketOrderDetailOfChinaResponse.PassengerListBean item = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getPassengerList().get(i);
            tuipiaoren.add(item.getName());
            ticketNo.add(item.getTicketNo());
        }
        fetch.setTuipiaoren(tuipiaoren);
        fetch.setTicketNo(ticketNo);
        biz.planeTicketOfChinaRefund(fetch, new BizGenericCallback<Object>() {
            @Override
            public void onCompletion(GenericResponseModel<Object> model) {
                LoadingIndicator.cancel();
                ToastUtil.show(getApplicationContext(), "申请退票成功");
                LogUtil.e(TAG, "planeTicketOfChinaRefund: " + model.headModel); //body = null
                btnAction.setVisibility(View.GONE);
            }

            @Override
            public void onError(FetchError error) {
                LoadingIndicator.cancel();
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "申请退票出错，请重试");
                }
                LogUtil.e(TAG, "planeTicketOfChinaRefund: " + error);
            }
        });
    }
    /**
     * 火车票退票：选择要退票的乘客
     */
//    private void selectPlaneOfChinaPassengers() {
//        Utils.alertBottomWheelOption(this, mList, new Utils.OnWheelViewClick() {
//            @Override
//            public void onClick(View view, int position) {
//                refundPlaneTicketOfChina(position);
//            }
//        });
//    }

    /**
     * 火车票退票：选择要退票的乘客
     */
//    private void selectPassengers() {
//        Utils.alertBottomWheelOption(this, mList, new Utils.OnWheelViewClick() {
//            @Override
//            public void onClick(View view, int position) {
////                ToastCommon.toastShortShow(getApplicationContext(), null, trainOrderDetail.getTicketInfo().getPassengers().getPassenger().get(position).getPsgName());
//                cancelTrainOrder(position);
//            }
//        });
//    }

    /**
     * 火车票订单退票
     */
    private void cancelTrainOrder() { //退票要选择的乘车人
        //根据
        LoadingIndicator.show(this, getString(R.string.http_notice));
        TrainTicketActionBiz biz = new TrainTicketActionBiz();
        TrainOrderRefundRequest request = new TrainOrderRefundRequest(trainOrderDetail.getOrderNo(), trainOrderDetail.getPlatOrderNo(), "all", "");
        List<TrainOrderRefundRequest.TuipiaorenBean> listPassenger = new ArrayList<>();
        for (int i = 0; i < trainOrderDetail.getTicketInfo().getPassengers().size(); i++) {
            TrainTicketOrderDetailResponse.PassengerBean passenger = trainOrderDetail.getTicketInfo().getPassengers().get(i);
            listPassenger.add(new TrainOrderRefundRequest.TuipiaorenBean("1", passenger.getPsgName(), "2", passenger.getCardNo()));
        }
        request.setTuipiaoren(listPassenger);
        biz.trainTicketCancel(request, new BizGenericCallback<Object>() {
            @Override
            public void onCompletion(GenericResponseModel<Object> model) { //model.body为null
                LoadingIndicator.cancel();
                LogUtil.e(TAG, "trainTicketCancel: " + model);
                ToastUtil.show(getApplicationContext(), "申请退款成功");
            }

            @Override
            public void onError(FetchError error) {
                LoadingIndicator.cancel();
                LogUtil.e(TAG, "trainTicketCancel: " + error);
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "申请退票出错，请重试");
                }
            }
        });
    }

    /**
     * 国内机票订单详情
     */
    private void refreshPlaneTicketOfChinaView() {
        layoutBottomAction.setVisibility(View.VISIBLE);
//        String fromAirport = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getFlightInfoList().get(0).getDepCode();
//        String arrivalAirport = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getFlightInfoList().get(0).getArrCode();
//        tvOrderTitle.setText(String.format(Locale.getDefault(), "%s—%s", fromAirport, arrivalAirport));
        String fromAirport = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getFlightInfoList().get(0).getDepCodes();
        String arrivalAirport = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getFlightInfoList().get(0).getArrCodes();
        tvOrderTitle.setText(String.format(Locale.getDefault(), "%s—%s", fromAirport, arrivalAirport));
        tvOrderSN.setText(planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getLiantuoOrderNo());
        layoutOrderTime.setVisibility(View.GONE);

        int people = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getPassengerList().size();
        tvOrderCount.setText(String.format(Locale.getDefault(), "%d人", people));
        tvOrderCountTitle.setText("乘机人数：");
        tvPrice.setText(String.format(Locale.getDefault(), "%.2f", planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getPaymentInfo().getTotalPay()));
        String status = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getStatus();
        tvOrderStatus.setText(getOrderStatus(status));
        //联系人
        layoutContact.setVisibility(View.GONE);
        //乘客信息
        tvTravelTitle.setText("乘客信息：");
        for (int j = 0; j < people; j++){
            PlaneTicketOrderDetailOfChinaResponse.PassengerListBean passenger = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getPassengerList().get(j);
            mList.add(new TypeBean(j, passenger.getName()));
            View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.traveler_info, null);
            TextView tvName = (TextView) rootView.findViewById(R.id.tv_order_traveler_name);
            TextView tvNameTitle = (TextView) rootView.findViewById(R.id.tv_traveler_name_title);
            TextView tvMobile = (TextView) rootView.findViewById(R.id.tv_order_traveler_mobile);
            TextView tvMobileTitle = (TextView) rootView.findViewById(R.id.tv_traveler_phone_title);
            TextView tvID = (TextView) rootView.findViewById(R.id.tv_order_traveler_id);

            tvNameTitle.setText("乘客姓名：");
            tvName.setText(passenger.getName());
//            tvMobileTitle.setVisibility(View.GONE);
//            tvMobile.setVisibility(View.GONE);
            List<PlaneTicketOrderDetailOfChinaResponse.FlightInfoListBean> flights = planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getFlightInfoList();

            tvMobileTitle.setText("航班信息：");
            tvMobile.setText(String.format(Locale.getDefault(), "%s %s—%s %s", flights.get(0).getDepDate(),
                    String.format(Locale.getDefault(), "%s:%s", flights.get(0).getDepTime().substring(0, 2), flights.get(0).getDepTime().substring(2, 4)),
                    String.format(Locale.getDefault(), "%s:%s", flights.get(0).getArrTime().substring(0, 2), flights.get(0).getArrTime().substring(2, 4)),
                    flights.get(0).getFlightNo())); //12月19日 12:30-15:50 的联航 KN5605 北京南苑机场-兰州中川机场经停庆阳机场已出票
            tvID.setText(passenger.getIdentityNo());
            if (1 == planeTicketOfChinaType) {
                TextView tvTicketNO = (TextView) rootView.findViewById(R.id.tv_plane_ticket_no);
                tvTicketNO.setVisibility(View.VISIBLE);
                tvTicketNO.setText(String.format(Locale.getDefault(), "票号：%s", planeTicketOrderDetailOfChina.getReturnX().getPolicyOrder().getPassengerList().get(0).getTicketNo())); //已出票
            }
            layoutTravelers.addView(rootView);
        }
        //发票
        layoutInvoice.setVisibility(View.GONE);
        //旅游币
        layoutTravelIcon.setVisibility(View.GONE);
    }

    /**
     * 国际机票订单详情
     */
    private void refreshPlaneTicketInternationalView() {
//        int size = planeTicketOrderDetailInternational.getInfos().getInterFlights().size(); //单程：一个元素；往返：两个元素
        tvOrderTitle.setText(planeTicketOrderDetailInternational.getTitle());
        List<PlaneTicketDetailInternationalResponse.InterFlightsBean> mList = new ArrayList<>();
        mList.addAll(planeTicketOrderDetailInternational.getInfos().getInterFlights().get(0));
        if ("RT".equals(planeTicketOrderDetailInternational.getInfos().getTravelType())){
            mList.addAll(planeTicketOrderDetailInternational.getInfos().getInterFlights().get(1));
        }
//        mListViewPlane.setVisibility(View.VISIBLE); //行程列表
//        PlaneInfoInternationalAdapter mAdapter = new PlaneInfoInternationalAdapter(getApplicationContext(), mList);
//        mAdapter.setOrderType(true);
//        mListViewPlane.setAdapter(mAdapter);

        tvOrderSN.setText(planeTicketOrderDetailInternational.getOrdersn());
        tvOrderTime.setText(Utils.getTimeStr(Long.parseLong(planeTicketOrderDetailInternational.getAddtime()) * 1000));
        if (Long.parseLong(planeTicketOrderDetailInternational.getAddtime()) + 15 * 60 < System.currentTimeMillis()/1000) { //时间大于15分钟，只能取消订单，否则可以支付
            planeTypeInternational = 2; //取消订单
            btnAction.setVisibility(View.GONE);
        }else{
            planeTypeInternational = 1; //支付
        }
        layoutBottomAction.setVisibility(View.VISIBLE);
        int people = planeTicketOrderDetailInternational.getInfos().getPassengers().size();
        tvOrderCount.setText(String.format(Locale.getDefault(), "%d人", people));
        tvOrderCountTitle.setText("乘机人数：");
        tvPrice.setText(planeTicketOrderDetailInternational.getPrice());
        tvOrderStatus.setText(getStatus(planeTicketOrderDetailInternational.getStatus()));
        //联系人
        tvOrderLinkName.setText(planeTicketOrderDetailInternational.getLinkman());
        tvOrderLinkMobile.setText(planeTicketOrderDetailInternational.getLinktel());
        //乘客
        List<PlaneTicketOrderInternationalRequest.PassengersBean> travelers = planeTicketOrderDetailInternational.getInfos().getPassengers();
        if (travelers != null && travelers.size() != 0) {
            for (int i = 0; i < people; i++) { //姓名，证件类型，证件号
                PlaneTicketOrderInternationalRequest.PassengersBean passenger = travelers.get(i);
                View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.traveler_info, null);
                TextView tvName = (TextView) rootView.findViewById(R.id.tv_order_traveler_name);
                TextView tvNameTitle = (TextView) rootView.findViewById(R.id.tv_traveler_name_title);
                TextView tvMobile = (TextView) rootView.findViewById(R.id.tv_order_traveler_mobile);
                TextView tvMobileTitle = (TextView) rootView.findViewById(R.id.tv_traveler_phone_title);
                TextView tvID = (TextView) rootView.findViewById(R.id.tv_order_traveler_id);
                TextView tvIDTitle = (TextView) rootView.findViewById(R.id.tv_traveler_id_title);
                tvNameTitle.setText("乘客姓名：");
                tvName.setText(passenger.getName());
                tvMobileTitle.setText("证件类型：");
                tvMobile.setText("2".equals(passenger.getCardType())?"护照":passenger.getCardType());
                tvIDTitle.setText("证件号：");
                tvID.setText(passenger.getCardNo());
                layoutTravelers.addView(rootView);
            }
        }
        //发票
        layoutInvoice.setVisibility(View.GONE);
        //旅游币
        layoutTravelIcon.setVisibility(View.GONE);
    }

    /**
     * 火车票订单展示
     */
    private void refreshTrainView(TrainTicketOrderDetailResponse body) {
        if (type == 0){
            btnAction.setVisibility(View.GONE);
        }
        layoutBottomAction.setVisibility(View.VISIBLE);
        tvOrderTitle.setText(String.format(Locale.getDefault(), "%s—%s", body.getTicketInfo().getFromStation(), body.getTicketInfo().getToStation()));
        if (body.getTrainOrderNo() == null || body.getTrainOrderNo().length() == 0 || "null".equals(body.getTradeNo())){
            tvOrderSN.setText("无");
        } else {
            tvOrderSN.setText(body.getTrainOrderNo());
        }
        tvOrderTime.setText(body.getTBookTime());
        if ("出票失败已退款".equals(body.getStatus())){
            btnAction.setVisibility(View.GONE);
        }

        int people = body.getTicketInfo().getPassengers().size();
        tvOrderCount.setText(String.format(Locale.getDefault(), "%d人", people));
        tvOrderCountTitle.setText("乘车人数：");
        tvPrice.setText(body.getTktSumPrice());
        tvOrderStatus.setText(body.getStatus());

        //联系人
        layoutContact.setVisibility(View.GONE);
        //乘客信息
        tvTravelTitle.setText("乘客信息");
        List<TrainTicketOrderDetailResponse.PassengerBean> passengers = body.getTicketInfo().getPassengers();
        if (passengers != null && passengers.size() != 0) {
            for (int i = 0; i < people; i++) {
                TrainTicketOrderDetailResponse.PassengerBean passenger = passengers.get(i);
                LogUtil.e(TAG, "passenger: " + passenger);
                mList.add(new TypeBean(i, passenger.getPsgName()));
                View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.traveler_info, null);
                TextView tvName = (TextView) rootView.findViewById(R.id.tv_order_traveler_name);
                TextView tvNameTitle = (TextView) rootView.findViewById(R.id.tv_traveler_name_title);
                TextView tvMobile = (TextView) rootView.findViewById(R.id.tv_order_traveler_mobile);
                TextView tvMobileTitle = (TextView) rootView.findViewById(R.id.tv_traveler_phone_title);
                TextView tvID = (TextView) rootView.findViewById(R.id.tv_order_traveler_id);
                TextView tvIDTitle = (TextView) rootView.findViewById(R.id.tv_traveler_id_title);
                tvNameTitle.setText("乘客姓名：");
                tvName.setText(passenger.getPsgName());
                tvMobileTitle.setText("车次信息：");
                tvMobile.setText(String.format(Locale.getDefault(), "%s %s开 %s %s—%s", body.getTicketInfo().getTrainDate(), body.getTicketInfo().getFromTime(), body.getTicketInfo().getTrainCode(), body.getTicketInfo().getFromStation(), body.getTicketInfo().getToStation()));
                tvIDTitle.setText("座位信息：");
                if (passenger.getTrainBox() == null || passenger.getTrainBox().length() == 0 || "null".equals(passenger.getTrainBox())){
                    tvID.setText(String.format(Locale.getDefault(), "%s", body.getTicketInfo().getSeatType()));
                } else {
                    tvID.setText(String.format(Locale.getDefault(), "%s车厢 %s %s", passenger.getTrainBox(), passenger.getSeatNo(), body.getTicketInfo().getSeatType()));
                }
                layoutTravelers.addView(rootView);
            }
        }
        //发票
        layoutInvoice.setVisibility(View.GONE);
        //旅游币
        layoutTravelIcon.setVisibility(View.GONE);
    }

    /**
     * 酒店订单详情展示
     */
    private void refreshHotelView(HotelOrderDetailResponse body) {
        btnAction.setVisibility(View.GONE);
        layoutBottomAction.setVisibility(View.VISIBLE);
//        btnAction.setText("取消订单");

        tvOrderTitle.setText(body.getHotelName());
        tvOrderSN.setText(body.getPlatOrderNo());
        tvOrderTime.setText(body.getCreationDate());

        int people = body.getCustomer().size();
        tvOrderCount.setText(String.format(Locale.getDefault(), "%d人", people));
        tvOrderCountTitle.setText("入住人数：");
        tvPrice.setText(body.getTotalPrice());
        if ("11".equals(body.getStatus())) {
            tvOrderStatus.setText("已取消");
        }else if ("10".equals(body.getStatus())){
            tvOrderStatus.setText("未确认");
        }else if ("12".equals(body.getStatus())){
            tvOrderStatus.setText("已确认"); //取消订单：担保规则给出订单是否可取消
            btnAction.setVisibility(View.VISIBLE);
            btnAction.setText("申请退款");
        }else if ("13".equals(body.getStatus())){
            tvOrderStatus.setText("入住中");
        }else if ("14".equals(body.getStatus())){
            tvOrderStatus.setText("正常离店");
        }else if ("15".equals(body.getStatus())){
            tvOrderStatus.setText("提前离店");
        }else if ("16".equals(body.getStatus())){
            tvOrderStatus.setText("NoShow");
        }else {
            tvOrderStatus.setText(body.getStatus());
        }
        //联系人
        tvOrderLinkName.setText(body.getContact().getName());
        tvOrderLinkMobile.setText(body.getContact().getMobile());
        //入住人
        List<HotelOrderDetailResponse.CustomerBean> travelers = body.getCustomer();
        if (travelers != null && travelers.size() != 0) {
            for (int i = 0; i < people; i++) {
                TravelerInfoClass traveler = new TravelerInfoClass(getApplicationContext());
                traveler.setShowView(travelers.get(i));
                LogUtil.e(TAG, travelers.get(i).toString());
                layoutTravelers.addView(traveler);
            }
        }
        //发票
        layoutInvoice.setVisibility(View.GONE);
        //旅游币
        layoutTravelIcon.setVisibility(View.GONE);
    }

    private int REQUEST_REFUND = 1501; //申请退款
    private int REQUEST_PAY = 1502; //立即付款
    private int REQUEST_COMMENT = 1505; //去评价
    private int REQUEST_CANCEL = 1506; //酒店取消订单

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REFUND) { //申请退款
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        } else if (requestCode == REQUEST_COMMENT) { //去评论
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        } else if (requestCode == REQUEST_PAY) { //立即付款，是不是该刷新？而不是关闭？
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            } else {

            }
        }
    }

    /**
     * 匹配国际机票订单状态
     */
    private String getStatus(String status) {
        switch (status) {
            case "1":
                status = "等待付款";
                break;
            case "0":
                status = "等待处理";
                break;
            case "2":
                status = "付款成功";
                break;
            case "3":
                status = "订单取消";
                break;
            case "4":
                status = "已退款";
                break;
            case "5":
                status = "交易完成";
                break;
        }
        return status;
    }

    /**
     * 匹配国内机票订单状态
     */
    private String getOrderStatus(String status) {
        switch (status){
            case "NO_SEAT":
                status = "订座失败";
                btnAction.setVisibility(View.GONE);
                break;
            case "NEW_ORDER":
                status = "新订单待支付"; //去支付
                planeTicketOfChinaType = 3;
                btnAction.setText("签约付款");
                break;
            case "ORDER_CANCLED":
                status = "未支付订单已取消";
                btnAction.setVisibility(View.GONE);
                break;
            case "PAY_SUCCESS":
                status = "已支付";     //取消订单
                planeTicketOfChinaType = 2;
                btnAction.setText("取消订单");
                break;
            case "TICKET_SUCCESS":
                status = "已出票";     //申请退票
                planeTicketOfChinaType = 1;
                btnAction.setText("申请退票");
                break;
            case "REFUSED_BY_SUPPLY":
                status = "出票被拒回";
                btnAction.setVisibility(View.GONE);
                break;
            case "ORDER_REFUNDED":
                status = "订单已退款";
                btnAction.setVisibility(View.GONE);
                break;
            case "OTHER_STATUS":
                status = "其他状态";
                btnAction.setVisibility(View.GONE);
                break;
        }
        return status;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAG = null;
        orderSN = null;
        type = 0;
        order = null;
        tvOrderTitle = null;
        tvOrderSN = null;
        tvOrderTime = null;
        tvOrderCount = null;
        tvOrderStatus = null;
        tvOrderLinkName = null;
        tvOrderLinkMobile = null;
        layoutTravelers = null;
        layoutInvoice = null;
        tvInvoiceTitle = null;
        tvInvoiceReceiver = null;
        tvInvoiceMobile = null;
        tvInvoiceAddress = null;
        layoutTravelIcon = null;
        tvTravelIconNotice = null;
        tvTravelIconCount = null;
        layoutBottomAction = null;
        tvPrice = null;
        btnAction = null;
        if (mList != null){
            mList.clear();
            mList = null;
        }
        planeTicketOrderDetailOfChina = null;
        planeTicketOrderDetailInternational = null;
        mListViewPlane = null;
        hotelOrderDetail = null;
        trainOrderDetail = null;
        layoutOrderTime = null;
    }
}
