package com.jhhy.cuiweitourism.popupwindows;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelFirstListViewAdapter;
import com.jhhy.cuiweitourism.adapter.InnerTravelTripDaysListViewAdapter;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;

import java.util.ArrayList;
import java.util.List;


public class EditGenderPopupWindow extends PopupWindow{

    private FragmentActivity mActivity;

    private ListView listViewFirst;
    private ListView listViewSecond;

    private InnerTravelFirstListViewAdapter firstAdapter;

    private List<String> secondListDefault;
    private List<String> secondList1;
    private List<String> secondList3;
    private InnerTravelTripDaysListViewAdapter secondAdapter1;

    private List<String> firstList;

    private LinearLayout layoutStartTime;
    private RelativeLayout layoutStartTime1;
    private RelativeLayout layoutStartTime2;
    private TextView tvStartTime1;
    private TextView tvStartTime2;

//    private List<Fragment> listFragments;

//	private String[][] secondArray = {
//			{"不限", "1天", "2天", "3天", "4天", "5天", "6天", "7天", "8天", "9天", "10天", "10天以上"},
//			{}, {} };

    public EditGenderPopupWindow(FragmentActivity activity, View parent) {
        super(activity);
        mActivity = activity;
        View view = View.inflate(activity, R.layout.popup_edit_gender, null);
//        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
//        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

//        initView(view);
//        initData();
//        addListener();
    }


    private void initView(View view) {

    }

    private void initData() {

    }

    private void addListener() {


    }




}
