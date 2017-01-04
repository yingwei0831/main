package com.jhhy.cuiweitourism.popupwindows;

import android.content.Context;
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
import com.jhhy.cuiweitourism.net.utils.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.jeesoft.ArrayDataDemo;

/**
 * 国内机票筛选 起飞时间，机场，机型，航空公司
 */
public class PopupWindowScreenPlane extends PopupWindow implements OnClickListener {

    private Context context;
    private String TAG = PopupWindowScreenPlane.class.getSimpleName();

    private ListView listViewFirst; //主
    private ListView listViewSecond; //从

    private InnerTravelFirstListViewAdapter firstAdapter; //主
    private InnerTravelTripDaysListViewAdapter secondAdapter1; //从

    private List<String> firstList; //主
    private List<String> secondListStartTime; //从：起飞时间

    private List<String> secondListAirport = new ArrayList<>(); //机场
    private List<String> secondListPlaneType = new ArrayList<>(); //机型
    private List<String> secondListAirCompany = new ArrayList(); //航空公司

    private LinearLayout layoutStartTime; //从：出发时间
//    private RelativeLayout layoutStartTime1; //最早出发时间
//    private RelativeLayout layoutStartTime2; //最晚出发时间
//    private TextView tvEarlyTime;
//    private TextView tvLaterTime;

    private TextView tvCancel; //取消
    private TextView tvConfirm; //确认
    private TextView tvClear; //清空
    private boolean isClear;

    private int selectionStartTime = 0;
    private int selectionAirport = 0; //机场
    private int selectionPlaneType = 0; //车型
    private int selectionCompany = 0; //航空公司

    private boolean commit;

    public PopupWindowScreenPlane(Context context) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.inner_travel_popupwindow, null);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_2));

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        initView(view);
        initData();
        addListener();
        initFirstListView();
    }

    /**
     * 第二次进入窗口，要显示之前选择的数据
     * @param startTimePosition 起飞时间
     * @param innerAirport      机场
     * @param innerPlaneType    机型
     * @param innerCompany      航空公司
     */
    public void refreshView(int startTimePosition, int innerAirport, int innerPlaneType, int innerCompany){
        firstAdapter.setSelectPosition(0);
        listViewFirst.setAdapter(firstAdapter);

        commit = false;

        selectionStartTime = startTimePosition;

        selectionAirport = innerAirport;

        selectionPlaneType = innerPlaneType;

        selectionCompany = innerCompany;

        secondAdapter1.setSelectPosition(selectionStartTime);
        secondAdapter1.notifyDataSetChanged();
    }


    private void initFirstListView() {
        firstAdapter = new InnerTravelFirstListViewAdapter(context, firstList);

        firstAdapter.setSelectPosition(0);
        listViewFirst.setAdapter(firstAdapter);
        initSecondListView(0);

        listViewFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                updateListView(position);
            }
        });

    }

    private void initSecondListView(int position) {
//        if (1 == tag) {
            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(context, secondListStartTime);
            secondAdapter1.setSelectPosition(position);
//        } else if (2 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(context, secondListArrivalTime);
//            secondAdapter1.setSelectPosition(selectionArrivalTime);
//        } else if (3 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(context, secondListTrainType);
//            secondAdapter1.setSelectPosition(selectionPlaneType);
//        } else if (4 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(context, secondListSeatType);
//            secondAdapter1.setSelectPosition(selectionCompany);
//        }

        listViewSecond.setAdapter(secondAdapter1);
        listViewSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e(TAG, "position = " + position);
                updateListView2(position);
            }
        });
