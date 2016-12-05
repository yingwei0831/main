package com.jhhy.cuiweitourism.popupwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaTypeListAdapter;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/10.
 */
public class PopupWindowPlanePriceType extends PopupWindow implements View.OnClickListener {

//    private Activity activity;

    private int selection; //1:含税总价；2：票价+税价

    private String TAG = PopupWindowPlanePriceType.class.getSimpleName();
    private RadioGroup radioGroup;
    private RadioButton radioButtonTax;
    private RadioButton radioButtonSeparate;

    public PopupWindowPlanePriceType(final Context activity) {
//        this.activity = activity;

        View view = View.inflate(activity, R.layout.pop_plane_outer_price_type, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
//        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
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
        radioButtonTax = (RadioButton) view.findViewById(R.id.rb_plane_price_type_tax_total);
        radioButtonSeparate = (RadioButton) view.findViewById(R.id.rb_plane_price_type_separate_price);
        radioButtonTax.setOnClickListener(this);
        radioButtonSeparate.setOnClickListener(this);
    }

    public int getSelection(){
        return selection;
    }

    public void refreshView(int tag){
        if (tag == 1){
            radioButtonTax.setChecked(true);
        }else {
            radioButtonSeparate.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_plane_price_type_tax_total:
                selection = 1;
                break;
            case R.id.rb_plane_price_type_separate_price:
                selection = 2;
                break;
        }
        dismiss();
    }

}
