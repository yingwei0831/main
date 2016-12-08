package com.jhhy.cuiweitourism.popupwindows;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;

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

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelFirstListViewAdapter;
import com.jhhy.cuiweitourism.adapter.InnerTravelTripDaysListViewAdapter;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.model.PriceArea;
import com.jhhy.cuiweitourism.ui.InnerTravelCityActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class InnerTravelPopupWindow extends PopupWindow implements OnClickListener {

    private Activity mActivity;
    private String TAG = InnerTravelPopupWindow.class.getSimpleName();

    private ListView listViewFirst; //主
    private ListView listViewSecond; //从

    private InnerTravelFirstListViewAdapter firstAdapter; //主
    private InnerTravelTripDaysListViewAdapter secondAdapter1; //从

    private List<String> firstList; //主
    private List<String> secondListDefault; //从：默认排序

    private List<String> secondListDayString = new ArrayList<>(); //行程天数 （构造数据）
    private List<String> secondListPriceString = new ArrayList<>(); //价格筛选(构造数据)
    private List<String> secondListDaysS; //行程天数 （原数据）
    private List<PriceArea> secondListPriceS; //价格筛选(原数据)


    private LinearLayout layoutStartTime; //从：出发时间
    private RelativeLayout layoutStartTime1; //最早出发时间
    private RelativeLayout layoutStartTime2; //最晚出发时间

    private TextView tvCancel; //取消
    private TextView tvConfirm; //确认
    private TextView tvClear; //清空
    private boolean isClear;

    private int selectionSort = 0;
    private int selectionDays = -1;
    private int selectionPrice = -1;

    public InnerTravelPopupWindow(Activity activity, View parent, int tag, List<String> secondListDays, List<PriceArea> secondListPrice) {
        super(activity);
        mActivity = activity;
        View view = View.inflate(activity, R.layout.inner_travel_popupwindow, null);
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        this.secondListDaysS = secondListDays;
        for (String s : secondListDays){
            secondListDayString.add(s+"天");
        }

        this.secondListPriceS = secondListPrice;
        if (secondListPrice != null && secondListPrice.size() != 0) {
            for (int i = 0; i < secondListPrice.size(); i++) {
                PriceArea price = secondListPrice.get(i);
                secondListPriceString.add(String.format("%s-%s元", price.getPriceLower(), price.getPriceHigh()));
            }
        }

        initView(view);
        initData();
        addListener();
        initFirstListView(tag);
    }

    private void initFirstListView(int tag) {
        firstAdapter = new InnerTravelFirstListViewAdapter(mActivity, firstList);

        firstAdapter.setSelectPosition(tag - 1);
        listViewFirst.setAdapter(firstAdapter);
        initSecondListView(tag, 0);

        listViewFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                updataListView(position);
            }
        });

    }

    private void initSecondListView(int tag, int position) {
        if (1 == tag) {
            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListDefault);
            secondAdapter1.setSelectPosition(position);
        } else if (2 == tag) {
            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListDayString);
            secondAdapter1.setSelectPosition(selectionDays);
        } else if (3 == tag) {
            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListDefault); //防止空指针

            listViewSecond.setVisibility(View.GONE);
            layoutStartTime.setVisibility(View.VISIBLE);
        } else if (4 == tag) {
            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListPriceString);
            secondAdapter1.setSelectPosition(selectionPrice);
        }

        listViewSecond.setAdapter(secondAdapter1);
        listViewSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e(TAG, "position = " + position);
                updataListView2(position);
            }
        });
//        initThirdListView(position);
    }

    /**
     * 第一级ListViewItem单击事件
     *
     * @param position
     */
    private void updataListView(int position) {
        firstAdapter.setSelectPosition(position);
        firstAdapter.notifyDataSetChanged();
        //更新第二ListView
        if (0 == position) {
            listViewSecond.setVisibility(View.VISIBLE);
            layoutStartTime.setVisibility(View.GONE);
            secondAdapter1.setData(secondListDefault);
            secondAdapter1.setSelectPosition(selectionSort);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionSort);
//            updataListView2(selectionSort);
        } else if (1 == position) {
            listViewSecond.setVisibility(View.VISIBLE);
            layoutStartTime.setVisibility(View.GONE);
            secondAdapter1.setData(secondListDayString);
            secondAdapter1.setSelectPosition(selectionDays);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionDays);
//            updataListView2(selectionDays);
        } else if (2 == position) {
            listViewSecond.setVisibility(View.GONE);
            layoutStartTime.setVisibility(View.VISIBLE);
        } else if (3 == position) {
            listViewSecond.setVisibility(View.VISIBLE);
            layoutStartTime.setVisibility(View.GONE);
            secondAdapter1.setData(secondListPriceString);
            secondAdapter1.setSelectPosition(selectionPrice);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionPrice);
