package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

public class HotelMainActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = HotelMainActivity.class.getSimpleName();
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private TextView tvAddress;
    private TextView tvLocation;

    private RelativeLayout layoutCheckIn;
    private RelativeLayout layoutCheckOut;
    private TextView tvCheckInDate;
    private TextView tvCheckOutDate;
    private TextView tvCheckInNotice;
    private TextView tvCheckOutNotice;

    private EditText etSearchText;
    private Button btnSearch;
    private Button btnMyOrder;
    private Button btnMyHotel;

    private String checkInDate;
    private String checkOutDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影
        setContentView(R.layout.activity_hotel_main);
        setupView();
        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.hotel_title));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        tvAddress = (TextView) findViewById(R.id.tv_location_name);
        tvLocation = (TextView) findViewById(R.id.tv_location_icon);
        layoutCheckIn = (RelativeLayout) findViewById( R.id.layout_hotel_check_in);
        layoutCheckOut = (RelativeLayout) findViewById(R.id.layout_hotel_check_out);
        btnSearch = (Button) findViewById(R.id.btn_commit);
        btnSearch = (Button) findViewById(R.id.btn_commit);
        btnMyOrder = (Button) findViewById(R.id.btn_to_my_order);
        btnMyHotel = (Button) findViewById(R.id.btn_to_my_hotel);
    }

    private void addListener() {
        ivTitleLeft .setOnClickListener(this);
        tvLocation .setOnClickListener(this);
        layoutCheckIn .setOnClickListener(this);
        layoutCheckOut.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnMyOrder.setOnClickListener(this);
        btnMyHotel.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_location_name: //选择位置
                ToastCommon.toastShortShow(getApplicationContext(), null, "后台没有接口");
                break;
            case R.id.tv_location_icon: //定位

                break;
            case R.id.layout_hotel_check_in: //选择入住日期
                Intent intentFromDate = new Intent( getApplicationContext(), HotelCalendarActivity.class);
                Bundle bundleFrom = new Bundle();
                bundleFrom.putString("from", checkInDate);
                bundleFrom.putString("left", checkOutDate);
                intentFromDate.putExtras(bundleFrom);
                startActivityForResult(intentFromDate, SELECT_CHECK_IN_DATE);
                break;
            case R.id.layout_hotel_check_out: //选择离店日期
                Intent intentLeftDate = new Intent( getApplicationContext(), HotelCalendarActivity.class);
                Bundle bundleLeft = new Bundle();
                bundleLeft.putString("from", checkInDate);
                bundleLeft.putString("left", checkOutDate);
                intentLeftDate.putExtras(bundleLeft);
                startActivityForResult(intentLeftDate, SELECT_CHECK_IN_DATE);
                break;
            case R.id.btn_commit: //搜索
                Intent intentSearch = new Intent(getApplicationContext(), HotelListActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.btn_to_my_order: //我的订单

                break;
            case R.id.btn_to_my_hotel: //我的酒店
                ToastCommon.toastShortShow(getApplicationContext(), null, "后台没有接口");
                break;
        }
    }

    private int SELECT_CHECK_IN_DATE = 6524; //选择入住日期

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CHECK_IN_DATE){ //选择入住日期
            if (resultCode == RESULT_OK){
//                Bundle bundle = data.getExtras();
//                checkInDate = bundle.getString("selectDate");
//                if (Utils.getCurrentTimeYMD().equals(checkInDate)){
//                    tvCheckInNotice.setText("今天入住");
//                }else{
//                    tvCheckInNotice.setText("今天入住");
//                }
//                tvCheckInDate.setText(checkInDate);
            }
        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, HotelMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
