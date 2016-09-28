package com.jhhy.cuiweitourism.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.Tab1GridViewAdapter;
import com.jhhy.cuiweitourism.moudle.Travel;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.ns.fhvp.IPagerScroll;
import com.ns.fhvp.PagerScrollUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1BottomContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1BottomContentFragment extends Fragment implements IPagerScroll, ArgumentOnClick {

    private static final String TAG = Tab1BottomContentFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private PullToRefreshGridView gridView;
    private List<Travel> lists = new ArrayList<>();
    private Tab1GridViewAdapter adapter;

    public Tab1BottomContentFragment() {
        // Required empty public constructor
    }


    public static Tab1BottomContentFragment newInstance(String param1, String param2) {
        Tab1BottomContentFragment fragment = new Tab1BottomContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1_content, container, false);
        gridView = (PullToRefreshGridView) view.findViewById(R.id.pull_to_refresh_grid_view);
        //这几个刷新Label的设置
        gridView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        gridView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        gridView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        gridView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        //上拉、下拉设定
        gridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);


        for(int i = 0; i < 19; i++){
            Travel travel = new Travel();
            travel.setTravelTitle(getString(R.string.tab1_recommend_for_you_title));
            travel.setTravelPrice("12100");
            lists.add(travel);
        }
        adapter = new Tab1GridViewAdapter(getContext(), lists, this);
        gridView.setAdapter(adapter);

        addListener();
        return view;
    }

    private boolean top;

    private void addListener() {
        //上拉、下拉监听函数
        gridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                gridView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                gridView.onRefreshComplete();
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVistbleItem, int visibleItemCount, int totalItemCount) {
//                LogUtil.i(TAG, "--------onScroll--------");
//                LogUtil.i(TAG, "firstVistbleItem = " + firstVistbleItem +  "visibleItemCount = " + visibleItemCount +  "totalItemCount = " + totalItemCount);
                if(firstVistbleItem == 0){
                    top = true;
                }
            }
        });
    }


    @Override
    public boolean isFirstChildOnTop() {
//        return PagerScrollUtils.isFirstChildOnTop(gridView);
        return  top;
    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        //讨价还价
    }
}
