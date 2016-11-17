package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.UserIconListAdapter;
import com.jhhy.cuiweitourism.biz.UserInformationBiz;
import com.jhhy.cuiweitourism.moudle.UserIcon;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class Tab4MyTourismCoinActivity extends BaseActivity implements View.OnClickListener {

//    private MyListView lvTourismCoinRecord;
//    private PullToRefreshScrollView pullScrollView;
//    private ScrollView mScrollView;
    private TextView tvTitle;
    private ImageView ivTitleLeft;

    private PullToRefreshListView pullListview;
    private ListView listView;
    private UserIconListAdapter adapter;
    private TextView tvIcons;

    private List<UserIcon> lists = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.REQUEST_CODE_GET_ICONS:
                    if (msg.arg1 == 1){
                        lists = (List<UserIcon>) msg.obj;
                        adapter.setData(lists);
                        tvIcons.setText(String.valueOf(msg.arg2));
                    } else {
                        ToastCommon.toastShortShow(getApplicationContext(), null, String.valueOf(msg.obj));
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_my_tourism_coin2);
        getData();
        setupView();
        addListener();
    }

    private void getData() {
        UserInformationBiz biz = new UserInformationBiz(getApplicationContext(), handler);
        biz.getUserCoin(MainActivity.user.getUserId()); //
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_inner_travel);
        tvTitle.setText(getString(R.string.fragment_mine_my_travel_money));
        ivTitleLeft = (ImageView) findViewById(R.id.title_main_tv_left_location);
        //这几个刷新Label的设置
        pullListview = (PullToRefreshListView) findViewById(R.id.mylistview_tab4_tourism_coin);
        pullListview.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        pullListview.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullListview.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullListview.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        pullListview.setMode(PullToRefreshBase.Mode.DISABLED);

        //上拉、下拉监听函数
        pullListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                PullToRefreshBase.Mode x = pullListview.getCurrentMode();
                if(PullToRefreshBase.Mode.PULL_FROM_END.equals(x)){ //上拉加载
                    Toast.makeText(getApplicationContext(), "上拉加载", Toast.LENGTH_SHORT).show();
                }else if(PullToRefreshBase.Mode.PULL_FROM_START.equals(x)){ //下拉刷新
                    Toast.makeText(getApplicationContext(), "下拉刷新", Toast.LENGTH_SHORT).show();
                }
                //执行刷新函数
//                new GetDataTask().execute();
                // Call onRefreshComplete when the list has been refreshed.
                //在更新UI后，无需其它Refresh操作，系统会自己加载新的listView
                pullListview.onRefreshComplete();
            }
        });

        listView = pullListview.getRefreshableView();
        LinearLayout header = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.header_tab4_my_icon, null);
        tvIcons = (TextView) header.findViewById(R.id.tv_tab4_my_tourism_coin_number);
        tvIcons.setText(MainActivity.user.getUserScore());
        listView.addHeaderView(header);
        adapter = new UserIconListAdapter(getApplicationContext(), lists);
        listView.setAdapter(adapter);

//        View view = findViewById(R.id.empty_view);
//        TextView tvText = (TextView) findViewById(R.id.tv_empty_view);
//        tvText.setText("您还没有旅游币收支明细哦~");
//        pullListview.setEmptyView(view);
//        lvTourismCoinRecord = (MyListView) findViewById(R.id.mylistview_tab4_tourism_coin);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
    }

    public static void actionStart(Context context, Bundle bundle){
        Intent intent = new Intent(context, Tab4MyTourismCoinActivity.class);
        if(bundle != null){
            intent.putExtra("bundle", bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_main_tv_left_location:
                finish();
                break;
        }
    }
}
