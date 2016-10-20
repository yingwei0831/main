package com.jhhy.cuiweitourism.dialog;

import android.app.Activity;
import android.content.Context;
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


public class NumberPickerActivity extends Activity implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();

    private TextView tvCancel; //取消
    private TextView tvConfirm; //确认
    private TextView tvClear; //清空

    private NumberPicker numberPicker;
    private boolean commit = true;
    private int value = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_number_picker);
        getData();
        super.onCreate(savedInstanceState);

        tvCancel = (TextView) findViewById(R.id.title_main_tv_left_location);
        tvConfirm = (TextView) findViewById(R.id.title_main_iv_right_telephone);
        tvClear = (TextView) findViewById(R.id.tv_clear);

        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setFocusable(true);
        numberPicker.setFocusableInTouchMode(true);
        numberPicker.setMaxValue(9);
        numberPicker.setMinValue(1);
        numberPicker.setValue(value);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                value = bundle.getInt("value");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location: //取消
                finish();
                break;
            case R.id.title_main_iv_right_telephone: //确定
                commit = true;

                finish();
                break;
            case R.id.tv_clear: //清空
                clear();
                break;
        }
    }

    private void clear() {
        numberPicker.setValue(1);
    }

    public void refreshView(int value){
        numberPicker.setValue(value);
    }


    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, NumberPickerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
