package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelListAdapter;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelScreenBrandRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelPositionLocationResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelScreenBrandResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelScreenFacilities;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowHotelLevel;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowHotelSort;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
    private String sortBy = "P";         //排序项: 价格P/星级S/好评G
    private String sortType = "A";       //排序顺序:升序A 降序 D

    private int brandNamePosition ;
    private int facilitiesPosition;

    private int businessDistrictPosition; //商业区
    private int districtPosition; //行政区
    private int viewSpot; //景点
    private String[] location;
    private LatLng latLng;

    private TextView etSearch;

    public static List<HotelScreenFacilities.FacilityItemBean> listFacilities = new ArrayList<>(); //酒店设施服务
    public static List<HotelScreenBrandResponse.BrandItemBean> listBrand = new ArrayList<>(); //酒店品牌

    public static List<HotelPositionLocationResponse.HotelDistrictItemBean> listBusinessDistrict = new ArrayList<>(); //商业区
    public static List<HotelPositionLocationResponse.HotelDistrictItemBean> listDistrict = new ArrayList<>(); //行政区
    public static List<HotelPositionLocationResponse.HotelDistrictItemBean> listViewSpot = new ArrayList<>(); //景点

    private boolean facilityTag;
    private boolean brandTag;
    private boolean BusinessDistrictTag;
    private boolean districtTag;
    private boolean viewSpotTag;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private HotelActionBiz hotelBiz;

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
        hotelBiz = new HotelActionBiz();
    }

    private void getHotelListData(){
        LoadingIndicator.show(HotelListActivity.this, getString(R.string.http_notice));

        HotelListRequest request = new HotelListRequest(selectCity.getCode(), checkInDate, checkOutDate, keyWords, district, downTown, landMark, price, starLevel, isEconomy,
                isApartment, brandName, "", facilities, String.valueOf(pageTemp), "10", sortBy, sortType);
        hotelBiz.getHotelList(request, new BizGenericCallback<HotelListResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelListResponse> model) {
                resetListView();
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    List<HotelListResponse.HotelBean> array = model.body.getHotels().getHotel();
                    LogUtil.e(TAG,"hotelGetInfoList =" + array.toString());
                    LogUtil.e(TAG, "refresh = " + ", loadMore = " + loadMore);
                    if (refresh){
                        if (listHotel != null){
                            listHotel.clear();
                        }
                        listHotel = array;
                        adapter.setData(listHotel);
                    }
                    if (loadMore){
                        page = pageTemp;
                        listHotel.addAll(array);
                        adapter.notifyDataSetChanged();
//                        adapter.addData(array);
                    }
                }
                resetValue();
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                resetListView();
                if (error.localReason != null && !"null".equals(error.localReason)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                } else {
//                    LogUtil.e(TAG, "hotelGetInfoList: " + error.toString());
                    ToastUtil.show(getApplicationContext(), "获取酒店列表失败，请重试");
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
        etSearch = (TextView) findViewById(R.id.edit_search);

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
        SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
        location = sp.getLocation();
        latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
    }

    private void loadMore() {
        pageTemp ++;
        loadMore = true;
        getHotelListData();
    }

    private void refresh() {
        page = 1;
        pageTemp = 1;
        refresh = true;
        getHotelListData();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        etSearch.setOnClickListener(this);

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
            case R.id.edit_search:
                search();
                break;
            case R.id.tv_tab1_hot_activity_list_sort_default: //筛选
                getScreenData();
                break;
            case R.id.tv_tab1_hot_activity_list_trip_days: //位置区域
                getLocationData();
                break;
            case R.id.tv_tab1_hot_activity_list_start_time: //价格星级
                showPopupWindowLevel();
                break;
            case R.id.tv_tab1_hot_activity_list_screen_price: //默认排序
                showPopupWindowSort();
                break;

        }
    }

    /**
     * 位置区域 商圈，行政区，景点
     */
    private void getLocationData() {
        if (BusinessDistrictTag && districtTag && viewSpotTag){
            showLocationData();
        }else {
            if (listBusinessDistrict.size() == 0) {
                getBusinessDistrict();
            }
            if (listDistrict.size() == 0) {
                getDistrict();
            }
            if (listViewSpot.size() == 0) {
                getViewSpot();
            }
        }
    }
    /**
     * 筛选 酒店品牌，服务设施
     */
    private void getScreenData() {
        if (brandTag && facilityTag){
            showScreenData();
        }else{
            if (listBrand.size() == 0) {
                getBrand();
            }
            if (listFacilities.size() == 0) {
                getFacilities();
            }
        }
    }

    private void showLocationData(){
        Intent intentLocation = new Intent( getApplicationContext(), HotelScreenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 7);
        bundle.putInt("businessDistrictPosition", businessDistrictPosition);
        bundle.putInt("districtPosition", districtPosition);
        bundle.putInt("viewSpot", viewSpot);
        intentLocation.putExtras(bundle);
        startActivityForResult(intentLocation, SELECT_LOCATION);
    }

    private void showScreenData(){
        Intent intentScreen = new Intent( getApplicationContext(), HotelScreenActivity.class);
        startActivityForResult(intentScreen, SELECT_SCREEN);
    }

    private void search() {
        Intent intentSearch = new Intent(getApplicationContext(), HotelSearchKeyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("checkInDate", checkInDate);
        bundle.putString("checkOutDate", checkOutDate);
        bundle.putInt("stayDays", stayDays);
        bundle.putParcelable("selectCity", selectCity);
        intentSearch.putExtras(bundle);
        startActivity(intentSearch);
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
                    //TODO 排序
                    if (orderPosition == 0){ //
                        sortByDefault();
                    }else if (orderPosition == 1){ //"距离 近—>远"
                        sortByDistance();
                    }else if (orderPosition == 2 || orderPosition == 3){ //"价格 低—>高" //"价格 高—>低"
                        sortByPrice();
                    }else if (orderPosition == 4 || orderPosition == 5){ //"星级 高—>低" //"星级 低—>高"
                        sortByStarLevel();
                    }
                    adapter.setData(listHotel);
                }
            }
        });
    }

    private void sortByDefault() {
        tvSortDefault.setText("默认排序");
    }

    private void sortByStarLevel() {
        Collections.sort(listHotel, new Comparator<HotelListResponse.HotelBean>() { //"星级 高—>低"
            @Override
            public int compare(HotelListResponse.HotelBean hotelBean1, HotelListResponse.HotelBean hotelBean2) {
                if (Integer.parseInt(hotelBean1.getStartLevel()) > Integer.parseInt(hotelBean2.getStartLevel())){
                    return -1;
                }else if (Integer.parseInt(hotelBean1.getStartLevel()) < Integer.parseInt(hotelBean2.getStartLevel())){
                    return 1;
                }
                return 0;
            }
        });
        if (orderPosition == 5){ //"星级 低—>高"
            Collections.reverse(listHotel);
            tvSortDefault.setText("星级由低到高");
        }else{
            tvSortDefault.setText("星级由高到低");
        }
    }

    private void sortByPrice() {
        Collections.sort(listHotel, new Comparator<HotelListResponse.HotelBean>() { //"价格 低—>高"
            @Override
            public int compare(HotelListResponse.HotelBean hotelBean1, HotelListResponse.HotelBean hotelBean2) {
                if (Float.parseFloat(hotelBean1.getMinPrice()) > Float.parseFloat(hotelBean2.getMinPrice())){
                    return 1;
                }else if (Float.parseFloat(hotelBean1.getMinPrice()) < Float.parseFloat(hotelBean2.getMinPrice())){
                    return -1;
                }
                return 0;
            }
        });
        if (orderPosition == 3){ //"价格 高—>低"
            Collections.reverse(listHotel);
            tvSortDefault.setText("价格由高到低");
        }else{
            tvSortDefault.setText("价格由低到高");
        }
    }

    private void sortByDistance() {
        Collections.sort(listHotel, new Comparator<HotelListResponse.HotelBean>() { //"距离 近—>远"
            @Override
            public int compare(HotelListResponse.HotelBean hotelBean1, HotelListResponse.HotelBean hotelBean2) {
                CoordinateConverter converter  = new CoordinateConverter();
                // CoordType.GPS 待转换坐标类型
                converter.from(CoordinateConverter.CoordType.BAIDU);

                LatLng sourceLatLng1 = new LatLng(Double.parseDouble(hotelBean1.getBaidulat()), Double.parseDouble(hotelBean1.getBaidulon())); //百度和高德转换坐标
                // sourceLatLng待转换坐标点 LatLng类型
                converter.coord(sourceLatLng1);
                // 执行转换操作
                LatLng desLatLng1 = converter.convert();

                float distance1 = AMapUtils.calculateLineDistance(latLng, desLatLng1) / 1000; //计算直线距离

                LatLng sourceLatLng2 = new LatLng(Double.parseDouble(hotelBean2.getBaidulat()), Double.parseDouble(hotelBean2.getBaidulon())); //百度和高德转换坐标
                // sourceLatLng待转换坐标点 LatLng类型
                converter.coord(sourceLatLng2);
                // 执行转换操作
                LatLng desLatLng2 = converter.convert();

                float distance2 = AMapUtils.calculateLineDistance(latLng, desLatLng2) / 1000; //计算直线距离

                if (distance1 > distance2){
                    return 1;
                }else if (distance1 < distance2){
                    return -1;
                }
                return 0;
            }
        });
        tvSortDefault.setText("由近到远");
    }

    private PopupWindowHotelLevel popUpLevel;
    private int levelPosition = 1; //星级选择
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
                    if (levelPosition == 1){
                        starLevel = "";
                    }else if (levelPosition == 2){
                        starLevel = "0,1,2";
                    }else{
                        starLevel = String.valueOf(levelPosition);
                    }
                    priceMinPosition = popUpLevel.getPriceMin();
                    priceMaxPosition = popUpLevel.getPriceMax();
                    if (priceMinPosition == 0 && priceMaxPosition == 5){
                        price = "";
                    }else{
                        price = String.format(Locale.getDefault(), "%s-%s", getMoney(priceMinPosition), getMoney(priceMaxPosition));
                    }
                    LogUtil.e(TAG, "starLevel = " + starLevel +", price= " + price);
                    //TODO 重新请求数据
                    refresh = true;
                    getHotelListData();
                }
            }
        });
    }

    private int VIEW_HOTEL_DETAIL = 3801; //查看酒店详情
    private int SELECT_SCREEN = 5696; //筛选
    private int SELECT_LOCATION = 5697; //位置区域

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIEW_HOTEL_DETAIL){ //详情中可能订酒店
            if (resultCode == RESULT_OK){

            }
        }else if(requestCode == SELECT_SCREEN){ //筛选
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                brandName = bundle.getString("brandName");
                facilities = bundle.getString("facilityName");
                refresh = true;
                getHotelListData();
            }
        }else if(requestCode == SELECT_LOCATION){ //位置区域
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                businessDistrictPosition = bundle.getInt("businessDistrictPosition");
                districtPosition = bundle.getInt("districtPosition");
                viewSpot = bundle.getInt("viewSpot");
                if (0 != businessDistrictPosition){
                    downTown = listBrand.get(businessDistrictPosition).getID();
                }else{
                    downTown = "";
                }
                if (0 != districtPosition){
                    district = listDistrict.get(districtPosition).getID();
                }else {
                    district = "";
                }
                if (0 != viewSpot){
                    landMark = listViewSpot.get(viewSpot).getID();
                }else {
                    landMark = "";
                }
                refresh = true;
                getHotelListData();
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
        bundle.putString("imageUrl", listHotel.get((int)l).getImgUrl());

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
        }, 1500);
    }

    private String getMoney(int position){
        if(position == 0){
            return "0";
        }else if (position == 1){
            return "150";
        }else if (position == 2){
            return "300";
        }else if (position == 3){
            return "500";
        }else if (position == 4){
            return "700";
        }else {
            return "1000000";
        }
    }


    /**
     * 获取酒店设施
     */
    private void getFacilities() {
        hotelBiz.hotelScreenFacilities(new BizGenericCallback<HotelScreenFacilities>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelScreenFacilities> model) {
                listFacilities.add(new HotelScreenFacilities.FacilityItemBean("不限"));
                listFacilities.addAll(model.body.getItem());
                LogUtil.e(TAG, "getFacilities " + model.body);
                facilityTag = true;
                if (brandTag){
                    showScreenData();
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
//                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
//                    ToastUtil.show(getApplicationContext(), "请求酒店设施服务信息失败，请返回重试");
                }
                LogUtil.e(TAG, "getFacilities " + error);
                facilityTag = true;
                if (brandTag){
                    showScreenData();
                }
            }
        });
    }

    /**
     * 获取品牌
     */
    private void getBrand(){
        HotelScreenBrandRequest request = new HotelScreenBrandRequest(selectCity.getCode());
        hotelBiz.hotelScreenBrand(request, new BizGenericCallback<HotelScreenBrandResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelScreenBrandResponse> model) {
                listBrand.add(new HotelScreenBrandResponse.BrandItemBean("不限"));
                listBrand.addAll(model.body.getItem());
                LogUtil.e(TAG, "hotelScreenBrand " + model.body);
                brandTag = true;
                if (facilityTag){
                    showScreenData();
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
//                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
//                    ToastUtil.show(getApplicationContext(), "请求酒店品牌信息失败，请重试");
                }
                LogUtil.e(TAG, "hotelScreenBrand " + error);
                brandTag = true;
                if (facilityTag){
                    showScreenData();
                }
            }
        });
    }

    /**
     * 获取商业区
     */
    private void getBusinessDistrict(){ //商业区
        HotelScreenBrandRequest request = new HotelScreenBrandRequest(selectCity.getCode());
        hotelBiz.getHotelLocationBusinessDistrict(request, new BizGenericCallback<HotelPositionLocationResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelPositionLocationResponse> model) {
                listBusinessDistrict.add(new HotelPositionLocationResponse.HotelDistrictItemBean("不限"));
                listBusinessDistrict.addAll(model.body.getItem());
                LogUtil.e(TAG, "getHotelLocationBusinessDistrict " + model.body);
                BusinessDistrictTag = true;
                if (districtTag && viewSpotTag){
                    showLocationData();
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "请求商业区信息失败，请重试");
                }
                LogUtil.e(TAG, "getHotelLocationBusinessDistrict " + error);
                BusinessDistrictTag = true;
                if (districtTag && viewSpotTag){
                    showLocationData();
                }
            }
        });
    }

    /**
     * 获取行政区
     */
    private void getDistrict(){ //行政区
        HotelScreenBrandRequest request = new HotelScreenBrandRequest(selectCity.getCode());
        hotelBiz.getHotelLocationDistrict(request, new BizGenericCallback<HotelPositionLocationResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelPositionLocationResponse> model) {
                listDistrict.add(new HotelPositionLocationResponse.HotelDistrictItemBean("不限"));
                listDistrict.addAll(model.body.getItem());
                LogUtil.e(TAG, "getHotelLocationDistrict " + model.body);
                districtTag = true;
                if (BusinessDistrictTag && viewSpotTag){
                    showLocationData();
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "请求行政区信息失败，请重试");
                }
                LogUtil.e(TAG, "getHotelLocationDistrict " + error);
                districtTag = true;
                if (BusinessDistrictTag && viewSpotTag){
                    showLocationData();
                }
            }
        });
    }

    /**
     * 获取景点
     */
    private void getViewSpot(){ //景点
        HotelScreenBrandRequest request = new HotelScreenBrandRequest(selectCity.getCode());
        hotelBiz.getHotelLocationViewSpot(request, new BizGenericCallback<HotelPositionLocationResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelPositionLocationResponse> model) {
                listViewSpot.add(new HotelPositionLocationResponse.HotelDistrictItemBean("不限"));
                listViewSpot.addAll(model.body.getItem());
                LogUtil.e(TAG, "getHotelLocationViewSpot " + model.body);
                viewSpotTag = true;
                if (BusinessDistrictTag && districtTag){
                    showLocationData();
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastUtil.show(getApplicationContext(), error.localReason);
                }else{
                    ToastUtil.show(getApplicationContext(), "请求景点信息失败，请重试");
                }
                LogUtil.e(TAG, "getHotelLocationViewSpot " + error);
                if (BusinessDistrictTag && districtTag){
                    showLocationData();
                }
            }
        });
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
