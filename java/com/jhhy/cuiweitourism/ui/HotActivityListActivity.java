package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotActivityListViewAdapter;
import com.jhhy.cuiweitourism.biz.FindLinesBiz;
import com.jhhy.cuiweitourism.biz.ScreenBiz;
import com.jhhy.cuiweitourism.moudle.PriceArea;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.popupwindows.InnerTravelPopupWindow;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowSearchLine;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class HotActivityListActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private ArrayList<Travel> listFreedom;

    private PullToRefreshListView pullToRefreshListView;
    private HotActivityListViewAdapter adapter;

    private View layout;
    private TextView tvSortDefault;
    private TextView tvSortDays;
    private TextView tvStartTime;
    private TextView tvScreenPrice;

    private int tag = 0;

    private List<String> listDays = new ArrayList<>();
    private List<PriceArea> listPrices = new ArrayList<>();

    private int page = 1;
    private String fromCityId = "1";
    private String sort = ""; //排序
    private String day = ""; //行程天数
    private String price = ""; //价格
    private String earlyTime = ""; //最早出发时间
    private String laterTime = ""; //最晚出发时间

    private Handler handler = new Handler(){
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
        setContentView(R.layout.activity_hot_activity_list);
        getData();
        getInternetData();
        setupView();
        addListener();
        initDatas();
    }

    private void getInternetData() {
        //获取筛选的行程天数和价格
        ScreenBiz screenBiz = new ScreenBiz(getApplicationContext(), handler);
        screenBiz.getScreenDays();
        screenBiz.getScreenPrice();
    }

    private void getData() {
//"areaid":"20","order":"addtime desc","day":"5","price":"2000,50000","zcfdate":"","page":"1","offset":"10"

    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("热门活动");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        layout = findViewById(R.id.activity_hot_activity_list);
//        indicatorInnerTravel = (TabLayout) findViewById(R.id.activity_inner_travel_indicator_top);
//        viewPager = (ViewPager) findViewById(R.id.activity_inner_travel_viewpager);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.activity_hot_activity_list_view);
        //这几个刷新Label的设置
        pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //上拉、下拉监听函数
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        tvSortDefault = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_sort_default);
        tvSortDays = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_trip_days);
        tvStartTime = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_start_time);
        tvScreenPrice = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_screen_price);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvSortDefault.setOnClickListener(this);
        tvSortDays.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvScreenPrice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_tab1_hot_activity_list_sort_default:
                tag = 1;
                showPopupWindow();
                break;
            case R.id.tv_tab1_hot_activity_list_trip_days:
                tag = 2;
                showPopupWindow();
                break;
            case R.id.tv_tab1_hot_activity_list_start_time:
                tag = 3;
                showPopupWindow();
                break;
            case R.id.tv_tab1_hot_activity_list_screen_price:
                tag = 4;
                showPopupWindow();
                break;

        }
    }

    private void showPopupWindow() {
        if (popupWindowSearchLine == null) {
            popupWindowSearchLine = new PopupWindowSearchLine(HotActivityListActivity.this, layout, tag, listDays, listPrices);
            addPopListener();
        }else{
            popupWindowSearchLine.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        }
    }


    private void initDatas() {
        listFreedom = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            Travel travel = new Travel();
            travel.setTravelTitle(getString(R.string.tab1_recommend_for_you_title));
            travel.setTravelPrice("62598");
            travel.setAccount(i);
            listFreedom.add(travel);
        }
        adapter = new HotActivityListViewAdapter(getApplicationContext(), listFreedom);
        pullToRefreshListView.setAdapter(adapter);

    }

    private PopupWindowSearchLine popupWindowSearchLine;

    private void addPopListener(){
        popupWindowSearchLine.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //获取相关参数值：摁了“确定”，返回按钮，摁了“取消”，BACK键
                boolean commit = popupWindowSearchLine.getCommit();
                if (commit) {
                    String newSort = popupWindowSearchLine.getSort();
                    if (newSort != null) {
                        sort = newSort;
                    }
                    String newDay = popupWindowSearchLine.getDay();
                    if (newDay != null) {
                        day = newDay;
                    }
                    String newPrice = popupWindowSearchLine.getPrice();
                    if (newPrice != null) {
                        price = newPrice;
                    }
                    String newEarylTime = popupWindowSearchLine.getEarlyTime();
                    if (newEarylTime != null) {
                        earlyTime = newEarylTime;
                    }
                    String newLaterTime = popupWindowSearchLine.getLaterTime();
                    if (newLaterTime != null) {
                        laterTime = newLaterTime;
                    }
                    //TODO 重新请求数据

                }
            }
        });
    }
    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, HotActivityListActivity.class);
        if(data !=null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
