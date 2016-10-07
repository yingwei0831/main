package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.OrdersAllBiz;
import com.jhhy.cuiweitourism.dialog.TourismCoinActivity;
import com.jhhy.cuiweitourism.moudle.Invoice;
import com.jhhy.cuiweitourism.moudle.Order;
import com.jhhy.cuiweitourism.moudle.TravelDetail;
import com.jhhy.cuiweitourism.moudle.UserContacts;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.GroupDeadline;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class InnerTravelEditOrderActivity extends BaseActivity implements View.OnClickListener, OnItemTextViewClick {


    private int priceAdult;
    private int priceChild;
    private int countAdult; //成人
    private int countChild; //儿童
    private int count; //人数总数
    private TravelDetail detail; //旅游详情
    private GroupDeadline selectGroupDeadline; //选择某天的价格日历
    private int priceIcon = 0; //将要抵扣的旅游币
    private int priceInvoice = 15;

    private TextView tvTitle;
    private TextView tvFromCity;
    private TextView tvTravelId;

    private TextView tvSelectFromCity; //选择出发城市

    private EditText etContactName;
    private EditText etContactTel;
    private EditText etContactMail;
    private EditText etContactRemark;

    private TextView tvTravelers;
    private TextView tvSelectTraveler; //常用联系人
    private LinearLayout layoutTravelers; //动态添加游客

    private TextView tvTravelIcon; //旅游币
    private TextView tvInvoice; //发票
    private TextView tvReserveNotice; //预订须知

    private TextView tvPriceTravel; //商品金额
    private TextView tvPriceIcon; //可抵扣的旅游币
    private RelativeLayout layoutPriceExpress;
    private TextView tvPriceInvoice; //快递费

    private CheckBox cbDeal;
    private TextView tvDeal;

    private TextView tvPriceTotal; //订单金额
    private Button btnPay; //立即支付

    private List<UserContacts> listCommitCon = new ArrayList<>(); //提交的联系人列表数据
    private Invoice  invoiceCommit = null; //提交的发票信息
    private int priceTotal; //订单总金额

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0){
                ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
            }else{
                switch (msg.what){
                    case Consts.MESSAGE_ORDER_COMMIT: //提交订单 //成功，进入付款页面
                        Order order = (Order) msg.obj;
                        Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_PAY); //订单生成成功，去支付
                        break;
                }
            }
            LoadingIndicator.cancel();
        }
    };
    private int invoiceTag = 1; //需要发票: 2（个人）,3（单位）；1：不需要发票

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_travel_edit_order);

        getData();
        setupView();
        addListener();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        selectGroupDeadline = (GroupDeadline) bundle.getSerializable("priceCalendar");
        countAdult = bundle.getInt("countAdult");
        countChild = bundle.getInt("countChild");
        priceAdult = Integer.parseInt(selectGroupDeadline.getSell_price_adult());
        priceChild = Integer.parseInt(selectGroupDeadline.getSell_price_children());
        detail = (TravelDetail) bundle.getSerializable("detail");
        priceTotal = countAdult * priceAdult + countChild * priceChild;
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_inner_travel_edit_order_title);
        tvFromCity = (TextView) findViewById(R.id.tv_travel_edit_order_from_city);

        tvSelectFromCity = (TextView) findViewById(R.id.tv_travel_edit_order_select_from_city);

        tvTravelId = (TextView) findViewById(R.id.tv_inner_travel_edit_order_travel_number);

        etContactName = (EditText) findViewById(R.id.et_travel_edit_order_contact_name);
        etContactTel = (EditText) findViewById(R.id.et_travel_edit_order_contact_tel);
        etContactMail = (EditText) findViewById(R.id.et_travel_edit_order_contact_mail);
        etContactRemark = (EditText) findViewById(R.id.et_travel_edit_order_contact_remark);

        tvTravelers = (TextView) findViewById(R.id.tv_travel_edit_order_travelers);
        tvSelectTraveler = (TextView) findViewById(R.id.tv_travel_edit_order_select_traveler);
        layoutTravelers = (LinearLayout) findViewById(R.id.layout_traveler);

        tvTravelIcon = (TextView) findViewById(R.id.tv_travel_edit_order_icon);
        tvInvoice = (TextView) findViewById(R.id.tv_travel_edit_order_invoice);
        tvReserveNotice = (TextView) findViewById(R.id.tv_travel_edit_order_notice);

        cbDeal = (CheckBox) findViewById(R.id.cb_travel_edit_order_deal); //条款
        tvDeal = (TextView) findViewById(R.id.tv_travel_edit_order_deal); //进入条款内容

        tvPriceTotal = (TextView) findViewById(R.id.tv_edit_order_price); //订单总金额
        tvPriceTotal.setText(String.valueOf(priceTotal));
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay); //去往立即支付

        int totalCustom = countAdult + countChild;

        for (int i = 0; i < totalCustom; i++) {
            View viewCustom = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_traveler, null);
            final TextView tvName = (TextView) viewCustom.findViewById(R.id.tv_travel_edit_order_name);
            final int j = i;
            viewCustom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   onItemTextViewClick(j, tvName, tvName.getId());
                }
            });
            layoutTravelers.addView(viewCustom);
        }
        tvTitle.setText(detail.getTitle());
        tvFromCity.setText("北京");
        tvTravelId.setText("A00"+detail.getId());
        String str = null;
        if (countChild > 0){
            str = "," + countChild + "儿童";
            count = countAdult + countChild;
        }else{
            str = "";
            count = countAdult;
        }
        tvTravelers.setText(countAdult + "成人"+ str);
        tvPriceTravel = (TextView) findViewById(R.id.tv_inner_travel_currency_price); //商品总金额
        tvPriceTravel.setText(String.valueOf(priceTotal));
        tvPriceIcon = (TextView) findViewById(R.id.tv_inner_travel_total_price_icon); //旅游币
        tvPriceIcon.setText(String.valueOf(priceIcon));

        layoutPriceExpress = (RelativeLayout) findViewById(R.id.layout_price_express);
        layoutPriceExpress.setVisibility(View.GONE);
        tvPriceInvoice = (TextView) findViewById(R.id.tv_inner_travel_express_price); //发票

    }

    int position = -1; //点击的联系人布局中的位置
    boolean childClick = false; //是否点击了联系人列表

    @Override
    public void onItemTextViewClick(int position, View textView, int id) { //如果是单个的联系人，则只能选择一个
        childClick = true;
        this.position = position;
        Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("number", 1);
        intent.putExtras(bundle);
        startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
    }

    private void addListener() {
        tvSelectFromCity.setOnClickListener(this);
        tvSelectTraveler.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        tvTravelIcon.setOnClickListener(this);
        tvInvoice.setOnClickListener(this);
        tvReserveNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_travel_edit_order_select_from_city: //选择出发城市

                break;
            case R.id.tv_travel_edit_order_select_traveler: //选择常用联系人,可选择多个联系人
                childClick = false;
                Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("number", count);
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
                break;
            case R.id.btn_edit_order_pay: //去付款
                goToPay();
                break;
            case R.id.tv_travel_edit_order_icon: //选择旅游币
                Intent intent1 = new Intent(getApplicationContext(), TourismCoinActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("needScore", detail.getNeedScore()); //本次订单可以用的最多旅游币
                intent1.putExtras(bundle1);
                startActivityForResult(intent1, Consts.REQUEST_CODE_RESERVE_SELECT_COIN);
                break;
            case R.id.tv_travel_edit_order_invoice: //是否需要发票
                Intent invoice = new Intent(getApplicationContext(), SettingInvoiceActivity.class);
                Bundle bundleI = new Bundle();
                bundleI.putInt("tag", invoiceTag);
                if (invoiceTag != 1){
                    bundleI.putSerializable("invoice", invoiceCommit);
                }
                invoice.putExtras(bundleI);
                startActivityForResult(invoice, Consts.REQUEST_CODE_RESERVE_SELECT_INVOICE);
                break;
            case R.id.tv_travel_edit_order_notice: //去往预订须知
                startActivity(new Intent(getApplicationContext(), ReserveNoticeActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY){ //订单生成成功，去支付，支付失败

            }
        } else {
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择常用联系人
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
                if (childClick){ //如果是单个的联系人，则只能选择一个
                    if (listCommitCon.size() != 0) {
                        listCommitCon.remove(position);
                    }
                    listCommitCon.addAll(listSelection);
                    RelativeLayout traveler = (RelativeLayout) layoutTravelers.getChildAt(position);
                    TextView tvName = (TextView) traveler.getChildAt(traveler.getChildCount() - 1 );
                    tvName.setText(listSelection.get(0).getContactsName());

                    childClick = false;
                    position = -1;
                }else{ //如果是选择联系人，则可以选择多个
                    listCommitCon.clear();
                    listCommitCon.addAll(listSelection);
                    for (int i = 0; i < listSelection.size(); i++){
                        UserContacts cont = listSelection.get(i);
                        RelativeLayout traveler = (RelativeLayout) layoutTravelers.getChildAt(i);
                        TextView tvName = (TextView) traveler.getChildAt(traveler.getChildCount() - 1 );
                        tvName.setText(cont.getContactsName());
                    }
                }

            } else if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_COIN){ //选择旅游币
                Bundle bundle = data.getExtras();
                priceIcon = bundle.getInt("score");
                tvTravelIcon.setText(String.valueOf(priceIcon));
                tvPriceIcon.setText(String.valueOf(priceIcon));
                tvPriceTotal.setText(String.valueOf(priceTotal - priceIcon));
            } else if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_INVOICE){ //选择是否开发票
                Bundle bundle = data.getExtras();

                invoiceTag = bundle.getInt("tag");
                if (invoiceTag == 1){
                    invoiceCommit = null;
                    tvInvoice.setText("不需要发票");
                    layoutPriceExpress.setVisibility(View.GONE);
                    tvPriceTotal.setText(String.valueOf(priceTotal - priceIcon));
                } else {
                    invoiceCommit = (Invoice) data.getSerializableExtra("invoice");
                    layoutPriceExpress.setVisibility(View.VISIBLE);
                    tvPriceInvoice.setText(String.valueOf(priceInvoice));
                    tvPriceTotal.setText(String.valueOf(priceTotal - priceIcon + priceInvoice));
                    if (invoiceTag == 3){
                        tvInvoice.setText("单位" + invoiceCommit.getTitle());
                    }else{
                        tvInvoice.setText("个人");
                    }

                }
            } else if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY){ //订单生成成功，去支付
                setResult(RESULT_OK);
                finish();
            }

        }
    }

    /**
     * 去支付页面
     */
    private void goToPay() {
        LoadingIndicator.show(InnerTravelEditOrderActivity.this, getString(R.string.http_notice));
        String name = etContactName.getText().toString();
        String mobile = etContactTel.getText().toString();
        String mail = etContactMail.getText().toString();
        String remark = etContactRemark.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人不能为空");
            etContactName.requestFocus();
            LoadingIndicator.cancel();
            return;
        }
        if(TextUtils.isEmpty(mobile) ){
            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人手机号码不能为空");
            etContactTel.requestFocus();
            LoadingIndicator.cancel();
            return;
        }
        if (TextUtils.isEmpty(mail)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "联系人邮件不能为空");
            etContactMail.requestFocus();
            LoadingIndicator.cancel();
            return;
        }
        String needInvoice = invoiceTag == 1 ? "0" : "1";
        String useIcon;
        if (priceIcon == 0){
            useIcon = "0";
        }else{
            useIcon = "1";
        }
        //提交订单，并进入支付页面
        OrdersAllBiz biz = new OrdersAllBiz(getApplicationContext(), handler);
        biz.commitOrder(detail, selectGroupDeadline, countAdult, countChild,
                name, mobile, mail, needInvoice, invoiceCommit,
                useIcon, String.valueOf(priceIcon), listCommitCon, remark);
    }

}
