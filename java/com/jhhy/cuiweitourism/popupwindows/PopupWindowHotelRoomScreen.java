package com.jhhy.cuiweitourism.popupwindows;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowHotelRoomScreen extends PopupWindow implements View.OnClickListener {

    private Context context;
    private TextView tvMeal; //不限
    private TextView tvBreakFast; //含早餐
    private TextView tvBreakFastSingle; //单份早餐
    private TextView tvBreakfastDouble; //双份早餐

    private TextView tvBedAll; //床型：不限
    private TextView tvBedLarge; //大床
    private TextView tvBedDouble; //双份早餐

    private int positionMeal = 1;
    private int positionBedType = 1;
    private String meal = "";
    private String bedType = "";

    private String TAG = PopupWindowHotelRoomScreen.class.getSimpleName();

    public PopupWindowHotelRoomScreen(final Context context) {
        this.context = context;

        View view = View.inflate(context, R.layout.popup_hotel_room_screen, null);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
//        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_2));
        setAnimationStyle(R.style.anim_menu_bottombar);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        showAsDropDown(parent, 0, -Utils.getScreenHeight(context));
        update();

        Button btnConfirm = (Button) view.findViewById(R.id.btn_hotel_room_screen_confirm);
        btnConfirm.setOnClickListener(this);

        tvMeal = (TextView) view.findViewById(R.id.tv_meal_all);
        tvBreakFast = (TextView) view.findViewById(R.id.tv_breakfast);
        tvBreakFastSingle = (TextView) view.findViewById(R.id.tv_breakfast_single);
        tvBreakfastDouble = (TextView) view.findViewById(R.id.tv_breakfast_double);
        tvBedAll = (TextView) view.findViewById(R.id.tv_bed_all);
        tvBedLarge = (TextView) view.findViewById(R.id.tv_bed_large);
        tvBedDouble = (TextView) view.findViewById(R.id.tv_bed_double);

        tvMeal.setOnClickListener(this);
        tvBreakFast.setOnClickListener(this);
        tvBreakFastSingle.setOnClickListener(this);
        tvBreakfastDouble.setOnClickListener(this);
        tvBedAll.setOnClickListener(this);
        tvBedLarge.setOnClickListener(this);
        tvBedDouble.setOnClickListener(this);

        tvMeal.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        tvBedAll.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_meal_all || view.getId() == R.id.tv_breakfast || view.getId() == R.id.tv_breakfast_single || view.getId() == R.id.tv_breakfast_double){
            tvMeal.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_unselected_corner_border));
            tvBreakFast.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_unselected_corner_border));
            tvBreakFastSingle.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_unselected_corner_border));
            tvBreakfastDouble.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_unselected_corner_border));
        }else if (view.getId() == R.id.tv_bed_all || view.getId() == R.id.tv_bed_large || view.getId() == R.id.tv_bed_double){
            tvBedAll.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_unselected_corner_border));
            tvBedLarge.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_unselected_corner_border));
            tvBedDouble.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_unselected_corner_border));
        }
        switch (view.getId()){
            case R.id.tv_meal_all:
                positionMeal = 1;
                meal = "";
                tvMeal.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_breakfast:
                positionMeal = 2;
                meal = "含单早，含双早，含三早";
                tvBreakFast.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_breakfast_single:
                positionMeal = 3;
                meal = "含单早，含双早，含三早";
                tvBreakFastSingle.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_breakfast_double:
                positionMeal = 4;
                meal = "含双早，含三早";
                tvBreakfastDouble.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_bed_all:
                positionBedType = 1;
                bedType = "";
                tvBedAll.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_bed_large:
                positionBedType = 2;
                bedType = "大床";
                tvBedLarge.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.tv_bed_double:
                positionBedType = 3;
                bedType = "双床，三张床";
                tvBedDouble.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
                break;
            case R.id.btn_hotel_room_screen_confirm: //确定，消失
                dismiss();
                break;
        }
    }

    public void refreshView(int positionMeal, int positionBedType){
        this.positionMeal = positionMeal;
        this.positionBedType = positionBedType;

        if (positionMeal == 1){
            tvMeal.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        }else if (positionMeal == 2){
            tvBreakFast.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        }else if (positionMeal == 3){
            tvBreakFastSingle.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        }else if (positionMeal == 4){
            tvBreakfastDouble.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        }

        if (positionBedType == 1){
            tvBedAll.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        }else if (positionBedType == 2){
            tvBedLarge.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        }else if (positionBedType == 3){
            tvBedDouble.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_et_city_selected_corner_border));
        }
    }

    public int getPositionMeal() {
        return positionMeal;
    }

    public int getPositionBedType() {
        return positionBedType;
    }
}
