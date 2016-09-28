package com.jhhy.cuiweitourism.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.ui.BaseActivity;

import net.simonvt.numberpicker.NumberPicker;

public class CarRentTimeSelectActivity extends BaseActivity {


    private NumberPicker npYear;
    private NumberPicker npMonth;
    private NumberPicker npDay;
    private NumberPicker npHour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_car_rent_time_select);

        npYear = (NumberPicker) findViewById(R.id.date_picker_year);
        npYear.setMaxValue(20);
        npYear.setMinValue(0);
        npYear.setFocusable(true);
        npYear.setFocusableInTouchMode(true);

        npMonth = (NumberPicker) findViewById(R.id.date_picker_month);
        npMonth.setMaxValue(12);
        npMonth.setMinValue(01);
        npMonth.setFocusable(true);
        npMonth.setFocusableInTouchMode(true);

        npDay = (NumberPicker) findViewById(R.id.date_picker_day);
        npDay.setMaxValue(31);
        npDay.setMinValue(01);
        npDay.setFocusable(true);
        npDay.setFocusableInTouchMode(true);

        npHour = (NumberPicker) findViewById(R.id.date_picker_hour);
        npDay.setMaxValue(23);
        npDay.setMinValue(00);
        npDay.setFocusable(true);
        npDay.setFocusableInTouchMode(true);

    }
}
