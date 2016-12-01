package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.InnerTravelFirstListViewAdapter;
import com.jhhy.cuiweitourism.adapter.InnerTravelPagerAdapter;
import com.jhhy.cuiweitourism.adapter.InnerTravelTripDaysListViewAdapter;
import com.jhhy.cuiweitourism.biz.ScreenBiz;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.fragment.InnerTravelCityFollowFragment;
import com.jhhy.cuiweitourism.fragment.InnerTravelCityFreedomFragment;
import com.jhhy.cuiweitourism.moudle.PriceArea;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowSearchLine;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InnerTravelCityActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = InnerTravelCityActivity.class.getSimpleName();
    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private List<String> titles = new ArrayList(Arrays.asList("跟团游", "自由行"));
    private TabLayout indicatorInnerTravel;
    private ViewPager viewPager;
    private InnerTravelPagerAdapter mAdapter;

    private List<Fragment> mContent = new ArrayList<>();
    private InnerTravelCityFollowFragment followFragment;
    private InnerTravelCityFreedomFragment freedomFragment;

    private View layout;
    private TextView tvSortDefault;
    private TextView tvSortDays;
    private TextView tvStartTime;
    private TextView tvScreenPrice;

    private int tag;

    private String cityId; //城市id，可以确定国内、出境
    private String cityName;

    public static String sort = "";
    public static String day = "";
    public static String price = "";
    public static String earlyTime = "";
    public static String laterTime = "";

    private List<String> listDays = new ArrayList<>();
    private List<PriceArea> listPrices = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Consts.MESSAGE_TRIP_DAYS:
                    if (msg.arg1 == 0) {
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                        return;
                    }
                    listDays = (List<String>) msg.obj;
                    if (listDays == null || listDays.size() == 0) {
                        ToastUtil.show(getApplicationContext(), "未获取到筛选天数");
                    }
                    break;
                case Consts.MESSAGE_TRIP_PRICE:
                    if (msg.arg1 == 0) {
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                        return;
                    }
                    listPrices = (List<PriceArea>) msg.obj;
                    if (listPrices == null || listPrices.size() == 0) {
                        ToastUtil.show(getApplicationContext(), "未获取到筛选价格区间");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inner_travel);
        getData();
        setupView();
        addListener();
        initDatas();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        cityId = bundle.getString("cityId");
        cityName = bundle.getString("cityName");


        ScreenBiz biz = new ScreenBiz(getApplicationContext(), handler);
        biz.getScreenDays();

        biz.getScreenPrice();
    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText(cityName);
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        layout = findViewById(R.id.activity_inner_travel);
        indicatorInnerTravel = (TabLayout) findViewById(R.id.activity_inner_travel_indicator_top);
        viewPager = (ViewPager) findViewById(R.id.activity_inner_travel_viewpager);

        tvSortDefault = (TextView) findViewById(R.id.tv_tab1_inner_travel_sort_default);
        tvSortDays = (TextView) findViewById(R.id.tv_tab1_inner_travel_trip_days);
        tvStartTime = (TextView) findViewById(R.id.tv_tab1_inner_travel_start_time);
        tvScreenPrice = (TextView) findViewById(R.id.tv_tab1_inner_travel_screen_price);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvSortDefault.setOnClickListener(this);
        tvSortDays.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvScreenPrice.setOnClickListener(this);
    }

    private void initDatas() {
        //设置TabLayout的模式
        indicatorInnerTravel.setTabMode(TabLayout.MODE_FIXED);
        indicatorInnerTravel.setTabGravity(TabLayout.GRAVITY_CENTER);

        mAdapter = new InnerTravelPagerAdapter(getApplicationContext(), this.getSupportFragmentManager(), mContent, titles);

        followFragment = InnerTravelCityFollowFragment.newInstance(titles.get(0), cityId);
        freedomFragment = InnerTravelCityFreedomFragment.newInstance(titles.get(1), cityId);

        mContent.add(followFragment);
        mContent.add(freedomFragment);

        viewPager.setAdapter(mAdapter);
        //TabLayout加载viewpager
        indicatorInnerTravel.setupWithViewPager(viewPager);
        //自定义TabLayout 布局
        for (int i = 0; i < indicatorInnerTravel.getTabCount(); i++) {
            TabLayout.Tab tab = indicatorInnerTravel.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mAdapter.getTabView(i));
            }
        }
        indicatorInnerTravel.getTabAt(0).getCustomView().setSelected(true);
    }

    private PopupWindowSearchLine popupWindow;
    private int pricePosition = -1;
    private int dayPosition = -1;
