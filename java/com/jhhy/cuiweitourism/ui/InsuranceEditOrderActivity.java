package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrderEditContactListAdapter;
import com.jhhy.cuiweitourism.dialog.TourismCoinActivity;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.model.UserContacts;
import com.jhhy.cuiweitourism.net.biz.InsuranceBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.InsuranceOrderRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketOrderFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceOrderResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceTypeResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LinkSpanWrapper;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.view.MyListView;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Locale;

public class InsuranceEditOrderActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "InsuranceEditOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_insurance_edit_order);
        getIntentData();
        super.onCreate(savedInstanceState);

    }

    private void getIntentData() {
        Intent intent = getIntent();
        insurance = (InsuranceTypeResponse) intent.getSerializableExtra("insurance");
        fromCity = intent.getStringExtra("fromCity");
        arrivalCity = intent.getStringExtra("arrivalCity");
        fromDate = intent.getStringExtra("fromDate");
        returnDate = intent.getStringExtra("returnDate");
        travelType = intent.getIntExtra("travelType", 0);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_user_contact: //选择联系人
                position = -1;
                selectUserContact(count - listContacts.size());
                break;
            case R.id.tv_insurance_number_reduce_child: //减少
                if (count > 1) {
                    count--;
                    showNumber();
                    mAdapter.setCount(count);
                    showPrice();
                }
                break;
            case R.id.tv_insurance_number_add_child: //增加
                if (count < 10) {
                    count++;
                    mAdapter.setCount(count);
                    showNumber();
                    showPrice();
                }
                break;
            case R.id.tv_insurance_order_icon:
                selectIcon();
                break;
            case R.id.tv_travel_edit_order_invoice:
                viewNotice();
                break;
            case R.id.btn_edit_order_pay:
                reserve();
                break;
        }
    }

    /**
     * TODO 预定
     */
    private void reserve() {
        if (count != listContacts.size()){
            ToastUtil.show(getApplicationContext(), "请完善被投保人信息");
            return;
        }
        String name = etContactName.getText().toString();
        String mobile = etContactMobile.getText().toString();
        String remark = etOrderRemark.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile)){
            ToastUtil.show(getApplicationContext(), "请输入紧急联系人信息");
            return;
        }
        if (!checkBox.isChecked()){
            ToastUtil.show(getApplicationContext(), "请查看投保注意事项");
            return;
        }
        InsuranceOrderRequest fetch = new InsuranceOrderRequest(MainActivity.user.getUserId(), insurance.getId(), insurance.getProductname(), insurance.getDefaultprice(), fromDate, String.valueOf(count),
                name, mobile, remark, String.valueOf(priceIcon), String.valueOf(Integer.parseInt(insurance.getDefaultprice()) * count));
        fetch.setBeibr(listContacts);

        InsuranceBiz biz = new InsuranceBiz();
        biz.setInsuranceOrder(fetch, new BizGenericCallback<InsuranceOrderResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<InsuranceOrderResponse> model) {
                LoadingIndicator.cancel();
                InsuranceOrderResponse order = model.body;
                Intent intent = new Intent(getApplicationContext(), SelectPaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                bundle.putInt("type", 31);
                intent.putExtras(bundle);
                startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_PAY); //订单生成成功，去支付
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求保险种类信息出错，请返回重试");
                }
                LogUtil.e(TAG, "setInsuranceOrder: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    private void showPrice() {
        tvPrice.setText(String.valueOf(Integer.parseInt(
                insurance.getDefaultprice()) * count)); //商品金额
        tvPriceOrder.setText(String.valueOf(Integer.parseInt(insurance.getDefaultprice()) * count - priceIcon)); //订单金额
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_LOGIN) { //登录成功
                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    MainActivity.logged = true;
                    MainActivity.user = user;
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
            } else if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT) { //选择常用联系人
                boolean doubleContact = false;
                Bundle bundle = data.getExtras();
                ArrayList<UserContacts> listSelection = bundle.getParcelableArrayList("selection");
                if (listSelection != null && listSelection.size() != 0) {
                    for (UserContacts contact : listSelection) {
                        if (position != -1){
                            InsuranceOrderRequest.BeibrBean con = new InsuranceOrderRequest.BeibrBean(
                                    contact.getContactsName(), contact.getContactsIdCard(), contact.getContactsMobile());
                            listContacts.set(position, con);
                            break;
                        }

                        //判断联系人是否在选，再加入联系人列表
                        if (listContacts.size() != 0) {
                            for (int j = 0; j < listContacts.size(); j++) {
                                InsuranceOrderRequest.BeibrBean contactSelect = listContacts.get(j);
                                if (contactSelect.getTourername().equals(contact.getContactsName())) { //如果存在姓名，则跳出循环，如果循环执行完毕，则执行添加
                                    doubleContact = true;
                                    break;
                                }
                            }
                            if (!doubleContact) {
                                InsuranceOrderRequest.BeibrBean con = new InsuranceOrderRequest.BeibrBean(
                                        contact.getContactsName(), contact.getContactsIdCard(), contact.getContactsMobile());
                                listContacts.add(con);
                            }
                        } else {
                            InsuranceOrderRequest.BeibrBean con = new InsuranceOrderRequest.BeibrBean(
                                    contact.getContactsName(), contact.getContactsIdCard(), contact.getContactsMobile());
                            listContacts.add(con);
                        }
                    }
                    mAdapter.setData(listContacts);
                    mAdapter.notifyDataSetChanged();
                }
            }else if (requestCode == Consts.REQUEST_CODE_RESERVE_SELECT_COIN) { //选择旅游币
                Bundle bundle = data.getExtras();
                priceIcon = bundle.getInt("score");
                tvShowIcon.setText(String.valueOf(priceIcon));
                tvPriceIcon.setText(String.valueOf(priceIcon));
                showPrice();
            }else if (requestCode == Consts.REQUEST_CODE_RESERVE_PAY){
                setResult(RESULT_OK);
                finish();
            }
        } else {
            if (requestCode == REQUEST_LOGIN) { //登录
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        }
    }

    private void showNumber() {
        tvNumber.setText(String.valueOf(count));
    }

    private void selectIcon(){
        if (MainActivity.logged) {
            Intent intent1 = new Intent(getApplicationContext(), TourismCoinActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("needScore", String.format(Locale.getDefault(), "%d", Integer.parseInt(insurance.getDefaultprice()) - 1)); //本次订单可以用的最多旅游币
            intent1.putExtras(bundle1);
            startActivityForResult(intent1, Consts.REQUEST_CODE_RESERVE_SELECT_COIN);
        }else{
            userLogin();
        }
    }

    private void userLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    private void selectUserContact(int size) {
        if (MainActivity.logged) {
            Intent intent = new Intent(getApplicationContext(), SelectCustomActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putInt("type", 13);
            bundle.putInt("number", size);
            intent.putExtras(bundle);
            startActivityForResult(intent, Consts.REQUEST_CODE_RESERVE_SELECT_CONTACT);
        } else {
            userLogin();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        position = i;
        selectUserContact(1);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText("填写订单");

        TextView tvProductName = (TextView) findViewById(R.id.tv_insurance_edit_order_title);
        tvProductName.setText(String.format(Locale.getDefault(), "%s%s", 0 == travelType ? "国内游" : "国际游", insurance.getProductname()));
        TextView tvProductNo = (TextView) findViewById(R.id.tv_insurance_order_product_no);
        tvProductNo.setText(String.format(Locale.getDefault(), "产品编号：%s", insurance.getProductcode()));
        TextView tvLineName = (TextView) findViewById(R.id.tv_insurance_order_from_city);
        tvLineName.setText(String.format(Locale.getDefault(), "旅游线路：%s—%s", fromCity, arrivalCity));
        TextView tvTravelDate = (TextView) findViewById(R.id.tv_insurance_order_city_name);
        tvTravelDate.setText(String.format(Locale.getDefault(), "旅游时间：%s~%s", fromDate, returnDate));

        tvNumber = (TextView) findViewById(R.id.tv_insurance_number_child);
        ivReduce = (ImageView) findViewById(R.id.tv_insurance_number_reduce_child);
        ivAdd = (ImageView) findViewById(R.id.tv_insurance_number_add_child);
        tvNumber.setText(String.valueOf(count));

        tvUserContact = (TextView) findViewById(R.id.tv_user_contact);
        listContact = (MyListView) findViewById(R.id.list_visitors);
        mAdapter = new OrderEditContactListAdapter(getApplicationContext(), listContacts);
        mAdapter.setCount(count);
        listContact.setAdapter(mAdapter);

        tvShowIcon = (TextView) findViewById(R.id.tv_insurance_order_icon);
        tvPriceIcon = (TextView) findViewById(R.id.tv_insurance_total_price_icon);
        tvPrice = (TextView) findViewById(R.id.tv_insurance_currency_price);

        tvPriceOrder = (TextView) findViewById(R.id.tv_edit_order_price);

        etContactName = (EditText) findViewById(R.id.et_insurance_order_contact_name);
        etContactMobile = (EditText) findViewById(R.id.et_insurance_order_contact_tel);
        etOrderRemark = (EditText) findViewById(R.id.et_insurance_order_contact_remark);
        tvNotice = (TextView) findViewById(R.id.tv_travel_edit_order_invoice);
        btnReserve = (Button) findViewById(R.id.btn_edit_order_pay);
        checkBox = (CheckBox) findViewById(R.id.cb_insurance_order_deal);
        tvDeal = (TextView) findViewById(R.id.tv_insurance_order_deal);
        StringBuilder actionText = new StringBuilder();
        actionText.append("我已阅读并接受");
        actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='" + "#28CE9D" + "'>" + "投保注意事项" + "</font> </a>").append("条款");

        tvDeal.setText(Html.fromHtml(actionText.toString()));
        tvDeal.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tvDeal.getText();
        int ends = text.length();
        Spannable spannable = (Spannable) tvDeal.getText();
        URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
        SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
        stylesBuilder.clearSpans();
        for (URLSpan url : urlspan) {
            LinkSpanWrapper myURLSpan = new LinkSpanWrapper(url.getURL(), getApplicationContext(), "投保注意事项", null, null, "#28CE9D"){
                @Override
                public void onItemTextViewClick(int position, View textView, int id) {
                    viewNotice();
                }
            };
            stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url), spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvDeal.setText(stylesBuilder);
    }


    /**
     * TODO 投保注意事项
     */
    private void viewNotice() {

    }

    @Override
    protected void addListener() {
        super.addListener();
        ivReduce.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        tvUserContact.setOnClickListener(this);
        tvShowIcon.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        btnReserve.setOnClickListener(this);
        listContact.setOnItemClickListener(this);
    }

    private TextView tvNumber;
    private ImageView ivReduce;
    private ImageView ivAdd;
    private int count = 1;

    private TextView tvUserContact;
    private MyListView listContact;
    private int position = -1;

    private EditText etContactName;
    private EditText etContactMobile;
    private EditText etOrderRemark;

    private ArrayList<InsuranceOrderRequest.BeibrBean> listContacts = new ArrayList();
    private OrderEditContactListAdapter mAdapter;

    private TextView tvShowIcon; //
    private TextView tvPriceIcon;
    private int priceIcon;

    private TextView tvPrice; //商品金额
    private TextView tvPriceOrder; //订单金额

    private CheckBox checkBox;
    private TextView tvDeal;
    private TextView tvNotice;
    private Button btnReserve;

    private int travelType; //0:国内；1：国际

    private InsuranceTypeResponse insurance;
    private String fromCity;
    private String arrivalCity;

    private String fromDate;
    private String returnDate;

    private int REQUEST_LOGIN = 2913; //请求登录

}
