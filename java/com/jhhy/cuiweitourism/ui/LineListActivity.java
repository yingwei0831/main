package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.SearchShopListAdapter;
import com.jhhy.cuiweitourism.biz.FindShopBiz;
import com.jhhy.cuiweitourism.model.Line;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class LineListActivity extends BaseActionBarActivity {

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private List<Line> list = new ArrayList<>();
    private SearchShopListAdapter adapter;

    private String shopId;
    private String shopName;
    private int page = 0;
//    private boolean add = false;

    private boolean refresh = true;
    private boolean loadMore;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_FIND_SHOP_LINE_LIST: //该商铺的所有线路
                    if (msg.arg1 == 1) {
                        List<Line> listNew = (List<Line>) msg.obj;
                        if (listNew != null && listNew.size()!= 0) {
                            if (loadMore) { //加载更多
                                loadMore = false;
                                list.addAll(listNew);
                                adapter.notifyDataSetChanged();
//                                adapter.addData(listNew);
                            }
                            if (refresh) { //下拉刷新
                                refresh = false;
                                list = listNew;
                                adapter.setData(list);
                            }
                        }else{
//                            ToastCommon.toastShortShow(getApplicationContext(), null, "暂无新数据");
                            if (loadMore){
                                page --;
                            }
                        }
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                        if (loadMore){
                            page --;
                        }
                    }
                    LoadingIndicator.cancel();
                    pullToRefreshListView.onRefreshComplete();
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
        setContentView(R.layout.layout_listview);
        getData();
        super.onCreate(savedInstanceState);
        getInternetData();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shopId = bundle.getString("shopId");
            shopName = bundle.getString("shopName");
        }
    }

    private void getInternetData(){
        LoadingIndicator.show(LineListActivity.this, getString(R.string.http_notice));
        FindShopBiz biz = new FindShopBiz(getApplicationContext(), handler);
        biz.getLineList(shopId, String.valueOf(page));
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText(shopName);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listView);
        pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("松开加载更多");

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        listView = pullToRefreshListView.getRefreshableView();
        adapter = new SearchShopListAdapter(getApplicationContext(), list);
        pullToRefreshListView.setAdapter(adapter);
    }

    @Override
    protected void addListener() {
        super.addListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //进入线路详情
                Intent intent = new Intent(getApplicationContext(), InnerTravelDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get((int)l).getLineId());
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_LINE_DETAIL);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //TODO 下拉刷新
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //TODO 加载更多
                loadMore();
            }
        });
    }

    private void loadMore() {
        if (loadMore)   return;
        loadMore = true;
        page ++;
        getInternetData();
    }

    private void refresh() {
        if (refresh)    return;
        refresh = true;
        getInternetData();
    }

    private final int VIEW_LINE_DETAIL = 2908; //进入线路详情

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIEW_LINE_DETAIL){
            if (resultCode == RESULT_OK){
                //TODO 可能预定线路
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