//    private View layoutPop;
//    private ListView listViewFirst; //主
//    private ListView listViewSecond; //从
//    private List<String> firstList; //主
//    private List<String> secondListDefault; //从：默认排序
//    private InnerTravelFirstListViewAdapter firstAdapter; //主
//    private InnerTravelTripDaysListViewAdapter secondAdapter1; //从
//    private TextView tvEarlyTime;
//    private TextView tvLaterTime;
//    private LinearLayout layoutStartTime; //从：出发时间
//    private RelativeLayout layoutStartTime1; //最早出发时间
//    private RelativeLayout layoutStartTime2; //最晚出发时间
//    private TextView tvCancel; //取消
//    private TextView tvConfirm; //确认
//    private TextView tvClear; //清空
//    private boolean isClear;
//    private int selectionSort = 0;
//    private int selectionDays = -1;
//    private int selectionPrice = -1;
//    private List<String> secondListDayString = new ArrayList<>(); //行程天数 （构造数据）
//    private List<String> secondListPriceString = new ArrayList<>(); //价格筛选(构造数据)
//    private List<String> secondListDaysS; //行程天数 （原数据）
//    private List<PriceArea> secondListPriceS; //价格筛选(原数据)

    @Override
    public void onClick(View view) {
//        if (layoutPop == null) {
//            layoutPop = LayoutInflater.from(getApplicationContext()).inflate(R.layout.inner_travel_popupwindow, null);
//            setupPopView(layoutPop);
//            addPopListener();
//            initData();
//        }
//        if (popupWindow == null) {
//            setupPop();
////            popupWindow = new PopupWindow(getApplicationContext(), layout, tag, listDays, listPrices);
//        }else{
//            if (popupWindow.isShowing()) {
//                popupWindow.dismiss();
//            } else {
//                popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
//            }
//        }

        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_tab1_inner_travel_sort_default:
                tag = 1;
                showPopupWindow();
                break;
            case R.id.tv_tab1_inner_travel_trip_days:
                tag = 2;
                showPopupWindow();
                break;
            case R.id.tv_tab1_inner_travel_start_time:
                tag = 3;
                showPopupWindow();
                break;
            case R.id.tv_tab1_inner_travel_screen_price:
                tag = 4;
                showPopupWindow();
                break;
        }
