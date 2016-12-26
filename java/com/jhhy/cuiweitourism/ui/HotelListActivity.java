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
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowHotelLevel;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowHotelSort;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String TAG = HotelListActivity.class.getSimpleName();

//    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private HotelListAdapter adapter;
//    private ArrayList<HotelListInfo> listHotel = new ArrayList<>();
    private List<HotelListResponse.HotelBean> listHotel = new ArrayList<>();

    private View layout;
    private TextView tvScreen; //筛选
    private TextView tvSelectPosition; //位置区域
    private TextView tvPriceLevel; //价格星级
    private TextView tvSortDefault; //默认排序


    private int pageTemp = 1;
    private int page = 1;
    private boolean loadMore;
    private boolean refresh = true;

//    private String areaId = "北京"; //城市名字，与主页一样
//    private String order = "price desc";
    private String price = ""; //房价范围 格式：0-150

    private String checkInDate ;
    private String checkOutDate;
    private int    stayDays    ;
    private HotelProvinceResponse.ProvinceBean selectCity;
    private String keyWords;
    private String district = "";       //行政区编码
    private String downTown = "";       //商业区编码
    private String landMark = "";       //景点/标志物
    private String starLevel = "";      //酒店星级; 经济/客栈: 0,1,2    三星/舒适: 3;   四星/高档: 4    五星/豪华: 5
    private String isEconomy = "0";     //是否经济；1是0否
    private String isApartment = "0";   //是否公寓；1是0否
    private String brandName = "";  	//酒店品牌编号
    private String facilities = "";     //设施ID 多个设施用逗号,隔开
    private String sortBy = "G";         //排序项: 价格P/星级S/好评G
    private String sortType = "D";       //排序顺序:升序A 降序 D


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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
                selectCity = bundle.getParcelable("selectCity");
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
        HotelListRequest request = new HotelListRequest(selectCity.getCode(), checkInDate, checkOutDate, keyWords, district, downTown, landMark, price, starLevel, isEconomy,
                isApartment, brandName, "", facilities, String.valueOf(pageTemp), "10", sortBy, sortType);
        hotelBiz.hotelList(request, new BizGenericCallback<HotelListResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelListResponse> model) {
                resetListView();
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    List<HotelListResponse.HotelBean> array = model.body.getHotels().getHotel();
                    LogUtil.e(TAG,"hotelGetInfoList =" + array.toString());
                    if (refresh){
                        listHotel = array;
                        adapter.setData(listHotel);
                    }
                    if (loadMore){
                        page = pageTemp;
                        listHotel.addAll(array);
                        adapter.addData(array);
                    }
                }
                resetValue();
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                resetListView();
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                } else {
                    LogUtil.e(TAG, "hotelGetInfoList: " + error.toString());
                }
                resetValue();
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

        tvScreen        .setOnClickListener(this);
        tvSelectPosition.setOnClickListener(this);
        tvPriceLevel    .setOnClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //TODO 进入酒店详情页
        LogUtil.e(TAG, "i = " + position +", l = " + l);
        Bundle bundle = new Bundle();
        bundle.putString("checkInDate", checkInDate);
        bundle.putString("checkOutDate", checkOutDate);
        bundle.putInt("stayDays", stayDays);
        bundle.putParcelable("selectCity", selectCity);

        bundle.putString("id", listHotel.get((int)l).getHotelID());
        Intent intent = new Intent(getApplicationContext(), HotelDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_HOTEL_DETAIL);
    }

    private void resetValue() {
        if (loadMore){
            loadMore = false;
        }
        if (refresh){
            refresh = false;
        }
    }

    private void resetListView() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshListView.onRefreshComplete();
            }
        }, 2000);
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
