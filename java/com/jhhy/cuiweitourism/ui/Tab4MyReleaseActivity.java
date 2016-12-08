package com.jhhy.cuiweitourism.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.UserReleaseListAdapter;
import com.jhhy.cuiweitourism.biz.UserReleaseBiz;
import com.jhhy.cuiweitourism.model.CustomActivity;
import com.jhhy.cuiweitourism.net.biz.MemberCenterActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.MemberReleaseDeleteFetch;
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

public class Tab4MyReleaseActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String TAG = Tab4MyReleaseActivity.class.getSimpleName();

    private TextView tvTitle;
    private TextView tvTitleLeft;
    private TextView tvTitleRight;

    private boolean edit = false; //编辑

    private RelativeLayout layoutDelete; //删除布局
    private TextView tvNumber;
    private Button btnDelete; //删除按钮

    private PullToRefreshListView pullListView;
    private ListView listView;

    private UserReleaseListAdapter adapter;
    private List<CustomActivity> lists = new ArrayList<>();

//    private List<String> collIdSet = new ArrayList<>(); //选择的收藏的id数组
    private String id;

//    private String id; //选中item的id

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.e(TAG, "-------------handleMessage-------------");
            switch (msg.what){
                case Consts.MESSAGE_GET_MY_RELEASE: //获取我的收藏
                    LoadingIndicator.cancel();
                    if (msg.arg1 == 1){
                        List<CustomActivity> newlists = (List<CustomActivity>) msg.obj;
                        if (newlists == null || newlists.size() == 0){
                            tvTitleRight.setVisibility(View.INVISIBLE);
                        } else {
                            tvTitleRight.setVisibility(View.VISIBLE);
                        }
                        lists = newlists;
                        adapter.setData(newlists);
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
        LoadingIndicator.show(this, getString(R.string.http_notice));
        getData();
    }
    private void getData() {
        UserReleaseBiz biz = new UserReleaseBiz(getApplicationContext(), handler);
        biz.getUserRelease(MainActivity.user.getUserId()); //
    }

    private void setupView() {
        tvTitle = (TextView) findViewById(R.id.tv_title_simple_title);
        tvTitle.setText(getString(R.string.fragment_mine_my_release));
        tvTitleRight = (TextView) findViewById(R.id.tv_title_simple_title_right);
        tvTitleRight.setVisibility(View.INVISIBLE);
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
        pullListView.setMode(PullToRefreshBase.Mode.DISABLED);

        listView = pullListView.getRefreshableView();
        ImageView ivEmpty = (ImageView) findViewById(R.id.iv_empty_view);
        ivEmpty.setImageResource(R.mipmap.no_release);
        listView.setEmptyView(ivEmpty);

        adapter = new UserReleaseListAdapter(getApplicationContext(), lists)
        {
            @Override
            public void goToArgument(View view, View viewGroup, int position, int which) {
                CustomActivity coll = lists.get(position);
                if (coll.isSelection()){
                    coll.setSelection(false);
                    tvNumber.setText(String.valueOf(0));
                    adapter.setSelection(position, false);
                }else{
                    coll.setSelection(true);
                    tvNumber.setText(String.valueOf(1));
                    adapter.setSelection(position, true);
                    id = coll.getId();
                }
//                CustomActivity comActy = lists.get(position);
//                boolean sele = comActy.isSelection();
//                if (sele){
//                    comActy.setSelection(false);
//                    collIdSet.remove(collIdSet.indexOf(comActy.getId()));
//                }else{
//                    comActy.setSelection(true);
//                    collIdSet.add(comActy.getId());
//                }
//                tvNumber.setText(String.valueOf(collIdSet.size()));
//                adapter.notifyDataSetChanged();
            }
        };
        adapter.setVisible(edit);
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
//                    initData();
                } else if (PullToRefreshBase.Mode.PULL_FROM_END.equals( pullListView.getCurrentMode())){
                    ToastCommon.toastShortShow(getApplicationContext(), null, "上拉加载");
//                    initData();
                }
            }
        });
        listView.setOnItemClickListener(this);
    }

//    private void initData() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullListView.onRefreshComplete();
//            }
//        }, 2000);
//    }

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
    private void delete() { //{"head":{"code":"User_delactivity"},"field":{"id":"1"}}
//        if (collIdSet == null || collIdSet.size() == 0){
//            return;
//        }
        if (id == null){
            ToastUtil.show(getApplicationContext(), "请选择要删除的发布条目");
            return;
        }
        LoadingIndicator.show(this, getString(R.string.http_notice));
        MemberCenterActionBiz biz = new MemberCenterActionBiz();
        MemberReleaseDeleteFetch fetch = new MemberReleaseDeleteFetch(id);
        biz.memberReleaseDelete(fetch, new BizGenericCallback<Object>() {
            @Override
            public void onCompletion(GenericResponseModel<Object> model) {
                LogUtil.e(TAG,"memberReleaseDelete: " + model.headModel.toString());
                if ("0000".equals(model.headModel.res_code)) {
                    //发布成功，重新请求发布数据
                    layoutDelete.setVisibility(View.GONE);
                    edit = false;
                    adapter.setVisible(edit);
                    tvTitleRight.setText("编辑");
                    ToastUtil.show(getApplicationContext(), "删除成功");
                    id = null;
                    getData();
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                    LoadingIndicator.cancel();
                }
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "删除发布信息出错");
                }
                LogUtil.e(TAG, "memberReleaseDelete: " + error.toString());
                LoadingIndicator.cancel();
            }
        });
    }

    private void save() {
        edit = false;
        adapter.setVisible(edit);
        tvTitleRight.setText("编辑");
        layoutDelete.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        LogUtil.e(TAG, "--onItemClick-- edit = " + edit);
//        if (edit) { //进入编辑发布的活动，可删除
//            id = lists.get((int) l).getId(); //当前只取最后一条
//
//            CustomActivity comActy = lists.get((int) l);
//            boolean sele = comActy.isSelection();
//            if (sele){
//                comActy.setSelection(false);
//                collIdSet.remove(collIdSet.indexOf(comActy.getId()));
//            }else{
//                comActy.setSelection(true);
//                collIdSet.add(comActy.getId());
//            }
//            tvNumber.setText(String.valueOf(collIdSet.size()));
//            adapter.notifyDataSetChanged();
//            tvNumber.setText(String.valueOf(collIdSet.size()));
//        } else { //进入活动详情
            //TODO 活动详情
            LogUtil.e(TAG, "此处可看到发布的详情");
        Bundle bundle = new Bundle();
        bundle.putString("id", lists.get((int)l).getId());
        bundle.putInt("type", 2); //2:我的发布，底下没有预定
        Intent intent = new Intent(getApplicationContext(), HotActivityDetailActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, VIEW_HOT_ACTIVITY_DETAIL);
//        }
    }
    private int VIEW_HOT_ACTIVITY_DETAIL = 6317; //查看我的发布详情

}
