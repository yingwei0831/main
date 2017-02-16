package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InsuranceRefundMoneyRecyclerAdapter;
import com.jhhy.cuiweitourism.adapter.InsuranceTypeAdapter;
import com.jhhy.cuiweitourism.net.biz.InsuranceBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.InsuranceRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceTypeResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.view.MyListView;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class InsuranceTypeActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "InsuranceTypeActivity";

    private InsuranceBiz biz;
    private String type;

    private ListView listInsuranceType;
    private InsuranceTypeAdapter mAdapterType;
    private ArrayList<InsuranceTypeResponse> listType = new ArrayList<>();

    private RecyclerView listInsuranceRefundMoney;
    private InsuranceRefundMoneyRecyclerAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_insurance_type);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.insurance_type_title));
        tvTitleRight.setText(getString(R.string.save));
        tvTitleRight.setVisibility(View.VISIBLE);

        listInsuranceType = (ListView) findViewById(R.id.listView_insurance_type);
        mAdapterType = new InsuranceTypeAdapter(getApplicationContext(), listType);
        listInsuranceType.setAdapter(mAdapterType);

        listInsuranceRefundMoney = (RecyclerView) findViewById(R.id.recyclerView_refund_money);
        rvAdapter = new InsuranceRefundMoneyRecyclerAdapter(getApplicationContext(), listType);
        listInsuranceRefundMoney.setAdapter(rvAdapter);
        listInsuranceRefundMoney.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        listInsuranceRefundMoney.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.HORIZONTAL));

        getIntentData();
        biz = new InsuranceBiz();
        getData(type);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mAdapterType.setPosition(i);
        mAdapterType.notifyDataSetChanged();
        rvAdapter.setPosition(i);
        rvAdapter.notifyDataSetChanged();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        switch (type){
            case 0:
                this.type = "D";
                break;
            case 1:
                this.type = "I";
                break;
        }
    }

    private void getData(String type) {
        LoadingIndicator.show(this, getString(R.string.http_notice));
        InsuranceRequest fetch = new InsuranceRequest(type);
        biz.getInsuranceType(fetch, new BizGenericCallback<ArrayList<InsuranceTypeResponse>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<InsuranceTypeResponse>> model) {
                listType = model.body;
                mAdapterType.setData(listType);
                mAdapterType.setPosition(listType.size()-1);
                mAdapterType.notifyDataSetChanged();

                rvAdapter.setPosition(listType.size() - 1);
                rvAdapter.setData(listType);
                LogUtil.e(TAG, "getInsuranceType: " + model.body);
                LogUtil.e(TAG, "getInsuranceType: " + listType.size());
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求保险种类信息出错，请返回重试");
                }
                LogUtil.e(TAG, "getInsuranceType: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    @Override
    protected void addListener() {
        super.addListener();
        LogUtil.e(TAG, "------------addListener-----------");
        listInsuranceType.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_hotel_reserve_rules: //保存
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("insurance", listType.get(mAdapterType.getPosition()));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
