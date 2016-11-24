package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.net.biz.ActivityActionBiz;
import com.jhhy.cuiweitourism.net.biz.HomePageActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ActivityOrder;
import com.jhhy.cuiweitourism.net.models.FetchModel.HomePageCustomAdd;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityOrderInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class PersonalizedCustomStartActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = PersonalizedCustomStartActivity.class.getSimpleName();
    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private EditText etSelectFromCity;  //选择出发城市
    private EditText etSelectToCity;    //选择目的城市
    private TextView tvSelectFromDate;  //选择出发日期

    private TextView tvDays;            //行程天数
    private TextView tvAdultNum;        //成人人数
    private TextView tvChildNum;        //儿童人数

    private ImageView ivDaySum;         //天数增加
    private ImageView ivDayReduce;      //天数减少
    private ImageView ivAdultSum;       //成人增加
    private ImageView ivAdultReduce;    //成人减少
    private ImageView ivChildSum;       //儿童增加
    private ImageView ivChildReduce;    //儿童减少

    private EditText etPriceLow;        //最低价格
    private EditText etPriceHigh;       //最高价格

    private TextView tvHotelTypeLuxury;         //豪华型
    private TextView tvHotelTypeLuxurious;      //奢华型
    private TextView tvHotelTypeComfort;        //舒适型
    private TextView tvHotelTypeEconomical;     //经济型
    private TextView tvHotelTypeBB;             //民宿型

    private EditText etRemark;  //备注
    private EditText etName;    //姓名
    private EditText etMobile;  //手机号
    private EditText etMail;    //邮箱

    private Button btnCommit;

    private int day = 1;
    private int adultNum = 1;
    private int childNum = 0;

    private PhoneBean selectCity; //从主页传过来的城市
    private PhoneBean selectFromCity; //出发城市
    private String selectFromCityName;
    private PhoneBean selectToCity; //出目的城市
    private String selectToCityName;

    private String selectDate; //出发时间
    private String type; //车型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalized_custom_start);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        selectCity = (PhoneBean) getIntent().getExtras().getSerializable("selectCity");
        if (selectCity == null){
            selectCity = new PhoneBean();
            selectCity.setName("北京");
            selectCity.setCity_id("20");
        }
    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText("开始定制");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        etSelectFromCity    = (EditText) findViewById(R.id.et_select_from_city);
        etSelectToCity      = (EditText) findViewById(R.id.et_select_to_city);
        tvSelectFromDate    = (TextView) findViewById(R.id.tv_select_from_date);

        ivDaySum =      (ImageView) findViewById(R.id.tv_price_calendar_day_number_reduce);
        ivDayReduce =   (ImageView) findViewById(R.id.tv_price_calendar_day_number_add);
        ivAdultSum =    (ImageView) findViewById(R.id.tv_price_calendar_number_reduce);
        ivAdultReduce = (ImageView) findViewById(R.id.tv_price_calendar_number_add);
        ivChildSum =    (ImageView) findViewById(R.id.tv_price_calendar_number_reduce_child);
        ivChildReduce = (ImageView) findViewById(R.id.tv_price_calendar_number_add_child);

        tvDays     = (TextView) findViewById(R.id.tv_price_calendar_day_number);
        tvAdultNum = (TextView) findViewById(R.id.tv_price_calendar_number);
        tvChildNum = (TextView) findViewById(R.id.tv_price_calendar_number_child);

        tvHotelTypeLuxury    = (TextView) findViewById(R.id.tv_hotel_type_luxury);
        tvHotelTypeLuxurious = (TextView) findViewById(R.id.tv_personalized_custom_hotel_luxury);
        tvHotelTypeComfort   = (TextView) findViewById(R.id.tv_personalized_custom_hotel_comfortable);
        tvHotelTypeEconomical= (TextView) findViewById(R.id.tv_personalized_custom_hotel_economical);
        tvHotelTypeBB        = (TextView) findViewById(R.id.tv_personalized_custom_hotel_bb);

        etPriceLow  = (EditText) findViewById(R.id.et_customized_price_lower);
        etPriceHigh = (EditText) findViewById(R.id.et_customized_price_higher);

        etRemark = (EditText) findViewById(R.id.et_personalized_custom_remarks);
        etName   = (EditText) findViewById(R.id.et_customized_name);
        etMobile = (EditText) findViewById(R.id.et_customized_mobile);
        etMail   = (EditText) findViewById(R.id.et_customized_mail);

        btnCommit = (Button) findViewById(R.id.btn_commit_custom);

        tvDays.setText(String.valueOf(day));
        tvAdultNum.setText(String.valueOf(adultNum));
        tvChildNum.setText(String.valueOf(childNum));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.btn_commit_custom: //提交个性定制
                commit();
                break;
