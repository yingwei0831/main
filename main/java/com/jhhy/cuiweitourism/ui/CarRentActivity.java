package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jhhy.cuiweitourism.R;

public class CarRentActivity extends BaseActivity implements View.OnClickListener {

    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        setupView();
        addListener();
    }

    private void setupView() {
        btnNext = (Button) findViewById(R.id.btn_car_rent_next);
    }

    private void addListener() {
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_car_rent_next:
                CarRentOrderActivity.actionStart(getApplicationContext(), null);
                break;
        }
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, CarRentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
