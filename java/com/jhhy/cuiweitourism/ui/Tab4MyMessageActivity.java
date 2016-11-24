package com.jhhy.cuiweitourism.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.UserCommentListAdapter;
import com.jhhy.cuiweitourism.adapter.UserMessageListAdapter;
import com.jhhy.cuiweitourism.net.biz.MemberCenterActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.MemberCenterMsg;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberCenterMsgInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.MemberCenterRemarkInfo;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.LoadingIndicator;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;

public class Tab4MyMessageActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String TAG = Tab4MyMessageActivity.class.getSimpleName();
    private ImageView ivTitleLeft;
    private TextView tvActionBarTitle;
    private ActionBar actionBar;

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;

    private ArrayList<MemberCenterMsgInfo> mList = new ArrayList<>();
    private UserMessageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取ActionBar对象
        actionBar =  getSupportActionBar();
        //自定义一个布局，并居中
        actionBar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.title_tab1_inner_travel, null);
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)); //自定义ActionBar布局);
        actionBar.setElevation(0); //删除自带阴影

        setContentView(R.layout.layout_listview);
        setupView();
        getInternetData();
        addListener();
    }

    private void setupView() {
        tvActionBarTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_title_inner_travel);
        tvActionBarTitle.setText(getString(R.string.fragment_mine_my_message));
        ivTitleLeft = (ImageView) actionBar.getCustomView().findViewById(R.id.title_main_tv_left_location);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listView);
        pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);

        listView = pullToRefreshListView.getRefreshableView();
        ImageView ivEmpty = (ImageView) findViewById(R.id.iv_empty_view);
        ivEmpty.setImageResource(R.mipmap.no_message);
        listView.setEmptyView(ivEmpty);

        adapter = new UserMessageListAdapter(getApplicationContext(), mList);
        pullToRefreshListView.setAdapter(adapter);
    }

    private void addListener() {
        ivTitleLeft.setOnClickListener(this);
        pullToRefreshListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_main_tv_left_location:
                finish();
                break;
            default:
                break;
        }
    }


    private void getInternetData() {
        MemberCenterActionBiz memBiz = new MemberCenterActionBiz();

        MemberCenterMsg msg = new MemberCenterMsg(MainActivity.user.getUserId());
        memBiz.memberCenterGetMessageInfo(msg, new BizGenericCallback<ArrayList<MemberCenterMsgInfo>>() {
            @Override
            public void onCompletion(GenericResponseModel<ArrayList<MemberCenterMsgInfo>> model) {
                if ("0000".equals(model.headModel.res_code)) {
                    ArrayList<MemberCenterMsgInfo> array = model.body;
                    LogUtil.e(TAG,"memberCenterGetMessageInfo =" + array.toString());
                    mList = array;
                    refreshView();
                }else if ("0001".equals(model.headModel.res_code)){
                    ToastCommon.toastShortShow(getApplicationContext(), null, model.headModel.res_arg);
                }
                LoadingIndicator.cancel();
            }

            @Override
            public void onError(FetchError error) {
                if (error.localReason != null){
                    ToastCommon.toastShortShow(getApplicationContext(), null, error.localReason);
                }else{
                    ToastCommon.toastShortShow(getApplicationContext(), null, "请求我的消息信息出错");
                }
                LoadingIndicator.cancel();
                LogUtil.e(TAG, "memberCenterGetMessageInfo: " + error.toString());
            }
        });
    }

    /**
     * 得到我的评论数据，刷新页面
     */
    private void refreshView() {
        adapter.setData(mList);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastCommon.toastShortShow(getApplicationContext(), null, "没有消息详情");
    }

    public static void actionStart(Context context, Bundle data){
        Intent intent = new Intent(context, Tab4MyMessageActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
