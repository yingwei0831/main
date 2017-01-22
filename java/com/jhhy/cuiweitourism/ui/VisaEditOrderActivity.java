package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.TourismCoinActivity;
import com.jhhy.cuiweitourism.model.Invoice;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.VisaActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaOrderRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaOrderResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.GroupDeadline;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisaEditOrderActivity extends BaseActionBarActivity implements OnItemTextViewClick {

    private String TAG = "VisaEditOrderActivity";

    private int countAdult; //成人
    private VisaDetailInfo detail; //旅游详情
    private GroupDeadline selectGroupDeadline; //选择某天的价格日历
    private int priceIcon = 0; //将要抵扣的旅游币
    private int priceInvoice = 15;

    private TextView tvTitle;
    private TextView tvFromCity;
    private TextView tvTravelId;

    private TextView tvSelectFromCity; //购买数量

    private EditText etContactName;
    private EditText etContactTel;
    private EditText etContactMail;
    private EditText etContactRemark;

//    private TextView tvTravelers;
//    private TextView tvSelectTraveler; //常用联系人
//    private LinearLayout layoutTravelers; //动态添加游客

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

    private TextView tvSelectIcon; //选择旅游币
    private TextView tvPayIcon; //旅游币个数
    private TextView tvTotalPrice; //商品总金额
//    private int icon; //积分支付


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0){
                ToastCommon.toastShortShow(getApplicationContext(), null, (String) msg.obj);
            }else{
                switch (msg.what){
                    case Consts.MESSAGE_VISA_RESERVE: //提交订单 //成功，进入付款页面
                        ToastCommon.toastShortShow(getApplicationContext(), null, "订单添加成功");
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
        setContentView(R.layout.activity_visa_edit_order);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        selectGroupDeadline = (GroupDeadline) bundle.getSerializable("priceCalendar");
        countAdult = bundle.getInt("countAdult");
        detail = (VisaDetailInfo) bundle.getSerializable("detail");
        priceTotal = countAdult * Integer.parseInt(detail.getVisaPrice());
    }

    @Override
    protected void setupView() {
        super.setupView();
        super.tvTitle.setText("订单填写");
        tvTitle = (TextView) findViewById(R.id.tv_inner_travel_edit_order_title);
        tvFromCity = (TextView) findViewById(R.id.tv_travel_edit_order_from_city);

        tvSelectFromCity = (TextView) findViewById(R.id.tv_travel_edit_order_select_from_city);

        tvTravelId = (TextView) findViewById(R.id.tv_inner_travel_edit_order_travel_number);

        etContactName = (EditText) findViewById(R.id.et_travel_edit_order_contact_name);
        etContactTel = (EditText) findViewById(R.id.et_travel_edit_order_contact_tel);
        etContactMail = (EditText) findViewById(R.id.et_travel_edit_order_contact_mail);
        etContactRemark = (EditText) findViewById(R.id.et_travel_edit_order_contact_remark);

//        tvTravelers = (TextView) findViewById(R.id.tv_travel_edit_order_travelers);
//        tvSelectTraveler = (TextView) findViewById(R.id.tv_travel_edit_order_select_traveler);
//        layoutTravelers = (LinearLayout) findViewById(R.id.layout_traveler);

        tvTravelIcon = (TextView) findViewById(R.id.tv_travel_edit_order_icon);
        tvInvoice = (TextView) findViewById(R.id.tv_travel_edit_order_invoice);
        tvReserveNotice = (TextView) findViewById(R.id.tv_travel_edit_order_notice); //预定规则
        tvReserveNotice.setVisibility(View.GONE);

        cbDeal = (CheckBox) findViewById(R.id.cb_travel_edit_order_deal); //条款
        tvDeal = (TextView) findViewById(R.id.tv_travel_edit_order_deal); //进入条款内容

        tvSelectIcon = (TextView) findViewById(R.id.tv_travel_edit_order_icon); //选择旅游币
        tvPayIcon = (TextView) findViewById(R.id.tv_inner_travel_total_price_icon); //旅游币个数
        tvTotalPrice = (TextView) findViewById(R.id.tv_inner_travel_currency_price); //商品总金额
        tvPriceTotal = (TextView) findViewById(R.id.tv_edit_order_price); //订单总金额
        tvTotalPrice.setText(String.valueOf(priceTotal));
        tvPriceTotal.setText(String.valueOf(priceTotal));
        btnPay = (Button) findViewById(R.id.btn_edit_order_pay); //去往立即支付

//        int totalCustom = countAdult;
//        for (int i = 0; i < totalCustom; i++) {
//            View viewCustom = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_traveler, null);
//            final TextView tvName = (TextView) viewCustom.findViewById(R.id.tv_travel_edit_order_name);
//            final int j = i;
//            viewCustom.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   onItemTextViewClick(j, tvName, tvName.getId());
//                }
//            });
//            layoutTravelers.addView(viewCustom);
//        }

        tvTitle.setText(detail.getVisaName());
        tvFromCity.setText(selectGroupDeadline.getDate());//出行时间
        tvTravelId.setText("A00"+detail.getVisaId()); //产品编号
        tvSelectFromCity.setText(String.valueOf(countAdult)); //购买数量

//        tvTravelers.setText(countAdult + "成人");
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
//        childClick = true;
//        this.position = position;
//        Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("number", 1);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
    }
    @Override
    protected void addListener() {
        super.addListener();
        tvSelectFromCity.setOnClickListener(this);
//        tvSelectTraveler.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        tvTravelIcon.setOnClickListener(this);
        tvInvoice.setOnClickListener(this);
        tvReserveNotice.setOnClickListener(this);
        tvSelectIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
//            case R.id.tv_travel_edit_order_select_from_city: //选择出发城市
//                break;
//            case R.id.tv_travel_edit_order_select_traveler: //选择常用联系人,可选择多个联系人
//                childClick = false;
//                Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("number", countAdult);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
//                break;
            case R.id.btn_edit_order_pay: //去付款
                goToPay();
                break;
            case R.id.tv_travel_edit_order_icon: //选择旅游币
                if (MainActivity.logged) {
                    Intent intent1 = new Intent(getApplicationContext(), TourismCoinActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("needScore", String.valueOf(priceTotal - 1)); //本次订单可以用的最多旅游币
                    intent1.putExtras(bundle1);
                    startActivityForResult(intent1, Consts.REQUEST_CODE_RESERVE_SELECT_COIN);
                }else{
                    userLogin();
                }
                break;
            case R.id.tv_travel_edit_order_invoice: //是否需要发票
                LogUtil.e(TAG, "invoiceTag = " + invoiceTag);
                Intent invoice = new Intent(getApplicationContext(), SettingInvoiceActivity.class);
                Bundle bundleI = new Bundle();
                bundleI.putInt("tag", invoiceTag);
                if (invoiceTag != 1){
                    bundleI.putSerializable("invoice", invoiceCommit);
                }
                invoice.putExtras(bundleI);
                startActivityForResult(invoice, Consts.REQUEST_CODE_RESERVE_SELECT_INVOICE);
                break;
//            case R.id.tv_travel_edit_order_notice: //去往预订须知
//                viewReserveNotice();
//                break;
        }
    }

    private void userLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    /**
     * 预定须知
     */
//    private void viewReserveNotice() {
//        Intent intent = new Intent(getApplicationContext(), ReserveNoticeActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("notice", detail.getRemark()); //预订须知
//        bundle.putString("contract", detail.getRemark()); //TODO 加载页面
//        bundle.putString("remark", detail.getStandard()); //费用说明
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "resultCode = " + resultCode +", requestCode = " + requestCode);
        if (resultCode == RESULT_CANCELED){
            if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY){ //订单生成成功，去支付，支付失败

            }else if (requestCode == REQUEST_LOGIN) { //登录
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        } else if (resultCode == RESULT_OK){
//            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT){ //选择常用联系人
//                Bundle bundle = data.getExtras();
//                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
//                if (childClick){ //如果是单个的联系人，则只能选择一个
//                    if (listCommitCon.size() != 0) {
//                        listCommitCon.remove(position);
//                    }
//                    listCommitCon.addAll(listSelection);
//                    RelativeLayout traveler = (RelativeLayout) layoutTravelers.getChildAt(position);
//                    TextView tvName = (TextView) traveler.getChildAt(traveler.getChildCount() - 1 );
//                    tvName.setText(listSelection.get(0).getContactsName());
//
//                    childClick = false;
//                    position = -1;
//                }else{ //如果是选择联系人，则可以选择多个
//                    listCommitCon.clear();
//                    listCommitCon.addAll(listSelection);
//                    for (int i = 0; i < listSelection.size(); i++){
//                        UserContacts cont = listSelection.get(i);
//                        RelativeLayout traveler = (RelativeLayout) layoutTravelers.getChildAt(i);
//                        TextView tvName = (TextView) traveler.getChildAt(traveler.getChildCount() - 1 );
//                        tvName.setText(cont.getContactsName());
//                    }
//                }
//
//            } else
            if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_COIN){ //选择旅游币
                Bundle bundle = data.getExtras();
                priceIcon = bundle.getInt("score");
                tvTravelIcon.setText(String.format(Locale.getDefault(), "%d个", priceIcon));
                tvPriceIcon.setText(String.format(Locale.getDefault(), "%d", priceIcon));
                tvPriceTotal.setText(String.format(Locale.getDefault(), "%d", priceTotal - priceIcon));
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
                        tvInvoice.setText("单位:" + invoiceCommit.getTitle());
                    }else{
                        tvInvoice.setText("个人");
                    }
                }
            } else if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY) { //订单生成成功，去支付成功
                setResult(RESULT_OK);
                finish();
            } else if (requestCode == REQUEST_LOGIN) { //登录成功
                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    MainActivity.logged = true;
                    MainActivity.user = user;
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
            }
        }
    }

    /**
     * 去支付页面
     */
    private void goToPay() {
        if (MainActivity.logged) {
            LoadingIndicator.show(VisaEditOrderActivity.this, getString(R.string.http_notice));
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
            if (TextUtils.isEmpty(mobile)) {
                ToastCommon.toastShortShow(getApplicationContext(), null, "联系人手机号码不能为空");
                etContactTel.requestFocus();
                LoadingIndicator.cancel();
                return;
            }
            if (TextUtils.isEmpty(mail)) {
                ToastCommon.toastShortShow(getApplicationContext(), null, "联系人邮件不能为空");
                etContactMail.requestFocus();
                LoadingIndicator.cancel();
                return;
            }
            String needInvoice = invoiceTag == 1 ? "0" : "1";
            String useIcon;
            if (priceIcon == 0) {
                useIcon = "0";
            } else {
                useIcon = "1";
            }

            //提交订单，并进入支付页面
            VisaActionBiz biz = new VisaActionBiz();
            VisaOrderRequest request = new VisaOrderRequest(MainActivity.user.getUserId(),
                    detail.getVisaName(), detail.getVisaPrice(),
                    selectGroupDeadline.getDate(), String.valueOf(countAdult),
                    name, mobile, mail, String.valueOf(priceIcon));
            biz.setVisaOrder(request, new BizGenericCallback<VisaOrderResponse>() {
                @Override
                public void onCompletion(GenericResponseModel<VisaOrderResponse> model) {
                    Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", model.body);
                    bundle.putInt("type", 22);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_PAY); //订单生成成功，去支付
                    LoadingIndicator.cancel();
                }

                @Override
                public void onError(FetchError error) {
                    if (error.localReason != null) {
                        ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                    } else {
                        ToastCommon.toastShortShow(getApplicationContext(), null, "预定出错，请重试");
                    }
                    LoadingIndicator.cancel();
                }
            });
        }else{
            userLogin();
        }
//        VisaBiz biz = new VisaBiz(getApplicationContext(), handler);
//        biz.doReserveVisa(MainActivity.user.getUserId(), detail.getId(), detail.getVisatype(), detail.getPrice(),
//                selectGroupDeadline.getDate(), String.valueOf(countAdult), name, mobile, mail, needInvoice, invoiceCommit,
//                useIcon, detail.getJifentprice(), String.valueOf(priceIcon));

    }

}
