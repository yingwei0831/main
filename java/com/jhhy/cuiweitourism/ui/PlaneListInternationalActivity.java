package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneListAdapter;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInfoForChinalRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInfoInternationalRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInternationalInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowPlaneOuterScreen;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowPlanePriceType;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowPlaneSortType;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PlaneListInternationalActivity extends BaseActionBarActivity implements  AdapterView.OnItemClickListener, PopupWindow.OnDismissListener //,RadioGroup.OnCheckedChangeListener
{

    private String TAG = "PlaneListInternationalActivity";
    private PlaneTicketActionBiz planeBiz; //机票业务类

    private TextView tvPreDay; //前一天
    private TextView tvNextDay; //后一天
    private TextView tvCurrentDay; //今天 2016-10-24 周一

//    private View parent;
    private PullToRefreshListView pullListView;
    private ListView listView;
    private List<PlaneTicketInternationalInfo.PlaneTicketInternationalF> list = new ArrayList<>();

    private PlaneListAdapter adapter;

    public static PlaneTicketInternationalInfo info; //查询得到的航班信息

//    private RadioGroup bottomRg; //底部筛选组合
    private RadioButton rbScreen;       //筛选
    private RadioButton rbSortTime;    //出发时间排序
    private RadioButton rbSortPrice;    //机票价格排序
    private int bottomTag = 0;

//    private Drawable drawableSortStartTimeIncrease; //时间从早到晚，升序，默认
//    private Drawable drawableSortStartTimeDecrease; //时间从晚到早，降序
//
//    private Drawable drawableSortPriceIncrease; //价格从高到低，升序，默认
//    private Drawable drawableSortPriceDecrease; //价格从高到低，降序

//    private Drawable timeDrawable; //时间初始图片
//    private Drawable priceDrawable; //价格初始图片

    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市
    private String dateFrom; //出发日期
    private String tempTime; //出发时间，下一天，上一天用

    private String dateReturn = ""; //返程日期
    private String traveltype; //航程类型 OW（单程） RT（往返）
    private String stoptype = "A"; // 是否中转 A（所有） D（直达）
    private String carrier = ""; //航司

    private boolean refresh; //下拉刷新

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    break;
                case -2:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求飞机票信息出错，请返回重试");
                    break;
                case 1:
                    dateFrom = tempTime;
                    tvCurrentDay.setText(dateFrom);
                    adapter.setData(listData);
                    adapter.notifyDataSetChanged();
                    break;
            }
            if (refresh){ //下拉刷新，将数据清空
                pullListView.onRefreshComplete();
                refresh = false;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pullListView.isRefreshing()){
                            pullListView.onRefreshComplete();
                        }
                    }
                }, 2000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_list);
        getData();
        super.onCreate(savedInstanceState);
        LoadingIndicator.show(PlaneListInternationalActivity.this, getString(R.string.http_notice));
        getInternetData();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        fromCity = (PlaneTicketCityInfo) bundle.getSerializable("fromCity");
        toCity = (PlaneTicketCityInfo) bundle.getSerializable("toCity");
        dateFrom = bundle.getString("dateFrom");
        traveltype = bundle.getString("traveltype");
        if ("RT".equals(traveltype)){
            dateReturn = bundle.getString("dateReturn");
        }
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(String.format("%s—%s", fromCity.getName(), toCity.getName()));

        tvPreDay = (TextView) findViewById( R.id.tv_train_preference);
        tvNextDay = (TextView) findViewById(R.id.tv_train_next_day);
        tvCurrentDay = (TextView) findViewById(R.id.tv_train_ticket_day);
        tempTime = dateFrom;
        tvCurrentDay.setText(dateFrom);

//        parent = findViewById(R.id.train_list_parent);
        pullListView = (PullToRefreshListView) findViewById(R.id.list_train_detail);
        //这几个刷新Label的设置
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("松开加载更多");

        //上拉、下拉设定
        pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView = pullListView.getRefreshableView();

        //上拉、下拉监听函数
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新,清空数据
                if (refresh)    return;
                refresh = true;
                getInternetData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载

            }
        });

        adapter = new PlaneListAdapter(getApplicationContext(), listData, fromCity, toCity, 2);
        listView.setAdapter(adapter);

        rbScreen = (RadioButton) findViewById(R.id.rb_plane_screen);

