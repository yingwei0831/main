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
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrderXListViewAdapter;
import com.jhhy.cuiweitourism.biz.OrderActionBiz;
import com.jhhy.cuiweitourism.biz.OrdersAllBiz;
import com.jhhy.cuiweitourism.moudle.Order;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.ui.Tab4OrderDetailsActivity;
import com.jhhy.cuiweitourism.utils.Consts;
import com.jhhy.cuiweitourism.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.just.sun.pricecalendar.ToastCommon;
import com.markmao.pulltorefresh.widght.XListView;

import java.util.ArrayList;
import java.util.List;

public class OrdersWaitRefundFragment extends Fragment  implements ArgumentOnClick {

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
                case Consts.MESSAGE_ORDERS_WAIT_REFUND: //订单详情
                    if (msg.arg1 == 0){
                        ToastUtil.show(getContext(), "获取数据失败");
                    }else{
                        List<Order> listWaitRefund = (List<Order>) msg.obj;
                        if (listWaitRefund == null || listWaitRefund.size() == 0){
                            ToastUtil.show(getContext(), "获取数据为空");
                        }else{
                            lists = listWaitRefund;
                            adapter.setData(listWaitRefund);
                        }
                    }
                    break;
                case Consts.MESSAGE_ORDER_CANCEL_REFUND: //取消退款
                    ToastCommon.toastShortShow(getContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        //TODO 刷新数据
                        getData(type);
                    }
                    break;
            }
        }
    };

    public OrdersWaitRefundFragment() {
    }

    public static OrdersWaitRefundFragment newInstance(String title, String type) {
        OrdersWaitRefundFragment fragment = new OrdersWaitRefundFragment();
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
        View view = inflater.inflate(R.layout.fragment_orders_wait_pay, container, false);
        getInternetData();
        setupView(view);
        addListener();

        return view;
    }

    private void getInternetData() {
        OrdersAllBiz biz = new OrdersAllBiz(getContext(), handler);
        biz.getWaitRefund("1", type); //MainActivity.user.getUserId()
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
                    ToastCommon.toastShortShow(getContext(), null, "下拉刷新");
                    initData();
                } else if (PullToRefreshBase.Mode.PULL_FROM_END.equals( pullListView.getCurrentMode())){
                    ToastCommon.toastShortShow(getContext(), null, "上拉加载");
                    initData();
                }
            }
        });
    }

    private void setupView(View view) {
        pullListView = (PullToRefreshListView) view.findViewById(R.id.listView_wait);
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        pullListView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");
        pullListView.setMode(PullToRefreshBase.Mode.BOTH);

        listView = pullListView.getRefreshableView();

        adapter = new OrderXListViewAdapter(getContext(), lists, this) {
            /**
             * 订单列表item单击事件
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
                startActivityForResult(intent, REQUEST_CODE_REFUND);
            }
        };
        listView.setAdapter(adapter);
    }

    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullListView.onRefreshComplete();
            }
        }, 2000);
    }

    private int REQUEST_CODE_REFUND = 1501; //

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "============ onActivityResult ============== ");
        if (requestCode == REQUEST_CODE_REFUND){
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
            case R.id.btn_order_refund: //取消退款
                OrderActionBiz biz = new OrderActionBiz(getContext(), handler);
                biz.requestCancelRefund(lists.get(position).getOrderSN());
                break;

        }
    }
}
