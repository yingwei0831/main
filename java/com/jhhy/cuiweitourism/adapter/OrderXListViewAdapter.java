package com.jhhy.cuiweitourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhy.cuiweitourism.ArgumentOnClick;
import com.jhhy.cuiweitourism.IOrderItemClick;
import com.jhhy.cuiweitourism.R;
import com.jhhy.cuiweitourism.moudle.Order;
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

    public OrderXListViewAdapter(Context context, List<Order> lists, ArgumentOnClick onClick){
        inflater = LayoutInflater.from(context);
        this.onClick = onClick;
        this.context = context;
        this.lists = lists;
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
            view = inflater.inflate(R.layout.tab3_listview_item, null, false);
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
        if("1".equals(order.getStatus())){ //等待付款——>取消订单，签约付款
            holder.tvOrderStatus.setText(context.getString(R.string.fragment_mine_wait_pay));
            holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
            holder.btnOrderComment.setVisibility(View.GONE); //去评价
            holder.btnOrderPayment.setVisibility(View.VISIBLE); //签约付款
            holder.btnOrderCancel.setVisibility(View.VISIBLE); //取消订单
            holder.btnGoRefund.setVisibility(View.GONE); //退款
        }else if("0".equals(order.getStatus())) { //正在退款——>取消退款
            holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_wait_refund));
            holder.btnOrderCancelPayment.setVisibility(View.VISIBLE); //取消退款
            holder.btnOrderComment.setVisibility(View.GONE); //去评价
            holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
            holder.btnOrderCancel.setVisibility(View.GONE); //取消订单
            holder.btnGoRefund.setVisibility(View.GONE); //退款
        } else if("5".equals(order.getStatus())){ //交易完成（待评价，已完成）
            holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
            holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
            holder.btnOrderCancel.setVisibility(View.GONE); //取消订单
            if("0".equals(order.getStatusComment())){ //待评价
                holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_wait_comment));
                holder.btnOrderComment.setVisibility(View.VISIBLE); //去评价
                holder.btnGoRefund.setVisibility(View.GONE); //退款
            } else {
                holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_already_comment));
                holder.btnOrderComment.setVisibility(View.GONE); //去评价
                holder.btnGoRefund.setVisibility(View.GONE); //退款
            }
        } else if ("3".equals(order.getStatus())){ //已取消，不显示
            holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_already_cancel));
            holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
            holder.btnOrderComment.setVisibility(View.GONE); //去评价
            holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
            holder.btnOrderCancel.setVisibility(View.GONE);
            holder.btnGoRefund.setVisibility(View.GONE); //退款
        } else if ("4".equals(order.getStatus()) ){ //已退款，不显示
            holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_already_refund));
            holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
            holder.btnOrderComment.setVisibility(View.GONE); //去评价
            holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
            holder.btnOrderCancel.setVisibility(View.GONE);
            holder.btnGoRefund.setVisibility(View.GONE); //退款
        } else if ("2".equals(order.getStatus())){ //付款成功——>申请退款
            holder.tvOrderStatus.setText(context.getString(R.string.tab3_order_item_payde));
            holder.btnGoRefund.setVisibility(View.VISIBLE); //退款
            holder.btnOrderCancelPayment.setVisibility(View.GONE); //取消退款
            holder.btnOrderComment.setVisibility(View.GONE); //去评价
            holder.btnOrderPayment.setVisibility(View.GONE); //签约付款
            holder.btnOrderCancel.setVisibility(View.GONE);
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
        LogUtil.e(TAG, "order = " + order);
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
