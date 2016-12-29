package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.Locale;

public class HotelInfoActivity extends BaseActionBarActivity {

    private TextView tvHotelFacilities; //酒店设施
    private TextView tvHotelServices;   //酒店服务
    private TextView tvHotelMobile;     //酒店电话
    private TextView tvHotelAddress;    //交通位置
    private TextView tvHotelIntroduction; //酒店介绍

//    private HotelDetailInfo hotelDetail;
    private HotelDetailResponse hotelDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_info);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
//        Bundle bundle = getIntent().getExtras();
//        hotelDetail = (HotelDetailInfo) bundle.getSerializable("info");
        hotelDetail = HotelDetailActivity.hotelDetail;
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(R.string.hotel_info_title);
        tvHotelFacilities  = (TextView) findViewById(R.id.tv_hotel_info_facilities);
        tvHotelServices   = (TextView) findViewById(R.id.tv_hotel_info_services);
        tvHotelMobile  = (TextView) findViewById(R.id.tv_hotel_info_tel);
        View tvAddressTitle = findViewById(R.id.tv_hotel_address_title);
        tvHotelAddress   = (TextView) findViewById(R.id.tv_hotel_info_address);
        tvHotelIntroduction = (TextView) findViewById(R.id.tv_hotel_info_introduce);

        tvHotelFacilities.setText(String.format(Locale.getDefault(), "%s\n%s\n%s\n%s",
                hotelDetail.getHotel().getRoomFac(), hotelDetail.getHotel().getCateringFac().replace("n", "\n"), hotelDetail.getHotel().getSportsEnterainment(), hotelDetail.getHotel().getMeetingFac().replace("n", "\n")));
        if (hotelDetail.getHotel().getPhone() != null && hotelDetail.getHotel().getPhone().length() != 0){
            tvHotelMobile.setText(String.format(Locale.getDefault(), "酒店电话：%s", hotelDetail.getHotel().getPhone()));
        }else{
            tvHotelMobile.setVisibility(View.GONE);
        }
        tvHotelServices.setText(hotelDetail.getHotel().getServices());
        if (TextUtils.isEmpty(hotelDetail.getHotel().getTraffic())){
            tvAddressTitle.setVisibility(View.GONE);
        }else {
            tvHotelAddress.setText(hotelDetail.getHotel().getTraffic());
        }
        tvHotelIntroduction.setText(hotelDetail.getHotel().getDescription().replace("n", "\n"));
    }

    @Override
    protected void addListener() {
        super.addListener();
        tvHotelMobile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_hotel_info_tel:
                Utils.contact(getApplicationContext(), hotelDetail.getHotel().getPhone());
                break;
        }
    }
}
