package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneListAdapter;
import com.jhhy.cuiweitourism.adapter.TrainListAdapter;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.biz.TrainTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketInfoForChinalRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.TrainTicketFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketInfoOfChina;
import com.jhhy.cuiweitourism.net.models.ResponseModel.TrainTicketDetailInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowScreenPlane;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowScreenTrain;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlaneListActivity extends BaseActionBarActivity implements  AdapterView.OnItemClickListener, PopupWindow.OnDismissListener //,RadioGroup.OnCheckedChangeListener
{

    private String TAG = "PlaneListActivity";
    private PlaneTicketActionBiz planeBiz; //机票业务类

    private TextView tvPreDay; //前一天
    private TextView tvNextDay; //后一天
    private TextView tvCurrentDay; //今天 2016-10-24 周一

//    private View parent;
    private PullToRefreshListView pullListView;
    private ListView listView;
    private List<PlaneTicketInfoOfChina.FlightInfo> list = new ArrayList<>();
    private List<PlaneTicketInfoOfChina.FlightInfo> listCopy = new ArrayList<>();
    private PlaneListAdapter adapter;

    private PlaneTicketInfoOfChina info; //查询得到的航班信息

//    private RadioGroup bottomRg; //底部筛选组合
    private RadioButton rbScreen;       //筛选
    private RadioButton rbSortTime;    //出发时间排序
    private RadioButton rbSortPrice;    //机票价格排序

    private Drawable drawableSortStartTimeIncrease; //时间从早到晚，升序，默认
    private Drawable drawableSortStartTimeDecrease; //时间从晚到早，降序

    private Drawable drawableSortPriceIncrease; //价格从高到低，升序，默认
    private Drawable drawableSortPriceDecrease; //价格从高到低，降序

    private Drawable timeDrawable; //时间初始图片
    private Drawable priceDrawable; //价格初始图片

    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市
    private String dateFrom; //出发日期
    private String tempTime; //出发时间，下一天，上一天用

    private String dateReturn; //返程日期
    private String traveltype; //航程类型 OW（单程） RT（往返）

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
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_list);
        getData();
        super.onCreate(savedInstanceState);
        getInternetData();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        fromCity = (PlaneTicketCityInfo) bundle.getSerializable("fromCity");
        toCity = (PlaneTicketCityInfo) bundle.getSerializable("toCity");
        dateFrom = bundle.getString("dateFrom");
        traveltype = bundle.getString("traveltype");
        if ("RT".equals(traveltype)){ //往返
            dateReturn = bundle.getString("dateReturn");
        }
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(String.format("%s—%s", fromCity.getName(), toCity.getName()));
        if ("RT".equals(traveltype)){
            tvPlaneDate.setVisibility(View.VISIBLE);
            tvPlaneDate.setText(String.format(Locale.getDefault(), "选去程：%s", dateFrom.substring(0, dateFrom.indexOf(" "))));
        }

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
        pullListView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView = pullListView.getRefreshableView();

        //上拉、下拉监听函数
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        adapter = new PlaneListAdapter(getApplicationContext(), list, fromCity, toCity, 1);
        listView.setAdapter(adapter);

        rbScreen = (RadioButton) findViewById(R.id.rb_plane_screen);
        rbScreen.setVisibility(View.GONE);
