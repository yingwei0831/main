package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelDetailInnerListAdapter;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelDetailRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelDetailInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelListInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
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
    private PhoneBean selectCity;

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
                id = bundle.getString("id");

                checkInDate = bundle.getString("checkInDate");
                checkOutDate = bundle.getString("checkOutDate");
                stayDays    = bundle.getInt("stayDays");
                selectCity = (PhoneBean) bundle.getSerializable("selectCity");
                LogUtil.e(TAG, "checkInDate = " + checkInDate +", checkOutDate = " + checkOutDate +", stayDays = " + stayDays +", selectCity = " + selectCity +", id = " + id);
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
        adapter.setData(hotelDetail.getRoom());
        adapter.notifyDataSetChanged();

        tvHotelName.setText(hotelDetail.getTitle());
        tvHotelImgs.setText(hotelDetail.getPiclist().size()+"张图片");
        tvHotelAddress.setText(hotelDetail.getAddress());
        tvOpening.setText(hotelDetail.getOpentime()+"开业 "+hotelDetail.getDecoratetime()+"装修");
        ImageLoaderUtil.getInstance(getApplicationContext()).displayImage(hotelDetail.getPiclist().get(0), ivMainImgs);
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

        adapter = new HotelDetailInnerListAdapter(getApplicationContext(), hotelDetail.getRoom()) {
            @Override
            public void goToArgument(View view, View viewGroup, int position, int which) {
                HotelDetailInfo.Room room = hotelDetail.getRoom().get(position);
                //TODO 生成订单
                Intent intent = new Intent(getApplicationContext(), HotelEditOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hotelDetail", hotelDetail);
                bundle.putString("checkInDate", checkInDate);
                bundle.putString("checkOutDate", checkOutDate);
                bundle.putInt("stayDays", stayDays);
                bundle.putInt("position", position);
                bundle.putSerializable("selectCity", selectCity);
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
                Utils.contact(getApplicationContext(), "010-23456789");
                break;
            case R.id.iv_collection_hotel:
                LoadingIndicator.show(HotelDetailActivity.this, getString(R.string.http_notice));
                UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
                biz.doCollection(MainActivity.user.getUserId(), "2", hotelDetail.getId());
                break;
            case R.id.layout_hotel_address:
                //TODO 进入地图
                break;
            case R.id.layout_hotel_opening:
                //TODO 进入详情页
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
