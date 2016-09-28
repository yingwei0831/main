package com.jhhy.cuiweitourism.popupwindows;


import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.Utils;


public class AllOrdersPopupWindow extends PopupWindow implements View.OnClickListener {

    private FragmentActivity mActivity;

    private View popView;
    private TextView tv1, tv2, tv3,
                    tv4, tv5, tv6,
                    tv7, tv8;

    private int selection = 0;

    public AllOrdersPopupWindow(FragmentActivity activity, View parent) {
        super(activity);
        mActivity = activity;
        popView = View.inflate(activity, R.layout.popup_all_orders, null);
        popView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(popView);
        showAsDropDown(parent, 0, 0);
        update();

        initView();
        initData();
        addListener();
    }


    private void initView() {
        tv1 = (TextView) popView.findViewById(R.id.rb_1_1);
        tv2 = (TextView) popView.findViewById(R.id.rb_1_2);
        tv3 = (TextView) popView.findViewById(R.id.rb_1_3);
        tv4 = (TextView) popView.findViewById(R.id.rb_2_1);
        tv5 = (TextView) popView.findViewById(R.id.rb_2_2);
        tv6 = (TextView) popView.findViewById(R.id.rb_2_3);
        tv7 = (TextView) popView.findViewById(R.id.rb_3_1);
        tv8 = (TextView) popView.findViewById(R.id.rb_3_2);
    }

    private void initData() {
        tv1.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
    }

    private void addListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
    }

    public int getCheckButton(){
        return selection;
    }

    public void refreshData(int selection){
        tv1.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv2.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv3.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv4.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv5.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv6.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv7.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv8.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        switch (selection){
            case 0:
                tv1.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
            case 1:
                tv2.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
            case 2:
                tv3.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
            case 3:
                tv4.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
            case 4:
                tv5.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
            case 5:
                tv6.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
            case 6:
                tv7.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
            case 7:
                tv8.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                break;
        }
    }

    private String TAG = getClass().getSimpleName();

    @Override
    public void onClick(View view) {
        tv1.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv2.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv3.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv4.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv5.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv6.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv7.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));
        tv8.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_rb_corner_border_selector));

        switch (view.getId()){
            case R.id.rb_1_1:
                tv1.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 0;
                break;
            case R.id.rb_1_2:
                tv2.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 1;
                break;
            case R.id.rb_1_3:
                tv3.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 2;
                break;
            case R.id.rb_2_1:
                tv4.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 3;
                break;
            case R.id.rb_2_2:
                tv5.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 4;
                break;
            case R.id.rb_2_3:
                tv6.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 5;
                break;
            case R.id.rb_3_1:
                tv7.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 6;
                break;
            case R.id.rb_3_2:
                tv8.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_et_city_selected_corner_border));
                selection = 7;
                break;
        }
        dismiss();
    }
}