//        bottomRg = (RadioGroup) findViewById(R.id.rg_train_list);
        rbSortTime = (RadioButton) findViewById(R.id.rb_plane_start_time);
        rbSortPrice = (RadioButton) findViewById(R.id.rb_plane_price);

        //起飞时间升序
        drawableSortStartTimeIncrease = ContextCompat.getDrawable(PlaneListActivity.this, R.mipmap.icon_train_start_time_decrease); //时间从早到晚
        drawableSortStartTimeDecrease = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_train_start_time_increase); //时间从晚到早

        drawableSortStartTimeIncrease.setBounds(0, 0, drawableSortStartTimeIncrease.getMinimumWidth(), drawableSortStartTimeIncrease.getMinimumHeight());
        drawableSortStartTimeDecrease.setBounds(0, 0, drawableSortStartTimeDecrease.getMinimumWidth(), drawableSortStartTimeDecrease.getMinimumHeight());

        //价格排序
        drawableSortPriceIncrease = ContextCompat.getDrawable(PlaneListActivity.this, R.mipmap.icon_price_incrase); //时间从早到晚
        drawableSortPriceDecrease = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_price_decrase); //时间从晚到早

        drawableSortPriceIncrease.setBounds(0, 0, drawableSortPriceIncrease.getMinimumWidth(), drawableSortPriceIncrease.getMinimumHeight());
        drawableSortPriceDecrease.setBounds(0, 0, drawableSortPriceDecrease.getMinimumWidth(), drawableSortPriceDecrease.getMinimumHeight());


        //radioButton原始图片
        timeDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_train_start_time);
        priceDrawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_price_plane);

        timeDrawable.setBounds(0, 0, timeDrawable.getMinimumWidth(), timeDrawable.getMinimumHeight());
        priceDrawable.setBounds(0, 0, priceDrawable.getMinimumWidth(), priceDrawable.getMinimumHeight());

        planeBiz = new PlaneTicketActionBiz();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tv_train_preference: //前一天
                tempTime = getDateStr(dateFrom.substring(0, dateFrom.indexOf(" ")), -1);
                getInternetData();
                break;
            case R.id.tv_train_next_day: //后一天
                tempTime = getDateStr(dateFrom.substring(0, dateFrom.indexOf(" ")), 1);
                getInternetData();
                break;
            case R.id.rb_plane_screen: //筛选
                screen();
                break;
            case R.id.rb_plane_start_time: //出发时间排序
                setRbBg();
                sortByStartTime();
                adapter.setData(list);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rb_plane_price: //价格排序
                setRbBg();
                sortByPrice();
                adapter.setData(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    //筛选飞机列表 起飞时间，机场，机型，航空公司
    private PopupWindowScreenPlane popupScreenPlane;
    private int startTimePosition;  //起飞时间
    private int innerAirport;       //机场
    private int innerPlaneType;     //机型
    private int innerCompany;       //航空公司

    private void screen() {
        if (popupScreenPlane == null){
            popupScreenPlane = new PopupWindowScreenPlane(getApplicationContext());
            popupScreenPlane.setOnDismissListener(this);
        }
        if (popupScreenPlane.isShowing()){
            popupScreenPlane.dismiss();
        }else{
            popupScreenPlane.showAtLocation(rbScreen, Gravity.BOTTOM, 0, 0);
            popupScreenPlane.refreshView(startTimePosition, innerAirport, innerPlaneType, innerCompany);
        }
    }

    @Override
    public void onDismiss() {
        boolean commit = popupScreenPlane.getCommit();
        LogUtil.e(TAG, "commit = " + commit);
        if (commit){
            //提交
            //分别筛选,再查时间和价格是否排序
            startTimePosition  = popupScreenPlane.getSelectionStartTime();
            innerAirport    = popupScreenPlane.getSelectionAirport();
            innerPlaneType  = popupScreenPlane.getSelectionPlaneType();
            innerCompany    = popupScreenPlane.getSelectionCompany();

            list.clear();

            if (startTimePosition == 0){ //不限
                list.addAll(listCopy);
            } else {
                for (PlaneTicketInfoOfChina.FlightInfo flightInfo: listCopy) {
                    if (1 == startTimePosition && Utils.getEqualMinutePoint(flightInfo.depTime, "1200")) { //上午 00:00-12:00
                        list.add(flightInfo);
                    } else if (2 == startTimePosition && !Utils.getEqualMinutePoint(flightInfo.depTime, "1200") && Utils.getEqualMinutePoint(flightInfo.depTime, "1400")) { //中午
                        list.add(flightInfo);
                    } else if (3 == startTimePosition && !Utils.getEqualMinutePoint(flightInfo.depTime, "1400") && Utils.getEqualMinutePoint(flightInfo.depTime, "1800")) { //下午
                        list.add(flightInfo);
                    } else if (4 == startTimePosition && !Utils.getEqualMinutePoint(flightInfo.depTime, "1800") && Utils.getEqualMinutePoint(flightInfo.depTime, "2400")) { //晚上
                        list.add(flightInfo);
                    }
                }
            }

            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }
    }

    private void setRbBg() {
        rbSortTime.setCompoundDrawables(null, timeDrawable, null, null);
        rbSortPrice.setCompoundDrawables(null, priceDrawable, null, null);
    }

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
        Intent intent = new Intent(getApplicationContext(), PlaneItemInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("flight", list.get((int) l));
        bundle.putString("dateFrom", dateFrom);
        bundle.putSerializable("fromCity", fromCity);
        bundle.putSerializable("toCity", toCity);
        bundle.putString("traveltype", traveltype);
        bundle.putString("dateReturn", dateReturn);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_TRAIN_ITEM); //查看某趟航班
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
        Collections.sort(list, new Comparator<PlaneTicketInfoOfChina.FlightInfo>() {
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
        }
    }

    private void sortByPrice(){
        Collections.sort(list, new Comparator<PlaneTicketInfoOfChina.FlightInfo>() {
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
        });
        sortStartTimeIncrease = false;
        if (sortPriceIncrease){ //如果当前是从早到晚，则改变为从晚到早
            Collections.reverse(list);
            //更换图标
            sortPriceIncrease = false; //任何一个顺序改变，都变为false
            rbSortPrice.setCompoundDrawables(null, drawableSortPriceDecrease, null, null);
        }else{
            sortPriceIncrease = true;
            rbSortPrice.setCompoundDrawables(null, drawableSortPriceIncrease, null, null);
        }
    }

    //获取国内飞机票
    private void getInternetData(){
        LoadingIndicator.show(PlaneListActivity.this, getString(R.string.http_notice));
        new Thread(){
            @Override
            public void run() {
                super.run();
                PlaneTicketInfoForChinalRequest request = new PlaneTicketInfoForChinalRequest(fromCity.getCode(), toCity.getCode(), tempTime.substring(0, tempTime.indexOf(" ")));
                planeBiz.planeTicketInfoInChina(request, new BizGenericCallback<PlaneTicketInfoOfChina>() {
                    @Override
                    public void onCompletion(GenericResponseModel<PlaneTicketInfoOfChina> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
                            info = model.body;
                            list = info.returnInfo.flightItems.flights;
                            listCopy.addAll(list);
                            handler.sendEmptyMessage(1);
                            LogUtil.e(TAG,"planeTicketInfoInChina =" + info.toString());
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
                        LogUtil.e(TAG, "planeTicketInfoInChina: " + error.toString());
                    }
                });
            }
        }.start();

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
