package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelDetailInnerListAdapter;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelDetailRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;


public class HotelDetailActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {

    private String TAG = HotelDetailActivity.class.getSimpleName();
    private String id;
    private HotelActionBiz hotelBiz;
    private HotelDetailInfo hotelDetail = new HotelDetailInfo();

    private PullToRefreshListView hotelListView;
    private ListView listView;
    private HotelDetailInnerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hotel_detail);
        super.onCreate(savedInstanceState);
        getData();
        getInternetData();
        setupView();
        addListener();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                id = bundle.getString("id");
            }
        }
    }

    private void getInternetData() {
        LoadingIndicator.show(HotelDetailActivity.this, getString(R.string.http_notice));
        HotelDetailRequest request1 = new HotelDetailRequest(id);
        //获取酒店详情
        hotelBiz = new HotelActionBiz();
        hotelBiz.hotelGetDetailInfo(request1, new BizGenericCallback<HotelDetailInfo>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelDetailInfo> model) {
                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)){
                    hotelDetail = model.body;
                    refreshView();
                    LogUtil.e(TAG,"hotelGetDetailInfo =" + hotelDetail.toString());
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                LogUtil.e(TAG, "hotelGetDetailInfo: " + error.toString());
            }
        });
    }

    private void refreshView() {
        adapter.setData(hotelDetail.getRoom());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(getString(R.string.hotel_detail_title));
//        ivTitleRight.setImageResource(R.mipmap.icon_telephone);
//        ivTitleRight.setVisibility(View.VISIBLE);

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

        adapter = new HotelDetailInnerListAdapter(getApplicationContext(), hotelDetail.getRoom()) {
            @Override
            public void goToArgument(View view, View viewGroup, int position, int which) {
                HotelDetailInfo.Room room = hotelDetail.getRoom().get(position);
                //TODO 生成订单

            }
        };
        hotelListView.setAdapter(adapter);
    }

    @Override
    protected void addListener() {
        super.addListener();
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
//            case R.id.title_main_iv_right_telephone:
//                Utils.contact(getApplicationContext(), hotelDetail.ge);
//                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
