package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jhhy.cuiweitourism.OnItemTextViewClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.PlaneInquiryAdapter;
import com.jhhy.cuiweitourism.dialog.DatePickerActivity;
import com.jhhy.cuiweitourism.model.PlaneInquiry;
import com.jhhy.cuiweitourism.net.biz.PlaneTicketActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketCityFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.jhhy.cuiweitourism.view.MyListView;

import java.util.ArrayList;

public class PlaneMainActivity extends BaseActionBarActivity implements RadioGroup.OnCheckedChangeListener, OnItemTextViewClick {

    private String TAG = PlaneMainActivity.class.getSimpleName();

    private RadioGroup radioGroup; //出行类型
    private ImageView ivExchange; //交换出发地和目的地
    private TextView tvFromCity;
    private TextView tvToCity;
    private TextView tvFromDate;
    private LinearLayout layoutReturnDate; //返程时间
    private TextView tvReturnDate; //返程时间
    private Button btnSearch; //搜索

//    private String selectDate; //当前时间
    private String dateFrom; //选择的出发时间，显示在Textview 上
    private String dateReturn; //选择的返程时间，显示在Textview 上
    private PlaneTicketCityInfo fromCity; //出发城市
    private PlaneTicketCityInfo toCity; //到达城市

    private PlaneTicketActionBiz planeBiz;
    public static ArrayList<PlaneTicketCityInfo> airportInner; //国内飞机场
    public static ArrayList<PlaneTicketCityInfo> airportOuter; //国际飞机场

    private View layoutPlaneSearch; //询价整体布局
    private MyListView layoutPlaneInquiryParent; //询价包裹行程布局
    private LinearLayout layoutBtn; //询价btn
    private Button btnInquiry; //询价btn
    private LinearLayout tvAddInquiry; //增加一程询价
    private LinearLayout layoutInquiryType; //形成类型

    private ArrayList<PlaneInquiry> listInquiry = new ArrayList<>(); //出发城市，目的城市，出发时间

