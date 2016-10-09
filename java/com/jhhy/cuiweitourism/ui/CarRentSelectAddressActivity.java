package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.CarRentCityListAdapter;
import com.jhhy.cuiweitourism.net.biz.CarRentActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentCity;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentPickLocation1;
import com.jhhy.cuiweitourism.net.models.FetchModel.CarRentPickLocation2;
import com.jhhy.cuiweitourism.net.models.ResponseModel.BasicResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.netcallback.BizCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class CarRentSelectAddressActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener, TextWatcher {

    private String TAG = CarRentSelectAddressActivity.class.getSimpleName();
    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private EditText etSearch;
    private ImageView ivSearch;

    private  CarRentActionBiz carBiz = null;

    private String city;

    private ArrayList<ArrayList<String>> list;
    private ArrayList<ArrayList<String>> listSearch; //搜索地址
    private CarRentCityListAdapter adapter;
    private ListView listView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rent_select_address);
        getData();
        getInternetData();
        setupView();
        addListener();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        city = bundle.getString("city")+"市";
    }

    private void getInternetData() {
        carBiz = new CarRentActionBiz(getApplicationContext(), handler);
        LoadingIndicator.show(CarRentSelectAddressActivity.this, getString(R.string.http_notice));
            CarRentPickLocation1 location1 = new CarRentPickLocation1(city);
            carBiz.carRentGetPickupLocationForAirport(location1, new BizCallback() {
                @Override
                public void onError(FetchError error) {
                    LogUtil.e(TAG, " carRentGetPickupLocationForAirport :" + error.toString());
                    LoadingIndicator.cancel();
                    if (error.localReason != null) {
                        ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                    } else {
                        ToastCommon.toastShortShow(getApplicationContext(), null, "请求地址信息失败，请稍后重试");
                    }
                }

                @Override
                public void onCompletion(BasicResponseModel model) {
                    ArrayList<ArrayList<String>> result = (ArrayList<ArrayList<String>>) model.body;
                    LogUtil.e(TAG, "carRentGetPickupLocationForAirport = " + result.toString());
                    LoadingIndicator.cancel();
                    list = result;
                    refreshView(result);
                }

            });


    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("租车");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        etSearch = (EditText) findViewById(R.id.et_search_key_words);
        ivSearch = (ImageView) findViewById(R.id.title_search_iv_right_telephone);

        listView = (ListView) findViewById(R.id.listview_destination);
        list = new ArrayList<>();
        adapter = new CarRentCityListAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        etSearch.addTextChangedListener(this);
        ivSearch.setOnClickListener(this);

        listView.setOnItemClickListener(this);
    }


    private void refreshView(ArrayList<ArrayList<String>> result) {
        adapter.setData(result);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayList<String> listSelect = list.get(i);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listSelect", listSelect);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.title_search_iv_right_telephone: //搜索
                search();
                break;
        }
    }

    private void search() {
        String keyWords = etSearch.getText().toString();
        if (TextUtils.isEmpty(keyWords)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入搜索关键字");
            return;
        }

        LoadingIndicator.show(CarRentSelectAddressActivity.this, getString(R.string.http_notice));

        keyWords = "浙江大厦";

        CarRentPickLocation2 location2 = new CarRentPickLocation2(city, keyWords);
        carBiz.carRentGetPickupLocationForOfficeBuilding(location2, new BizCallback() {
            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, " carRentGetPickupLocationForOfficeBuilding :" + error.toString());
                LoadingIndicator.cancel();
                if (error.localReason != null) {
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                } else {
                    ToastCommon.toastShortShow(getApplicationContext(), null, "搜索地址信息失败");
                }
            }

            @Override
            public void onCompletion(BasicResponseModel model) {
                ArrayList<ArrayList<String>> result = (ArrayList<ArrayList<String>>) model.body;
                LogUtil.e(TAG, "carRentGetPickupLocationForOfficeBuilding = " + result.toString());
                LoadingIndicator.cancel();
                listSearch = result;
                refreshView(result);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(TextUtils.isEmpty(charSequence)){
            adapter.setData(list);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
