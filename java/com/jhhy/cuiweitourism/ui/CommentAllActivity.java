package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.CommentAllListAdapter;
import com.jhhy.cuiweitourism.adapter.Tab1InnerTravelListViewAdapter;
import com.jhhy.cuiweitourism.net.biz.ForeEndActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.ForeEndMoreCommentFetch;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndAdvertisingPositionInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ForeEndMoreCommentInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class CommentAllActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final String TAG = CommentAllActivity.class.getSimpleName();
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private String articleId;

    private PullToRefreshListView listView;
    private ListView refreshView; //数据操作列表

    private CommentAllListAdapter adapter;
    private List<ForeEndMoreCommentInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影
        setContentView(R.layout.layout_listview);
        getData();
        getInternetData();
        setupView();
        addListener();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                articleId = bundle.getString("articleId");
            }
        }
    }

    private void getInternetData() {
        LoadingIndicator.show(CommentAllActivity.this, getString(R.string.http_notice));
        //更多评论
        ForeEndMoreCommentFetch fetch = new ForeEndMoreCommentFetch("1","12"); //typeid 1.线路、2.酒店、3租车、8签证、14私人定制；articleId(上页传过来的)
        ForeEndActionBiz fbiz = new ForeEndActionBiz();
        fbiz.foreEndGetMoreCommentInfo(fetch, new BizGenericCallback<ArrayList<ForeEndMoreCommentInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<ForeEndMoreCommentInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<ForeEndMoreCommentInfo> array = model.body;
                    LogUtil.e(TAG,"foreEndGetMoreCommentInfo =" + array.toString());
                    refreshViewData(array);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }


            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "获取广告位数据出错");
                }
                LogUtil.e(TAG, "foreEndGetMoreCommentInfo: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    private void setupView() {
        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.tab1_inner_travel_detail_all_comment));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        listView = (PullToRefreshListView) findViewById(R.id.listView);
        listView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        listView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        listView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        listView.getLoadingLayoutProxy().setReleaseLabel("松开加载更多");

//        listView.setEmptyView();

        refreshView = listView.getRefreshableView();
        adapter = new CommentAllListAdapter(getApplicationContext(), list) {
            @Override
            public void onItemClickI(AdapterView<?> adapterView, View view, int position, long id, int parentPosition) {
                //TODO 进入图片大图浏览页面
                ToastCommon.toastShortShow(getApplicationContext(), null, "进入图片大图浏览页面 " + parentPosition + ", " + position);
            }
        };

        listView.setAdapter(adapter);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refrehsh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogUtil.e(TAG, "i = " + i + ", l = " + l);
    }


    private void refreshViewData(ArrayList<ForeEndMoreCommentInfo> array) {
        adapter.setData(array);
    }

    private void loadMore() {
        listView.onRefreshComplete();
    }

    private void refrehsh() {
        listView.onRefreshComplete();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
        }
    }
}
