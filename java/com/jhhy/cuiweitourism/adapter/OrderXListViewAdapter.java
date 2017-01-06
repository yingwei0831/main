package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.IOrderItemClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.model.Order;
import com.jhhy.cuiweitourism.net.utils.LogUtil;
import com.jhhy.cuiweitourism.utils.ImageLoaderUtil;
import com.jhhy.cuiweitourism.utils.Utils;

import java.util.List;

/**
 * Created by jiahe008 on 2016/9/5.
 */
public abstract class OrderXListViewAdapter extends BaseAdapter implements IOrderItemClick {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private List<Order> lists;
    private LayoutInflater inflater;
    private ArgumentOnClick onClick;
    private int type = 1; //0：全部订单

    public OrderXListViewAdapter(Context context, List<Order> lists, ArgumentOnClick onClick){
        inflater = LayoutInflater.from(context);
        this.onClick = onClick;
        this.context = context;
        this.lists = lists;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setData(List<Order> lists){
        this.lists = lists;
        notifyDataSetChanged();
    }

    public void addData(List<Order> list){
        this.lists.addAll(lists.size(), list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        OrderViewHolder holder = null;
        if(view == null){
            holder = new OrderViewHolder();
            view = inflater.inflate(R.layout.tab3_listview_item, null);
            holder.tvOrderStatus = (TextView) view.findViewById(R.id.tv_order_status);
            holder.tvOrderTime = (TextView) view.findViewById(R.id.tv_order_time);
            holder.tvOrderTitle = (TextView) view.findViewById(R.id.tv_order_title);
            holder.tvOrderPrice = (TextView) view.findViewById(R.id.tv_tab3_item_price);

            holder.ivOrderPic = (ImageView) view.findViewById(R.id.iv_tab3_listview_item_destination);

            holder.btnOrderCancel = (Button) view.findViewById(R.id.btn_order_cancel); //取消订单
            holder.btnOrderPayment = (Button) view.findViewById(R.id.btn_tab3_item_sign_contact); //签约付款
            holder.btnOrderCancelPayment = (Button) view.findViewById(R.id.btn_order_refund); //取消退款
            holder.btnOrderComment = (Button) view.findViewById(R.id.btn_order_comment); //去评价
            holder.btnGoRefund = (Button) view.findViewById(R.id.btn_order_go_refund); //退款
            view.setTag(holder);
        }else{
            holder = (OrderViewHolder) view.getTag();
        }

        Order order = (Order) getItem(position);
        if ("2".equals(order.getTypeId())){ //酒店，可以取消订单
            holder.tvOrderStatus.setText(context.getString(R.string.fragment_mine_hotel_reserve)); //预订成功
            holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
            holder.btnOrderComment.setVisibility(View.GONE); //去评价
            holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
            holder.btnOrderCancel.setVisibility(View.VISIBLE); //取消订单
            holder.btnGoRefund.setVisibility(View.GONE); //退款
            if (type == 0){
                holder.btnOrderCancel.setVisibility(View.GONE); //取消订单:全部订单不给酒店做任何操作
            }
        }
//        else if ("80".equals(order.getTypeId())){ //火车票：可以付款，可以取消订单；如果已经付款了，可以申请退款，否则没有申请退款；
//            if (order.getSanfangorderno() == null || order.getSanfangorderno().length() == 0){ //未付款，则进行付款
//                if (((order.getAddTime() != null && order.getAddTime().length() != 0 && !"null".equals(order.getAddTime())
//                        && (System.currentTimeMillis() / 1000 - Integer.parseInt(order.getAddTime()) < 15 * 60))) && "1".equals(order.getStatus())){
//                    holder.tvOrderStatus.setText(context.getString(R.string.fragment_mine_wait_pay));
//                    holder.btnOrderPayment.setVisibility(View.VISIBLE); //签约付款
//
//                    holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
//                    holder.btnOrderComment.setVisibility(View.GONE); //去评价
//                    holder.btnOrderCancel.setVisibility(View.GONE); //取消订单
//                    holder.btnGoRefund.setVisibility(View.GONE); //退款
//                } else if ("3".equals(order.getStatus())){ //已取消，不可支付
//                    holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_already_cancel));
//                    holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
//
//                    holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
//                    holder.btnOrderComment.setVisibility(View.GONE); //去评价
//                    holder.btnOrderCancel.setVisibility(View.GONE); //取消订单
//                    holder.btnGoRefund.setVisibility(View.GONE); //退款
//                }else if ("2".equals(order.getStatus())){ //付款成功，可申请退款
//                    holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_payde));
//                    holder.btnGoRefund.setVisibility(View.VISIBLE); //退款
//                    holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
//                    holder.btnOrderComment.setVisibility(View.GONE); //去评价
//                    holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
//                    holder.btnOrderCancel.setVisibility(View.GONE);
//                }
//            }else{ //已经付款，则申请退款
//                holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_payde));
//                holder.btnGoRefund.setVisibility(View.VISIBLE); //退款
//                holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
//                holder.btnOrderComment.setVisibility(View.GONE); //去评价
//                holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
//                holder.btnOrderCancel.setVisibility(View.GONE); //取消订单
//            }
//        }
//        else if ("82".equals(order.getTypeId())){ //机票
//            //15分钟之内，未支付——>等待付款
//            //超过15分钟未支付——>取消订单
//            //已经取消订单——>已取消
//            //已经付款——>申请退款
//            //已经申请退款——>正在退款
//            //正在退款——>取消退款
//            //交易完成——>待评价、已评价
//        }
        else {
            if ("1".equals(order.getStatus())) { //等待付款——>取消订单，签约付款
                holder.tvOrderStatus.setText(context.getString(R.string.fragment_mine_wait_pay));
                holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
                holder.btnOrderComment.setVisibility(View.GONE); //去评价
                holder.btnOrderPayment.setVisibility(View.VISIBLE); //签约付款
                holder.btnOrderCancel.setVisibility(View.VISIBLE); //取消订单
                holder.btnGoRefund.setVisibility(View.GONE); //退款

                if ("80".equals(order.getTypeId()) &&
                        (order.getSanfangorderno1() == null || order.getSanfangorderno1().length() == 0 ||
                        order.getSanfangorderno2() == null || order.getSanfangorderno2().length() == 0)){ //火车票
                    if (order.getAddTime() != null && order.getAddTime().length() != 0 && !"null".equals(order.getAddTime())
                            && (System.currentTimeMillis() / 1000 - Integer.parseInt(order.getAddTime()) < 15 * 60)){ //15分钟之内，可以付款,取消订单
//                        holder.btnOrderPayment.setVisibility(View.VISIBLE); //签约付款
                    }else{ //等待取消—>取消订单
                        holder.tvOrderStatus.setText(context.getString(R.string.fragment_mine_wait_cancel));
                        holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
                    }
                }
            } else if ("0".equals(order.getStatus())) { //正在退款——>取消退款
                holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_wait_refund));
                holder.btnOrderCancelPayment.setVisibility(View.VISIBLE); //取消退款
                holder.btnOrderComment.setVisibility(View.GONE); //去评价
                holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
                holder.btnOrderCancel.setVisibility(View.GONE); //取消订单
                holder.btnGoRefund.setVisibility(View.GONE); //退款
            } else if ("5".equals(order.getStatus())) { //交易完成（待评价，已完成）
                holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
                holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
                holder.btnOrderCancel.setVisibility(View.GONE); //取消订单
                if ("0".equals(order.getStatusComment())) { //待评价
                    holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_wait_comment));
                    holder.btnOrderComment.setVisibility(View.VISIBLE); //去评价
                    holder.btnGoRefund.setVisibility(View.GONE); //退款
                } else {
                    holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_already_comment));
                    holder.btnOrderComment.setVisibility(View.GONE); //去评价
                    holder.btnGoRefund.setVisibility(View.GONE); //退款
                }
            } else if ("3".equals(order.getStatus())) { //已取消，不显示
                holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_already_cancel));
                holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
                holder.btnOrderComment.setVisibility(View.GONE); //去评价
                holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
                holder.btnOrderCancel.setVisibility(View.GONE);
                holder.btnGoRefund.setVisibility(View.GONE); //退款
            } else if ("4".equals(order.getStatus())) { //已退款，不显示
                holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_already_refund));
                holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
                holder.btnOrderComment.setVisibility(View.GONE); //去评价
                holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
                holder.btnOrderCancel.setVisibility(View.GONE);
                holder.btnGoRefund.setVisibility(View.GONE); //退款
            } else if ("2".equals(order.getStatus())) { //付款成功——>申请退款
                holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_payde));
                holder.btnGoRefund.setVisibility(View.VISIBLE); //退款
                holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
                holder.btnOrderComment.setVisibility(View.GONE); //去评价
                holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
                holder.btnOrderCancel.setVisibility(View.GONE);
            }
        }
        final View finalConvertView = view;

        //取消订单
        final int idCancel = holder.btnOrderCancel.getId();
        holder.btnOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.goToArgument(finalConvertView, viewGroup, position, idCancel);
            }
        });

        //去评价
        final int idComment = holder.btnOrderComment.getId();
        holder.btnOrderComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.goToArgument(finalConvertView, viewGroup, position, idComment);
            }
        });

        //去付款
        final int idPayment = holder.btnOrderPayment.getId();
        holder.btnOrderPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.goToArgument(finalConvertView, viewGroup, position, idPayment);
            }
        });

        //取消付款
        final int idCancelPayment = holder.btnOrderCancelPayment.getId();
        holder.btnOrderCancelPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.goToArgument(finalConvertView, viewGroup, position, idCancelPayment);
            }
        });

        //退款
        final int idGoRefund = holder.btnGoRefund.getId();
        holder.btnGoRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.goToArgument(finalConvertView, viewGroup, position, idGoRefund);
            }
        });

        //单击item，进入详情
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOrderItemClick(position);
            }
        });
        if (order.getAddTime() != null && !"null".equals(order.getAddTime())) {
            holder.tvOrderTime.setText(Utils.getTimeStrYMD(Long.parseLong(order.getAddTime()) * 1000));
        }else{
            holder.tvOrderTime.setText(order.getAddTime());
        }
        holder.tvOrderTitle.setText(order.getProductName());
        holder.tvOrderPrice.setText(order.getPrice());
        ImageLoaderUtil.getInstance(context).displayImage(order.getPicPath(), holder.ivOrderPic);
        return view;
    }

    class OrderViewHolder{
        private TextView tvOrderTime;
        private TextView tvOrderStatus;
        private TextView tvOrderTitle;
        private TextView tvOrderPrice;
        private ImageView ivOrderPic;
        private Button btnOrderCancel;
        private Button btnOrderPayment;
        private Button btnOrderComment;
        private Button btnOrderCancelPayment;
        private Button btnGoRefund; //退款
    }
}
