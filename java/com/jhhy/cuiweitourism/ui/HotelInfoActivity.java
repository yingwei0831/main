package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;

public class HotelInfoActivity extends BaseActionBarActivity {

    private TextView tvHotelFacilities; //酒店设施
    private TextView tvHotelServices;   //酒店服务
    private TextView tvHotelMobile;     //酒店电话
    private TextView tvHotelAddress;    //交通位置
    private TextView tvHotelIntroduction; //酒店介绍

    private HotelDetailInfo hotelDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_info);
        getData();
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        hotelDetail = (HotelDetailInfo) bundle.getSerializable("info");
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(R.string.hotel_info_title);
        tvHotelFacilities  = (TextView) findViewById(R.id.tv_hotel_info_facilities);
        tvHotelServices   = (TextView) findViewById(R.id.tv_hotel_info_services);
        tvHotelMobile  = (TextView) findViewById(R.id.tv_hotel_info_tel);
        tvHotelAddress   = (TextView) findViewById(R.id.tv_hotel_info_address);
        tvHotelIntroduction = (TextView) findViewById(R.id.tv_hotel_info_introduce);

//        tvHotelFacilities.setText();
        tvHotelServices.setText(hotelDetail.getFuwu());
//        tvHotelMobile.setText();
        tvHotelAddress.setText(hotelDetail.getAddress());
        tvHotelIntroduction.setText(hotelDetail.getContent());
    }
}
