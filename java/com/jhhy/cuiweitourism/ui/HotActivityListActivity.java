package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotActivityListViewAdapter;
import com.jhhy.cuiweitourism.biz.ScreenBiz;
import com.jhhy.cuiweitourism.model.PhoneBean;
import com.jhhy.cuiweitourism.model.PriceArea;
import com.jhhy.cuiweitourism.net.biz.ActivityActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ActivityHot;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowSearchLine;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门活动列表页
 */
public class HotActivityListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String TAG = HotActivityListActivity.class.getSimpleName();

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private ArrayList<ActivityHotInfo> listFreedom = new ArrayList<>();

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
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
    private String sort = ""; //排序
    private String sortCommit = ""; //排序
    private String day = ""; //行程天数
    private String price = ""; //价格
    private int pricePosition = -1; //价格位置
    private String earlyTime = ""; //最早出发时间
    private String tempEarlyTime = ""; //最早出发时间
    private String laterTime = ""; //最晚出发时间
    private String tempLaterTime = ""; //最晚出发时间

    private PhoneBean selectCity; //主页选择的城市
    private String areaId; //城市id

    private boolean refresh = true; //刷新
    private boolean loadMore; //加载更多

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
        LoadingIndicator.show(HotActivityListActivity.this, getString(R.string.http_notice));
        //获取筛选的行程天数和价格
        ScreenBiz screenBiz = new ScreenBiz(getApplicationContext(), handler);
        screenBiz.getScreenDays();
        screenBiz.getScreenPrice();

        if (selectCity == null){
            areaId = "20"; //此处是北京
        }else{
            areaId = selectCity.getCity_id();
        }

        getHotActivityList();
    }

    private void getData() {
        //"areaid":"20","order":"addtime desc","day":"5","price":"2000,50000","zcfdate":"","page":"1","offset":"10"
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            selectCity = (PhoneBean) bundle.getSerializable("selectCity");
            if (selectCity == null){
                selectCity = new PhoneBean();
                selectCity.setCity_id("20");
                selectCity.setName("北京");
            }
        }
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText("热门活动");
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        layout = findViewById(R.id.activity_hot_activity_list);
//        indicatorInnerTravel = (TabLayout) findViewById(R.id.activity_inner_travel_indicator_top);
//        viewPager = (ViewPager) findViewById(R.id.activity_inner_travel_viewpager);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.activity_hot_activity_list_view);
        listView = pullToRefreshListView.getRefreshableView();
        //这几个刷新Label的设置
        pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");

        //上拉、下拉设定
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //上拉、下拉监听函数
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });

        tvSortDefault = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_sort_default);
        tvSortDays = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_trip_days);
        tvStartTime = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_start_time);
        tvScreenPrice = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_screen_price);
    }

    private void refresh() {
//        if (pullToRefreshListView.isRefreshing())   return;
        LogUtil.e(TAG, "refresh = " + refresh +", loadMore = " + loadMore);
        if (loadMore)   return;
        if (refresh)    return;
        refresh = true;
        page = 1;
        getHotActivityList();
    }

    private void loadMore() {
//        if (pullToRefreshListView.isRefreshing())   return;
        LogUtil.e(TAG, "loadMore = " + loadMore +", refresh = " + refresh);
        if (refresh)    return;
        if (loadMore)   return;
        loadMore = true;
        page ++;
        getHotActivityList();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvSortDefault.setOnClickListener(this);
        tvSortDays.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvScreenPrice.setOnClickListener(this);

        listView.setOnItemClickListener(this);
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
            String toSrot = "";
            if ("2".equals(sort)){
                toSrot = "1";
            }else if ("1".equals(sort)){
                toSrot = "2";
            }
            popupWindowSearchLine.refreshView(tag, toSrot, day, earlyTime, laterTime, pricePosition);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Consts.REQUEST_CODE_DATE_PICKER_EARLY){ //选择最早出发时间
            if (resultCode == RESULT_OK){
                String selectDate = data.getExtras().getString("selectDate");
                popupWindowSearchLine.setEarlyTime(selectDate);
                tempEarlyTime = selectDate;
            }
        }else if (requestCode == Consts.REQUEST_CODE_DATE_PICKER_LATER){ //选择最晚出发时间
            if (resultCode == RESULT_OK){
                String selectDate = data.getExtras().getString("selectDate");
                popupWindowSearchLine.setLaterTime(selectDate);
                tempLaterTime = selectDate;
            }
        } else if (requestCode == VIEW_HOT_ACTIVITY_DETAIL){
            if (resultCode == RESULT_OK){ //有可能预订活动

            }
        }
    }

    private void initDatas() {
        adapter = new HotActivityListViewAdapter(getApplicationContext(), listFreedom);
        pullToRefreshListView.setAdapter(adapter);
    }

    private void refreshView(){
        adapter.setData(listFreedom);
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
                        if ("0".equals(newSort)){
                            sort = "";
                        }else if ("1".equals(newSort)){
                            sort = "2";
                        }else if ("2".equals(newSort)){
                            sort = "1";
                        }
                    }
                    String newDay = popupWindowSearchLine.getDay();
                    if (newDay != null) {
                        day = newDay;
                    }
                    String newPrice = popupWindowSearchLine.getPrice();
                    int newPricePosition = popupWindowSearchLine.getPricePosition();
                    if (newPrice != null) {
                        price = newPrice;
                    }
                    if (newPricePosition != -1){
                        pricePosition = newPricePosition;
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
                    getHotActivityList();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO 进入热门活动详情页
        LogUtil.e(TAG, "i = " + i +", l = " + l);
        Bundle bundle = new Bundle();
        bundle.putString("id", listFreedom.get((int)l).getId());
        Intent intent = new Intent(getApplicationContext(), HotActivityDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_HOT_ACTIVITY_DETAIL);
    }

    private int VIEW_HOT_ACTIVITY_DETAIL = 3801; //查看热门活动详情

    private void getHotActivityList() {
        //获取活动列表
        ActivityActionBiz activityBiz = new ActivityActionBiz();
        //{"areaid":"20","order":"addtime desc","day":"5", "price":"2000,50000","zcfdate":"","page":"1","offset":"10" (price asc/price desc/2016-10-10)
        ActivityHot hot = new ActivityHot(areaId, sortCommit, day, price, earlyTime, String.valueOf(page), "10");
        activityBiz.activitiesHotGetInfo(hot, new BizGenericCallback<ArrayList<ActivityHotInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ActivityHotInfo>> model) {

                    ArrayList<ActivityHotInfo> array = model.body;
                    if (array == null || array.size() == 0){
                        if (loadMore){
                            page --;
                        }
                    } else {
                        //重新加载
                        if (refresh) {
//                            refresh = false;
                            listFreedom = array;
                            refreshView();
                        }
                        if (loadMore) {
//                            loadMore = false;
                            listFreedom.addAll(array);
                            adapter.notifyDataSetChanged();
//                            adapter.addData(array);
                        }
                    }
                    LogUtil.e(TAG,"activitiesHotGetInfo =" + array.toString());

                LoadingIndicator.cancel();
//                pullToRefreshListView.onRefreshComplete();
                complete();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "与服务器通信异常，请重试");
                }
                if (loadMore){
                    page --;
                }
                LogUtil.e(TAG, " activitiesHotGetInfo :" + error.toString());
                LoadingIndicator.cancel();
//                pullToRefreshListView.onRefreshComplete();
                complete();
            }
        });
    }

    private void complete(){
        pullToRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshListView.onRefreshComplete();
                if (refresh){
                    refresh = false;
                }
                if (loadMore){
                    loadMore = false;
                }
            }
        }, 1000);
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
