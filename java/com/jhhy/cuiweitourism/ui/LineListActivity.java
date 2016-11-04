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
import com.jhhy.cuiweitourism.moudle.Line;
import com.jhhy.cuiweitourism.net.utils.Consts;
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
    private boolean add = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_FIND_SHOP_LINE_LIST: //该商铺的所有线路
                    if (msg.arg1 == 1) {
                        if (add) { //加载更多

                        } else { //下拉刷新
                            list = (List<Line>) msg.obj;
                            if (list == null || list.size() == 0) {

                            } else {
                                adapter.setData(list);
                            }
                        }
                    }else{
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
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

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);

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
                bundle.putString("id", list.get(i).getLineId());
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
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
