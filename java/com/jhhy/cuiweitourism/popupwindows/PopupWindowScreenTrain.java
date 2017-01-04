package com.jhhy.cuiweitourism.popupwindows;


import android.app.Activity;
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


public class PopupWindowScreenTrain extends PopupWindow implements OnClickListener {

    private Activity mActivity;
    private String TAG = PopupWindowScreenTrain.class.getSimpleName();

    private ListView listViewFirst; //主
    private ListView listViewSecond; //从

    private InnerTravelFirstListViewAdapter firstAdapter; //主
    private InnerTravelTripDaysListViewAdapter secondAdapter1; //从

    private List<String> firstList; //主
    private List<String> secondListStartTime; //从：发车时段

    private List<String> secondListArrivalTime = new ArrayList<>(); //到达时段
    private List<String> secondListTrainType = new ArrayList<>(); //车型
    private List<String> secondListSeatType = new ArrayList(); //席别类型
//    private Map<String, String> secondListSeatType; // = new HashMap<>(); //席别类型

    private LinearLayout layoutStartTime; //从：出发时间
    private RelativeLayout layoutStartTime1; //最早出发时间
    private RelativeLayout layoutStartTime2; //最晚出发时间
    private TextView tvEarlyTime;
    private TextView tvLaterTime;

    private TextView tvCancel; //取消
    private TextView tvConfirm; //确认
    private TextView tvClear; //清空
    private boolean isClear;

    private int selectionStartTime = 0;
    private int selectionArrivalTime = -1; //到达时间
    private int selectionTypeTrain = -1; //车型
    private int selectionTypeSeat = -1; //席别类型

    private String trainSeatType = "";

    private boolean commit;

    public PopupWindowScreenTrain(Activity activity, View parent) {
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

        initView(view);
        initData();
        addListener();
        initFirstListView();
    }

    /**
     * 第二次进入窗口，要显示之前选择的数据
     * @param startTimePosition 出发时间位置
     * @param arrivalTimePosition 到达时间位置
     * @param typeTrainPosition 车型
     * @param typeSeatPosition 席别类型
     */
    public void refreshView(int startTimePosition, int arrivalTimePosition, int typeTrainPosition, int typeSeatPosition){
        initFirstListView();
        if (startTimePosition != -1) {
            selectionStartTime = startTimePosition;
        } else {
            selectionStartTime = 0;
        }
        if (arrivalTimePosition != -1){
            selectionArrivalTime = arrivalTimePosition;
        }
        if (typeTrainPosition != -1){
            selectionTypeTrain = typeTrainPosition;
        }
        if (typeSeatPosition != -1) {
            selectionTypeSeat = typeSeatPosition;
        }

//        if (tag == 1){
            secondAdapter1.setSelectPosition(selectionStartTime);
//        } else if (tag == 2) {
//            secondAdapter1.setSelectPosition(selectionArrivalTime);
//        }else if (tag == 4){
//            secondAdapter1.setSelectPosition(selectionTypeSeat);
//        }
        secondAdapter1.notifyDataSetChanged();
    }


    private void initFirstListView() {
        firstAdapter = new InnerTravelFirstListViewAdapter(mActivity, firstList);

        firstAdapter.setSelectPosition(0);
        listViewFirst.setAdapter(firstAdapter);
        initSecondListView(0);

        listViewFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                updataListView(position);
            }
        });

    }

    private void initSecondListView(int position) {
//        if (1 == tag) {
            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListStartTime);
            secondAdapter1.setSelectPosition(position);
