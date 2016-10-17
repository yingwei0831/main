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
import com.jhhy.cuiweitourism.adapter.HotelListAdapter;
import com.jhhy.cuiweitourism.biz.ScreenBiz;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.moudle.PriceArea;
import com.jhhy.cuiweitourism.net.biz.ActivityActionBiz;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ActivityHot;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListFetchRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HoutelPropertiesInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowSearchLine;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String TAG = HotelListActivity.class.getSimpleName();

    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private ArrayList<HotelListInfo> listHotel = new ArrayList<>();

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private HotelListAdapter adapter;

    private View layout;
    private TextView tvScreen; //筛选
    private TextView tvSelectPosition; //位置区域
    private TextView tvPriceLevel; //价格星级
    private TextView tvSortDefault; //默认排序

    private int tag = 0;

    private List<String> listDays = new ArrayList<>();
    private List<PriceArea> listPrices = new ArrayList<>();

    private int page = 1;
    private String fromCityId = "1";
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
        setContentView(R.layout.activity_hotel_list);
//        getData();
        getInternetData();
        getHotelListData();
        setupView();
        addListener();
    }

    private void getInternetData() {
        LoadingIndicator.show(HotelListActivity.this, getString(R.string.http_notice));

        HotelActionBiz hotelBiz = new HotelActionBiz();

        // 获取酒店属性列表
        hotelBiz.hotelGetPropertiesList(new BizGenericCallback<ArrayList<HoutelPropertiesInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<HoutelPropertiesInfo>> model) {
                ArrayList<HoutelPropertiesInfo> array = model.body;
                LogUtil.e(TAG,"hotelGetPropertiesList =" + array.toString());
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "hotelGetPropertiesList: " + error.toString());
            }
        });

    }

    private void getHotelListData(){
        HotelActionBiz hotelBiz = new HotelActionBiz();
        //"areaid":"8","starttime":"2016-09-16","endtime":"","keyword":"","page":"1","offset":"10","order":"price desc","price":"","level":""
        //获取酒店列表
        HotelListFetchRequest request = new HotelListFetchRequest("8","2016-09-16","","","1","offset","price desc","","");
        hotelBiz.hotelGetInfoList(request, new BizGenericCallback<ArrayList<HotelListInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<HotelListInfo>> model) {
                ArrayList<HotelListInfo> array = model.body;
                LogUtil.e(TAG,"houtelGetInfoList =" + array.toString());
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "houtelGetInfoList: " + error.toString());
            }
        });
    }

    private void setupView() {

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

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        tvScreen       = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_sort_default);
        tvSelectPosition   = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_trip_days);
        tvPriceLevel    = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_start_time);
        tvSortDefault        = (TextView) findViewById(R.id.tv_tab1_hot_activity_list_screen_price);

        adapter = new HotelListAdapter(getApplicationContext(), listHotel);
        pullToRefreshListView.setAdapter(adapter);
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
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_tab1_hot_activity_list_sort_default: //筛选

                break;
            case R.id.tv_tab1_hot_activity_list_trip_days: //位置区域

                break;
            case R.id.tv_tab1_hot_activity_list_start_time: //价格星级
                tag = 3;
                showPopupWindow();
                break;
            case R.id.tv_tab1_hot_activity_list_screen_price: //默认排序
                tag = 4;
                showPopupWindow();
                break;

        }
    }

    private void showPopupWindow() {
        if (popupWindowSearchLine == null) {
            popupWindowSearchLine = new PopupWindowSearchLine(HotelListActivity.this, layout, tag, listDays, listPrices);
            addPopListener();
        }else{
            popupWindowSearchLine.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            popupWindowSearchLine.refreshView(tag, sort, day, earlyTime, laterTime, pricePosition);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIEW_HOTEL_DETAIL){ //详情中可能订酒店
            if (resultCode == RESULT_OK){

            }
        }
    }


    private void refreshView(){
        adapter.setData(listHotel);
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
                            sortCommit = "";
                        }else if ("1".equals(newSort)){
                            sortCommit = "price asc";
                        }else if ("2".equals(newSort)){
                            sortCommit = "price desc"; //价格降序
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
                    getHotelListData();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO 进入酒店详情页
        LogUtil.e(TAG, "i = " + i +", l = " + l);
        Bundle bundle = new Bundle();
        bundle.putString("id", listHotel.get((int)l).getId());
        Intent intent = new Intent(getApplicationContext(), HotelDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_HOTEL_DETAIL);
    }

    private int VIEW_HOTEL_DETAIL = 3801; //查看酒店详情



    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, HotelListActivity.class);
        if(data !=null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
