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
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.adapter.OrderXListViewAdapter;
import com.jhhy.cuiweitourism.biz.OrderActionBiz;
import com.jhhy.cuiweitourism.biz.OrdersAllBiz;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.ui.Tab4OrderDetailsActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class OrdersWaitRefundFragment extends Fragment  implements ArgumentOnClick {

    private String TAG = getClass().getSimpleName();
    private static final String TITLE = "title";
    private static final String TYPE = "type";

    private String title;
    private String type;
    private boolean refresh;

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
                    if (refresh){
                        pullListView.onRefreshComplete();
                        refresh = false;
                    }
                    if (msg.arg1 == 1){
                        List<Order> listWaitRefund = (List<Order>) msg.obj;
                        lists = listWaitRefund;
                        adapter.setData(listWaitRefund);
                    }else{
                        ToastCommon.toastShortShow(getContext(), null, "获取数据失败");
                    }
                    break;
                case Consts.MESSAGE_ORDER_CANCEL_REFUND: //取消退款
                    ToastCommon.toastShortShow(getContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        //TODO 刷新数据
                        getData(type);
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
        biz.getWaitRefund(MainActivity.user.getUserId(), type); //MainActivity.user.getUserId() "1"
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
                    refresh();
                } else { //上拉加载
//                    getData();
//                    loadMore();
                }
            }
        });
    }

    private void refresh() {
        //TODO 下拉刷新
        if (refresh){
            return;
        }
        getData(type);
        refresh = true;
    }

    private void loadMore() {
        //TODO 加载更多
        pullListView.onRefreshComplete();
    }

    private void setupView(View view) {
        pullListView = (PullToRefreshListView) view.findViewById(R.id.listView_wait);
        pullListView.getLoadingLayoutProxy().setLastUpdatedLabel(Utils.getCurrentTime());
        pullListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
        pullListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullListView.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
        pullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        listView = pullListView.getRefreshableView();
        ImageView ivEmpty = (ImageView) view.findViewById(R.id.iv_empty_view);
        ivEmpty.setImageResource(R.mipmap.no_order);
        listView.setEmptyView(ivEmpty);

        adapter = new OrderXListViewAdapter(getContext(), lists, this) {
            /**
             * 订单列表item单击事件
             * @param position 此item位于列表中的位置
             */
            @Override
            public void onOrderItemClick(int position) {
                orderDetail(position);
//                Order order = lists.get(position);
//                Intent intent = new Intent(getContext(), Tab4OrderDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("orderSN", order.getOrderSN());
//                bundle.putInt("type", Integer.parseInt(order.getStatus()));
//                bundle.putString("typeId", order.getTypeId());
//                intent.putExtras(bundle);
//                startActivityForResult(intent, REQUEST_CODE_REFUND);
            }
        };
        listView.setAdapter(adapter);
    }

    /**
     * 订单详情
     */
    private void orderDetail(int position) {
        Order order = lists.get(position);
//        if ("2".equals(order.getTypeId()) && "0".equals(type)){ //酒店，在这里没有详情，没有操作，只显示
//            ToastCommon.toastShortShow(getContext(), null, "请选择酒店订单，再进入详细信息页");
//        } else {
        if ("80".equals(order.getTypeId())){ //火车票详情
            if (order.getSanfangorderno1() == null || order.getSanfangorderno1().length() == 0 ||
                    order.getSanfangorderno2() == null || order.getSanfangorderno2().length() == 0){
                ToastCommon.toastShortShow(getContext(), null, "订单号不存在，无详情");
                return;
            }
        }else if ("82".equals(order.getTypeId())){ //机票详情
            getPlaneTicketDetail(order);
            return;
        }
        getOrderDetail(order);
    }

    /**
     * 普通订单详情
     */
    private void getOrderDetail(Order order) {
        Intent intent = new Intent(getContext(), Tab4OrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderSN", order.getOrderSN());
        bundle.putInt("type", Integer.parseInt(order.getStatus())); //订单状态
        bundle.putString("typeId", order.getTypeId()); //订单类型
        bundle.putString("sanfangorderno1", order.getSanfangorderno1()); //酒店，签证，国内机票，国际机票，火车票
        bundle.putString("sanfangorderno2", order.getSanfangorderno2()); //酒店，签证，国内机票，国际机票，火车票
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_REFUND);
    }

    /**
     * 国内机票详情，国际机票详情
     */
    private void getPlaneTicketDetail(Order order) {
        Intent intent = new Intent(getContext(), Tab4OrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderSN", order.getOrderSN());
        bundle.putInt("type", Integer.parseInt(order.getStatus())); //订单状态
        if ("国内机票".equals(order.getProductName())){ //82
            bundle.putString("typeId", order.getTypeId()); //订单类型
        }else if ("国际机票".equals(order.getProductName())){ //83
            bundle.putString("typeId", "83"); //订单类型
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_REFUND);
    }

    private int REQUEST_CODE_REFUND = 1501; //

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "============ onActivityResult ============== ");
        if (requestCode == REQUEST_CODE_REFUND){
            if (resultCode == Activity.RESULT_OK){
//                getData(type); //刷新数据
                getContext().sendBroadcast(new Intent(Consts.ACTION_ORDER_UPDATE));
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