    private int bottomSelectionPosition; //底部点击位置
    private PlaneInquiryAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    ToastUtil.show(getApplicationContext(), String.valueOf(msg.obj));
                    break;
                case -2:
                    ToastUtil.show(getApplicationContext(), "请求国内机场信息出错，请返回重试");
                    break;
                case -3:
                    ToastUtil.show(getApplicationContext(), "请求国际机场信息出错，请返回重试");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_plane_main);
        super.onCreate(savedInstanceState);
        getInternetData();
        getInternetDataOut();
        LoadingIndicator.show(PlaneMainActivity.this, getString(R.string.http_notice));
    }

    @Override
    protected void setupView() {
        super.setupView();
        planeBiz = new PlaneTicketActionBiz();
        tvTitle.setText(getString(R.string.tab1_tablelayout_plane));
        radioGroup = (RadioGroup) findViewById(R.id.rg_plane_type);
        ivExchange = (ImageView) findViewById(R.id.iv_train_exchange);
        tvFromCity =    (TextView) findViewById(R.id.tv_plane_from_city);
        tvToCity =      (TextView) findViewById(R.id.tv_plane_to_city);
        tvFromDate =    (TextView) findViewById(R.id.tv_plane_from_time);
        layoutReturnDate = (LinearLayout) findViewById(R.id.layout_return_time);
        layoutReturnDate.setVisibility(View.GONE);
        tvReturnDate =  (TextView) findViewById(R.id.tv_plane_return_time);
        btnSearch = (Button) findViewById(R.id.btn_plane_search);

        layoutPlaneSearch = findViewById(R.id.layout_plane_search); //搜索航班线路
        layoutBtn = (LinearLayout) findViewById(R.id.layout_inquiry_btn); //查询下一步
        btnInquiry = (Button) findViewById(R.id.btn_train_next_step); //查询下一步
        tvAddInquiry = (LinearLayout) findViewById(R.id.layout_add_one_query); //增加
        layoutInquiryType = (LinearLayout) findViewById(R.id.layout_inquiry_type); //类型
        layoutPlaneInquiryParent = (MyListView) findViewById(R.id.layout_query_lines); //查询线路列表
        tvAddInquiry.setVisibility(View.GONE);
        layoutInquiryType.setVisibility(View.GONE);
        layoutBtn.setVisibility(View.GONE);
        layoutPlaneInquiryParent.setVisibility(View.GONE);

        listInquiry.add(new PlaneInquiry());
        adapter = new PlaneInquiryAdapter(getApplicationContext(), listInquiry, this);
        layoutPlaneInquiryParent.setAdapter(adapter);

        tvFromCity.setText("北京");
        tvToCity.setText("大连");
        dateFrom = Utils.getCurrentTimeYMDE();
        dateReturn = dateFrom;
        tvFromDate.setText(dateFrom.substring(dateFrom.indexOf("-") + 1, dateFrom.indexOf(" ")));
        tvReturnDate.setText(dateReturn.substring(dateReturn.indexOf("-") + 1, dateReturn.indexOf(" ")));

        fromCity = new PlaneTicketCityInfo();
        fromCity.setName("北京");
        fromCity.setCode("PEK");
        fromCity.setAirportname("北京首都国际机场");
        toCity = new PlaneTicketCityInfo();
        toCity.setName("大连");
        toCity.setCode("DLC");
        toCity.setAirportname("大连周水子国际机场");
    }

    @Override
    protected void addListener() {
        super.addListener();
        radioGroup.setOnCheckedChangeListener(this);
        ivExchange.setOnClickListener(this);
        tvFromCity.setOnClickListener(this);
        tvToCity.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        tvReturnDate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        tvAddInquiry.setOnClickListener(this);
        btnInquiry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_train_exchange:
                exchange();
                break;
            case R.id.tv_plane_from_city: //出发城市
                selectFromCity();
                break;
            case R.id.tv_plane_to_city: //到达城市
                selectToCity();
                break;
            case R.id.tv_plane_from_time: //出发时间
                selectFromTime();
                break;
            case R.id.tv_plane_return_time: //出发时间
                selectReturnTime();
                break;
            case R.id.btn_plane_search:
                search();
                break;
            case R.id.layout_add_one_query:
                addView();
                break;
            case R.id.btn_train_next_step: //询价
                inquiry();
                break;

        }
    }

    private void inquiry() {
        PlaneInquiryEditOrderActivity.actionStart(getApplicationContext(), null);
    }

    //搜索
    private void search() {
        if(type == 2){ //往返，添加返程时间
            if (TextUtils.isEmpty(dateReturn)){
                ToastUtil.show(getApplicationContext(), "返程日期不能为空");
                return;
            }
            LogUtil.e(TAG, "dateReturn = " + dateReturn);
        }

        if (typeSearchFrom == 1 && typeSearchTo == 1) { //国内机票
            Intent intent = new Intent(getApplicationContext(), PlaneListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("fromCity", fromCity);
            bundle.putSerializable("toCity", toCity);
            bundle.putString("dateFrom", dateFrom);
            bundle.putString("traveltype", traveltype);
            intent.putExtras(bundle);
            startActivityForResult(intent, VIEW_PLANE_LIST);
        } else { //国际机票
            Intent intent = new Intent(getApplicationContext(), PlaneListInternationalActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("fromCity", fromCity);
            bundle.putSerializable("toCity", toCity);
            bundle.putString("dateFrom", dateFrom);
            bundle.putString("traveltype", traveltype);
            if (type == 2) { //往返，询价
                bundle.putString("dateReturn", dateReturn);
            }
            intent.putExtras(bundle);
            startActivityForResult(intent, VIEW_PLANE_LIST);
        }
    }

    //出发城市
    private void selectFromCity() {
        if (airportInner == null || airportInner.size() == 0){
            ToastUtil.show(getApplicationContext(), "出发地获取失败，请返回重试");
            return;
        }
        Intent intent = new Intent(getApplicationContext(), PlaneCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        //根据当前是单程/往返/询价，传入type; 1:国内 2:国际 3:往返

        bundle.putInt("type", type);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_FROM_CITY);
    }

    //目的城市
    private void selectToCity() {
        if (airportInner == null || airportInner.size() == 0){
            ToastUtil.show(getApplicationContext(), "目的地获取失败，请返回重试");
            return;
        }
        Intent intent = new Intent(getApplicationContext(), PlaneCitySelectionActivity.class);
        Bundle bundle = new Bundle();
        //根据当前是单程/往返/询价，传入type; 1:国内 2:国际 3:询价

        bundle.putInt("type", type);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_TO_CITY);
    }

    //返程时间
    private void selectReturnTime() {
        Intent intent = new Intent(getApplicationContext(), DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_RETURN_TIME);
    }

    //出发时间
    private void selectFromTime() {
        Intent intent = new Intent(getApplicationContext(), DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, SELECT_START_TIME);
    }

    private int SELECT_START_TIME = 6528; //选择出发时间 10-24
    private int SELECT_RETURN_TIME = 6529; //选择返程时间 10-25
    private int SELECT_FROM_CITY = 6526; //选择出发城市
    private int SELECT_TO_CITY = 6527; //选择到达城市
    private int VIEW_PLANE_LIST = 6530; //选择到达城市

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_START_TIME){ //选择出发时间
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                if (type == 3){
                    setFromTime(bundle.getString("selectDate"));
                    return;
                }
                dateFrom = bundle.getString("selectDate");
                tvFromDate.setText(dateFrom.substring(0, dateFrom.indexOf(" ")));
            }
        }else if (requestCode == SELECT_RETURN_TIME){ //选择返程时间
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                dateReturn = bundle.getString("selectDate");
                tvReturnDate.setText(dateReturn.substring(0, dateReturn.indexOf(" ")));
            }
        }else if (requestCode == SELECT_FROM_CITY){ //选择出发城市
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                PlaneTicketCityInfo city = (PlaneTicketCityInfo) bundle.getSerializable("selectCity");
                if (type == 3){
                    setFromCity(city);
                    return;
                }
                typeSearchFrom = bundle.getInt("typeSearch");
                LogUtil.e(TAG, "selectCity = " + city +", typeSearchFrom = " + typeSearchFrom);
                fromCity = city;
                tvFromCity.setText(city.getName());
            }
        } else if (requestCode == SELECT_TO_CITY){ //选择到达城市
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                PlaneTicketCityInfo city = (PlaneTicketCityInfo) bundle.getSerializable("selectCity");
                if (type == 3){
                    setArrivalCity(city);
                    return;
                }
                typeSearchTo = bundle.getInt("typeSearch");
                LogUtil.e(TAG, "selectCity = " + city +", typeSearchTo = " + typeSearchTo);
                toCity = city;
                tvToCity.setText(city.getName());
            }
        }else if (requestCode == VIEW_PLANE_LIST){ //搜索
            if (resultCode == RESULT_OK){
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_plane_one_way: //单程
                layoutPlaneSearch.setVisibility(View.VISIBLE);
                layoutReturnDate.setVisibility(View.GONE);
                layoutBtn.setVisibility(View.GONE);
                layoutPlaneInquiryParent.setVisibility(View.GONE);
                tvAddInquiry.setVisibility(View.GONE);
                layoutInquiryType.setVisibility(View.GONE);
                type = 1;
                traveltype = "OW";
                break;
            case R.id.rb_plane_return: //往返
                layoutPlaneSearch.setVisibility(View.VISIBLE);
                layoutReturnDate.setVisibility(View.VISIBLE);
                layoutBtn.setVisibility(View.GONE);
                layoutPlaneInquiryParent.setVisibility(View.GONE);
                tvAddInquiry.setVisibility(View.GONE);
                layoutInquiryType.setVisibility(View.GONE);
                type = 2;
                traveltype = "RT";
                break;
            case R.id.rb_plane_inquiry: //询价
                layoutPlaneSearch.setVisibility(View.GONE);
                layoutBtn.setVisibility(View.VISIBLE);
                layoutPlaneInquiryParent.setVisibility(View.VISIBLE);
                tvAddInquiry.setVisibility(View.VISIBLE);
                layoutInquiryType.setVisibility(View.VISIBLE);
                type = 3;
                traveltype = "";
                break;
        }
    }

    /**
     * 增加询价行程
     */
    private void addView() { //默认当前询价行程的个数
        PlaneInquiry planeInquiry = new PlaneInquiry();
        if (listInquiry == null){
            listInquiry = new ArrayList<>();
        }
        listInquiry.add(planeInquiry);
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    /**
     * @param position 点击的联系人位置
     * @param textView 控件
     */
    @Override
    public void onItemTextViewClick(int position, View textView, int idNoUse) {
        LogUtil.e(TAG, "position = " + position );
        switch (textView.getId()){
            case R.id.iv_delete_one_query: //删除某个查询路线
                if (listInquiry.size() == 1){
                    ToastUtil.show(getApplicationContext(), "至少查询一条航线");
                    return;
                }
                listInquiry.remove(position);
                adapter.setData(listInquiry);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_plane_from_city: //出发城市
                bottomSelectionPosition = position;
                selectFromCity();
                break;
            case R.id.tv_plane_to_city: //目的城市
                bottomSelectionPosition = position;
                selectToCity();
                break;
            case R.id.iv_train_exchange: //交换出发城市/目的城市
                exchangeCity(position);
                break;
            case R.id.tv_plane_from_time: //出发时间
                bottomSelectionPosition = position;
                selectFromTime();
                break;
        }
    }


    private void setFromTime(String selectDate) {
        String date = selectDate.substring(0, selectDate.indexOf(" "));
        PlaneInquiry item = listInquiry.get(bottomSelectionPosition);
        item.fromDate = date;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    private void setArrivalCity(PlaneTicketCityInfo city) {
        PlaneInquiry item = listInquiry.get(bottomSelectionPosition);
        item.arrivalCity = city;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    private void setFromCity(PlaneTicketCityInfo city) {
        PlaneInquiry item = listInquiry.get(bottomSelectionPosition);
        item.fromCity = city;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    private void exchangeCity(int position) {
        PlaneInquiry temp = listInquiry.get(position);
        PlaneTicketCityInfo tempCity = temp.arrivalCity;
        temp.arrivalCity = temp.fromCity;
        temp.fromCity = tempCity;
        adapter.setData(listInquiry);
        adapter.notifyDataSetChanged();
    }

    //交换出发城市和目的城市
    private void exchange() {
        PlaneTicketCityInfo tempCity = toCity;
        toCity = fromCity;
        fromCity = tempCity;
        tvFromCity.setText(fromCity.getName());
        tvToCity.setText(toCity.getName());
    }

    private int type = 1; //1：单程 2：往返 3：询价
    private int typeSearchFrom = 1; //1:国内，2：国外
    private int typeSearchTo = 1; //1:国内，2：国外
    private String traveltype = "OW"; //航程类型 OW（单程） RT（往返）
    private boolean inner;
    private boolean outer;


    //获取国内机场列表
    private void getInternetData() {
        //飞机出发城市、到达城市
        new Thread(){
            @Override
            public void run() {
                super.run();
                PlaneTicketCityFetch fetch = new PlaneTicketCityFetch("D"); //国内飞机场
                planeBiz.getPlaneTicketCityInfo(fetch, new BizGenericCallback<ArrayList<PlaneTicketCityInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<PlaneTicketCityInfo>> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
//                            ArrayList<PlanTicketCityInfo> array = model.body;
                            airportInner = model.body;
                            LogUtil.e(TAG,"getPlaneTicketCityInfo =" + airportInner.toString());
                        }
                        inner = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }

                    @Override
                    public void onError(FetchError error) {
                        LogUtil.e(TAG, "getPlaneTicketCityInfo: " + error.toString());
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-2);
                        }
                        inner = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }
                });
            }
        }.start();
    }

    //获取国际机场列表
    private void getInternetDataOut() {
        //飞机出发城市、到达城市
        new Thread(){
            @Override
            public void run() {
                super.run();
                PlaneTicketCityFetch fetch = new PlaneTicketCityFetch("I"); //国际飞机场
                planeBiz.getPlaneTicketCityInfo(fetch, new BizGenericCallback<ArrayList<PlaneTicketCityInfo>>() {
                    @Override
                    public void onCompletion(GenericResponseModel<ArrayList<PlaneTicketCityInfo>> model) {
                        if ("0001".equals(model.headModel.res_code)){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = model.headModel.res_arg;
                            handler.sendMessage(msg);
                        }else if ("0000".equals(model.headModel.res_code)){
//                            ArrayList<PlanTicketCityInfo> array = model.body;
                            airportOuter = model.body;
                            LogUtil.e(TAG," 2 getPlaneTicketCityInfo =" + airportOuter.toString());
                        }
                        outer = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }

                    @Override
                    public void onError(FetchError error) {
                        LogUtil.e(TAG, " 1 getPlaneTicketCityInfo: " + error.toString());
                        if (error.localReason != null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = error.localReason;
                            handler.sendMessage(msg);
                        }else{
                            handler.sendEmptyMessage(-3);
                        }
                        outer = true;
                        if (inner && outer) {
                            LoadingIndicator.cancel();
                        }
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (airportInner != null) {
            airportInner.clear();
            airportInner = null;
        }
        if (airportOuter != null){
            airportOuter.clear();
            airportOuter = null;
        }
        if (listInquiry != null){
            listInquiry.clear();
            listInquiry = null;
        }
        planeBiz = null;
        fromCity = null;
        toCity = null;
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, PlaneMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}