//            case R.id.et_select_from_city: //选择出发城市
//                Intent intentCity = new Intent(getApplicationContext(), CitySelectionActivity.class);
//                Bundle bundleCity = new Bundle();
//                bundleCity.putString("currentCity", selectCity.getName());
//                intentCity.putExtras(bundleCity);
//                startActivityForResult(intentCity, Consts.REQUEST_CODE_SELECT_CITY);
//                break;
//            case R.id.et_select_to_city: //选择目的城市
//                Intent intentCityTo = new Intent(getApplicationContext(), CitySelectionActivity.class);
//                Bundle bundleCityTo = new Bundle();
//                bundleCityTo.putString("currentCity", selectCity.getName());
//                intentCityTo.putExtras(bundleCityTo);
//                startActivityForResult(intentCityTo, REQUEST_CODE_SELECT_TO_CITY);
//                break;
            case R.id.tv_select_from_date: //选择出发日期
                Intent intentTime = new Intent(getApplicationContext(), DatePickerActivity.class);
                startActivityForResult(intentTime, SELECT_TIME);
                break;
            case R.id.tv_price_calendar_day_number_add: //天数增加
                addDays();
                break;
            case R.id.tv_price_calendar_day_number_reduce: //天数减少
                reduceDays();
                break;
            case R.id.tv_price_calendar_number_add:  //成人增加
                addAdultNum();
                break;
            case R.id.tv_price_calendar_number_reduce: //成人减少
                reduceAdultNum();
                break;
            case R.id.tv_price_calendar_number_add_child: //儿童增加
                addChildNum();
                break;
            case R.id.tv_price_calendar_number_reduce_child: //儿童减少
                reduceChildNum();
                break;
            case R.id.tv_hotel_type_luxury: //豪华型
                changeType(1);
                break;
            case R.id.tv_personalized_custom_hotel_luxury: //奢华型
                changeType(2);
                break;
            case R.id.tv_personalized_custom_hotel_comfortable: //舒适型
                changeType(3);
                break;
            case R.id.tv_personalized_custom_hotel_economical: //经济型
                changeType(4);
                break;
            case R.id.tv_personalized_custom_hotel_bb: //民宿型
                changeType(5);
                break;
        }
    }

    private void commit() {
        selectFromCityName = selectCity.getName();
        if (selectFromCityName == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入目的城市");
            return;
        }
        if (selectToCityName == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入目的城市");
            return;
        }
        if (selectDate == null){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择出发日期");
            return;
        }
        String priceLower = etPriceLow.getText().toString();
        if (priceLower == null || priceLower.length() == 0){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入最低预算");
            return;
        }
        String priceHigher = etPriceHigh.getText().toString();
        if (priceHigher == null || priceHigher.length() == 0){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入最高预算");
            return;
        }
        if (type == null || type.length() == 0){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请选择酒店类型");
            return;
        }
        String name = etName.getText().toString();
        if (name == null || name.trim().length() == 0){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入姓名");
            return;
        }
        String mobile = etMobile.getText().toString();
        if (mobile == null || mobile.trim().length() == 0){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入手机号");
            return;
        }
        String mail = etMail.getText().toString();
        if (mail == null || mail.trim().length() == 0){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入邮箱");
            return;
        }

        String remark = etRemark.getText().toString();
        HomePageCustomAdd add = new HomePageCustomAdd(selectFromCityName, selectToCityName, selectDate,
                String.valueOf(day), String.valueOf(adultNum), String.valueOf(childNum),
                priceLower +","+priceHigher, type, remark, name, mobile, mail);
        HomePageActionBiz homePageBiz = new HomePageActionBiz();
        homePageBiz.homePageCustomAdd(add, new BizGenericCallback<ArrayList<Object>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<Object>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<Object> array = model.body;
                    LogUtil.e(TAG,"homePageCustomAdd =" + array.toString());
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                    finish();
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, " homePageCustomAdd :" + error.toString());
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求失败，请返回重试");
                }
            }
        });

    }

    private void changeType(int tag) {
        tvHotelTypeLuxury    .setBackgroundResource(R.drawable.bg_tv_border_cornet_light);
        tvHotelTypeLuxurious .setBackgroundResource(R.drawable.bg_tv_border_cornet_light);
        tvHotelTypeComfort   .setBackgroundResource(R.drawable.bg_tv_border_cornet_light);
        tvHotelTypeEconomical.setBackgroundResource(R.drawable.bg_tv_border_cornet_light);
        tvHotelTypeBB        .setBackgroundResource(R.drawable.bg_tv_border_cornet_light);
        if (tag == 1){
            tvHotelTypeLuxury    .setBackgroundResource(R.drawable.bg_tv_border_cornet_dark);
            type = tvHotelTypeLuxury.getText().toString();
        }else if (tag == 2){
            tvHotelTypeLuxurious .setBackgroundResource(R.drawable.bg_tv_border_cornet_dark);
            type = tvHotelTypeLuxurious.getText().toString();
        }else if (tag == 3){
            tvHotelTypeComfort   .setBackgroundResource(R.drawable.bg_tv_border_cornet_dark);
            type = tvHotelTypeComfort.getText().toString();
        }else if (tag == 4){
            tvHotelTypeEconomical.setBackgroundResource(R.drawable.bg_tv_border_cornet_dark);
            type = tvHotelTypeEconomical.getText().toString();
        }else if (tag == 5){
            tvHotelTypeBB        .setBackgroundResource(R.drawable.bg_tv_border_cornet_dark);
            type = tvHotelTypeBB.getText().toString();
        }

    }

    //增加成人数量
    private void addAdultNum() {
        adultNum += 1;
        tvAdultNum.setText(String.valueOf(adultNum));
    }
    //减少成人数量
    private void reduceAdultNum() {
        if (adultNum > 1){
            adultNum -= 1;
            tvAdultNum.setText(String.valueOf(adultNum));
        }
    }
    //增加儿童数量
    private void addChildNum() {
        childNum += 1;
        tvChildNum.setText(String.valueOf(childNum));
    }
    //减少儿童数量
    private void reduceChildNum() {
        if (childNum > 0){
            childNum -= 1;
            tvChildNum.setText(String.valueOf(childNum));
        }
    }

    //增加天数
    private void addDays() {
        day += 1;
        tvDays.setText(String.valueOf(day));
    }

    //减少天数
    private void reduceDays() {
        if (day > 1){
            day -= 1;
            tvDays.setText(String.valueOf(day));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Consts.REQUEST_CODE_SELECT_CITY){ //选择出发城市
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                selectFromCity = (PhoneBean) bundle.getSerializable("city");
                if (selectFromCity == null){
                    selectFromCityName = bundle.getString("selectCity");
                }else{
                    selectFromCityName = selectFromCity.getName();
                }
                etSelectFromCity.setText(selectFromCityName);
            }
        }else if (requestCode == REQUEST_CODE_SELECT_TO_CITY){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                selectToCity = (PhoneBean) bundle.getSerializable("city");
                if (selectToCity == null){
                    selectToCityName = bundle.getString("selectCity");
                }else{
                    selectToCityName = selectToCity.getName();
                }
                etSelectToCity.setText(selectToCityName);
            }
        }else if (requestCode == SELECT_TIME){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                selectDate = bundle.getString("selectDate");
                tvSelectFromDate.setText(selectDate);
            }
        }
    }

    private int REQUEST_CODE_SELECT_TO_CITY = 6635; //选择目的城市
    private int SELECT_TIME = 6636; //选择出发时间

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
//        etSelectFromCity.setOnClickListener(this);
//        etSelectToCity  .setOnClickListener(this);
        tvSelectFromDate.setOnClickListener(this);

        ivDaySum.setOnClickListener(this);
        ivDayReduce.setOnClickListener(this);
        ivAdultSum.setOnClickListener(this);
        ivAdultReduce.setOnClickListener(this);
        ivChildSum.setOnClickListener(this);
        ivChildReduce.setOnClickListener(this);

        tvHotelTypeLuxury    .setOnClickListener(this);
        tvHotelTypeLuxurious .setOnClickListener(this);
        tvHotelTypeComfort   .setOnClickListener(this);
        tvHotelTypeEconomical.setOnClickListener(this);
        tvHotelTypeBB        .setOnClickListener(this);

        btnCommit.setOnClickListener(this);
    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PersonalizedCustomStartActivity.class);
        if(bundle != null){
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
