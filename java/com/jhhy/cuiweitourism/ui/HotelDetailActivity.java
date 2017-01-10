package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelDetailInnerListAdapter;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.model.PhoneBean;
import com.jhhy.cuiweitourism.model.User;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelDetailRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelPriceCheckRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelPriceCheckResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowHotelRoomScreen;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HotelDetailActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {

    private String TAG = HotelDetailActivity.class.getSimpleName();
    private String hotelID; //
    private int type;
    private HotelActionBiz hotelBiz;
//    private HotelDetailInfo hotelDetail = new HotelDetailInfo();
    public static HotelDetailResponse hotelDetail;

    private PullToRefreshListView hotelListView;
    private ListView listView;
    private HotelDetailInnerListAdapter adapter;
    private List<HotelDetailResponse.HotelRoomBean> listRooms;
    private List<HotelDetailResponse.HotelProductBean> listProducts = new ArrayList<>();
    private List<HotelDetailResponse.HotelProductBean> listProductsCopy = new ArrayList<>();
    private int mPosition; //列表中第几个Room的item
    public static HotelDetailResponse.HotelProductBean hotelProduct = null;
    private HotelDetailResponse.HotelProductBean roomItem; //选中的某个Room的某个Product
    private HotelPriceCheckResponse priceCheck; //数据校验返回结果


    private ImageView ivCollection; //收藏
    private ImageView ivMainImgs; //详情页展示的一张大图，点击进入查看全部图片页面

    private TextView tvHotelName; //酒店名称
    private TextView tvHotelLevel; //酒店级别
    private TextView tvHotelImgs; //酒店图片数量

    private RelativeLayout layoutAddress;
    private RelativeLayout layoutOpening;
    private TextView tvHotelAddress; //酒店地址
    private TextView tvOpening; //开业详情

    private TextView tvCheckInDate; //入住日期
    private TextView tvCheckInDays; //住宿时间
    private TextView tvScreenRoom; //筛选房型

    private String checkInDate ;
    private String checkOutDate;
    private int    stayDays    ;
    private HotelProvinceResponse.ProvinceBean selectCity;
    private String imageUrl;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.NET_ERROR:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "网络无连接，请检查网络");
                    break;
                case Consts.MESSAGE_DO_COLLECTION:
                    if (msg.arg1 == 1){
                        //TODO 五角星是否应该改变颜色？是否应该做本地数据库的记录
                    }
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    break;
            }
            LoadingIndicator.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_detail);
        getData();
        super.onCreate(savedInstanceState);
        getInternetData();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                hotelID = bundle.getString("id");
                type = bundle.getInt("type"); //2:从收藏进来的酒店详情

                checkInDate = bundle.getString("checkInDate");
                checkOutDate = bundle.getString("checkOutDate");
                stayDays    = bundle.getInt("stayDays");
                selectCity = bundle.getParcelable("selectCity");
                imageUrl = bundle.getString("imageUrl");
                LogUtil.e(TAG, "checkInDate = " + checkInDate +", checkOutDate = " + checkOutDate +", stayDays = " + stayDays +", selectCity = " + selectCity +", id = " + hotelID);
            }
        }
    }

    private void getInternetData() {
        LoadingIndicator.show(HotelDetailActivity.this, getString(R.string.http_notice));
        HotelDetailRequest request1 = new HotelDetailRequest(hotelID, checkInDate, checkOutDate);
//        //获取酒店详情
        hotelBiz = new HotelActionBiz();
        hotelBiz.getHotelDetailInfo(request1, new BizGenericCallback<HotelDetailResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelDetailResponse> model) {
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    hotelDetail = model.body;
                    listRooms = hotelDetail.getHotel().getRooms().getRoom();
                    refreshView();
                    LogUtil.e(TAG,"hotelGetDetailInfo =" + hotelDetail.toString());
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求酒店详细信息出错，请返回重试");
                }
                LogUtil.e(TAG, "hotelGetDetailInfo: " + error.toString());
                LoadingIndicator.cancel();
            }
        });

    }

    private void refreshView() {
        for (HotelDetailResponse.HotelRoomBean roomBean: listRooms){
            List<HotelDetailResponse.HotelProductBean> products = roomBean.getProducts().getProduct();
            for (HotelDetailResponse.HotelProductBean productBean: products){
                productBean.setRoomName(roomBean.getRoomName());
                productBean.setRoomImgUrl(imageUrl);
                productBean.setRoomId(roomBean.getRoomID());
                productBean.setBedType(roomBean.getBedType());
                listProducts.add(productBean);
            }
        }
        listProductsCopy.addAll(listProducts);
        adapter.setData(listProducts);
        adapter.notifyDataSetChanged();

        tvHotelName.setText(hotelDetail.getHotel().getName());
        tvHotelImgs.setText(hotelDetail.getHotel().getImages().getImage().size()+"张图片");
        tvHotelAddress.setText(hotelDetail.getHotel().getTraffic());
        tvOpening.setText(hotelDetail.getHotel().getSummary());
        ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(imageUrl, ivMainImgs);
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.hotel_detail_title));
        ivTitleRight.setImageResource(R.mipmap.icon_telephone_hollow);
        ivTitleRight.setVisibility(View.VISIBLE);

        hotelListView = (PullToRefreshListView) findViewById(R.id.listview_hotel_detail);
        //这几个刷新Label的设置
        hotelListView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        hotelListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        hotelListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        hotelListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        hotelListView.setMode(PullToRefreshBase.Mode.DISABLED);
        listView = hotelListView.getRefreshableView();

        //上拉、下拉监听函数
        hotelListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        adapter = new HotelDetailInnerListAdapter(getApplicationContext(), listProducts) {
            @Override
            public void goToArgument(View view, View viewGroup, int position, int which) {
//                HotelDetailResponse.HotelRoomBean room = hotelDetail.getHotel().getRooms().getRoom().get(position);
                //先弹出选择房间数量，然后验价，再进入订单
                showSelectNumber();
                mPosition = position;
            }
        };
        hotelListView.setAdapter(adapter);
        View headerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.header_hotel_detail, null);

        ivCollection = (ImageView) headerView.findViewById(R.id.iv_collection_hotel);
        ivMainImgs = (ImageView) headerView.findViewById(R.id.iv_hotel_detail_imgs);

        tvHotelName = (TextView) headerView.findViewById(R.id.tv_hotel_name);
        tvHotelLevel = (TextView) headerView.findViewById(R.id.tv_hotel_level);
        tvHotelImgs = (TextView) headerView.findViewById(R.id.tv_hotel_imgs);

        tvHotelAddress = (TextView) headerView.findViewById(R.id.tv_hotel_detail_address);
        tvOpening      = (TextView) headerView.findViewById(R.id.tv_hotel_detail_opening);
        layoutAddress = (RelativeLayout) headerView.findViewById(R.id.layout_hotel_address);
        layoutOpening = (RelativeLayout) headerView.findViewById(R.id.layout_hotel_opening);

        tvCheckInDate = (TextView) headerView.findViewById(R.id.tv_check_in_date);
        tvCheckInDays = (TextView) headerView.findViewById(R.id.tv_check_in_days);
        tvScreenRoom  = (TextView) headerView.findViewById(R.id.tv_screen_room_type);
        listView.addHeaderView(headerView);

        tvCheckInDate.setText(String.format("%s月%s日", checkInDate.substring(checkInDate.indexOf("-") + 1, checkInDate.lastIndexOf("-")), checkInDate.substring(checkInDate.lastIndexOf("-"))));
        tvCheckInDays.setText(String.format("入住%d晚", stayDays));
    }

    @Override
    protected void addListener() {
        super.addListener();
        listView.setOnItemClickListener(this);

        ivCollection.setOnClickListener(this);
        layoutAddress.setOnClickListener(this);
        layoutOpening.setOnClickListener(this);
        tvScreenRoom.setOnClickListener(this);
        tvHotelImgs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.title_main_iv_right_telephone:
                if (hotelDetail.getHotel().getPhone() != null || hotelDetail.getHotel().getPhone().length() != 0) {
                    Utils.contact(getApplicationContext(), hotelDetail.getHotel().getPhone());
                }else{
                    ToastUtil.show(getApplicationContext(), "商家未提供联系方式");
                }
                break;
            case R.id.tv_hotel_imgs: //TODO 酒店图片

                break;
//            case R.id.iv_collection_hotel: //收藏
//                LoadingIndicator.show(HotelDetailActivity.this, getString(R.string.http_notice));
//                if (MainActivity.logged) {
//                    UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
//                    biz.doCollection(MainActivity.user.getUserId(), "2", hotelDetail.getHotel().getHotelID());
//                }else{
//    //            ToastUtil.show(getApplicationContext(), "请登录后再试");
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type", 2);
//                    intent.putExtras(bundle);
//                    startActivityForResult(intent, REQUEST_LOGIN);
//                }
//                break;
            case R.id.layout_hotel_address:
                if (hotelDetail == null){
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ReGeocoderActivity.class);
                Bundle bundle = new Bundle();
                //TODO 将百度地图坐标转换为高德地图坐标
                LatLng latLngFrom = new LatLng(Double.parseDouble(hotelDetail.getHotel().getBaidulat()), Double.parseDouble(hotelDetail.getHotel().getBaidulon())); //百度和高德转换坐标
                CoordinateConverter converter  = new CoordinateConverter();
                // CoordType.GPS 待转换坐标类型
                converter.from(CoordinateConverter.CoordType.BAIDU);
                // sourceLatLng待转换坐标点 LatLng类型
                converter.coord(latLngFrom);
                // 执行转换操作
                LatLng desLatLng = converter.convert();

                bundle.putString("latitude", String.valueOf(desLatLng.latitude));
                bundle.putString("longitude", String.valueOf(desLatLng.longitude));
                bundle.putString("address", hotelDetail.getHotel().getTraffic());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.layout_hotel_opening:
                //TODO 进入酒店详情页
                if (hotelDetail == null){
                    return;
                }
                Intent intentInfo = new Intent(getApplicationContext(), HotelInfoActivity.class);
                Bundle bundleInfo = new Bundle();
//                bundleInfo.putSerializable("info", hotelDetail);
                intentInfo.putExtras(bundleInfo);
                startActivity(intentInfo);
                break;
            case R.id.tv_screen_room_type: //TODO 筛选
                showPopupRoomScreen();
                break;
            case R.id.btn_hot_activity_reserve: //TODO 下一步，填写订单  先验价,验价成功后，进入填写订单页
                getHotelPrice();
                break;
            case R.id.tv_price_calendar_number_reduce: //房间数减少
                if (number <= 1){
                    return;
                }
                number --;
                popShow();
                break;
            case R.id.tv_price_calendar_number_add: //房间数增加
                if (number >= 9){
                    ToastCommon.toastShortShow(getApplicationContext(), null, "最多添加9间房");
                    return;
                }
                number ++;
                popShow();
                break;
        }
    }

    private void getHotelPrice() {
        roomItem = listProducts.get(mPosition);
        if ("1".equals(roomItem.getIsBooking())){
            LoadingIndicator.show(HotelDetailActivity.this, getString(R.string.http_notice));
            String earlierCheckInDate = ""; //最早到店时间，最晚到店时间
            if (Utils.getCurrentTimeYMD().equals(checkInDate)){
                Calendar c = Calendar.getInstance();
                if (c.get(Calendar.HOUR_OF_DAY) <23 ) {
                    earlierCheckInDate = String.format(Locale.getDefault(), "%s %d%s", checkInDate, c.get(Calendar.HOUR_OF_DAY) + 1, ":00:00");
                }else{
                    earlierCheckInDate = String.format(Locale.getDefault(), "%s %s", Utils.getTimeStrYMD(System.currentTimeMillis() + 24 * 60 * 60 * 1000), "00:30:00"); //第二天凌晨入住
                }
            }else{
                earlierCheckInDate = String.format(Locale.getDefault(), "%s %s", checkInDate, "08:00:00");
            }
            HotelPriceCheckRequest request = new HotelPriceCheckRequest(checkInDate, checkOutDate,
                    earlierCheckInDate,  String.format(Locale.getDefault(), "%s %s", checkInDate, "23:59:00"), //最早入店时间比当前晚5分钟，最晚入店时间为今天23:59:59，明天凌晨
                    hotelDetail.getHotel().getHotelID(), roomItem.getRoomTypeID(), roomItem.getProductID(), roomItem.getPrice(), String.valueOf(number));
            hotelBiz.getHotelPriceCheck(request, new BizGenericCallback<HotelPriceCheckResponse>() {
                @Override
                public void onCompletion(GenericResponseModel<HotelPriceCheckResponse> model) {
                    LogUtil.e(TAG, "getHotelPriceCheck: "+model.body);
                    LoadingIndicator.cancel();
                    if ("0001".equals(model.headModel.res_code)){
                        ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                    }else if ("0000".equals(model.headModel.res_code)){
                        priceCheck = model.body;
                        popupWindow.dismiss(); //选择房间消失
                        setHotelOrder(mPosition);
                    }
                }

                @Override
                public void onError(FetchError error) {
                    if (error.localReason != null){
                        ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, "请求酒店数据校验出错，请重试");
                    }
                    LogUtil.e(TAG, "getHotelPriceCheck: "+error);
                    LoadingIndicator.cancel();
                }
            });
        }else{
            ToastCommon.toastShortShow(getApplicationContext(), null, "当前房间不可预订");
        }
    }

    private void setHotelOrder(int position) {
        Intent intent = new Intent(getApplicationContext(), HotelEditOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("checkInDate", checkInDate);
        bundle.putString("checkOutDate", checkOutDate);
        bundle.putInt("stayDays", stayDays);
        bundle.putInt("number", number);
//        bundle.putInt("position", position); //房间列表中第几个
        hotelProduct = listProducts.get(position);
        bundle.putParcelable("selectCity", selectCity);
        listProducts.get(position);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_HOTEL_ORDER);
    }

    private PopupWindowHotelRoomScreen popHotelRoomScreen;
    private int positionMeal = 1;
    private int positionBedType = 1;

