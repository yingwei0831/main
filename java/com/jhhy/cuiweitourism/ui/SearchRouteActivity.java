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
import com.jhhy.cuiweitourism.moudle.User;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.popupwindows.PopupWindowSearchLine;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.ui.easemob.EasemobLoginActivity;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.SharedPreferencesUtils;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SearchRouteActivity extends BaseActivity implements View.OnClickListener, ArgumentOnClick {

    private String TAG = SearchRouteActivity.class.getSimpleName();

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
    private String sort = "";
    private String day = "";
    private String price = "";
    private String earlyTime = "";
    private String laterTime = "";
    private int dayPosition = -1;
    private int pricePosition = -1;

    private PhoneBean selectCity;
    private boolean refresh = true; //刷新
    private boolean loadMore; //加载更多

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_FIND_LINES:
                    if(msg.arg1 == 1){
                        List<Travel> listNew = (List<Travel>) msg.obj;
//                        if(listNew != null && listNew.size() != 0){
//                            LogUtil.e(TAG, "refresh = " + refresh +", loadMore = " + loadMore);
                            if (refresh){
                                refresh = false;
                                mLists = listNew;
                                adapter.setData(mLists);
                            }
                            if (loadMore){
                                loadMore = false;
                                mLists.addAll(listNew);
                                adapter.addData(listNew);
                            }
//                        }else{
//                            if (loadMore){
//                                loadMore = false;
//                                page --;
//                            }
//                            if (refresh){
//                                refresh = false;
//                            }
                        if (listNew.size() == 0){
                            ToastUtil.show(getApplicationContext(), "暂无数据");
                            if (loadMore){
                                page --;
                            }
                        }
                    }else{
                        ToastUtil.show(getApplicationContext(), String.valueOf(msg.obj));
                    }
                    LoadingIndicator.cancel();
                    pullToRefreshListView.onRefreshComplete();
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
                case Consts.NET_ERROR:
                    ToastUtil.show(getApplicationContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getApplicationContext(), "与服务器链接超时，请重试");
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
        LoadingIndicator.show(SearchRouteActivity.this, getString(R.string.http_notice));
        getInternetData();
    }

    private void getInternetData() {
        //获取筛选的行程天数和价格
        ScreenBiz screenBiz = new ScreenBiz(getApplicationContext(), handler);
        screenBiz.getScreenDays();
        screenBiz.getScreenPrice();

        FindLinesBiz biz = new FindLinesBiz(getApplicationContext(), handler);
        biz.getLines(page, selectCity.getCity_id(), sort, day, price, earlyTime, laterTime);
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

        TextView tvSearch = (TextView) findViewById(R.id.et_search_key_words);
        tvSearch.setOnClickListener(this);
        layout = findViewById(R.id.activity_search_route_list);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.activity_search_route_list_view);
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
                //TODO 下拉刷新
                update();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //TODO 加载更多
                loadMore();
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

    private void update() {
        if(refresh) return;
        refresh = true;
        page = 1;
        getInternetData();
//        pullToRefreshListView.onRefreshComplete();
    }

    private void loadMore() {
        if (loadMore)   return;
        loadMore = true;
        page ++;
        getInternetData();
//        pullToRefreshListView.onRefreshComplete();
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
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
                bundle.putString("id", mLists.get((int)l).getId());
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_LINE_DETAIL);
            }
        });
    }

    private final int VIEW_LINE_DETAIL = 2908; //进入线路详情
    private int REQUEST_LOGIN = 2913; //请求登录

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "------------onActivityResult------------- " + requestCode);
        if (requestCode == VIEW_LINE_DETAIL){
            if (resultCode == RESULT_OK){
                //TODO 可能预定线路

            }
        }else if (requestCode == Consts.REQUEST_CODE_DATE_PICKER_EARLY){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                earlyTime = bundle.getString("selectDate");
            }
            popupWindowSearchLine.setEarlyTime(earlyTime);
        }else if (requestCode == Consts.REQUEST_CODE_DATE_PICKER_LATER){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                laterTime = bundle.getString("selectDate");
            }
            popupWindowSearchLine.setLaterTime(laterTime);
        }else if (requestCode == REQUEST_LOGIN){
            if (resultCode == RESULT_OK) {
                User user = (User) data.getExtras().getSerializable(Consts.KEY_REQUEST);
                if (user != null) {
                    MainActivity.logged = true;
                    MainActivity.user = user;
                    SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance(getApplicationContext());
                    sp.saveUserId(user.getUserId());
                }
            }else{
                ToastUtil.show(getApplicationContext(), "登录失败");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_search_key_words:
                Bundle bundleSearch = new Bundle();
                bundleSearch.putSerializable("selectCity", selectCity);
                SearchActivity.actionStart(getApplicationContext(), bundleSearch);
                break;
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.tv_tab1_search_route_list_sort_default: //默认排序
                tag = 1;
//                new InnerTravelPopupWindow(this, layout, tag);
                showPopupWindow();
                break;
            case R.id.tv_tab1_search_route_list_trip_days: //行程天数
                tag = 2;
                showPopupWindow();
                break;
            case R.id.tv_tab1_search_route_list_start_time: //出发时间
                tag = 3;
                showPopupWindow();
                break;
            case R.id.tv_tab1_search_route_list_screen_price: //价格筛选
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
            String toSrot = "";
            if ("2".equals(sort)){
                toSrot = "1";
            }else if ("1".equals(sort)){
                toSrot = "2";
            }else{
                toSrot = sort;
            }
            popupWindowSearchLine.refreshView(tag, toSrot, String.valueOf(dayPosition), earlyTime, laterTime, pricePosition);
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
                    if ("0".equals(newSort)){
                        sort = "";
                    }else if ("1".equals(newSort)){
                        sort = "2";
                    }else if ("2".equals(newSort)){
                        sort = "1";
                    }
                    String newDay = popupWindowSearchLine.getDay();
                    if (newDay != null && newDay.length() != 0) {
                        day = newDay;
                        dayPosition = Integer.parseInt(newDay) - 1;
                    }else{
                        day = "";
                        dayPosition = -1;
                    }
                    int newPricePosition = popupWindowSearchLine.getPricePosition();
                    pricePosition = newPricePosition;
                    if (newPricePosition != -1) {
                        price = popupWindowSearchLine.getPrice();
//                        price = listPrices.get(newPricePosition).getPriceLower() +"," + listPrices.get(newPricePosition).getPriceHigh();
                    }else{
                        price = "";
                    }
                    String newEarylTime = popupWindowSearchLine.getEarlyTime();
                    if (newEarylTime != null) {
                        earlyTime = newEarylTime;
                    }
                    String newLaterTime = popupWindowSearchLine.getLaterTime();
                    if (newLaterTime != null) {
                        laterTime = newLaterTime;
                    }
//                    LogUtil.e(TAG, "sort = " + sort + ", day = " + day + ", price = " + price + ", earlyTime = " + earlyTime + ", laterTime = " + laterTime);
//                    LogUtil.e(TAG, "dayPosition = " + dayPosition +", pricePosition = " + pricePosition);
                    //重新请求数据
                    page = 1;
                    refresh = true;
                    FindLinesBiz biz = new FindLinesBiz(getApplicationContext(), handler);
                    biz.getLines(page, selectCity.getCity_id(), sort, day, price, earlyTime, laterTime);
                }
            }
        });
    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
//        ToastUtil.show(getApplicationContext(), "讨价还价");
        switch (which){
            case R.id.tv_inner_travel_item_argument:
                if (MainActivity.logged) { //|| (number != null && !"null".equals(number) && pwd != null && !"null".equals(pwd))
                    Intent intent = new Intent(getApplicationContext(), EasemobLoginActivity.class);
                    String im = mLists.get(position).getIm();
                    if (im == null || im.length() == 0){
                        ToastUtil.show(getApplicationContext(), "当前商户暂未提供客服功能");
                        return;
                    }
                    intent.putExtra("im", im);
                    startActivity(intent);
                }else{
//                    ToastUtil.show(getApplicationContext(), "请登录后再试");
                    //TODO 进入登录页面
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 2);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_LOGIN);
                }
                break;
        }
    }

    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, SearchRouteActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