//        initFirstListView();
    }

    private void showPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindowSearchLine(InnerTravelCityActivity.this, layout, tag, listDays, listPrices);
            addPopListener();
        } else {
            popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            LogUtil.e(TAG, tag + ", " + sort + ", " + String.valueOf(dayPosition) + ", " + earlyTime + ", " + laterTime + ", " + pricePosition); //2, , -1, , , -1
            String toSrot = "";
            if ("2".equals(sort)){
                toSrot = "1";
            }else if ("1".equals(sort)){
                toSrot = "2";
            }
            popupWindow.refreshView(tag, toSrot, String.valueOf(dayPosition), earlyTime, laterTime, pricePosition);
        }
    }

    private void addPopListener() {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //获取相关参数值：摁了“确定”，返回按钮，摁了“取消”，BACK键
                boolean commit = popupWindow.getCommit();
                if (commit) {
                    String newSort = popupWindow.getSort();
                    if ("0".equals(newSort)){
                        sort = "";
                    }else if ("1".equals(newSort)){
                        sort = "2";
                    }else if ("2".equals(newSort)){
                        sort = "1";
                    }
                    String newDay = popupWindow.getDay();
                    if (newDay != null && newDay.length() != 0) {
                        day = newDay;
                        dayPosition = Integer.parseInt(newDay) - 1;
                    } else {
                        day = "";
                        dayPosition = -1;
                    }
                    int newPricePosition = popupWindow.getPricePosition();
                    pricePosition = newPricePosition;
                    if (newPricePosition != -1) {
                        price = popupWindow.getPrice();
//                        price = listPrices.get(newPricePosition).getPriceLower() +"," + listPrices.get(newPricePosition).getPriceHigh();
                    } else {
                        price = "";
                    }
                    String newEarylTime = popupWindow.getEarlyTime();
                    if (newEarylTime != null) {
                        earlyTime = newEarylTime;
                    }
                    String newLaterTime = popupWindow.getLaterTime();
                    if (newLaterTime != null) {
                        laterTime = newLaterTime;
                    }
                    LogUtil.e(TAG, "dayPosition = " + dayPosition + ", pricePosition = " + pricePosition);
                    LogUtil.e(TAG, "sort = " + sort + ", day = " + day + ", price = " + price + ", earlyTime = " + earlyTime + ", laterTime = " + laterTime);
//                    {"head":{"code":"Publics_lines"},"field":{"type":"1","attr":"1","page":"1","offset":"10"}}
                    int currentItem = viewPager.getCurrentItem();
                    if (currentItem == 0) {
                        followFragment.getData(true);
                    } else if (currentItem == 1) {
                        freedomFragment.getData(true);
                    }

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_CANCELED) {

        } else {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    if (requestCode == Consts.REQUEST_CODE_DATE_PICKER_EARLY) { //选择最早出发时间
                        String selectData = bundle.getString("selectDate");
//                        tvEarlyTime.setText(selectData);
                    } else if (requestCode == Consts.REQUEST_CODE_DATE_PICKER_LATER) { //选择最晚出发时间
                        String selectData = bundle.getString("selectDate");
//                        tvLaterTime.setText(selectData);
                    }
                }
            }
        }
    }

//    private void initFirstListView() {
//        firstAdapter = new InnerTravelFirstListViewAdapter(getApplicationContext(), firstList);
//
//        firstAdapter.setSelectPosition(tag - 1);
//        listViewFirst.setAdapter(firstAdapter);
//        initSecondListView(tag, 0);
//
//        listViewFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                updataListView(position);
//            }
//        });
//    }

//    private void initSecondListView(int tag, int position) {
//        if (1 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(getApplicationContext(), secondListDefault);
//            secondAdapter1.setSelectPosition(selectionSort);
//        } else if (2 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(getApplicationContext(), secondListDayString);
//            secondAdapter1.setSelectPosition(selectionDays);
//        } else if (3 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(getApplicationContext(), secondListDefault); //防止空指针
//
//            listViewSecond.setVisibility(View.GONE);
//            layoutStartTime.setVisibility(View.VISIBLE);
//        } else if (4 == tag) {
//            secondAdapter1 = new InnerTravelTripDaysListViewAdapter(getApplicationContext(), secondListPriceString);
//            secondAdapter1.setSelectPosition(selectionPrice);
//        }
//
//        listViewSecond.setAdapter(secondAdapter1);
//        listViewSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LogUtil.e(TAG, "position = " + position);
//                updataListView2(position);
//            }
//        });
////        initThirdListView(position);
//    }

    /**
     * 第一级ListViewItem单击事件
     *
     * @param position
     */
//    private void updataListView(int position) {
//        firstAdapter.setSelectPosition(position);
//        firstAdapter.notifyDataSetChanged();
//        //更新第二ListView
//        if (0 == position) {
//            listViewSecond.setVisibility(View.VISIBLE);
//            layoutStartTime.setVisibility(View.GONE);
//            secondAdapter1.setData(secondListDefault);
//            secondAdapter1.setSelectPosition(selectionSort);
//            secondAdapter1.notifyDataSetChanged();
//            listViewSecond.smoothScrollToPosition(selectionSort);
////            updataListView2(selectionSort);
//        } else if (1 == position) {
//            listViewSecond.setVisibility(View.VISIBLE);
//            layoutStartTime.setVisibility(View.GONE);
//            secondAdapter1.setData(secondListDayString);
//            secondAdapter1.setSelectPosition(selectionDays);
//            secondAdapter1.notifyDataSetChanged();
//            listViewSecond.smoothScrollToPosition(selectionDays);
////            updataListView2(selectionDays);
//        } else if (2 == position) {
//            listViewSecond.setVisibility(View.GONE);
//            layoutStartTime.setVisibility(View.VISIBLE);
//        } else if (3 == position) {
//            listViewSecond.setVisibility(View.VISIBLE);
//            layoutStartTime.setVisibility(View.GONE);
//            secondAdapter1.setData(secondListPriceString);
//            secondAdapter1.setSelectPosition(selectionPrice);
//            secondAdapter1.notifyDataSetChanged();
//            listViewSecond.smoothScrollToPosition(selectionPrice);
////            updataListView2(selectionPirce);
//        }
//    }

    /**
     * 第二级ListViewItem单击事件
     * position 第一级ListView的位置
     */

