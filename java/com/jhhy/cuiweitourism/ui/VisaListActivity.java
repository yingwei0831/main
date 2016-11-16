package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.VisaListAdapter;
import com.jhhy.cuiweitourism.biz.VisaBiz;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.moudle.VisaHotCountryCity;
import com.jhhy.cuiweitourism.moudle.VisaType;
import com.jhhy.cuiweitourism.net.biz.VisaActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.VisaHot;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaCountryInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.VisaHotInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowVisaType;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VisaListActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private String TAG = VisaListActivity.class.getSimpleName();

    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private String nationId;
    private String nationName;

    private ListView listVisa;
    private VisaListAdapter adapter;
    private List<VisaHotInfo> lists = new ArrayList<>(); //签证列表
    private List<VisaHotInfo> listsScreenByType = new ArrayList<>(); //签证类型筛选列表

    private TextView tvOrder;
    private TextView tvType;
    private TextView tvFromCity;

    private List<String> visaTypeList;

    private LinearLayout layoutParent;

    private PhoneBean fromCity = new PhoneBean();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_VISA_HOT_COUNTRY_LIST:
                    if (msg.arg1 == 1){
                        lists = (List<VisaHotInfo>) msg.obj;
                        if (lists.size() != 0){
                            adapter.setData(lists);
                        }else{
                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取数据为空");
                        }
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
                    break;
//                case Consts.MESSAGE_VISA_TYPE: //签证类型
//                    if (msg.arg1 == 1){
//                        visaTypeList = (List<VisaType>) msg.obj;
//                        visaTypeList.add(0, new VisaType("0", "全部签证"));
//                        if (visaTypeList == null || visaTypeList.size() == 0){
////                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取签证类型为空");
//                        }else{
//
//                        }
//                    }else{
////                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
//                    }
//                    break;
                case Consts.MESSAGE_VISA_CITY:
                    if (msg.arg1 == 1){
//                        visaCityList = (ArrayList<PhoneBean>) msg.obj;
                        return;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.title_tab1_inner_travel); //自定义ActionBar布局);

        setContentView(R.layout.activity_visa_list);
        getData();
        setupView();
        getInternetData();
        addListener();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listVisa.setOnItemClickListener(this);
        tvOrder.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvFromCity.setOnClickListener(this);
    }

    private void setupView() {
        layoutParent = (LinearLayout) findViewById(R.id.layout_parent);

        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(nationName + "签证");
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        listVisa = (ListView) findViewById(R.id.list_view_visa);
        adapter = new VisaListAdapter(getApplicationContext(), lists);
        listVisa.setAdapter(adapter);

        tvOrder = (TextView) findViewById(  R.id.tv_visa_list_sort_default);
        tvType = (TextView) findViewById(   R.id.tv_visa_list_trip_days);
        tvFromCity = (TextView) findViewById(R.id.tv_visa_list_start_time);

        visaOrderList.add("默认排序");
        visaOrderList.add("价格从低到高");
        visaOrderList.add("价格从高到低");

        biz = new VisaActionBiz(getApplicationContext(), handler);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        nationId = bundle.getString("nationId");
        nationName = bundle.getString("nationName");

        visaTypeList = new ArrayList<>();
        visaTypeList.add("全部签证");
        visaTypeList.add("访友签证");
        visaTypeList.add("移民签证");
        visaTypeList.add("非移民签证");
        visaTypeList.add("留学签证");
        visaTypeList.add("旅游签证");
        visaTypeList.add("工作签证");
        visaTypeList.add("商务签证");
        visaTypeList.add("探亲签证");
    }
    private VisaActionBiz biz; //热门签证国家，查看全部国家和地区

    private void getInternetData() {
        VisaHot visaHot = new VisaHot();
        visaHot.setCountryCode(nationId);
        visaHot.setDistributionArea(""); //送签地（三字码）
        biz.getVisaHotList(visaHot, new BizGenericCallback<ArrayList<VisaHotInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<VisaHotInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    lists = model.body;
                    LogUtil.e(TAG,"visaHotInfo: " + lists.toString());
                    adapter.setData(lists);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "热门签证数据出错");
                }
                LogUtil.e(TAG, "visaHotInfo: " + error.toString());
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //进入签证详情
        String id = lists.get(i).getVisaId();
        Intent intent = new Intent(getApplicationContext(), VisaItemDetailActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_VIES_DETAIL);
    }

    private int REQUEST_VIES_DETAIL = 1881; //查看签证详情

    private int SELECT_VISA_CITY = 2101; //选择签证city

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIES_DETAIL){ //查看签证详情，有可能产生办理签证订单
            if (resultCode == RESULT_OK){

            }
        }else if (requestCode == SELECT_VISA_CITY){ //选择签证城市
            if (resultCode == RESULT_OK){
                if (data != null){
                    Bundle bundle = data.getExtras();
                    if (bundle != null){
                        if (fromCity.getCity_id() != null) {
                            if (fromCity.getCity_id().equals(((PhoneBean) bundle.getSerializable("city")).getCity_id())) {
                                return;
                            }
                        }
                        fromCity = (PhoneBean) bundle.getSerializable("city");
                        tvFromCity.setText(fromCity.getName());
                        lists.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    private PopupWindowVisaType popupWindowVisaType;
    private int visaTypePosition = 0;

    private List<String> visaOrderList = new ArrayList<>();
    private PopupWindowVisaType popupWindowOrder;
    private int visaOrderPosition = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_visa_list_sort_default: //默认排序
                if (popupWindowOrder == null) {
                    popupWindowOrder = new PopupWindowVisaType(VisaListActivity.this, layoutParent, visaOrderList);
                    popupWindowOrder.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (visaOrderPosition == popupWindowOrder.getSelection()){
                                return;
                            }
                            visaOrderPosition = popupWindowOrder.getSelection();
                            String orderType = visaOrderList.get(visaOrderPosition);
                            if(visaOrderPosition == 0) {
                                Collections.sort(lists);
                            }else if (visaOrderPosition == 1){
                                Collections.sort(lists);
                            }else{
                                Collections.sort(lists);
                                Collections.reverse(lists);
                            }
                            adapter.setData(lists);
                            tvOrder.setText(orderType);
                        }
                    });
                }else{
                    popupWindowOrder.showAsDropDown(layoutParent, 0, -Utils.getScreenHeight(getApplicationContext()));
                    popupWindowOrder.refreshView(visaOrderPosition);
                }
                break;
            case R.id.tv_visa_list_trip_days: //签证类型
                if (popupWindowVisaType == null) {
                    popupWindowVisaType = new PopupWindowVisaType(VisaListActivity.this, layoutParent, visaTypeList);
                    popupWindowVisaType.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (visaTypePosition == popupWindowVisaType.getSelection()){
                                return;
                            }
                            visaTypePosition = popupWindowVisaType.getSelection();
                            String type = visaTypeList.get(visaTypePosition);
                            if (visaTypePosition == 0){
                                adapter.setData(lists);
                            } else {
                                screenByType(type); //根据签证类型筛选
                                adapter.setData(listsScreenByType);
                            }
                            tvType.setText(type);
                        }
                    });
                }else{
                    popupWindowVisaType.showAsDropDown(layoutParent, 0, -Utils.getScreenHeight(getApplicationContext()));
                    popupWindowVisaType.refreshView(visaTypePosition);
                }
                break;
//            case R.id.tv_visa_list_start_time: //领区（会删除）
//                Intent intent = new Intent(getApplicationContext(), VisaCitySelectionActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("cityList", visaCityList);
////                bundle.putString("currentCity", "北京");
//                intent.putExtras(bundle);
//                startActivityForResult(intent, SELECT_VISA_CITY);
//                break;
        }
    }

    private void screenByType(String type) {
        listsScreenByType.clear();
        for (VisaHotInfo info : lists){
            if (type.equals(info.getVisaType())){
                listsScreenByType.add(info);
            }
        }
    }


}
