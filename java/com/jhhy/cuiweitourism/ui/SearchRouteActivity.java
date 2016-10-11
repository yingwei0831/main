package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab1InnerTravelListViewAdapter;
import com.jhhy.cuiweitourism.biz.FindLinesBiz;
import com.jhhy.cuiweitourism.biz.ScreenBiz;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.moudle.PriceArea;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowSearchLine;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchRouteActivity extends BaseActivity implements View.OnClickListener, ArgumentOnClick {

    private TextView tvTitleTop;
    private ImageView ivTitleLeft;

    private List<Travel> mLists = new ArrayList<>();

    private PullToRefreshListView pullToRefreshListView;
    private Tab1InnerTravelListViewAdapter adapter;
    private ListView listView;

    private View layout;
    private TextView tvSortDefault;
    private TextView tvSortDays;
    private TextView tvStartTime;
    private TextView tvScreenPrice;

    private int tag;

    private List<String> listDays = new ArrayList<>();
    private List<PriceArea> listPrices = new ArrayList<>();

    private int page = 1;
    private String fromCityId = "1";
    private String sort = "";
    private String day = "";
    private String price = "";
    private String earlyTime = "";
    private String laterTime = "";

    private PhoneBean selectCity;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_FIND_LINES:
                    if(msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), "加载数据出错");
                    }else {
                        List<Travel> listNew = (List<Travel>) msg.obj;
                        if(listNew != null && listNew.size() != 0){
                            adapter.setData(listNew);
                        }else{
                            ToastUtil.show(getApplicationContext(), "加载数据失败");
                        }
                    }
                    break;
                case Consts.MESSAGE_TRIP_DAYS:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                        return;
                    }
                    listDays = (List<String>) msg.obj;
                    if (listDays == null || listDays.size() == 0){
                        ToastUtil.show(getApplicationContext(), "未获取到筛选天数");
                    }
                    break;
                case Consts.MESSAGE_TRIP_PRICE:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getApplicationContext(), (String) msg.obj);
                        return;
                    }
                    listPrices = (List<PriceArea>) msg.obj;
                    if (listPrices == null || listPrices.size() == 0){
                        ToastUtil.show(getApplicationContext(), "未获取到筛选价格区间");
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_route);
        getData();
        setupView();
        addListener();
        getInternetData();
    }

    private void getInternetData() {
        //获取筛选的行程天数和价格
        ScreenBiz screenBiz = new ScreenBiz(getApplicationContext(), handler);
        screenBiz.getScreenDays();
        screenBiz.getScreenPrice();

        FindLinesBiz biz = new FindLinesBiz(getApplicationContext(), handler);
        biz.getLines(page, fromCityId, sort, day, price, earlyTime, laterTime);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            selectCity = (PhoneBean) bundle.getSerializable("selectCity");
            if (selectCity == null){
                selectCity = new PhoneBean();
                selectCity.setCity_id("20");
                selectCity.setName("北京");
            }
        }
    }

    private void setupView() {
        tvTitleTop = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitleTop.setText(selectCity.getName());
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);

        layout = findViewById(R.id.activity_search_route_list);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.activity_search_route_list_view);
        //这几个刷新Label的设置
        pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //上拉、下拉监听函数
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //TODO 下拉刷新
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //TODO 加载更多
                pullToRefreshListView.onRefreshComplete();
            }
        });

        tvSortDefault = (TextView) findViewById(R.id.tv_tab1_search_route_list_sort_default);
        tvSortDays = (TextView) findViewById(R.id.tv_tab1_search_route_list_trip_days);
        tvStartTime = (TextView) findViewById(R.id.tv_tab1_search_route_list_start_time);
        tvScreenPrice = (TextView) findViewById(R.id.tv_tab1_search_route_list_screen_price);

        adapter = new Tab1InnerTravelListViewAdapter(getApplicationContext(), mLists, this);
        pullToRefreshListView.setAdapter(adapter);

        listView = pullToRefreshListView.getRefreshableView();
    }

    private void addListener() {
        tvSortDefault.setOnClickListener(this);
        tvSortDays.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvScreenPrice.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //进入线路详情
                Intent intent = new Intent(getApplicationContext(), InnerTravelDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mLists.get(i).getId());
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_LINE_DETAIL);
            }
        });
    }

    private final int VIEW_LINE_DETAIL = 2908; //进入线路详情

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_LINE_DETAIL){
            if (resultCode == RESULT_OK){
                //TODO 可能预定线路

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1_search_route_list_sort_default:
                tag = 1;
//                new InnerTravelPopupWindow(this, layout, tag);
                showPopupWindow();
                break;
            case R.id.tv_tab1_search_route_list_trip_days:
                tag = 2;
                showPopupWindow();
                break;
            case R.id.tv_tab1_search_route_list_start_time:
                tag = 3;
                showPopupWindow();
                break;
            case R.id.tv_tab1_search_route_list_screen_price:
                tag = 4;
                showPopupWindow();
                break;

        }
    }

    private void showPopupWindow() {
        if (popupWindowSearchLine == null) {
            popupWindowSearchLine = new PopupWindowSearchLine(SearchRouteActivity.this, layout, tag, listDays, listPrices);
            addPopListener();
        }else{
            popupWindowSearchLine.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        }
    }

    private PopupWindowSearchLine popupWindowSearchLine;

    private void addPopListener(){
        popupWindowSearchLine.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //获取相关参数值：摁了“确定”，返回按钮，摁了“取消”，BACK键
                boolean commit = popupWindowSearchLine.getCommit();
                if (commit) {
                    String newSort = popupWindowSearchLine.getSort();
                    if (newSort != null) {
                        sort = newSort;
                    }
                    String newDay = popupWindowSearchLine.getDay();
                    if (newDay != null) {
                        day = newDay;
                    }
                    String newPrice = popupWindowSearchLine.getPrice();
                    if (newPrice != null) {
                        price = newPrice;
                    }
                    String newEarylTime = popupWindowSearchLine.getEarlyTime();
                    if (newEarylTime != null) {
                        earlyTime = newEarylTime;
                    }
                    String newLaterTime = popupWindowSearchLine.getLaterTime();
                    if (newLaterTime != null) {
                        laterTime = newLaterTime;
                    }
                    //重新请求数据
                    FindLinesBiz biz = new FindLinesBiz(getApplicationContext(), handler);
                    biz.getLines(page, fromCityId, sort, day, price, earlyTime, laterTime);
                }
            }
        });
    }

    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, SearchRouteActivity.class);
        if(data != null){
            intent.putExtra("data", data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        ToastUtil.show(getApplicationContext(), "讨价还价");
    }
}
