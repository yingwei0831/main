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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rent_select_type);

        setupView();
        addListener();
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("租车");
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

        tvRentCar.setOnClickListener(this);
        tvRentCarBusiness.setOnClickListener(this);
        tvRentCarComfortable.setOnClickListener(this);
        tvRentCarLuxury.setOnClickListener(this);
        tvRentCarriages.setOnClickListener(this);
        tvRentCarriagesKoste20.setOnClickListener(this);
        tvRentCarriagesKinglong35.setOnClickListener(this);
        tvRentCarriagesKinglong55.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;

            case R.id.tv_rent_car:
            case R.id.tv_car_business:
            case R.id.tv_car_comfortable:
            case R.id.tv_car_luxury: //租小车

                break;
            case R.id.tv_rent_carriages:
            case R.id.tv_carriages_20_koste:
            case R.id.tv_carriages_35_kinglong:
            case R.id.tv_carriages_55_kinglong: //租大车
                CarRentActivity.actionStart(getApplicationContext(), null);
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
