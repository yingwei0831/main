package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.SearchListAdapter;
import com.jhhy.cuiweitourism.adapter.Tab1InnerTravelListViewAdapter;
import com.jhhy.cuiweitourism.moudle.PhoneBean;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndSearch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForceEndSearchInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActionBarActivity implements ArgumentOnClick {

    private String TAG = SearchActivity.class.getSimpleName();

    private PhoneBean selectCity;

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;

    private SearchListAdapter adapter;
    private ArrayList<ForceEndSearchInfo> lists = new ArrayList<>();

    private EditText etSearchCity; //
    private ImageView ivSearch;
    private String city;
    private boolean refresh;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    if (refresh){
                        pullToRefreshListView.onRefreshComplete();
                    }
                    break;
                case -2:
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求线路信息出错，请重试");
                    if (refresh){
                        pullToRefreshListView.onRefreshComplete();
                    }
                    break;
                case 1:
                    if (refresh){
                        pullToRefreshListView.onRefreshComplete();
                    }
                    adapter.setData(lists);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        getData();
        super.onCreate(savedInstanceState);
        getSearchData();
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
        city = selectCity.getName();
    }

    @Override
    protected void setupView() {
        super.setupView();
        tvTitle.setText("路线查找");

        etSearchCity = (EditText) findViewById(R.id.et_search_key_words);
        etSearchCity.setHint(selectCity.getName());
        etSearchCity.setText(selectCity.getName());
        ivSearch = (ImageView) findViewById(R.id.title_search_iv_right_telephone);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.activity_search_list_view);
        //这几个刷新Label的设置
        pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");

        //上拉、下拉设定
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

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
//                loadMore();
            }
        });
        adapter = new SearchListAdapter(getApplicationContext(), lists, this);
        pullToRefreshListView.setAdapter(adapter);

        listView = pullToRefreshListView.getRefreshableView();
    }

    private void update() {
        refresh = true;
        getSearchData();
    }

    @Override
    protected void addListener() {
        super.addListener();
        ivSearch.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //进入线路详情
                Intent intent = new Intent(getApplicationContext(), InnerTravelDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", lists.get((int)l).getTid());
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_LINE_DETAIL);
            }
        });
    }

    private int VIEW_LINE_DETAIL = 7591; //查看详情

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == VIEW_LINE_DETAIL){ //订线路，不需要响应
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.title_search_iv_right_telephone:
                getSearchCity();
                break;
        }
    }

    private void getSearchCity() {
        String temp = etSearchCity.getText().toString();
        if (TextUtils.isEmpty(temp)){
            ToastCommon.toastShortShow(getApplicationContext(), null, "请输入城市名称");
            return;
        }
        city = temp;
        getSearchData();
    }

    private void getSearchData() {
        LoadingIndicator.show(SearchActivity.this, getString(R.string.http_notice));
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
        ForeEndSearch search = new ForeEndSearch(city);
        //搜索
        fbiz.forceEndSearch(search, new BizGenericCallback<ArrayList<ForceEndSearchInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ForceEndSearchInfo>> model) {
                if ("0001".equals(model.headModel.res_code)){
//                    ToastUtil.show(getApplicationContext(), model.headModel.res_arg);
                    Message msg = new Message();
                    msg.what = -1;
                    msg.obj = model.headModel.res_arg;
                    handler.sendMessage(msg);
                }else if ("0000".equals(model.headModel.res_code)){
                    lists = model.body;
                    handler.sendEmptyMessage(1);
                    LogUtil.e(TAG,"forceEndSearch =" + model.body.toString());
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
//                    ToastUtil.show(getApplicationContext(), error.localReason);
                    Message msg = new Message();
                    msg.what = -1;
                    msg.obj = error.localReason;
                    handler.sendMessage(msg);
                }else{
//                    ToastUtil.show(getApplicationContext(), "请求火车票信息出错，请返回重试");
                    handler.sendEmptyMessage(-2);
                }
                LogUtil.e(TAG, "forceEndSearch: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * @param view      layout布局
     * @param viewGroup useless
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        //讨价还价
    }
}
