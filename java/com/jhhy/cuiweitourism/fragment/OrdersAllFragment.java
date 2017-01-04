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
import com.jhhy.cuiweitourism.net.biz.HotelActionBiz;
import com.jhhy.cuiweitourism.net.models.FetchModel.HotelOrderCancelRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.HotelOrderResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.ui.MainActivity;
import com.jhhy.cuiweitourism.ui.RequestRefundActivity;
import com.jhhy.cuiweitourism.ui.SelectPaymentActivity;
import com.jhhy.cuiweitourism.ui.Tab4OrderDetailsActivity;
import com.jhhy.cuiweitourism.net.utils.Consts;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ToastUtil;
import com.jhhy.cuiweitourism.utils.Utils;
import com.just.sun.pricecalendar.ToastCommon;

import java.util.ArrayList;
import java.util.List;

public class OrdersAllFragment extends Fragment implements ArgumentOnClick {

    private static final String TAG = OrdersAllFragment.class.getSimpleName();
    private static final String TITLE = "title";
    private static final String TYPE = "type";

//    private String title;
    private String type = "0"; //type 0.全部订单、1.线路、2.酒店、8签证、14私人定制、202活动、82机票、80火车票
    private boolean refresh; //刷新或加载更多

    private PullToRefreshListView pullListView;
    private ListView listView;

    private List<Order> lists = new ArrayList<>();
    private OrderXListViewAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Consts.MESSAGE_ORDERS_ALL: //获取订单
                    if (refresh){
                        pullListView.onRefreshComplete();
                        refresh = false;
                    }
                    if (msg.arg1 == 0){
                        ToastCommon.toastShortShow(getContext(), null, "获取失败");
                    } else {
                        List<Order> listNew = (List<Order>) msg.obj;
//                        if (listNew == null || listNew.size() == 0) {
//                            ToastCommon.toastShortShow(getContext(), null, "没有数据");
//                        }else {
                            lists = listNew;
                            adapter.setData(lists);
//                        }
                    }
                    break;
                case Consts.MESSAGE_ORDER_CANCEL: //取消订单
                    ToastCommon.toastShortShow(getContext(), null, String.valueOf(msg.obj));
                    if (msg.arg1 == 1){
                        getData(type);
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
    public OrdersAllFragment() {
        // Required empty public constructor
    }


    public static OrdersAllFragment newInstance(String title, String type) {
        OrdersAllFragment fragment = new OrdersAllFragment();
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
//            title = getArguments().getString(TITLE);
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i(TAG, "=== onCreateView ===");
        View view = inflater.inflate(R.layout.fragment_orders_wait_pay, container, false);
        getInternetData();
        setupView(view);
        addListener();
        return view;
    }

    private void getInternetData() {
        if (MainActivity.logged) {
            OrdersAllBiz biz = new OrdersAllBiz(getContext(), handler);
            biz.getAllOrders(MainActivity.user.getUserId(), type); //MainActivity.user.getUserId() "1"
        }else{
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putInt("type", 2);
//            intent.putExtras(bundle);
//            getActivity().startActivityForResult(intent, REQUEST_LOGIN);
            ToastUtil.show(getContext(), "请回到个人中心页登录后重试");
        }
    }

    public void getData(String type) {
        this.type = type;
        getInternetData();
        lists.clear();
        adapter.setData(lists);
        adapter.setType(Integer.parseInt(type));
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
        refresh = true;
        getData(type);
//        pullListView.onRefreshComplete();
    }
    private void loadMore() {
        //TODO 加载更多
        getInternetData();
//        pullListView.onRefreshComplete();
    }

    private void setupView(View view) {
//        xListView = (XListView) view.findViewById(R.id.listView_wait);
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
            @Override
            public void onOrderItemClick(int position) { //订单详情
                Order order = lists.get(position);
                if ("2".equals(order.getTypeId()) && "0".equals(type)){ //酒店，在这里没有详情，没有操作，只显示
                    ToastCommon.toastShortShow(getContext(), null, "请选择酒店订单，再进入详细信息页");
                } else {
                    Intent intent = new Intent(getContext(), Tab4OrderDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderSN", order.getOrderSN());
                    bundle.putInt("type", Integer.parseInt(order.getStatus())); //订单状态
                    bundle.putString("typeId", order.getTypeId()); //订单类型
                    bundle.putString("sanfangorderno", order.getSanfangorderno()); //酒店，签证，国内机票，国际机票，火车票
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CODE_DETAIL);
                }
            }
        };
        adapter.setType(Integer.parseInt(type));
        pullListView.setAdapter(adapter);
    }