//        initThirdListView(position);
    }

    /**
     * 第一级ListViewItem单击事件,更新第二级listView
     * @param position 第一级listView的位置
     */
    private void updateListView(int position) {
        firstAdapter.setSelectPosition(position);
        firstAdapter.notifyDataSetChanged();
        //更新第二ListView
        if (0 == position) { //发车时段
            secondAdapter1.setData(secondListStartTime);
            secondAdapter1.setSelectPosition(selectionStartTime);

            listViewSecond.smoothScrollToPosition(selectionStartTime);
        } else if (1 == position) { //到达时段
            secondAdapter1.setData(secondListAirport);
            secondAdapter1.setSelectPosition(selectionAirport);

            listViewSecond.smoothScrollToPosition(selectionAirport);
        } else if (2 == position) { //车型
            secondAdapter1.setData(secondListPlaneType);
            secondAdapter1.setSelectPosition(selectionPlaneType);

            listViewSecond.smoothScrollToPosition(selectionPlaneType);
        }else if (3 == position) { //席别类型
            secondAdapter1.setData(secondListAirCompany);
            secondAdapter1.setSelectPosition(selectionCompany);

            listViewSecond.smoothScrollToPosition(selectionCompany);
        }
        secondAdapter1.notifyDataSetChanged();
    }

    /**
     * 第二级ListViewItem单击事件
     * position 第二级ListView的位置
     */
    private void updateListView2(int position) {
        LogUtil.e(TAG, "isClear = " + isClear + ", position = " + position);
        if (isClear) {
            isClear = false;
        }

        if (context.getResources().getString(R.string.plane_screen_start_time).equals(firstAdapter.getCurrentPositionItem())) { //发车时段
            selectionStartTime = position;
        } else if (context.getResources().getString(R.string.plane_screen_from_airport).equals(firstAdapter.getCurrentPositionItem())) { //到达时段
            selectionAirport= position;
        } else if (context.getResources().getString(R.string.plane_screen_aircraft_model).equals(firstAdapter.getCurrentPositionItem())) { //车型
            selectionPlaneType = position;
        } else if (context.getResources().getString(R.string.plane_screen_airline_company).equals(firstAdapter.getCurrentPositionItem())) { //席别类型
            selectionCompany = position;
        }
        secondAdapter1.setSelectPosition(position);
        secondAdapter1.notifyDataSetChanged();
        listViewSecond.smoothScrollToPosition(position);
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
//        layoutStartTime1 = (RelativeLayout) view.findViewById(R.id.layout_start_time_1);
//        layoutStartTime2 = (RelativeLayout) view.findViewById(R.id.layout_start_time_2);
//        tvEarlyTime = (TextView) view.findViewById(R.id.inner_trip_start_time_1);
//        tvLaterTime = (TextView) view.findViewById(R.id.inner_trip_start_time_2);
    }

    private void initData() {
        firstList = new ArrayList<>();
        firstList.add(context.getString(R.string.plane_screen_start_time));
        firstList.add(context.getString(R.string.plane_screen_from_airport));
        firstList.add(context.getString(R.string.plane_screen_aircraft_model));
        firstList.add(context.getString(R.string.plane_screen_airline_company));

        secondListStartTime = new ArrayList<>();
        secondListStartTime.add("不限");
        secondListStartTime.add("上午 (00:00~12:00)");
        secondListStartTime.add("中午 (12:00~14:00)");
        secondListStartTime.add("下午 (14:00~18:00)");
        secondListStartTime.add("晚上 (18:00~24:00)");

        secondListAirport.add("不限");

        secondListPlaneType.add("不限");

        secondListAirCompany.add("不限");
    }

    private void addListener() {
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location: //取消
                commit = false;
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
        if (isClear) return;
        isClear = true;

        selectionStartTime = 0;
        selectionAirport = 0;
        selectionPlaneType = 0;
        selectionCompany = 0;


        if (context.getResources().getString(R.string.train_screen_start_time).equals(firstAdapter.getCurrentPositionItem())) { //发车时段
            selectionStartTime = 0;
            secondAdapter1.setSelectPosition(selectionStartTime); //默认排序，0
        } else if (context.getResources().getString(R.string.train_screen_arrival_time).equals(firstAdapter.getCurrentPositionItem())) { //到达时段
            selectionAirport = -1;
            secondAdapter1.setSelectPosition(selectionAirport);
        } else if (context.getResources().getString(R.string.train_screen_type_train).equals(firstAdapter.getCurrentPositionItem())) { //车型
            selectionPlaneType = -1;
            secondAdapter1.setSelectPosition(selectionPlaneType);
        }else if (context.getResources().getString(R.string.train_screen_type_seat).equals(firstAdapter.getCurrentPositionItem())) { //席别类型
            selectionCompany = -1;
            secondAdapter1.setSelectPosition(selectionCompany);
        }
        secondAdapter1.notifyDataSetChanged();
        listViewSecond.smoothScrollToPosition(0);
    }

    public void setCommit(boolean flag) {
        commit = flag;
    }

    public boolean getCommit(){
        return commit;
    }

    public int getSelectionStartTime() {
        return selectionStartTime;
    }

    public int getSelectionAirport() {
        return selectionAirport;
    }

    public int getSelectionPlaneType() {
        return selectionPlaneType;
    }

    public int getSelectionCompany() {
        return selectionCompany;
    }
}
