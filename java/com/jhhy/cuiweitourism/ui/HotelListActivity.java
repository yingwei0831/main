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
import com.jhhy.cuiweitourism.adapter.HotelListAdapter;
import com.jhhy.cuiweitourism.model.PhoneBean;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListFetchRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowHotelLevel;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowHotelSort;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class HotelListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String TAG = HotelListActivity.class.getSimpleName();

//    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private HotelListAdapter adapter;
    private ArrayList<HotelListInfo> listHotel = new ArrayList<>();

    private View layout;
    private TextView tvScreen; //筛选
    private TextView tvSelectPosition; //位置区域
    private TextView tvPriceLevel; //价格星级
    private TextView tvSortDefault; //默认排序


    private int pageTemp = 1;
    private int page = 1;
    private boolean loadMore;
    private boolean refresh = true;

    private String areaId = "北京"; //城市名字，与主页一样
    private String order = "price desc";
    private String price = ""; //价格

    private String checkInDate ;
    private String checkOutDate;
    private int    stayDays    ;
    private PhoneBean selectCity;
    private String keyWords;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        getData();
        getHotelListData();
        setupView();
        addListener();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                checkInDate = bundle.getString("checkInDate");
                checkOutDate = bundle.getString("checkOutDate");
                stayDays    = bundle.getInt("stayDays");
                selectCity = (PhoneBean) bundle.getSerializable("selectCity");
                keyWords = bundle.getString("keyWords");
                if (keyWords == null){
                    keyWords = "";
                }
            }
        }
    }

    private void getHotelListData(){
        LoadingIndicator.show(HotelListActivity.this, getString(R.string.http_notice));
        HotelActionBiz hotelBiz = new HotelActionBiz();
        //"areaid":"8","starttime":"2016-09-16","endtime":"","keyword":"","page":"1","offset":"10","order":"price desc","price":"","level":""
        //获取酒店列表
        HotelListFetchRequest request = new HotelListFetchRequest(selectCity.getName(), checkInDate, checkOutDate, keyWords, String.valueOf(pageTemp), "10", String.valueOf(orderPosition), price, String.valueOf(levelPosition));
//        HotelListFetchRequest request = new HotelListFetchRequest(areaId, startTime, endTime, keyWords, String.valueOf(page), "10", order, price, "");
        hotelBiz.hotelGetInfoList(request, new BizGenericCallback<ArrayList<HotelListInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<HotelListInfo>> model) {
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    ArrayList<HotelListInfo> array = model.body;
                    LogUtil.e(TAG,"houtelGetInfoList =" + array.toString());
                    listHotel = array;
                    refreshView();
                    if (loadMore){
                        page = pageTemp;
                    }
                }
                if (loadMore){
                    loadMore = false;
                }
                if (refresh){
                    refresh = false;
                }
                pullToRefreshListView.onRefreshComplete();
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else {
                    LogUtil.e(TAG, "houtelGetInfoList: " + error.toString());
                }
                if (loadMore){
                    loadMore = false;
                }
                if (refresh){
                    refresh = false;
                }
                pullToRefreshListView.onRefreshComplete();
                LoadingIndicator.cancel();
            }
        });
    }

    private void setupView() {
//        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        layout = findViewById(R.id.activity_hot_activity_list);
        ivTitleLeft = (ImageView) findViewById(R.id.iv_title_search_left);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.activity_hot_activity_list_view);
        listView = pullToRefreshListView.getRefreshableView();
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
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });

        tvScreen       = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_sort_default);
        tvSelectPosition   = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_trip_days);
        tvPriceLevel    = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_start_time);
        tvSortDefault        = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_screen_price);

        adapter = new HotelListAdapter(getApplicationContext(), listHotel);
        pullToRefreshListView.setAdapter(adapter);
    }

    private void loadMore() {
        pageTemp ++;
        loadMore = true;
        getHotelListData();
    }

    private void refresh() {
        page = 1;
        refresh = true;
        getHotelListData();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);

        tvScreen   .setOnClickListener(this);
        tvSelectPosition   .setOnClickListener(this);
        tvPriceLevel   .setOnClickListener(this);
        tvSortDefault   .setOnClickListener(this);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_search_left:
                finish();
                break;
            case R.id.tv_tab1_hot_activity_list_sort_default: //筛选
                Intent intentScreen = new Intent( getApplicationContext(), HotelScreenActivity.class);
                startActivityForResult(intentScreen, SELECT_SCREEN);
                break;
            case R.id.tv_tab1_hot_activity_list_trip_days: //位置区域

                break;
            case R.id.tv_tab1_hot_activity_list_start_time: //价格星级

                showPopupWindowLevel();
                break;
            case R.id.tv_tab1_hot_activity_list_screen_price: //默认排序

                showPopupWindowSort();
                break;

        }
    }

    private PopupWindowHotelSort popUpSort;
    private int orderPosition = 0;

    private void showPopupWindowSort() {
        if (popUpSort == null){
            popUpSort = new PopupWindowHotelSort(HotelListActivity.this, layout);
            addSortListener();
        }else{
            popUpSort.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            popUpSort.refreshView(orderPosition);
        }
    }

    private void addSortListener() {
        popUpSort.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                int orderPositionNew = popUpSort.getSortPosition();
                if (orderPosition != orderPositionNew){
                    orderPosition = orderPositionNew;
                    //TODO 重新获取数据

                }
            }
        });
    }

    private PopupWindowHotelLevel popUpLevel;
    private int levelPosition; //星级选择
    private int priceMinPosition; //价格最低选择
    private int priceMaxPosition; //价格最高选择

    private void showPopupWindowLevel() {
        if (popUpLevel == null){
            popUpLevel = new PopupWindowHotelLevel(HotelListActivity.this, layout);
            addLevelListener();
        }else{
            popUpLevel.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            popUpLevel.refreshView(levelPosition, priceMinPosition, priceMaxPosition);
        }
    }

    private void addLevelListener() {
        popUpLevel.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                boolean commit = popUpLevel.getCommit();
                if (commit){
                    levelPosition = popUpLevel.getStarLevel();
                    priceMinPosition = popUpLevel.getPriceMin();
                    priceMaxPosition = popUpLevel.getPriceMax();
                    //TODO 重新请求数据

                }
            }
        });
    }

    private int VIEW_HOTEL_DETAIL = 3801; //查看酒店详情
    private int SELECT_SCREEN = 5696; //筛选

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIEW_HOTEL_DETAIL){ //详情中可能订酒店
            if (resultCode == RESULT_OK){

            }
        }else if(requestCode == SELECT_SCREEN){ //筛选
            if (resultCode == RESULT_OK){

            }
        }
    }

    private void refreshView(){
        adapter.setData(listHotel);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //TODO 进入酒店详情页
        LogUtil.e(TAG, "i = " + position +", l = " + l);
        Bundle bundle = new Bundle();
        bundle.putString("checkInDate", checkInDate);
        bundle.putString("checkOutDate", checkOutDate);
        bundle.putInt("stayDays", stayDays);
        bundle.putSerializable("selectCity", selectCity);

        bundle.putString("id", listHotel.get((int)l).getId());
        Intent intent = new Intent(getApplicationContext(), HotelDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_HOTEL_DETAIL);
    }

    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, HotelListActivity.class);
        if(data !=null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
