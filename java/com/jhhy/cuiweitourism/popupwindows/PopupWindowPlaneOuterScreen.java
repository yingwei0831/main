package com.jhhy.cuiweitourism.popupwindows;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelFirstListViewAdapter;
import com.jhhy.cuiweitourism.adapter.InnerTravelTripDaysListViewAdapter;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.InnerTravelCityActivity;
import com.jhhy.cuiweitourism.ui.PlaneListInternationalActivity;

import java.util.ArrayList;
import java.util.List;


public class PopupWindowPlaneOuterScreen extends PopupWindow implements OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Context context;
    private String TAG = PopupWindowPlaneOuterScreen.class.getSimpleName();

    private ListView listViewFirst; //主
    private ListView listViewSecond; //从

    private InnerTravelFirstListViewAdapter firstAdapter; //主
    private InnerTravelTripDaysListViewAdapter secondAdapter; //从

    private List<String> firstList; //主

    private List<String> secondListFromTime; //起飞时间
    private List<PlaneTicketInternationalInfo.AirportInfo> secondListAirport; //机场
    private List<PlaneTicketInternationalInfo.AircraftTypeInfo> secondListAirplane; //机型
    private List<PlaneTicketInternationalInfo.AirlineCompanyInfo> secondListAirportCompany; //航空公司

    private ToggleButton toggleButton; //是否只看直飞
    private TextView tvCancel; //取消
    private TextView tvConfirm; //确认
    private TextView tvClear; //清空
    private boolean isClear;
    private boolean commit; //确定
    private boolean direct;

    private int selectionFromTime = 0;
    private int selectionAirport = -1;
    private int selectionAirplane = -1;
    private int selectionAirportCompany = -1;

    public PopupWindowPlaneOuterScreen(Context context) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.pop_plane_outer_screen, null);
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

    private void initFirstListView() {
        firstAdapter = new InnerTravelFirstListViewAdapter(context, firstList);

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
        secondAdapter = new InnerTravelTripDaysListViewAdapter(context, secondListFromTime);
        secondAdapter.setSelectPosition(position);

        listViewSecond.setAdapter(secondAdapter);
        listViewSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updataListView2(position);
            }
        });
    }

    /**
     * 第一级ListViewItem单击事件
     * @param position
     */
    private void updataListView(int position) {
        firstAdapter.setSelectPosition(position);
        firstAdapter.notifyDataSetChanged();
        //更新第二ListView
        if (0 == position) { //起飞时间
            secondAdapter.setType(1);
            secondAdapter.setData(secondListFromTime);
            secondAdapter.setSelectPosition(selectionFromTime);
            secondAdapter.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionFromTime);
        } else if (1 == position) { //机场
            secondAdapter.setType(2);
            secondAdapter.setData(secondListAirport);
            secondAdapter.setSelectPosition(selectionAirport);
            secondAdapter.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionAirport);
        } else if (2 == position) { //机型
            secondAdapter.setType(3);
            secondAdapter.setData(secondListAirplane);
            secondAdapter.setSelectPosition(selectionAirplane);
            secondAdapter.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionAirplane);
        } else if (3 == position) { //航空公司
            secondAdapter.setType(4);
            secondAdapter.setData(secondListAirportCompany);
            secondAdapter.setSelectPosition(selectionAirportCompany);
            secondAdapter.notifyDataSetChanged();
            listViewSecond.smoothScrollToPosition(selectionAirportCompany);
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

        int firstPosition = firstAdapter.getSelection();
        if (0 == firstPosition){
            selectionFromTime = position;
            secondAdapter.setType(1);
        }else if (1 == firstPosition){
            selectionAirport = position;
            secondAdapter.setType(2);
        }else if (2 == firstPosition){
            selectionAirplane = position;
            secondAdapter.setType(3);
        }else if (3 == firstPosition){
            selectionAirportCompany = position;
            secondAdapter.setType(4);
        }
        secondAdapter.setSelectPosition(position);
        secondAdapter.notifyDataSetChanged();
        listViewSecond.smoothScrollToPosition(position);
    }

    private void initView(View view) {
        tvCancel = (TextView) view.findViewById(R.id.title_main_tv_left_location);
        tvConfirm = (TextView) view.findViewById(R.id.title_main_iv_right_telephone);
        tvClear = (TextView) view.findViewById(R.id.tv_clear);

        listViewFirst = (ListView) view.findViewById(R.id.listViewFirst);
        listViewSecond = (ListView) view.findViewById(R.id.listViewSecond);

        toggleButton = (ToggleButton) view.findViewById(R.id.toggle_button);
        toggleButton.setChecked(direct);
    }

    private void initData() {
        firstList = new ArrayList<>();
        firstList.add(context.getString(R.string.plane_screen_start_time));
        firstList.add(context.getString(R.string.plane_screen_from_airport));
        firstList.add(context.getString(R.string.plane_screen_aircraft_model));
        firstList.add(context.getString(R.string.plane_screen_airline_company));

        secondListFromTime = new ArrayList<>();
        secondListFromTime.add(context.getString(R.string.plane_flight_screen_no));
        secondListFromTime.add(context.getString(R.string.plane_flight_screen_time_1));
        secondListFromTime.add(context.getString(R.string.plane_flight_screen_time_2));
        secondListFromTime.add(context.getString(R.string.plane_flight_screen_time_3));
        secondListFromTime.add(context.getString(R.string.plane_flight_screen_time_4));

        secondListAirport = new ArrayList<>();
        secondListAirport.addAll(PlaneListInternationalActivity.info.P.values());

        secondListAirplane = new ArrayList<>();
        secondListAirplane.addAll(PlaneListInternationalActivity.info.J.values());

        secondListAirportCompany = new ArrayList<>();
        secondListAirportCompany.addAll(PlaneListInternationalActivity.info.A);
    }

    private void addListener() {
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        toggleButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
        if (isClear)    return;
        isClear = true;

        selectionFromTime = 0;
        selectionAirport = -1;
        selectionAirplane = -1;
        selectionAirportCompany = -1;

        int firstPosition = firstAdapter.getSelection();
        int selection = 0;
        if (0 == firstPosition){
            selection = selectionFromTime;
            secondAdapter.setType(1);
        }else if (1 == firstPosition){
            selection = selectionAirport;
            secondAdapter.setType(2);
        }else if (2 == firstPosition){
            selection = selectionAirplane;
            secondAdapter.setType(3);
        }else if (3 == firstPosition){
            selection = selectionAirportCompany;
            secondAdapter.setType(4);
        }
        secondAdapter.setSelectPosition(selection);
        secondAdapter.notifyDataSetChanged();
        listViewSecond.smoothScrollToPosition(0);
    }

    public int getS1(){
        return selectionFromTime;
    }
    public int getS2(){
        return selectionAirport;
    }
    public int getS3(){
        return selectionAirplane;
    }
    public int getS4(){
        return selectionAirportCompany;
    }

    public String getS2Code(){
        return secondListAirport.get(selectionAirport).airportCode;
    }

    public String getS3Code(){
        return secondListAirplane.get(selectionAirplane).typeCode;
    }

    public String getS4Code(){
        return secondListAirportCompany.get(selectionAirportCompany).airlineCompanyCode;
    }

    public boolean isDirect(){
        return direct;
    }

    public void refreshView(boolean direct, int p1, int p2, int p3, int p4) {
        this.direct = direct;
        commit = false;
        selectionFromTime = p1;
        selectionAirport = p2;
        selectionAirplane = p3;
        selectionAirportCompany = p4;
        toggleButton.setChecked(direct);
    }

    public boolean isCommit(){
        return commit;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        direct = b;
    }


}
