package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.OrderActionBiz;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderDetailRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainOrderListFetch;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainOrderRefundRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderDetailResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.view.TravelerInfoClass;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.List;

public class Tab4OrderDetailsActivity extends BaseActionBarActivity {

    private String TAG = getClass().getSimpleName();
    private String orderSN;
    private int type = -1; //0:1:2:3:4:5:   订单状态
    private String typeId; //订单类型   type 0.全部订单、1.线路、14私人定制、202活动、     2.酒店、8签证、82机票、80火车票
    private String sanfangorderno; //订单第三方id
    private Order order;

    private TextView tvOrderTitle;
    private TextView tvOrderSN;
    private TextView tvOrderTime;
    private TextView tvOrderCount; //人数
    private TextView tvOrderCountTitle; //
    private TextView tvOrderStatus;
    private TextView tvOrderLinkName;
    private TextView tvOrderLinkMobile;

    private View layoutTravel; //游客信息布局
    private LinearLayout layoutTravelers; //游客信息，根据游客数量创建

    private LinearLayout layoutInvoice; //发票信息，如果没有发票，则不显示
    private TextView tvInvoiceTitle;
    private TextView tvInvoiceReceiver;
    private TextView tvInvoiceMobile;
    private TextView tvInvoiceAddress;

    private LinearLayout layoutTravelIcon; //旅游币，如果使用显示，不适用隐藏
    private TextView tvTravelIconNotice; //账户共xxx个旅游币，本次使用xxx个旅游币折扣
    private TextView tvTravelIconCount;

    private LinearLayout layoutBottomAction; //详情请求成功，则显示
    private TextView tvPrice; //订单总价
    private Button btnAction;

    public static HotelOrderDetailResponse hotelOrderDetail;

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
        tvOrderCount = (TextView) findViewById(R.id.tv_order_count);
        tvOrderCountTitle = (TextView) findViewById(R.id.tv_order_detail_count_person);
        tvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        tvOrderLinkName = (TextView) findViewById(R.id.tv_order_link_name);
        tvOrderLinkMobile = (TextView) findViewById(R.id.tv_order_link_mobile);

        layoutTravel = findViewById(R.id.layout_travelers);
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
        }
    }

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
                sanfangorderno = bundle.getString("sanfangorderno");
                LoadingIndicator.show(this, getString(R.string.http_notice));
                if ("82".equals(typeId)){ //机票（国内/国际） 详情
                    getPlaneOrderDetail();
                }else if ("2".equals(typeId)){ //酒店 详情
                    getHotelDetail();
                }else if ("80".equals(typeId)){ //火车票 详情
                    getTrainDetail();
                }
                else {
                    OrderActionBiz biz = new OrderActionBiz(getApplicationContext(), handler);
                    biz.getOrderDetail(orderSN);
                }
            }
        }
    }

    /**
     * 火车票详情
     */
    private void getTrainDetail() {
        //TODO
        TrainTicketActionBiz biz = new TrainTicketActionBiz();
        TrainOrderListFetch fetch = new TrainOrderListFetch(orderSN, sanfangorderno);
//        biz.trainTicketOrderQuery(fetch);
    }

    /**
     * 机票订单详情（国内/国际）
     */
    private void getPlaneOrderDetail() {

    }

    /**
     * 酒店订单详情
     */
    private void getHotelDetail() {
        HotelActionBiz hotelActionBiz = new HotelActionBiz();
        HotelOrderDetailRequest request = new HotelOrderDetailRequest(orderSN, sanfangorderno);
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
        else if ("80".equals(typeId)){ //火车票 取消订单
            cancelTrainOrder();
        }
        switch (order.getStatus()) {
            case "0": //正在退款——>取消退款
                OrderActionBiz biz = new OrderActionBiz(getApplicationContext(), handler);
                biz.requestCancelRefund(order.getOrderSN());
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

    /**
     * 取消火车票订单
     */
    private void cancelTrainOrder() {

    }

    /**
     * 酒店订单详情展示
     */
    private void refreshHotelView(HotelOrderDetailResponse body) {
        btnAction.setVisibility(View.GONE);
//        btnAction.setText("取消订单");

        tvOrderTitle.setText(body.getHotelName());
        tvOrderSN.setText(body.getPlatOrderNo());
        tvOrderTime.setText(body.getCreationDate());

        int people = body.getCustomers().getCustomer().size();
        tvOrderCount.setText(String.valueOf(people));
        tvOrderCountTitle.setText("入住人数：");
        tvPrice.setText(body.getTotalPrice());
        if ("11".equals(body.getStatus())) {
            tvOrderStatus.setText("已取消");
        }else if ("10".equals(body.getStatus())){
            tvOrderStatus.setText("未确认");
        }else if ("12".equals(body.getStatus())){
            tvOrderStatus.setText("已确认"); //取消订单：担保规则给出订单是否可取消
            btnAction.setVisibility(View.VISIBLE);
            btnAction.setText("取消订单");
        }else if ("13".equals(body.getStatus())){
            tvOrderStatus.setText("入住中");
        }else if ("14".equals(body.getStatus())){
            tvOrderStatus.setText("正常离店");
        }else if ("15".equals(body.getStatus())){
            tvOrderStatus.setText("提前离店");
        }else if ("16".equals(body.getStatus())){
            tvOrderStatus.setText("NoShow");
        }
        //联系人
        tvOrderLinkName.setText(body.getContact().getName());
        tvOrderLinkMobile.setText(body.getContact().getMobile());
        //入住人
        List<HotelOrderDetailResponse.CustomerBean> travelers = body.getCustomers().getCustomer();
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
    }
}
