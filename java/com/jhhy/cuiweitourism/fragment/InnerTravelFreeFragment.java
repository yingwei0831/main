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
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;

public class InnerTravelFreeFragment extends Fragment implements ArgumentOnClick, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, SlideDetailsLayout.IPagerScrollSlide {

    private static final String TITLE = "title";
    private static final String TYPE = "type"; //国内游，出境游

    private String title;
    private int type; //传过来的，区分国内游和出境游

    private String TAG = getClass().getSimpleName();

    private boolean top = true;
    private SlideDetailsLayout layout;

    private ISlideCallback mISlideCallback;

    private int page = 1;
    private int attr = 142; //自由游，本页固定的

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_INNER_FREEDOM:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getContext(), (String) msg.obj);
                    }else{
                        List<Travel> follow = (List<Travel>) msg.obj;
                        if (follow == null || follow.size() == 0){
                            ToastUtil.show(getContext(), (String) msg.obj);
                        }else{
                            mList = follow;
                            adapter.setData(mList);
                        }
                    }
                    break;
            }
        }
    };

    public InnerTravelFreeFragment() {

    }


    public static InnerTravelFreeFragment newInstance(String title, int type) {
        InnerTravelFreeFragment fragment = new InnerTravelFreeFragment();
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

    private PullToRefreshGridView gridViewFreedom;
    private GridView refreshableView;
    private List<Travel> mList = new ArrayList<>();
    private Tab1GridViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_travel_follow, container, false);
        setupView(view);
        addListener();
        getData();
        return view;
    }

    //获取自由游数据
    private void getData() {
        InnerTravelMainBiz biz = new InnerTravelMainBiz(getContext(), handler, Consts.MESSAGE_INNER_FREEDOM);
        biz.getInnerTravelData(String.valueOf(type), String.valueOf(attr), String.valueOf(page));
    }

    private void addListener() {
        refreshableView.setOnItemClickListener(this);
        refreshableView.setOnScrollListener(this);
    }

    private void setupView(View view){
        gridViewFreedom = (PullToRefreshGridView) view.findViewById(R.id.gv_inner_travel_follow);
        gridViewFreedom.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        gridViewFreedom.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        gridViewFreedom.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        gridViewFreedom.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        gridViewFreedom.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshableView = gridViewFreedom.getRefreshableView();

        //上拉、下拉监听函数
        gridViewFreedom.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                gridViewFreedom.onRefreshComplete();
            }
        });

        for(int i = 0; i < 11; i++){
            Travel travel = new Travel();
            travel.setTravelTitle(getString(R.string.tab1_recommend_for_you_title));
            travel.setTravelPrice("12100");
            mList.add(travel);
        }
        adapter = new Tab1GridViewAdapter(getContext(), mList, this);
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
        ToastUtil.show(getContext(), "讨价还价");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(getContext(), "进入详情页面");

    }

    protected void open(boolean smooth) {
        mISlideCallback.openDetails(smooth);
    }

    protected void close(boolean smooth) {
        mISlideCallback.closeDetails(smooth);
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0){
            top = true;
        }else{
            top = false;
        }
    }

    @Override
    public boolean isFirstChildOnTop() {
        return top;
    }
}
