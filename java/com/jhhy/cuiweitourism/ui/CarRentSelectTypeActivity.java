package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

public class CarRentSelectTypeActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private TextView tvRentCar;
    private TextView tvRentCarBusiness;
    private TextView tvRentCarComfortable;
    private TextView tvRentCarLuxury;

    private TextView tvRentCarriages;
    private TextView tvRentCarriagesKoste20;
    private TextView tvRentCarriagesKinglong35;
    private TextView tvRentCarriagesKinglong55;

    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rent_select_type);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        city = getIntent().getExtras().getString("city");
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.car_rent_title));
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        tvRentCar = (TextView) findViewById(R.id.tv_rent_car);
        tvRentCarBusiness = (TextView) findViewById(R.id.tv_car_business);
        tvRentCarComfortable = (TextView) findViewById(R.id.tv_car_comfortable);
        tvRentCarLuxury = (TextView) findViewById(R.id.tv_car_luxury);

        tvRentCarriages = (TextView) findViewById(R.id.tv_rent_carriages);
        tvRentCarriagesKoste20 = (TextView) findViewById(R.id.tv_carriages_20_koste);
        tvRentCarriagesKinglong35 = (TextView) findViewById(R.id.tv_carriages_35_kinglong);
        tvRentCarriagesKinglong55 = (TextView) findViewById(R.id.tv_carriages_55_kinglong);

        SpannableString ss1 = new SpannableString("考斯特(20座)");
        ss1.setSpan(new AbsoluteSizeSpan(18), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new AbsoluteSizeSpan(12), 3, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRentCarriagesKoste20.setText(ss1);

        SpannableString ss2 = new SpannableString("金龙客车(35座)");
        ss2.setSpan(new AbsoluteSizeSpan(18), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new AbsoluteSizeSpan(12), 4, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRentCarriagesKinglong35.setText(ss2);

        SpannableString ss3 = new SpannableString("金龙客车(55座)");
        ss3.setSpan(new AbsoluteSizeSpan(16), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(new AbsoluteSizeSpan(12), 4, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRentCarriagesKinglong55.setText(ss3);

    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

//        tvRentCar.setOnClickListener(this);
        tvRentCarBusiness.setOnClickListener(this);
        tvRentCarComfortable.setOnClickListener(this);
        tvRentCarLuxury.setOnClickListener(this);
//        tvRentCarriages.setOnClickListener(this);
        tvRentCarriagesKoste20.setOnClickListener(this);
        tvRentCarriagesKinglong35.setOnClickListener(this);
        tvRentCarriagesKinglong55.setOnClickListener(this);
    }

    private int position = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;

//            case R.id.tv_rent_car:
//                position = 0;
//                Bundle bundle0 = new Bundle();
//                bundle0.putInt("position", position);
//                bundle0.putString("city", city);
//                CarRentSmallActivity.actionStart(getApplicationContext(), bundle0);
//                break;
            case R.id.tv_car_business:
                position = 1;
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position", position);
                bundle1.putString("city", city);
                CarRentSmallActivity.actionStart(getApplicationContext(), bundle1);
                break;
            case R.id.tv_car_comfortable:
                position = 2;
                Bundle bundle2 = new Bundle();
                bundle2.putInt("position", position);
                bundle2.putString("city", city);
                CarRentSmallActivity.actionStart(getApplicationContext(), bundle2);
                break;
            case R.id.tv_car_luxury: //租小车
                position = 3;
                Bundle bundle3 = new Bundle();
                bundle3.putInt("position", position);
                bundle3.putString("city", city);
                CarRentSmallActivity.actionStart(getApplicationContext(), bundle3);
                break;

//            case R.id.tv_rent_carriages:
//                position = 0;
//                Bundle bundle4 = new Bundle();
//                bundle4.putInt("position", position);
//                CarRentMainActivity.actionStart(getApplicationContext(), bundle4);
//                break;
            case R.id.tv_carriages_20_koste:
                position = 1;
                Bundle bundle5 = new Bundle();
                bundle5.putInt("position", position);
                CarRentActivity.actionStart(getApplicationContext(), bundle5);
                break;
            case R.id.tv_carriages_35_kinglong:
                position = 2;
                Bundle bundle6 = new Bundle();
                bundle6.putInt("position", position);
                CarRentActivity.actionStart(getApplicationContext(), bundle6);
                break;
            case R.id.tv_carriages_55_kinglong: //租大车
                position = 3;
                Bundle bundle7 = new Bundle();
                bundle7.putInt("position", position);
                CarRentActivity.actionStart(getApplicationContext(), bundle7);
                break;
        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, CarRentSelectTypeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
