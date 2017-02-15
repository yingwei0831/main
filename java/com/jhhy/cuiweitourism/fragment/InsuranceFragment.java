package com.jhhy.cuiweitourism.fragment;


import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.InsuranceMainActivity;
import com.jhhy.cuiweitourism.ui.InsuranceTypeActivity;
import com.jhhy.cuiweitourism.ui.PlaneCitySelectionActivity;
import com.jhhy.cuiweitourism.utils.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsuranceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsuranceFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "InsuranceFragment";

    private static final String TYPE = "type";

    private int type; //0:国内；1：国际

    public InsuranceFragment() {
        // Required empty public constructor
    }

    public static InsuranceFragment newInstance(int type) {
        InsuranceFragment fragment = new InsuranceFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insurance, container, false);
        setupView(view);
        addListener();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_insurance_from_city:
                selectCity(CODE_FROM_CITY);
                break;
            case R.id.tv_insurance_to_city:
                selectCity(CODE_ARRIVAL_CITY);
                break;
            case R.id.tv_insurance_from_time:
                selectDate(CODE_FROM_DATE);
                break;
            case R.id.tv_insurance_return_time:
                selectDate(CODE_RETURN_DATE);
                break;
            case R.id.tv_insurance_price:
                selectInsuranceType();
                break;
            case R.id.btn_insurance_search:
                editInsuranceOrder();
                break;
        }
    }

    private void editInsuranceOrder() { //订单页

    }

    /**
     * 选择保险方案
     */
    private void selectInsuranceType() {
        Intent intent = new Intent(getContext(), InsuranceTypeActivity.class);
        intent.putExtra("type", type);
        startActivityForResult(intent, CODE_INSURANCE_TYPE);
    }

    /**
     * 选择日期
     */
    private void selectDate(int requestCode) {
        Intent intent = new Intent(getContext(), DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
    /**
     * 选择出发城市
     */
    private void selectCity(int requestCode) {
        if (InsuranceMainActivity.airportInner == null || InsuranceMainActivity.airportInner.size() == 0){
            ToastUtil.show(getContext(), "旅游地址获取失败，请返回重试");
            return;
        }
        Intent intent = new Intent(getContext(), PlaneCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        //根据当前是单程/往返/询价，传入type; 1:国内 2:国际 3:往返(飞机票主页)
        bundle.putInt("type", type+10);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "-----------onActivityResult-----------");

        if (resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (requestCode == CODE_FROM_CITY) { //出发城市
                    PlaneTicketCityInfo city = (PlaneTicketCityInfo) bundle.getSerializable("selectCity");
                    tvFromCity.setText(city.getName());
                } else if (requestCode == CODE_ARRIVAL_CITY) { //到达城市
                    PlaneTicketCityInfo city = (PlaneTicketCityInfo) bundle.getSerializable("selectCity");
                    tvArrivalCity.setText(city.getName());
                } else if (requestCode == CODE_FROM_DATE) { //出发日期
                    String dateFrom = bundle.getString("selectDate");
                    tvFromDate.setText(dateFrom.substring(0, dateFrom.indexOf(" ")));
                } else if (requestCode == CODE_RETURN_DATE) { //返程日期
                    String dateReturn = bundle.getString("selectDate");
                    tvArrivalDate.setText(dateReturn.substring(0, dateReturn.indexOf(" ")));
                } else if (requestCode == CODE_INSURANCE_TYPE) { //保险方案
//                    bundle.getString("name");
//                    tvInsuranceName.setText();
//                    tvInsurancePrice.setText();
                }
            }
        }
    }

    private void setupView(View view) {
        tvFromCity = (TextView) view.findViewById(R.id.tv_insurance_from_city);
        tvArrivalCity = (TextView) view.findViewById(R.id.tv_insurance_to_city);
        tvFromDate = (TextView) view.findViewById(R.id.tv_insurance_from_time);
        tvArrivalDate = (TextView) view.findViewById(R.id.tv_insurance_return_time);
        tvInsuranceName = (TextView) view.findViewById(R.id.tv_insurance_name);
        tvInsurancePrice = (TextView) view.findViewById(R.id.tv_insurance_price);
        btnBuy = (Button) view.findViewById(R.id.btn_insurance_search);
    }

    private void addListener() {
        tvFromCity.setOnClickListener(this);
        tvArrivalCity.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvArrivalDate.setOnClickListener(this);
        tvInsurancePrice.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }

    private TextView tvFromCity;
    private TextView tvArrivalCity;

    private TextView tvFromDate;
    private TextView tvArrivalDate;

    private TextView tvInsuranceName;
    private TextView tvInsurancePrice;

    private Button btnBuy;

    private final int CODE_FROM_CITY = 987; //出发地点
    private final int CODE_ARRIVAL_CITY = 988; //到达地点
    private final int CODE_FROM_DATE = 989; //出发时间
    private final int CODE_RETURN_DATE = 990; //到达时间
    private final int CODE_INSURANCE_TYPE = 991; //保险方案


}