//        bottomRg = (RadioGroup) findViewById(R.id.rg_train_list);
        rbSortTime = (RadioButton) findViewById(R.id.rb_plane_start_time);
        rbSortTime.setText(getString(R.string.plane_flight_price_sort_recommend));
        rbSortTime.setTextColor(Color.WHITE);

        rbSortPrice = (RadioButton) findViewById(R.id.rb_plane_price);
        rbSortPrice.setText(getString(R.string.plane_flight_separate)); //默认：票价+税费
        rbSortPrice.setTextColor(Color.WHITE);

        //起飞时间升序
//        drawableSortStartTimeIncrease = ContextCompat.getDrawable(PlaneListInternationalActivity.this, R.mipmap.icon_train_start_time_decrease); //时间从早到晚
//        drawableSortStartTimeDecrease = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_train_start_time_increase); //时间从晚到早
//
//        drawableSortStartTimeIncrease.setBounds(0, 0, drawableSortStartTimeIncrease.getMinimumWidth(), drawableSortStartTimeIncrease.getMinimumHeight());
//        drawableSortStartTimeDecrease.setBounds(0, 0, drawableSortStartTimeDecrease.getMinimumWidth(), drawableSortStartTimeDecrease.getMinimumHeight());

        //价格排序
//        drawableSortPriceIncrease = ContextCompat.getDrawable(PlaneListInternationalActivity.this, R.mipmap.icon_price_incrase); //时间从早到晚
//        drawableSortPriceDecrease = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_price_decrase); //时间从晚到早
//
//        drawableSortPriceIncrease.setBounds(0, 0, drawableSortPriceIncrease.getMinimumWidth(), drawableSortPriceIncrease.getMinimumHeight());
//        drawableSortPriceDecrease.setBounds(0, 0, drawableSortPriceDecrease.getMinimumWidth(), drawableSortPriceDecrease.getMinimumHeight());


        //radioButton原始图片
//        timeDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_train_start_time);
//        priceDrawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_price_plane);

//        timeDrawable.setBounds(0, 0, timeDrawable.getMinimumWidth(), timeDrawable.getMinimumHeight());
//        priceDrawable.setBounds(0, 0, priceDrawable.getMinimumWidth(), priceDrawable.getMinimumHeight());

        planeBiz = new PlaneTicketActionBiz();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_train_preference: //前一天
                tempTime = getDateStr(dateFrom.substring(0, dateFrom.indexOf(" ")), -1);
                LoadingIndicator.show(PlaneListInternationalActivity.this, getString(R.string.http_notice));
                getInternetData();
                break;
            case R.id.tv_train_next_day: //后一天
                tempTime = getDateStr(dateFrom.substring(0, dateFrom.indexOf(" ")), 1);
                LoadingIndicator.show(PlaneListInternationalActivity.this, getString(R.string.http_notice));
                getInternetData();
                break;
            case R.id.rb_plane_screen: //筛选
                bottomTag = 1;
                screen();
                break;
            case R.id.rb_plane_start_time: //出发时间排序
//                setRbBg();
//                sortByStartTime();
                bottomTag = 2;
                showPopSort();
                adapter.setData(listData);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_plane_price: //价格筛选（含税总价/票价+税价）
//                setRbBg();
//                sortByPrice();
                bottomTag = 3;
                showPopPrice();
                adapter.setData(listData);
                adapter.notifyDataSetChanged();
                break;
        }
    }



