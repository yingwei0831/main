package com.jhhy.cuiweitourism.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;

import net.simonvt.numberpicker.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class DatePickerActivity extends Activity implements View.OnClickListener {

    private String TAG = "DatePickerActivity";

    private NumberPicker npYear;
    private NumberPicker npMonth;
    private NumberPicker npDay;

    private TextView tvDate;
    private Button btnCancel;
    private Button btnConfirm;

    private int currentYear;
    private int currentMonth;
    private int currentDate;
    private long longdate; //日期的整数,毫秒值

    private int year;
    private int month;
    private int date;
    private String newString = null;
    private String week;

    private int type = -1; //2:火车票选时间
    private String dateToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_date_picker);

        getData();

        tvDate = (TextView) findViewById(R.id.tv_date_picker_select_time);
        btnCancel = (Button) findViewById(R.id.btn_date_picker_cancel);
        btnConfirm = (Button) findViewById(R.id.btn_date_picker_comfirm);

        npYear = (NumberPicker) findViewById(R.id.date_picker_year);
        npYear.setFocusable(true);
        npYear.setFocusableInTouchMode(true);

        npMonth = (NumberPicker) findViewById(R.id.date_picker_month);
        npMonth.setMaxValue(12);

        npMonth.setFocusable(true);
        npMonth.setFocusableInTouchMode(true);

        npDay = (NumberPicker) findViewById(R.id.date_picker_day);
        npDay.setMaxValue(31);

        npDay.setFocusable(true);
        npDay.setFocusableInTouchMode(true);

        initData();
        addListener();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                type = bundle.getInt("type");
            }
        }
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        calendar.setTimeZone(tz);
        year = calendar.get(Calendar.YEAR);
        month = (calendar.get(Calendar.MONTH) + 1);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        dateToday = String.format(Locale.getDefault(), "%d-%02d-%d", year, month, date);
//        LogUtil.e(TAG, "year = " + year +", month = " + month +", date = " + date);
        currentYear = year;
        currentMonth = month;
        currentDate = date;
        week = Utils.getDayOfStrE(calendar.get(Calendar.DAY_OF_WEEK));
        newString = String.format(Locale.getDefault(), "%d年%02d月%02d日 %s", year, month, date, Utils.getDayOfStr(calendar.get(Calendar.DAY_OF_WEEK))); //calendar.get(Calendar.DAY_OF_WEEK);
        longdate = Utils.getTime(String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, date));
//        LogUtil.e(TAG, "longdate = " + longdate +", currentTimeMillis = " + System.currentTimeMillis());
        npYear.setMaxValue(calendar.get(Calendar.YEAR) + 1);
        npYear.setMinValue(calendar.get(Calendar.YEAR));

        tvDate.setText(newString);
        npMonth.setMinValue(currentMonth);
        npMonth.setValue(month);
        npDay.setMinValue(currentDate);
        npDay.setValue(date);

        listenerMonth(month);
    }

    private void addListener() {
        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                listenerMonth(newVal);
                month = npMonth.getValue();
                date = npDay.getValue();
                if (month == currentMonth){
                    npDay.setMinValue(currentDate);
                }else{
                    npDay.setMinValue(1);
                }
                //xxxx年xx月xx日 星期X
                setPreviewDate();
            }
        });

        npDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                listenerMonth(month);
                date = npDay.getValue();
                //xxxx年xx月xx日 星期X
                setPreviewDate();
            }
        });
        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = npYear.getValue();
                if (year == currentYear){
                    npMonth.setMinValue(currentMonth);
                    npDay.setMinValue(currentDate);
                }else{
                    npMonth.setMinValue(1);
                    npDay.setMinValue(1);
                }
                //xxxx年xx月xx日 星期X
                setPreviewDate();
            }
        });
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void setPreviewDate() {
        Calendar c = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        c.setTimeZone(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        try {
            c.setTime(format.parse( String.format(Locale.getDefault(), "%d-%d-%d 00:00:00", year, month, date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        week = Utils.getDayOfStrE(c.get(Calendar.DAY_OF_WEEK));
        newString = String.format(Locale.getDefault(), "%d年%02d月%02d日 %s", year, month, date, Utils.getDayOfStr(c.get(Calendar.DAY_OF_WEEK)));
        longdate = Utils.getTime(String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, date));
//        LogUtil.e(TAG, "longdate = " + longdate +", currentTimeMillis = " + System.currentTimeMillis());
        tvDate.setText(newString);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_date_picker_cancel:
                finish();
                break;
            case R.id.btn_date_picker_comfirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        if (longdate < Utils.getTime(dateToday)){
            ToastUtil.show(getApplicationContext(), "请选择 "+dateToday+" 或以后的日期");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (type == 2) {
            bundle.putString("selectDate", String.format(Locale.getDefault(), "%d-%02d-%02d %s", year, month, date, week));
        }else{
            bundle.putString("selectDate", String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, date));
        }
        bundle.putLong("longdate", longdate);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void listenerMonth(int month) {
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                npDay.setMaxValue(31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                npDay.setMaxValue(30);
                break;
            case 2:
                int year = npYear.getValue();
                if ( year / 4 == 0 && year / 100 != 0 || year / 400 == 0){ //闰年29天
                    npDay.setMaxValue(29);
                } else { //平年28天
                    npDay.setMaxValue(28);
                }
                break;
        }
    }

}
