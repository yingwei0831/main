package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelDetailRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HotelDetailActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {

    private String TAG = HotelDetailActivity.class.getSimpleName();
    private String hotelID; //
    private int type;
    private HotelActionBiz hotelBiz;
//    private HotelDetailInfo hotelDetail = new HotelDetailInfo();
    public static HotelDetailResponse hotelDetail = new HotelDetailResponse();

    private PullToRefreshListView hotelListView;
    private ListView listView;
    private HotelDetailInnerListAdapter adapter;
    private List<HotelDetailResponse.HotelRoomBean> listRooms;
    private List<HotelDetailResponse.HotelProductBean> listProducts = new ArrayList<>();

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
        LogUtil.e(TAG, "-----setupView------");
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
                //TODO 生成订单
                Intent intent = new Intent(getApplicationContext(), HotelEditOrderActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putSerializable("hotelDetail", hotelDetail);
                bundle.putString("checkInDate", checkInDate);
                bundle.putString("checkOutDate", checkOutDate);
                bundle.putInt("stayDays", stayDays);
                bundle.putInt("position", position); //房间列表中第几个
                bundle.putParcelable("selectCity", selectCity);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_HOTEL_ORDER);
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
            case R.id.iv_collection_hotel:
                LoadingIndicator.show(HotelDetailActivity.this, getString(R.string.http_notice));
                UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
                biz.doCollection(MainActivity.user.getUserId(), "2", hotelDetail.getHotel().getHotelID());
                break;
            case R.id.layout_hotel_address:
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
                //TODO 进入酒店信息页
                Intent intentInfo = new Intent(getApplicationContext(), HotelInfoActivity.class);
                Bundle bundleInfo = new Bundle();
//                bundleInfo.putSerializable("info", hotelDetail);
                intentInfo.putExtras(bundleInfo);
                startActivity(intentInfo);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private int EDIT_HOTEL_ORDER = 6522; //编辑酒店订单

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_HOTEL_ORDER){
            if (resultCode == RESULT_OK){

            }
        }
    }
}
