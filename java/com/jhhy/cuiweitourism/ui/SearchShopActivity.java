package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.SearchShopGridAdapter;
import com.jhhy.cuiweitourism.biz.FindShopBiz;
import com.jhhy.cuiweitourism.model.ShopRecommend;
import com.jhhy.cuiweitourism.net.biz.ShopActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ShopSearchRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class SearchShopActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private String TAG = "SearchShopActivity";
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private PullToRefreshGridView pullToRefreshGridView;
    private GridView gridView;

    private List<ShopRecommend> lists = new ArrayList<>();
    private SearchShopGridAdapter adapter;

    private List<ShopRecommend> listSearch = new ArrayList<>();

    private int page = 1;

    private boolean refresh = true;
    private boolean loadMore;

    private EditText etSearch; //输入框
    private ImageView ivSearchShop; //搜索

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_FIND_SHOP:
                    if (msg.arg1 == 1){
                        List<ShopRecommend> listNew = (List<ShopRecommend>) msg.obj;
                        if (listNew != null && listNew.size() != 0) {
                            if (loadMore) { //下一页
                                loadMore = false;
                                lists.addAll(listNew);
                                adapter.notifyDataSetChanged();
//                                adapter.addData(listNew);
                            }
                            if (refresh) { //刷新
                                refresh = false;
                                lists = (List<ShopRecommend>) msg.obj;
                                adapter.setData(lists);
                            }
                        }else{
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
                    pullToRefreshGridView.onRefreshComplete();
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
        try {
            //获取ActionBar对象
//            ActionBar bar =  getSupportActionBar();
//            //自定义一个布局，并居中
//            bar.setDisplayShowCustomEnabled(true);
//            viewTitle = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
//            bar.setCustomView(viewTitle, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

            //自定义ActionBar
            actionBar = getSupportActionBar();
//            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            if (actionBar != null) {
                actionBar.setDisplayShowCustomEnabled(true);
                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
                actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
                actionBar.setElevation(0); //删除自带阴影
            }

            setContentView(R.layout.activity_search_shop);
            getInternetData();
            setupView();
            addListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getInternetData() {
        LoadingIndicator.show(SearchShopActivity.this, getString(R.string.http_notice));
        FindShopBiz biz = new FindShopBiz(getApplicationContext(), handler);
        biz.getShop(String.valueOf(page));
    }

    private void addListener() {
        ivSearchShop.setOnClickListener(this);
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.e(TAG, "i = " + i +", l = " + l);
                Intent intent = new Intent(getApplicationContext() , LineListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("shopId", lists.get((int) l).getSid());
                bundle.putString("shopName", lists.get((int) l).getSuppliername());
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_LINE_LIST);
            }
        });

        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                loadMore();
            }
        });

        etSearch.addTextChangedListener(this);
    }

    private void loadMore() {
        if (loadMore)   return;
        loadMore = true;
        page ++;
        getInternetData();
    }

    private void refresh() {
        if (refresh)    return;
        page = 1;
        refresh = true;
        getInternetData();
    }

    private int REQUEST_LINE_LIST = 8901; //该旅行社所有旅行线路列表

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LINE_LIST){
            if (resultCode == RESULT_OK){

            }
        }
    }

    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.tab1_shop_title));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        etSearch = (EditText) findViewById(R.id.edit_search);
        ivSearchShop = (ImageView) findViewById(R.id.iv_search_shop);

        pullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_gridview);
        pullToRefreshGridView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullToRefreshGridView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullToRefreshGridView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullToRefreshGridView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);

        gridView = pullToRefreshGridView.getRefreshableView();
        adapter = new SearchShopGridAdapter(getApplicationContext(), lists);
        pullToRefreshGridView.setAdapter(adapter);
    }


    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, SearchShopActivity.class);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            case R.id.iv_search_shop: //搜索按钮
                searchShop();
                break;
        }
    }

    private void searchShop() {
        String search = etSearch.getText().toString();
        if (TextUtils.isEmpty(search)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "搜索输入为空");
            return;
        }
//        Utils.setKeyboardInvisible(this);
        ShopActionBiz biz = new ShopActionBiz();
        ShopSearchRequest fetch = new ShopSearchRequest(search);
        biz.getShopSearch(fetch, new BizGenericCallback<ArrayList<ShopRecommend>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ShopRecommend>> model) {
                listSearch = model.body;
                adapter.setData(listSearch);
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "搜索数据发生错误，请重试");
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        LogUtil.e(TAG, "--------beforeTextChanged-------");
//        LogUtil.e(TAG, "charSequence: " + charSequence);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        LogUtil.e(TAG, "--------onTextChanged-------");
//        LogUtil.e(TAG, "charSequence: " + charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {
//        LogUtil.e(TAG, "--------afterTextChanged-------");
//        LogUtil.e(TAG, "editable: " + editable);
        if (TextUtils.isEmpty(editable)){
//            Utils.setKeyboardInvisible(this);
            adapter.setData(lists);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lists.clear();
        lists = null;
        listSearch.clear();
        listSearch = null;
        adapter = null;
    }
}
