package com.jhhy.cuiweitourism.popupwindows;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.picture.TestPicActivity;
import com.jhhy.cuiweitourism.ui.Tab4AccountCertificationActivity;

import net.simonvt.numberpicker.NumberPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowNumberPicker extends PopupWindow implements View.OnClickListener {

    private Activity activity;

    private TextView tvCancel; //取消
    private TextView tvConfirm; //确认
    private TextView tvClear; //清空

    private NumberPicker numberPicker;
    private boolean commit = true;

    public PopupWindowNumberPicker(final Activity activity, View parent) {
        this.activity = activity;

        View view = View.inflate(activity, R.layout.pop_number_picker, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        setupView(view);
        addListener();
    }

    private void addListener() {
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    private void setupView(View view) {
        tvCancel = (TextView) view.findViewById(R.id.title_main_tv_left_location);
        tvConfirm = (TextView) view.findViewById(R.id.title_main_iv_right_telephone);
        tvClear = (TextView) view.findViewById(R.id.tv_clear);

        numberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
        numberPicker.setFocusable(true);
        numberPicker.setFocusableInTouchMode(true);
        numberPicker.setMaxValue(9);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);
//        listViewFirst = (ListView) view.findViewById(R.id.listViewFirst);
//        listViewFirst.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1));
    }

    public int getValue(){
        return numberPicker.getValue();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location: //取消
                dismiss();
                break;
            case R.id.title_main_iv_right_telephone: //确定
                commit = true;
                dismiss();
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

}
