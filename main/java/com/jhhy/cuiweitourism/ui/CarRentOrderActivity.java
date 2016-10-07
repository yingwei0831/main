package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jhhy.cuiweitourism.R;

public class CarRentOrderActivity extends BaseActivity implements View.OnClickListener {

    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取ActionBar对象
        ActionBar bar =  getSupportActionBar();
        //自定义一个布局，并居中
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_simple, null);
        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        setContentView(R.layout.activity_rent_car_order);
        setupView();
        addListener();
    }

    private void setupView() {
        btnNext = (Button) findViewById(R.id.btn_car_rent_order_next);
    }

    private void addListener() {
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_car_rent_order_next:
                CarRentSuccessActivity.actionStart(getApplicationContext(), null);
                break;
        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, CarRentOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
