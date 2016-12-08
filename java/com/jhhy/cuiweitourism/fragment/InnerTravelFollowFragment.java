package com.jhhy.cuiweitourism.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.ISlideCallback;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.biz.InnerTravelMainBiz;
import com.jhhy.cuiweitourism.model.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.ui.InnerTravelDetailActivity;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;


public class InnerTravelFollowFragment extends Fragment implements ArgumentOnClick, SlideDetailsLayout.IPagerScrollSlide, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private static final String TITLE = "title";
    private static final String TYPE = "type"; //国内游，出境游

    private String title;
    private int type; //传过来的，区分国内游和出境游

    private static final String TAG = InnerTravelFollowFragment.class.getSimpleName();

    private ISlideCallback mISlideCallback;
    private boolean top = true;

    private PullToRefreshGridView gridViewFollow; //跟团游
    private List<Travel> lists = new ArrayList<>();
    private Tab1GridViewAdapter adapter;
    private GridView refreshableView;

    private int page = 1;
    private int attr = 1; //跟团游，本页固定的


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_INNER_FOLLOW:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getContext(), (String) msg.obj);
                    }else{
                        List<Travel> follow = (List<Travel>) msg.obj;
                        if (follow == null || follow.size() == 0){
                            ToastUtil.show(getContext(), "获取数据为空");
                        }else{
                            lists = follow;
                            adapter.setData(lists);
                        }
                    }
                    break;
                case Consts.NET_ERROR:
                    ToastUtil.show(getContext(), "请检查网络后重试");
                    break;
                case Consts.NET_ERROR_SOCKET_TIMEOUT:
                    ToastUtil.show(getContext(), "与服务器链接超时，请重试");
                    break;
            }
        }
    };

    public InnerTravelFollowFragment() {
        // Required empty public constructor
    }


    public static InnerTravelFollowFragment newInstance(String title, int type) {
        InnerTravelFollowFragment fragment = new InnerTravelFollowFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISlideCallback = (ISlideCallback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            type = getArguments().getInt(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inner_travel_follow, container, false);
        setupView(view);
        addListener();
        getData();
        return view;
    }

    private void addListener() {
        gridViewFollow.setOnItemClickListener(this);

        refreshableView.setOnScrollListener(this);
    }

    //获取跟团游数据
    private void getData() {
        InnerTravelMainBiz biz = new InnerTravelMainBiz(getContext(), handler, Consts.MESSAGE_INNER_FOLLOW);
        biz.getInnerTravelData(String.valueOf(type), String.valueOf(attr), String.valueOf(page));
    }

    private void setupView(View view) {
        gridViewFollow = (PullToRefreshGridView) view.findViewById(R.id.gv_inner_travel_follow);
        gridViewFollow.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        gridViewFollow.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        gridViewFollow.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        gridViewFollow.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");        

        //上拉、下拉设定
        gridViewFollow.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshableView = gridViewFollow.getRefreshableView();

        //上拉、下拉监听函数
        gridViewFollow.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                gridViewFollow.onRefreshComplete();
            }
        });

        for(int i = 0; i < 15; i++){
            Travel travel = new Travel();
            travel.setTravelTitle(getString(R.string.tab1_recommend_for_you_title));
            travel.setTravelPrice("12100");
            lists.add(travel);
        }
        adapter = new Tab1GridViewAdapter(getContext(), lists, this);
        refreshableView.setAdapter(adapter);
    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
//        ToastUtil.showShortToast(getContext(), "讨价还价"); //本类貌似没用
//        if (MainActivity.logged) { //|| (number != null && !"null".equals(number) && pwd != null && !"null".equals(pwd))
//            Intent intent = new Intent(getContext(), EasemobLoginActivity.class);
//            startActivity(intent);
//        }else{
//            ToastUtil.show(getContext(), "请登录后再试");
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(getContext(), "进入详情页面");
        Travel travel = lists.get((int) l);
        Bundle bundle = new Bundle();
        bundle.putString("id", travel.getId());
        InnerTravelDetailActivity.actionStart(getContext(), bundle);
    }

    protected void open(boolean smooth) {
        LogUtil.e(TAG, "=======open=======");
        mISlideCallback.openDetails(smooth);
    }

    protected void close(boolean smooth) {
        mISlideCallback.closeDetails(smooth);
    }

    @Override
    public boolean isFirstChildOnTop() {
        return top;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (i == 0){
            top = true;
        }else{
            top = false;
        }
    }
}
