package com.jhhy.cuiweitourism.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrderXListViewAdapter;
import com.jhhy.cuiweitourism.biz.OrdersAllBiz;
import com.jhhy.cuiweitourism.moudle.Order;
import com.jhhy.cuiweitourism.ui.RequestRefundActivity;
import com.jhhy.cuiweitourism.ui.Tab4OrderDetailsActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class OrdersWaitCommentFragment extends Fragment  implements ArgumentOnClick {

    private String TAG = getClass().getSimpleName();
    private static final String TITLE = "title";
    private static final String TYPE = "type";

    private String title;
    private String type;

    //    private XListView xListView;
    private PullToRefreshListView pullListView;
    private ListView listView;

    private List<Order> lists = new ArrayList<>();
    private OrderXListViewAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case Consts.MESSAGE_ORDERS_WAIT_COMMENT:
                    if (msg.arg1 == 0){
                        ToastUtil.show(getContext(), "获取数据失败");
                    }else{
                        List<Order> listWaitComment = (List<Order>) msg.obj;
                        if (listWaitComment == null || listWaitComment.size() == 0){
                            ToastUtil.show(getContext(), "获取数据为空");
                        }else{
                            lists = listWaitComment;
                            adapter.setData(listWaitComment);
                        }
                    }
                    break;
            }
        }
    };

    public OrdersWaitCommentFragment() {
        // Required empty public constructor
    }


    public static OrdersWaitCommentFragment newInstance(String title, String type) {
        OrdersWaitCommentFragment fragment = new OrdersWaitCommentFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_wait_pay, container, false);
        getInternetData();
        setupView(view);
        addListener();

        return view;
    }

    private void getInternetData() {
        OrdersAllBiz biz = new OrdersAllBiz(getContext(), handler);
        biz.getWaitComment("1", type); //MainActivity.user.getUserId()
    }

    public void getData(String type) {
        this.type = type;
        getInternetData();
        lists.clear();
        adapter.setData(lists);

    }

    private void addListener() {
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (PullToRefreshBase.Mode.PULL_FROM_START.equals( pullListView.getCurrentMode())){ //下拉刷新
//                    mListItems.addFirst(result); //在头部增加新添内容
//                    mAdapter.notifyDataSetChanged();//通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
                    // Call onRefreshComplete when the list has been refreshed.
                    refresh();
                } else { //上拉加载
                    loadMore();
                }
            }
        });
    }

    private void refresh() {
        //TODO 下拉刷新
        pullListView.onRefreshComplete();
    }
    private void loadMore() {
        //TODO 加载更多
        pullListView.onRefreshComplete();
    }

    private void setupView(View view) {
//        xListView = (XListView) view.findViewById(R.id.listView_wait);
        pullListView = (PullToRefreshListView) view.findViewById(R.id.listView_wait);
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        pullListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");

        pullListView.setMode(PullToRefreshBase.Mode.BOTH);

        listView = pullListView.getRefreshableView();

        adapter = new OrderXListViewAdapter(getContext(), lists, this) {
            /**
             * 订单列表item单击事件，进入Order详情页面
             * @param position 此item位于列表中的位置
             */
            @Override
            public void onOrderItemClick(int position) {
                Order order = lists.get(position);
                Intent intent = new Intent(getContext(), Tab4OrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderSN", order.getOrderSN());
                bundle.putInt("type", Integer.parseInt(order.getStatus()));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_DETAIL);
            }
        };
        listView.setAdapter(adapter);
    }

    private int REQUEST_CODE_DETAIL = 1501; //此处是待评论页，进入详情之后，可能会进行评论
    private int REQUEST_COMMENT = 1502; //单机了去评论按钮，进入评论页面
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "============ onActivityResult ============== ");
        if (requestCode == REQUEST_COMMENT){
            if (resultCode == Activity.RESULT_OK){
                getData(type); //刷新数据
            }
        } else if (requestCode == REQUEST_CODE_DETAIL){
            if (resultCode == Activity.RESULT_OK){
                getData(type); //刷新数据
            }
        }
    }

    /**
     * @param view      layout布局
     * @param viewGroup
     * @param position  列表中的位置
     * @param which     控件的id
     */
    @Override
    public void goToArgument(View view, View viewGroup, int position, int which) {
        switch (which){

            case R.id.btn_order_comment: //去评价,进入评论页面
//                ToastUtil.show(getContext(), "去评价");
                Intent intent = new Intent(getContext(), RequestRefundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", lists.get(position));
                bundle.putInt("type", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_COMMENT);

                break;

        }
    }

}