//    private void setRbBg() {
//        rbSortTime.setCompoundDrawables(null, timeDrawable, null, null);
//        rbSortPrice.setCompoundDrawables(null, priceDrawable, null, null);
//    }

    @Override
    protected void addListener() {
        super.addListener();
        tvPreDay .setOnClickListener(this);
        tvNextDay.setOnClickListener(this);

        rbScreen.setOnClickListener(this);
        rbSortTime.setOnClickListener(this);
        rbSortPrice.setOnClickListener(this);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(getApplicationContext(), "查看某各航班 " + l);
//        Intent intent = new Intent(getApplicationContext(), PlaneItemInfoActivity.class);
//        Bundle bundle = new Bundle();
//        PlaneTicketInfoOfChina.FlightInfo flight = list.get((int) l);
//        bundle.putSerializable("flight", flight);
//        bundle.putString("dateFrom", dateFrom);
//        bundle.putSerializable("fromCity", fromCity);
//        bundle.putSerializable("toCity", toCity);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, VIEW_TRAIN_ITEM); //查看某趟列车
    }

    private int VIEW_TRAIN_ITEM = 7546; //查看某趟列车

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_TRAIN_ITEM){ //有可能订票了
            if (resultCode == RESULT_OK){

            }
        }
    }

    private boolean sortStartTimeIncrease = true; //从早到晚,出发时间（默认从早到晚）
    private boolean sortPriceIncrease = false; //价格从低到高

    //按出发时间排序
    private void sortByStartTime() {
        //默认从早到晚排序
        /*Collections.sort(list, new Comparator<PlaneTicketInfoOfChina.FlightInfo>() {
            public int compare(PlaneTicketInfoOfChina.FlightInfo arg0, PlaneTicketInfoOfChina.FlightInfo arg1) {
                //1400
                int hour1 = Integer.parseInt(arg0.depTime.substring(0, 2));
                int hour2 = Integer.parseInt(arg1.depTime.substring(0, 2));
                if (hour1 > hour2) {
                    return 1;
                } else if (hour1 == hour2) {
                    int minute1 = Integer.parseInt(arg0.depTime.substring(2));
                    int minute2 = Integer.parseInt(arg1.depTime.substring(2));
                    if (minute1 > minute2) {
                        return 1;
                    } else if (minute1 == minute2) {
                        return 0;
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            }
        });
        sortPriceIncrease = false;
        if (sortStartTimeIncrease){ //如果当前是从早到晚，则改变为从晚到早
            Collections.reverse(list);
            //更换图标
            sortStartTimeIncrease = false; //任何一个顺序改变，都变为false
            rbSortTime.setCompoundDrawables(null, drawableSortStartTimeDecrease, null, null);
        }else{
            sortStartTimeIncrease = true;
            rbSortTime.setCompoundDrawables(null, drawableSortStartTimeIncrease, null, null);
        }*/
    }

    private void sortByPrice(){
        /*Collections.sort(list, new Comparator<PlaneTicketInfoOfChina.FlightInfo>() {
            public int compare(PlaneTicketInfoOfChina.FlightInfo arg0, PlaneTicketInfoOfChina.FlightInfo arg1) {
                //parPrice:3200
                ArrayList<PlaneTicketInfoOfChina.SeatItemInfo> seats0 = arg0.getSeatItems();
                ArrayList<PlaneTicketInfoOfChina.SeatItemInfo> seats1 = arg1.getSeatItems();

                Collections.sort(seats0);
                Collections.sort(seats1);

                PlaneTicketInfoOfChina.SeatItemInfo seat0 = seats0.get(0);
                PlaneTicketInfoOfChina.SeatItemInfo seat1 = seats1.get(0);

                int price0 = Integer.parseInt(seat0.parPrice);
                int price1 = Integer.parseInt(seat1.parPrice);

                if (price0 > price1) {
                    return 1;
                } else if (price0 == price1) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });*/
        /*sortStartTimeIncrease = false;
        if (sortPriceIncrease){ //如果当前是从早到晚，则改变为从晚到早
            Collections.reverse(list);
            //更换图标
            sortPriceIncrease = false; //任何一个顺序改变，都变为false
            rbSortPrice.setCompoundDrawables(null, drawableSortPriceDecrease, null, null);
        }else{
            sortPriceIncrease = true;
            rbSortPrice.setCompoundDrawables(null, drawableSortPriceIncrease, null, null);
        }*/
    }

    //获取国内飞机票
    private void getInternetData(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                PlaneTicketInfoInternationalRequest request = new PlaneTicketInfoInternationalRequest(traveltype, fromCity.getCode(), toCity.getCode(),
                        dateFrom.substring(0, dateFrom.indexOf(" ")), dateReturn, stoptype, carrier);
//{"head":{"code":"Plane_gjhb"},"field":{"boardpoint":"PEK","offPoint":"CDG","departuredate":"2016-12-02 星期五","stoptype":"A","carrier":"","returndate":""}} //填写返程日期
//{"head":{"code":"Plane_gjhb"},"field":{"boardpoint":"PEK","offPoint":"CDG","departuredate":"2016-12-02 星期五","traveltype":"OW","stoptype":"A","carrier":"","returndate":""}} //参数错误
//{"head":{"code":"Plane_gjhb"},"field":{"boardpoint":"PEK","offPoint":"BER","departuredate":"2016-12-02","traveltype":"OW","stoptype":"A","carrier":"","returndate":""}} //参数错误?
                planeBiz.planeTicketInfoOfInternational(request, new BizGenericCallback<PlaneTicketInternationalInfo>() {
                    @Override
                    public void onCompletion(GenericResponseModel<PlaneTicketInternationalInfo> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
                            if (refresh){
                                info = null;
                                listData.clear();
                                listData = null;
                            }
                            info = model.body;
                            list = new ArrayList<>(info.FMap.values());
                            listData = list;
                            handler.sendEmptyMessage(1);
                            LogUtil.e(TAG,"planeTicketInfoInternational =" + info.toString());
                        }
                        LoadingIndicator.cancel();
                    }

                    @Override
                    public void onError(FetchError error) {
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-2);
                        }
                        LoadingIndicator.cancel();
                        LogUtil.e(TAG, "planeTicketInfoInternational: " + error.toString());
                    }
                });
            }
        }.start();

    }

    //筛选
    private PopupWindowPlaneOuterScreen popScreen;
    private boolean direct;
    private int screen1 = 0;
    private int screen2 = -1;
    private int screen3 = -1;
    private int screen4 = -1;
    private String code2 = "";
    private String code3 = "";
    private String code4 = "";

    private void screen() {
        if (popScreen == null){
            popScreen = new PopupWindowPlaneOuterScreen(getApplicationContext());
            popScreen.setOnDismissListener(this);
        }
        if (popScreen.isShowing()){
            popScreen.dismiss();
        }else{
            popScreen.refreshView(direct, screen1, screen2, screen3, screen4);
            popScreen.showAtLocation(rbScreen, Gravity.BOTTOM, 0, 0);
        }

    }

    private PopupWindowPlaneSortType popSortType;
    private int sortSelection = 1;

    private void showPopSort() {
        if (popSortType == null){
            popSortType = new PopupWindowPlaneSortType(getApplicationContext());
            popSortType.setOnDismissListener(this);
        }
        if (popSortType.isShowing()){
            popSortType.dismiss();
        }else{
            popSortType.showAtLocation(rbSortTime, Gravity.BOTTOM, 0, 0);
            popSortType.refreshView(sortSelection);
        }
    }


    private PopupWindowPlanePriceType popPriceType;
    private int selection = 2; //1：含税总价；2:票价+税价； 默认票价+税价

    private void showPopPrice() {
        if (popPriceType == null){
            popPriceType = new PopupWindowPlanePriceType(getApplicationContext());
            popPriceType.setOnDismissListener(this);
        }
        if (popPriceType.isShowing()){
            popPriceType.dismiss();
        }else{
            popPriceType.showAtLocation(rbSortPrice, Gravity.BOTTOM, 0, 0);
            popPriceType.refreshView(selection);
        }
    }

    @Override
    public void onDismiss() {
        LogUtil.e(TAG, "bottomTag = " + bottomTag);
        if (bottomTag == 3) {
            int newSelection = popPriceType.getSelection();
            LogUtil.e(TAG, "selection" + selection + ", newSelection = " + newSelection);
//            if (selection == newSelection) return;
            selection = newSelection;
            if (selection == 1) {
                rbSortPrice.setText(getString(R.string.plane_flight_tax));
            } else {
                rbSortPrice.setText(getString(R.string.plane_flight_separate));
            }
            //TODO 重新显示
            adapter.setPriceType(selection);
        }else if (bottomTag == 2){
            int newSortSelection = popSortType.getSelection();
            LogUtil.e(TAG, "sortSelection = " + sortSelection + ", newSortSelection = " + newSortSelection);
//            if (sortSelection == newSortSelection)  return;
            sortSelection = newSortSelection;
            if (sortSelection == 1){ //直飞优先
                sortDirect();
                rbSortTime.setText(getString(R.string.plane_flight_price_sort_recommend));
            }else if (sortSelection == 2){ //价格 低—>高
                sortPrice();
                rbSortTime.setText(getString(R.string.plane_flight_price_sort_increase_txt));
            }else if (sortSelection == 3){ //起飞 早—>晚
                sortFromTimeIncrease = 1;
                sortFromTime();
                rbSortTime.setText(getString(R.string.plane_flight_price_sort_from_increase_txt));
            }else if (sortSelection == 4){ //起飞 晚—>早
                sortFromTimeIncrease = -1;
                sortFromTime();
                rbSortTime.setText(getString(R.string.plane_flight_price_sort_from_decrease_txt));
            }else if (sortSelection == 5){ //到达 早—>晚
                sortArrivalTimeIncrease = 1;
                sortArrivalTime();
                rbSortTime.setText(getString(R.string.plane_flight_price_sort_arrival_increase_txt));
            }else if (sortSelection == 6){ //到达 晚—>早
                sortArrivalTimeIncrease = -1;
                sortArrivalTime();
                rbSortTime.setText(getString(R.string.plane_flight_price_sort_arrival_decrease_txt));
            }else if (sortSelection == 7){ //总时长 短—>长
                sortConsuming();
                rbSortTime.setText(getString(R.string.plane_flight_price_sort_consuming_increase_txt));
            }
            adapter.setData(listData);
            adapter.notifyDataSetChanged();
        }else if (bottomTag == 1){
            boolean commit = popScreen.isCommit();
            if (commit){
                direct = popScreen.isDirect();
                int newScreen1 = popScreen.getS1();
                int newScreen2 = popScreen.getS2();
                int newScreen3 = popScreen.getS3();
                int newScreen4 = popScreen.getS4();
                if (newScreen1 != screen1){
                    screen1 = newScreen1;
                }
                if (newScreen2 != screen2){
                    screen2 = newScreen2;
                    code2 = popScreen.getS2Code();
                }
                if (newScreen3 != screen3){
                    screen3 = newScreen3;
                    code3 = popScreen.getS3Code();
                }
                if (newScreen4 != screen4){
                    screen4 = newScreen4;
                    code4 = popScreen.getS4Code();
                }
                sortScreen();
            }
        }
    }

    //筛选5个条件
    private void sortScreen() {
        
    }

    private void sortConsuming() {
        Collections.sort(listData, new Comparator<PlaneTicketInternationalInfo.PlaneTicketInternationalF>() {
            @Override
            public int compare(PlaneTicketInternationalInfo.PlaneTicketInternationalF f1, PlaneTicketInternationalInfo.PlaneTicketInternationalF f2) {
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f1s = f1.S1;
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f2s = f2.S1;

                if(Utils.getTimeH(String.format(Locale.getDefault(), "%s %s", f1s.toDate, f1s.toTime)) > Utils.getTimeH(String.format(Locale.getDefault(), "%s %s", f2s.toDate, f2s.toTime))) {
                    return 1;
                } else if (Utils.getTimeH(String.format(Locale.getDefault(), "%s %s", f1s.toDate, f1s.toTime)) < Utils.getTimeH(String.format(Locale.getDefault(), "%s %s", f2s.toDate, f2s.toTime))) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private int sortArrivalTimeIncrease = 0;
    private void sortArrivalTime() {
        Collections.sort(listData, new Comparator<PlaneTicketInternationalInfo.PlaneTicketInternationalF>() {
            @Override
            public int compare(PlaneTicketInternationalInfo.PlaneTicketInternationalF f1, PlaneTicketInternationalInfo.PlaneTicketInternationalF f2) {
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f1s = f1.S1;
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f2s = f2.S1;

                if(Utils.getTimeHM(f1s.toTime) > Utils.getTimeHM(f2s.toTime)) {
                    return 1;
                } else if (Utils.getTimeHM(f1s.toTime) < Utils.getTimeHM(f2s.toTime)) {
                    return -1;
                }
                return 0;
            }
        });
        if (sortArrivalTimeIncrease == -1){
            Collections.reverse(listData);
        }
    }

    private int sortFromTimeIncrease = 0;
    private void sortFromTime() {
        Collections.sort(listData, new Comparator<PlaneTicketInternationalInfo.PlaneTicketInternationalF>() {
            @Override
            public int compare(PlaneTicketInternationalInfo.PlaneTicketInternationalF f1, PlaneTicketInternationalInfo.PlaneTicketInternationalF f2) {
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f1s = f1.S1;
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f2s = f2.S1;

                if (Utils.getTimeHM(f1s.fromTime) > Utils.getTimeHM(f2s.fromTime)) {
                    return 1;
                } else if (Utils.getTimeHM(f1s.fromTime) < Utils.getTimeHM(f2s.fromTime)) {
                    return -1;
                }
                return 0;
            }
        });
        if (sortFromTimeIncrease == -1){
            Collections.reverse(listData);
        }
    }

    private void sortPrice() {
        Collections.sort(listData, new Comparator<PlaneTicketInternationalInfo.PlaneTicketInternationalF>() {
            @Override
            public int compare(PlaneTicketInternationalInfo.PlaneTicketInternationalF f1, PlaneTicketInternationalInfo.PlaneTicketInternationalF f2) {
                PlaneTicketInternationalInfo.PlaneTicketInternationalHF hf1 = info.HMap.get(f1.F);
                PlaneTicketInternationalInfo.PlaneTicketInternationalHF hf2 = info.HMap.get(f2.F);
                if (selection == 1){ //含税总价排序
                    if (Integer.parseInt(hf1.cabin.totalFare.taxTotal) > Integer.parseInt(hf2.cabin.totalFare.taxTotal)){
                        return 1;
                    }else if(Integer.parseInt(hf1.cabin.totalFare.taxTotal) < Integer.parseInt(hf2.cabin.totalFare.taxTotal)){
                        return -1;
                    }
                }else{ //票价排序
                    if (Integer.parseInt(hf1.cabin.baseFare.faceValueTotal) > Integer.parseInt(hf2.cabin.baseFare.faceValueTotal)){
                        return 1;
                    }else if(Integer.parseInt(hf1.cabin.baseFare.faceValueTotal) < Integer.parseInt(hf2.cabin.baseFare.faceValueTotal)){
                        return -1;
                    }
                }
                return 0;
            }
        });

    }

    private List<PlaneTicketInternationalInfo.PlaneTicketInternationalF> listData = new ArrayList<>();
    private void sortDirect() { //直飞优先
//        if (s1.stopPeriod == null || s1.stopPeriod.length() == 0) {
//        listData =
//        stoptype = "D"; //"A"所有，"D"直达
        //单程/往返
        Collections.sort(listData, new Comparator<PlaneTicketInternationalInfo.PlaneTicketInternationalF>() {
            @Override
            public int compare(PlaneTicketInternationalInfo.PlaneTicketInternationalF f1, PlaneTicketInternationalInfo.PlaneTicketInternationalF f2) {
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f1s = f1.S1;
                PlaneTicketInternationalInfo.PlaneTicketInternationalFS f2s = f2.S1;

                if (Integer.parseInt(f1s.transferFrequency) > Integer.parseInt(f2s.transferFrequency)){
                    return 1;
                }else if (Integer.parseInt(f1s.transferFrequency) < Integer.parseInt(f2s.transferFrequency)){
                    return -1;
                }
                return 0;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAG = null;
        planeBiz = null;
        rbScreen = null;
        rbSortTime = null;
        rbSortPrice = null;
//        drawableSortStartTimeIncrease = null;
//        drawableSortStartTimeDecrease = null;
        fromCity = null;
        toCity = null;
        dateFrom = null;
        tempTime = null;
        dateReturn = null;
        traveltype = null;
        stoptype = null;
        carrier = null;
        handler = null;
        popPriceType = null;
    }

    /**
    * 获取指定日后 后 dayAddNum 天的 日期
    * @param day  日期，格式为String："2013-9-3";
    * @param dayAddNum 增加天数 格式为int;
    * @return
            */
    private String getDateStr(String day, int dayAddNum) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd E");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }


}
