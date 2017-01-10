package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;


public class BaseActionBarActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView ivTitleLeft;
    protected ImageView ivTitleRight;
    protected TextView tvTitle;
    protected ActionBar actionBar;
    protected TextView tvPlaneDate;
    protected TextView tvTitleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(android.R.color.white));
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影
        setupView();
        addListener();
    }

    protected void addListener() {
        ivTitleLeft.setOnClickListener(this);
        ivTitleRight.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
    }

    protected void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvPlaneDate = (TextView) actionBar.getCustomView().findViewById(R.id.tv_select_plane_date);
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);
        ivTitleRight = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_iv_right_telephone);
        tvTitleRight = (TextView) actionBar.getCustomView().findViewById(R.id.tv_hotel_reserve_rules);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;

        }
    }
}
