package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.SearchShopGridAdapter;
import com.jhhy.cuiweitourism.biz.FindShopBiz;
import com.jhhy.cuiweitourism.moudle.ShopRecommend;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchShopActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = SearchShopActivity.class.getSimpleName();
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private ActionBar actionBar;

    private PullToRefreshGridView pullToRefreshGridView;
    private GridView gridView;

    private List<ShopRecommend> lists = new ArrayList<>();
    private SearchShopGridAdapter adapter;

    private int page = 1;
    private boolean add = false; //标记下一页或刷新

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_FIND_SHOP:
                    if (msg.arg1 == 1){
                        if (add){ //下一页
                            lists.addAll((Collection<? extends ShopRecommend>) msg.obj);
                            adapter.addData((List<ShopRecommend>) msg.obj);
                            page ++;
                            add = false;
                        } else { //刷新

                            lists = (List<ShopRecommend>) msg.obj;
                            adapter.setData(lists);
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
            actionBar.setDisplayShowCustomEnabled(true);
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
            actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
            actionBar.setElevation(0); //删除自带阴影

            setContentView(R.layout.activity_search_shop);
            getInternetData();
            setupView();
            addListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getInternetData() {
        FindShopBiz biz = new FindShopBiz(getApplicationContext(), handler);
        biz.getShop(String.valueOf(page));
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastCommon.toastShortShow(getApplicationContext(), null, "grid item Click");
                Intent intent = new Intent(getApplicationContext() , LineListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("shopId", lists.get(i).getId());
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_LINE_LIST);
            }
        });
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
        tvTitle.setText("找线路");
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        pullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_gridview);
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.DISABLED);
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
        }
    }
}