//            updataListView2(selectionPirce);
        }

    }

    /**
     * 第二级ListViewItem单击事件
     * position 第一级ListView的位置
     */
    private void updataListView2(int position) {
        LogUtil.e(TAG, "isClear = " + isClear + ", position = " + position);
        if (isClear) {
            isClear = false;
        }
//            secondAdapter1.setSelectPosition(-1);
//            secondAdapter1.notifyDataSetChanged();
//            listViewSecond.smoothScrollToPosition(0);
//        }else{
            if (mActivity.getResources().getString(R.string.tab1_inner_travel_sort_default).equals(firstAdapter.getCurrentPositionItem())) { //弹出排序选择
                selectionSort = position;
                InnerTravelCityActivity.sort = String.valueOf(position);
            }else if (mActivity.getResources().getString(R.string.tab1_inner_travel_trip_days).equals(firstAdapter.getCurrentPositionItem())){ //弹出行程天数
                selectionDays = position;
                InnerTravelCityActivity.day = secondListDaysS.get(position);
            }else if (mActivity.getResources().getString(R.string.tab1_inner_travel_price_screen).equals(firstAdapter.getCurrentPositionItem())){ //弹出价格筛选
                selectionPrice = position;
                InnerTravelCityActivity.price = secondListPriceS.get(position).getPriceLower() +"," + secondListPriceS.get(position).getPriceHigh();
            }
            secondAdapter1.setSelectPosition(position);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(position);
//        }
    }

    private void initView(View view) {
        tvCancel = (TextView) view.findViewById(R.id.title_main_tv_left_location);
        tvConfirm = (TextView) view.findViewById(R.id.title_main_iv_right_telephone);
        tvClear = (TextView) view.findViewById(R.id.tv_clear);

        listViewFirst = (ListView) view.findViewById(R.id.listViewFirst);
        listViewSecond = (ListView) view.findViewById(R.id.listViewSecond);
        listViewSecond.setVisibility(View.VISIBLE);
        layoutStartTime = (LinearLayout) view.findViewById(R.id.layout_second_start_time);
        layoutStartTime.setVisibility(View.GONE);
        layoutStartTime1 = (RelativeLayout) view.findViewById(R.id.layout_start_time_1);
        layoutStartTime2 = (RelativeLayout) view.findViewById(R.id.layout_start_time_2);

//        tvEarlyTime = (TextView) view.findViewById(R.id.inner_trip_start_time_1);
//        tvLaterTime = (TextView) view.findViewById(R.id.inner_trip_start_time_2);
    }

    private void initData() {
        firstList = new ArrayList<>();
        firstList.add(mActivity.getString(R.string.tab1_inner_travel_sort_default));
        firstList.add(mActivity.getString(R.string.tab1_inner_travel_trip_days));
        firstList.add(mActivity.getString(R.string.tab1_inner_travel_start_time));
        firstList.add(mActivity.getString(R.string.tab1_inner_travel_price_screen));

        secondListDefault = new ArrayList<>();
        secondListDefault.add(mActivity.getString(R.string.tab1_inner_travel_pop_sort_default));
        secondListDefault.add(mActivity.getString(R.string.tab1_inner_travel_pop_sort_increase));
        secondListDefault.add(mActivity.getString(R.string.tab1_inner_travel_pop_sort_decrease));
    }

    private void addListener() {
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvClear.setOnClickListener(this);

        layoutStartTime1.setOnClickListener(this);
        layoutStartTime2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_start_time_1:
                mActivity.startActivityForResult(new Intent(mActivity, DatePickerActivity.class), Consts.REQUEST_CODE_DATE_PICKER_EARLY);
                break;
            case R.id.layout_start_time_2:
                mActivity.startActivityForResult(new Intent(mActivity, DatePickerActivity.class), Consts.REQUEST_CODE_DATE_PICKER_LATER);
                break;
            case R.id.title_main_tv_left_location: //取消
                dismiss();
                break;
            case R.id.title_main_iv_right_telephone: //确定
                dismiss();
                break;
            case R.id.tv_clear: //清空
                clear();
                break;
        }
    }

    private void clear() {
        if (isClear)    return;
        isClear = true;

        selectionSort = 0;
        InnerTravelCityActivity.sort = "";
        selectionDays = -1;
        InnerTravelCityActivity.day = "";
        selectionPrice = -1;
        InnerTravelCityActivity.price = "";

        InnerTravelCityActivity.earlyTime = "";
        InnerTravelCityActivity.laterTime = "";

        if (mActivity.getResources().getString(R.string.tab1_inner_travel_sort_default).equals(firstAdapter.getCurrentPositionItem())) { //弹出排序选择
            selectionSort = 0;
            secondAdapter1.setSelectPosition(selectionSort); //默认排序，0
        }else if (mActivity.getResources().getString(R.string.tab1_inner_travel_trip_days).equals(firstAdapter.getCurrentPositionItem())){ //弹出行程天数
            selectionDays = -1;
            secondAdapter1.setSelectPosition(selectionDays);
        }else if (mActivity.getResources().getString(R.string.tab1_inner_travel_price_screen).equals(firstAdapter.getCurrentPositionItem())){ //弹出价格筛选
            selectionPrice = -1;
            secondAdapter1.setSelectPosition(selectionPrice);
        }
        secondAdapter1.notifyDataSetChanged();
        listViewSecond.smoothScrollToPosition(0);
    }

    @Override
    public void dismiss() {
        LogUtil.e(TAG, " popup window dismiss! ");
        LogUtil.e(TAG, " popup window dismiss! " + InnerTravelCityActivity.sort);
        LogUtil.e(TAG, " popup window dismiss! " + InnerTravelCityActivity.day);
        LogUtil.e(TAG, " popup window dismiss! " + InnerTravelCityActivity.price);

        super.dismiss();
    }

//    public void setEarlyTime(String earlyTime){
//        tvEarlyTime.setText(earlyTime);
//    }
//    public void setLaterTime(String laterTime){
//        tvLaterTime.setText(laterTime);
//    }

    //    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        LogUtil.i("InnerTravelPopupWindow", "position = " + position + ", id = " + id );
//        if(view.getId() == R.id.listViewFirst) {
//            if (id == -1) { //点击的是 headerView 或者 footerView
//                return;
//            }
//            int realPosition = (int) id;
//
//            switch (realPosition) {
//                case 0:
//                    Toast.makeText(mActivity, "type = 1", Toast.LENGTH_SHORT).show();
//                    break;
//                case 1:
//                    Toast.makeText(mActivity, "type = 2", Toast.LENGTH_SHORT).show();
//                    break;
//                case 2:
//                    Toast.makeText(mActivity, "type = 3", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        } else {
//
//        }
//    }


}
