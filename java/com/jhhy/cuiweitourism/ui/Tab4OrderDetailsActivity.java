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
import com.jhhy.cuiweitourism.moudle.Order;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.view.TravelerInfoClass;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.List;

public class Tab4OrderDetailsActivity extends BaseActionBarActivity {

    private String TAG = getClass().getSimpleName();
    private String orderSN;
    private int type = -1; //0:1:2:3:4:5:
    private String typeId; //1.线路、2.酒店、3租车、8签证、14私人定制、202活动
    private Order order;

    private TextView tvOrderTitle;
    private TextView tvOrderSN;
    private TextView tvOrderTime;
    private TextView tvOrderCount;
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

    private LinearLayout layoutBottomAction;
    private TextView tvPrice;
    private Button btnAction;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Consts.MESSAGE_ORDER_DETAIL: //订单详情
                    if (msg.arg1 == 1) {
                        order = (Order) msg.obj;
                        if (order != null) {
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
    }

    private void refreshView() {
        tvOrderTitle.setText(order.getProductName());
        tvOrderSN.setText(order.getOrderSN());
        tvOrderTime.setText(order.getAddTime());
        int adult = Integer.parseInt(order.getAdultNum());
        int child = Integer.parseInt(order.getChildNum());
        StringBuffer count = new StringBuffer().append(adult).append("成人");
        if (child != 0) {
            count.append(" ").append(child).append("儿童");
        }
        tvOrderCount.setText(count.toString());
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
        if (travelers != null && travelers.size() != 0) {
            for (int i = 0; i < adult + child; i++) {
                TravelerInfoClass traveler = new TravelerInfoClass(getApplicationContext());
                traveler.setShowView(travelers.get(i));
                LogUtil.e(TAG, travelers.get(i).toString());
                layoutTravelers.addView(traveler);
            }
        }
        if (order.getInvoice() == null) {
            layoutInvoice.setVisibility(View.GONE);
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
        if ("3".equals(typeId) || "202".equals(typeId)){ //游客信息隐藏
            layoutTravel.setVisibility(View.GONE);
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
                LoadingIndicator.show(this, getString(R.string.http_notice));
                OrderActionBiz biz = new OrderActionBiz(getApplicationContext(), handler);
                biz.getOrderDetail(orderSN);
            }
        }
    }

    private int REQUEST_REFUND = 1501; //申请退款
    private int REQUEST_PAY = 1502; //立即付款
    private int REQUEST_COMMENT = 1505; //去评价

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                return;
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
