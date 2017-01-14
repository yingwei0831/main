package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelListAdapter;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelListRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelProvinceResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahe008 on 2016/12/29.
 */
public class HotelSearchKeyActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "HotelSearchKeyActivity";
    private EditText    etSearchText;
    private ImageView   ivSearchAction;
    private PullToRefreshListView pullListView;
    private ListView listView;
    private HotelListAdapter adapter;
    private List<HotelListResponse.HotelBean> listHotel = new ArrayList<>();

    private int pageTemp = 1;
//    private int page = 1;
    private boolean loadMore;
    private boolean refresh = true;

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
            LogUtil.e(TAG, "handleMessage");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_search);
        super.onCreate(savedInstanceState);
        getData();
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
            }
        }
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText("关键字");

        etSearchText = (EditText) findViewById(R.id.et_search_text);
        ivSearchAction = (ImageView) findViewById(R.id.search_title_main_iv_right_search);
        pullListView = (PullToRefreshListView) findViewById(R.id.list_view_hotel_search);
        listView = pullListView.getRefreshableView();
        //这几个刷新Label的设置
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");

        //上拉、下拉设定
        pullListView.setMode(PullToRefreshBase.Mode.BOTH);

        //上拉、下拉监听函数
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });
        adapter = new HotelListAdapter(getApplicationContext(), listHotel);
        pullListView.setAdapter(adapter);
    }

    private void getHotelListData(){
        LoadingIndicator.show(HotelSearchKeyActivity.this, getString(R.string.http_notice));
        HotelActionBiz hotelBiz = new HotelActionBiz();
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
                    if (refresh){
                        listHotel = array;
                        adapter.setData(listHotel);
                    }
                    if (loadMore){
//                        page = pageTemp;
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
                pullListView.onRefreshComplete();
            }
        }, 1500);
    }

    private void loadMore() {
        pageTemp ++;
        loadMore = true;
        getHotelListData();
    }

    private void refresh() {
//        page = 1;
        pageTemp = 1;
        refresh = true;
        getHotelListData();
    }

    @Override
    protected void addListener() {
        super.addListener();
        listView.setOnItemClickListener(this);
        ivSearchAction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.search_title_main_iv_right_search:
                if (TextUtils.isEmpty(etSearchText.getText().toString())){
                    ToastCommon.toastShortShow(getApplicationContext(), null, getString(R.string.empty_input));
                    return;
                }
                refresh = true;
                keyWords = etSearchText.getText().toString();
                getHotelListData();
                break;
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

    private int VIEW_HOTEL_DETAIL = 3801; //查看酒店详情

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            if(requestCode == VIEW_HOTEL_DETAIL){ //详情中可能订酒店

            }
        }
    }
}