//    private void updataListView2(int position) {
//        LogUtil.e(TAG, "isClear = " + isClear + ", position = " + position);
//        if (isClear) {
//            isClear = false;
//        }
//        if (getResources().getString(R.string.tab1_inner_travel_sort_default).equals(firstAdapter.getCurrentPositionItem())) { //弹出排序选择
//            selectionSort = position;
////            InnerTravelCityActivity.sort = String.valueOf(position);
//        }else if (getResources().getString(R.string.tab1_inner_travel_trip_days).equals(firstAdapter.getCurrentPositionItem())){ //弹出行程天数
//            selectionDays = position;
////            InnerTravelCityActivity.day = listDays.get(position);
//        }else if (getResources().getString(R.string.tab1_inner_travel_price_screen).equals(firstAdapter.getCurrentPositionItem())){ //弹出价格筛选
//            selectionPrice = position;
////            InnerTravelCityActivity.price = listPrices.get(position).getPriceLower() +"," + listPrices.get(position).getPriceHigh();
//        }
//        secondAdapter1.setSelectPosition(position);
//        secondAdapter1.notifyDataSetChanged();
//        listViewSecond.smoothScrollToPosition(position);
//    }
//    private void setupPop() {
//        // 创建PopupWindow对象
//        popupWindow = new PopupWindow(layoutPop, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//        // 需要设置一下此参数，点击外边可消失
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //设置点击窗口外边窗口消失
//        popupWindow.setOutsideTouchable(true);
//        // 设置此参数获得焦点，否则无法点击
//        popupWindow.setFocusable(true);
//        popupWindow.setTouchable(true);
//        popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
//    }

