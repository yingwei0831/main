package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.Invoice;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.just.sun.pricecalendar.ToastCommon;

public class SettingInvoiceActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private String TAG = SettingInvoiceActivity.class.getSimpleName();

    private RadioGroup radioGroup;
    private int selection;

    private EditText etTitle;

    private LinearLayout layoutReceiver;
    private EditText etName;
    private EditText etMobile;
    private EditText etAddress;

    private TextView delivarNotice;
    private Button btnSaveInvoice;
    private Invoice invoiceCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        ActionBar bar =  getSupportActionBar();
        //自定义一个布局，并居中
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        setContentView(R.layout.activity_setting_invoice);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        selection = bundle.getInt("tag");
        if (selection != 1){
            invoiceCommit = (Invoice) bundle.getSerializable("invoice");
        }
    }

    private void setupView() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_invoice);

        etTitle = (EditText) findViewById(R.id.et_input_title);
        layoutReceiver = (LinearLayout) findViewById(R.id.layout_invoice_receiver);
        etName = (EditText) findViewById(R.id.et_receiver_name);
        etMobile = (EditText) findViewById(R.id.et_receiver_mobile);
        etAddress = (EditText) findViewById(R.id.et_receiver_address);
        delivarNotice = (TextView) findViewById(R.id.tv_deliver_notice);

        btnSaveInvoice = (Button) findViewById(R.id.btn_save_invoice);

        if (selection == 1){
            etTitle.setVisibility(View.GONE);
            layoutReceiver.setVisibility(View.GONE);
            delivarNotice.setVisibility(View.GONE);
            radioGroup.check(R.id.rb_invoice_none);
        }else{
            if (selection == 2) {
                etTitle.setVisibility(View.GONE);
                radioGroup.check(R.id.rb_invoice_person);
            }else{
                radioGroup.check(R.id.rb_invoice_unit);
                etTitle.setText(invoiceCommit.getTitle());
            }
            etName.setText(invoiceCommit.getReceiver());
            etMobile.setText(invoiceCommit.getMobile());
            etAddress.setText(invoiceCommit.getAddress());
        }


    }

    private void addListener() {
        radioGroup.setOnCheckedChangeListener(this);
        btnSaveInvoice.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int position) {
        switch (position){
            case R.id.rb_invoice_none:
                etTitle.setVisibility(View.GONE);
                layoutReceiver.setVisibility(View.GONE);
                delivarNotice.setVisibility(View.GONE);
                selection = 1;
                break;
            case R.id.rb_invoice_person:
                etTitle.setVisibility(View.GONE);
                layoutReceiver.setVisibility(View.VISIBLE);
                delivarNotice.setVisibility(View.VISIBLE);
                selection = 2;
                break;
            case R.id.rb_invoice_unit:
                etTitle.setVisibility(View.VISIBLE);
                layoutReceiver.setVisibility(View.VISIBLE);
                delivarNotice.setVisibility(View.VISIBLE);
                selection = 3;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_invoice:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("tag", selection);
                if (selection == 2 || selection == 3){
                    String name = etName.getText().toString();
                    String mobile = etMobile.getText().toString();
                    String address = etAddress.getText().toString();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(address)){
                        ToastCommon.toastShortShow(getApplicationContext(), null, "必输项不能为空");
                        return;
                    }
                    if (invoiceCommit == null){
                        invoiceCommit = new Invoice();
                    }
                    invoiceCommit.setReceiver(name.trim());
                    invoiceCommit.setMobile(mobile.trim());
                    invoiceCommit.setAddress(address.trim());
                    if (selection == 3) {
                        String title = etTitle.getText().toString();
                        if (TextUtils.isEmpty(title)){
                            ToastCommon.toastShortShow(getApplicationContext(), null, "公司名称不能为空");
                            return;
                        }
                        invoiceCommit.setTitle(title.trim());
                    }
                    intent.putExtra("invoice", invoiceCommit);
                }
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
