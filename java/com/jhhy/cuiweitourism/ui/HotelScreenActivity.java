package com.jhhy.cuiweitourism.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotelSortListAdapter;
import com.jhhy.cuiweitourism.adapter.InnerTravelTripDaysListViewAdapter;
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HoutelPropertiesInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class HotelScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = HotelScreenActivity.class.getSimpleName();

    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private ListView listViewFirst;
    private ListView listViewSecond;
    private LinearLayout layoutCommit;
    private Button btnCommit;

    private ArrayList<HoutelPropertiesInfo> screenProperties = new ArrayList<>();


    private HotelSortListAdapter adapterFirst;
    private HotelSortListAdapter adapterSecond;


//    private int firstSelection = 0;
//    private int secondSelection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影

        setContentView(R.layout.activity_select_all_country_area);
        getData();
        getInternetData();
        setupView();
        addListener();
    }

    private void getData() {

    }
    private void getInternetData() {
        LoadingIndicator.show(HotelScreenActivity.this, getString(R.string.http_notice));

        HotelActionBiz hotelBiz = new HotelActionBiz();

        // 获取酒店属性列表
        hotelBiz.hotelGetPropertiesList(new BizGenericCallback<ArrayList<HoutelPropertiesInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<HoutelPropertiesInfo>> model) {

                if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }else if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<HoutelPropertiesInfo> array = model.body;
                    LogUtil.e(TAG, "hotelGetPropertiesList =" + array.toString());
                    screenProperties = array;
                    refreshView();
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else {
                    LogUtil.e(TAG, "hotelGetPropertiesList: " + error.toString());
                }
                LoadingIndicator.cancel();
            }
        });

    }

    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.hotel_screen));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        listViewFirst = (ListView) findViewById(R.id.list_father);
        listViewSecond = (ListView) findViewById(R.id.list_son);

        layoutCommit = (LinearLayout) findViewById(R.id.layout_hotel_commit);
        layoutCommit.setVisibility(View.VISIBLE);
        btnCommit = (Button) findViewById(R.id.btn_commit);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        listViewFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                refreshSecondListView(i);
            }
        });

        listViewSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterSecond.setSelection(i);
                adapterSecond.notifyDataSetChanged();
            }
        });
    }

    private void refreshSecondListView(int position) {
        adapterFirst.setSelection(position);
        adapterFirst.notifyDataSetChanged();
        adapterSecond.setData(screenProperties.get(position).getSon());
        adapterSecond.notifyDataSetChanged();
    }

    private void refreshView() {
        adapterFirst = new HotelSortListAdapter(getApplicationContext(), screenProperties);
        adapterFirst.setSelection(0);
        adapterFirst.setType(1);
        listViewFirst.setAdapter(adapterFirst);
        adapterSecond = new HotelSortListAdapter(getApplicationContext(), screenProperties.get(0).getSon());
        adapterSecond.setSelection(0);
        adapterSecond.setType(2);
        listViewSecond.setAdapter(adapterSecond);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.btn_commit:

                break;
        }
    }

}
