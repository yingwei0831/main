package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.AMapUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;


public class CarRentInputAddressActivity extends BaseActivity implements View.OnClickListener, TextWatcher, Inputtips.InputtipsListener, AdapterView.OnItemClickListener {

    private TextView tvTitle;
    private TextView tvTitleLeft;
    private TextView tvTitleRight;

//    private EditText etCurrentPwd;
    private EditText etNewPwd;
    private AutoCompleteTextView searchText;

    private int type; //1:fromAddress 2:toAddress
    private String city = "北京";
    private ListView listSearch;
    private List<String> listString = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rent_input_address);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        type = getIntent().getExtras().getInt("type");
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setText("保存");
        tvTitleRight.setTextColor(getResources().getColor(R.color.colorActionBar));
        tvTitleLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);

        etNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        searchText = (AutoCompleteTextView) findViewById(R.id.keyWord);
        listSearch = (ListView) findViewById(R.id.list_result);

        if (type == 1){
            tvTitle.setText("出发地点");
        }else if (type == 2){
            tvTitle.setText("目的地点");
        }
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        searchText.addTextChangedListener(this);// 添加文本输入框监听事件
        listSearch.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_right:
                save();
                break;
            case R.id.tv_title_simple_title_left:
                finish();
                break;
        }
    }

    private void save() {
//        StringBuffer sb = new StringBuffer();

        String county = searchText.getText().toString();
        if (TextUtils.isEmpty(county)){
            ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
            return;
        }
//        sb.append(county.trim());

        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("address", county);
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, city);
            if (type == 1) {
                inputquery.setCityLimit(true);
            }else{
                inputquery.setCityLimit(false);
            }
            Inputtips inputTips = new Inputtips(CarRentInputAddressActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// 正确返回
//            listString = new ArrayList<String>();
            listString.clear();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.route_inputs, listString);
//            searchText.setAdapter(aAdapter);
            listSearch.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showerror(CarRentInputAddressActivity.this, rCode);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (listString != null){
            searchText.setText(listString.get(i));
        }
    }
}
