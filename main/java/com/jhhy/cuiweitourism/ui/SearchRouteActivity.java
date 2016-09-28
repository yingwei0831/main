package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.HotActivityListViewAdapter;
import com.jhhy.cuiweitourism.adapter.Tab1InnerTravelListViewAdapter;
import com.jhhy.cuiweitourism.biz.FindLinesBiz;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.popupwindows.InnerTravelPopupWindow;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchRouteActivity extends BaseActivity implements View.OnClickListener, ArgumentOnClick {


    private List<Travel> mLists = new ArrayList<>();

    private PullToRefreshListView pullToRefreshListView;
    private Tab1InnerTravelListViewAdapter adapter;

    private View layout;
    private TextView tvSortDefault;
    private TextView tvSortDays;
    private TextView tvStartTime;
    private TextView tvScreenPrice;

    private int tag;

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
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_route);
        setupView();
        addListener();
        getData();
    }

    private void getData() {
        FindLinesBiz biz = new FindLinesBiz(getApplicationContext(), handler);
//        biz.getLines(1, 10);
    }

    private void setupView() {
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
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullToRefreshListView.onRefreshComplete();
            }
        });

        tvSortDefault = (TextView) findViewById(R.id.tv_tab1_search_route_list_sort_default);
        tvSortDays = (TextView) findViewById(R.id.tv_tab1_search_route_list_trip_days);
        tvStartTime = (TextView) findViewById(R.id.tv_tab1_search_route_list_start_time);
        tvScreenPrice = (TextView) findViewById(R.id.tv_tab1_search_route_list_screen_price);

        adapter = new Tab1InnerTravelListViewAdapter(getApplicationContext(), mLists, this);
        pullToRefreshListView.setAdapter(adapter);
    }

    private void addListener() {
        tvSortDefault.setOnClickListener(this);
        tvSortDays.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvScreenPrice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab1_search_route_list_sort_default:
                tag = 1;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_tab1_search_route_list_trip_days:
                tag = 2;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_tab1_search_route_list_start_time:
                tag = 3;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;
            case R.id.tv_tab1_search_route_list_screen_price:
                tag = 4;
//                new InnerTravelPopupWindow(this, layout, tag);
                break;

        }
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