//    private String screenMeal = "";
//    private String screenBedType = "";

    private void showPopupRoomScreen() {
        if (popHotelRoomScreen == null){
            popHotelRoomScreen = new PopupWindowHotelRoomScreen(getApplicationContext());
            addPopRoomListener();
        }
        if (popHotelRoomScreen.isShowing()){
            popHotelRoomScreen.dismiss();
        }else{
            popHotelRoomScreen.showAtLocation(hotelListView, Gravity.BOTTOM, 0, 0);
            popHotelRoomScreen.refreshView(positionMeal, positionBedType);
        }
    }

    private void addPopRoomListener() {
        popHotelRoomScreen.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                int positionMealNew = popHotelRoomScreen.getPositionMeal();
                int positionBedTypeNew = popHotelRoomScreen.getPositionBedType();
                if (positionMeal != positionMealNew || positionBedType != positionBedTypeNew){ //有改变
                    positionMeal = positionMealNew;
                    positionBedType = positionBedTypeNew;
                    listProducts.clear();
                    List<HotelDetailResponse.HotelProductBean> listProductsTemp = new ArrayList<HotelDetailResponse.HotelProductBean>();
                    //TODO 对当前数据进行房型筛选
                    //早餐
                    for (HotelDetailResponse.HotelProductBean product : listProductsCopy){
                        if (positionMeal == 1){ //不限
                            listProducts.add(product);
                        } else { //含单早
                            if (positionMeal  == 2 || 3 == positionMeal){ //含早餐 //单份早餐
                                if (product.getMeals().contains("无早") || product.getMeals().contains("不含早") || product.getName().contains("无早") || product.getName().contains("不含早")){

                                }else{
                                    listProducts.add(product);
                                }
                            }else if (4 == positionMeal) { //双份早餐
                                if (product.getMeals().contains("含双早") || product.getMeals().contains("含三早") || product.getName().contains("含双早") || product.getName().contains("含三早")){
                                    listProducts.add(product);
                                }
                            }
                        }
                    }
                    //床型
                    for (HotelDetailResponse.HotelProductBean product : listProducts){
                        if (1 == positionBedType){
                            listProductsTemp.add(product);
                        }else{
                            //不限，大床，双床，三张床
                            if (2 == positionBedType && product.getBedType().contains("大床")){
                                listProductsTemp.add(product);
                            }else if (3 == positionBedType && product.getBedType().contains("双床")){
                                listProductsTemp.add(product);
                            }
                        }
                    }
                    listProducts.clear();
                    listProducts.addAll(listProductsTemp);
                    listProductsTemp.clear();
                    listProductsTemp = null;
                    adapter.setData(listProducts);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    private int EDIT_HOTEL_ORDER = 6522; //编辑酒店订单
//    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == EDIT_HOTEL_ORDER){

            }
//            else if (requestCode == REQUEST_LOGIN) { //登录成功
//                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
//                if (user != null) {
//                    MainActivity.logged = true;
//                    MainActivity.user = user;
//                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
//                    sp.saveUserId(user.getUserId());
//                }
//            }
//        }else{
//            if (requestCode == REQUEST_LOGIN) { //登录
//                ToastUtil.show(getApplicationContext(), "登录失败");
//            }
        }
    }

    private PopupWindow popupWindow; //选择房间数
    private TextView tvNumber; //显示房间数
    private int number = 1; //房间数

    private void showSelectNumber() {
        if (popupWindow == null) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_hot_select_number, null);
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            // 允许点击外部消失
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); //防止虚拟软键盘被弹出菜单遮住
            setupPopView(view);
        }
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
        }else{
            popupWindow.showAtLocation(tvScreenRoom, Gravity.NO_GRAVITY,0, 0);
        }
        popShow();
    }

    private void popShow() {
        tvNumber.setText(String.valueOf(number));
    }

    private void setupPopView(View view) {
        ImageView ivReduce = (ImageView) view.findViewById(R.id.tv_price_calendar_number_reduce);
        ImageView ivAdd = (ImageView) view.findViewById(R.id.tv_price_calendar_number_add);
        tvNumber = (TextView) view.findViewById(R.id.tv_price_calendar_number);
        TextView tvTitlePop = (TextView) view.findViewById(R.id.tv_adult_notice);
        tvTitlePop.setText("选择房间数");
        Button btnReserveOrder = (Button) view.findViewById(R.id.btn_hot_activity_reserve);
        ivReduce.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        btnReserveOrder.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAG = null;
        hotelID = null;
        hotelBiz = null;
        hotelDetail = null;
        hotelListView = null;
        listView = null;
        adapter = null;
        if (listRooms != null){
            listRooms.clear();
            listRooms = null;
        }
        if (listProducts != null){
            listProducts.clear();
            listProducts = null;
        }
        if (listProductsCopy != null){
            listProductsCopy.clear();
            listProductsCopy = null;
        }
        hotelProduct = null;
        roomItem = null;
        priceCheck = null;
        ivCollection = null;
        ivMainImgs = null;
        tvHotelName = null;
        tvHotelLevel = null;
        tvHotelImgs = null;
        layoutAddress = null;
        layoutOpening = null;
        tvHotelAddress = null;
        tvOpening = null;
        tvCheckInDate = null;
        tvCheckInDays = null;
        tvScreenRoom = null;
        selectCity = null;
    }

}
