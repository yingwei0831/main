package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;

public class AboutCuiweiActivity extends BaseActionBarActivity {

    private TextView aboutUs;
    private TextView contactUs;
    private TextView joinUs;
    private TextView aboutIcon;
    private TextView statement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cuiwei);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.fragment_mine_about_cuiwei));
        aboutUs = (TextView) findViewById(R.id.tv_about_us);
        contactUs = (TextView) findViewById(R.id.tv_contact_us);
        joinUs = (TextView) findViewById(R.id.tv_join_us_rule);
        aboutIcon = (TextView) findViewById(R.id.tv_about_travel_icon);
        statement = (TextView) findViewById(R.id.tv_about_law_statement);
    }

    @Override
    protected void addListener() {
        super.addListener();
        aboutUs.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        joinUs.setOnClickListener(this);
        aboutIcon.setOnClickListener(this);
        statement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_about_us:

                break;
            case R.id.tv_contact_us:

                break;
            case R.id.tv_join_us_rule:

                break;
            case R.id.tv_about_travel_icon:

                break;
            case R.id.tv_about_law_statement:

                break;
        }
    }
}