//        }
//        else if (2 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListArrivalTime);
//            secondAdapter1.setSelectPosition(selectionArrivalTime);
//        } else if (3 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListTrainType);
//            secondAdapter1.setSelectPosition(selectionTypeTrain);
//        } else if (4 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(mActivity, secondListSeatType);
//            secondAdapter1.setSelectPosition(selectionTypeSeat);
//        }

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
     * 第一级ListViewItem单击事件,更新第二级listView
     * @param position
     */
    private void updataListView(int position) {
        firstAdapter.setSelectPosition(position);
        firstAdapter.notifyDataSetChanged();
        //更新第二ListView
        if (0 == position) { //发车时段
            secondAdapter1.setData(secondListStartTime);
            secondAdapter1.setSelectPosition(selectionStartTime);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionStartTime);
        } else if (1 == position) { //到达时段
            secondAdapter1.setData(secondListArrivalTime);
            secondAdapter1.setSelectPosition(selectionArrivalTime);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionArrivalTime);
        } else if (2 == position) { //车型
            secondAdapter1.setData(secondListTrainType);
            secondAdapter1.setSelectPosition(selectionTypeTrain);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionTypeTrain);
        }else if (3 == position) { //席别类型
            secondAdapter1.setData(secondListSeatType);
            secondAdapter1.setSelectPosition(selectionTypeSeat);
            secondAdapter1.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionTypeSeat);
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

        if (mActivity.getResources().getString(R.string.train_screen_start_time).equals(firstAdapter.getCurrentPositionItem())) { //发车时段
            selectionStartTime = position;
        } else if (mActivity.getResources().getString(R.string.train_screen_arrival_time).equals(firstAdapter.getCurrentPositionItem())) { //到达时段
            selectionArrivalTime = position;
        } else if (mActivity.getResources().getString(R.string.train_screen_type_train).equals(firstAdapter.getCurrentPositionItem())) { //车型
            selectionTypeTrain = position;
        } else if (mActivity.getResources().getString(R.string.train_screen_type_seat).equals(firstAdapter.getCurrentPositionItem())) { //席别类型
            selectionTypeSeat = position;
            trainSeatType = secondListSeatType.get(position);
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

        tvEarlyTime = (TextView) view.findViewById(R.id.inner_trip_start_time_1);
        tvLaterTime = (TextView) view.findViewById(R.id.inner_trip_start_time_2);
    }

    private void initData() {
        firstList = new ArrayList<>();
        firstList.add(mActivity.getString(R.string.train_screen_start_time));
        firstList.add(mActivity.getString(R.string.train_screen_arrival_time));
        firstList.add(mActivity.getString(R.string.train_screen_type_train));
        firstList.add(mActivity.getString(R.string.train_screen_type_seat));

        secondListStartTime = new ArrayList<>();
        secondListStartTime.add("不限");
        secondListStartTime.add("00:00~06:00");
        secondListStartTime.add("06:00~12:00");
        secondListStartTime.add("12:00~18:00");
        secondListStartTime.add("18:00~24:00");

        secondListArrivalTime.add("不限");
        secondListArrivalTime.add("00:00~06:00");
        secondListArrivalTime.add("06:00~12:00");
        secondListArrivalTime.add("12:00~18:00");
        secondListArrivalTime.add("18:00~24:00");

        secondListTrainType.add("不限");
        secondListTrainType.add("动车/高铁");
        secondListTrainType.add("普通");

        secondListSeatType = getList(ArrayDataDemo.getSeat(1).keySet());
    }

    //将map中key取出放入List
    private List<String> getList(Set<String> seat){
        List<String> seatList = new ArrayList<>();
        Iterator it = seat.iterator();
        while (it.hasNext()){
            String s = (String) it.next();
            seatList.add(s);
        }
        return seatList;
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
        selectionArrivalTime = -1;
        selectionTypeTrain = -1;
        selectionTypeSeat = -1;


        if (mActivity.getResources().getString(R.string.train_screen_start_time).equals(firstAdapter.getCurrentPositionItem())) { //发车时段
            selectionStartTime = 0;
            secondAdapter1.setSelectPosition(selectionStartTime); //默认排序，0
        } else if (mActivity.getResources().getString(R.string.train_screen_arrival_time).equals(firstAdapter.getCurrentPositionItem())) { //到达时段
            selectionArrivalTime = -1;
            secondAdapter1.setSelectPosition(selectionArrivalTime);
        } else if (mActivity.getResources().getString(R.string.train_screen_type_train).equals(firstAdapter.getCurrentPositionItem())) { //车型
            selectionTypeTrain = -1;
            secondAdapter1.setSelectPosition(selectionTypeTrain);
        }else if (mActivity.getResources().getString(R.string.train_screen_type_seat).equals(firstAdapter.getCurrentPositionItem())) { //席别类型
            selectionTypeSeat = -1;
            secondAdapter1.setSelectPosition(selectionTypeSeat);
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

    public int getStartTime(){
        return selectionStartTime;
    }

    public int getSelectionArrivalTime() {
        return selectionArrivalTime;
    }

    public int getSelectionTypeTrain() {
        return selectionTypeTrain;
    }

    public int getSelectionTypeSeat() {
        return selectionTypeSeat;
    }

    public String getTrainSeatType() {
        return trainSeatType;
    }
}