    private int REQUEST_CODE_DETAIL = 1501; //订单详情
    private int REQUEST_COMMENT = 1502; //去评论按钮，进入评论页面
    private int REQUEST_REFUND = 1503; //退款按钮，进入申请退款页面
    private int REQUEST_CODE_PAY = 1504; //签约付款

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG, "============ onActivityResult ============== ");
        if (requestCode == REQUEST_CODE_DETAIL){ //订单详情
            if (resultCode == Activity.RESULT_OK){
//                getData(type);
                getContext().sendBroadcast(new Intent(Consts.ACTION_ORDER_UPDATE));
            }
        } else if (requestCode == REQUEST_COMMENT){ //去评论
            if (resultCode == Activity.RESULT_OK){
//                getData(type);
                getContext().sendBroadcast(new Intent(Consts.ACTION_ORDER_UPDATE));
            }
        } else if (requestCode == REQUEST_REFUND){ //申请退款
            if (resultCode == Activity.RESULT_OK){
//                getData(type);
                getContext().sendBroadcast(new Intent(Consts.ACTION_ORDER_UPDATE));
            }
        } else if (requestCode == REQUEST_CODE_PAY){ //签约付款
            if (resultCode == Activity.RESULT_OK){
//                getData(type);
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
        LogUtil.e(TAG, "position = " + position +", which = " + which);
        Order order = lists.get(position);

        switch (which){
            case R.id.btn_order_cancel: //取消订单
                if ("2".equals(order.getTypeId())){ //酒店
                    cancelHotelOrder(position);
                }else {
                    OrderActionBiz biz = new OrderActionBiz(getContext(), handler);
                    biz.requestCancelOrder(order.getOrderSN());
                }
                break;
            case R.id.btn_order_refund: //取消退款
                OrderActionBiz bizRefund = new OrderActionBiz(getContext(), handler);
                bizRefund.requestCancelRefund(order.getOrderSN());
                break;
            case R.id.btn_order_comment: //去评价——>进入评论页面
                Intent intent = new Intent(getContext(), RequestRefundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                bundle.putInt("type", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_COMMENT);
                break;
            case R.id.btn_tab3_item_sign_contact: //签约付款
                Intent intentPay = new Intent(getContext(), SelectPaymentActivity.class);
                Bundle bundlePay = new Bundle();
                bundlePay.putSerializable("order", order);
                intentPay.putExtras(bundlePay);
                startActivityForResult(intentPay, REQUEST_CODE_PAY);
                break;
            case R.id.btn_order_go_refund: //退款——>进入申请退款页面
                Intent intentRequestRefund = new Intent(getContext(), RequestRefundActivity.class);
                Bundle bundleRefund = new Bundle();
                bundleRefund.putSerializable("order", order);
                bundleRefund.putInt("type", -1);
                intentRequestRefund.putExtras(bundleRefund);
                startActivityForResult(intentRequestRefund, REQUEST_REFUND);
                break;
        }
    }

    private void cancelHotelOrder(int position) { //直接取消订单或进入订单详情后再取消订单
        Order order = lists.get(position);
        HotelActionBiz hotelActionBiz = new HotelActionBiz();
        HotelOrderCancelRequest request = new HotelOrderCancelRequest(order.getOrderSN(), "", "", "");
        hotelActionBiz.setHotelOrderCancel(request, new BizGenericCallback<HotelOrderResponse>() {
            @Override
            public void onCompletion(GenericResponseModel<HotelOrderResponse> model) {

            }

            @Override
            public void onError(FetchError error) {

            }
        });
    }


}
