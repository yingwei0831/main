package com.jhhy.cuiweitourism.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.utils.Utils;

import net.simonvt.numberpicker.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DatePickerActivity extends Activity implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private NumberPicker npYear;
    private NumberPicker npMonth;
    private NumberPicker npDay;

    private TextView tvDate;
    private Button btnCancel;
    private Button btnConfirm;

    private int year;
    private int month;
    private int date;
    private String newString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_date_picker);

        tvDate = (TextView) findViewById(R.id.tv_date_picker_select_time);
        btnCancel = (Button) findViewById(R.id.btn_date_picker_cancel);
        btnConfirm = (Button) findViewById(R.id.btn_date_picker_comfirm);

        npYear = (NumberPicker) findViewById(R.id.date_picker_year);
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

        initData();
        addListener();
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = (calendar.get(Calendar.MONTH) + 1);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        newString = String.format("%d年%02d月%02d日 %s", year, month, date, Utils.getDayOfStr(calendar.get(Calendar.DAY_OF_WEEK))); //calendar.get(Calendar.DAY_OF_WEEK);
        npYear.setMaxValue(calendar.get(Calendar.YEAR) + 2);
        npYear.setMinValue(calendar.get(Calendar.YEAR));

        tvDate.setText(newString);
        npMonth.setValue(month);
        npDay.setValue(date);
    }

    private void addListener() {

        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal){
                    case 01:
                    case 03:
                    case 05:
                    case 07:
                    case 8:
                    case 10:
                    case 12:
                        npDay.setMaxValue(31);
                        break;
                    case 04:
                    case 06:
                    case 9:
                    case 11:
                        npDay.setMaxValue(30);
                        break;
                    case 02:
                        int year = npYear.getValue();
                        if ( year / 4 == 0 && year / 100 != 0 || year / 400 == 0){
                            npDay.setMaxValue(29);
                        } else {
                            npDay.setMaxValue(28);
                        }
                        break;
                }
                month = npMonth.getValue();
                date = npDay.getValue();
                //xxxx年xx月xx日 星期X
                setPreviewDate();
            }
        });

        npDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                date = npDay.getValue();
                //xxxx年xx月xx日 星期X
                setPreviewDate();
            }
        });
        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = npYear.getValue();
                //xxxx年xx月xx日 星期X
                setPreviewDate();
            }
        });
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void setPreviewDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            c.setTime(format.parse( String.format("%d-%d-%d 00:00:00", year, month, date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newString = String.format("%d年%02d月%02d日 %s", year, month, date, Utils.getDayOfStr(c.get(Calendar.DAY_OF_WEEK)));
        tvDate.setText(newString);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_date_picker_cancel:
                finish();
                break;
            case R.id.btn_date_picker_comfirm:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("selectDate", String.format("%d-%02d-%02d", year, month, date));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