//    private void initData() {
//        firstList = new ArrayList<>();
//        firstList.add(getString(R.string.tab1_inner_travel_sort_default));
//        firstList.add(getString(R.string.tab1_inner_travel_trip_days));
//        firstList.add(getString(R.string.tab1_inner_travel_start_time));
//        firstList.add(getString(R.string.tab1_inner_travel_price_screen));
//
//        secondListDefault = new ArrayList<>();
//        secondListDefault.add(getString(R.string.tab1_inner_travel_pop_sort_default));
//        secondListDefault.add(getString(R.string.tab1_inner_travel_pop_sort_increase));
//        secondListDefault.add(getString(R.string.tab1_inner_travel_pop_sort_decrease));
//
//        for (String s : listDays){
//            secondListDayString.add(s+"天");
//        }
//
//        if (listPrices != null && listPrices.size() != 0) {
//            for (int i = 0; i < listPrices.size(); i++) {
//                PriceArea price = listPrices.get(i);
//                secondListPriceString.add(String.format("%s-%s元", price.getPriceLower(), price.getPriceHigh()));
//            }
//        }
//    }
//    private void addPopListener() {
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 全部失效,不赋值
//                popupWindow.dismiss();
//            }
//        });
//        tvConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 赋值
//                if (selectionSort != 0){
//                    sort = String.valueOf(selectionSort);
//                }
//                if (selectionDays != -1){
//                    day = listDays.get(selectionDays);
//                }
//                if (selectionPrice != -1){
//                    price = listPrices.get(selectionPrice).getPriceLower() +"," + listPrices.get(selectionPrice).getPriceHigh();
//                }
//                earlyTime = tvEarlyTime.getText().toString().trim();
//                laterTime = tvLaterTime.getText().toString().trim();
//                LogUtil.e(TAG, "sort = " + sort + ", day = " + day+", price = "+ price+", earlyTime = " + earlyTime + ", laterTime = " + laterTime);
//                popupWindow.dismiss();
//            }
//        });
//        tvClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO 全部清空
//                selectionSort = 0;
//                selectionDays = -1;
//                selectionPrice = -1;
//                isClear = true;
//                if (getResources().getString(R.string.tab1_inner_travel_sort_default).equals(firstAdapter.getCurrentPositionItem())) { //弹出排序选择
//                    selectionSort = 0;
//                    secondAdapter1.setSelectPosition(selectionSort); //默认排序，0
//                    secondAdapter1.notifyDataSetChanged();
//                    listViewSecond.smoothScrollToPosition(0);
//                }else if (getResources().getString(R.string.tab1_inner_travel_trip_days).equals(firstAdapter.getCurrentPositionItem())){ //弹出行程天数
//                    selectionDays = -1;
//                    secondAdapter1.setSelectPosition(selectionDays);
//                    secondAdapter1.notifyDataSetChanged();
//                    listViewSecond.smoothScrollToPosition(0);
//                }else if (getResources().getString(R.string.tab1_inner_travel_price_screen).equals(firstAdapter.getCurrentPositionItem())){ //弹出价格筛选
//                    selectionPrice = -1;
//                    secondAdapter1.setSelectPosition(selectionPrice);
//                    secondAdapter1.notifyDataSetChanged();
//                    listViewSecond.smoothScrollToPosition(0);
//                }
//                if (!TextUtils.isEmpty(tvEarlyTime.getText().toString())){
//                    tvEarlyTime.setText("");
//                }
//                if (!TextUtils.isEmpty(tvLaterTime.getText().toString())) {
//                    tvLaterTime.setText("");
//                }
//            }
//        });
//
//        layoutStartTime1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(getApplicationContext(), DatePickerActivity.class), Consts.REQUEST_CODE_DATE_PICKER_EARLY);
//            }
//        });
//        layoutStartTime2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(getApplicationContext(), DatePickerActivity.class), Consts.REQUEST_CODE_DATE_PICKER_LATER);
//            }
//        });
//    }
//    private void setupPopView(View view) {
//        tvEarlyTime = (TextView) layoutPop.findViewById(R.id.inner_trip_start_time_1);
//        tvLaterTime = (TextView) layoutPop.findViewById(R.id.inner_trip_start_time_2);
//
//        tvCancel = (TextView) view.findViewById(R.id.title_main_tv_left_location);
//        tvConfirm = (TextView) view.findViewById(R.id.title_main_iv_right_telephone);
//        tvClear = (TextView) view.findViewById(R.id.tv_clear);
//
//        listViewFirst = (ListView) view.findViewById(R.id.listViewFirst);
//        listViewSecond = (ListView) view.findViewById(R.id.listViewSecond);
//        listViewSecond.setVisibility(View.VISIBLE);
//        layoutStartTime = (LinearLayout) view.findViewById(R.id.layout_second_start_time);
//        layoutStartTime.setVisibility(View.GONE);
//        layoutStartTime1 = (RelativeLayout) view.findViewById(R.id.layout_start_time_1);
//        layoutStartTime2 = (RelativeLayout) view.findViewById(R.id.layout_start_time_2);
//    }
    public static void actionStart(Context context, Bundle data) {
        Intent intent = new Intent(context, InnerTravelCityActivity.class);
        if (data != null) {
            intent.putExtras(data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAG = null;
        tvTitleTop = null;
        ivTitleLeft = null;
        titles.clear();
        titles = null;
        indicatorInnerTravel = null;
        viewPager = null;
        mAdapter = null;
        mContent.clear();
        mContent = null;
        followFragment = null;
        freedomFragment = null;
        layout = null;
        tvSortDefault = null;
        tvSortDays = null;
        tvStartTime = null;
        tvScreenPrice = null;
        cityId = null;
        cityName = null;
        sort = null;
        day = null;
        price = null;
        earlyTime = null;
        laterTime = null;
        listDays.clear();
        listDays= null;
        listPrices.clear();
        listPrices= null;
        popupWindow = null;
    }
}