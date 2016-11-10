package com.jhhy.cuiweitourism.ui;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.UserCollectionListAdapter;
import com.jhhy.cuiweitourism.biz.UserCollectionBiz;
import com.jhhy.cuiweitourism.moudle.Collection;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;


public class Tab4MyCollectionActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = Tab4MyCollectionActivity.class.getSimpleName();

    private TextView tvTitle;
    private TextView tvTitleLeft;
    private TextView tvTitleRight;

    private boolean edit = false; //编辑

    private RelativeLayout layoutDelete; //删除布局
    private TextView tvNumber;
    private Button btnDelete; //删除按钮

    private PullToRefreshListView pullListView;
    private ListView listView;

    private UserCollectionListAdapter adapter;
    private List<Collection> lists = new ArrayList<>();

    private List<String> collIdSet = new ArrayList<>(); //选择的收藏的id数组

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e(TAG, "-------------handleMessage-------------");

            switch (msg.what){
                case Consts.MESSAGE_GET_MY_COLLECTION: //获取我的收藏
                    if (msg.arg1 == 1){
                        List<Collection> newlists = (List<Collection>) msg.obj;
                        LogUtil.e(TAG, "newlists.size = " + newlists.size()+", list = " + newlists );
//                        if (newlists == null || newlists.size() == 0){
//                            LogUtil.e(TAG, "if ----- newlists = " + newlists );
//                            ToastCommon.toastShortShow(getApplicationContext(), null, "获取收藏数据为空");
//                        } else {
                            LogUtil.e(TAG, "else ----- newlists = " + newlists );
                            lists = newlists;
                            adapter.setData(newlists);
//                        }
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
        setContentView(R.layout.activity_tab4_my_collection);
        setupView();
        addListener();
        getData();
    }

    private void getData() {
        UserCollectionBiz biz = new UserCollectionBiz(getApplicationContext(), handler);
        biz.getMyCollection(MainActivity.user.getUserId()); //MainActivity.user.getUserId()
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText("我的收藏");
        tvTitleRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setText("编辑");
        tvTitleRight.setTextColor(getResources().getColor(R.color.colorActionBar));
        tvTitleLeft = (TextView) findViewById(R.id.tv_title_simple_title_left);

        layoutDelete = (RelativeLayout) findViewById(R.id.layout_my_collection_delete);
        layoutDelete.setVisibility(View.GONE);
        tvNumber = (TextView) findViewById(R.id.tv_collection_select_number);
        btnDelete = (Button) findViewById(R.id.btn_collection_delete);

        pullListView = (PullToRefreshListView) findViewById(R.id.xlistview_my_collection);
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
        pullListView.setMode(PullToRefreshBase.Mode.BOTH);

        listView = pullListView.getRefreshableView();
        ImageView ivEmpty = (ImageView) findViewById(R.id.iv_empty_view);
        ivEmpty.setImageResource(R.mipmap.no_collection);
        listView.setEmptyView(ivEmpty);

        adapter = new UserCollectionListAdapter(getApplicationContext(), lists) {
            @Override
            public void goToArgument(View view, View viewGroup, int position, int which) {

                Collection coll = lists.get(position);
                boolean sele = coll.isSelection();
                if (sele){
                    coll.setSelection(false);
                    collIdSet.remove(collIdSet.indexOf(coll.getColId()));
                }else{
                    coll.setSelection(true);
                    collIdSet.add(coll.getColId());
                }
                tvNumber.setText(String.valueOf(collIdSet.size()));
                adapter.notifyDataSetChanged();
            }
        };
        listView.setAdapter(adapter);
    }

    private void addListener() {
        tvTitleLeft.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (PullToRefreshBase.Mode.PULL_FROM_START.equals( pullListView.getCurrentMode())){ //下拉刷新
                    ToastCommon.toastShortShow(getApplicationContext(), null, "下拉刷新");
                    initData();
                } else if (PullToRefreshBase.Mode.PULL_FROM_END.equals( pullListView.getCurrentMode())){
                    ToastCommon.toastShortShow(getApplicationContext(), null, "上拉加载");
                    initData();
                }
            }
        });
    }

    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullListView.onRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title_simple_title_right:
                if (edit){ //点击，完成编辑，进行删除请求，并改变现实文字为“编辑”，重新请求数据
                    save();
                } else { //点击，进入可编辑状态,刷新列表
                    edit = true;
                    tvTitleRight.setText("完成");
                    adapter.setVisible(edit);
                    layoutDelete.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_title_simple_title_left:
                finish();
                break;
            case R.id.btn_collection_delete:
                delete();
                break;
        }
    }

    //删除所选
    private void delete() {

    }

    private void save() {
        edit = false;
        adapter.setVisible(edit);
        tvTitleRight.setText("编辑");
        layoutDelete.setVisibility(View.GONE);
    }
}
