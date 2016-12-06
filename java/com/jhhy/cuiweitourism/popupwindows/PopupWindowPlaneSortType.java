package com.jhhy.cuiweitourism.popupwindows;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jhhy.cuiweitourism.R;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowPlaneSortType extends PopupWindow implements View.OnClickListener {

//    private Activity activity;

    private int selection; //1:含税总价；2：票价+税价

    private String TAG = PopupWindowPlaneSortType.class.getSimpleName();
    private RadioGroup radioGroup;
    private RadioButton radioButtonSortDefault;
    private RadioButton radioButtonPriceIncrease;
    private RadioButton radioButtonFromIncrease     ;
    private RadioButton radioButtonFromDecrease     ;
    private RadioButton radioButtonArrivalIncrease  ;
    private RadioButton radioButtonArrivalDecrease  ;
    private RadioButton radioButtonConsumingIncrease;

    public PopupWindowPlaneSortType(final Context activity) {
        View view = View.inflate(activity, R.layout.pop_plane_outer_sort_type, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));
        setAnimationStyle(R.style.anim_menu_bottombar);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        setupView(view);
    }

    private void setupView(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.ll_popup);
        radioButtonSortDefault      = (RadioButton) view.findViewById(R.id.rb_plane_price_sort_direct);
        radioButtonPriceIncrease    = (RadioButton) view.findViewById(R.id.rb_plane_price_increase);
        radioButtonFromIncrease     = (RadioButton) view.findViewById(R.id.rb_plane_price_from_increase);
        radioButtonFromDecrease     = (RadioButton) view.findViewById(R.id.rb_plane_price_from_decrease);
        radioButtonArrivalIncrease  = (RadioButton) view.findViewById(R.id.rb_plane_price_arrival_increase);
        radioButtonArrivalDecrease  = (RadioButton) view.findViewById(R.id.rb_plane_price_arrival_decrease);
        radioButtonConsumingIncrease = (RadioButton) view.findViewById(R.id.rb_plane_price_consuming_increase);

        radioButtonSortDefault.setOnClickListener(this);
        radioButtonPriceIncrease.setOnClickListener(this);
        radioButtonFromIncrease     .setOnClickListener(this);
        radioButtonFromDecrease     .setOnClickListener(this);
        radioButtonArrivalIncrease  .setOnClickListener(this);
        radioButtonArrivalDecrease  .setOnClickListener(this);
        radioButtonConsumingIncrease.setOnClickListener(this);
    }

    public int getSelection(){
        return selection;
    }

    public void refreshView(int tag){
        this.selection = tag;
        if (tag == 1){
            radioButtonSortDefault.setChecked(true);
        } else if (2 == tag){
            radioButtonPriceIncrease.setChecked(true);
        } else if (3 == tag) {
            radioButtonFromIncrease.setChecked(true);
        } else if (4 == tag) {
            radioButtonFromDecrease.setChecked(true);
        } else if (5 == tag) {
            radioButtonArrivalIncrease.setChecked(true);
        } else if (6 == tag) {
            radioButtonArrivalDecrease.setChecked(true);
        } else if (7 == tag) {
            radioButtonConsumingIncrease.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_plane_price_sort_direct:
                selection = 1;
                break;
            case R.id.rb_plane_price_increase:
                selection = 2;
                break;
            case R.id.rb_plane_price_from_increase:
                selection = 3;
                break;
            case R.id.rb_plane_price_from_decrease:
                selection = 4;
                break;
            case R.id.rb_plane_price_arrival_increase:
                selection = 5;
                break;
            case R.id.rb_plane_price_arrival_decrease:
                selection = 6;
                break;
            case R.id.rb_plane_price_consuming_increase:
                selection = 7;
                break;
        }
        dismiss();
    }

}
